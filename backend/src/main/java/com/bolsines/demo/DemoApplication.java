package com.bolsines.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bolsines.demo.Service.ServicioNavTrackQX7A;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		ServicioNavTrackQX7A.retrieveTrackingData("sk_xtr_9F3a7C21bE6d4A10f2c8e5b7d913", 101, "CM-ROS-02");
	}

}
