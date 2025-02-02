package com.db.trade.trade_service.kafka;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.db.trade.trade_service.model.Trade;
import com.db.trade.trade_service.model.TradeRequest;

@Service
public class TradeProducer {

    private static final String TOPIC = "trade-topic";

    @Autowired
    private KafkaTemplate<String, TradeRequest> kafkaTemplate;

    public void sendTrade(TradeRequest trade) {
        kafkaTemplate.send(TOPIC, trade);
    }
}

