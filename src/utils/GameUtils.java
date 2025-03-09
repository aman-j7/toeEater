package utils;

public class GameUtils {
    public static boolean isStringEmptyAndBlank(String string) {
        return string == null || string.isBlank();
    }

    public static int[] PositionToMatrixCoOrdinates(int position, int dimension) {
        return new int[]{(position / dimension),
                (position % dimension)};
    }

    public static int matrixToPositionCoOrdinates(int row, int col, int dimension) {
        return row * dimension + col + 1;
    }
}
