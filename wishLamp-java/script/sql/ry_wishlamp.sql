-- ----------------------------
-- WishLamp 活动平台业务表
-- ----------------------------

-- ----------------------------
-- 活动产品表 (全局表，不做租户隔离)
-- ----------------------------
DROP TABLE IF EXISTS wish_product;
CREATE TABLE wish_product (
    product_id   BIGINT(20)    NOT NULL            COMMENT '产品ID',
    name         VARCHAR(100)  NOT NULL            COMMENT '产品名称',
    slug         VARCHAR(50)   NOT NULL            COMMENT 'URL标识',
    type         VARCHAR(20)   NOT NULL            COMMENT '类型(BLESSING=灯笼祈福,RIDDLE=猜灯谜)',
    description  VARCHAR(500)  DEFAULT NULL        COMMENT '产品描述',
    icon_url     VARCHAR(255)  DEFAULT NULL        COMMENT '图标地址',
    enabled      TINYINT(1)    DEFAULT 1           COMMENT '是否启用(1=启用,0=禁用)',
    create_dept  BIGINT(20)    DEFAULT NULL        COMMENT '创建部门',
    create_by    BIGINT(20)    DEFAULT NULL        COMMENT '创建者',
    create_time  DATETIME      DEFAULT NULL        COMMENT '创建时间',
    update_by    BIGINT(20)    DEFAULT NULL        COMMENT '更新者',
    update_time  DATETIME      DEFAULT NULL        COMMENT '更新时间',
    PRIMARY KEY (product_id)
) ENGINE=InnoDB COMMENT='活动产品';

-- ----------------------------
-- 活动实例表 (租户隔离，自动注入 tenant_id)
-- ----------------------------
DROP TABLE IF EXISTS wish_instance;
CREATE TABLE wish_instance (
    instance_id  BIGINT(20)    NOT NULL            COMMENT '实例ID',
    tenant_id    VARCHAR(20)   NOT NULL            COMMENT '租户编号',
    product_id   BIGINT(20)    NOT NULL            COMMENT '关联产品ID',
    name         VARCHAR(100)  NOT NULL            COMMENT '实例名称',
    status       VARCHAR(20)   DEFAULT 'DRAFT'     COMMENT '状态(DRAFT=草稿,ACTIVE=进行中,PAUSED=已暂停,ENDED=已结束)',
    config       JSON          DEFAULT NULL        COMMENT '活动配置(JSON)',
    start_time   DATETIME      DEFAULT NULL        COMMENT '开始时间',
    end_time     DATETIME      DEFAULT NULL        COMMENT '结束时间',
    create_dept  BIGINT(20)    DEFAULT NULL        COMMENT '创建部门',
    create_by    BIGINT(20)    DEFAULT NULL        COMMENT '创建者',
    create_time  DATETIME      DEFAULT NULL        COMMENT '创建时间',
    update_by    BIGINT(20)    DEFAULT NULL        COMMENT '更新者',
    update_time  DATETIME      DEFAULT NULL        COMMENT '更新时间',
    PRIMARY KEY (instance_id)
) ENGINE=InnoDB COMMENT='活动实例';

-- ----------------------------
-- 初始化-活动产品数据
-- ----------------------------
INSERT INTO wish_product VALUES (1, '灯笼祈福', 'blessing', 'BLESSING', '传统灯笼祈福互动活动，用户可以在线点灯、许愿、祈福', NULL, 1, 103, 1, SYSDATE(), 1, SYSDATE());
INSERT INTO wish_product VALUES (2, '猜灯谜',   'riddle',   'RIDDLE',   '趣味猜灯谜活动，支持自定义题目、多种难度设置', NULL, 1, 103, 1, SYSDATE(), 1, SYSDATE());

-- ----------------------------
-- 菜单 SQL — WishLamp 业务菜单
-- 列顺序: menu_id, menu_name, parent_id, order_num, path, component, query_param,
--         is_frame, is_cache, menu_type, visible, status, perms, icon,
--         create_dept, create_by, create_time, update_by, update_time, remark
-- ----------------------------

