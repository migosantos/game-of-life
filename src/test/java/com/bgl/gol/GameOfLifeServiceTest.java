package com.bgl.gol;

import com.bgl.gol.com.bgl.gol.InvalidInputException;
import com.bgl.gol.dto.Cell;
import com.bgl.gol.dto.World;
import com.bgl.gol.service.GameOfLifeService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameOfLifeServiceTest {	
    
    private GameOfLifeService gameOfLifeService = new GameOfLifeService();
    private static final int ROW_SIZE = 200;
    private static final int COLUMN_SIZE = 200;

    @Test
    public void testSetInitialAliveCells() throws InvalidInputException {
        World world = new World(ROW_SIZE,COLUMN_SIZE);
        String aliveCoordinates = "[[5, 5], [6, 5], [7, 5], [5, 6], [6, 6], [7, 6]]";
        gameOfLifeService.setInitialAliveCells(world, aliveCoordinates);
        assertTrue(world.getGrid()[5][5].isAlive());
        assertTrue(world.getGrid()[6][5].isAlive());
        assertTrue(world.getGrid()[7][5].isAlive());
        assertTrue(world.getGrid()[5][6].isAlive());
        assertTrue(world.getGrid()[6][6].isAlive());
        assertTrue(world.getGrid()[7][6].isAlive());
        assertFalse(world.getGrid()[100][100].isAlive());
    }

    @Test
    public void testCountAliveNeighbors() throws InvalidInputException {
        World world = new World(ROW_SIZE,COLUMN_SIZE);
        String aliveCoordinates = "[[5, 5], [6, 5], [7, 5], [8, 6]";
        gameOfLifeService.setInitialAliveCells(world, aliveCoordinates);
        assertTrue(gameOfLifeService.countAliveNeighbors(world, Cell.builder().rowNum(5).colNum(5).build()) == 1);
        assertTrue(gameOfLifeService.countAliveNeighbors(world, Cell.builder().rowNum(6).colNum(5).build()) == 2);
        assertTrue(gameOfLifeService.countAliveNeighbors(world, Cell.builder().rowNum(7).colNum(5).build()) == 2);
        assertTrue(gameOfLifeService.countAliveNeighbors(world, Cell.builder().rowNum(8).colNum(6).build()) == 1);
    }

    @Test
    public void testUnderPopulation() throws InvalidInputException {
        World world = new World(ROW_SIZE,COLUMN_SIZE);
        String aliveCoordinates = "[[5, 5]]";
        gameOfLifeService.setInitialAliveCells(world, aliveCoordinates);
        assertTrue(gameOfLifeService.countAliveNeighbors(world, Cell.builder().rowNum(5).colNum(5).build()) == 0);

        World newWorld = gameOfLifeService.applyRulesForOneGeneration(world);
        assertFalse(newWorld.getGrid()[5][5].isAlive());

    }

    @Test
    public void testOverPopulation() throws InvalidInputException {
        World world = new World(ROW_SIZE,COLUMN_SIZE);
        String aliveCoordinates = "[[5, 5], [5, 6], [5, 4], [4, 5], [6, 5]]";
        gameOfLifeService.setInitialAliveCells(world, aliveCoordinates);
        assertTrue(gameOfLifeService.countAliveNeighbors(world, Cell.builder().rowNum(5).colNum(5).build()) == 4);

        World newWorld = gameOfLifeService.applyRulesForOneGeneration(world);
        assertFalse(newWorld.getGrid()[5][5].isAlive());

    }

    @Test
    public void testLiveUntilNextGeneration() throws InvalidInputException {
        World world = new World(ROW_SIZE,COLUMN_SIZE);
        String aliveCoordinates = "[[5, 5], [5, 6], [5, 4], [4, 5]]";
        gameOfLifeService.setInitialAliveCells(world, aliveCoordinates);
        assertTrue(gameOfLifeService.countAliveNeighbors(world, Cell.builder().rowNum(5).colNum(5).build()) == 3);

        World newWorld = gameOfLifeService.applyRulesForOneGeneration(world);
        assertTrue(newWorld.getGrid()[5][5].isAlive());

    }

    @Test
    public void testReproduction() throws InvalidInputException {
        World world = new World(ROW_SIZE,COLUMN_SIZE);
        String aliveCoordinates = "[[5, 5], [6, 5], [7, 5]]";
        gameOfLifeService.setInitialAliveCells(world, aliveCoordinates);
        assertFalse(world.getGrid()[6][6].isAlive());
        assertTrue(gameOfLifeService.countAliveNeighbors(world, Cell.builder().rowNum(6).colNum(6).build()) == 3);

        World newWorld = gameOfLifeService.applyRulesForOneGeneration(world);
        assertTrue(newWorld.getGrid()[6][6].isAlive());

    }

    @Test
    public void testInvalidInput()  {
        World world = new World(ROW_SIZE,COLUMN_SIZE);
        String aliveCoordinates = "invalidInput123";
        try {
            gameOfLifeService.setInitialAliveCells(world, aliveCoordinates);
        } catch (InvalidInputException e) {
            assertTrue(e != null);
        }

    }
}