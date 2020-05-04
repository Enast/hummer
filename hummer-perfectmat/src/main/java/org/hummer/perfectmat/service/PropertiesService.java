package org.hummer.perfectmat.service;

import org.hummer.perfectmat.ResourceCache;
import org.hummer.perfectmat.ResourceQuotas;

import java.util.Date;
import java.util.Map;

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
