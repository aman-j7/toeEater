//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import bots.EasyBot;
import utils.GameUtils;

import java.util.*;

import static utils.GameUtils.isStringEmptyAndBlank;
import static java.lang.System.exit;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to the Game!!!");
        System.out.println("You have 3 change to start the game.");
        System.out.println("To start the game type (s) and to quit type (q) : ");
        int openCloseGame = 1;
        String winner = gameLoop(openCloseGame);
        if (winner.equals("Not started")) {
            System.out.println("Game not started!!!");
        } else if (winner.equals("Both Lost")) {
            System.out.println("Both Player Lost No One wins, to restart again type s or to quite type q");
            gameLoop(1);
        } else {
            System.out.println("Player " + winner + " wins the game!");
        }
        exit(1);
    }

    private static String gameLoop(int openCloseGame) {
        Scanner sc = new Scanner(System.in);
        while (openCloseGame <= 3) {
            String gameState = sc.next();
            switch (gameState) {
                case "s":
                    System.out.println("Provide the dimension for which you want to play : ");
                    int dimension = sc.nextInt();
                    String winner = startGame(dimension);
                    if (winner.equals("Both Lost")) {
                        System.out.println("Both Player Lost No One wins, to restart again type s or to quite type q :");
                        gameLoop(1);
                    } else {
                        return winner;
                    }
                case "q":
                    endGame();
                default:
                    System.out.println("Please type 's' to start and 'q' to quit");
                    System.out.println("remaining attempt to start game " + (3 - openCloseGame));
                    openCloseGame += 1;
            }
        }
        return "Not started";
    }

    private static void endGame() {
        System.out.println("Thanks for playing!");
        exit(1);
    }

    private static String startGame(int dimension) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Lord ship who you want to challenge a mere human or machine : type 'H' for human or machine type 'M' for machine");
        String challenger = sc.next();
        String machineName = null;
        if (challenger.equalsIgnoreCase("M")) {
            System.out.println("you will be playing against Cyberdyne Bot T-800");
            machineName = "Cyberdyne";
        }
        int totalState = dimension * dimension;
        String[][] gamePosition = new String[dimension][dimension];
        for (int row = 0; row < dimension; row++) {
            Arrays.fill(gamePosition[row], "-");
        }
        System.out.println("Game floor looks like : ");
        boolean playersTurn = true;
        int status;
        int coOrdinatePosition = 0;
        int checkWinnerAfterMoves = dimension + (dimension - 1) - 1;
        String winner = null;
        while (totalState > 0) {
            printCurrentState(gamePosition);
            int movesTillNow = dimension * dimension - totalState;
            if (movesTillNow >= checkWinnerAfterMoves) {
                winner = checkForWinner(gamePosition, coOrdinatePosition - 1, dimension);
                if (Objects.nonNull(winner)) {
                    return winner;
                }
            }

            System.out.println(playersTurn ? "Player A to play :" : isStringEmptyAndBlank(machineName)
                    ? "Player B to play enter position :"
                    : "Machine " + machineName + " played ");

            if (!isStringEmptyAndBlank(machineName) && !playersTurn) {
//                status = EasyBot.placeAtPosition(gamePosition, dimension);
                status = placeAtPosition(gamePosition,
                        getHardBotNextMove(gamePosition,dimension,totalState) - 1
                        , dimension, playersTurn);
            } else {
                coOrdinatePosition = sc.nextInt();
                status = placeAtPosition(gamePosition, coOrdinatePosition - 1, dimension, playersTurn);
            }

            if (status == -1) {
                System.out.println("already that position is filled please check the game status\n and place provide the untouched position");
            } else if (status == 0) {
                if (!isStringEmptyAndBlank(machineName)) {
                    System.out.println("Machine was not able to play the move,terminator got terminated.");
                } else {
                    System.out.println("position is not correct choose between 1 To " + dimension * dimension);
                }
            } else {
                playersTurn = !playersTurn;
                totalState -= 1;
            }
        }

        printCurrentState(gamePosition);
        winner = "Both Lost";
        return winner;
    }

    private static int placeAtPosition(String[][] gamePosition, int coOrdinatePosition, int dimension, boolean playerAToPlay) {
        int gameFloorLen = gamePosition.length;
        int gameFloorWid = gamePosition[0].length;
        int rowPosition = (coOrdinatePosition / dimension);
        int colPosition = (coOrdinatePosition % dimension);
        if (rowPosition >= gameFloorLen || colPosition >= gameFloorWid) {
            return 0;
        }
        if (rowPosition < 0 || colPosition < 0) {
            return 0;
        }
        if (!gamePosition[rowPosition][colPosition].equals("-")) {
            return -1;
        }
        gamePosition[rowPosition][colPosition] = playerAToPlay ? "X" : "O";
        return 1;
    }

    private static void printCurrentState(String[][] gamePosition) {
        for (String[] strings : gamePosition) {
            for (int col = 0; col < gamePosition[0].length; col++) {
                System.out.print(strings[col] + "  ");
            }
            System.out.println();
        }
    }

    private static String checkForWinner(String[][] gamePosition, int coOrdinatePosition, int dimension) {
        int rowPosition = (coOrdinatePosition / dimension);
        int colPosition = (coOrdinatePosition % dimension);

        int xCountRow = 0, xCountCol = 0, oCountCol = 0, oCountRow = 0;

        for (int i = 0; i < dimension; i++) {
            if (gamePosition[rowPosition][i].equals("X")) {
                xCountRow++;
            } else if (gamePosition[rowPosition][i].equals("O")) {
                oCountRow++;
            }
            if (gamePosition[i][colPosition].equals("X")) {
                xCountCol++;
            } else if (gamePosition[i][colPosition].equals("O")) {
                oCountCol++;
            }
        }

        if (xCountRow == dimension || xCountCol == dimension) {
            return "A";
        } else if (oCountRow == dimension || oCountCol == dimension) {
            return "B";
        }

        xCountRow = 0;
        xCountCol = 0;
        oCountCol = 0;
        oCountRow = 0;

        boolean shouldCheckMajorDiagonal = (rowPosition == colPosition);
        boolean shouldCheckMinorDiagonal = (rowPosition + colPosition) == dimension - 1;

        if (shouldCheckMajorDiagonal) {
            for (int i = 0; i < dimension; i++) {
                if (gamePosition[i][i].equals("X")) {
                    xCountRow++;
                } else if (gamePosition[i][i].equals("O")) {
                    oCountRow++;
                }
            }
        }

        if (shouldCheckMinorDiagonal) {
            for (int i = 0; i < dimension; i++) {
                if (gamePosition[dimension - 1 - i][i].equals("X")) {
                    xCountCol++;
                } else if (gamePosition[dimension - 1 - i][i].equals("O")) {
                    oCountCol++;
                }
            }
        }

        if (xCountRow == dimension || xCountCol == dimension) {
            return "A";
        } else if (oCountRow == dimension || oCountCol == dimension) {
            return "B";
        }

        return null;
    }

    static int getHardBotNextMove(String[][] gamePosition, int dimension, int totalMovesLeft) {
        int result = -1, calCol = -1, calRow = -1;
        for (int row = 0; row < dimension; row++) {
            for (int col = 0; col < dimension; col++) {
                if (gamePosition[row][col].equals("-")) {
                    int value = minMax(gamePosition, true, dimension, totalMovesLeft - 1, row, col);
                    if(value > result) {
                       calCol = col;
                       calRow = row;
                    }
                }
            }
        }

        return GameUtils.matrixToPositionCoOrdinates(calRow,calCol,dimension) ;
    }

    static int minMax(String[][] gamePosition, boolean isMax, int dimension
            , int totalMovesLeft, int curRow, int curCol) {

        if(totalMovesLeft < 1){
            return 0;
        }

        gamePosition[curRow][curCol] = isMax ? "O" : "X";
        int curCoOrdinatePosition = GameUtils.matrixToPositionCoOrdinates(curRow, curCol, dimension);
        String winner = checkForWinner(gamePosition, curCoOrdinatePosition - 1, dimension);
        if (Objects.nonNull(winner)) {
            gamePosition[curRow][curCol] = "-";
            if (winner.equals("B")) {
                return 1;
            } else if (winner.equals("A")) {
                return -1;
            }
        }

        int bestValue = 0;

        for (int row = 0; row < dimension; row++) {
            for (int col = 0; col < dimension; col++) {
                if (gamePosition[row][col].equals("-")) {
                    int value = minMax(gamePosition, !isMax, dimension, totalMovesLeft - 1, row, col);
                    if (isMax) {
                        bestValue = Math.max(bestValue,value);
                    } else{
                        bestValue = Math.min(bestValue,value);
                    }
                }
            }
        }

        gamePosition[curRow][curCol] = "-";
        return bestValue;
    }

}