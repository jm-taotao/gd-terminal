package com.terminal.manage.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * @author Jyt
 * @date 2021/9/27
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("菜单信息")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("菜单id")
    private Long id;

    @ApiModelProperty("菜单名称")
    private String name;

    @ApiModelProperty("菜单序号")
    private Integer son;

    @ApiModelProperty("菜单地址")
    private String url;

    @ApiModelProperty(name = "type",notes = "类型")
    private Integer type;

    @ApiModelProperty(name = "status",notes = "状态",value = "1/-1,/正常/停用")
    private Integer status;

    @ApiModelProperty(name = "isDeleted",notes = "是否已删除",value = "YES/NO")
    private String isDeleted;

    private LocalDateTime updateTime;

    private LocalDateTime createTime;
}