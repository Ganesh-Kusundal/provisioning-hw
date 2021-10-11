package com.voxloud.provisioning.service.factories.fileFactory;

import com.voxloud.provisioning.entity.Device;
import com.voxloud.provisioning.service.strategies.fileStrategy.ProvisioningFileBuilder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProvisioningFileBuilderFactoryImpl implements ProvisioningFileBuilderFactory {
    @Qualifier("conferenceFileBuilder")
    @NonNull
    private final ProvisioningFileBuilder conferenceFileBuilder;

    @Qualifier("deskFileBuilder")
    @NonNull
    private final ProvisioningFileBuilder deskFileBuilder;

    public ProvisioningFileBuilder initialize(Device.DeviceModel deviceModel) {
        if (deviceModel.equals(Device.DeviceModel.CONFERENCE)) {
            return this.conferenceFileBuilder;
        }
        return this.deskFileBuilder;
    }
}
