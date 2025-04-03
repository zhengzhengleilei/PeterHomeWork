package com.peter.service;


import com.peter.domain.TradeInfo;

import java.util.List;


public interface TradeService {

    public TradeInfo selectTradeInfoById(String id);

    public List<TradeInfo> selectTradeInfoList(TradeInfo tradeInfo);

    public int insertTradeInfo(TradeInfo tradeInfo);

    public int updateTradeInfo(TradeInfo tradeInfo);

    public int deleteTradeInfoByIds(String[] ids);

    public int deleteTradeInfoById(String id);

    public List<TradeInfo> getAllTradeInfo();
}
