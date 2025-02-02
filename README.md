# SOME ASSUMPTIONS MADE
1. Successful trade that gets created as per the business rules get persisted on a postgres table named 'Trade'.
2. Do to lack of clarity, have assumed and considered a composite primary key for trade_id and version column in 'Trade' table.
3. Reason for choosing postgres table for trade store is that we get flexibility for reporting use cases when you have to retrieve data from multiple table reference. We could have made it work with No Sql as well for this simple case.
4. No sql reference is just to capture the input request for the REST controller. Request is always successful from the controller. As soon as the request is received we log the event with payload in Mongo DB in a collection named 'INPUT_REQUEST'.
   Trade is send to a Kafka topic and gets processed asynchronous. Trade could be rejected or created based on the business rules (refer com.db.trade.trade_service.service.TradeService class >> saveTrade(TradeRequest request)) . This is a crude version to demostrate the concept. 
6. We use POST version of /trades to create a trade [http://<HOST>:<PORT>/trade-service/trades] with following request in JSON format (sample payload):
                   {
                      "tradeId":"T1",
                      "version":1,
                      "counterPartyId":"CP1",
                      "bookId":"B1",
                      "maturityDate": "2025-08-20"
                  }
7. Application could be run locally hence we have only application.properties inside src/main/resources folder. Improvement could be done by introducing application.yml file and have execute based on the spring profile. Due to time constraint I have skipped this.
8. Sonarqube output could be generated locally. From Eclipse > Select the project trade-service > Maven Project > Run As > Run Configuration or execute from a script the following command:
         mvn clean verify sonar:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.login=<USER> -Dsonar.password=<USER_PASSWORD>


# SUPPORTING INFRA
1. All supporting infrastructure items could be run locally using the docker-compose file (prerequisites to have docker installed).
2. Docker compose has reference to bring following containers using the valid images:
     a. Sonarqube
     b. Zookeeper
     c. Kafka
     d. Postgres
     e. Mongo
4. After cloning the repo, cd into trade-service folder. On command prompt >> docker compose up -d
5. Verify all relevant container has started. Use >> docker ps
