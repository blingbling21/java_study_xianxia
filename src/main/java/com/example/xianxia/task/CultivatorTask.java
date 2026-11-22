package com.example.xianxia.task;


import com.example.xianxia.mapper.CultivatorMapper;
import com.example.xianxia.service.CultivatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CultivatorTask {
    private final CultivatorMapper cultivatorMapper;

    @Scheduled(fixedRate = 30000)
    public void absorbHeavenAndEarthQi() {
        Long basePower = 20L;
        int affected = cultivatorMapper.addPassivePowerForAll(basePower);
        if (affected > 0) {
            log.info("【天地灵气潮汐】全服 {} 位修士自动运转功法，基础灵力 +{}（按境界倍增）", affected, basePower);
        }
    }
}
