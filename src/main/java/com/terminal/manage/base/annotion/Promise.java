package com.terminal.manage.base.annotion;

import java.lang.annotation.*;

/**权限验证
 * @author Jyt
 * @date 2021/9/30
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented()
public @interface Promise {

    String value();

}
