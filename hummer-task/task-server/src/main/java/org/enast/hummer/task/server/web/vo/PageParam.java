package org.enast.hummer.task.server.web.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;

import java.io.Serializable;
import java.util.Map;

@ApiModel(value = "PageParam", description = "分页实体类")
public class PageParam implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiParam(name = "pageNo", value = "页码", example = "1", required = false)
    private Integer pageNo = 1;
    @ApiParam(name = "pageSize", value = "每页数据量", example = "10", required = false)
    private Integer pageSize = 10;
    @ApiParam(hidden = true)
    private String sort;
    @ApiParam(hidden = true)
    private String order;
    private Map<String, Object> param;

    public PageParam() {
    }

    public PageParam(Integer pageNo, Integer pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    @ApiParam(hidden = true)
    public int getStart() {
        return (pageNo - 1) * pageSize;
    }

    public Map<String, Object> getParam() {
        return param;
    }

    public void setParam(Map<String, Object> param) {
        this.param = param;
    }
}
