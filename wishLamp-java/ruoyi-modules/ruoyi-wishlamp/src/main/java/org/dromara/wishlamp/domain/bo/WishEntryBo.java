package org.dromara.wishlamp.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.wishlamp.domain.WishEntry;

/**
 * 祝福参与记录业务对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = WishEntry.class, reverseConvertGenerate = false)
public class WishEntryBo extends BaseEntity {

    @NotNull(message = "活动实例ID不能为空")
    private Long instanceId;

    private String participantName;

    private String toName;

    private String message;

    private String cardStyle;

    private String ipAddress;

}
