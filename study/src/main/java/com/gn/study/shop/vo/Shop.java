package com.gn.study.shop.vo;

import lombok.Data;

import java.util.Map;

/**
 * @author guanning
 * 商品实体类
 */
@Data
public class Shop {
    /**
     * 商店id
     */
    private Integer shopId;
    /**
     * 商店名称
     */
    private String shopName;
    /**
     * 商品及库存
     */
    private volatile Map<Sku,Integer> skuStock;
}
