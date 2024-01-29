package com.iche.sco.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class InnerData {
    @JsonProperty("authorization_url")
    private String authorizationUrl;
    @JsonProperty("access_code")
    private String accessCode;
    private String reference;
}
