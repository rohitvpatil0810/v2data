package com.rohitvpatil0810.v2data.modules.interaction.dto;

import com.rohitvpatil0810.v2data.modules.v2DataTranscriber.dto.V2DataTranscriberResponseBody;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InteractionResponse {
    private String fileURL;
    private String notes;
    private V2DataTranscriberResponseBody v2DataTranscriberResponse;
}
