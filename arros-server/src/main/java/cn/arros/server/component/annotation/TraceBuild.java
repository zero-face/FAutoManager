package cn.arros.server.component.annotation;

import java.lang.annotation.*;

/**
 * 标记该次构建需要被追踪
 * @Author Verge
 * @Date 2021/11/27 18:56
 * @Version 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TraceBuild {
}
