package org.enast.hummer.cluster.core.vo;

/**
 * 选举结果
 *
 * @author zhujinming6
 * @create 2019-10-08 21:32
 * @update 2019-10-08 21:32
 **/
public class ElectResponse {

    /**
     * 选举结果
     */
    private Boolean electResult = false;
    /**
     * consul KV 存储修改索引
     */
    private long modifyIndex = 0;
    /**
     * leaderId
     */
    private String leaderId;
    /**
     * consul 客户端session Id
     */
    private String sessionId;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Boolean getElectResult() {
        return electResult;
    }

    public void setElectResult(Boolean electResult) {
        this.electResult = electResult;
    }

    public long getModifyIndex() {
        return modifyIndex;
    }

    public void setModifyIndex(long modifyIndex) {
        this.modifyIndex = modifyIndex;
    }

    public String getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(String leaderId) {
        this.leaderId = leaderId;
    }
}
