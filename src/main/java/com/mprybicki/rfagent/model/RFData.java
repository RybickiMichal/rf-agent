package com.mprybicki.rfagent.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Min;

@AllArgsConstructor
@Getter
public class RFData {

    @Min(0)
    double rssi;

    @Min(0)
    Double altitude;

    @Min(0)
    double middleFrequency;
}
