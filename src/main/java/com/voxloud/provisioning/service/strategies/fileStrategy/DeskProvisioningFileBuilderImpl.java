package com.voxloud.provisioning.service.strategies.fileStrategy;

import com.voxloud.provisioning.entity.Device;
import com.voxloud.provisioning.models.DeviceFileModel;
import com.voxloud.provisioning.service.deviceFileModelAssembler.DeviceFileModelAssembler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import java.util.stream.Collectors;

@Component("deskFileBuilder")
@Log4j2
@RequiredArgsConstructor
public class DeskProvisioningFileBuilderImpl implements ProvisioningFileBuilder {
    private final DeviceFileModelAssembler fileModelAssembler;

    @Override
    public String build(Device device) {
        try {
            DeviceFileModel deviceFileModel = this.fileModelAssembler.toModel(device);
            Properties properties = toProperties(deviceFileModel);
            return properties.entrySet()
                    .stream()
                    .map(e -> e.getKey() + "=" + e.getValue())
                    .collect(Collectors.joining("\n"));
        } catch (IllegalAccessException | IntrospectionException | InvocationTargetException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    private Properties toProperties(DeviceFileModel deviceFileModel) throws IntrospectionException, InvocationTargetException, IllegalAccessException {

        BeanInfo beanInfo = Introspector.getBeanInfo(deviceFileModel.getClass());
        Properties properties = new Properties();

        for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
            String name = pd.getName();
            Object o = pd.getReadMethod().invoke(deviceFileModel);
            if (o != null && !name.equals("class"))
                properties.setProperty(name, o.toString());
        }
        return properties;
    }
}
