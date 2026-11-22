package com.example.xianxia.service;

import com.example.xianxia.common.BusinessException;
import com.example.xianxia.entity.Cultivator;
import com.example.xianxia.enums.RealmEnum;
import com.example.xianxia.mapper.CultivatorMapper;
import com.example.xianxia.vo.CultivatorDetailVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class CultivatorService {
    private final CultivatorMapper cultivatorMapper;

    /**
     * 新建修士
     * @param daoName 道号
     * @return 新建修士信息
     */
    public Cultivator createCultivator(String daoName) {
        if (cultivatorMapper.existsByBaoName(daoName)) {
            throw new BusinessException("道号：【" + daoName + "】已名震修真界，请另择道号");
        }

        Cultivator cultivator = new Cultivator();
        cultivator.setDaoName(daoName);
        cultivator.setRealmLevel(1);
        cultivator.setRealmName("炼气期");
        cultivator.setSpiritualPower(0L);
        cultivator.setSpiritStones(0L);

        cultivatorMapper.insert(cultivator);

        return cultivator;
    }

    /**
     * 根据id查询修士信息
     * @param id 修士id
     * @return 修士信息
     */
    public Cultivator getCultivatorById(Long id) {
        return cultivatorMapper.selectById(id)
                .orElseThrow(() -> new BusinessException("未查到此修士神魂印记：" + id));
    }

    /**
     * 根据id查询修士详细信息
     */
    public CultivatorDetailVO getDetailById(Long id) {
        return cultivatorMapper.selectDetailById(id)
                .orElseThrow(() -> new BusinessException("未查到此修士神魂印记：" + id));
    }

    /**
     * 打坐修炼：增长灵力与少量灵石
     * @param id 打坐修炼的修士id
     * @return 修士数据
     */
    @Transactional(rollbackFor = Exception.class)
    public Cultivator meditate(Long id) {
        Cultivator cultivator = getCultivatorById(id);

        long gainedPower = ThreadLocalRandom.current().nextLong(100, 300) * cultivator.getRealmLevel();
        long gainedStones = ThreadLocalRandom.current().nextLong(30, 50) * cultivator.getRealmLevel();

        cultivator.setSpiritualPower(cultivator.getSpiritualPower() + gainedPower);
        cultivator.setSpiritStones(cultivator.getSpiritStones() + gainedStones);

        saveOptimisticLock(cultivator);

        return cultivatorMapper.selectById(id)
                .orElseThrow(() -> new BusinessException("未查到此修士神魂印记：" + id));
    }

    /**
     * 冲击下一境界
     * @param id 修士id
     * @return 修士数据
     */
    @Transactional(rollbackFor = Exception.class)
    public String breakthrough(Long id) {
        Cultivator cultivator = getCultivatorById(id);

        RealmEnum nextRealm = RealmEnum.fromLevel(cultivator.getRealmLevel() + 1);

        // 检查突破条件是否满足
        if (cultivator.getSpiritualPower() < nextRealm.getRequiredPower()) {
            throw new BusinessException(String.format("灵力不足！冲击【%s】需要 %d 点灵力，当前仅 %d 点",
                    nextRealm.getName(), nextRealm.getRequiredPower(), cultivator.getSpiritualPower()));
        }
        if (cultivator.getSpiritStones() < nextRealm.getStoneCost()) {
            throw new BusinessException(String.format("灵石不足！布阵突破需要 %d 块灵石，当前仅 %d 块",
                    nextRealm.getStoneCost(), cultivator.getSpiritStones()));
        }

        // 无论成功还是失败，都要扣除灵石
        cultivator.setSpiritStones(cultivator.getSpiritStones() - nextRealm.getStoneCost());

        // 天劫成功率
        boolean success = ThreadLocalRandom.current().nextDouble() < nextRealm.getSuccessRate();
        String outcomeMessage;
        if (success) {
            cultivator.setRealmLevel(nextRealm.getLevel());
            cultivator.setRealmName(nextRealm.getName());
            outcomeMessage = "天降祥瑞！成功渡过雷劫，晋升至【" + nextRealm.getName() + "】！";
        } else {
            long leftoverPower = (long)(cultivator.getSpiritualPower() * 0.8);
            cultivator.setSpiritualPower(leftoverPower);
            outcomeMessage = "心魔作祟，冲击【" + nextRealm.getName() + "】失败！损失灵石并遭受反噬跌落到 " + leftoverPower + " 点灵力。";
        }

        saveOptimisticLock(cultivator);
        return outcomeMessage;
    }

    /**
     * 乐观锁
     * @param cultivator 更新数据
     */
    public void saveOptimisticLock(Cultivator cultivator) {
        int affectedRows = cultivatorMapper.updateWithVersion(cultivator);
        if (affectedRows == 0) {
            throw new BusinessException("灵气波动剧烈（并发操作冲突），请稍后再试");
        }
    }
}
