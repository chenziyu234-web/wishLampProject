package org.dromara.wishlamp.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.wishlamp.domain.WishProduct;

/**
 * 活动产品业务对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = WishProduct.class, reverseConvertGenerate = false)
public class WishProductBo extends BaseEntity {

    private Long productId;

    @NotBlank(message = "产品名称不能为空")
    @Size(max = 100, message = "产品名称长度不能超过{max}个字符")
    private String name;

    @NotBlank(message = "URL标识不能为空")
    @Size(max = 50, message = "URL标识长度不能超过{max}个字符")
    private String slug;

    @NotBlank(message = "产品类型不能为空")
    private String type;

    private String description;

    private String iconUrl;

    private Integer enabled;

}
