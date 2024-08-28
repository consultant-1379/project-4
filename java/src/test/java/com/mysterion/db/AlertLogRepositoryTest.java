package com.mysterion.db;

import com.mongodb.MongoClientURI;
import com.mongodb.WriteConcern;
import com.mysterion.alert.Alert;
import com.mysterion.alert.SeverityLevel;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.support.MongoRepositoryFactoryBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


@ExtendWith(SpringExtension.class)
@DataMongoTest(excludeAutoConfiguration = {EmbeddedMongoAutoConfiguration.class})
public class AlertLogRepositoryTest {

    @Autowired
    private AlertLogRepository alertLogRepository;

    @DisplayName("Setting up AlertLogRepository class for test")
    @BeforeEach
    public void dataSetup(){
    }

    @DisplayName("Testing find by severity method")
    @Test
    public void testFindBySeverity(){
        Alert alertWarning = new Alert(SeverityLevel.WARNING,"v1.0.1","Warning");
        Alert alertCritical = new Alert(SeverityLevel.CRITICAL,"v1.0.2","critical");

        alertLogRepository.save(alertWarning);
        alertLogRepository.save(alertCritical);

        Alert retrieveWarning = alertLogRepository.findBySeverity("WARNING").get(0);
        alertWarning.setId(retrieveWarning.getId());

        Alert retrieveCritical = alertLogRepository.findBySeverity("CRITICAL").get(0);
        alertCritical.setId(retrieveCritical.getId());

        assertThat(retrieveWarning, is(equalTo(retrieveWarning)));
        assertThat(retrieveCritical,is(equalTo(retrieveCritical)));
        assertThat(alertLogRepository.findBySeverity("Major"), is(Collections.emptyList()));
    }

    @Configuration
    static class MongoConfiguration implements InitializingBean, DisposableBean {

        MongodExecutable executable;

        @Override
        public void afterPropertiesSet() throws Exception {
            String host = "localhost";
            int port = 27019;

            IMongodConfig mongodConfig = new MongodConfigBuilder().version(Version.Main.PRODUCTION)
                    .net(new Net(host, port, Network.localhostIsIPv6()))
                    .build();

            MongodStarter starter = MongodStarter.getDefaultInstance();
            executable = starter.prepare(mongodConfig);
            executable.start();
        }


        @Bean
        public MongoDbFactory factory() {
            MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(new MongoClientURI("mongodb://localhost:27019/test_db"));
            return mongoDbFactory;
        }


        @Bean
        public MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory) {
            MongoTemplate template = new MongoTemplate(mongoDbFactory);
            template.setWriteConcern(WriteConcern.ACKNOWLEDGED);
            return template;
        }

        @Bean
        public MongoRepositoryFactoryBean mongoFactoryRepositoryBean(MongoTemplate template) {
            MongoRepositoryFactoryBean mongoDbFactoryBean = new MongoRepositoryFactoryBean(AlertLogRepository.class);
            mongoDbFactoryBean.setMongoOperations(template);

            return mongoDbFactoryBean;
        }

        @Override
        public void destroy() throws Exception {
            executable.stop();
        }
    }

}
