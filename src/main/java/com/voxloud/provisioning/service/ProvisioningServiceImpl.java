package com.voxloud.provisioning.service;

import com.voxloud.provisioning.entity.Device;
import com.voxloud.provisioning.repository.DeviceRepository;
import com.voxloud.provisioning.service.factories.fileFactory.ProvisioningFileBuilderFactory;
import com.voxloud.provisioning.service.strategies.fileStrategy.ProvisioningFileBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProvisioningServiceImpl implements ProvisioningService {
    private final DeviceRepository repository;
    private final ProvisioningFileBuilderFactory provisioningFileBuilderFactory;

    public String getProvisioningFile(String macAddress) {
        try {
            Device device = this.repository.getOne(macAddress);
            if (device == null) {
                return null;
            }
            ProvisioningFileBuilder provisioningFileBuilder = provisioningFileBuilderFactory.initialize(device.getModel());
            return provisioningFileBuilder.build(device);
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
}
