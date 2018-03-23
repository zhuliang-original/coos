package top.coos.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import top.coos.enums.InputType;
import top.coos.enums.Operator;



/**
 * 字段配置
 * 
 * @author 朱亮
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {

    /**
     * 列名
     * 
     * @return
     */
    String name();

    /**
     * 注释
     * 
     * @return
     */
    String comment();

    /**
     * 是用于父编号
     * 
     * @return
     */
    boolean forParentid() default false;

    /**
     * 是唯一
     * 
     * @return
     */
    boolean unique() default false;

    /**
     * 输入类型
     * 
     * @return
     */
    InputType inputType() default InputType.TEXT;

    /**
     * 标签信息
     * 
     * @return
     */
    String label() default "";

    /**
     * 默认值
     * 
     * @return
     */
    String defaultValue() default "";

    /**
     * 用作查询
     * 
     * @return
     */
    boolean forSearch() default true;

    /**
     * 查询比对符号
     * 
     * @return
     */
    Operator operator() default Operator.EQ;

    /**
     * 最小长度
     * 
     * @return
     */
    int minlength() default 0;

    /**
     * 最大长度
     * 
     * @return
     */
    int maxlength() default 0;

    /**
     * 可空
     * 
     * @return
     */
    boolean nullable() default true;

    /**
     * 字段长度 与符号无关
     * 
     * @return
     */
    int precision() default 0;

    /**
     * 小数位数
     * 
     * @return
     */
    int scale() default 0;

}
