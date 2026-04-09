package org.dromara.wishlamp.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.domain.R;
import org.dromara.common.web.core.BaseController;
import org.dromara.wishlamp.domain.bo.WishEntryBo;
import org.dromara.wishlamp.service.IWishEntryService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 祝福活动公开接口 (无需登录，供 Portal 活动页面调用)
 */
@SaIgnore
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/public/wish")
public class WishPublicController extends BaseController {

    private final IWishEntryService entryService;

    /**
     * 提交祝福记录
     */
    @PostMapping("/entry")
    public R<Long> submitEntry(@Validated @RequestBody WishEntryBo bo, HttpServletRequest request) {
        bo.setIpAddress(resolveClientIp(request));
        Long entryId = entryService.submitEntry(bo);
        return R.ok(entryId);
    }

    private String resolveClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isBlank() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isBlank() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }
        return ip;
    }

}
