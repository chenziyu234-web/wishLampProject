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
import org.dromara.wishlamp.domain.bo.WishProductBo;
import org.dromara.wishlamp.domain.vo.WishProductVo;
import org.dromara.wishlamp.service.IWishProductService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 活动产品管理
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/wishlamp/product")
public class WishProductController extends BaseController {

    private final IWishProductService productService;

    /**
     * 查询活动产品列表
     */
    @SaCheckPermission("wishlamp:product:list")
    @GetMapping("/list")
    public TableDataInfo<WishProductVo> list(WishProductBo bo, PageQuery pageQuery) {
        return productService.queryPageList(bo, pageQuery);
    }

    /**
     * 查询全部活动产品 (下拉选择用)
     */
    @GetMapping("/listAll")
    public R<List<WishProductVo>> listAll(WishProductBo bo) {
        return R.ok(productService.queryList(bo));
    }

    /**
     * 获取活动产品详细信息
     */
    @SaCheckPermission("wishlamp:product:query")
    @GetMapping("/{productId}")
    public R<WishProductVo> getInfo(@PathVariable Long productId) {
        return R.ok(productService.queryById(productId));
    }

    /**
     * 新增活动产品
     */
    @SaCheckPermission("wishlamp:product:add")
    @Log(title = "活动产品", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping
    public R<Void> add(@Validated @RequestBody WishProductBo bo) {
        return toAjax(productService.insertByBo(bo));
    }

    /**
     * 修改活动产品
     */
    @SaCheckPermission("wishlamp:product:edit")
    @Log(title = "活动产品", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping
    public R<Void> edit(@Validated @RequestBody WishProductBo bo) {
        return toAjax(productService.updateByBo(bo));
    }

    /**
     * 删除活动产品
     */
    @SaCheckPermission("wishlamp:product:remove")
    @Log(title = "活动产品", businessType = BusinessType.DELETE)
    @DeleteMapping("/{productIds}")
    public R<Void> remove(@PathVariable Long[] productIds) {
        return toAjax(productService.deleteByIds(Arrays.asList(productIds)));
    }

}
