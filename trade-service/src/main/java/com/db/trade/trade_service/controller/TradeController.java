package com.db.trade.trade_service.controller;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.db.trade.trade_service.kafka.TradeProducer;
import com.db.trade.trade_service.model.InputRequest;
import com.db.trade.trade_service.model.Trade;
import com.db.trade.trade_service.model.TradeRequest;
import com.db.trade.trade_service.repository.InputRequestRepository;
import com.db.trade.trade_service.service.TradeService;

@RestController
@RequestMapping("/trades")
public class TradeController {

    @Autowired
    private TradeService tradeService;
    
    @Autowired
    private TradeProducer kafkaProducer;
    
    @Autowired
    private InputRequestRepository inputRequestRepository;

    @PostMapping
    public ResponseEntity<String> createTrade(@RequestBody TradeRequest tradeRequest) {

    	inputRequestRepository.save(convertToInputRequest(tradeRequest));
        try {
        	kafkaProducer.sendTrade(tradeRequest);
            return new ResponseEntity<>("Trade created "+tradeRequest.getTradeId()+ " with version "+tradeRequest.getVersion(), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("BAD REQUEST", HttpStatus.BAD_REQUEST);
        }
    }

	@GetMapping
    public ResponseEntity<List<Trade>> getAllTrades() {
        List<Trade> trades = tradeService.getAllTrades();
        return new ResponseEntity<>(trades, HttpStatus.OK);
    }
    
    @Transactional
    public void saveTradeRequest(final InputRequest inputRequest, final TradeRequest payload) {
    	inputRequestRepository.save(inputRequest);
    }
    
    private InputRequest convertToInputRequest(TradeRequest tradeRequest) {
    	return InputRequest.builder()
                .payload(tradeRequest)
                .createdAt(Instant.now())
                .build();
	}
}
