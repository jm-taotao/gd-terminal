package com.terminal.manage.mapper;

import com.terminal.manage.base.mapper.BaseMapper;
import com.terminal.manage.model.Menu;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author Jyt
 * @date 2021/9/27
 */
public interface MenuMapper extends BaseMapper<Menu> {

    @Update("update terminal_menu set sno=sno-1 where sno > #{start} and sno <= #{end}")
    int updateMenuSnoUp(Long id, Integer start, Integer end);


    @Update("update terminal_menu set sno=sno+1 where sno >= #{start} and sno < #{end}")
    int updateMenuSnoDown(Long id, Integer start, Integer end);

}
