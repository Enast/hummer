package org.enast.hummer.dynamodel.conmon;

import java.util.Date;
import java.util.Set;

/**
 * 资源类型属性
 */

public class ResourceAttribute {
    private static final long serialVersionUID = 1L;
    /**
     * 资源属性ID
     */
    private String id;
    /**
     * 资源属性编码
     */

    private String code;
    /**
     * 资源属性名称
     */

    private String name;
    /**
     * 资源类型Code
     */

    private String typeCode;
    /**
     * 资源属性类型
     */

    private AttributeType type;

    private String aliasCode;


    private String capabilityCode;

    /**
     * 资源属性数据类型
     */

    private DataType dataType;
    /**
     * 日期或时间类型
     */

    private String timeFormat;
    /**
     * 资源属性数据长度
     */

    private Integer dataLength;
    /**
     * 资源属性数据精度
     */

    private Integer dataScale;
    /**
     * 最大值，数字类型属性可选填
     */

    private String maximum;
    /**
     * 最小值，数字类型属性可选填
     */

    private String minimum;
    /**
     * 引用关系id
     */

    private String referenceId;


    private String dictCode;

    /**
     * 资源属性展示顺序
     */

    private Integer sort = 0;

    /**
     * 资源属性是否唯一键
     */

    private Boolean unique;
    /**
     * 资源属性是否可为空
     */

    private Boolean required;
    /**
     * 资源动态属性是否可告警
     */

    private Boolean alertable;
    /**
     * 资源动态属性是否保留历史
     */

    private Boolean keepHistory;

    private Boolean hotData;

    private Boolean cacheData;


    private Boolean keyCode;


    private String logicalRegex;

    /**
     * 是否加密，只有文本类型能加密，且页面不能修改
     */

    private Boolean encrypt;
    /**
     * 资源属性是否列展示
     */

    private Boolean display;
    /**
     * 资源属性列展示顺序
     */

    private Integer displayOrder = 0;
    /**
     * 资源属性列app是否展示
     */

    private Boolean appDisplay;
    /**
     * 资源属性列app展示顺序
     */

    private Integer appDisplayOrder = 0;
    /**
     * 资源属性列app可编辑
     */

    private Boolean appEditable;
    /**
     * 资源属性数据检索
     */

    private Boolean filter;
    /**
     * 资源属性检索顺序
     */

    private Integer filterOrder = 0;


    private Boolean detailsDisplay;
    /**
     * 资源属性检索顺序
     */

    private Boolean detailsEditable;

    /**
     * 是否默认属性
     */

    private Boolean defaultAttr;
    /**
     * 默认值
     */

    private String defaultValue;
    /**
     * 资源属性备注
     */

    private String from;
    /**
     * 资源属性计量单位
     */

    private String unit;



    private boolean sys;


    private boolean assetAttr;

    /**
     * 资源属性备注
     */

    private String remark;

    //公共字段

    private String createBy;


    private Date created;


    private String modifyBy;


    private Date modified;

    /**
     * 字典是否支持多选
     */

    private Boolean dictMultiSelect;

    /**
     * @since dy
     */

    private String version;
    /**
     * @since dy
     */

    private String foreignKey;
    /**
     * @since dy
     */

    private String specs;
    /**
     * @since dy
     */

    private String format;
    /**
     * @since dy
     */

    private String source;

    /**
     * @since dy
     */

    private String accessMode;

    /**
     * @since dy
     */

    private AttributeOrigin origin;

    /**
     * @since dy
     */

    private String eventType;


    private String level;

    private Set<Integer> channel;



    private Set<String> modelIdentifiers;

    /**
     * 属性唯一标识
     * 设备型号+固件版本号+资源类型
     */

    private Set<String> identifiers;


    private Set<String> superCategorys;

    private Set<String> categorys;


    private Set<String> modelVersions;


    private String labelType;


    public ResourceAttribute() {
    }

