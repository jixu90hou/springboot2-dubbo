package com.winterchen.service.user;

import com.winterchen.model.UserDomain;

import java.util.List;

/**
 * Created by Administrator on 2018/4/19.
 */
public interface IUserService {

    int addUser(UserDomain user);

    List<UserDomain> findAllUser(int pageNum, int pageSize);
}
