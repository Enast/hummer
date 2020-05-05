package org.enast.hummer.dynamodel.model;

import org.enast.hummer.dynamodel.attribute.BaseAttribute;
import lombok.Data;

import java.util.List;

/**
 * 动态模型表和对应的属性信息
 *
 * @author zhujinming6
 * @create 2020-03-13 17:04
 * @update 2020-03-13 17:04
 **/
@Data
public class DynamicModelTable<A extends BaseAttribute> {

    private String tableName;
    private List<A> attributes;

}
