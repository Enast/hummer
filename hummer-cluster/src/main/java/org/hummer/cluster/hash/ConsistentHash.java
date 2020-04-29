package org.hummer.cluster.hash;


import log.HikLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 一致性Hash(带虚拟节点)
 * 注意:S 需要重写equals()方法
 *
 * @author zhujinming6
 * @create 2019-12-18 19:07
 * @update 2019-12-18 19:07
 **/
public abstract class ConsistentHash<H, S> {

    private Logger log = LoggerFactory.getLogger(ConsistentHash.class);

    /**
     * 服务列表
     */
    private S[] servers;

    /**
     * 服务新增和删除操作比较多
     */
    private List<S> realNodes = new LinkedList<>();

    /**
     * hash "环"
     * 底层使用的红黑树
     */
    private SortedMap<Integer, S> virtualNodes = new TreeMap<Integer, S>();

    private int virtualNodesCount = 2;

    /**
     * hash"环"最大值
     */
    private int maxSize = Integer.MAX_VALUE;

    /**
     * 借鉴hashMap
     */
    static final int MAXIMUM_CAPACITY = 1 << 30;

    public ConsistentHash(S[] servers) {
        this.servers = servers;
    }

    /**
     * 初始化数据
     */
    public void init() {
        // 把原始的服务添加到真实节点列表中
        for (int i = 0; i < servers.length; i++) {
            realNodes.add(servers[i]);
        }
        for (S s : realNodes) {
            for (int i = 0; i < virtualNodesCount; i++) {
                String virtualNodeName = s.toString() + "&&VN" + String.valueOf(i);
                int hash = getHash(virtualNodeName);
                log.info("virtualNode[" + virtualNodeName + "]add, hash:" + hash));
                S oldS = virtualNodes.get(hash);
                // 判断是否hash冲突
                if (oldS != null && !oldS.equals(s)) {
                    // 解决hash 冲突
                    hash = reHash(hash, 0);
                }
                virtualNodes.put(hash, s);
            }
        }
    }

    /**
     * 使用FNV1_32_HASH算法计算服务器的Hash值,这里不使用重写hashCode的方法，最终效果没区别
     */
    private int getHash(String str) {
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (int i = 0; i < str.length(); i++) {
            hash = (hash ^ str.charAt(i)) * p;
        }
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;
        // 如果算出来的值为负数则取其绝对值
        if (hash < 0) {
            hash = Math.abs(hash);
        }
        if (hash > maxSize) {
            hash = hash & maxSize;
        }
        return hash;
    }

    /**
     * 均值hash冲突解决算法(自创)
     * <p>
     * 1.冲突时取,当前冲突节点的下一个节点的索引所在位置,然后取这两者的中间位置,再次检查是否冲突,以此类推
     * 2.hash 环满时,直接返回0,之前的0节点会被新节点替换掉
     *
     * @param hash
     * @param size 当前遍历到节点数,小于等于所有虚拟节点数
     * @return
     */
    protected int reHash(int hash, int size) {
        // 获取到大于该hash的所有Map,所以+1
        SortedMap<Integer, S> sortedMap = virtualNodes.tailMap(hash + 1);
        // hash值比较靠后,后面没有节点
        if (sortedMap.isEmpty()) {
            // 下一个节点为空,直接放入当前hash到最大值的中间位置
            if (hash < maxSize) {
                return hash + ((maxSize - hash) >>> 1);
            } else if (hash == maxSize) {
                // 0节点为空,直接放入0节点中
                if (virtualNodes.get(0) == null) {
                    return 0;
                } else {
                    int i = virtualNodes.firstKey();
                    return reHash(i >>> 1, size);
                }
            } else {
                return 0;
            }
        } else {
            Integer i = sortedMap.firstKey();
            int newHash = i > hash ? (hash + ((i - hash) >>> 1)) : 0;
            if (virtualNodes.get(newHash) == null) {
                return newHash;
            }
            // 遍历完了所有节点都没有找到合适位置,当前hash环已满
            else if (virtualNodes.size() == size || virtualNodes.size() == maxSize) {
                return 0;
            } else {
                return reHash(i, ++size);
            }
        }
    }

    /**
     * 获取节点
     *
     * @param h
     * @return
     */
    public S getServer(H h) {
        int hash = getHash(h.toString());
        // 获取到大于该hash的所有Map
        SortedMap<Integer, S> sortedMap = virtualNodes.tailMap(hash);
        if (sortedMap.isEmpty()) {
            Integer i = virtualNodes.firstKey();
            return virtualNodes.get(i);
        } else {
            // 第一个key就是顺时针过去离node最近的那个节点
            Integer i = sortedMap.firstKey();
            return virtualNodes.get(i);
        }
    }

    // 节点新增和节点删除

    /**
     * 新增节点时
     * hash冲突问题
     *
     * @param s
     * @return
     */
    public S addOrUpateServer(S s) {
        if (s == null) {
            return null;
        }
        for (int i = 0; i < virtualNodesCount; i++) {
            String virtualNodeName = s.toString() + "&&VN" + String.valueOf(i);
            int hash = getHash(virtualNodeName);
            S oldS = virtualNodes.get(hash);
            // 判断是否hash冲突
            if (oldS != null && !oldS.equals(s)) {
                hash = reHash(hash, 0);
            } else if (oldS != null && oldS.equals(s)) {
                continue;
            }
            virtualNodes.put(hash, s);
        }
        log.info("addServer s:" + s.toString()));
        return s;
    }

    /**
     * 删除节点
     * 删除相应的虚拟节点
     *
     * @param s
     * @return
     */
    public boolean deleteServer(S s) {
        if (s == null) {
            return true;
        }
        Iterator<Map.Entry<Integer, S>> it = virtualNodes.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, S> entry = it.next();
            S oldS = entry.getValue();
            if (oldS.equals(s)) {
                it.remove();
            }
        }
        log.info("deleteServer s:" + s.toString()));
        return true;
    }

    // 清除所有节点
    public void clear() {
        realNodes.clear();
        virtualNodes.clear();
    }

    public void setServers(S[] servers) {
        this.servers = servers;
    }

    public void setVirtualNodesCount(int virtualNodesCount) {
        this.virtualNodesCount = virtualNodesCount;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = tableSizeFor(maxSize);
    }

    /**
     * 借鉴hashMap,返回2的幂次方
     */
    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

//    public static void main(String[] args) {
//        ParseDataQueue[] servers = {new ParseDataQueue("b0af44f9a2954baca4e7a5465b54157d"), new ParseDataQueue("b0af44f9a2954baca4e7a5465b54157d"), new ParseDataQueue("cb685996c9f64b8598bee53f7bcdc7b3"), new ParseDataQueue("c1f885c61f424dc392dc9175b2957193"), new ParseDataQueue("48db19b203df4e79862a4e20568f2658"), new ParseDataQueue("4069021e31144728bc2ee8d9fb3445f7")};
//        ConsistentHash consistentHash = new TestConsistentHash(servers);
//        consistentHash.init();
//        ParseDataQueue queue = (ParseDataQueue) consistentHash.getServer("4311e6ff79124d22bd84f89095bd4005");
//        ParseDataQueue queue2 = (ParseDataQueue) consistentHash.getServer("b0af44f9a2954baca4e7a5465b54157d");
//        System.out.println(queue.toString());
//        System.out.println(queue2.toString());
//    }

    static class TestConsistentHash extends ConsistentHash<String, ParseDataQueue> {
        public TestConsistentHash(ParseDataQueue[] servers) {
            super(servers);
        }
    }
}

class ParseDataQueue {
    private String id;

    public ParseDataQueue(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
