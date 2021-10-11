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
public class DeskProvisioningFileBuilderTests {
    @Autowired
    @Qualifier("deskFileBuilder")
    private ProvisioningFileBuilder provisioningFileBuilder;

    private Device device;

    @Before
    public void init() {
        this.device = new Device();
        device.setUsername("testuser");
        device.setPassword("pass");
        device.setModel(Device.DeviceModel.DESK);
    }

    @Test
    public void build_deviceDeskNoOverriding_shouldReturnProcessed() {
        String result = this.provisioningFileBuilder.build(this.device);

        Assert.assertNotNull(result);
        Assert.assertThat(result, CoreMatchers.containsString("sip.voxloud.com"));
        Assert.assertThat(result, CoreMatchers.containsString("port=5060"));
        Assert.assertThat(result, CoreMatchers.containsString("username=testuser"));
        Assert.assertThat(result, CoreMatchers.containsString("password=pass"));
        Assert.assertThat(result, CoreMatchers.containsString("codecs=[G711, G729, OPUS]"));
    }

    @Test
    public void build_deviceDeskWithOverriding_shouldReturnProcessed() {
        this.device.setOverrideFragment("domain=sip.anotherdomain.com\nport=5161\ntimeout=10");

        String result = this.provisioningFileBuilder.build(this.device);

        Assert.assertNotNull(result);
        Assert.assertThat(result, CoreMatchers.containsString("sip.anotherdomain.com"));
        Assert.assertThat(result, CoreMatchers.containsString("port=5161"));
        Assert.assertThat(result, CoreMatchers.containsString("username=testuser"));
        Assert.assertThat(result, CoreMatchers.containsString("password=pass"));
        Assert.assertThat(result, CoreMatchers.containsString("timeout=10"));
        Assert.assertThat(result, CoreMatchers.containsString("codecs=[G711, G729, OPUS]"));
    }
}
