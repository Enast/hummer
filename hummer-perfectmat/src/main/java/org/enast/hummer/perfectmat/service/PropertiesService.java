package org.enast.hummer.perfectmat.service;

import org.enast.hummer.perfectmat.entity.ResourceQuotas;
import org.enast.hummer.perfectmat.entity.ResourceCache;

/**
 * @author zhujinming6
 * @create 2018-07-06 12:55
 * @update 2018-07-06 12:55
 **/
public interface PropertiesService {
    /**
     * 发送状态属性数据
     */
    void sendStatus(ResourceQuotas quotas, ResourceCache simpleVO);
}
