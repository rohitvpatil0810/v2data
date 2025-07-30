package com.rohitvpatil0810.v2data.modules.v2DataTranscriber.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class V2DataTranscriberResponseBody {
    String structuredNotes;
    String fullTranscript;
}
