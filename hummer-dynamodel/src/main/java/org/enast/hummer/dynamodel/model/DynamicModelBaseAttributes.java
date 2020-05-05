package org.enast.hummer.dynamodel.model;

import org.enast.hummer.dynamodel.attribute.BaseAttribute;
import org.enast.hummer.dynamodel.conmon.DataType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhujinming6
 * @create 2020-03-17 10:24
 * @update 2020-03-17 10:24
 **/
public class DynamicModelBaseAttributes {

    public static BaseAttribute id = new BaseAttribute("id", "主键", DataType.LONG, true, true, "r", 32, true, null);
    public static BaseAttribute name = new BaseAttribute("name", "名称", DataType.VARCHAR, false, false, "rw", 128, null, null);
    public static BaseAttribute modifyBy = new BaseAttribute("modify_by", "修改者", DataType.VARCHAR, false, false, "rw", 128, null, null);
    public static BaseAttribute createBy = new BaseAttribute("create_by", "创建者", DataType.VARCHAR, false, false, "rw", 128, null, null);
    public static BaseAttribute createTime = new BaseAttribute("create_time", "数据创建时间", DataType.TIMESTAMP, false, false, "rw", 0, null, null);
    public static BaseAttribute modifyTime = new BaseAttribute("modify_time", "数据修改时间", DataType.TIMESTAMP, false, false, "rw", 0, null, null);
    public static BaseAttribute dataValid = new BaseAttribute("data_valid", "数据有效性", DataType.BOOL, false, false, "rw", 0, true, "TRUE");

    public static List<BaseAttribute> commons = new ArrayList<>();

    static {

        commons.add(id);
        commons.add(name);
        commons.add(modifyBy);
        commons.add(createBy);
        commons.add(createTime);
        commons.add(modifyTime);
        commons.add(dataValid);
    }
}
