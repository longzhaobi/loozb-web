package com.loozb.core.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;

/**
 * @Author： 龙召碧
 * @Date: Created in 2017-8-12 17:50
 */
public class AspectUtils {
    /**
     * 获取被拦截方法对象
     *
     * MethodSignature.getMethod() 获取的是顶层接口或者父类的方法对象 而缓存的注解在实现类的方法上
     * 所以应该使用反射获取当前对象的方法对象
     */
    @SuppressWarnings("rawtypes")
    public static Method getMethod(ProceedingJoinPoint pjp){
        try {
            Signature sig = pjp.getSignature();
            MethodSignature msig = null;
            if (!(sig instanceof MethodSignature)) {
                throw new IllegalArgumentException("该注解只能用于方法");
            }
            msig = (MethodSignature) sig;
            Object target = pjp.getTarget();
            return target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取参数的值
     *
     * @return
     */
    public static Object parseArgsValue(String fieldKey, Method method, Object[] args, Class clazz) {
        // 获取被拦截方法参数名列表(使用Spring支持类库)
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paraNameArr = u.getParameterNames(method);

        // 使用SPEL进行key的解析
        ExpressionParser parser = new SpelExpressionParser();
        // SPEL上下文
        StandardEvaluationContext context = new StandardEvaluationContext();
        // 把方法参数放入SPEL上下文中
        for (int i = 0; i < paraNameArr.length; i++) {
            context.setVariable(paraNameArr[i], args[i]);
        }
        return parser.parseExpression(fieldKey).getValue(context, clazz);
    }
}
