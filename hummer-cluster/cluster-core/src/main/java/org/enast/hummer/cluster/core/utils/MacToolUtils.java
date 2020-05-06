package org.enast.hummer.cluster.core.utils;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * mac 地址
 *
 * @author zhujinming6
 * @create 2019-10-29 14:42
 * @update 2019-10-29 14:42
 **/
public class MacToolUtils {
    /**
     * 返回当前机器的mac地址列表
     * 多网卡会返回多个
     *
     * @return
     * @throws Exception
     */
    public static List<String> getMacList() throws Exception {
        java.util.Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
        if (en == null) {
            return Collections.EMPTY_LIST;
        }
        StringBuilder sb = new StringBuilder();
        ArrayList<String> tmpMacList = new ArrayList<>();
        while (en.hasMoreElements()) {
            NetworkInterface iface = en.nextElement();
            List<InterfaceAddress> addrs = iface.getInterfaceAddresses();
            for (InterfaceAddress addr : addrs) {
                InetAddress ip = addr.getAddress();
                NetworkInterface network = NetworkInterface.getByInetAddress(ip);
                if (network == null) {
                    continue;
                }
                byte[] mac = network.getHardwareAddress();
                if (mac == null) {
                    continue;
                }
                if (network.isVirtual()) {
                    continue;
                }
                sb.delete(0, sb.length());
                for (int i = 0; i < mac.length; i++) {
                    sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                }
                tmpMacList.add(sb.toString());
            }
        }
        if (tmpMacList.size() <= 0) {
            return tmpMacList;
        }
        // 去重 排序
        List<String> unique = tmpMacList.stream().distinct().sorted().collect(Collectors.toList());
        return unique;
    }

    //    public static void main(String[] args) throws Exception {
    //        System.out.println("进行 multi net address 测试===》");
    //        List<String> macs = getMacList();
    //        System.out.println("本机的mac网卡的地址有：" + macs);
    //
    //    }
}
