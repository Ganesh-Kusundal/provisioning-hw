package com.voxloud.provisioning.service.strategies.overrideFragments;

import com.voxloud.provisioning.models.DeviceFileModel;
import com.voxloud.provisioning.service.strategies.overrideFragmentStrategy.DeskOverrideFragmentHandlerImpl;
import com.voxloud.provisioning.service.strategies.overrideFragmentStrategy.OverrideFragmentHandler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DeskOverrideFragmentHandlerTests {
    private OverrideFragmentHandler overrideFragmentHandler;

    @Before
    public void init() {
        this.overrideFragmentHandler = new DeskOverrideFragmentHandlerImpl();
    }

    @Test
    public void handle_modelPresentJsonCorrect_shouldProcess() {
        DeviceFileModel deviceFileModel = new DeviceFileModel();
        String overrideFragment = "domain=sip.anotherdomain.com\nport=5161\ntimeout=10";
        this.overrideFragmentHandler.handle(deviceFileModel, overrideFragment);

        Assert.assertEquals(deviceFileModel.getDomain(), "sip.anotherdomain.com");
        Assert.assertEquals(deviceFileModel.getPort(), "5161");
        Assert.assertSame(deviceFileModel.getTimeout(), 10);
    }

    @Test
    public void handle_modelAbsent_shouldNotProcess() {
        DeviceFileModel deviceFileModel = new DeviceFileModel();
        String overrideFragment = "domain=sip.anotherdomain.com\nport=5161\ntimeout=10";
        this.overrideFragmentHandler.handle(null, overrideFragment);

        Assert.assertNull(deviceFileModel.getDomain());
        Assert.assertNull(deviceFileModel.getPort());
        Assert.assertNull(deviceFileModel.getTimeout());
    }

    @Test
    public void handle_fragmentAbsent_shouldNotProcess() {
        DeviceFileModel deviceFileModel = new DeviceFileModel();
        this.overrideFragmentHandler.handle(deviceFileModel, null);

        Assert.assertNull(deviceFileModel.getDomain());
        Assert.assertNull(deviceFileModel.getPort());
        Assert.assertNull(deviceFileModel.getTimeout());
    }

    @Test
    public void handle_fragmentIncorrect_shouldNotProcessExceptionCatched() {
        DeviceFileModel deviceFileModel = new DeviceFileModel();
        String overrideFragment = "{\"domain\":\"sip.anotherdomain.com\",\"port\":\"5161\",\"timeout\":10}";
        this.overrideFragmentHandler.handle(deviceFileModel, overrideFragment);

        Assert.assertNull(deviceFileModel.getDomain());
        Assert.assertNull(deviceFileModel.getPort());
        Assert.assertNull(deviceFileModel.getTimeout());
    }

}
