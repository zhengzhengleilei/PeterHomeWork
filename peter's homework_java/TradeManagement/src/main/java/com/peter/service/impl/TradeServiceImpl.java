package com.peter.service.impl;

import com.peter.domain.TradeInfo;
import com.peter.exception.TradeException;
import com.peter.service.TradeService;
import com.peter.utils.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TradeServiceImpl implements TradeService {

    private static Map<String, TradeInfo> map = new LinkedHashMap<>();

    static {
        for (int i = 1; i <= 10000; i++) {
            String id = UUID.randomUUID().toString();

            map.put(id, new TradeInfo(id, "name_" + i, i % 2 == 0 ? "1" : "2", new BigDecimal(i), "desc_" + i, new Date(), null));
        }
    }

    @Override
    public TradeInfo selectTradeInfoById(String id) {
        return map.get(id);
    }

    @Override
    public List<TradeInfo> selectTradeInfoList(TradeInfo tradeInfo) {
        List<TradeInfo> list = null;
        if (StringUtils.isNotEmpty(tradeInfo.getName())) {
            list = map.values().stream()
                    .filter(item -> item.getName().contains(tradeInfo.getName()))
                    .collect(Collectors.toCollection(ArrayList::new));
        } else {
            list = map.values().stream()
                    .collect(Collectors.toCollection(ArrayList::new));
        }
        return list != null ? list : Collections.emptyList();
    }

    @Override
    public int insertTradeInfo(TradeInfo tradeInfo) {
        try {
            String id = UUID.randomUUID().toString();
            tradeInfo.setId(id);
            tradeInfo.setCreateTime(new Date());
            map.put(id, tradeInfo);
        } catch (Exception e) {
            throw new TradeException("插入交易信息失败");
        }

        return 1;
    }

    @Override
    public int updateTradeInfo(TradeInfo tradeInfo) {
        TradeInfo tradeInfo_for_map = map.get(tradeInfo.getId());
        if (StringUtils.isNotEmpty(tradeInfo.getName())) {
            tradeInfo_for_map.setName(tradeInfo.getName());
        }
        if (StringUtils.isNotEmpty(tradeInfo.getType())) {
            tradeInfo_for_map.setType(tradeInfo.getType());
        }
        if (null != tradeInfo.getPrice()) {
            tradeInfo_for_map.setPrice(tradeInfo.getPrice());
        }
        if (StringUtils.isNotEmpty(tradeInfo.getDesc())) {
            tradeInfo_for_map.setDesc(tradeInfo.getDesc());
        }
        tradeInfo_for_map.setUpdateTime(new Date());
        map.put(tradeInfo.getId(), tradeInfo_for_map);
        return 1;
    }

    @Override
    public int deleteTradeInfoByIds(String[] ids) {
        Set<String> idSet = new HashSet<>(Arrays.asList(ids));
        map.keySet().removeAll(idSet);
        return ids.length;
    }

    @Override
    public int deleteTradeInfoById(String id) {
        map.remove(id);
        return 1;
    }

    @Cacheable(value = "tradeInfoCache", key = "#root.methodName")
    public List<TradeInfo> getAllTradeInfo() {
        return selectTradeInfoList(null);
    }

}
