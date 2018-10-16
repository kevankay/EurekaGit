package com.capgemini.EurekaClient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;

@RefreshScope
@RestController
public class EurekaClientController {

	@Autowired
	private EurekaClient eurekaClient;

	private RestTemplate restTemplate = new RestTemplate();

	@GetMapping("/message")
	public String getGreetingMessage() {
		Application application = eurekaClient.getApplication("helloworld");
		InstanceInfo instanceInfo = application.getInstances().get(0);
		String url = "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/" + "message";
		return restTemplate.getForObject(url, String.class);
	}

}