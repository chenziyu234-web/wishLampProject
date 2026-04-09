package org.dromara.wishlamp.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.tenant.core.TenantEntity;

/**
 * 祝福参与记录 wish_entry (租户隔离)
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wish_entry")
public class WishEntry extends TenantEntity {

    @TableId(value = "entry_id")
    private Long entryId;

    /**
     * 关联活动实例ID
     */
    private Long instanceId;

    /**
     * 参与者姓名
     */
    private String participantName;

    /**
     * 祝福对象
     */
    private String toName;

    /**
     * 祝福语内容
     */
    private String message;

    /**
     * 卡片样式
     */
    private String cardStyle;

    /**
     * 参与者IP
     */
    private String ipAddress;

}
