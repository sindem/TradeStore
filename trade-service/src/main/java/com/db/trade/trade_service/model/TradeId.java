package com.db.trade.trade_service.model;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
//@Builder
public class TradeId implements Serializable {
    
    private String tradeId;
    private Integer version;

    public TradeId() {}

    public TradeId(String tradeId, Integer version) {
        this.tradeId = tradeId;
        this.version = version;
    }

}

