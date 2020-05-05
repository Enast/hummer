package org.enast.hummer.dynamodel.model;

import org.enast.hummer.dynamodel.attribute.BaseAttribute;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author zhujinming6
 * @create 2020-03-13 15:51
 * @update 2020-03-13 15:51
 **/
@Data
@NoArgsConstructor
public class OperationMaintModelCache<A extends BaseAttribute> {

    private String resourceType;
    private String version;
    private Map<String, A> baseInfo;
    private Map<String, A> config;

    public OperationMaintModelCache(String resourceType) {
        this.resourceType = resourceType;
    }
}
