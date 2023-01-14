package it.developer.temperatureservice;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class AOPData {

    @Pointcut("execution(public void it.developer.temperatureservice.service.TemperatureService.postData(it.developer.temperatureservice.dto.DataRequest))")
    public void method() {}

    @After("method()")
    public void after() {
        log.info("Do something after method");
    }

    @Before("method()")
    public void before(JoinPoint joinPoint) {
        log.info("Do something before method {}", joinPoint.getSignature().getName());
    }
}
