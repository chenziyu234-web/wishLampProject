package org.dromara.wishlamp.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.domain.R;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.web.core.BaseController;
import org.dromara.wishlamp.domain.bo.WishInstanceBo;
import org.dromara.wishlamp.domain.vo.WishInstanceVo;
import org.dromara.wishlamp.service.IWishInstanceService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * 活动实例管理
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wishlamp/instance")
public class WishInstanceController extends BaseController {

    private final IWishInstanceService instanceService;

    /**
     * 查询活动实例列表
     */
    @SaCheckPermission("wishlamp:instance:list")
    @GetMapping("/list")
    public TableDataInfo<WishInstanceVo> list(WishInstanceBo bo, PageQuery pageQuery) {
        return instanceService.queryPageList(bo, pageQuery);
    }

    /**
     * 获取活动实例详细信息
     */
    @SaCheckPermission("wishlamp:instance:query")
    @GetMapping("/{instanceId}")
    public R<WishInstanceVo> getInfo(@PathVariable Long instanceId) {
        return R.ok(instanceService.queryById(instanceId));
    }

    /**
     * 开通活动实例
     */
    @SaCheckPermission("wishlamp:instance:add")
    @Log(title = "活动实例", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping
    public R<Void> add(@Validated @RequestBody WishInstanceBo bo) {
        return toAjax(instanceService.insertByBo(bo));
    }

    /**
     * 修改活动实例
     */
    @SaCheckPermission("wishlamp:instance:edit")
    @Log(title = "活动实例", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping
    public R<Void> edit(@Validated @RequestBody WishInstanceBo bo) {
        return toAjax(instanceService.updateByBo(bo));
    }

    /**
     * 删除活动实例
     */
    @SaCheckPermission("wishlamp:instance:remove")
    @Log(title = "活动实例", businessType = BusinessType.DELETE)
    @DeleteMapping("/{instanceIds}")
    public R<Void> remove(@PathVariable Long[] instanceIds) {
        return toAjax(instanceService.deleteByIds(Arrays.asList(instanceIds)));
    }

    /**
     * 获取平台统计数据 (Dashboard)
     */
    @SaCheckPermission("wishlamp:dashboard:list")
    @GetMapping("/stats")
    public R<Map<String, Object>> stats() {
        return R.ok(instanceService.getStats());
    }

}
