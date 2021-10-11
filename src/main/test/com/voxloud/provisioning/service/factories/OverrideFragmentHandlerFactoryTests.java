package com.voxloud.provisioning.service.factories;

import com.voxloud.provisioning.entity.Device;
import com.voxloud.provisioning.service.factories.overrideFragmentHandlerFactory.OverrideFragmentHandlerFactory;
import com.voxloud.provisioning.service.strategies.overrideFragmentStrategy.ConferenceOverrideFragmentHandlerImpl;
import com.voxloud.provisioning.service.strategies.overrideFragmentStrategy.DeskOverrideFragmentHandlerImpl;
import com.voxloud.provisioning.service.strategies.overrideFragmentStrategy.OverrideFragmentHandler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.core.IsInstanceOf.instanceOf;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OverrideFragmentHandlerFactoryTests {
    private Device device;

    @Before
    public void init() {
        this.device = new Device();
    }

    @Test
    public void initialize_deviceConference_shouldReturnConferenceBuilder() {
        this.device.setModel(Device.DeviceModel.CONFERENCE);
        OverrideFragmentHandler fileBuilder = OverrideFragmentHandlerFactory.initialize(this.device);

        Assert.assertThat(fileBuilder, instanceOf(ConferenceOverrideFragmentHandlerImpl.class));
    }

    @Test
    public void initialize_deviceDesk_shouldReturnDeskBuilder() {
        this.device.setModel(Device.DeviceModel.DESK);
        OverrideFragmentHandler fileBuilder = OverrideFragmentHandlerFactory.initialize(this.device);

        Assert.assertThat(fileBuilder, instanceOf(DeskOverrideFragmentHandlerImpl.class));
    }
}
