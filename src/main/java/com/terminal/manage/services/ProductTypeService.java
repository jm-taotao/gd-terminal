package com.terminal.manage.services;

import com.github.pagehelper.PageInfo;
import com.terminal.manage.model.ProductType;

import java.util.Optional;

/**
 * @author Jyt
 * @date 2021/9/27
 */
public interface ProductTypeService {

    Optional<Boolean> addProductType(ProductType productType);

    Optional<Boolean> updateProductType(ProductType productType);

    Optional<Boolean> delProductTypeById(Long productTypeId);

    Optional<PageInfo<ProductType>> pageProductTypeList(Integer page, Integer pageSize, ProductType productType);

    Optional<ProductType> getProductTypeById(Long productTypeId);
}