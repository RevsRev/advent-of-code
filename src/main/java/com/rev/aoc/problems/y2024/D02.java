package com.rev.aoc.problems.y2024;

import com.rev.aoc.framework.problem.AocCoordinate;
import com.rev.aoc.framework.problem.AocProblem;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

public final class D02 extends AocProblem<Long, Long> {
    @Override
    public AocCoordinate getCoordinate() {
        return new AocCoordinate(2024, 2);
    }

    @Override
    protected Long partOneImpl() {
        Integer[][] reports = parseReports();
        List<Integer[]> safeReports = new ArrayList<>();
        getSafeReportsNoDampening(reports, safeReports, new ArrayList<>());
        return (long) safeReports.size();
    }

    @Override
    protected Long partTwoImpl() {
        Integer[][] reports = parseReports();
        List<Integer[]> safeReports = new ArrayList<>();
        List<Integer[]> unsafeReports = new ArrayList<>();
        List<Integer[]> dampedReports = new ArrayList<>();
        getSafeReportsNoDampening(reports, safeReports, unsafeReports);
        getSafeReportsDampening(unsafeReports, dampedReports);

        return (long) (safeReports.size() + dampedReports.size());
    }

    private static void getSafeReportsNoDampening(final Integer[][] reports,
                                                  final List<Integer[]> safeReports,
                                                  final List<Integer[]> unsafeReports) {
        for (int i = 0; i < reports.length; i++) {
            if (checkReportNoDampening(reports[i])) {
                safeReports.add(reports[i]);
            } else {
                unsafeReports.add(reports[i]);
            }
        }
    }

    private static void getSafeReportsDampening(final List<Integer[]> unsafeReports,
                                                final List<Integer[]> dampedReports) {
        for (int i = 0; i < unsafeReports.size(); i++) {
            if (checkReportDampening(unsafeReports.get(i))) {
                dampedReports.add(unsafeReports.get(i));
            }
        }
    }

    private static boolean checkReportDampening(final Integer[] report) {
        for (int i = 0; i < report.length; i++) {
            if (checkReportNoDampening(ArrayUtils.remove(report, i))) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkReportNoDampening(final Integer[] report) {
        boolean increasing = report[1] > report[0];
        for (int i = 0; i < report.length - 1; i++) {
            int current = report[i];
            int next = report[i + 1];

            if (increasing) {
                if (current >= next) {
                    return false;
                }
                if (next - current > 3) {
                    return false;
                }
            } else {
                if (current <= next) {
                    return false;
                }
                if (current - next > 3) {
                    return false;
                }
            }
        }
        return true;
    }

    private Integer[][] parseReports() {
        List<String> fileLines = loadResources();
        Integer[][] retval = new Integer[fileLines.size()][];
        for (int i = 0; i < fileLines.size(); i++) {
            String[] split = fileLines.get(i).split("\\s+");
            retval[i] = new Integer[split.length];
            for (int j = 0; j < split.length; j++) {
                retval[i][j] = Integer.parseInt(split[j]);
            }
        }
        return retval;
    }
}
