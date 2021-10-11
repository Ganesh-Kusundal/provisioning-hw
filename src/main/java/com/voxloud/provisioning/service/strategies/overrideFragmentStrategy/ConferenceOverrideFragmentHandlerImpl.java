package com.voxloud.provisioning.service.strategies.overrideFragmentStrategy;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.voxloud.provisioning.models.DeviceFileModel;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ConferenceOverrideFragmentHandlerImpl implements OverrideFragmentHandler {
    @Override
    public void handle(DeviceFileModel model, String overrideFragment) {
        if (model == null || overrideFragment == null) {
            return;
        }

        try {
            JsonObject jsonObject = new Gson().fromJson(overrideFragment, JsonObject.class);
            model.setPort(jsonObject.get("port").getAsString());
            model.setDomain(jsonObject.get("domain").getAsString());
            model.setTimeout(jsonObject.get("timeout").getAsInt());
        } catch (JsonSyntaxException e) {
            log.error(e.getMessage(), e);
        }
    }
}
