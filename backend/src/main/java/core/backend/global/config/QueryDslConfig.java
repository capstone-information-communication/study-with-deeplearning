package core.backend.global.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
public class QueryDslConfig {

    @Bean
    public JPAQueryFactory setJPAQueryFactory(EntityManager em) {
        return new JPAQueryFactory(em);
    }
}
