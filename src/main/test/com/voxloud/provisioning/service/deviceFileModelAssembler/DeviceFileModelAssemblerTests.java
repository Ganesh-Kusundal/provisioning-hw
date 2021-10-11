package com.voxloud.provisioning.service.deviceFileModelAssembler;

import com.voxloud.provisioning.entity.Device;
import com.voxloud.provisioning.models.DeviceFileModel;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeviceFileModelAssemblerTests {
    @Autowired
    private DeviceFileModelAssembler deviceFileModelAssembler;

    @Test
    public void toModel_devicePresentDeskNoOverriderFragment_shouldReturnProcessedModel() {
        Device device = new Device();
        device.setModel(Device.DeviceModel.DESK);
        device.setPassword("pass");
        device.setUsername("testName");

        DeviceFileModel result = this.deviceFileModelAssembler.toModel(device);

        Assert.assertNotNull(result);
        Assert.assertEquals(result.getDomain(), "sip.voxloud.com");
        Assert.assertEquals(result.getPort(), "5060");
        Assert.assertNull(result.getTimeout());
        Assert.assertEquals(result.getPassword(), "pass");
        Assert.assertEquals(result.getUsername(), "testName");
        Assert.assertArrayEquals(result.getCodecs().toArray(), new String[]{"G711", "G729", "OPUS"});
    }

    @Test
    public void toModel_devicePresentDeskWithOverriderFragment_shouldReturnProcessedModel() {
        Device device = new Device();
        device.setModel(Device.DeviceModel.DESK);
        device.setPassword("pass");
        device.setUsername("testName");
        device.setOverrideFragment("domain=sip.anotherdomain.com\nport=5161\ntimeout=10");

        DeviceFileModel result = this.deviceFileModelAssembler.toModel(device);

        Assert.assertNotNull(result);
        Assert.assertEquals(result.getDomain(), "sip.anotherdomain.com");
        Assert.assertEquals(result.getPort(), "5161");
        Assert.assertSame(result.getTimeout(), 10);
        Assert.assertEquals(result.getPassword(), "pass");
        Assert.assertEquals(result.getUsername(), "testName");
        Assert.assertArrayEquals(result.getCodecs().toArray(), new String[]{"G711", "G729", "OPUS"});
    }

    @Test
    public void toModel_devicePresentConferenceNoOverriderFragment_shouldReturnProcessedModel() {
        Device device = new Device();
        device.setModel(Device.DeviceModel.CONFERENCE);
        device.setPassword("pass");
        device.setUsername("testName");

        DeviceFileModel result = this.deviceFileModelAssembler.toModel(device);

        Assert.assertNotNull(result);
        Assert.assertEquals(result.getDomain(), "sip.voxloud.com");
        Assert.assertEquals(result.getPort(), "5060");
        Assert.assertNull(result.getTimeout());
        Assert.assertEquals(result.getPassword(), "pass");
        Assert.assertEquals(result.getUsername(), "testName");
        Assert.assertArrayEquals(result.getCodecs().toArray(), new String[]{"G711", "G729", "OPUS"});
    }

    @Test
    public void toModel_devicePresentConferenceWithOverriderFragment_shouldReturnProcessedModel() {
        Device device = new Device();
        device.setModel(Device.DeviceModel.CONFERENCE);
        device.setPassword("pass");
        device.setUsername("testName");
        device.setOverrideFragment("{\"domain\":\"sip.anotherdomain.com\",\"port\":\"5161\",\"timeout\":10}");

        DeviceFileModel result = this.deviceFileModelAssembler.toModel(device);

        Assert.assertNotNull(result);
        Assert.assertEquals(result.getDomain(), "sip.anotherdomain.com");
        Assert.assertEquals(result.getPort(), "5161");
        Assert.assertSame(result.getTimeout(), 10);
        Assert.assertEquals(result.getPassword(), "pass");
        Assert.assertEquals(result.getUsername(), "testName");
        Assert.assertArrayEquals(result.getCodecs().toArray(), new String[]{"G711", "G729", "OPUS"});
    }
}
