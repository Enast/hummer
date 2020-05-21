package org.enast.hummer.task.server.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;


@ApiModel(value = "Pagination",description = "分页包装类")
public class Pagination<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(name = "pageNo", value = "当前页码")
    private int pageNo;

    @ApiModelProperty(name = "pageSize", value = "每页数据量")
    private int pageSize;

    @ApiModelProperty(name = "totalPage", value = "总页数")
    private int totalPage = 1;

    @ApiModelProperty(name = "total", value = "总数据量")
    private long total;
    @ApiModelProperty(name = "rows", value = "当前页列表数据")
    private Collection<T> rows;
    public Pagination() {
    }

    public Pagination(PageParam pageParam) {
        this.pageNo = pageParam.getPageNo();
        this.pageSize = pageParam.getPageSize();
    }

    public Pagination(int pageNo, sf.common.wrapper.Page<T> page) {
        this.pageNo = pageNo;
        this.pageSize = page.getPageSize();
        this.totalPage = page.getTotalPage();
        this.total = page.getTotalCount();
        this.rows = page.getList();
    }

    public <U> Pagination(int pageNo, sf.common.wrapper.Page<U> page, Function<? super List<U>, Collection<T>> dataMapper) {
        this.pageNo = pageNo;
        this.pageSize = page.getPageSize();
        this.totalPage = page.getTotalPage();
        this.total = page.getTotalCount();
        this.rows = dataMapper.apply(page.getList());
    }

    /**
     * 拷贝参数值
     * @param p
     */
    public void copy(Pagination<T> p) {
        this.pageNo = p.getPageNo();
        this.pageSize = p.getPageSize();
        this.totalPage = p.getTotalPage();
        this.total = p.getTotal();
        this.rows = p.getRows();
    }

    /**
     * 拷贝参数值
     * @param p
     */
    public void copyIgnoreRows(Pagination<?> p) {
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

    public static <T> Pagination<T> build(PageParam pageParam) {
        Pagination<T> pagination = new Pagination<>();
        pagination.setPageNo(pageParam.getPageNo());
        pagination.setPageSize(pageParam.getPageSize());
        pagination.setRows(Collections.emptyList());
        return pagination;
    }


    public static Pagination build(Page page) {
        Pagination p = new Pagination();
        p.setPageNo(page.getNumber() + 1);
        p.setPageSize(page.getSize());
        p.setTotalPage(page.getTotalPages());
        p.setTotal(page.getTotalElements());
        p.setRows(page.getContent());
        return p;
    }

    public static <T> Pagination<T> build(int pageNo, int pageSize) {
        Pagination<T> pagination = new Pagination<>();
        pagination.setPageNo(pageNo);
        pagination.setPageSize(pageSize);
        pagination.setRows(Collections.emptyList());
        return pagination;
    }

}