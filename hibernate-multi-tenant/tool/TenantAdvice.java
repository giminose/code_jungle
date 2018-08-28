package tool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author by gimme on 18/3/21.
 */
@Aspect
@Order(1)
public class TenantAdvice {

    private static final Logger logger = LogManager.getLogger(TenantAdvice.class);

    @Pointcut("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void openSession() {
    }

    @Before("openSession()")
    public void injectTenant(JoinPoint jp) {
        MethodSignature signature = (MethodSignature) jp.getSignature();
        Method method = signature.getMethod();
        Annotation[][] paramAnnots = method.getParameterAnnotations();
        Object[] args = jp.getArgs();

        int i = 0;
        for (Annotation[] annotations : paramAnnots) {
            Object arg = args[i++];
            for (Annotation annotation : annotations) {
                if (annotation instanceof Tenant) {
                    logger.info("Tenant: " + arg.toString());
                    String tenantIdentifier = arg.toString();
                    SchemaCurrentTenantIdentifierResolver.setTenantIdentifier(tenantIdentifier);
                    break;
                }
            }
        }
    }
}
