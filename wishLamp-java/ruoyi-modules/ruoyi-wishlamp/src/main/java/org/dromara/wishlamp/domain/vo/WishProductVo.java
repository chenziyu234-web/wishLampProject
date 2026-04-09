package org.dromara.wishlamp.domain.vo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.dromara.wishlamp.domain.WishProduct;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 活动产品视图对象
 */
@Data
@AutoMapper(target = WishProduct.class)
public class WishProductVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long productId;

    private String name;

    private String slug;

    private String type;

    private String description;

    private String iconUrl;

    private Integer enabled;

    private Date createTime;

    private Date updateTime;

}
