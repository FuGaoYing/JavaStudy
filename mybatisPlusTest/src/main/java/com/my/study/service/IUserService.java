package com.my.study.service;

import com.my.study.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author FGY
 * @since 2023-03-07
 */
public interface IUserService extends IService<User> {

    void test(User user);

}
