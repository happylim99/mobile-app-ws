package com.sean.ws.aspect;

import org.aspectj.lang.annotation.Pointcut;

public class JoinPointConfig {
	
	@Pointcut("execution(* com.sean.ws.ui.controller.*.*(..))")
	public void controllerExecution() {}
	
	@Pointcut("execution(* com.sean.ws.shared.*.*(..))")
	public void sharedExecution() {}
	
	@Pointcut("com.sean.ws.aspect.JoinPointConfig.controllerExecution() || com.sean.ws.aspect.JoinPointConfig.sharedExecution()")
	public void combineExecution() {}
	
	@Pointcut("bean(*utils*)")
	public void beanContainUtils() {}
	
	@Pointcut("within(com.sean.ws.ui.controller..*)")
	public void withinPackage() {}
}
