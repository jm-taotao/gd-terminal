package com.terminal.manage.controller;

import com.github.pagehelper.PageInfo;
import com.terminal.manage.base.response.Response;
import com.terminal.manage.model.Product;
import com.terminal.manage.services.ProductService;
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
@Api("商品管理")
@RestController
@RequestMapping("/product/")
public class ProductController {

    @Autowired
    private ProductService productService;



    @RequestMapping("list")
    @ApiOperation(value = "商品列表", notes = "商品列表",response = Product.class,httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",required = true,paramType = "Integer"),
            @ApiImplicitParam(name = "pageSize",required = true,paramType = "Integer"),
            @ApiImplicitParam(name = "product",required = true,paramType = "Product")
    })
    public Response<PageInfo<Product>> list(Integer page, Integer pageSize, Product product){
        return Response.doResponse(()->{
            Optional<PageInfo<Product>> optionalProducts = productService.pageProductList(page-1, pageSize, product);
            return optionalProducts.orElse(null);
        });
    }

    @ApiOperation(value = "商品信息", notes = "商品信息",response = Product.class,httpMethod = "POST")
    @ApiImplicitParam(name = "productId",required = true,paramType = "Long")
    @RequestMapping("detail")
    public Response<Product> detail(Long productId){
        return Response.doResponse(()->{
           Optional<Product> product = productService.getProductById(productId);
           return product.orElse(null);
        });
    }

    @ApiOperation(value = "新增商品", notes = "新增商品",response = Boolean.class,httpMethod = "POST")
    @ApiImplicitParam(name = "product",required = true,paramType = "Product")
    @RequestMapping("add")
    public Response<Boolean> add(Product product){
        return Response.doResponse(()->{
            Optional<Boolean> booleanOptional = productService.addProduct(product);
            return booleanOptional.orElse(false);
        });
    }


    @RequestMapping("update")
    public Response<Boolean> update(Product product){
        return Response.doResponse(()->{
            Optional<Boolean> optionalBoolean = productService.updateProduct(product);
            return optionalBoolean.orElse(false);
        });
    }


    @RequestMapping("del")
    public Response<Boolean> delProductById(Long id){
        return Response.doResponse(()->{
            Optional<Boolean> optionalBoolean = productService.delProductById(id);
            return optionalBoolean.orElse(false);
        });
    }
}