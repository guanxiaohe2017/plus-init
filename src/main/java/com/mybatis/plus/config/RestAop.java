package com.mybatis.plus.config;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
public class RestAop {

    /**
     * // 控制是否开启日志
     */
    public static Boolean onOff = false;

    private static Log logger = LogFactory.getLog(RestAop.class);

    @Pointcut("execution(public * com.mybatis.*.web..*.*(..))")
    public void pointCutRestDef() {
    }

    @Around("pointCutRestDef()")
    public Object processRst(ProceedingJoinPoint point) throws Throwable {
        Object returnValue = null;
        final List<Object> params = new ArrayList<>();
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra.getRequest();
        Object[] args = point.getArgs();
        for (int i = 0; i < args.length; i++) {
            Object object = args[i];
            if (object instanceof HttpServletResponse) {
                continue;
            }
            if (object instanceof HttpServletRequest) {
                continue;
            }
            params.add(object);
        }
        logger.info(
                "rest method: " + point.getSignature().getDeclaringTypeName() + "." + point.getSignature().getName());
        String cloneParams = JSONObject.toJSONString(params);
        logger.info("rest param: " + cloneParams);
        Long startTime = System.currentTimeMillis();
        try {
            returnValue = point.proceed(point.getArgs());
        } catch (Exception e) {
            // 请求异常处理
            throw e;
        }
        Long endTime = System.currentTimeMillis();
        logger.info("rest  " + request.getRequestURI() + "---used time---" + (endTime - startTime));
        Boolean boolean1 = true;
        if (returnValue != null && !returnValue.equals(boolean1)) {
            System.out.println(JSONObject.toJSONString(returnValue));

        }
        return returnValue;
    }

}
