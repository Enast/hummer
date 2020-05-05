package org.enast.hummer.dynamodel.conmon;


import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;


public class Page<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private int pageNo;

    private int pageSize;

    private int totalPage = 1;

    private long total;
    private Collection<T> rows;

    public Page() {
    }

    public Page(PageParam pageParam) {
        this.pageNo = pageParam.getPageNo();
        this.pageSize = pageParam.getPageSize();
    }

    /**
     * 拷贝参数值
     *
     * @param p
     */
    public void copy(Page<T> p) {
        this.pageNo = p.getPageNo();
        this.pageSize = p.getPageSize();
        this.totalPage = p.getTotalPage();
        this.total = p.getTotal();
        this.rows = p.getRows();
    }

    /**
     * 拷贝参数值
     *
     * @param p
     */
    public void copyIgnoreRows(Page<?> p) {
        this.pageNo = p.getPageNo();
        this.pageSize = p.getPageSize();
        this.totalPage = p.getTotalPage();
        this.total = p.getTotal();
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public Collection<T> getRows() {
        return rows == null ? Collections.emptyList() : rows;
    }

    public void setRows(Collection<T> rows) {
        this.rows = rows;
    }

    public static <T> Page<T> build(PageParam pageParam) {
        Page<T> pagination = new Page<>();
        pagination.setPageNo(pageParam.getPageNo());
        pagination.setPageSize(pageParam.getPageSize());
        pagination.setRows(Collections.emptyList());
        return pagination;
    }


    public static <T> Page<T> build(int pageNo, int pageSize) {
        Page<T> pagination = new Page<>();
        pagination.setPageNo(pageNo);
        pagination.setPageSize(pageSize);
        pagination.setRows(Collections.emptyList());
        return pagination;
    }

    public void setTotalCount(Long total) {

    }
}