package com.cj.framework.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public  class RespPageBean {


    private Long total;

    private List<?> data;
}
