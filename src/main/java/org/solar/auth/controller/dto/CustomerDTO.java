package org.solar.auth.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerDTO {


    String username;

    String mode;

    public String getUsername(){
        return username;
    }

    @JsonProperty("gdef")
    public String getCdef(){
        return username;
    }
    @JsonProperty("mode")
    public String getMode() {
        return mode;
    }

    @JsonProperty("ggg")
    public void setMode(String mode) {
        this.mode = mode;
    }
}
