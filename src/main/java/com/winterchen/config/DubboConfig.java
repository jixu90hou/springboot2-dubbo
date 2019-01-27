package com.winterchen.config;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.ProviderConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DubboConfig {
    private ApplicationConfig application;
    private RegistryConfig registry;
    private ProtocolConfig protocol;
    private ProviderConfig provider;

    public void setProvider(ProviderConfig provider) {
        provider.setTimeout(6000);
        this.provider = provider;
    }

    @Bean
    @ConfigurationProperties(prefix = "dubbo.application")
    public ApplicationConfig applicationConfig() {
        if (application == null) {
            application = new ApplicationConfig();
        }
        return application;
    }

    public ApplicationConfig getApplication() {
        return application;
    }

    public RegistryConfig getRegistry() {
        return registry;
    }

    public ProtocolConfig getProtocol() {
        return protocol;
    }

    public ProviderConfig getProvider() {
        return provider;
    }
}