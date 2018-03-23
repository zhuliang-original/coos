package top.coos.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import top.coos.enums.DatabasePlace;

/**
 * 表配置
 * 
 * @author 朱亮
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Database {

	String name();

	DatabasePlace place() default DatabasePlace.CORE;

	String programid() default "";
}
