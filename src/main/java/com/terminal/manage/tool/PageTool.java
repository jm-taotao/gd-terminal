package com.terminal.manage.tool;

/**
 * @author TAO
 * @date 2022/7/8 / 22:31
 */
public class PageTool {

    private Integer page;

    private Integer pageSize;

    private Integer offset;

    private Integer limitNum;


    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getOffset() {
        return page*pageSize;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(Integer limitNum) {
        this.limitNum = limitNum;
    }
}
