package com.weasel.util.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weasel.util.StringUtil;

@Aspect
public class LoggerAspect {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Pointcut("execution(* kr.co.hmarket..*Controller.*(..))")
	public void requestAll() {
	}

	@Before("requestAll()")
	public void beforeAll(JoinPoint joinPoint) throws Exception {
		Object[] args = joinPoint.getArgs();

		StringBuffer msg = new StringBuffer();
		msg.append("******");
		msg.append("[hMarket Method Before   ]" + joinPoint.getSignature().toString());

		for (int i = 0; i < args.length; i++) {
			if (args[i] != null) {
				String arg = args[i].toString();
				arg = StringUtil.replaceAll(arg, "%", "&#37;");
				msg.append("[hMarket paramMap ]" + arg);
			}
		}
		msg.append("******");
		logger.debug(msg.toString());

	}

	@After("requestAll()")
	public void afterAll(JoinPoint joinPoint) throws Exception {
		Object[] args = joinPoint.getArgs();

		StringBuffer msg = new StringBuffer();
		msg.append("******");
		msg.append("[hMarket Method After  ]" + joinPoint.getSignature().toString());

		for (int i = 0; i < args.length; i++) {
			if (args[i] != null) {
				String arg = args[i].toString();
				arg = StringUtil.replaceAll(arg, "%", "&#37;");
				msg.append("[hMarket paramMap ]" + arg);
			}
		}
		msg.append("******");
		logger.debug(msg.toString());

	}

	@AfterThrowing(pointcut = "requestAll()", throwing = "e")
	public void afterThrowingAll(JoinPoint joinPoint, Throwable e) throws Exception {
		Object[] args = joinPoint.getArgs();

		StringBuffer msg = new StringBuffer();
		msg.append("******");
		msg.append("[hMarket Exception]");
		msg.append("[hMarket Method   ]" + joinPoint.getSignature().toShortString());
		for (int i = 0; i < args.length; i++) {
			if (args[i] != null) {
				String arg = args[i].toString();
				arg = StringUtil.replaceAll(arg, "%", "&#37;");
				msg.append("[hMarket paramMap ]" + arg);
			}
		}
		msg.append("******");
		logger.error(msg.toString(), e);
	}
}
