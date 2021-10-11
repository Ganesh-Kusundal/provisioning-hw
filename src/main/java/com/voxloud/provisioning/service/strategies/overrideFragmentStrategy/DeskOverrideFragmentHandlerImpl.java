package com.voxloud.provisioning.service.strategies.overrideFragmentStrategy;

import com.voxloud.provisioning.models.DeviceFileModel;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

@Log4j2
public class DeskOverrideFragmentHandlerImpl implements OverrideFragmentHandler {

    @Override
    public void handle(DeviceFileModel model, String overrideFragment) {
        if (model == null || overrideFragment == null) {
            return;
        }

        try {
            Properties properties = this.buildProperties(overrideFragment);
            model.setPort(properties.getProperty("port"));
            model.setDomain(properties.getProperty("domain"));
            model.setTimeout(Integer.valueOf(properties.getProperty("timeout")));
        } catch (IOException | NumberFormatException e) {
            log.error(e.getMessage(), e);
        }
    }

    private Properties buildProperties(String propertiesFromString) throws IOException {
        Properties properties = new Properties();
        properties.load(new StringReader(propertiesFromString));
        return properties;
    }
}
