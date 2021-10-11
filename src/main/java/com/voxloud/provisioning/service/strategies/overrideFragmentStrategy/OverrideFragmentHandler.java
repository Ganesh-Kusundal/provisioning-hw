package com.voxloud.provisioning.service.strategies.overrideFragmentStrategy;

import com.voxloud.provisioning.models.DeviceFileModel;

public interface OverrideFragmentHandler {
    void handle(DeviceFileModel model, String overrideFragment);
}
