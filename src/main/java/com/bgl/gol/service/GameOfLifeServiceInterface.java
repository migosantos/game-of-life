package com.bgl.gol.service;

import com.bgl.gol.com.bgl.gol.InvalidInputException;
import com.bgl.gol.dto.Cell;
import com.bgl.gol.dto.World;

public interface GameOfLifeServiceInterface {
    World setInitialAliveCells(World world, String coordinates) throws InvalidInputException;

    int countAliveNeighbors(World world, Cell cell);

    World applyRulesForOneGeneration(World world);

    World generateNTimes(World world, int numberOfTimes);

    void printWorld(World world, int worldNo);
}
