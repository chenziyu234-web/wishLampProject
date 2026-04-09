package org.dromara.wishlamp.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.domain.R;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.web.core.BaseController;
import org.dromara.wishlamp.domain.bo.WishEntryBo;
import org.dromara.wishlamp.domain.vo.WishEntryVo;
import org.dromara.wishlamp.service.IWishEntryService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 祝福参与记录管理 (需要登录权限，供客户后台查询)
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wishlamp/entry")
public class WishEntryController extends BaseController {

    private final IWishEntryService entryService;

    /**
     * 查询祝福参与记录列表
     */
    @SaCheckPermission("wishlamp:entry:list")
    @GetMapping("/list")
    public TableDataInfo<WishEntryVo> list(WishEntryBo bo, PageQuery pageQuery) {
        return entryService.queryPageList(bo, pageQuery);
    }

    /**
     * 删除祝福参与记录
     */
    @SaCheckPermission("wishlamp:entry:remove")
    @Log(title = "祝福记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{entryIds}")
    public R<Void> remove(@PathVariable Long[] entryIds) {
        return toAjax(entryService.deleteByIds(Arrays.asList(entryIds)));
    }

}
