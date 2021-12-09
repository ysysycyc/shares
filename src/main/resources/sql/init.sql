create table account
(
    id             int auto_increment comment '主键'
        primary key,
    name           varchar(20)                              not null comment '账户名',
    balance_amount decimal(10, 2) default 0.00              not null comment '可用金额',
    frozen_amount  decimal(10, 2) default 0.00              not null comment '冻结金额',
    create_time    datetime       default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time    datetime                                 null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '资金账户';

create table account_flow
(
    id          int auto_increment comment '主键'
        primary key,
    account_id  int                                not null comment '账户id',
    operation   tinyint                            not null comment '操作 1.买入 2.卖出 3.充值 4.提现',
    amount      decimal(10, 2)                     not null comment '金额',
    deal_time   datetime                           not null comment '交易时间',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime                           null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '资金账户流水记录';

create table stock
(
    id          int auto_increment comment '主键'
        primary key,
    code        varchar(20)                        not null comment '股票代码',
    name        varchar(20)                        not null comment '股票名称',
    type        char(2)                            null comment '股票类型 0.深股 6.沪股',
    is_valid    tinyint  default 1                 not null comment '是否有效',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime                           null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '股票';

create table stock_daily_flow
(
    id            int auto_increment comment '主键'
        primary key,
    stock_id      int                                not null comment '股票id',
    hold_amount   decimal(10, 2)                     not null comment '持有金额',
    close_amount  decimal(10, 2)                     null comment '收盘价格',
    profit_amount decimal(10, 2)                     not null comment '盈亏',
    hold_count    int                                not null comment '持有股数',
    account_id    int                                not null comment '交易账号',
    create_time   datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time   datetime                           null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '股票每日盈亏流水';

create table stock_flow
(
    id          int auto_increment comment '主键'
        primary key,
    stock_id    int                                not null comment '股票id',
    operation   tinyint                            not null comment '操作 1.买入 2.卖出',
    amount      decimal(10, 2)                     not null comment '交易价格',
    buy_amount  decimal(10, 2)                     null comment '买入价格，卖出时有值',
    count       int                                not null comment '交易股数',
    tax         decimal(10, 2)                     null comment '交易税',
    account_id  int                                not null comment '交易账号',
    sell_reason tinyint                            null comment '卖出原因，卖出时有值',
    deal_time   datetime                           not null comment '交易时间',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime                           null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '股票交易记录';

create table stock_hold
(
    id          int auto_increment comment '主键'
        primary key,
    stock_id    int                                not null comment '股票id',
    buy_amount  decimal(10, 2)                     not null comment '买入价格',
    buy_count   int                                not null comment '买入股数',
    buy_time    datetime                           not null comment '买入时间',
    buy_tax     decimal(10, 2)                     null comment '交易税',
    account_id  int                                not null comment '交易账号',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime                           null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '持有股票';