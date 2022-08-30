package com.kavithas84.usermgmt.config;

import com.kavithas84.usermgmt.entity.UserAccount;
import com.kavithas84.usermgmt.repository.UserAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(UserAccountRepository repository) {

        return args -> {
            log.info("Preloading " + repository.save(new UserAccount("Lorelai Gilmore", "mother")));
            log.info("Preloading " + repository.save(new UserAccount("Rory Gilmore", "daughter")));
        };
    }
}