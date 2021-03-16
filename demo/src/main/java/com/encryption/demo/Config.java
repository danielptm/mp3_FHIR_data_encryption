package com.encryption.demo;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config extends ResourceConfig {

    public Config() {
        register(EncryptionController.class);
        register(Filter.class);
    }

}
