package com.sean.ws.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

@Aspect// a combination of pointcut and advice
@Configuration
public class BeforeAspect {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//execution(any-return-type in-a-specific-package.any-class.any-method
	//(..) irrespect the argument
	//execution(* package.*.*(..))
	
	//@Before("execution(* com.sean.ws.ui.controller.UserController.hello(..))")
	@Before("execution(* com.sean.ws.ui.controller.*.*(..))")
	public void before(JoinPoint joinPoint) {
		//throw new Exception("rrrwwwwww");
		logger.info("Intercept Method Calls - {}", joinPoint);
		
	}

}
