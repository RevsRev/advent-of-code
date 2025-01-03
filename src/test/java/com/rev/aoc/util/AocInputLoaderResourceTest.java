package com.rev.aoc.util;

import com.rev.aoc.framework.io.load.AocInputLoaderResource;
import com.rev.aoc.framework.problem.AocCoordinate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.List;

public final class AocInputLoaderResourceTest {

    private static final String RESOURCE_READER_TEST_DIRECTORY = "src/test/resources/";

    @Test
    public void testLoadResourceExists() {
        try {
            AocInputLoaderResource reader = new AocInputLoaderResource();
            List<String> lines = reader.load(new AocCoordinate(2010, 1));
            Assertions.assertEquals(
                    List.of("This problem does not exist.", "This file is used to test loading resources!"), lines);
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }

    @Test
    public void testLoadResourceNotExists() {
        Assertions.assertThrows(FileNotFoundException.class,
                () -> (new AocInputLoaderResource()).load(new AocCoordinate(2010, 2)));
    }

}
