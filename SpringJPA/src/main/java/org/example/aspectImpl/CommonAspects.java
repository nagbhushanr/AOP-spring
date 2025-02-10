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

		// Retrieve method dynamically (supports overloaded methods)
		Method[] methods = targetClass.getMethods();
		Method targetMethod = null;
		for (Method method : methods) {
			if (method.getName().equals(methodName) && method.getParameterCount() == 1) {
				targetMethod = method;
				break;
			}
		}

		if (targetMethod == null) {
			throw new NoSuchMethodException("No matching method found for " + methodName);
		}

		int maxRetries = 3;  // Define retries inside method scope to reset for each call

		while (maxRetries > 0) {
			try {
				return joinPoint.proceed();
			} catch (Exception exception) {
				if (exception instanceof NotFoundException) {
					System.out.println("inside AOP retry logic, remaining retries: " + maxRetries);

					try {
						Object result = targetMethod.invoke(joinPoint.getTarget(), argument.toString());
						if (result != null) {
							return result;
						}
					} catch (Exception e) {
						System.out.println("Retry failed: " + e.getMessage());
					}

					maxRetries--;

					if (maxRetries == 0) {
						throw exception;  // Re-throw original exception if all retries fail
					}
				} else {
					throw exception;  // If it's not a NotFoundException, rethrow immediately
				}
			}
		}
		return null;
	}

}
