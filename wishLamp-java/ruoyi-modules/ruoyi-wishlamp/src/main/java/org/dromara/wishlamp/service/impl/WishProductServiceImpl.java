package org.dromara.wishlamp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.wishlamp.domain.WishProduct;
import org.dromara.wishlamp.domain.bo.WishProductBo;
import org.dromara.wishlamp.domain.vo.WishProductVo;
import org.dromara.wishlamp.mapper.WishProductMapper;
import org.dromara.wishlamp.service.IWishProductService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 活动产品 服务实现
 */
@RequiredArgsConstructor
@Service
public class WishProductServiceImpl implements IWishProductService {

    private final WishProductMapper baseMapper;

    @Override
    public TableDataInfo<WishProductVo> queryPageList(WishProductBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<WishProduct> lqw = buildQueryWrapper(bo);
        Page<WishProductVo> page = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(page);
    }

    @Override
    public List<WishProductVo> queryList(WishProductBo bo) {
        LambdaQueryWrapper<WishProduct> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    @Override
    public WishProductVo queryById(Long productId) {
        return baseMapper.selectVoById(productId);
    }

    @Override
    public Boolean insertByBo(WishProductBo bo) {
        WishProduct entity = MapstructUtils.convert(bo, WishProduct.class);
        if (entity.getEnabled() == null) {
            entity.setEnabled(1);
        }
        return baseMapper.insert(entity) > 0;
    }

    @Override
    public Boolean updateByBo(WishProductBo bo) {
        WishProduct entity = MapstructUtils.convert(bo, WishProduct.class);
        return baseMapper.updateById(entity) > 0;
    }

    @Override
    public Boolean deleteByIds(List<Long> ids) {
        return baseMapper.deleteByIds(ids) > 0;
    }

    private LambdaQueryWrapper<WishProduct> buildQueryWrapper(WishProductBo bo) {
        LambdaQueryWrapper<WishProduct> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getName()), WishProduct::getName, bo.getName());
        lqw.eq(StringUtils.isNotBlank(bo.getType()), WishProduct::getType, bo.getType());
        lqw.eq(StringUtils.isNotBlank(bo.getSlug()), WishProduct::getSlug, bo.getSlug());
        lqw.eq(bo.getEnabled() != null, WishProduct::getEnabled, bo.getEnabled());
        lqw.orderByDesc(WishProduct::getCreateTime);
        return lqw;
    }

}
