package com.sean.ws.aspect;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class AroundAspect {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	/*
	@Around("com.sean.ws.aspect.JoinPointConfig.controllerExecution()")
	public Object contrallerAround(ProceedingJoinPoint joinPoint) throws Throwable {

		long startTime = System.currentTimeMillis();
		Object obj = joinPoint.proceed();
		long timeTaken = System.currentTimeMillis() - startTime;
		logger.info("Time taken by {} is {}", joinPoint, timeTaken);
		return obj;
	}
	*/
	@Around("com.sean.ws.aspect.JoinPointConfig.combineExecution()")
	public Object sharedAround(ProceedingJoinPoint joinPoint) throws Throwable {

		long startTime = System.currentTimeMillis();
		//Thread.sleep(3000);
		Object obj = joinPoint.proceed();
		long timeTaken = System.currentTimeMillis() - startTime;
		//if(timeTaken > 3000)
			logger.info("Time taken by {} is {}", joinPoint, timeTaken);
		
		return obj;
	}

}
