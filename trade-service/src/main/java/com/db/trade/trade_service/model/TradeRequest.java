package com.db.trade.trade_service.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Trade details")
public class TradeRequest implements Serializable {
	@Serial
    private static final long serialVersionUID = 1L;
	
	@Id
    @JsonProperty("tradeId")
    private String tradeId;
	
    @JsonProperty("version")
    private Integer version;
    
    @JsonProperty("counterPartyId")
    private String counterPartyId;
    
    @JsonProperty("bookId")
    private String bookId;
    
    @JsonProperty("maturityDate")
    private LocalDate maturityDate;
    
    @JsonProperty("createdDate")
    private LocalDate createdDate;
    
    @JsonProperty("expired")
    private char expired;
}
