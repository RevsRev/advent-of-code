package com.rev.aoc;

import com.rev.aoc.framework.problem.AocCoordinate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

public final class AocCoordinateTest {

    @ParameterizedTest
    @MethodSource("getFailureCases")
    public void testFailureCases(final String invalid) {
        Assertions.assertNull(AocCoordinate.parse(invalid));
    }

    @ParameterizedTest
    @MethodSource("getSuccessCases")
    public void testSuccessCases(final AocCoordinateSuccessTestCase successTestCase) {
        AocCoordinate coord = AocCoordinate.parse(successTestCase.str);
        Assertions.assertEquals(successTestCase.year, coord.getYear());
        Assertions.assertEquals(successTestCase.day, coord.getDay());
    }

    public static List<String> getFailureCases() {
        return List.of(
                "2022:ab",
                "202:01",
                "2024:012",
                "cd:12",
                "20:a0"
        );
    }

    public static List<AocCoordinateSuccessTestCase> getSuccessCases() {
        return List.of(
                new AocCoordinateSuccessTestCase("2022:12", 2022, 12),
                new AocCoordinateSuccessTestCase("2022:3", 2022, 3),
                new AocCoordinateSuccessTestCase("2022:03", 2022, 3),
                new AocCoordinateSuccessTestCase("19:12", 2019, 12),
                new AocCoordinateSuccessTestCase("19:09", 2019, 9),
                new AocCoordinateSuccessTestCase("19:9", 2019, 9)
        );
    }

    private static final class AocCoordinateSuccessTestCase {
        private final String str;
        private final int year;
        private final int day;

        private AocCoordinateSuccessTestCase(final String str, final int year, final int day) {
            this.str = str;
            this.year = year;
            this.day = day;
        }
    }
}
