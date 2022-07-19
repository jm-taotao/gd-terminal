package com.terminal.manage.model;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author TAO
 * @date 2022/7/20 / 0:14
 */
@Table(name = "terminal_product")
public class Product extends Base implements Serializable {

    private static final long serialVersionUID = -6057732618210690251L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false)
    @ApiModelProperty("商品ID")
    private Long id;

    @ApiModelProperty("商品名称")
    private String name;

    @ApiModelProperty("商品条码")
    private String barCode;

    @ApiModelProperty("商品类型")
    private String typeId;

    @ApiModelProperty("商品规格")
    private String spec;

    @ApiModelProperty("商品单位")
    private String unit;

    @ApiModelProperty("商品描述")
    private String description;

    @ApiModelProperty("商品小图")
    private String minImageUrl;

    @ApiModelProperty("商品正常大小图片")
    private String normalImageUrl;

    @ApiModelProperty("商品占位(占用几格货道)")
    private String colSpan;

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

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMinImageUrl() {
        return minImageUrl;
    }

    public void setMinImageUrl(String minImageUrl) {
        this.minImageUrl = minImageUrl;
    }

    public String getNormalImageUrl() {
        return normalImageUrl;
    }

    public void setNormalImageUrl(String normalImageUrl) {
        this.normalImageUrl = normalImageUrl;
    }

    public String getColSpan() {
        return colSpan;
    }

    public void setColSpan(String colSpan) {
        this.colSpan = colSpan;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }
}
