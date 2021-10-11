package com.voxloud.provisioning.propertyStorage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@PropertySource("classpath:application.properties")
public class DevicePropertiesStorage {
    @Value("${provisioning.domain}")
    private String domain;

    @Value("${provisioning.port}")
    private String port;

    @Value("#{'${provisioning.codecs}'.split(',')}")
    private List<String> codecs;

    public String getDomain() {
        return domain;
    }

    public String getPort() {
        return port;
    }

    public List<String> getCodecs() {
        return codecs;
    }
}
