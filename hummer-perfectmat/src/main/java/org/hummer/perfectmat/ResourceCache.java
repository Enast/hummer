package org.hummer.perfectmat;

import lombok.Data;

import java.util.Map;

@Data
public class ResourceCache {

    /**
     * 资源id
     */
    private String resId;
    /**
     * 资源名称
     */
    private String name;
    /**
     * 国标编码
     */
    private String indexCode;

    private String subTypeCode;

}
