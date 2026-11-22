package com.example.xianxia.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Cultivator {
    private Long id;
    private String daoName;
    private Integer realmLevel;
    private String realmName;
    private Long spiritualPower;
    private Long spiritStones;
    private Integer version;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
