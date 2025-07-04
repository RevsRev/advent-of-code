package com.rev.aoc.problems.y2016;

import com.rev.aoc.framework.problem.AocCoordinate;
import com.rev.aoc.framework.problem.AocProblem;
import com.rev.aoc.util.geom.Triangle;

import java.util.List;

public final class D03 extends AocProblem<Integer, Integer> {

    @Override
    @SuppressWarnings("checkstyle:MagicNumber")
    public AocCoordinate getCoordinate() {
        return new AocCoordinate(2016, 3);
    }

    @Override
    protected Integer partOneImpl() {
        int count = 0;
        List<String> maybeTriangles = loadResources();
        for (final String maybeTriangle : maybeTriangles) {
            String[] sides = maybeTriangle.trim().split("\\s+");
            final long a = Long.parseLong(sides[0]);
            final long b = Long.parseLong(sides[1]);
            final long c = Long.parseLong(sides[2]);
            if (Triangle.validTriangle(a, b, c)) {
                count++;
            }
        }
        return count;
    }

    @Override
    protected Integer partTwoImpl() {
        int count = 0;
        List<String> maybeTriangles = loadResources();
        for (int i = 0; i < maybeTriangles.size(); i += 3) {
            final String first = maybeTriangles.get(i);
            final String second = maybeTriangles.get(i + 1);
            final String third = maybeTriangles.get(i + 2);

            String[] as = first.trim().split("\\s+");
            final long a1 = Long.parseLong(as[0]);
            final long a2 = Long.parseLong(as[1]);
            final long a3 = Long.parseLong(as[2]);

            String[] bs = second.trim().split("\\s+");
            final long b1 = Long.parseLong(bs[0]);
            final long b2 = Long.parseLong(bs[1]);
            final long b3 = Long.parseLong(bs[2]);

            String[] cs = third.trim().split("\\s+");
            final long c1 = Long.parseLong(cs[0]);
            final long c2 = Long.parseLong(cs[1]);
            final long c3 = Long.parseLong(cs[2]);

            if (Triangle.validTriangle(a1, b1, c1)) {
                count++;
            }
            if (Triangle.validTriangle(a2, b2, c2)) {
                count++;
            }
            if (Triangle.validTriangle(a3, b3, c3)) {
                count++;
            }
        }
        return count;
    }
}
