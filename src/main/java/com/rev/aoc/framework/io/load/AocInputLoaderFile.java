package com.rev.aoc.framework.io.load;

import com.rev.aoc.framework.problem.AocCoordinate;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public final class AocInputLoaderFile implements AocInputLoader {

    private final String basePath = String.format("%s/%s", System.getProperty("user.dir"), "problems");

    @Override
    public List<String> load(final AocCoordinate coordinate) throws IOException {
        String fullyQualifiedName = String.format("%s/y%s/D%s.txt",
                basePath,
                coordinate.getYear(),
                AocInputLoader.pad(coordinate.getDay()));
        return load(fullyQualifiedName);
    }
    private List<String> load(final String filePath) throws IOException {

        List<String> lines = new ArrayList<>();
        InputStream is = null;
        try {
            is = new FileInputStream(filePath);
            if (is == null) {
                throw new FileNotFoundException(filePath);
            }
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader r = new BufferedReader(isr);
            String line = r.readLine();
            while (line != null) {
                lines.add(line);
                line = r.readLine();
            }
            return lines;
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }
}
