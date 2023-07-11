package com.guerra08;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public final class FileUtils {
    private FileUtils() {
    }

    public static List<Path> getPaths(String path) throws IOException {
        Objects.requireNonNull(path);
        Path p = Paths.get(path);
        if (Files.isRegularFile(p)) {
            return List.of(p);
        }
        try (var paths = Files.walk(p)) {
            return paths.toList();
        }
    }

    public static String getNameOfFile(String pathString) {
        Objects.requireNonNull(pathString);
        return pathString.substring(0, pathString.lastIndexOf("."));
    }

    public static String getExtensionOfFile(String pathString) {
        Objects.requireNonNull(pathString);
        var segments = pathString.split("\\.");
        return segments[segments.length - 1];
    }
}
