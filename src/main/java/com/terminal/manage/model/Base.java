package com.terminal.manage.model;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Transient;
import java.time.LocalDateTime;

public class Base {

    @ApiModelProperty(name = "isDeleted",notes = "是否已删除",value = "YES/NO")
    protected String isDeleted;

    @ApiModelProperty(name = "updateTime",notes = "更新时间")
    protected LocalDateTime updateTime;

    @ApiModelProperty(name = "createTime",notes = "创建时间")
    protected LocalDateTime createTime;

    @ApiModelProperty(name = "createTimeStr",notes = "创建时间(列表展示)")
    @Transient
    protected String createTimeStr;

    @ApiModelProperty(name = "start",notes = "查询条件(创建时间),开始时间")
    @Transient
    protected String start;

    @ApiModelProperty(name = "end",notes = "查询条件(创建时间),结束时间")
    @Transient
    protected String end;

    public String getUpdateTimeStr() {
        return updateTimeStr;
    }

    public void setUpdateTimeStr(String updateTimeStr) {
        this.updateTimeStr = updateTimeStr;
    }

    @Transient
    protected String updateTimeStr;

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
