package com.sean.ws.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class AfterAspect {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@AfterReturning(
			value="com.sean.ws.aspect.JoinPointConfig.controllerExecution()",
			returning="result")
	public void afterReturning(JoinPoint joinPoint, Object result) {
		
		logger.info("{} - returned with value - {}", joinPoint, result);
		
	}

	//for exception
	//after is a more general way of doing aop
	@AfterThrowing(
			value="com.sean.ws.aspect.JoinPointConfig.controllerExecution()",
			throwing="e")
	public void afterThrowing(JoinPoint joinPoint, Exception e) {
		
		logger.info("{} - throw exception - {}", joinPoint, e.getMessage());
		
	}

}
