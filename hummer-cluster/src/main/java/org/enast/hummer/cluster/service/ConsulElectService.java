package org.enast.hummer.cluster.service;

import org.enast.hummer.cluster.vo.ElectResponse;

/**
 * @author zhujinming6
 * @create 2019-10-08 21:30
 * @update 2019-10-08 21:30
 **/
public interface ConsulElectService {

    /**
     * 选举初始化
     */
    void work(String electName);

    /**
     * 选举状态监听,和相应变更
     *
     * @param electResponse
     */
    void watch(ElectResponse electResponse);

    /**
     * 选举逻辑
     *
     * @return
     */
    ElectResponse elect();
}
