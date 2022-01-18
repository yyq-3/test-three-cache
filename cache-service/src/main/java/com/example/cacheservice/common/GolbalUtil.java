package com.example.cacheservice.common;

import com.example.cacheservice.domain.UserEntity;

import java.util.concurrent.ConcurrentHashMap;

/**
 * GolbalUtil
 *
 * @author yongqi yang
 * @date 2022/1/18
 */
public class GolbalUtil {
    private GolbalUtil(){
        // do nothing
    }
    private static volatile ConcurrentHashMap<String, UserEntity> userMap;

    public static ConcurrentHashMap<String, UserEntity> getUserMap(){
        if (null == userMap){
            synchronized (GolbalUtil.class){
                if (null == userMap){
                    userMap = new ConcurrentHashMap<>();
                }
            }
        }
        return userMap;
    }
}
