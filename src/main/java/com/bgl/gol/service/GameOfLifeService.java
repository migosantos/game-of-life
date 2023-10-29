package com.bgl.gol.service;

import com.bgl.gol.com.bgl.gol.InvalidInputException;
import com.bgl.gol.dto.Cell;
import com.bgl.gol.dto.World;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class GameOfLifeService implements GameOfLifeServiceInterface {
    @Override
    public World setInitialAliveCells(World world, String coordinates) throws InvalidInputException {
        if(coordinates == null) {
            throw new InvalidInputException();
        }

        // Parse string to remove all brackets
        coordinates = coordinates.replace(" ","")
                .replace("[", "")
                .replace("]", "");
        // Split the string into integers. Coordinates should come as pairs so the iteration below is +2
        String[] coordinatesArray = coordinates.split(",");

        if(coordinatesArray.length % 2 != 0) {
            throw new InvalidInputException();
        }

        for(int i=0; i<coordinatesArray.length; i+=2) {
            if(!validateCoordinates(coordinatesArray[i], coordinatesArray[i+1], world.getGrid().length)) {
                throw new InvalidInputException();
            }
            int row = Integer.valueOf(coordinatesArray[i]);
            int column = Integer.valueOf(coordinatesArray[i+1]);
            world.setCellState(row, column, true);
        }

        return world;
    }

    @Override
    public int countAliveNeighbors(World world, Cell cell) {
        int row = cell.getRowNum();
        int column = cell.getColNum();
        int totalAliveNeighbors = 0;

        // Neighbors are 1 space away from the cell
        for (int rowDelta=-1; rowDelta<=1; rowDelta++) {
            for (int columnDelta=-1; columnDelta<=1; columnDelta++) {
                // Don't count self
                if (rowDelta == 0 && columnDelta == 0) {
                    continue;
                }

                int neighborRow = row+rowDelta;
                int neighborColumn = column+columnDelta;
                Cell[][] grid = world.getGrid();
                // Check if we don't go array out of bounds first before checking if neighbor is alive
                if (neighborRow >= 0 && neighborRow < grid.length
                        && neighborColumn >= 0 && neighborColumn < grid[row].length
                        && grid[neighborRow][neighborColumn].isAlive()) {
                    totalAliveNeighbors++;
                }
            }
        }
        return totalAliveNeighbors;
    }

    @Override
    public World applyRulesForOneGeneration(World oldWorld) {
        Cell[][] oldGrid = oldWorld.getGrid();
        World newWorld = new World(oldGrid.length, oldGrid.length);

        for(int row=0; row<oldGrid.length; row++) {
            for(int column=0; column<oldGrid[row].length; column++) {
                Cell currentCell = oldGrid[row][column];
                int aliveNeighbors = countAliveNeighbors(oldWorld, currentCell);

                if(currentCell.isAlive()) {
                    if(aliveNeighbors < 2 || aliveNeighbors > 3) {
                        newWorld.setCellState(row, column, false);
                    } else {
                        newWorld.setCellState(row, column, true);
                    }
                } else {
                    if(aliveNeighbors == 3) {
                        newWorld.setCellState(row, column, true);
                    }
                }
            }
        }

        return newWorld;
    }

    @Override
    public World generateNTimes(World world, int numberOfTimes) {
        for(int i=1; i<=numberOfTimes; i++) {
            world = applyRulesForOneGeneration(world);
            printWorld(world, i);
        }
        return world;
    }

    @Override
    public void printWorld(World world, int worldNo) {
        List<String> printList = new ArrayList<String>();
        Cell[][] grid = world.getGrid();
        for(int row=0; row<grid.length; row++) {
            for(int column=0; column<grid[row].length; column++) {
                if(grid[row][column].isAlive()) {
                    printList.add(new StringBuilder().append("[").append(row).append(",").append(column).append("]").toString());
                }
            }
        }
        System.out.println(new StringBuilder().append(worldNo).append(" : ").append(printList));
    }

    private boolean isStringIntegerValid(String str, int max) {
        // If str is non numeric or greater than 199, return false
        if(!StringUtils.isNumeric(str) || Integer.valueOf(str) > max - 1 || Integer.valueOf(str ) < 0) {
            return false;
        }

        return true;
    }

    private boolean validateCoordinates(String x, String y, int max) {
        return isStringIntegerValid(x, max) && isStringIntegerValid(y, max);
    }
}
