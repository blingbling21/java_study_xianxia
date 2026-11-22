package com.example.xianxia.controller;

import com.example.xianxia.common.Result;
import com.example.xianxia.entity.Cultivator;
import com.example.xianxia.service.CultivatorService;
import com.example.xianxia.vo.CultivatorDetailVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cultivators")
@RequiredArgsConstructor
public class CultivatorController {
    private final CultivatorService cultivatorService;

    @PostMapping
    public Result<Cultivator> register(@RequestParam String daoName) {
        return Result.success("拜入仙门成功", cultivatorService.createCultivator(daoName));
    }

    @GetMapping("/{id}")
    public Result<Cultivator> getById(@PathVariable Long id) {
        return Result.success(cultivatorService.getCultivatorById(id));
    }

    @PostMapping("/{id}/meditate")
    public Result<Cultivator> meditate(@PathVariable Long id) {
        return Result.success("吐纳一周天，修为有所精进", cultivatorService.meditate(id));
    }

    @PostMapping("/{id}/breakthrough")
    public Result<String> breakthrough(@PathVariable Long id) {
        return Result.success(cultivatorService.breakthrough(id), null);
    }

    @GetMapping("/{id}/detail")
    public Result<CultivatorDetailVO> getDetailById(@PathVariable Long id) {
        return Result.success(cultivatorService.getDetailById(id));
    }
}
