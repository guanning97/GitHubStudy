package com.gn.study.shop.vo;

import lombok.Data;

/**
 * @author guanning
 * sku实体类
 */
@Data
public class Sku {
    /**
     * 商品id
     */
    private Integer skuId;
    /**
     * 单位:分
     */
    private Integer skuPrice;

}
