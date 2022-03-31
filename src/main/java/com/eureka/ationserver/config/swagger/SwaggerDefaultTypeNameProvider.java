package com.eureka.ationserver.config.swagger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class SwaggerDefaultTypeNameProvider {

  @Around("execution(* springfox.documentation.schema.DefaultTypeNameProvider.nameFor(..))")
  public Object around(ProceedingJoinPoint point) {
    Class<?> type = (Class<?>) point.getArgs()[0];

    String name = type.getName();
    name = name.substring(name.lastIndexOf('.') + 1);

    return name;
  }

}
