package com.rohitvpatil0810.v2data.modules.cloudflareR2.controller;

import com.rohitvpatil0810.v2data.common.api.responses.ApiResponse;
import com.rohitvpatil0810.v2data.common.api.responses.SuccessResponse;
import com.rohitvpatil0810.v2data.modules.cloudflareR2.CloudflareR2ConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/cloudflare-r2")
public class CloudflareR2Controller {

    @Autowired
    private CloudflareR2ConfigProperties cloudflareR2Config;

    @GetMapping("/config")
    public ApiResponse<CloudflareR2ConfigProperties> getConfig() {
        return new SuccessResponse<CloudflareR2ConfigProperties>("Cloudflare R2 config fetched successfully", cloudflareR2Config);
    }
}
