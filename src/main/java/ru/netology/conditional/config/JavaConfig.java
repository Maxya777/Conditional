package ru.netology.conditional.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.netology.conditional.system.DevProfile;
import ru.netology.conditional.system.ProductionProfile;
import ru.netology.conditional.system.SystemProfile;


@Configuration
public class JavaConfig {

    @Bean()
    @ConditionalOnProperty(
            name = "netology.profile.dev"
    )
    public SystemProfile devProfile() {
        return new DevProfile();
    }

    @Bean
    @ConditionalOnProperty(
            name = "netology.profile.dev",
            matchIfMissing = true,
            havingValue = "false"
    )
    public SystemProfile prodProfile() {
        return new ProductionProfile();
    }
}
