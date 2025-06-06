package com.rev.aoc.problems.y2015;

import com.rev.aoc.framework.problem.AocCoordinate;
import com.rev.aoc.framework.problem.AocProblem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class D14 extends AocProblem<Long, Long> {

    private static final Pattern NUMBER_PATTERN = Pattern.compile("\\d+");
    private static final Pattern NAME_PATTERN = Pattern.compile("^\\w+");
    public static final int RACE_DURATION = 2503;

    @Override
    @SuppressWarnings("checkstyle:MagicNumber")
    public AocCoordinate getCoordinate() {
        return new AocCoordinate(2015, 14);
    }

    @Override
    protected Long partOneImpl() {
        List<Reindeer> reindeer = loadReindeer();
        long winningDistance = 0;
        for (Reindeer r : reindeer) {
            final long distance = r.distanceTravelled(RACE_DURATION);
            if (distance > winningDistance) {
                winningDistance = distance;
            }
        }
        return winningDistance;
    }

    @Override
    protected Long partTwoImpl() {
        List<Reindeer> reindeer = loadReindeer();
        final Map<String, Long> scores = new HashMap<>();
        reindeer.forEach(r -> scores.putIfAbsent(r.name, 0L));

        for (int i = 1; i <= RACE_DURATION; i++) {
            long winningDistance = 0;
            Set<String> winningReindeer = new HashSet<>();
            for (Reindeer r : reindeer) {
                final long distance = r.distanceTravelled(i);
                if (distance > winningDistance) {
                    winningDistance = distance;
                    winningReindeer.clear();
                    winningReindeer.add(r.name);
                } else if (distance == winningDistance) {
                    winningReindeer.add(r.name);
                }
            }

            winningReindeer.forEach(s -> {
                final long nextScore = scores.get(s) + 1;
                scores.put(s, nextScore);
            });
        }
        return scores.values().stream().max(Long::compare).orElseThrow();
    }

    private List<Reindeer> loadReindeer() {
        List<String> lines = loadResources();
        final List<Reindeer> reindeer = new ArrayList<>(lines.size());
        for (final String line : lines) {
            final Matcher nameMatcher = NAME_PATTERN.matcher(line);
            nameMatcher.find();
            final String name = nameMatcher.group();


            Matcher m = NUMBER_PATTERN.matcher(line);
            m.find();
            final long speed = Long.parseLong(m.group());
            m.find();
            final long flightDuration = Long.parseLong(m.group());
            m.find();
            final long restDuration = Long.parseLong(m.group());
            reindeer.add(new Reindeer(name, speed, flightDuration, restDuration));
        }
        return reindeer;
    }

    private static final class Reindeer {
        private final String name;
        private final long speed;
        private final long flightDuration;
        private final long restDuration;

        private Reindeer(final String name, long speed, long flightDuration, long restDuration) {
            this.name = name;
            this.speed = speed;
            this.flightDuration = flightDuration;
            this.restDuration = restDuration;
        }

        private long distanceTravelled(long time) {
            final long blocks = time / (flightDuration + restDuration);
            final long remainder = Math.min(flightDuration, time % (flightDuration + restDuration));

            return (blocks * flightDuration + remainder) * speed;
        }
    }
}
