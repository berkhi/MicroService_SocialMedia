package com.berkhayta.rabbitmq.consumer;

import com.berkhayta.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivateStatusConsumer {
	private final UserService userService;
	
	@RabbitListener(queues = "activestatusqueue.socialmedia.auth")
	public void activateStatus(Long authId){
		userService.activateUserProfile(authId);
	}
}