    public ResourceAttribute(String code, String name, DataType dataType, Boolean unique, Boolean required, String accessMode, Integer dataLength) {
        this.code = code;
        this.name = name;
        this.required = required;
        this.dataType = dataType;
        this.unique = unique;
        this.accessMode = accessMode;
        this.dataLength = dataLength;
    }

    public Set<String> getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(Set<String> identifiers) {
        this.identifiers = identifiers;
    }

    public String getLabelType() {
        return labelType;
    }

    public void setLabelType(String labelType) {
        this.labelType = labelType;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Set<Integer> getChannel() {
        return channel;
    }

    public void setChannel(Set<Integer> channel) {
        this.channel = channel;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public AttributeOrigin getOrigin() {
        return origin;
    }

    public void setOrigin(AttributeOrigin origin) {
        this.origin = origin;
    }

    public Boolean getDictMultiSelect() {
        return dictMultiSelect;
    }

    public void setDictMultiSelect(Boolean dictMultiSelect) {
        this.dictMultiSelect = dictMultiSelect;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public AttributeType getType() {
        return type;
    }

    public void setType(AttributeType type) {
        this.type = type;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public String getTimeFormat() {
        return timeFormat;
    }

    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }

    public Integer getDataLength() {
        return dataLength;
    }

    public void setDataLength(Integer dataLength) {
        this.dataLength = dataLength;
    }

    public Integer getDataScale() {
        return dataScale;
    }

    public void setDataScale(Integer dataScale) {
        this.dataScale = dataScale;
    }

    public String getMaximum() {
        return maximum;
    }

    public void setMaximum(String maximum) {
        this.maximum = maximum;
    }

    public String getMinimum() {
        return minimum;
    }

    public void setMinimum(String minimum) {
        this.minimum = minimum;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Boolean getUnique() {
        return unique;
    }

    public void setUnique(Boolean unique) {
        this.unique = unique;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Boolean getAlertable() {
        return alertable;
    }

    public void setAlertable(Boolean alertable) {
        this.alertable = alertable;
    }

    public Boolean getKeepHistory() {
        return keepHistory;
    }

    public void setKeepHistory(Boolean keepHistory) {
        this.keepHistory = keepHistory;
    }

    public Boolean getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(Boolean encrypt) {
        this.encrypt = encrypt;
    }

    public Boolean getFilter() {
        return filter;
    }

    public void setFilter(Boolean filter) {
        this.filter = filter;
    }

    public Integer getFilterOrder() {
        return filterOrder;
    }

    public void setFilterOrder(Integer filterOrder) {
        this.filterOrder = filterOrder;
    }

    public Boolean getDisplay() {
        return display;
    }

    public void setDisplay(Boolean display) {
        this.display = display;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Boolean getAppDisplay() {
        return appDisplay;
    }

    public void setAppDisplay(Boolean appDisplay) {
        this.appDisplay = appDisplay;
    }

    public Integer getAppDisplayOrder() {
        return appDisplayOrder;
    }

    public void setAppDisplayOrder(Integer appDisplayOrder) {
        this.appDisplayOrder = appDisplayOrder;
    }

    public Boolean getAppEditable() {
        return appEditable;
    }

    public void setAppEditable(Boolean appEditable) {
        this.appEditable = appEditable;
    }

    public Boolean getDefaultAttr() {
        return defaultAttr;
    }

    public void setDefaultAttr(Boolean defaultAttr) {
        this.defaultAttr = defaultAttr;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(String modifyBy) {
        this.modifyBy = modifyBy;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public String getAliasCode() {
        return aliasCode;
    }

    public void setAliasCode(String aliasCode) {
        this.aliasCode = aliasCode;
    }

    public String getCapabilityCode() {
        return capabilityCode;
    }

    public void setCapabilityCode(String capabilityCode) {
        this.capabilityCode = capabilityCode;
    }

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }

    public Boolean getHotData() {
        return hotData;
    }

    public void setHotData(Boolean hotData) {
        this.hotData = hotData;
    }

    public Boolean getCacheData() {
        return cacheData;
    }

    public void setCacheData(Boolean cacheData) {
        this.cacheData = cacheData;
    }

    public Boolean getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(Boolean keyCode) {
        this.keyCode = keyCode;
    }

    public String getLogicalRegex() {
        return logicalRegex;
    }

    public void setLogicalRegex(String logicalRegex) {
        this.logicalRegex = logicalRegex;
    }

    public boolean isSys() {
        return sys;
    }

    public void setSys(boolean sys) {
        this.sys = sys;
    }

    public boolean isAssetAttr() {
        return assetAttr;
    }

    public void setAssetAttr(boolean assetAttr) {
        this.assetAttr = assetAttr;
    }

    public Boolean getDetailsDisplay() {
        return detailsDisplay;
    }

    public void setDetailsDisplay(Boolean detailsDisplay) {
        this.detailsDisplay = detailsDisplay;
    }

    public Boolean getDetailsEditable() {
        return detailsEditable;
    }

    public void setDetailsEditable(Boolean detailsEditable) {
        this.detailsEditable = detailsEditable;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getForeignKey() {
        return foreignKey;
    }

    public void setForeignKey(String foreignKey) {
        this.foreignKey = foreignKey;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getAccessMode() {
        return accessMode;
    }

    public void setAccessMode(String accessMode) {
        this.accessMode = accessMode;
    }

    public Set<String> getModelIdentifiers() {
        return modelIdentifiers;
    }

    public void setModelIdentifiers(Set<String> modelIdentifiers) {
        this.modelIdentifiers = modelIdentifiers;
    }


    public Set<String> getSuperCategorys() {
        return superCategorys;
    }

    public void setSuperCategorys(Set<String> superCategorys) {
        this.superCategorys = superCategorys;
    }

    public Set<String> getCategorys() {
        return categorys;
    }

    public void setCategorys(Set<String> categorys) {
        this.categorys = categorys;
    }

    public Set<String> getModelVersions() {
        return modelVersions;
    }

    public void setModelVersions(Set<String> modelVersions) {
        this.modelVersions = modelVersions;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ResourceAttribute other = (ResourceAttribute) obj;
        if (code == null) {
            if (other.code != null)
                return false;
        } else if (!code.equals(other.code))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ResourceAttribute{" + "id='" + id + '\'' + ", code='" + code + '\'' + ", name='" + name + '\'' + ", typeCode='" + typeCode + '\'' + ", type=" + type + ", aliasCode='" + aliasCode + '\'' + ", capabilityCode='" + capabilityCode + '\'' + ", dataType=" + dataType + ", timeFormat='" + timeFormat + '\'' + ", dataLength=" + dataLength + ", dataScale=" + dataScale + ", maximum='" + maximum + '\'' + ", minimum='" + minimum + '\'' + ", referenceId='" + referenceId + '\'' + ", dictCode='" + dictCode + '\'' + ", sort=" + sort + ", unique=" + unique + ", nullable=" + required + ", alertable=" + alertable + ", keepHistory=" + keepHistory + ", hotData=" + hotData + ", cacheData=" + cacheData + ", keyCode=" + keyCode + ", logicalRegex=" + logicalRegex + ", encrypt=" + encrypt + ", display=" + display + ", displayOrder=" + displayOrder + ", appDisplay=" + appDisplay + ", appDisplayOrder=" + appDisplayOrder + ", appEditable=" + appEditable + ", dictMultiSelect=" + dictMultiSelect + ", filter=" + filter + ", filterOrder=" + filterOrder + ", defaultAttr=" + defaultAttr + ", defaultValue='" + defaultValue + '\'' + ", from='" + from + '\'' + ", unit='" + unit + '\'' + ", remark='" + remark + '\'' + ", createBy='" + createBy + '\'' + ", created=" + created + ", modifyBy='" + modifyBy + '\'' + ", modified=" + modified + '}';
    }
}
