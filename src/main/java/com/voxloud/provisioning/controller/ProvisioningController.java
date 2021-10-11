package com.voxloud.provisioning.controller;

import com.voxloud.provisioning.service.ProvisioningService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProvisioningController {
    private final ProvisioningService provisioningService;

    @GetMapping(value = "/provisioning/{macAddress}")
    public ResponseEntity<String> getProvisioning(@PathVariable(value = "macAddress") String macAddress) {
        String provisioning = this.provisioningService.getProvisioningFile(macAddress);

        if (provisioning == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(provisioning);
    }

}