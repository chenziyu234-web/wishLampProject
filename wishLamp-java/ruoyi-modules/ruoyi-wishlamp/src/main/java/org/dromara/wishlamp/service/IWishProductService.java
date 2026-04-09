package org.dromara.wishlamp.service;

import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.wishlamp.domain.bo.WishProductBo;
import org.dromara.wishlamp.domain.vo.WishProductVo;

import java.util.List;

/**
 * 活动产品 服务层
 */
public interface IWishProductService {

    TableDataInfo<WishProductVo> queryPageList(WishProductBo bo, PageQuery pageQuery);

    List<WishProductVo> queryList(WishProductBo bo);

    WishProductVo queryById(Long productId);

    Boolean insertByBo(WishProductBo bo);

    Boolean updateByBo(WishProductBo bo);

    Boolean deleteByIds(List<Long> ids);

}
