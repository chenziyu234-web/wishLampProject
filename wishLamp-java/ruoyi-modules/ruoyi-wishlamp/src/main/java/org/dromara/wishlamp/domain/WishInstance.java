package org.dromara.wishlamp.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.tenant.core.TenantEntity;

import java.util.Date;

/**
 * 活动实例 wish_instance (租户隔离)
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wish_instance")
public class WishInstance extends TenantEntity {

    @TableId(value = "instance_id")
    private Long instanceId;

    /**
     * 关联产品ID
     */
    private Long productId;

    /**
     * 实例名称
     */
    private String name;

    /**
     * 状态: DRAFT/ACTIVE/PAUSED/ENDED
     */
    private String status;

    /**
     * 活动配置 (JSON)
     */
    private String config;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

}
