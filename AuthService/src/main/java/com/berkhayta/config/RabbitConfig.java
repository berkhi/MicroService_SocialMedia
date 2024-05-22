package com.berkhayta.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
	String exchange="exchange.direct";
	String registerQueueName="queue.socialmedia.auth";
	String registerBindingKey="update.user.key";

	String activeStatusQueueName="activestatusqueue.socialmedia.auth";
	String activeStatusBindingKey="activestatus.user.key";

	@Bean
	DirectExchange exchangeDirect(){
		return new DirectExchange(exchange);
	}

	@Bean
	Queue registerQueue(){
		return new Queue(registerQueueName);
	}

	@Bean
	Binding bindingRegister(Queue registerQueue, DirectExchange exchangeDirect){
		return BindingBuilder.bind(registerQueue).to(exchangeDirect).with(registerBindingKey);
	}

	@Bean
	Queue activateStatusQueue(){
		return new Queue(activeStatusQueueName);
	}

	@Bean
	Binding bindingActivateStatus(Queue activateStatusQueue, DirectExchange exchangeDirect){
		return BindingBuilder.bind(activateStatusQueue).to(exchangeDirect).with(activeStatusBindingKey);
	}

	@Bean
	MessageConverter messageConverter(){
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(messageConverter());
		return rabbitTemplate;
	}
}