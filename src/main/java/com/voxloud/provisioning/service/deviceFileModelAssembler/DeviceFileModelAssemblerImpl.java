package com.voxloud.provisioning.service.deviceFileModelAssembler;

import com.voxloud.provisioning.entity.Device;
import com.voxloud.provisioning.models.DeviceFileModel;
import com.voxloud.provisioning.propertyStorage.DevicePropertiesStorage;
import com.voxloud.provisioning.service.factories.overrideFragmentHandlerFactory.OverrideFragmentHandlerFactory;
import com.voxloud.provisioning.service.strategies.overrideFragmentStrategy.OverrideFragmentHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeviceFileModelAssemblerImpl implements DeviceFileModelAssembler {
    private final DevicePropertiesStorage propertiesStorage;

    @Override
    public DeviceFileModel toModel(Device device) {
        DeviceFileModel fileModel = new DeviceFileModel();

        fileModel.setUsername(device.getUsername());
        fileModel.setPassword(device.getPassword());
        fileModel.setCodecs(this.propertiesStorage.getCodecs());

        if (device.getOverrideFragment() == null) {
            fileModel.setDomain(this.propertiesStorage.getDomain());
            fileModel.setPort(this.propertiesStorage.getPort());
        } else {
            OverrideFragmentHandler handler = OverrideFragmentHandlerFactory.initialize(device);
            handler.handle(fileModel, device.getOverrideFragment());
        }

        return fileModel;
    }
}
