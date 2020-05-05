package org.enast.hummer.perfectmat.entity;

import java.util.HashMap;

/**
 * 服务器
 *
 * @author zhujinming6
 * @create 2018-09-14 20:30
 * @update 2018-09-14 20:30
 **/
public enum ServerAccess implements EnumMessage {

    devOnlineStatus("devOnlineStatus","", BasicAccess.STATUS),

    cn("cn", "cn", BasicAccess.STATIC),
    systemDetail("systemDetail", "systemDetail", BasicAccess.STATIC),

    /**
     * 开机后持续时间
     */
    runningTime("runningTime", "runningTime", BasicAccess.STATUS),

    cpuList("cpuList", "cpuList", BasicAccess.ARRAY),
    cpuType("cpuType", "cpuType", BasicAccess.STATIC),
    cpuUsage("cpuUsage", "cpuUsage", BasicAccess.STATIC),

    /**
     * 虚拟内存
     */
    virtualMem("virtualMem", "virtualMem", BasicAccess.ARRAY),

    /**
     * 物理内存
     */
    physicalMem("physicalMem", "physicalMem", BasicAccess.ARRAY),
    processes("processes", "processes", BasicAccess.ARRAY),
    pid("pid", "pid", BasicAccess.STATIC),
    memUsed("memUsed", "memUsed", BasicAccess.STATUS),


    hdList("hdList", "hdList", BasicAccess.ARRAY),
    diskInfo("diskInfo", "diskInfo", BasicAccess.ARRAY),
    hd("hd", "hd", "磁盘", BasicAccess.STATUS),
    num("num", "num", BasicAccess.STATUS),
    hdUsage("hdUsage", "hdUsage", BasicAccess.STATUS),

    physicalMemTotal("memTotalSpace", "physicalMemTotal", BasicAccess.STATUS),
    physicalMemUsed("memUsedSpace", "physicalMemUsed", BasicAccess.STATUS),

    virtualMemTotal("memTotalSpace", "virtualMemTotal", BasicAccess.STATUS),
    virtualMemUsed("memUsedSpace", "virtualMemUsed", BasicAccess.STATUS),


    physicalMemUsage("Usage", "physicalMemUsage", BasicAccess.STATUS),

    virtualMemUsage("Usage", "virtualMemUsage", BasicAccess.STATUS),

    ports("portList", "ports", BasicAccess.ARRAY),;

    private String originCode;

    private String hospCode;

    private String name;

    private BasicAccess basicAccess;

    private final static HashMap<String, ServerAccess> CODE_TO_ENUM;

    public static ServerAccess value4(String originCode) {
        ServerAccess access = CODE_TO_ENUM.get(originCode);
        return access;
    }


    static {
        CODE_TO_ENUM = new HashMap<>();
        for (ServerAccess access : ServerAccess.values()) {
            CODE_TO_ENUM.put(access.getOriginCode(), access);
        }
    }

    ServerAccess(String originCode, String hospCode, BasicAccess basicAccess) {
        this.originCode = originCode;
        this.hospCode = hospCode;
        this.basicAccess = basicAccess;
    }

    ServerAccess(String originCode, String hospCode, String name, BasicAccess basicAccess) {
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
