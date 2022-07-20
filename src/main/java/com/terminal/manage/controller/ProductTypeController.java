package com.terminal.manage.controller;

import com.github.pagehelper.PageInfo;
import com.terminal.manage.base.response.Response;
import com.terminal.manage.model.ProductType;
import com.terminal.manage.services.ProductService;
import com.terminal.manage.services.ProductTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author Jyt
 * @date 2021/9/29
 */
@Api("商品类型管理")
@RestController
@RequestMapping("/productType/")
public class ProductTypeController {

    @Autowired
    private ProductTypeService productTypeService;


    @RequestMapping("list")
    @ApiOperation(value = "商品类型列表", notes = "商品类型列表",response = ProductType.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",required = true,paramType = "Integer"),
            @ApiImplicitParam(name = "pageSize",required = true,paramType = "Integer"),
            @ApiImplicitParam(name = "productType",required = true,paramType = "ProductType")
    })
    public Response<PageInfo<ProductType>> list(Integer page, Integer pageSize, ProductType productType){
        return Response.doResponse(()->{
            Optional<PageInfo<ProductType>> optionalProductTypes = productTypeService.pageProductTypeList(page-1, pageSize, productType);
            return optionalProductTypes.orElse(null);
        });
    }

    @ApiOperation(value = "商品类型信息", notes = "商品类型信息",response = ProductType.class,httpMethod = "POST")
    @ApiImplicitParam(name = "productTypeId",required = true,paramType = "Long")
    @RequestMapping("detail")
    public Response<ProductType> detail(Long productTypeId){
        return Response.doResponse(()->{
           Optional<ProductType> productType = productTypeService.getProductTypeById(productTypeId);
           return productType.orElse(null);
        });
    }

    @ApiOperation(value = "新增商品类型", notes = "新增商品类型",response = Boolean.class,httpMethod = "POST")
    @ApiImplicitParam(name = "productType",required = true,paramType = "ProductType")
    @RequestMapping("add")
    public Response<Boolean> add(ProductType productType){
        return Response.doResponse(()->{
            Optional<Boolean> booleanOptional = productTypeService.addProductType(productType);
            return booleanOptional.orElse(false);
        });
    }


    @RequestMapping("update")
    public Response<Boolean> update(ProductType productType){
        return Response.doResponse(()->{
            Optional<Boolean> optionalBoolean = productTypeService.updateProductType(productType);
            return optionalBoolean.orElse(false);
        });
    }


    @RequestMapping("del")
    public Response<Boolean> delProductTypeById(Long id){
        return Response.doResponse(()->{
            Optional<Boolean> optionalBoolean = productTypeService.delProductTypeById(id);
            return optionalBoolean.orElse(false);
        });
    }
}