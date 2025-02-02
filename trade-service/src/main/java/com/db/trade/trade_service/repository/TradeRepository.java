package com.db.trade.trade_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.db.trade.trade_service.model.Trade;
import com.db.trade.trade_service.model.TradeId;

@Repository
public interface TradeRepository extends JpaRepository<Trade, TradeId> {
	public Optional<Trade> findById(TradeId tradeId);
}


