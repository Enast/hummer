package org.enast.hummer.dynamodel.conmon;


import java.util.ArrayList;
import java.util.List;

/**
 * @author zhujinming6
 * @create 2020-03-17 10:24
 * @update 2020-03-17 10:24
 **/
public class DynamicModelDefaultAttributes {

    public static ResourceAttribute id = new ResourceAttribute("id", "主键", DataType.LONG, true, true, "r", 32);
    public static ResourceAttribute indexCode = new ResourceAttribute("index_code", "国标编码", DataType.VARCHAR, false, true, "rw", 128);
    public static ResourceAttribute parentIndexCode = new ResourceAttribute("parent_index_code", "父设备国标编码", DataType.VARCHAR, false, true, "rw", 128);
    public static ResourceAttribute name = new ResourceAttribute("name", "设备名称", DataType.VARCHAR, false, true, "rw", 128);
    public static ResourceAttribute resType = new ResourceAttribute("res_type", "资源类型", DataType.VARCHAR, false, true, "rw", 64);
    public static ResourceAttribute resModel = new ResourceAttribute("res_model", "设备型号", DataType.VARCHAR, false, true, "rw", 64);
    public static ResourceAttribute resId = new ResourceAttribute("res_id", "资源id", DataType.VARCHAR, false, true, "rw", 128);
    public static ResourceAttribute version = new ResourceAttribute("version", "固件版本", DataType.VARCHAR, false, true, "rw", 128);
    public static ResourceAttribute serialNo = new ResourceAttribute("serial_no", "序列号", DataType.VARCHAR, false, true, "rw", 128);
    public static ResourceAttribute manufacturer = new ResourceAttribute("manufacturer", "厂商", DataType.VARCHAR, false, true, "rw", 128);
    public static ResourceAttribute createTime = new ResourceAttribute("create_time", "数据创建时间", DataType.TIMESTAMP, false, true, "rw", 0);
    public static ResourceAttribute modifyTime = new ResourceAttribute("modify_time", "数据修改时间", DataType.TIMESTAMP, false, true, "rw", 0);
    public static ResourceAttribute dataValid = new ResourceAttribute("data_valid", "数据有效性", DataType.BOOL, false, true, "rw", 0);
    public static ResourceAttribute channelNo = new ResourceAttribute("channel_no", "通道号", DataType.INT, false, true, "rw", 0);
    public static ResourceAttribute deviceId = new ResourceAttribute("device_id", "所属设备id", DataType.VARCHAR, false, true, "rw", 128);
    public static ResourceAttribute tenantId = new ResourceAttribute("tenant_id", "租户id", DataType.VARCHAR, false, true, "rw", 64);
    public static ResourceAttribute regionIndexCode = new ResourceAttribute("region_index_code", "组织id", DataType.VARCHAR, false, true, "rw", 128);

    public static List<ResourceAttribute> deviceBaseInfo = new ArrayList<>();
    public static List<ResourceAttribute> deviceStatus = new ArrayList<>();
    public static List<ResourceAttribute> channelBaseInfo = new ArrayList<>();
    public static List<ResourceAttribute> channelStatus = new ArrayList<>();

    static {

        deviceBaseInfo.add(id);
        deviceBaseInfo.add(resId);
        deviceBaseInfo.add(indexCode);
        deviceBaseInfo.add(resType);
        deviceBaseInfo.add(name);
        deviceBaseInfo.add(version);
        deviceBaseInfo.add(resModel);
        deviceBaseInfo.add(serialNo);
        deviceBaseInfo.add(manufacturer);
        deviceBaseInfo.add(createTime);
        deviceBaseInfo.add(modifyTime);
        deviceBaseInfo.add(dataValid);
        deviceBaseInfo.add(tenantId);
        deviceBaseInfo.add(deviceId);
        deviceBaseInfo.add(regionIndexCode);

        deviceStatus.add(id);
        deviceStatus.add(resId);
        deviceStatus.add(resType);
        deviceStatus.add(resModel);
        deviceStatus.add(serialNo);
        deviceStatus.add(createTime);
        deviceStatus.add(modifyTime);
        deviceStatus.add(dataValid);
        deviceStatus.add(tenantId);
        deviceStatus.add(regionIndexCode);

        channelBaseInfo.add(id);
        channelBaseInfo.add(indexCode);
        channelBaseInfo.add(parentIndexCode);
        channelBaseInfo.add(resId);
        channelBaseInfo.add(resType);
        channelBaseInfo.add(name);
        channelBaseInfo.add(version);
        channelBaseInfo.add(resModel);
        channelBaseInfo.add(serialNo);
        channelBaseInfo.add(createTime);
        channelBaseInfo.add(modifyTime);
        channelBaseInfo.add(dataValid);
        channelBaseInfo.add(manufacturer);
        channelBaseInfo.add(deviceId);
        channelBaseInfo.add(channelNo);
        channelBaseInfo.add(tenantId);
        channelBaseInfo.add(regionIndexCode);

        channelStatus.add(id);
        channelStatus.add(resId);
        channelStatus.add(resType);
        channelStatus.add(resModel);
        channelStatus.add(serialNo);
        channelStatus.add(createTime);
        channelStatus.add(modifyTime);
        channelStatus.add(dataValid);
        channelStatus.add(deviceId);
        channelStatus.add(channelNo);
        channelStatus.add(tenantId);
        channelStatus.add(regionIndexCode);
    }
}
