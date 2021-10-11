package com.voxloud.provisioning.service.deviceFileModelAssembler;

import com.voxloud.provisioning.entity.Device;
import com.voxloud.provisioning.models.DeviceFileModel;

public interface DeviceFileModelAssembler {
    DeviceFileModel toModel(Device device);
}
