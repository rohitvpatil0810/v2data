package com.rohitvpatil0810.v2data.modules.interaction.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InteractionResponse {
    private String fileName;
    private String notes;
}
