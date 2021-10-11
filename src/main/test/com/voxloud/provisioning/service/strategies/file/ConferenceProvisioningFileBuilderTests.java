package com.voxloud.provisioning.service.strategies.file;

import com.voxloud.provisioning.entity.Device;
import com.voxloud.provisioning.service.strategies.fileStrategy.ProvisioningFileBuilder;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConferenceProvisioningFileBuilderTests {
    @Autowired
    @Qualifier("conferenceFileBuilder")
    private ProvisioningFileBuilder provisioningFileBuilder;

    private Device device;

    @Before
    public void init() {
        this.device = new Device();
        device.setUsername("testuser");
        device.setPassword("pass");
        device.setModel(Device.DeviceModel.CONFERENCE);
    }

    @Test
    public void build_deviceDeskNoOverriding_shouldReturnProcessed() {
        String result = this.provisioningFileBuilder.build(this.device);

        Assert.assertNotNull(result);
        Assert.assertThat(result, CoreMatchers.containsString("sip.voxloud.com"));
        Assert.assertThat(result, CoreMatchers.containsString("5060"));
        Assert.assertThat(result, CoreMatchers.containsString("testuser"));
        Assert.assertThat(result, CoreMatchers.containsString("pass"));
        Assert.assertThat(result, CoreMatchers.containsString("codecs\":[\"G711\",\"G729\",\"OPUS\"]"));
    }

    @Test
    public void build_deviceDeskWithOverriding_shouldReturnProcessed() {
        this.device.setOverrideFragment("{\"domain\":\"sip.anotherdomain.com\",\"port\":\"5161\",\"timeout\":10}");

        String result = this.provisioningFileBuilder.build(this.device);

        Assert.assertNotNull(result);
        Assert.assertThat(result, CoreMatchers.containsString("sip.anotherdomain.com"));
        Assert.assertThat(result, CoreMatchers.containsString("5161"));
        Assert.assertThat(result, CoreMatchers.containsString("testuser"));
        Assert.assertThat(result, CoreMatchers.containsString("pass"));
        Assert.assertThat(result, CoreMatchers.containsString("10"));
        Assert.assertThat(result, CoreMatchers.containsString("codecs\":[\"G711\",\"G729\",\"OPUS\"]"));
    }
}
