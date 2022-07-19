package com.terminal.manage.services;

import com.github.pagehelper.PageInfo;
import com.terminal.manage.model.Product;

import java.util.Optional;

/**
 * @author Jyt
 * @date 2021/9/27
 */
public interface ProductService {

    Optional<Boolean> addProduct(Product product);

    Optional<Boolean> updateProduct(Product product);

    Optional<Boolean> delProductById(Long productId);

    Optional<PageInfo<Product>> pageProductList(Integer page, Integer pageSize, Product product);

    Optional<Product> getProductById(Long productId);
}