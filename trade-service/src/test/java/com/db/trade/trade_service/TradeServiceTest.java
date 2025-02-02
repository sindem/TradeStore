package com.db.trade.trade_service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.db.trade.trade_service.model.Trade;
import com.db.trade.trade_service.model.TradeId;
import com.db.trade.trade_service.model.TradeRequest;
import com.db.trade.trade_service.repository.TradeRepository;
import com.db.trade.trade_service.service.TradeService;

@ExtendWith(MockitoExtension.class)
class TradeServiceTest {

    @Mock
    private TradeRepository tradeRepository;

    @InjectMocks
    private TradeService tradeService;

    private TradeRequest tradeRequest;
    private Trade trade;
    private TradeId tradeId;

    @BeforeEach
    void setUp() {
        tradeRequest = TradeRequest.builder()
        				.tradeId("T1")
        				.version(1)
        				.bookId("B1")
        				.counterPartyId("C1")
        				.maturityDate(LocalDate.now().plusDays(10))
        				.expired('N')
        				.build();
        
        tradeId = new TradeId("T1", 1);
        trade = Trade.builder()
        		.tradeId(tradeId)
        		.bookId("B1")
        		.counterPartyId("C1")
        		.maturityDate(LocalDate.now().plusDays(10))
        		.expired('N')
        		.build();
    }
    
    @Test
    void testSaveTradeForNewTrade() {
        when(tradeRepository.findById(any(TradeId.class))).thenReturn(Optional.empty());
        when(tradeRepository.save(any(Trade.class))).thenReturn(trade);

        Trade savedTrade = tradeService.saveTrade(tradeRequest);

        assertNotNull(savedTrade);
        assertEquals("T1", savedTrade.getTradeId().getTradeId());
        verify(tradeRepository).save(any(Trade.class));
    }
        
    @Test
    void testSaveTradeForExistingTradeWithHigherVersion() {
        when(tradeRepository.findById(any(TradeId.class))).thenReturn(Optional.of(trade));

        tradeRequest.setVersion(2);
        Trade updatedTrade = Trade.builder()
        		.tradeId(new TradeId("T1", 2))
        		.bookId("B1")
        		.counterPartyId("C1")
        		.maturityDate(trade.getMaturityDate())
        		.expired('N')
        		.build();
        when(tradeRepository.save(any(Trade.class))).thenReturn(updatedTrade);

        Trade savedTrade = tradeService.saveTrade(tradeRequest);

        assertNotNull(savedTrade);
        assertEquals(2, savedTrade.getTradeId().getVersion()); // Now it should match
        verify(tradeRepository).save(any(Trade.class));
    }

    @Test
    void testSaveTradeForExistingTradeWithLowerVersionFailing() {
        tradeRequest.setVersion(0);
        when(tradeRepository.findById(any(TradeId.class))).thenReturn(Optional.of(trade));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> tradeService.saveTrade(tradeRequest));
        assertEquals("Trade version is lower than the current version.", exception.getMessage());
    }
    
    @Test
    void testSaveTradeForExpiredTradeThatFail() {
        trade.setExpired('Y');
        when(tradeRepository.findById(any(TradeId.class))).thenReturn(Optional.of(trade));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> tradeService.saveTrade(tradeRequest));
        assertEquals("Expired trade cannot be set", exception.getMessage());
    }
    
    @Test
    void testSaveTradePastMaturityDateThatFail() {
        tradeRequest.setMaturityDate(LocalDate.now().minusDays(1));
        when(tradeRepository.findById(any(TradeId.class))).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> tradeService.saveTrade(tradeRequest));
        assertEquals("Trade maturity date cannot be in the past.", exception.getMessage());
    }

}
