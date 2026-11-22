package com.example.xianxia.mapper;

import com.example.xianxia.entity.Cultivator;
import com.example.xianxia.vo.CultivatorDetailVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface CultivatorMapper {
    int insert(Cultivator cultivator);
    Optional<Cultivator> selectById(@Param("id") Long id);
    Optional<Cultivator> selectByDaoName(@Param("daoName") String daoName);
    boolean existsByBaoName(@Param("daoName") String daoName);
    int updateWithVersion(Cultivator cultivator);
    Optional<CultivatorDetailVO> selectDetailById(@Param("id") Long id);
    int addPassivePowerForAll(@Param("basePower") Long basePower);
}
