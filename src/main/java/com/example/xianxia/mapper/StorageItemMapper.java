package com.example.xianxia.mapper;

import com.example.xianxia.entity.StorageItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface StorageItemMapper {
    int updateItem(StorageItem storageItem);
    Optional<StorageItem> selectById(@Param("id") Long id);
    List<StorageItem> selectByCondition(
            @Param("cultivatorId") Long cultivatorId,
            @Param("itemType") String itemType,
            @Param("minQuality") Integer minQuality
    );
    int decreaseQuantity(@Param("id") Long id, @Param("amount") int amount);
}