-- 一级菜单: WishLamp管理
INSERT INTO sys_menu VALUES (5000, 'WishLamp管理', 0, 6, 'wishlamp', NULL, '', 1, 0, 'M', '0', '0', '', 'star', 103, 1, SYSDATE(), NULL, NULL, '活动平台管理菜单');

-- 二级菜单: 数据概览
INSERT INTO sys_menu VALUES (5001, '数据概览', 5000, 1, 'dashboard', 'wishlamp/dashboard/index', '', 1, 0, 'C', '0', '0', 'wishlamp:dashboard:list', 'monitor', 103, 1, SYSDATE(), NULL, NULL, 'WishLamp数据概览');

-- 二级菜单: 活动产品
INSERT INTO sys_menu VALUES (5010, '活动产品', 5000, 2, 'product', 'wishlamp/product/index', '', 1, 0, 'C', '0', '0', 'wishlamp:product:list', 'shopping', 103, 1, SYSDATE(), NULL, NULL, '活动产品管理');

-- 活动产品按钮
INSERT INTO sys_menu VALUES (5011, '产品查询', 5010, 1, '#', '', '', 1, 0, 'F', '0', '0', 'wishlamp:product:query',  '#', 103, 1, SYSDATE(), NULL, NULL, '');
INSERT INTO sys_menu VALUES (5012, '产品新增', 5010, 2, '#', '', '', 1, 0, 'F', '0', '0', 'wishlamp:product:add',    '#', 103, 1, SYSDATE(), NULL, NULL, '');
INSERT INTO sys_menu VALUES (5013, '产品修改', 5010, 3, '#', '', '', 1, 0, 'F', '0', '0', 'wishlamp:product:edit',   '#', 103, 1, SYSDATE(), NULL, NULL, '');
INSERT INTO sys_menu VALUES (5014, '产品删除', 5010, 4, '#', '', '', 1, 0, 'F', '0', '0', 'wishlamp:product:remove', '#', 103, 1, SYSDATE(), NULL, NULL, '');

-- 二级菜单: 活动实例
INSERT INTO sys_menu VALUES (5020, '活动实例', 5000, 3, 'instance', 'wishlamp/instance/index', '', 1, 0, 'C', '0', '0', 'wishlamp:instance:list', 'guide', 103, 1, SYSDATE(), NULL, NULL, '活动实例管理');

-- 活动实例按钮
INSERT INTO sys_menu VALUES (5021, '实例查询', 5020, 1, '#', '', '', 1, 0, 'F', '0', '0', 'wishlamp:instance:query',  '#', 103, 1, SYSDATE(), NULL, NULL, '');
INSERT INTO sys_menu VALUES (5022, '实例新增', 5020, 2, '#', '', '', 1, 0, 'F', '0', '0', 'wishlamp:instance:add',    '#', 103, 1, SYSDATE(), NULL, NULL, '');
INSERT INTO sys_menu VALUES (5023, '实例修改', 5020, 3, '#', '', '', 1, 0, 'F', '0', '0', 'wishlamp:instance:edit',   '#', 103, 1, SYSDATE(), NULL, NULL, '');
INSERT INTO sys_menu VALUES (5024, '实例删除', 5020, 4, '#', '', '', 1, 0, 'F', '0', '0', 'wishlamp:instance:remove', '#', 103, 1, SYSDATE(), NULL, NULL, '');

-- ----------------------------
-- 字典类型: 活动产品类型
-- 列顺序: dict_id, tenant_id, dict_name, dict_type, create_dept, create_by, create_time, update_by, update_time, remark
-- ----------------------------
INSERT INTO sys_dict_type VALUES (200, '000000', '活动产品类型', 'wish_product_type',   103, 1, SYSDATE(), NULL, NULL, '活动产品类型列表');
INSERT INTO sys_dict_type VALUES (201, '000000', '活动实例状态', 'wish_instance_status', 103, 1, SYSDATE(), NULL, NULL, '活动实例状态列表');

