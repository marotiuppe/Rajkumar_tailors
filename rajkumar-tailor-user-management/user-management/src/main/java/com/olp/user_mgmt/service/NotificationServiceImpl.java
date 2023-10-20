package com.olp.user_mgmt.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.olp.user_mgmt.to.NotificationRequest;

@FeignClient(name = "NOTIFICATION-MANAGEMENT")
public interface NotificationServiceImpl {

	@PostMapping("/notification-management/notification/signUpSuccsess")
	void sendSignUpSuccsessNotification(@RequestBody NotificationRequest notificationRequest);

	@PostMapping("/notification-management/notification/loginSuccess")
	void sendLoginSuccessNotification(@RequestBody NotificationRequest notificationRequest);

	@PostMapping("/notification-management/notification/forgotPasswordLink")
	void sendForgotPasswordLink(@RequestBody NotificationRequest notificationRequest);

	@PostMapping("/notification-management/notification/resetPasswordSuccess")
	void sendResetPasswordSuccessNotification(@RequestBody NotificationRequest notificationRequest);

}
