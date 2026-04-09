package org.dromara.wishlamp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.wishlamp.domain.WishEntry;
import org.dromara.wishlamp.domain.WishInstance;
import org.dromara.wishlamp.domain.bo.WishEntryBo;
import org.dromara.wishlamp.domain.vo.WishEntryVo;
import org.dromara.wishlamp.mapper.WishEntryMapper;
import org.dromara.wishlamp.mapper.WishInstanceMapper;
import org.dromara.wishlamp.service.IWishEntryService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 祝福参与记录 服务实现
 */
@RequiredArgsConstructor
@Service
public class WishEntryServiceImpl implements IWishEntryService {

    private final WishEntryMapper baseMapper;
    private final WishInstanceMapper instanceMapper;

    @Override
    public TableDataInfo<WishEntryVo> queryPageList(WishEntryBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<WishEntry> lqw = buildQueryWrapper(bo);
        Page<WishEntryVo> page = baseMapper.selectVoPage(pageQuery.build(), lqw);
        fillInstanceInfo(page.getRecords());
        return TableDataInfo.build(page);
    }

    @Override
    public Long submitEntry(WishEntryBo bo) {
        // Look up the instance to obtain tenant_id for proper tenant isolation
        WishInstance instance = instanceMapper.selectById(bo.getInstanceId());
        if (instance == null) {
            throw new ServiceException("活动实例不存在");
        }
        WishEntry entity = MapstructUtils.convert(bo, WishEntry.class);
        // Manually set tenant_id from the instance so the public submission
        // is stored under the correct tenant without requiring user login
        entity.setTenantId(instance.getTenantId());
        if (StringUtils.isBlank(entity.getCardStyle())) {
            entity.setCardStyle("default");
        }
        baseMapper.insert(entity);
        return entity.getEntryId();
    }

    @Override
    public Boolean deleteByIds(List<Long> ids) {
        return baseMapper.deleteByIds(ids) > 0;
    }

    @Override
    public long countTotal() {
        return baseMapper.selectCount(null);
    }

    private LambdaQueryWrapper<WishEntry> buildQueryWrapper(WishEntryBo bo) {
        LambdaQueryWrapper<WishEntry> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getInstanceId() != null, WishEntry::getInstanceId, bo.getInstanceId());
        lqw.like(StringUtils.isNotBlank(bo.getParticipantName()),
            WishEntry::getParticipantName, bo.getParticipantName());
        lqw.orderByDesc(WishEntry::getCreateTime);
        return lqw;
    }

    private void fillInstanceInfo(List<WishEntryVo> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        List<WishInstance> instances = instanceMapper.selectList(null);
        Map<Long, String> nameMap = new HashMap<>();
        for (WishInstance inst : instances) {
            nameMap.put(inst.getInstanceId(), inst.getName());
        }
        for (WishEntryVo vo : list) {
            vo.setInstanceName(nameMap.get(vo.getInstanceId()));
        }
    }

}
