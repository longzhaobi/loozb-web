package com.loozb.core.bind.annotation;

import java.lang.annotation.*;

/**
 * 修改注解，用来存储每个数据的修改记录
 * @Author： 龙召碧
 * @Date: Created in 2017-8-12 17:37
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Modify {

}
