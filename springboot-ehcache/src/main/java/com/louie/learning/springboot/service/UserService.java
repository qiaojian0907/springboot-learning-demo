package com.louie.learning.springboot.service;

import com.louie.learning.springboot.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 一般情况下，我们在Sercive层进行对缓存的操作。先介绍 Ehcache 在 Spring 中的注解：在支持 Spring Cache 的环境下，
 *
 * @Cacheable : Spring在每次执行前都会检查Cache中是否存在相同key的缓存元素，如果存在就不再执行该方法，而是直接从缓存中获取结果进行返回，否则才会执行并将返回结果存入指定的缓存中。
 * @CacheEvict : 清除缓存。
 * @CachePut : @CachePut也可以声明一个方法支持缓存功能。使用@CachePut标注的方法在执行前不会去检查缓存中是否存在之前执行过的结果，而是每次都会执行该方法，并将执行结果以键值对的形式存入指定的缓存中。
 * 这三个方法中都有两个主要的属性：value 指的是 ehcache.xml 中的缓存策略空间；key 指的是缓存的标识，同时可以用 # 来引用参数。
 */
@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    //这里的单引号不能少，否则会报错，被识别是一个对象
    private static final String CACHE_KEY = "'user'";
    private static final String DEMO_CACHE_NAME = "users";

    public static Map<String, User> mapUser = new HashMap<>();

    //删除用户数据
    @CacheEvict(value = DEMO_CACHE_NAME, key = "'user_'+#uuid")//这是清除缓存
    public void delete(String uuid) {
        mapUser.remove(uuid);
    }

    //更新用户数据
    @CachePut(value = DEMO_CACHE_NAME, key = "'user_'+#user.getUuid()")
    public User update(User user) {
        User user1 = mapUser.get(user.getUuid());
        if (user1 != null) {
            user1.setAge(user.getAge());
            user1.setName(user.getName());
        }
        return user1;
    }

    //查找用户数据
    @Cacheable(value = DEMO_CACHE_NAME, key = "'user_'+#uuid")
    public User findByUuid(String uuid) {
        //若找不到缓存将打印出提示语句
        logger.info("没有走缓存！" + uuid);
        return mapUser.get(uuid);
    }

    //保存用户数据
    @CacheEvict(value = DEMO_CACHE_NAME, key = CACHE_KEY)
    public int save(User user) {
        user = mapUser.put(user.getUuid(), user);
        return user == null ? 1 : 0;
    }
}
