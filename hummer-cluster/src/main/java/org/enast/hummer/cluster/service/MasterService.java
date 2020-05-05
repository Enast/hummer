package org.enast.hummer.cluster.service;

import org.enast.hummer.cluster.vo.ElectResponse;

/**
 * @author zhujinming6
 * @create 2019-10-08 10:33
 * @update 2019-10-08 10:33
 **/
public interface MasterService {

    /**
     * 主节点相关业务启动(选举成功)和非主节点相关业务停止(选举失败)
     *
     * @param electResponse 选举结果
     */
    void businessSwitch(ElectResponse electResponse, String nodeId);

    /**
     * 判断当前节点是否是主节点
     *
     * @return
     */
    Boolean master();
}
