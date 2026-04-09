package org.dromara.wishlamp.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.mybatis.core.domain.BaseEntity;

/**
 * 活动产品 wish_product
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wish_product")
public class WishProduct extends BaseEntity {

    @TableId(value = "product_id")
    private Long productId;

    /**
     * 产品名称
     */
    private String name;

    /**
     * URL 标识
     */
    private String slug;

    /**
     * 类型: BLESSING/RIDDLE
     */
    private String type;

    /**
     * 描述
     */
    private String description;

    /**
     * 图标地址
     */
    private String iconUrl;

    /**
     * 是否启用
     */
    private Integer enabled;

}
