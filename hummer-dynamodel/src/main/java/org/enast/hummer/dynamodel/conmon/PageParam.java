package org.enast.hummer.dynamodel.conmon;


import java.io.Serializable;

public class PageParam implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer pageNo = 1;

    private Integer pageSize = 10;

    private String sort;
    private String order;

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

    public int getStart() {
        return (pageNo - 1) * pageSize;
    }
}
