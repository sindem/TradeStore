package com.db.trade.trade_service.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Trade")
public class Trade implements java.io.Serializable {
	
    @EmbeddedId
    private TradeId tradeId; // Use the composite key

    @Column(name = "counter_party_id", nullable = false)
    private String counterPartyId;

    @Column(name = "book_id", nullable = false)
    private String bookId;

    @Column(name = "maturity_date", nullable = false)
    private LocalDate maturityDate;

    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    @Column(name = "expired", nullable = false)
    private char expired;
    
}