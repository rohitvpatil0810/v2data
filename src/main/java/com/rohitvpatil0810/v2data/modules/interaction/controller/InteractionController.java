package com.rohitvpatil0810.v2data.modules.interaction.controller;

import com.rohitvpatil0810.v2data.common.api.exceptions.BadRequestException;
import com.rohitvpatil0810.v2data.common.api.responses.ApiResponse;
import com.rohitvpatil0810.v2data.common.api.responses.SuccessResponse;
import com.rohitvpatil0810.v2data.modules.interaction.dto.InteractionResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadLocalRandom;

@RestController
public class InteractionController {

    @PostMapping("/interact")
    ApiResponse<InteractionResponse> echoMessage(@RequestBody InteractionResponse body) throws BadRequestException {
        boolean toThrowError = ThreadLocalRandom.current().nextBoolean();

        if(toThrowError) {
            throw new BadRequestException("Randomly throwing error....");
        }

        InteractionResponse res = InteractionResponse.builder().message(body.getMessage()).build();

        return new SuccessResponse<>("Success", res);
    }
}
