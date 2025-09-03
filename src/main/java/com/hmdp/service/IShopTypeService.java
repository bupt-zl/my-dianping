package com.hmdp.service;

import com.hmdp.dto.Result;
import com.hmdp.entity.ShopType;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 龙哥
 * @since 2025-01-22
 */
public interface IShopTypeService extends IService<ShopType> {

    Result queryType();
}
