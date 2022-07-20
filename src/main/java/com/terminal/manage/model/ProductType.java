package com.terminal.manage.model;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author TAO
 * @date 2022/7/20 / 0:37
 */
@Table(name = "terminal_product_type")
public class ProductType extends Base implements Serializable {

    private static final long serialVersionUID = -4067207142626556794L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false)
    @ApiModelProperty("商品类型ID")
    private Long id;

    @ApiModelProperty("商品类型父级ID")
    private Long pid;

    @ApiModelProperty("商品类型名称")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
