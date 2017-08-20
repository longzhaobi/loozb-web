package com.loozb.core.aspect;

import com.loozb.core.bind.annotation.ValidError;
import com.loozb.core.util.AspectUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 切面编程，用于验证参数是否正确
 * 根据hibernate-validator.jar插件验证，需要在目标方法上的实体参数前注解：@Valid, 和增加Errors errors 参数
 * 这里会根据errors参数判断是否验证通过
 *
 * @Author： 龙召碧
 * @Date: Created in 2017-8-12 17:35
 */
@Aspect
@Component
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ValidAspect {

    /**
     * 切入点
     * 凡是在方法上注解有ValidError，就会触发这里的业务逻辑
     */
    @Pointcut("@annotation(com.loozb.core.bind.annotation.ValidError)")
    public void validAspect() {

    }

    @Around("validAspect()")
    public Object validAspectAround(ProceedingJoinPoint point) throws Throwable {
        Object result = null;
        /**
         * 访问目标方法前，在这里获取Errors参数并判断
         */

        Method method = AspectUtils.getMethod(point);
        if (method == null) {
            return point.proceed();
        }

        //获取目标方法的注解信息
        ValidError ve = method.getAnnotation(ValidError.class);
        Errors errors = (Errors) AspectUtils.parseArgsValue(ve.errors(), method, point.getArgs(), Errors.class);

        if (errors == null) {
            //此时说明没有加Errors参数
            throw new IllegalArgumentException("请添加参数Errors,或者在方法上取消ValidError注解");
        }

        if (errors.hasErrors()) {
            List<ObjectError> list = errors.getAllErrors();
            String error = "";
            for (int i = 0; i < list.size(); i++) {
                ObjectError oe = list.get(i);
                if(i == 0) {
                    error = oe.getDefaultMessage();
                } else {
                    error += "<br />" + oe.getDefaultMessage();
                }
            }
            throw new IllegalArgumentException(error);
        }

        result = point.proceed();

        /**
         * 访问目标方法返回后
         */

        return result;
    }
}
