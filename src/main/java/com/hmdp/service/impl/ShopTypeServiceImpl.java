package com.hmdp.service.impl;

import cn.hutool.json.JSONUtil;
import com.hmdp.dto.Result;
import com.hmdp.entity.ShopType;
import com.hmdp.mapper.ShopTypeMapper;
import com.hmdp.service.IShopTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.hmdp.utils.RedisConstants.SHOP_TYPE_LIST;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 龙哥
 * @since 2025-01-22
 */
@Service
public class ShopTypeServiceImpl extends ServiceImpl<ShopTypeMapper, ShopType> implements IShopTypeService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result queryType() {
        String key = SHOP_TYPE_LIST;
        // 1. 在redis中查询
        List<String> shopTypeList = stringRedisTemplate.opsForList().range(key,0,-1);
        // 2.检查是否存在
        if(!shopTypeList.isEmpty()){
            // 3.存在返回
            List<ShopType> shopTypes = new ArrayList<>();
            for(String shopType:shopTypeList){
                shopTypes.add(JSONUtil.toBean(shopType, ShopType.class));
            }
            return Result.ok(shopTypes);
        }
        // 4. 缓存中不存在，在数据库中查找
        List<ShopType> typeList = query().orderByAsc("sort").list();
        // 5. 数据库中不存在，返回
        if(typeList.isEmpty()){
            return Result.fail("未找到商户类型");
        }
        // 6. 存在保存在redis
        for(ShopType shopType:typeList){
            shopTypeList.add(JSONUtil.toJsonStr(shopType));
        }
        stringRedisTemplate.opsForList().rightPushAll(key, shopTypeList);
        // 7. 返回
        return Result.ok(typeList);
    }
}
