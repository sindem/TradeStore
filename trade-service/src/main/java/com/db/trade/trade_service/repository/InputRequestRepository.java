package com.db.trade.trade_service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.db.trade.trade_service.model.InputRequest;

@Repository
public interface InputRequestRepository extends MongoRepository<InputRequest, String> {
   
}

