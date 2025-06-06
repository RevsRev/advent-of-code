package com.rev.aoc.problems.y2024;

import com.rev.aoc.framework.io.load.LoaderUtils;
import com.rev.aoc.framework.problem.AocCoordinate;
import com.rev.aoc.framework.problem.AocProblem;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashSet;
import java.util.Set;

public final class D10 extends AocProblem<Long, Long> {
    private static final int[][] DIRECTIONS = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

    @Override
    public AocCoordinate getCoordinate() {
        return new AocCoordinate(2024, 10);
    }

    @Override
    protected Long partOneImpl() {
        int[][] map = LoaderUtils.loadResourcesAsIntMatrix(loadResources(), s -> s.split(""));
        int height = map.length;
        int width = map[0].length;

        long score = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (map[i][j] == 0) {
                    Set<Pair<Integer, Integer>> ninesReached = new HashSet<>();
                    computeTrail(ninesReached, map, height, width, i, j, 0);
                    score += ninesReached.size();
                }
            }
        }
        return score;
    }

    @Override
    protected Long partTwoImpl() {
        int[][] map = LoaderUtils.loadResourcesAsIntMatrix(loadResources(), s -> s.split(""));
        int height = map.length;
        int width = map[0].length;

        long score = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (map[i][j] == 0) {
                    score += computeTrail(new HashSet<>(), map, height, width, i, j, 0);
                }
            }
        }
        return score;
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    private long computeTrail(final Set<Pair<Integer, Integer>> ninesReached,
                              final int[][] map, int height, int width, int i, int j, int trailHeight) {
        if (trailHeight == 9) {
            ninesReached.add(Pair.of(i, j));
            return 1;
        }
        long score = 0;
        for (int dirIndex = 0; dirIndex < DIRECTIONS.length; dirIndex++) {
            int x = i + DIRECTIONS[dirIndex][0];
            int y = j + DIRECTIONS[dirIndex][1];
            if (x < 0 || x >= height || y < 0 || y >= width) {
                continue;
            }
            if (map[x][y] == trailHeight + 1) {
                score += computeTrail(ninesReached, map, height, width, x, y, trailHeight + 1);
            }
        }
        return score;
    }
}
