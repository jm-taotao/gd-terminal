package com.terminal.manage.model;

import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;

public class Base {

    @ApiModelProperty(name = "isDeleted",notes = "是否已删除",value = "YES/NO")
    protected String isDeleted;

    @ApiModelProperty(name = "updateTime",notes = "更新时间")
    protected LocalDateTime updateTime;

    @ApiModelProperty(name = "createTime",notes = "创建时间")
    protected LocalDateTime createTime;

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
}
