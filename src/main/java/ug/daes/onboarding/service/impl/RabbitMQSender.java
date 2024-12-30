package ug.daes.onboarding.service.impl;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ug.daes.onboarding.dto.LogModelDTO;

@Service
public class RabbitMQSender {
	
	@Autowired
	private AmqpTemplate amqpTemplate;
	
	@Value("${com.dt.rabbitmq.exchange}")
	private String exchange = "/";
	
	@Value("${com.dt.rabbitmq.routingkey}")
	private String routingkey = "ob-log";
	
	@Value("${com.dt.rabbitmq.routingkey.central}")
	private String centralRoutingkey = "central-log";
	
	public void send(LogModelDTO logmodel) {
		System.out.println("routingkey " + routingkey);
//		System.out.println("exchange = "+exchange+"\n routingkey = "+routingkey);
		System.out.println("centralRoutingkey => "+centralRoutingkey);
		amqpTemplate.convertAndSend(exchange, routingkey, logmodel);
		//amqpTemplate.convertAndSend(exchange, centralRoutingkey, logmodel);
		//System.out.println("Send msg = " + logmodel);
	}
	
	public void sendString(String logmodel) {
		
//		System.out.println("exchange = "+exchange+"\n routingkey = "+routingkey);
//		System.out.println("centralRoutingkey => "+centralRoutingkey);
		amqpTemplate.convertAndSend(exchange, routingkey, logmodel);
//		amqpTemplate.convertAndSend(exchange, centralRoutingkey, logmodel);
		//System.out.println("Send msg = " + logmodel);
	}
}