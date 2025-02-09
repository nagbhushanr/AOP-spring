package org.example.aspectImpl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.entity.User;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CommonAspects {
	int maxRetries = 3;
	@Around(value = "@annotation(org.example.customAnnotation.RetryDBOperation) && args(argument)")
	public Object retryDBOperation(ProceedingJoinPoint joinPoint, Object argument) throws Throwable {
		String methodName = joinPoint.getSignature().getName();
		Class<?> targetClass = joinPoint.getTarget().getClass();
		Method method = targetClass.getMethod(methodName,String.class);
		try {
			return joinPoint.proceed();
		}catch (Exception exception){
			if(exception instanceof NotFoundException) {
				while (maxRetries > 0){
					Object result = method.invoke(joinPoint.getTarget(),argument.toString());
					if(!Objects.isNull(result)){
						return result;
					}
					maxRetries = maxRetries - 1;
				}
			}
		}
		return null;
	}

}
