package org.dromara.wishlamp.service;

import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.wishlamp.domain.bo.WishInstanceBo;
import org.dromara.wishlamp.domain.vo.WishInstanceVo;

import java.util.List;
import java.util.Map;

/**
 * 活动实例 服务层
 */
public interface IWishInstanceService {

    TableDataInfo<WishInstanceVo> queryPageList(WishInstanceBo bo, PageQuery pageQuery);

    List<WishInstanceVo> queryList(WishInstanceBo bo);

    WishInstanceVo queryById(Long instanceId);

    Boolean insertByBo(WishInstanceBo bo);

    Boolean updateByBo(WishInstanceBo bo);

    Boolean deleteByIds(List<Long> ids);

    Map<String, Object> getStats();

}
