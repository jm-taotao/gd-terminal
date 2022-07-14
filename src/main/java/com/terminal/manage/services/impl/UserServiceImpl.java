package com.terminal.manage.services.impl;

import cn.hutool.crypto.digest.MD5;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.terminal.manage.base.enums.Constants;
import com.terminal.manage.base.enums.IsDeleted;
import com.terminal.manage.base.excption.BizException;
import com.terminal.manage.mapper.UserMapper;
import com.terminal.manage.model.User;
import com.terminal.manage.services.UserService;
import javafx.scene.input.DataFormat;
import org.apache.ibatis.session.RowBounds;
import org.apache.tomcat.jni.Local;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Jyt
 * @date 2021/9/27
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public Optional<User> login(String loginName, String password) {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isEmpty(loginName) || StringUtils.isEmpty(password)){
            return Optional.empty();
        }
        criteria.andEqualTo("loginName",loginName);
        int count = userMapper.selectCountByExample(example);
        if (count<=0){
            throw  new BizException(Constants.LOGIN_FAILED_NOT_EXIST);
        }
        criteria.andEqualTo("password", MD5.create().digestHex(password).toUpperCase());
        User user = userMapper.selectOneByExample(example);
        if (Objects.isNull(user)){
            throw  new BizException(Constants.LOGIN_FAILED_PASSWORD_MISTAKE);
        }
        return Optional.of(user);
    }

    @Override
    public Optional<Boolean> addUser(User user) {

        if (!StringUtils.isEmpty(user.getLoginName())){
            Example example = new Example(User.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("loginName",user.getLoginName());
            int count = userMapper.selectCountByExample(example);
            if (count>0){
                throw new BizException(Constants.USER_EXIST);
            }
        }
        user.setIsDeleted(IsDeleted.NO.code);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        if (StringUtils.isEmpty(user.getPassword())){
            throw new BizException(Constants.USER_PASSWORD_ISEMPTY);
        }
        user.setPassword(MD5.create().digestHex(user.getPassword()).toLowerCase());
        Boolean flag = userMapper.insertSelective(user) > 0;
        return Optional.of(flag);
    }

    @Override
    public Optional<Boolean> updateUser(User user) {
        if (!StringUtils.isEmpty(user.getLoginName())){
            Example example = new Example(User.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("loginName",user.getLoginName());
            User userOld = userMapper.selectOneByExample(example);
            if (Objects.nonNull(userOld) && !userOld.getId().equals(user.getId())){
                throw new BizException(Constants.USER_EXIST);
            }
        }
        user.setUpdateTime(LocalDateTime.now());
        Boolean flag = userMapper.updateByPrimaryKeySelective(user) < 0;
        return Optional.of(flag);
    }

    @Override
    public Optional<Boolean> delUserById(Long userId) {
        if (Objects.isNull(userId)){
            return Optional.of(false);
        }
        User user = new User();
        user.setId(userId);
        user.setIsDeleted(IsDeleted.YES.code);
        user.setUpdateTime(LocalDateTime.now());
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id",user.getId())
                .andEqualTo("isDeleted",IsDeleted.NO.code);
        Boolean flag = userMapper.updateByExampleSelective(user,example) > 0;
        return Optional.of(flag);
    }

    @Override
    public Optional<PageInfo<User>> pageUserList(Integer page, Integer pageSize, User user) {
        Example example = Example.builder(User.class).build();
        Example.Criteria criteria = example.createCriteria();
        if (!Objects.isNull(user.getId())){
            criteria.andEqualTo("id",user.getId());
        }
        if (!StringUtils.isEmpty(user.getLoginName())){
            criteria.andLike("loginName","%"+user.getLoginName()+"%");
        }
//        DateTimeFormatter dft = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (!StringUtils.isEmpty(user.getStart())){
            criteria.andGreaterThanOrEqualTo("createTime",user.getStart());
        }
        if (!StringUtils.isEmpty(user.getEnd())){
            criteria.andLessThanOrEqualTo("createTime",user.getEnd());
        }

//        criteria.andEqualTo("isDeleted", IsDeleted.NO.code);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<User> users = userMapper.selectByExampleAndRowBounds(example,new RowBounds(page*pageSize,pageSize)).stream().peek(v->{
            if (Objects.nonNull(v.getCreateTime())){
                v.setCreateTimeStr(dtf.format(v.getCreateTime()));
            }
            if (Objects.nonNull(v.getUpdateTime())){
                v.setUpdateTimeStr(dtf.format(v.getUpdateTime()));
            }
        }).collect(Collectors.toList());
        int count = userMapper.selectCountByExample(example);
        PageInfo<User> userPageInfo = new PageInfo<>(users);
        userPageInfo.setTotal(count);
        return Optional.of(userPageInfo);
    }

    @Override
    public Optional<User> getUserById(Long userId) {
        if (Objects.isNull(userId)){
            return Optional.empty();
        }
        User user = userMapper.selectByPrimaryKey(userId);
        return Optional.of(user);
    }
}