-- ----------------------------
-- 字典数据: 活动产品类型
-- 列顺序: dict_code, tenant_id, dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, create_dept, create_by, create_time, update_by, update_time, remark
-- ----------------------------
INSERT INTO sys_dict_data VALUES (400, '000000', 1, '灯笼祈福', 'BLESSING', 'wish_product_type',   '', 'warning', 'N', 103, 1, SYSDATE(), NULL, NULL, '灯笼祈福活动');
INSERT INTO sys_dict_data VALUES (401, '000000', 2, '猜灯谜',   'RIDDLE',   'wish_product_type',   '', 'success', 'N', 103, 1, SYSDATE(), NULL, NULL, '猜灯谜活动');

-- ----------------------------
-- 字典数据: 活动实例状态
-- ----------------------------
INSERT INTO sys_dict_data VALUES (410, '000000', 1, '草稿',   'DRAFT',  'wish_instance_status', '', 'info',    'N', 103, 1, SYSDATE(), NULL, NULL, '');
INSERT INTO sys_dict_data VALUES (411, '000000', 2, '进行中', 'ACTIVE', 'wish_instance_status', '', 'success', 'N', 103, 1, SYSDATE(), NULL, NULL, '');
INSERT INTO sys_dict_data VALUES (412, '000000', 3, '已暂停', 'PAUSED', 'wish_instance_status', '', 'warning', 'N', 103, 1, SYSDATE(), NULL, NULL, '');
INSERT INTO sys_dict_data VALUES (413, '000000', 4, '已结束', 'ENDED',  'wish_instance_status', '', 'danger',  'N', 103, 1, SYSDATE(), NULL, NULL, '');

-- ----------------------------
-- 祝福参与记录表 (租户隔离)
-- ----------------------------
DROP TABLE IF EXISTS wish_entry;
CREATE TABLE wish_entry (
    entry_id          BIGINT(20)   NOT NULL              COMMENT '记录ID',
    tenant_id         VARCHAR(20)  NOT NULL              COMMENT '租户编号',
    instance_id       BIGINT(20)   NOT NULL              COMMENT '活动实例ID',
    participant_name  VARCHAR(50)  DEFAULT NULL          COMMENT '参与者姓名',
    to_name           VARCHAR(50)  DEFAULT NULL          COMMENT '祝福对象',
    message           VARCHAR(500) DEFAULT NULL          COMMENT '祝福语内容',
    card_style        VARCHAR(20)  DEFAULT 'default'     COMMENT '卡片样式',
    ip_address        VARCHAR(64)  DEFAULT NULL          COMMENT '参与者IP',
    create_time       DATETIME     DEFAULT NULL          COMMENT '参与时间',
    PRIMARY KEY (entry_id)
) ENGINE=InnoDB COMMENT='祝福参与记录';

-- ----------------------------
-- 初始化-端午祝福卡产品
-- ----------------------------
INSERT INTO wish_product VALUES (3, '端午祝福卡', 'duanwu-blessing', 'BLESSING', '端午节主题祝福卡活动，用户填写祝福语生成精美电子祝福卡，可下载分享', NULL, 1, 103, 1, SYSDATE(), 1, SYSDATE());

-- ----------------------------
-- 菜单 SQL — 祝福记录
-- ----------------------------

-- 二级菜单: 祝福记录
INSERT INTO sys_menu VALUES (5030, '祝福记录', 5000, 4, 'entry', 'wishlamp/entry/index', '', 1, 0, 'C', '0', '0', 'wishlamp:entry:list', 'message', 103, 1, SYSDATE(), NULL, NULL, '祝福参与记录管理');

-- 祝福记录按钮
INSERT INTO sys_menu VALUES (5031, '记录查询', 5030, 1, '#', '', '', 1, 0, 'F', '0', '0', 'wishlamp:entry:query',  '#', 103, 1, SYSDATE(), NULL, NULL, '');
INSERT INTO sys_menu VALUES (5032, '记录删除', 5030, 2, '#', '', '', 1, 0, 'F', '0', '0', 'wishlamp:entry:remove', '#', 103, 1, SYSDATE(), NULL, NULL, '');
INSERT INTO sys_menu VALUES (5033, '记录导出', 5030, 3, '#', '', '', 1, 0, 'F', '0', '0', 'wishlamp:entry:export', '#', 103, 1, SYSDATE(), NULL, NULL, '');
