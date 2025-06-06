package com.rev.aoc.problems.y2024;

import com.rev.aoc.framework.io.load.LoaderUtils;
import com.rev.aoc.framework.problem.AocCoordinate;
import com.rev.aoc.framework.problem.AocProblem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class D19 extends AocProblem<Long, Long> {

    public static final int REGEX_MAX_LENGTH = 100;

    @Override
    public AocCoordinate getCoordinate() {
        return new AocCoordinate(2024, 19);
    }

    @SuppressWarnings("checkstyle:MagicNumber")
    @Override
    protected Long partOneImpl() {
        List<String> strings = loadResources();
        Set<String>[] regexes = LoaderUtils.emptyArray(new HashSet[0], 100, () -> new HashSet());

        int start = 0;
        while (start < strings.size() && !strings.get(start).isEmpty()) {
            String[] split = strings.get(start).replaceAll("\\s+", "").split(",");
            for (int i = 0; i < split.length; i++) {
                int len = split[i].length();
                regexes[len - 1].add(split[i]);
            }
            start++;
        }

        int end = regexes.length;
        while (end > 0 && regexes[end - 1].isEmpty()) {
            end--;
        }
        regexes = Arrays.copyOfRange(regexes, 0, end);

        start++;
        List<String> targets = new ArrayList<>();
        while (start < strings.size()) {
            targets.add(strings.get(start));
            start++;
        }

        long count = 0;
        for (int i = 0; i < targets.size(); i++) {
            if (canDesign(targets.get(i), regexes) != 0) {
                count++;
            }
        }
        return count;
    }

    @Override
    protected Long partTwoImpl() {
        List<String> strings = loadResources();
        Set<String>[] regexes = LoaderUtils.emptyArray(new HashSet[0], REGEX_MAX_LENGTH, () -> new HashSet());

        int start = 0;
        while (start < strings.size() && !strings.get(start).isEmpty()) {
            String[] split = strings.get(start).replaceAll("\\s+", "").split(",");
            for (int i = 0; i < split.length; i++) {
                int len = split[i].length();
                regexes[len - 1].add(split[i]);
            }
            start++;
        }

        int end = regexes.length;
        while (end > 0 && regexes[end - 1].isEmpty()) {
            end--;
        }
        regexes = Arrays.copyOfRange(regexes, 0, end);

        start++;
        List<String> targets = new ArrayList<>();
        while (start < strings.size()) {
            targets.add(strings.get(start));
            start++;
        }

        long count = 0;
        for (int i = 0; i < targets.size(); i++) {
            count += canDesign(targets.get(i), regexes);
        }
        return count;
    }

    private long canDesign(final String target,
                              final Set<String>[] regexes) {
        Long[] cachedResults = LoaderUtils.emptyArray(new Long[0], target.length(), () -> null);
        return canDesign(target, regexes, cachedResults, 0);
    }
    private long canDesign(final String target,
                              final Set<String>[] regexes,
                              final Long[] cachedResults,
                              final int offset) {
        if (offset == target.length()) {
            return 1;
        }

        if (cachedResults[offset] != null) {
            return cachedResults[offset];
        } else {
            cachedResults[offset] = 0L;
        }

        int end = offset + 1;
        while (end < offset + 1 + regexes.length && end <= target.length()) {
            long designsAtEnd = canDesign(target, regexes, cachedResults, end);
            if (designsAtEnd != 0
                    && regexes[end - offset - 1].contains(target.substring(offset, end))) {
                cachedResults[offset] = cachedResults[offset] + designsAtEnd;
            }
            end++;
        }
        return cachedResults[offset];
    }
}
