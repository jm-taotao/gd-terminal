package com.terminal.manage.tool;

import com.terminal.manage.model.Base;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

/**
 * @author TAO
 * @date 2022/7/16 / 13:13
 */
public class DataUtils {

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    public static void formatterDateForList(List<? extends Base> list){
        list.forEach(v->{
            if (Objects.nonNull(v.getCreateTime())){
                v.setCreateTimeStr(dtf.format(v.getCreateTime()));
            }
            if (Objects.nonNull(v.getUpdateTime())){
                v.setUpdateTimeStr(dtf.format(v.getUpdateTime()));
            }
        });
    }
}
