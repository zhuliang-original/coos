package top.coos.servlet.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解 支持模板注解
 * 
 * @Description: TODO(作用:注解 支持模板注解)
 * @author 朱亮
 * @date 2015-3-28 下午1:18:44
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RequestController {
    public String name() default "";

    public String[] urlPatterns();

}
