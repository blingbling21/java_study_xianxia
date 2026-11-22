package com.example.xianxia.vo;

import com.example.xianxia.entity.StorageItem;
import lombok.Data;

import java.util.List;

@Data
public class CultivatorDetailVO {
    private Long id;
    private String daoName;
    private Integer realmLevel;
    private String realmName;
    private Long spiritualPower;
    private Long spiritStones;
    private List<StorageItem> bagItems;
}
