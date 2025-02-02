package com.db.trade.trade_service.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.db.trade.trade_service.model.Trade;
import com.db.trade.trade_service.model.TradeRequest;
import com.db.trade.trade_service.service.TradeService;

@Service
public class TradeConsumer {

    @Autowired
    private TradeService tradeService;

    @KafkaListener(topics = "trade-topic", groupId = "trade-group")
    public void consumeTrade(TradeRequest trade) {
    	Trade savedTrade = tradeService.saveTrade(trade);
    }
}

