package com.terminal.manage.services.impl;

import com.github.pagehelper.PageInfo;
import com.terminal.manage.base.enums.Constants;
import com.terminal.manage.base.enums.IsDeleted;
import com.terminal.manage.base.excption.BizException;
import com.terminal.manage.mapper.ProductTypeMapper;
import com.terminal.manage.model.ProductType;
import com.terminal.manage.services.ProductTypeService;
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
public class ProductTypeServiceImpl implements ProductTypeService {

    @Autowired
    private ProductTypeMapper productTypeMapper;
    

    @Override
    public Optional<Boolean> addProductType(ProductType productType) {

        if (!StringUtils.isEmpty(productType.getName())){
            Example example = new Example(ProductType.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("name",productType.getName());
            int count = productTypeMapper.selectCountByExample(example);
            if (count>0){
                throw new BizException(Constants.PRODUCT_TYPE_EXIST);
            }
        }
        productType.setIsDeleted(IsDeleted.NO.code);
        productType.setCreateTime(LocalDateTime.now());
        productType.setUpdateTime(LocalDateTime.now());
        Boolean flag = productTypeMapper.insertSelective(productType) > 0;
        return Optional.of(flag);
    }

    @Override
    public Optional<Boolean> updateProductType(ProductType productType) {
        if (!StringUtils.isEmpty(productType.getName())){
            Example example = new Example(ProductType.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("name",productType.getName());
            ProductType productTypeOld = productTypeMapper.selectOneByExample(example);
            if (Objects.nonNull(productTypeOld) && !productTypeOld.getId().equals(productType.getId())){
                throw new BizException(Constants.PRODUCT_TYPE_EXIST);
            }
        }
        productType.setUpdateTime(LocalDateTime.now());
        Boolean flag = productTypeMapper.updateByPrimaryKeySelective(productType) < 0;
        return Optional.of(flag);
    }

    @Override
    public Optional<Boolean> delProductTypeById(Long productTypeId) {
        if (Objects.isNull(productTypeId)){
            return Optional.of(false);
        }
        ProductType productType = new ProductType();
        productType.setId(productTypeId);
        productType.setIsDeleted(IsDeleted.YES.code);
        productType.setUpdateTime(LocalDateTime.now());
        Example example = new Example(ProductType.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id",productType.getId())
                .andEqualTo("isDeleted",IsDeleted.NO.code);
        Boolean flag = productTypeMapper.updateByExampleSelective(productType,example) > 0;
        return Optional.of(flag);
    }

    @Override
    public Optional<PageInfo<ProductType>> pageProductTypeList(Integer page, Integer pageSize, ProductType productType) {
        Example example = Example.builder(ProductType.class).build();
        Example.Criteria criteria = example.createCriteria();
        if (!Objects.isNull(productType.getId())){
            criteria.andEqualTo("id",productType.getId());
        }
        if (!StringUtils.isEmpty(productType.getName())){
            criteria.andLike("name","%"+productType.getName()+"%");
        }
        if (!StringUtils.isEmpty(productType.getIsDeleted())){
            criteria.andEqualTo("isDeleted", productType.getIsDeleted());
        }
        if (!StringUtils.isEmpty(productType.getStart())){
            criteria.andGreaterThanOrEqualTo("createTime",productType.getStart());
        }
        if (!StringUtils.isEmpty(productType.getEnd())){
            criteria.andLessThanOrEqualTo("createTime",productType.getEnd());
        }
        List<ProductType> productTypes = productTypeMapper.selectByExampleAndRowBounds(example,new RowBounds(page*pageSize,pageSize));
        DataUtils.formatterDateForList(productTypes);
        int count = productTypeMapper.selectCountByExample(example);
        PageInfo<ProductType> productTypePageInfo = new PageInfo<>(productTypes);
        productTypePageInfo.setTotal(count);
        return Optional.of(productTypePageInfo);
    }

    @Override
    public Optional<ProductType> getProductTypeById(Long productTypeId) {
        if (Objects.isNull(productTypeId)){
            return Optional.empty();
        }
        ProductType productType = productTypeMapper.selectByPrimaryKey(productTypeId);
        return Optional.of(productType);
    }

    @Override
    public Optional<List<ProductType>> getProductTypeList() {
        List<ProductType> productTypes = productTypeMapper.selectAll();
        return Optional.of(productTypes);
    }
}
