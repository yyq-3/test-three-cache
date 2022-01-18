package com.example.cacheservice.dto;

import lombok.*;

import java.io.Serializable;

/**
 * ResultDTO
 *
 * @author yongqi yang
 * @date 2022/1/18
 */
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultDTO<T> implements Serializable {
    @Builder.Default
    private String code = "200";
    private String message;
    private T data;
    @Builder.Default
    private Boolean success = true;
}
