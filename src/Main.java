import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        GameOfLife gameOfLife = new GameOfLife(getRandomCells(1337));
        while (true){
            System.out.println(gameOfLife.render(-10, -10, 50, 30));
            gameOfLife = gameOfLife.next();
        }
    }

    private static List<Cell> getRandomCells(long seed) {
        ArrayList<Cell> cells = new ArrayList<>();
        Random random = new Random(seed);
        for (int i = 0; i < 100; i++) {
            cells.add(new Cell(random.nextInt(0,21), random.nextInt(0,21)));
        }
        return cells.stream().distinct().toList();
    }
}
