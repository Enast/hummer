/**
 * Copyright 2019 bejson.com
 */
package org.enast.hummer.perfectmat.entity;

import java.util.Date;

/**
 * Auto-generated: 2019-11-20 10:55:57
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class NetStatusList {

    private Integer status;
    private Date infoTime;
    private Integer ping;

    public Integer getPing() {
        return ping;
    }

    public void setPing(Integer ping) {
        this.ping = ping;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getInfoTime() {
        return infoTime;
    }

    public void setInfoTime(Date infoTime) {
        this.infoTime = infoTime;
    }
}