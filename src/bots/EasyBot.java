package bots;

public class EasyBot {

    public static int placeAtPosition(String[][] gamePosition, int dimension) {
        int majorDiagonalCount = 0;
        for (int i = 0; i < dimension; i++) {
            if (gamePosition[i][i].equalsIgnoreCase("X")) {
                majorDiagonalCount++;
            }
        }
        int minorDiagonalCount = 0;
        for (int i = 0; i < dimension; i++) {
            if (gamePosition[dimension - i - 1][i].equalsIgnoreCase("X")) {
                minorDiagonalCount++;
            }
        }
        if (majorDiagonalCount == (dimension - 1)) {
            for (int i = 0; i < dimension; i++) {
                for (int j = 0; j < dimension; j++) {
                    if (i != j) {
                        if (gamePosition[i][j].equalsIgnoreCase("-")) {
                            gamePosition[i][j] = "O";
                            return 1;
                        }
                    }
                }
            }
        }

        if (minorDiagonalCount == (dimension - 1)) {
            for (int i = 0; i < dimension; i++) {
                for (int j = 0; j < dimension; j++) {
                    if (!(i == (dimension - i - 1))) {
                        if (gamePosition[i][j].equalsIgnoreCase("-")) {
                            gamePosition[i][j] = "O";
                            return 1;
                        }
                    }
                }
            }
        }

        int maxStreakInRow = -1;
        int rowMax = Integer.MIN_VALUE;
        for (int i = 0; i < dimension; i++) {
            int currentCount = 0;
            for (int j = 0; j < dimension; j++) {
                if (gamePosition[i][j].equalsIgnoreCase("X")) {
                    currentCount++;
                }
            }
            if (currentCount > rowMax) {
                rowMax = currentCount;
                maxStreakInRow = i;
            }
        }

        int maxStreakInCol = -1;
        int colMax = Integer.MIN_VALUE;
        for (int i = 0; i < dimension; i++) {
            int currentCount = 0;
            for (int j = 0; j < dimension; j++) {
                if (gamePosition[j][i].equalsIgnoreCase("X")) {
                    currentCount++;
                }
            }
            if (currentCount > colMax) {
                colMax = currentCount;
                maxStreakInCol = i;
            }
        }
        int rowOrCol = -1;
        if (rowMax > colMax) {
            rowOrCol = maxStreakInRow;
            for (int i = 0; i < dimension; i++) {
                for (int j = 0; j < dimension; j++) {
                    if (i != rowOrCol) {
                        if (gamePosition[i][j].equalsIgnoreCase("-")) {
                            gamePosition[i][j] = "O";
                            return 1;
                        }
                    }
                }
            }
        } else {
            rowOrCol = maxStreakInCol;
            for (int i = 0; i < dimension; i++) {
                for (int j = 0; j < dimension; j++) {
                    if (j != rowOrCol) {
                        if (gamePosition[i][j].equalsIgnoreCase("-")) {
                            gamePosition[i][j] = "O";
                            return 1;
                        }
                    }
                }
            }
        }
        return 0;
    }
}
