package top.coos.servlet.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import top.coos.servlet.enums.RequestMethod;

/**
 * @Retention 指定注释的生存时期 CLASS:注释记录在类文件中，但在运行时 VM 不需要保留注释。 RUNTIME:注释记录在类文件中，在运行时
 *            VM 将保留注释，因此可以使用反射机制读取注释内容。 SOURCE:编译器要丢弃的注释。
 */
@Retention(RetentionPolicy.RUNTIME)
/** 
 * @Target  
 * 指示注释类型所适用的程序元素的种类，如果注释类型声明中不存在 Target 元注释， 
 * 则声明的类型可以用在任一程序元素上。 
 * ElementType.ANNOTATION_TYPE：注释类型声明 
 * ElementType.CONSTRUCTOR：构造方法声明 
 * ElementType.FILED：字段声明 
 * ElementType.LOCAL_VARIABLE：局部变量声明 
 * ElementType.METHOD：方法声明 
 * ElementType.PACKAGE：包声明 
 * ElementType.PARAMETER：参数声明 
 * ElementType.TYPE：类、借口或枚举声明 
 */
@Target(ElementType.METHOD)
/**
 * 数据配置项
 * @author 朱亮
 *
 */
public @interface RequestMapping {

    /**
     * 名称
     * 
     * @return
     */
    String[] value();

    RequestMethod[] method() default {};
}
