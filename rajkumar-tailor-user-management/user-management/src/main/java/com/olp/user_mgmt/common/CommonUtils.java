package com.olp.user_mgmt.common;

import java.util.UUID;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.olp.user_mgmt.entity.User;
import com.olp.user_mgmt.service.NotificationServiceImpl;


@Component
public class CommonUtils {
	

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	Environment env;
	
	@Autowired
	NotificationServiceImpl notificationServiceImpl;

	private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtils.class);

	public static User getWebUser() {
		LOGGER.info(CommonConstants.BEGIN);
		User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		User userDetails=new User();
//		userDetails.setUserid(1);
		LOGGER.info(CommonConstants.END);
		return userDetails;
	}

	public static boolean isNull(Object object) {
		boolean nullFlag = false;
		if (object == null) {
			nullFlag = true;
		} else if (object instanceof String && object.equals("")) {
			nullFlag = true;
		}
		return nullFlag;
	}
	public static void logIncomingJsonData(Object incomingData) {
		try {
			if (null != incomingData) {
				ObjectWriter ow = new ObjectMapper().writer();
				String json = ow.writeValueAsString(incomingData);
				boolean contains = json.contains("data:image");
				if (!contains) {
					LOGGER.info("::HEADERS JSON::" + json);
				}else {
					LOGGER.info("::HEADERS JSON::" + "::json contains base64 image data::");
				}
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	public static boolean isVaildMobileNumber(String number) {
		LOGGER.info(CommonConstants.BEGIN);
		boolean result = false;
		try {
			String regex = "\\d{10}";
			result = Pattern.compile(regex).matcher(number).matches();
			LOGGER.info("::Mobile Number is::" + number + " ::Result::" + result);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		LOGGER.info(CommonConstants.END);
		return result;
	}

	public static String generateUniqueIdentifierNumber() {
		UUID uniqueId = UUID.randomUUID();
		return uniqueId.toString();
	}
	

//	public void sendNotification(String endPointUrl, NotificationRequest notificationRequest) {
//		//String url="http://NOTIFICATION-MANAGEMENT/notification-management/notification/"+endPointUrl;
//		ResponseStatus responseStatus = new ResponseStatus();
//		try {
////			AppUser appUser=notificationRequest.getAppuser();
//			
////			HttpHeaders headers = new HttpHeaders();
////			headers.setContentType(MediaType.APPLICATION_JSON);
//			
//			
////			Map<String, String> headers=new HashMap();
////			headers.put("display_name", appUser.getDisplayName());
////			headers.put("email", appUser.getEmailId());
////			headers.put("mobile", appUser.getMobileNo());
////			headers.put("reset_token", appUser.getResetToken());
////			headers.put("user_name", appUser.getUserName());
////			headers.put("context_path", notificationRequest.getContextPath());
////			headers.put("user_id", appUser.getUserId().toString());
////			
////			if(endPointUrl.equalsIgnoreCase("loginSuccess")) {
////				notificationServiceImpl.sendLoginSuccessNotification(headers);
////			}else if(endPointUrl.equalsIgnoreCase("forgotPassword")) {
////				notificationServiceImpl.sendLoginSuccessNotification(headers);
////			}else if(endPointUrl.equalsIgnoreCase("resetPassword")) {
////				notificationServiceImpl.sendLoginSuccessNotification(headers);
////			}else if(endPointUrl.equalsIgnoreCase("loginSuccess")) {
////				notificationServiceImpl.sendLoginSuccessNotification(headers);
////			}
//			
//			
//			
////			HttpEntity<String> entity = new HttpEntity<>(headers);
////
////			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
////			System.out.println("response " + response.getBody());
//		} catch (Exception e) {
//			LOGGER.error(e.getMessage(), e);
//		}
//	}
}
