package com.voxloud.provisioning.service.strategies.fileStrategy;

import com.voxloud.provisioning.entity.Device;

public interface ProvisioningFileBuilder {
    String build(Device device);
}
