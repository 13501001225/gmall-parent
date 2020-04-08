package com.gw.gmall.publisher.mapper;

import java.util.List;
import java.util.Map;

public interface DauMapper {
    /**
     * 查询活跃用户总数
     * @param date
     * @return
     */
    Integer selectDauTotal(String date);

    /**
     * 返回分时用户数
     * @param date
     * @return
     */
    List<Map> selectDauTotalHourMap(String date);
}
