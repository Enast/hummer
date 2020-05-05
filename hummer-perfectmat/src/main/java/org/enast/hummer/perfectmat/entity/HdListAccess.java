package org.enast.hummer.perfectmat.entity;


import java.util.HashMap;

/**
 * 磁盘
 *
 * @author zhujinming6
 * @create 2018-09-14 20:30
 * @update 2018-09-14 20:30
 **/
public enum HdListAccess implements EnumMessage {


    hdNum("hdNum", "hdNum", BasicAccess.STATUS),
    hdTotalSpace("hdTotalSpace", "hdTotalSpace", BasicAccess.STATUS),
    hdFreeSpace("hdFreeSpace", "hdFreeSpace", BasicAccess.STATUS),
    hdUsedSpace("hdUsedSpace", "hdUsedSpace", BasicAccess.STATUS),
    hdStatus("hdStatus", "totalDiskStatus", BasicAccess.STATUS),
    hdUsage("hdUsage", "hdUsage", BasicAccess.STATUS),
    hdList("hdList", "diskInfo", BasicAccess.ARRAY),
    statusList("statusList", "statusList", BasicAccess.ARRAY),;

    private String originCode;

    private String hospCode;

    private String name;

    private BasicAccess basicAccess;

    private final static HashMap<String, HdListAccess> CODE_TO_ENUM;

    public static HdListAccess value4(String originCode) {
        HdListAccess access = CODE_TO_ENUM.get(originCode);
        return access;
    }


    static {
        CODE_TO_ENUM = new HashMap<>();
        for (HdListAccess access : HdListAccess.values()) {
            CODE_TO_ENUM.put(access.getOriginCode(), access);
        }
    }

    HdListAccess(String originCode, String hospCode, BasicAccess basicAccess) {
        this.originCode = originCode;
        this.hospCode = hospCode;
        this.basicAccess = basicAccess;
    }


    public String getName() {
        return name;
    }

    @Override
    public String getOriginCode() {
        return originCode;
    }

    @Override
    public String getHospCode() {
        return hospCode;
    }

    @Override
    public BasicAccess getBasicAccess() {
        return basicAccess;
    }


}
