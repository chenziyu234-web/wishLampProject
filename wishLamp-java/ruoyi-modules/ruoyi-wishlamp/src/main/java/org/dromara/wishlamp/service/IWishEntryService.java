package org.dromara.wishlamp.service;

import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.wishlamp.domain.bo.WishEntryBo;
import org.dromara.wishlamp.domain.vo.WishEntryVo;

import java.util.List;

/**
 * 祝福参与记录 服务层
 */
public interface IWishEntryService {

    TableDataInfo<WishEntryVo> queryPageList(WishEntryBo bo, PageQuery pageQuery);

    Long submitEntry(WishEntryBo bo);

    Boolean deleteByIds(List<Long> ids);

    long countTotal();

}
