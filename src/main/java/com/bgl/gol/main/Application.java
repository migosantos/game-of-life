package com.bgl.gol.main;

import com.bgl.gol.com.bgl.gol.InvalidInputException;
import com.bgl.gol.dto.World;
import com.bgl.gol.service.GameOfLifeService;
import com.bgl.gol.service.GameOfLifeServiceInterface;

import java.util.Scanner;

public class Application {
    private GameOfLifeServiceInterface gameOfLifeService;
    private static final int ROW_SIZE = 200;
    private static final int COLUMN_SIZE = 200;
    private static final int GENERATION_TIMES = 100;

    public Application() {
        gameOfLifeService = new GameOfLifeService();
    }

    public String askForInput() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Please enter Alive cells coordinates. Example of coordinates input format is [[5, 5], [6, 5], [7, 5], [5, 6], [6, 6], [7, 6]]");
        String coordinates = sc.nextLine();

        return coordinates;
    }

    public World initializeWorld(String aliveCoordinates) throws InvalidInputException {
        World world = new World(ROW_SIZE,COLUMN_SIZE);
        return gameOfLifeService.setInitialAliveCells(world, aliveCoordinates);
    }

    public World generateNTimes(World world, int numberOfTimes) {
        return gameOfLifeService.generateNTimes(world, numberOfTimes);
    }

    public static void main(String[] args) {
        Application application = new Application();
        World world = null;

        do {
            String aliveCoordinates = application.askForInput();

            try {
                world = application.initializeWorld(aliveCoordinates);
            } catch (InvalidInputException e) {
                System.out.println("Invalid Input, please try again");
            }
        } while(world == null);

        System.out.println("Output of the next 100 state:");
        application.generateNTimes(world, GENERATION_TIMES);
    }
}
