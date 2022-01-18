package com.example.cacheservice.service;

import com.alibaba.fastjson.JSON;
import com.example.cacheservice.common.GolbalUtil;
import com.example.cacheservice.domain.UserEntity;
import com.example.cacheservice.dto.ResultDTO;
import com.example.cacheservice.repository.UserEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestTemplate;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * UserService
 *
 * @author yongqi yang
 * @date 2022/1/18
 */
@Service
@Slf4j
public class UserService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private UserEntityRepository userEntityRepository;
    @Resource
    private AsyncRestTemplate asyncRestTemplate;


    public ResultDTO<Object> getInfo(String username) {
        // 查询jvm缓存
        ConcurrentHashMap<String, UserEntity> userMap = GolbalUtil.getUserMap();
        UserEntity userEntity = userMap.get(username);
        if (null == userEntity){
            // jvm缓存为空查询redis缓存
            String userInfo = stringRedisTemplate.opsForValue().get(username);
            if (StringUtils.isBlank(userInfo)){
                // redis缓存为空 利用排他锁读取db
                Boolean result = stringRedisTemplate.opsForValue().setIfAbsent("lock:" + username, username, 3, TimeUnit.SECONDS);
                if (Boolean.TRUE.equals(result)){
                    try {
                        // 获取到锁后查询db
                        UserEntity userEntity1 = userEntityRepository.findByUsername(username).orElse(null);
                        stringRedisTemplate.opsForValue().set(username, JSON.toJSONString(userEntity1));
                        userMap.putIfAbsent(username, userEntity1);
                        return ResultDTO.builder().data(userEntity1).build();
                    } catch (Exception e) {
                        log.error("发生异常：{}", e.getMessage(), e);
                    }finally {
                        stringRedisTemplate.delete("lock:" + username);
                    }
                }else {
                    return ResultDTO.builder().code("500001").success(false).message("获取排他锁失败").build();
                }
            }else {
                return ResultDTO.builder().data(JSON.parseObject(userInfo)).build();
            }
        }else {
            return ResultDTO.builder().data(userEntity).build();
        }
        return ResultDTO.builder().code("400001").message("失败").success(false).build();
    }

    public ResultDTO<UserEntity> addUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setWidthKg(ThreadLocalRandom.current().nextInt(200));
        userEntity.setHigthCm(ThreadLocalRandom.current().nextInt(200));
        userEntity.setSex(false);
        userEntity.setPhone(UUID.randomUUID().toString().substring(0,11));
        userEntity.setAddress(UUID.randomUUID().toString());
        userEntity.setBirthday("1888-08-08");
        userEntity.setName("阿瓦达回复啊和覅u俄方");
        userEntity.setPassword("weofjoiwejhifhwejfwoifu2893928yfh29f2");
        userEntity.setUsername(UUID.randomUUID().toString());
        userEntityRepository.save(userEntity);
        return ResultDTO.<UserEntity>builder().data(userEntity).build();
    }

    public ResultDTO<Object> cache(String userName) throws Throwable {
        IntStream.range(0, 4).forEach(i -> {
            ListenableFuture<ResponseEntity<String>> forEntity = asyncRestTemplate.getForEntity("http://localhost:8111/user/get?username=" + userName, String.class);
            forEntity.addCallback(new ListenableFutureCallback<ResponseEntity<String>>() {
                @Override
                public void onFailure(Throwable ex) {
                    log.error(ex.getMessage());
                }

                @Override
                public void onSuccess(ResponseEntity<String> result) {
                    log.info(result.getBody());
                }
            });
        });

        return null;
    }
}
