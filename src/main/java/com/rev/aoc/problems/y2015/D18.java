package com.rev.aoc.problems.y2015;

import com.rev.aoc.framework.io.load.LoaderUtils;
import com.rev.aoc.framework.problem.AocCoordinate;
import com.rev.aoc.framework.problem.AocProblem;
import com.rev.aoc.util.grid.Grid;

public final class D18 extends AocProblem<Long, Long> {

    private static final int ITERATIONS = 100;
    private static final char ALIVE = '#';
    private static final char DEAD = '.';

    @Override
    @SuppressWarnings("checkstyle:MagicNumber")
    public AocCoordinate getCoordinate() {
        return new AocCoordinate(2015, 18);
    }

    @Override
    protected Long partOneImpl() {
        char[][] chars = LoaderUtils.linesToCharMatrix(loadResources());
        char[][] updated = Grid.stateComputer(chars, gridConwaysLifeUpdater(false), ITERATIONS);
        return Grid.countGrid(updated, ALIVE);
    }

    @Override
    protected Long partTwoImpl() {
        char[][] chars = LoaderUtils.linesToCharMatrix(loadResources());

        final int height = chars.length;
        final int width = chars[0].length;
        chars[0][0] = ALIVE;
        chars[0][width - 1] = ALIVE;
        chars[height - 1][0] = ALIVE;
        chars[height - 1][width - 1] = ALIVE;

        char[][] updated = Grid.stateComputer(chars, gridConwaysLifeUpdater(true), ITERATIONS);
        return Grid.countGrid(updated, ALIVE);
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    private Grid.GridUpdater gridConwaysLifeUpdater(boolean fixCorners) {
        return (i, j, grid) -> {
            final int height = grid.length;
            final int width = grid[0].length;

            if (fixCorners) {
                if ((i == 0 && j == 0)
                        || (i == 0 && j == width - 1)
                        || (i == height - 1 && j == 0)
                        || (i == height - 1 && j == height - 1)) {
                    return ALIVE;
                }
            }

            int liveNeighboursCount = 0;
            for (int a = -1; a <= 1; a++) {
                for (int b = -1; b <= 1; b++) {
                    if (a == 0 && b == 0) {
                        continue;
                    }
                    if (i + a < 0 || i + a >= height || j + b < 0 || j + b >= width) {
                        continue;
                    }
                    if (grid[i + a][j + b] == ALIVE) {
                        liveNeighboursCount++;
                    }
                }
            }

            if (grid[i][j] == ALIVE) {
                if (liveNeighboursCount == 2 || liveNeighboursCount == 3) {
                    return ALIVE;
                }
                return DEAD;
            }

            if (liveNeighboursCount == 3) {
                return ALIVE;
            }
            return DEAD;
        };
    }
}
