package com.example.practiceAOP.aop;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.example.practiceAOP.dto.UserDto;

@Aspect
@Component
public class DecodeAop {

	@Pointcut("execution(* com.example.practiceAOP.controller..*.*(..))")
	private void cut() {
	}

	@Pointcut("@annotation(com.example.practiceAOP.annotation.Decode)")
	private void enableDecode() {
	}

	// 암호화된 데이터 들어오면 복호화!
	@Before("cut() && enableDecode()")
	public void before(JoinPoint joinPoint) throws UnsupportedEncodingException {

		// 메서드로 들어오는 매개변수들
		Object[] args = joinPoint.getArgs();

		for (Object arg : args) {
			if (arg instanceof UserDto) {
				// 클래스 캐스팅
				UserDto user = UserDto.class.cast(arg);
				String base64Email = user.getEmail();
				// 복호화
				String email = new String(Base64.getDecoder().decode(base64Email));
				user.setEmail(email);
			}
		}
	}

	// 리턴할때는 암호화하여 리턴
	@AfterReturning(value = "cut() && enableDecode()", returning = "returnObj")
	public void afterReturn(JoinPoint joinPoint, Object returnObj) {

		if (returnObj instanceof UserDto) {
			// 클래스 캐스팅
			UserDto user = UserDto.class.cast(returnObj);
			String email = user.getEmail();
			// 암호화
			String base64Email = Base64.getEncoder().encodeToString(email.getBytes());
			user.setEmail(base64Email);
		}
	}
}
