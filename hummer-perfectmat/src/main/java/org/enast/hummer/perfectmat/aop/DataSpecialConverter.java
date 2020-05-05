package org.enast.hummer.perfectmat.aop;

import java.lang.annotation.*;

/**
 * 探针报文特殊处理注解
 *
 * @author zhujinming6
 * @create 2019-09-25 16:00
 * @update 2019-09-25 16:00
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSpecialConverter {
    // 使用这个解析器的的keys,多个key逗号隔开
    // 特殊报文(根据资源类型+能力类型/资源类型/能力类型)固定解析
    // 优先级:源类型+能力类型>资源类型>能力类型 @PafParseDataServiceImpl#specialByBusinessType()
    String keys();
}
