package org.enast.hummer.perfectmat.entity;

/**
 * @author zhujinming6
 * @create 2019-11-21 16:05
 * @update 2019-11-21 16:05
 **/
public class HdStatus {

    private Integer hdNum;
    private String hdIndexcode;
    private Long hdTotalSpace;
    private Long hdFreeSpace;
    private Integer hdStatus;
    private Long hdUsedSpace;
    private String hdLocation;

    public String getHdLocation() {
        return hdLocation;
    }

    public void setHdLocation(String hdLocation) {
        this.hdLocation = hdLocation;
    }

    public Integer getHdNum() {
        return hdNum;
    }

    public void setHdNum(Integer hdNum) {
        this.hdNum = hdNum;
    }

    public String getHdIndexcode() {
        return hdIndexcode;
    }

    public void setHdIndexcode(String hdIndexcode) {
        this.hdIndexcode = hdIndexcode;
    }

    public Long getHdTotalSpace() {
        return hdTotalSpace;
    }

    public void setHdTotalSpace(Long hdTotalSpace) {
        this.hdTotalSpace = hdTotalSpace;
    }

    public Long getHdFreeSpace() {
        return hdFreeSpace;
    }

    public void setHdFreeSpace(Long hdFreeSpace) {
        this.hdFreeSpace = hdFreeSpace;
    }

    public Integer getHdStatus() {
        return hdStatus;
    }

    public void setHdStatus(Integer hdStatus) {
        this.hdStatus = hdStatus;
    }

    public Long getHdUsedSpace() {
        return hdUsedSpace;
    }

    public void setHdUsedSpace(Long hdUsedSpace) {
        this.hdUsedSpace = hdUsedSpace;
    }
}
