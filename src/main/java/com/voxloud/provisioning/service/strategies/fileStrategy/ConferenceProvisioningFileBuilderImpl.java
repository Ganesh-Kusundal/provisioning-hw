package com.voxloud.provisioning.service.strategies.fileStrategy;

import com.google.gson.Gson;
import com.voxloud.provisioning.entity.Device;
import com.voxloud.provisioning.models.DeviceFileModel;
import com.voxloud.provisioning.service.deviceFileModelAssembler.DeviceFileModelAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("conferenceFileBuilder")
@RequiredArgsConstructor
public class ConferenceProvisioningFileBuilderImpl implements ProvisioningFileBuilder {
    private final DeviceFileModelAssembler fileModelAssembler;

    @Override
    public String build(Device device) {
        DeviceFileModel deviceFileModel = this.fileModelAssembler.toModel(device);
        Gson gson = new Gson();
        return gson.toJson(deviceFileModel);
    }
}
