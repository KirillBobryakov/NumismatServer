package numismat;

import numismat.entity.Piece;
import org.neo4j.driver.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.AbstractReactiveNeo4jConfig;
import org.springframework.data.neo4j.core.ReactiveDatabaseSelectionProvider;
import org.springframework.data.neo4j.core.transaction.ReactiveNeo4jTransactionManager;
import org.springframework.data.neo4j.repository.config.EnableReactiveNeo4jRepositories;
import org.springframework.data.neo4j.repository.config.ReactiveNeo4jRepositoryConfigurationExtension;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Collection;
import java.util.Collections;
import java.util.logging.Level;

@Configuration
@EnableReactiveNeo4jRepositories
@EnableTransactionManagement
//class MyConfiguration extends AbstractReactiveNeo4jConfig {
class MyConfiguration{
//
//    @Bean
//    public Driver driver() {
//        return GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "password"),
//                Config.builder()
//                        .withLogging(Logging.console(Level.ALL))
//                        .withLogging(Logging.javaUtilLogging(Level.ALL))
//                        .withLogging(Logging.slf4j())
//                        .build());
//    }
//
//    @Override
//    protected Collection<String> getMappingBasePackages() {
//        return Collections.singletonList(Piece.class.getPackage().getName());
//    }

    @Bean(ReactiveNeo4jRepositoryConfigurationExtension.DEFAULT_TRANSACTION_MANAGER_BEAN_NAME)
    public ReactiveTransactionManager reactiveTransactionManager(Driver driver,
                                                                 ReactiveDatabaseSelectionProvider databaseNameProvider) {
        return new ReactiveNeo4jTransactionManager(driver, databaseNameProvider);
    }

}