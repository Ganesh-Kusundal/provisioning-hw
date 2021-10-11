package com.voxloud.provisioning.service.factories.overrideFragmentHandlerFactory;

import com.voxloud.provisioning.entity.Device;
import com.voxloud.provisioning.service.strategies.overrideFragmentStrategy.ConferenceOverrideFragmentHandlerImpl;
import com.voxloud.provisioning.service.strategies.overrideFragmentStrategy.DeskOverrideFragmentHandlerImpl;
import com.voxloud.provisioning.service.strategies.overrideFragmentStrategy.OverrideFragmentHandler;

public class OverrideFragmentHandlerFactory {

    public static OverrideFragmentHandler initialize(Device device) {
        if (device.getModel().equals(Device.DeviceModel.CONFERENCE)) {
            return new ConferenceOverrideFragmentHandlerImpl();
        }
        return new DeskOverrideFragmentHandlerImpl();
    }
}
