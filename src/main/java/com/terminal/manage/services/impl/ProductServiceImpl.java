package com.terminal.manage.services.impl;

import com.github.pagehelper.PageInfo;
import com.terminal.manage.base.enums.Constants;
import com.terminal.manage.base.enums.IsDeleted;
import com.terminal.manage.base.excption.BizException;
import com.terminal.manage.mapper.ProductMapper;
import com.terminal.manage.model.Product;
import com.terminal.manage.services.ProductService;
import com.terminal.manage.tool.DataUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Jyt
 * @date 2021/9/27
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;
    

    @Override
    public Optional<Boolean> addProduct(Product product) {

        if (!StringUtils.isEmpty(product.getName())){
            Example example = new Example(Product.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("name",product.getName());
            int count = productMapper.selectCountByExample(example);
            if (count>0){
                throw new BizException(Constants.PRODUCT_EXIST);
            }
        }
        product.setIsDeleted(IsDeleted.NO.code);
        product.setCreateTime(LocalDateTime.now());
        product.setUpdateTime(LocalDateTime.now());
        Boolean flag = productMapper.insertSelective(product) > 0;
        return Optional.of(flag);
    }

    @Override
    public Optional<Boolean> updateProduct(Product product) {
        if (!StringUtils.isEmpty(product.getName())){
            Example example = new Example(Product.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("name",product.getName());
            Product productOld = productMapper.selectOneByExample(example);
            if (Objects.nonNull(productOld) && !productOld.getId().equals(product.getId())){
                throw new BizException(Constants.PRODUCT_EXIST);
            }
        }
        product.setUpdateTime(LocalDateTime.now());
        Boolean flag = productMapper.updateByPrimaryKeySelective(product) < 0;
        return Optional.of(flag);
    }

    @Override
    public Optional<Boolean> delProductById(Long productId) {
        if (Objects.isNull(productId)){
            return Optional.of(false);
        }
        Product product = new Product();
        product.setId(productId);
        product.setIsDeleted(IsDeleted.YES.code);
        product.setUpdateTime(LocalDateTime.now());
        Example example = new Example(Product.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id",product.getId())
                .andEqualTo("isDeleted",IsDeleted.NO.code);
        Boolean flag = productMapper.updateByExampleSelective(product,example) > 0;
        return Optional.of(flag);
    }

    @Override
    public Optional<PageInfo<Product>> pageProductList(Integer page, Integer pageSize, Product product) {
        Example example = Example.builder(Product.class).build();
        Example.Criteria criteria = example.createCriteria();
        if (!Objects.isNull(product.getId())){
            criteria.andEqualTo("id",product.getId());
        }
        if (!StringUtils.isEmpty(product.getName())){
            criteria.andLike("name","%"+product.getName()+"%");
        }
        if (!StringUtils.isEmpty(product.getIsDeleted())){
            criteria.andEqualTo("isDeleted", product.getIsDeleted());
        }
        if (!StringUtils.isEmpty(product.getStart())){
            criteria.andGreaterThanOrEqualTo("createTime",product.getStart());
        }
        if (!StringUtils.isEmpty(product.getEnd())){
            criteria.andLessThanOrEqualTo("createTime",product.getEnd());
        }
        List<Product> products = productMapper.selectByExampleAndRowBounds(example,new RowBounds(page*pageSize,pageSize));
        DataUtils.formatterDateForList(products);
        int count = productMapper.selectCountByExample(example);
        PageInfo<Product> productPageInfo = new PageInfo<>(products);
        productPageInfo.setTotal(count);
        return Optional.of(productPageInfo);
    }

    @Override
    public Optional<Product> getProductById(Long productId) {
        if (Objects.isNull(productId)){
            return Optional.empty();
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        return Optional.of(product);
    }
}
