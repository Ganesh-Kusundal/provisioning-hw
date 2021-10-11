package com.voxloud.provisioning.service;

import com.voxloud.provisioning.entity.Device;
import com.voxloud.provisioning.repository.DeviceRepository;
import com.voxloud.provisioning.service.factories.fileFactory.ProvisioningFileBuilderFactory;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProvisioningServiceTests {
    @Autowired
    private ProvisioningFileBuilderFactory factory;
    @Mock
    private DeviceRepository deviceRepository;
    private ProvisioningService provisioningService;

    @Before
    public void init() {
        this.provisioningService = new ProvisioningServiceImpl(this.deviceRepository, this.factory);
    }

    @Test
    public void getProvisioningFile_devicePresentDeskNoOverriderFragment_shouldReturnProcessedDesk() {
        Device device = new Device();
        device.setMacAddress("aa-bb-cc-dd");
        device.setModel(Device.DeviceModel.DESK);
        device.setPassword("pass");
        device.setUsername("testName");

        when(this.deviceRepository.getOne(device.getMacAddress())).thenReturn(device);

        String result = this.provisioningService.getProvisioningFile(device.getMacAddress());

        Assert.assertNotNull(result);
        Assert.assertThat(result, CoreMatchers.containsString("sip.voxloud.com"));
        Assert.assertThat(result, CoreMatchers.containsString("port=5060"));
        Assert.assertThat(result, CoreMatchers.containsString("username=testName"));
        Assert.assertThat(result, CoreMatchers.containsString("password=pass"));
        Assert.assertThat(result, CoreMatchers.containsString("codecs=[G711, G729, OPUS]"));
    }

    @Test
    public void getProvisioningFile_devicePresentDeskWithOverrideFragments_shouldReturnProcessedDeskWithOverride() {
        Device device = new Device();
        device.setMacAddress("aa-bb-cc-dd");
        device.setModel(Device.DeviceModel.DESK);
        device.setPassword("pass");
        device.setUsername("testName");
        device.setOverrideFragment("domain=sip.anotherdomain.com\nport=5161\ntimeout=10");

        when(this.deviceRepository.getOne(device.getMacAddress())).thenReturn(device);

        String result = this.provisioningService.getProvisioningFile(device.getMacAddress());

        Assert.assertNotNull(result);
        Assert.assertThat(result, CoreMatchers.containsString("sip.anotherdomain.com"));
        Assert.assertThat(result, CoreMatchers.containsString("port=5161"));
        Assert.assertThat(result, CoreMatchers.containsString("username=testName"));
        Assert.assertThat(result, CoreMatchers.containsString("password=pass"));
        Assert.assertThat(result, CoreMatchers.containsString("timeout=10"));
        Assert.assertThat(result, CoreMatchers.containsString("codecs=[G711, G729, OPUS]"));
    }

    @Test
    public void getProvisioningFile_devicePresentConferencNohOverrideFragments_shouldReturnProcessedConference() {
        Device device = new Device();
        device.setMacAddress("aa-bb-cc-dd");
        device.setModel(Device.DeviceModel.CONFERENCE);
        device.setPassword("pass");
        device.setUsername("testName");

        when(this.deviceRepository.getOne(device.getMacAddress())).thenReturn(device);

        String result = this.provisioningService.getProvisioningFile(device.getMacAddress());

        Assert.assertNotNull(result);
        Assert.assertThat(result, CoreMatchers.containsString("sip.voxloud.com"));
        Assert.assertThat(result, CoreMatchers.containsString("5060"));
        Assert.assertThat(result, CoreMatchers.containsString("testName"));
        Assert.assertThat(result, CoreMatchers.containsString("pass"));
        Assert.assertThat(result, CoreMatchers.containsString("codecs\":[\"G711\",\"G729\",\"OPUS\"]"));
    }

    @Test
    public void getProvisioningFile_devicePresentConferenceWithOverrideFragments_shouldReturnProcessedConferenceWithOverride() {
        Device device = new Device();
        device.setMacAddress("aa-bb-cc-dd");
        device.setModel(Device.DeviceModel.CONFERENCE);
        device.setPassword("pass");
        device.setUsername("testName");
        device.setOverrideFragment("{\"domain\":\"sip.anotherdomain.com\",\"port\":\"5161\",\"timeout\":10}");

        when(this.deviceRepository.getOne(device.getMacAddress())).thenReturn(device);

        String result = this.provisioningService.getProvisioningFile(device.getMacAddress());

        Assert.assertNotNull(result);
        Assert.assertThat(result, CoreMatchers.containsString("sip.anotherdomain.com"));
        Assert.assertThat(result, CoreMatchers.containsString("5161"));
        Assert.assertThat(result, CoreMatchers.containsString("testName"));
        Assert.assertThat(result, CoreMatchers.containsString("pass"));
        Assert.assertThat(result, CoreMatchers.containsString("10"));
        Assert.assertThat(result, CoreMatchers.containsString("codecs\":[\"G711\",\"G729\",\"OPUS\"]"));
    }

    @Test
    public void getProvisioningFile_devicenotFound_shouldReturnNull() {
        when(this.deviceRepository.getOne(anyString())).thenReturn(null);

        String result = this.provisioningService.getProvisioningFile("tt-11-11-aa");

        Assert.assertNull(result);
    }

    @Test
    public void getProvisioningFile_devicenotFound_shouldThrow() {
        when(this.deviceRepository.getOne(anyString())).thenThrow(new EntityNotFoundException());

        String result = this.provisioningService.getProvisioningFile("tt-11-11-aa");

        Assert.assertNull(result);
    }
}
