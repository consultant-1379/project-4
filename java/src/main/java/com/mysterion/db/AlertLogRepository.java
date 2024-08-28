package com.mysterion.db;

import com.mysterion.alert.Alert;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.List;

@RepositoryRestResource(collectionResourceRel = "alert", path = "alert")
public interface AlertLogRepository extends MongoRepository<Alert, String> {
    public List<Alert> findBySeverity(@Param("severity") String severity);
}
