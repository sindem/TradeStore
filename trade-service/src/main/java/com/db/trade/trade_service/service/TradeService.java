package com.db.trade.trade_service.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.db.trade.trade_service.model.Trade;
import com.db.trade.trade_service.model.TradeId;
import com.db.trade.trade_service.model.TradeRequest;
import com.db.trade.trade_service.repository.TradeRepository;

@Service
public class TradeService {

    @Autowired
    private TradeRepository tradeRepository;

    public Trade saveTrade(TradeRequest tradeRequest) {
    	Trade trade = convertToTrade(tradeRequest);
    	TradeId tradeId =new TradeId(tradeRequest.getTradeId(), tradeRequest.getVersion());
        Optional<Trade> existingTrade = tradeRepository.findById(tradeId);
        if (existingTrade.isPresent()) {
        	if (existingTrade.get().getExpired() == 'Y') {
        		throw new IllegalArgumentException("Expired trade cannot be set");
        	}
        	TradeId existingTradeId = existingTrade.get().getTradeId();
        	Integer existingVersion = existingTradeId.getVersion();
        	if (trade.getTradeId().getVersion() < existingVersion) {
        		throw new IllegalArgumentException("Trade version is lower than the current version.");
        	} else if (trade.getTradeId().getVersion()==existingVersion) {
        		TradeId newTradeId = new TradeId(existingTradeId.getTradeId(), existingVersion+1);
        		tradeRepository.delete(existingTrade.get());
        		existingTradeId.setVersion(existingVersion+1);
        		trade.setTradeId(newTradeId);
        	}
        }

        if (trade.getMaturityDate().isBefore(java.time.LocalDate.now())) {
            throw new IllegalArgumentException("Trade maturity date cannot be in the past.");
        }

        trade.setExpired('N'); // Default to not expired
        trade.setCreatedDate(java.time.LocalDate.now());
        return tradeRepository.save(trade);
    }

    private Trade convertToTrade(TradeRequest tradeRequest) {
		Trade trade = new Trade();
		trade.setTradeId(new TradeId(tradeRequest.getTradeId(), tradeRequest.getVersion()));
		trade.setBookId(tradeRequest.getBookId());
		trade.setCounterPartyId(tradeRequest.getCounterPartyId());
		trade.setMaturityDate(tradeRequest.getMaturityDate());
		trade.setExpired(tradeRequest.getExpired());
		return trade;
	}

	public List<Trade> getAllTrades() {
        return tradeRepository.findAll();
    }
    
    @Scheduled(cron = "0 0 0 * * ?") // Runs daily
    public void markExpiredTrades() {
        List<Trade> trades = tradeRepository.findAll();
        for (Trade trade : trades) {
            if (trade.getMaturityDate().isBefore(LocalDate.now())) {
                trade.setExpired('Y');
                tradeRepository.save(trade);
            }
        }
    }
}

