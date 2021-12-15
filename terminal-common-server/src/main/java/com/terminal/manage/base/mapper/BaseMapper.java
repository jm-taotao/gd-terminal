package com.terminal.manage.base.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;
import tk.mybatis.mapper.common.special.InsertUseGeneratedKeysMapper;

/**
 * @author Jyt
 * @date 2021/9/26
 */
public interface BaseMapper<T> extends Mapper<T>, InsertListMapper<T>, InsertUseGeneratedKeysMapper<T> {
}
