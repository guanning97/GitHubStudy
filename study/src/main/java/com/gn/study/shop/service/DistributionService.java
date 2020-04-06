package com.gn.study.shop.service;

import com.gn.study.shop.vo.Shop;
import com.gn.study.shop.vo.Sku;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author guanning
 * 配送服务
 */
@Service
@Slf4j
public class DistributionService {

    /**
     * 店铺集合,假设已初始化完成全部数据
     */
    private List<Shop> shopList;


    /**
     * 获取配送计划,只做计划,不做库存扣减操作
     * @param reqSku 需要配送的商品及数量
     * @return 承载配送的店铺以及店铺对应的商品结合
     */
    private Map<Shop, List<Sku>> getDistributionPlan(Map<Sku, Integer> reqSku) {
        try {
            //初始化配送结果
            Map<Shop, List<Sku>> diDistributionPlanMap = new HashMap<>(10);

            //剩余需配送商品
            Map<Sku, Integer> restSku = new HashMap<>(reqSku);

            while (restSku.size() > 0){
                //获取包含最多商品的店铺
                Map.Entry<Shop, List<Sku>> shopWithSku = getMostSkuShop(restSku);

                //加入到配送计划中
                diDistributionPlanMap.put(shopWithSku.getKey(),shopWithSku.getValue());

                //去除需求商品中已确定配送店铺的商品
                shopWithSku.getValue().forEach(sku -> restSku.remove(sku));
            }

            return diDistributionPlanMap;
        } catch (Exception ex) {
            log.error("配送计算错误", ex);

            //异常监控
            return null;
        }
    }

    /**
     * 获取满足最多商品的店铺及对应商品
     * @param reqSkuMap 需要配送的商品及数量
     * @return  店铺及对应商品
     */
    private Map.Entry<Shop, List<Sku>> getMostSkuShop(Map<Sku, Integer> reqSkuMap) {

        //每个店铺对于这次购物,所支持的商品集合
        Map<Shop, List<Sku>> matchSkuMap = new LinkedHashMap<>();

        //计算每个店铺满足配送需求的商品列表
        shopList.forEach(shop ->
                shop.getSkuStock().entrySet().forEach(shopSku -> {
                    if (reqSkuMap.keySet().contains(shopSku.getKey())
                            && reqSkuMap.get(shopSku.getKey()) < shopSku.getValue()) {

                        //符合条件的店铺及商品加入到结果集
                        if (matchSkuMap.containsKey(shop)) {
                            matchSkuMap.get(shop).add(shopSku.getKey());
                        } else {
                            List<Sku> skuList = new ArrayList<>();
                            skuList.add(shopSku.getKey());
                            matchSkuMap.put(shop, skuList);
                        }
                    }
                }));

        //获取满足商品最多的店铺及对应商品
        return matchSkuMap.entrySet().stream().sorted(Comparator.comparing(sku -> sku.getValue().size())).iterator().next();
    }


}
