package com.olp.user_mgmt.rest;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.olp.user_mgmt.common.CommonConstants;
import com.olp.user_mgmt.common.CommonUtils;
import com.olp.user_mgmt.common.to.ResponseStatus;
import com.olp.user_mgmt.entity.AppUser;
import com.olp.user_mgmt.entity.AppUserLoginFailedAttempts;
import com.olp.user_mgmt.entity.User;
import com.olp.user_mgmt.service.AppUserServiceImpl;
import com.olp.user_mgmt.service.NotificationServiceImpl;
import com.olp.user_mgmt.to.AppUserTO;
import com.olp.user_mgmt.to.AuthResponse;
import com.olp.user_mgmt.to.CaptchaUtil;
import com.olp.user_mgmt.to.JwtTokenFilter;
import com.olp.user_mgmt.to.JwtTokenUtil;
import com.olp.user_mgmt.to.NotificationRequest;
import com.olp.user_mgmt.to.TokenBlacklist;

@RestController
public class AuthApi {
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthApi.class);

	@Autowired
	AuthenticationManager authManager;
	@Autowired
	JwtTokenUtil jwtUtil;
	@Autowired
	JwtTokenFilter jwtTokenFilter;
	@Autowired
	AppUserServiceImpl appUserServiceImpl;
	@Autowired
	Environment env;
	@Autowired
	CaptchaUtil captchaUtil;

	@Autowired
	NotificationServiceImpl notificationServiceImpl;

	@PostMapping("/auth/login")
	public HashMap<String, Object> login(@RequestHeader(required = true) String username,
			@RequestHeader(required = false) String password,
			@RequestHeader(defaultValue = "false") String enableCaptcha) throws MessagingException {
		LOGGER.info(CommonConstants.BEGIN);
		HashMap<String, Object> apiResponse = new HashMap<String, Object>();
		HashMap<String, Object> apiResponse1 = new HashMap<String, Object>();
		User user = null;
		try {
			Authentication authentication = authManager
					.authenticate(new UsernamePasswordAuthenticationToken(username, password));

			user = (User) authentication.getPrincipal();
			String accessToken = jwtUtil.generateAccessToken(user);

			AppUserLoginFailedAttempts apUserLastLogin = null;
			if (StringUtils.isNotBlank(user.getUsername())) {
				apUserLastLogin = appUserServiceImpl.getAppUserLoginFailedAttemptsByUserName(username);
			}

			AuthResponse response = null;
			if (null == apUserLastLogin) {
				response = new AuthResponse(user.getUsername(), accessToken);
			} else if (apUserLastLogin.getLastLoginTime() != null) {
				String lastLoginTime = new SimpleDateFormat("dd-MMM-YYYY hh:mm:ss a")
						.format(apUserLastLogin.getLastLoginTime());
				response = new AuthResponse(user.getUsername(), accessToken, lastLoginTime);
			}

			apiResponse1.put("statusCode", 200);
			apiResponse1.put("statusMessage", "OK");
			apiResponse.put("responseStatus", apiResponse1);
			apiResponse.put("response", response);

			appUserServiceImpl.saveOrUpdateAppUserLoginFailedAttempts(apUserLastLogin, user);

			AppUser temPAppUser = appUserServiceImpl.getAppUserByUserName(username);

			NotificationRequest notificationRequest = new NotificationRequest();
			AppUserTO appUserTo = new AppUserTO();
			appUserTo.setDisplayName(temPAppUser.getDisplayName());
			appUserTo.setEmailId(temPAppUser.getEmailId());
			appUserTo.setMobileNo(temPAppUser.getMobileNo());
			appUserTo.setUserName(temPAppUser.getUserName());
			appUserTo.setUserId(temPAppUser.getUserId());
			notificationRequest.setAppuser(appUserTo);
			notificationRequest.setResetToken(temPAppUser.getResetToken());

			//notificationServiceImpl.sendLoginSuccessNotification(notificationRequest);

			// Validating Captcha
//			boolean enableCaptcha1 = Boolean.parseBoolean(enableCaptcha);
//			if (enableCaptcha1) {
//				HashMap<String, Object> captchaResponse = appUserServiceImpl .getCaptchaDataByJessionId(request.getJsessionId(), request.getCaptcha());
//				if (null != captchaResponse && null != captchaResponse.get("statusCode")) {
//					apiResponse.put("responseStatus", captchaResponse);
//					apiResponse.put("response", null);
//				}
//			}
		} catch (BadCredentialsException ex) {
			LOGGER.error(ex.getMessage(), ex);
			apiResponse1.put("statusCode", 401);
			apiResponse1.put("statusMessage", "Invalid Username or Password");
			apiResponse.put("responseStatus", apiResponse1);

		}
		LOGGER.info(CommonConstants.END);
		return apiResponse;
	}

	@GetMapping("/auth/forgotPassword")
	public ResponseStatus sendPasswordLink(HttpServletRequest request,
			@RequestHeader(required = false) String username) {
		LOGGER.info(CommonConstants.BEGIN);
		ResponseStatus responseStatus = new ResponseStatus();
		try {
			if (StringUtils.isNotBlank(username) && username.length() == 0) {
				responseStatus.setStatusCode(Integer.parseInt(env.getProperty("AUTH_USERNAME_MAND_CODE")));
				responseStatus.setStatusMessage(env.getProperty("AUTH_USERNAME_MAND_MESSSAGE"));
				return responseStatus;
			}

			AppUser temPAppUser = appUserServiceImpl.getAppUserByUserName(username);
			if (temPAppUser == null || temPAppUser.getUserName() == null) {
				responseStatus.setStatusCode(Integer.parseInt(env.getProperty("AUTH_USERNAME_CODE")));
				responseStatus.setStatusMessage(env.getProperty("AUTH_USER_MESSSAGE"));
				return responseStatus;
			} else {
				temPAppUser.setResetToken(UUID.randomUUID().toString() + ":" + System.currentTimeMillis());
				LocalDateTime dateTime = LocalDateTime.now().plus(Duration.of(20, ChronoUnit.MINUTES));
				Date tmfn = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
				temPAppUser.setResetTokenExpiryTime(tmfn);
				temPAppUser.setModifiedBy(temPAppUser.getUserId());
				temPAppUser.setModifiedDate(new Date());

				appUserServiceImpl.updateAppUserPassword(temPAppUser, "Forgot Password");
				String path = request.getRequestURL().toString();

				if (StringUtils.isNotBlank(path)) {
					if (path.contains("/auth/forgotPassword")) {
						path = path.replace("/auth/forgotPassword", "/#/");
					}
				}
				LOGGER.info("MODIFIED PATH", path);

				NotificationRequest notificationRequest = new NotificationRequest();
				AppUserTO appUserTo = new AppUserTO();
				appUserTo.setDisplayName(temPAppUser.getDisplayName());
				appUserTo.setEmailId(temPAppUser.getEmailId());
				appUserTo.setMobileNo(temPAppUser.getMobileNo());
				appUserTo.setUserName(temPAppUser.getUserName());
				appUserTo.setUserId(temPAppUser.getUserId());
				notificationRequest.setAppuser(appUserTo);
				notificationRequest.setContextPath(path);
				notificationRequest.setResetToken(temPAppUser.getResetToken());

				notificationServiceImpl.sendForgotPasswordLink(notificationRequest);

				responseStatus.setStatusCode(Integer.parseInt(env.getProperty("AUTH_PASSWORD_MAIL_CODE")));
				responseStatus.setStatusMessage(env.getProperty("AUTH_PASSWORD_MAIL_MESSSAGE"));
				return responseStatus;
			}
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			responseStatus.setStatusCode(Integer.parseInt(env.getProperty("AUTH_PAGE_LOAD_FAIL_CODE")));
			responseStatus.setStatusMessage(env.getProperty("AUTH_PAGE_LOAD_FAIL_MESSAGE"));
			return responseStatus;
		}
	}

	@PostMapping("/auth/resetPassword")
	public ResponseStatus saveAppUserPassword(@RequestHeader(required = false) String username,
			@RequestHeader(required = false) String password) {
		LOGGER.info(CommonConstants.BEGIN);
		ResponseStatus responseStatus = new ResponseStatus();
		try {
			if (null != username && StringUtils.isNotBlank(username)) {
				AppUser editUser = appUserServiceImpl.getAppUserByUserName(username);
				ResponseStatus responseStatus2 = appUserPasswordCreation(editUser);
				if (null != responseStatus2.getStatusMessage()
						&& !"SUCCESS".equalsIgnoreCase(responseStatus2.getStatusMessage())) {
					return responseStatus2;
				}
				if (null != editUser) {
					MessageDigest md = MessageDigest.getInstance("SHA-256");
					String saltToken = generateSaltToken();
					md.update(saltToken.getBytes());
					byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
					StringBuilder sb = new StringBuilder();
					for (byte b : hashedPassword) {
						sb.append(String.format("%02x", b));
					}
//					String saltedHashPwd = null;
//					if (StringUtils.isNotBlank(sb)) {
//						saltedHashPwd = new String(sb);
//					}
//					if (StringUtils.isNotBlank(saltToken)) {
//						// using BCryptPasswordEncoder for newsms specfic
//						BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//						String passwordDecode = passwordEncoder.encode(password);
//						editUser.setUserPassword(passwordDecode);
//					}
//					if (StringUtils.isNotBlank(saltedHashPwd)) {
//						editUser.setSaltedHashPwd(saltedHashPwd);
//					}
					editUser.setResetToken("");
					editUser.setResetTokenExpiryTime(null);
					editUser.setModifiedBy(null);
					editUser.setModifiedDate(new Date());

					appUserServiceImpl.updateAppUserPassword(editUser, "Reset Password");

					NotificationRequest notificationRequest = new NotificationRequest();
					AppUserTO appUserTo = new AppUserTO();
					appUserTo.setDisplayName(editUser.getDisplayName());
					appUserTo.setEmailId(editUser.getEmailId());
					appUserTo.setMobileNo(editUser.getMobileNo());
					appUserTo.setUserName(editUser.getUserName());
					appUserTo.setUserId(editUser.getUserId());
					notificationRequest.setAppuser(appUserTo);
					notificationRequest.setResetToken(editUser.getResetToken());

					notificationServiceImpl.sendResetPasswordSuccessNotification(notificationRequest);
				}
			}
			responseStatus.setStatusCode(Integer.parseInt(env.getProperty("AUTH_SAVE_PWD_SET_CODE")));
			responseStatus.setStatusMessage(env.getProperty("AUTH_SAVE_PWD_SET_MESSSAGE"));

		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			responseStatus.setStatusCode(Integer.parseInt(env.getProperty("AUTH_SAVE_PWD_LINK_EXP_CODE")));
			responseStatus.setStatusMessage(env.getProperty("AUTH_SAVE_PWD_LINK_EXP_MESSAGE"));
		}
		LOGGER.info(CommonConstants.END);
		return responseStatus;
	}

	private String generateSaltToken() {
		SecureRandom random = new SecureRandom();
		byte bytes[] = new byte[20];
		random.nextBytes(bytes);
		Encoder encoder = Base64.getUrlEncoder().withoutPadding();
		String saltToken = encoder.encodeToString(bytes);
		return saltToken;
	}

	public ResponseStatus appUserPasswordCreation(AppUser appUser) {
		LOGGER.info(CommonConstants.BEGIN);
		ResponseStatus responseStatus = new ResponseStatus();
		try {
			String username = "";
			String token = "";
			AppUser tempAppUser = null;
			if (StringUtils.isNotBlank(appUser.getUserName()) && (StringUtils.isNotBlank(appUser.getResetToken()))) {
				username = appUser.getUserName();
				token = appUser.getResetToken();
				tempAppUser = appUserServiceImpl.getAppUserByUserName(username);
			}
			if (null != tempAppUser && (token.equals(tempAppUser.getResetToken()))) {
				if (StringUtils.isNotBlank(tempAppUser.getUserPassword())) {
					tempAppUser.setUserPassword("");
				}

				Date tokenTime = tempAppUser.getResetTokenExpiryTime();
				if (null != tokenTime) {
					LocalDateTime ldtt = tokenTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
					long epoch1 = Instant.now().toEpochMilli();
					LocalDateTime ldt1 = Instant.ofEpochMilli(epoch1).atZone(ZoneId.systemDefault()).toLocalDateTime();
					final Duration between = Duration.between(ldtt, ldt1);

					if (between.toMinutes() <= 20) {
						responseStatus.setStatusCode(Integer.parseInt(env.getProperty("SUCCESS_STATUS_CODE")));
						responseStatus.setStatusMessage(env.getProperty("SUCCESS_STATUS_MESSAGE"));
					} else {
						responseStatus.setStatusCode(Integer.parseInt(env.getProperty("AUTH_SAVE_PWD_LINK_EXP_CODE")));
						responseStatus.setStatusMessage(env.getProperty("AUTH_SAVE_PWD_LINK_EXP_MESSAGE"));
					}
				} else {
					responseStatus.setStatusCode(Integer.parseInt(env.getProperty("AUTH_UNKNOWN_ERR_CODE")));
					responseStatus.setStatusMessage(env.getProperty("AUTH_UNKNOWN_ERR_MESSAGE"));
				}
			} else {
				responseStatus.setStatusCode(Integer.parseInt(env.getProperty("AUTH_INVAILD_TOKEN_CODE")));
				responseStatus.setStatusMessage(env.getProperty("AUTH_INVAILD_TOKEN_MESSAGE"));
			}
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			responseStatus.setStatusCode(Integer.parseInt(env.getProperty("AUTH_UNKNOWN_ERR_CODE")));
			responseStatus.setStatusMessage(env.getProperty("AUTH_UNKNOWN_ERR_MESSAGE"));
		}
		LOGGER.info(CommonConstants.END);
		return responseStatus;
	}

	@GetMapping("/auth/logout")
	public ResponseStatus logoutPage(HttpServletRequest request, HttpServletResponse response,
			@RequestHeader(required = false) String jSessionId) {
		LOGGER.info(CommonConstants.BEGIN);
		ResponseStatus responseStatus = new ResponseStatus();
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String accessToken = jwtTokenFilter.getAccessToken(request);
			TokenBlacklist.revokeToken(accessToken);
			if (auth != null) {
				new SecurityContextLogoutHandler().logout(request, response, auth);
				
				responseStatus.setStatusCode(Integer.parseInt(env.getProperty("AUTH_LOGOUT_SUCCESS_CODE")));
				responseStatus.setStatusMessage(env.getProperty("AUTH_LOGOUT_SUCCESS_MESSAGE"));
			} else {
				responseStatus.setStatusCode(Integer.parseInt(env.getProperty("AUTH_LOGOUT_INVAILD_CODE")));
				responseStatus.setStatusMessage(env.getProperty("AUTH_LOGOUT_INVAILD_MESSAGE"));
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			responseStatus.setStatusCode(Integer.parseInt(env.getProperty("FAILURE_STATUS_CODE")));
			responseStatus.setStatusMessage(env.getProperty("FAILURE_STATUS_MESSAGE"));
		}
		LOGGER.info(CommonConstants.END);
		return responseStatus;
	}

	/*
	 * @ResponseBody
	 * 
	 * @GetMapping("/auth/captcha") public CapthaResponse
	 * captchaImage(HttpServletResponse response) throws Exception {
	 * LOGGER.info(CommonConstants.BEGIN); byte[] captchaImage = null;
	 * CapthaResponse capthaResponse = new CapthaResponse(); ResponseStatus
	 * responseStatus = new ResponseStatus(); try { String jSessionId =
	 * UUID.randomUUID().toString(); captchaImage = captchaUtil.execute(response);
	 * if (StringUtils.isNotBlank(jSessionId) &&
	 * StringUtils.isNotBlank(captchaUtil.getCaptcodeGenerated())) {
	 * appUserServiceImpl.saveCaptchaData(jSessionId,
	 * captchaUtil.getCaptcodeGenerated()); }
	 * capthaResponse.setjSessionId(jSessionId);
	 * capthaResponse.setCaptchaImage(captchaImage);
	 * responseStatus.setStatusCode(Integer.parseInt(env.getProperty(
	 * "SUCCESS_STATUS_CODE")));
	 * responseStatus.setStatusMessage(env.getProperty("SUCCESS_STATUS_MESSAGE"));
	 * capthaResponse.setResponseStatus(responseStatus); } catch (Exception e) {
	 * LOGGER.error(e.getMessage(), e);
	 * responseStatus.setStatusCode(Integer.parseInt(env.getProperty(
	 * "FAILURE_STATUS_CODE")));
	 * responseStatus.setStatusMessage(env.getProperty("FAILURE_STATUS_MESSAGE"));
	 * capthaResponse.setResponseStatus(responseStatus); }
	 * LOGGER.info(CommonConstants.END); return capthaResponse; }
	 */

}
