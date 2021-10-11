package com.voxloud.provisioning.service.factories;

import com.voxloud.provisioning.entity.Device;
import com.voxloud.provisioning.service.factories.fileFactory.ProvisioningFileBuilderFactoryImpl;
import com.voxloud.provisioning.service.strategies.fileStrategy.ConferenceProvisioningFileBuilderImpl;
import com.voxloud.provisioning.service.strategies.fileStrategy.DeskProvisioningFileBuilderImpl;
import com.voxloud.provisioning.service.strategies.fileStrategy.ProvisioningFileBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.core.IsInstanceOf.instanceOf;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProvisioningFileBuilderFactoryTests {
    @Autowired
    private ProvisioningFileBuilderFactoryImpl factory;

    @Test
    public void initialize_deviceConference_shouldReturnConferenceBuilder() {
        ProvisioningFileBuilder fileBuilder = this.factory.initialize(Device.DeviceModel.CONFERENCE);

        Assert.assertThat(fileBuilder, instanceOf(ConferenceProvisioningFileBuilderImpl.class));
    }

    @Test
    public void initialize_deviceDesk_shouldReturnDeskBuilder() {
        ProvisioningFileBuilder fileBuilder = this.factory.initialize(Device.DeviceModel.DESK);

        Assert.assertThat(fileBuilder, instanceOf(DeskProvisioningFileBuilderImpl.class));
    }
}
