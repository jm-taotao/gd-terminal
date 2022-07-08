package com.terminal.manage.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Jyt
 * @date 2021/9/27
 */

@ApiModel("菜单信息")
@Table(name = "terminal_menu")
public class Menu extends Base implements Serializable {

    private static final long serialVersionUID = 7533183372523572651L;

    @Id
    @Column(insertable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("菜单id")
    private Long id;

    @ApiModelProperty("父级菜单id")
    private Long pid;

    @ApiModelProperty("菜单名称")
    private String name;

    @ApiModelProperty("菜单序号")
    private Integer sno;

    @ApiModelProperty("菜单地址")
    private String url;

//    @ApiModelProperty(name = "type",notes = "类型")
//    private Integer type;

//    @ApiModelProperty(name = "status",notes = "状态",value = "1/-1,/正常/停用")
//    private Integer status;

    @ApiModelProperty(name = "isDeleted",notes = "是否已删除",value = "YES/NO")
    private String isDeleted;

    @ApiModelProperty("子菜单")
    @Transient
    private List<Menu> children;

    private LocalDateTime updateTime;

    private LocalDateTime createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

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

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    public Integer getSno() {
        return sno;
    }

    public void setSno(Integer sno) {
        this.sno = sno;
    }
}