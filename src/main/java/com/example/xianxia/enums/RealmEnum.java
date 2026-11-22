package com.example.xianxia.enums;

import com.example.xianxia.common.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum RealmEnum {
    LIAN(1, "炼气期", 1L, 0L, 0.99),
    ZHUJI(2, "筑基期", 500L, 100L, 0.85),
    JINDAN(3, "金丹期", 2000L, 500L, 0.72),
    YUANYING(4, "元婴期", 8000L, 2000L, 0.64),
    HUASHEN(5, "化神期", 30000L, 10000L, 0.53),
    LIANXU(6, "炼虚期", 100000L, 50000L, 0.39),
    HETI(7, "合体期", 300000L, 100000L, 0.22),
    DACHENG(8, "大乘期", 1000000L, 500000L, 0.11),
    DUJIE(9, "渡劫期", 10000000L, 1000000L, 0.01);

    private final int level;
    private final String name;
    private final long requiredPower;
    private final long stoneCost;
    private final double successRate;

    public static RealmEnum fromLevel(int level) {
        return Arrays.stream(values())
                .filter(r -> r.level == level)
                .findFirst()
                .orElseThrow(() -> new BusinessException("你已登临【渡劫期】绝巅，此界再无更高境界"));
    }
}
