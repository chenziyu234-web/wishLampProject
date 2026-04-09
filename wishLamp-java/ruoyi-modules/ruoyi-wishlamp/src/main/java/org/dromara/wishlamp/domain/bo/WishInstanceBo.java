package org.dromara.wishlamp.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.wishlamp.domain.WishInstance;

import java.util.Date;

/**
 * 活动实例业务对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = WishInstance.class, reverseConvertGenerate = false)
public class WishInstanceBo extends BaseEntity {

    private Long instanceId;

    @NotNull(message = "产品ID不能为空")
    private Long productId;

    @NotBlank(message = "实例名称不能为空")
    private String name;

    private String status;

    private String config;

    private Date startTime;

    private Date endTime;

}
