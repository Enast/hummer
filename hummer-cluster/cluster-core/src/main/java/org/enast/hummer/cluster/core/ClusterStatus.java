package org.enast.hummer.cluster.core;

/**
 * 当前实例主从节点状态:
 * master = true 表示是主节点
 *
 * @author zhujinming6
 * @create 2019-10-08 11:41
 * @update 2019-10-08 11:41
 **/
public class ClusterStatus {

    public static boolean master;

    public static boolean isMaster() {
        return master;
    }

    public static void setMaster(boolean master) {
        ClusterStatus.master = master;
    }
}
