package com.example.xianxia.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class GrantStorageItemRequest {
    @NotBlank(message = "宝物名称不能为空")
    private String itemName;

    @NotBlank(message = "宝物类型不能为空")
    @Pattern(regexp = "DANYAO|FABAO|LINGCAI", message = "宝物类型只能是DANYAO（丹药）、FABAO（法宝）或LINGCAI（灵材）")
    private String itemType;

    @Min(value = 1, message = "品质最低为1（黄阶）")
    @Max(value = 4, message = "品质最高为4（天阶）")
    private Integer quality = 1;

    @NotNull(message = "数量不能为空")
    @Min(value = 1, message = "存入数量至少为1")
    private Integer quantity;

    @Min(value = 0, message = "效用数值不能为负数")
    private Long effectValue = 0L;
}
