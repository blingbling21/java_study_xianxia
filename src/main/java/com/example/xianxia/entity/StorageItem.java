package com.example.xianxia.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StorageItem {
    private Long id;
    private Long cultivatorId;
    private String itemName;
    private String itemType;
    private Integer quality;
    private Integer quantity;
    private Long effectValue;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
