package com.mysterion.db;

import com.mysterion.model.LogItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends MongoRepository<LogItem, String> {
}
