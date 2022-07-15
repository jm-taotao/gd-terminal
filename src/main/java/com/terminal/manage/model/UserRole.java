package com.terminal.manage.model;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author TAO
 * @date 2022/7/16 / 2:01
 */
public class UserRole extends Base implements Serializable {

    private static final long serialVersionUID = -1762750539214096365L;

    @Id
    @Column(insertable = false)
    private Long id;

    @ApiModelProperty(name = "userId",notes = "用户ID")
    private Long userId;

    @ApiModelProperty(name = "roleId",notes = "角色ID")
    private Long roleId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }


    @Override
    public String getIsDeleted() {
        return super.getIsDeleted();
    }

    @Override
    public void setIsDeleted(String isDeleted) {
        super.setIsDeleted(isDeleted);
    }

    @Override
    public LocalDateTime getUpdateTime() {
        return super.getUpdateTime();
    }

    @Override
    public void setUpdateTime(LocalDateTime updateTime) {
        super.setUpdateTime(updateTime);
    }

    @Override
    public LocalDateTime getCreateTime() {
        return super.getCreateTime();
    }

    @Override
    public void setCreateTime(LocalDateTime createTime) {
        super.setCreateTime(createTime);
    }
}
