package com.voxloud.provisioning.service.factories.fileFactory;

import com.voxloud.provisioning.entity.Device;
import com.voxloud.provisioning.service.strategies.fileStrategy.ProvisioningFileBuilder;

public interface ProvisioningFileBuilderFactory {
    ProvisioningFileBuilder initialize(Device.DeviceModel deviceModel);
}
