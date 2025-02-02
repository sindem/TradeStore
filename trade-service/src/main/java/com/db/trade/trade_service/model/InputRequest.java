package com.db.trade.trade_service.model;

import java.io.Serializable;
import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "INPUT_REQUEST")
public class InputRequest implements Serializable {
    @MongoId(FieldType.OBJECT_ID)
    private String id;
    
    private TradeRequest payload;
    
    @Indexed
    @CreatedDate
    private Instant createdAt;

}
