package org.dromara.wishlamp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.wishlamp.domain.WishInstance;
import org.dromara.wishlamp.domain.WishProduct;
import org.dromara.wishlamp.domain.bo.WishInstanceBo;
import org.dromara.wishlamp.domain.vo.WishInstanceVo;
import org.dromara.wishlamp.mapper.WishEntryMapper;
import org.dromara.wishlamp.mapper.WishInstanceMapper;
import org.dromara.wishlamp.mapper.WishProductMapper;
import org.dromara.wishlamp.service.IWishInstanceService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 活动实例 服务实现
 */
@RequiredArgsConstructor
@Service
public class WishInstanceServiceImpl implements IWishInstanceService {

    private final WishInstanceMapper baseMapper;
    private final WishProductMapper productMapper;
    private final WishEntryMapper entryMapper;

    @Override
    public TableDataInfo<WishInstanceVo> queryPageList(WishInstanceBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<WishInstance> lqw = buildQueryWrapper(bo);
        Page<WishInstanceVo> page = baseMapper.selectVoPage(pageQuery.build(), lqw);
        fillProductInfo(page.getRecords());
        return TableDataInfo.build(page);
    }

    @Override
    public List<WishInstanceVo> queryList(WishInstanceBo bo) {
        LambdaQueryWrapper<WishInstance> lqw = buildQueryWrapper(bo);
        List<WishInstanceVo> list = baseMapper.selectVoList(lqw);
        fillProductInfo(list);
        return list;
    }

    @Override
    public WishInstanceVo queryById(Long instanceId) {
        WishInstanceVo vo = baseMapper.selectVoById(instanceId);
        if (vo != null && vo.getProductId() != null) {
            WishProduct product = productMapper.selectById(vo.getProductId());
            if (product != null) {
                vo.setProductName(product.getName());
                vo.setProductType(product.getType());
            }
        }
        return vo;
    }

    @Override
    public Boolean insertByBo(WishInstanceBo bo) {
        WishInstance entity = MapstructUtils.convert(bo, WishInstance.class);
        if (StringUtils.isBlank(entity.getStatus())) {
            entity.setStatus("DRAFT");
        }
        return baseMapper.insert(entity) > 0;
    }

    @Override
    public Boolean updateByBo(WishInstanceBo bo) {
        WishInstance entity = MapstructUtils.convert(bo, WishInstance.class);
        return baseMapper.updateById(entity) > 0;
    }

    @Override
    public Boolean deleteByIds(List<Long> ids) {
        return baseMapper.deleteByIds(ids) > 0;
    }

    @Override
    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalInstances", baseMapper.selectCount(null));
        stats.put("activeInstances", baseMapper.selectCount(
            Wrappers.lambdaQuery(WishInstance.class).eq(WishInstance::getStatus, "ACTIVE")));
        stats.put("totalProducts", productMapper.selectCount(null));
        stats.put("totalEntries", entryMapper.selectCount(null));
        return stats;
    }

    private LambdaQueryWrapper<WishInstance> buildQueryWrapper(WishInstanceBo bo) {
        LambdaQueryWrapper<WishInstance> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getName()), WishInstance::getName, bo.getName());
        lqw.eq(StringUtils.isNotBlank(bo.getStatus()), WishInstance::getStatus, bo.getStatus());
        lqw.eq(bo.getProductId() != null, WishInstance::getProductId, bo.getProductId());
        lqw.orderByDesc(WishInstance::getCreateTime);
        return lqw;
    }

    private void fillProductInfo(List<WishInstanceVo> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        List<WishProduct> products = productMapper.selectList(null);
        Map<Long, WishProduct> productMap = new HashMap<>();
        for (WishProduct p : products) {
            productMap.put(p.getProductId(), p);
        }
        for (WishInstanceVo vo : list) {
            WishProduct p = productMap.get(vo.getProductId());
            if (p != null) {
                vo.setProductName(p.getName());
                vo.setProductType(p.getType());
            }
        }
    }

}
