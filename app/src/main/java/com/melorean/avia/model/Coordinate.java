package com.melorean.avia.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Coordinate {
    private BigDecimal lon;
    private BigDecimal lat;
}
