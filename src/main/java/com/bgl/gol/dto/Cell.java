package com.bgl.gol.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Cell {
    int rowNum;
    int colNum;
    boolean isAlive;
}
