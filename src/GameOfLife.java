import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Stream.empty;
import static java.util.stream.Stream.of;

public record GameOfLife(List<Cell> cells) {

    public GameOfLife next() {
        final var nextGen = cells.stream()
                .flatMap(GameOfLife::spread)
                .collect(Collectors.toMap(cell -> cell, cell -> 1, Integer::sum))
                .entrySet().stream()
                .flatMap(cellIntegerEntry -> {
                    Cell spreadCell = cellIntegerEntry.getKey();
                    Integer neighbours = cellIntegerEntry.getValue();
                    boolean alive = cells.contains(spreadCell);

                    if (alive) {
                        return neighbours == 2 || neighbours == 3 ? of(spreadCell) : empty();
                    } else {
                        return neighbours == 3 ? of(spreadCell) : empty();
                    }
                })
                .toList();
        return new GameOfLife(nextGen);
    }

    private static Stream<Cell> spread(Cell cell) {
        return of(
                new Cell(cell.x() - 1, cell.y() - 1),
                new Cell(cell.x() - 1, cell.y()),
                new Cell(cell.x() - 1, cell.y() + 1),
                new Cell(cell.x(), cell.y() - 1),
                new Cell(cell.x(), cell.y() + 1),
                new Cell(cell.x() + 1, cell.y() - 1),
                new Cell(cell.x() + 1, cell.y()),
                new Cell(cell.x() + 1, cell.y() + 1)
        );
    }

    public String render(int x, int y, int w, int h) {

        List<String> render = new ArrayList<>();
        for (int i = 0; i < h; i++) {
            render.add(" ".repeat(w));
        }
        cells.forEach(cell -> {
            try {
                String replace = render.remove(cell.y() - y);
                StringBuilder myString = new StringBuilder(replace);
                myString.setCharAt(cell.x() - x, 'O');
                render.add(cell.y() - y, myString.toString());
            } catch (Exception e) {
            }
        });
        return String.join("\n", render) +"\n" + "-".repeat(w);
    }
}
