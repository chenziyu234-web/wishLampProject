package org.dromara.wishlamp.domain.vo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.dromara.wishlamp.domain.WishInstance;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 活动实例视图对象
 */
@Data
@AutoMapper(target = WishInstance.class)
public class WishInstanceVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long instanceId;

    private Long productId;

    private String name;

    private String status;

    private String config;

    private Date startTime;

    private Date endTime;

    private Date createTime;

    private Date updateTime;

    /**
     * 关联产品名称 (翻译字段)
     */
    private String productName;

    /**
     * 关联产品类型
     */
    private String productType;

}
