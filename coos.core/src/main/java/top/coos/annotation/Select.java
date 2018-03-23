package top.coos.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字段下拉框配置
 * 
 * @author 朱亮
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Select {

    /**
     * 实体名称
     * 
     * @return
     */
    Class<?> entityClass() default Class.class;

    /**
     * 数据字典
     * 
     * @return
     */
    String dictionaryName() default "";

    /**
     * 名称字段
     * 
     * @return
     */
    String textColumnName() default "";

    /**
     * 值字段
     * 
     * @return
     */
    String valueColumnName() default "";

    /**
     * 级联下拉使用——关联字段名称
     * 
     * @return
     */
    String relationColumnName() default "";

    String imageColumnName() default "";

    String parentColumnName() default "";

    /**
     * 下拉框数据配置条件 如查询sql语句where条件
     * 
     * @return
     */
    String where() default "";

}
