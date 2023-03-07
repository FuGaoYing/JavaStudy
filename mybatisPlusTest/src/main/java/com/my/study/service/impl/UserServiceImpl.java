package com.my.study.service.impl;

import com.my.study.entity.User;
import com.my.study.mapper.UserMapper;
import com.my.study.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * <p>
 *  服务实现类
 * </p>
 *          Transactional注解参数
 *              1. propagation 传播行为
 *                  ① Propagation.REQUIRED 默认 如果当前有事务，其他就用当前事务，不会新增事务。
 *                  ② Propagation.REQUIRES_NEW 如果当前有事务，其他不会加入当前事务，会新增事务。即他们的事务没有关系，不是同一个事务。
 *                  ③ Propagation.SUPPORTS 当前没有事务，就以非事务运行。当前有事务呢？就以当前事务运行。
 *                  ④ Propagation.NOT_SUPPORTED 以非事务执行。如果有将他挂起
 *                  ⑤ Propagation.MANDATORY 当前方法必须运行在事物中，如果没有抛异常
 *                  ⑥ Propagation.NEVER    当前方法不应该运行在事务中，如果有抛异常
 *                  ⑦ Propagation.NESTED    如果有事务在运行，在当前事务的嵌套事务内运行，否则启动新的事务运行。
*               2. 隔离级别
 *                  ①   READ_COMMITTED  读已提交
 *                  ②   READ_UNCOMMITTED  读未提交
 *                  ③   REPEATABLE_READ  可重复读
 *                  ④   SERIALIZABLE    串行化
 *              3. timeout 超时时间：事务在规定时间内提交，否则回滚
 *              4. readOnly 是否只读
 *              5. rollbackFor 回滚 设置出现那些异常时回滚
 *              6. noRollbackFor  不回滚
 *
 * @author FGY
 * @since 2023-03-07
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

//    @Transactional(isolation = Isolation.READ_UNCOMMITTED
//            ,rollbackFor = Exception.class)

    /**
     *  编程式事务
     */
    @Autowired
    TransactionTemplate template;

    @Override
    public void test(User user) {
        Integer execute = template.execute(status -> {
            baseMapper.updateById(user);
            user.setVersion(2);
            int i = baseMapper.updateById(user);
            return i;
        });
        System.out.println(execute);

    }
}
