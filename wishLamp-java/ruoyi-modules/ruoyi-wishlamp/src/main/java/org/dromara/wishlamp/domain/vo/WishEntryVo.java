package org.dromara.wishlamp.domain.vo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.dromara.wishlamp.domain.WishEntry;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 祝福参与记录视图对象
 */
@Data
@AutoMapper(target = WishEntry.class)
public class WishEntryVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long entryId;

    private Long instanceId;

    private String participantName;

    private String toName;

    private String message;

    private String cardStyle;

    private String ipAddress;

    private Date createTime;

    /**
     * 关联活动实例名称 (翻译字段)
     */
    private String instanceName;

}
