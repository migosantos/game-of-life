package com.bgl.gol.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class World {
    private Cell[][] grid;

    public World(int rows, int columns) {
        this.grid = new Cell[rows][columns];
        for(int i=0; i<rows; i++) {
            for(int j=0; j<columns; j++) {
                setCellState(i, j, false);
            }
        }
    }

    public void setCellState(int row, int column, boolean isAlive) {
        this.grid[row][column] = Cell.builder()
                .rowNum(row)
                .colNum(column)
                .isAlive(isAlive)
                .build();
    }
}
