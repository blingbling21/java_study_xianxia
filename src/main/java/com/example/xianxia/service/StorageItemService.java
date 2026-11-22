package com.example.xianxia.service;

import com.example.xianxia.common.BusinessException;
import com.example.xianxia.dto.GrantStorageItemRequest;
import com.example.xianxia.entity.Cultivator;
import com.example.xianxia.entity.StorageItem;
import com.example.xianxia.mapper.StorageItemMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class StorageItemService {

    private final CultivatorService cultivatorService;
    private final StorageItemMapper storageItemMapper;

    public StorageItem getById(Long id) {
        return storageItemMapper.selectById(id)
                .orElseThrow(() -> new BusinessException("储物袋中未找到此物品"));
    }

    /**
     * 获赠/拾取宝物存入储物袋
     */
    public void grantItem(Long cultivatorId, GrantStorageItemRequest grantStorageItemRequest) {
        cultivatorService.getCultivatorById(cultivatorId);

        StorageItem storageItem = new StorageItem();
        storageItem.setCultivatorId(cultivatorId);
        storageItem.setItemName(grantStorageItemRequest.getItemName());
        storageItem.setItemType(grantStorageItemRequest.getItemType());
        storageItem.setQuality(grantStorageItemRequest.getQuality());
        storageItem.setQuantity(grantStorageItemRequest.getQuantity());
        storageItem.setEffectValue(grantStorageItemRequest.getEffectValue());

        storageItemMapper.updateItem(storageItem);
    }

    /**
     * 按条件筛选储物袋物品
     */
    public List<StorageItem> searchBag(Long cultivatorId, String itemType, Integer minQuality) {
        cultivatorService.getCultivatorById(cultivatorId);
        return storageItemMapper.selectByCondition(cultivatorId, itemType, minQuality);
    }

    /**
     * 吞服丹药：跨表事务（扣减丹药库存 + 增长修士灵力）
     */
    @Transactional(rollbackFor = Exception.class)
    public Cultivator consumeDANYAO(Long cultivatorId, Long itemId) {
        Cultivator cultivator = cultivatorService.getCultivatorById(cultivatorId);
        StorageItem storageItem = getById(itemId);

        if (storageItem == null || !storageItem.getCultivatorId().equals(cultivatorId)) {
            throw new BusinessException("储物袋中未找到此物品");
        }
        if (!"DANYAO".equals(storageItem.getItemType())) {
            throw new BusinessException("【" + storageItem.getItemName() + "】并非丹药，不可直接吞服！");
        }

        int row = storageItemMapper.decreaseQuantity(itemId, 1);
        if (row == 0) {
            throw new BusinessException("该丹药已耗尽");
        }

        cultivator.setSpiritualPower(cultivator.getSpiritualPower() + storageItem.getEffectValue());
        cultivatorService.saveOptimisticLock(cultivator);

        return cultivatorService.getCultivatorById(cultivatorId);
    }
}
