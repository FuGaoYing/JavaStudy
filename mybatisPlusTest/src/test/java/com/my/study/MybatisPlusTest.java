package com.my.study;

import com.my.study.entity.User;
import com.my.study.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author: FGY
 * @Description:
 * @Date: Created in 2023/3/7 18:25
 * @Version:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MybatisPlusTest {

    @Autowired
    private IUserService userService;

    @Test
    public void test1() {
        List<User> list = userService.list();
        System.out.println(list);
    }
    @Test
    public void test2() {
        User user = new User().setId(1L).setAge(18).setName("John").setVersion(1).setEmail("john@example.com");
        userService.test(user);
    }
}
