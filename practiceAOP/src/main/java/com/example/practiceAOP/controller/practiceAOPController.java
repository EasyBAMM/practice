package com.example.practiceAOP.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.practiceAOP.annotation.Decode;
import com.example.practiceAOP.annotation.Timer;
import com.example.practiceAOP.dto.UserDto;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class practiceAOPController {

	@GetMapping("/get/{id}")
	public String get(@PathVariable String id, @RequestParam String name) {
		// log.info("Get Method가 실행됨!");
		// log.info("Get Method {id}: " + id);
		// log.info("Get Method {name}: " + name);

		// 서비스 로직

		return id + " " + name;
	}

	@PostMapping("/post")
	public UserDto post(@RequestBody UserDto user) {
		// log.info("Post Method가 실행됨!");
		// log.info("Post Method {user}: " + user.toString());

		// 서비스 로직

		return user;
	}

	@Timer
	@DeleteMapping("/delete")
	public void delete() {

		// 삭제 서비스 로직: 소요시간 3초
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Decode
	@PostMapping("/postEncrypt")
	public UserDto postEncrypt(@RequestBody UserDto user) {
		// log.info("Post Method가 실행됨!");
		// log.info("Post Method {user}: " + user.toString());

		// 서비스 로직

		return user;
	}
}
