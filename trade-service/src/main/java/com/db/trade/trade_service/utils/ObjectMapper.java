package com.db.trade.trade_service.utils;

import com.db.trade.trade_service.model.InputRequest;



public class ObjectMapper {
    private ObjectMapper() {
    }


    public static InputRequest toInputRequest(final String id, final com.db.trade.trade_service.model.TradeRequest payload) {
        return InputRequest.builder()
        		.id(id)
                //.payload(payload)
                .build();
    }
}
