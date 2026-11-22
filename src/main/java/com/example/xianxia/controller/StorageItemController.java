package com.example.xianxia.controller;

import com.example.xianxia.common.Result;
import com.example.xianxia.dto.GrantStorageItemRequest;
import com.example.xianxia.entity.Cultivator;
import com.example.xianxia.entity.StorageItem;
import com.example.xianxia.service.StorageItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cultivators/{cultivatorId}/items")
@RequiredArgsConstructor
public class StorageItemController {

    private final StorageItemService storageItemService;

    @PostMapping
    public Result<Void> addItem(@PathVariable Long cultivatorId, @RequestBody @Valid GrantStorageItemRequest grantStorageItemRequest) {
        storageItemService.grantItem(cultivatorId, grantStorageItemRequest);
        return Result.success("已将【" + grantStorageItemRequest.getItemName() + "】收入储物袋", null);
    }

    @GetMapping
    public Result<List<StorageItem>> listItems(
            @PathVariable Long cultivatorId,
            @RequestParam(required = false) String itemType,
            @RequestParam(required = false) Integer minQuality
    ) {
        return Result.success(storageItemService.searchBag(cultivatorId, itemType, minQuality));
    }

    @PostMapping("/{itemId}/consume")
    public Result<Cultivator> consumeDANYAO(@PathVariable Long cultivatorId, @PathVariable Long itemId) {
        return Result.success("丹药化开，灵力大增！", storageItemService.consumeDANYAO(cultivatorId, itemId));
    }
}
