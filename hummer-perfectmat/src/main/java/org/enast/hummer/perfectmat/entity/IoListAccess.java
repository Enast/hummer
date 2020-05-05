package org.enast.hummer.perfectmat.entity;


import java.util.HashMap;

/**
 * 磁盘
 *
 * @author zhujinming6
 * @create 2018-09-14 20:30
 * @update 2018-09-14 20:30
 **/
public enum IoListAccess implements EnumMessage {


    ioList("ioList", "ioList", BasicAccess.STATUS),;

    private String originCode;

    private String hospCode;

    private String name;

    private BasicAccess basicAccess;

    private final static HashMap<String, IoListAccess> CODE_TO_ENUM;

    public static IoListAccess value4(String originCode) {
        IoListAccess access = CODE_TO_ENUM.get(originCode);
        return access;
    }


    static {
        CODE_TO_ENUM = new HashMap<>();
        for (IoListAccess access : IoListAccess.values()) {
            CODE_TO_ENUM.put(access.getOriginCode(), access);
        }
    }

    IoListAccess(String originCode, String hospCode, BasicAccess basicAccess) {
        this.originCode = originCode;
        this.hospCode = hospCode;
        this.basicAccess = basicAccess;
    }

    IoListAccess(String originCode, String hospCode, String name, BasicAccess basicAccess) {
        this.originCode = originCode;
        this.hospCode = hospCode;
        this.name = name;
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
