package com.terminal.manage.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @author Jyt
 * @date 2021/9/26
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("用户基本信息")
public class User {

    private Long id;

    @ApiModelProperty(name = "loginName",notes = "用户登录名")
    private String loginName;

    @ApiModelProperty(name = "userName",notes = "用户姓名")
    private String userName;

    private String password;

    @ApiModelProperty(name = "mobile",notes = "用户手机号")
    private String mobile;

    @ApiModelProperty(name = "sex",notes = "用户性别")
    private Integer sex;

    @ApiModelProperty(name = "type",notes = "类型")
    private Integer type;

    @ApiModelProperty(name = "status",notes = "状态",value = "1/-1,/正常/停用")
    private Integer status;

    @ApiModelProperty(name = "isDeleted",notes = "是否已删除",value = "YES/NO")
    private String isDeleted;

    private LocalDateTime updateTime;

    private LocalDateTime createTime;

}
