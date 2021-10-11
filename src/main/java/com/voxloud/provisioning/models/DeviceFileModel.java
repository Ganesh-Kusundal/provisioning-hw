package com.voxloud.provisioning.models;

import lombok.Data;

import java.util.List;

@Data
public class DeviceFileModel {
    private String username;
    private String password;
    private String domain;
    private String port;
    private List<String> codecs;
    private Integer timeout;
}
