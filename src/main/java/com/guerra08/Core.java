package com.guerra08;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class Core {

    private static final String COMPRESSION_SUFFIX = "comp";

    public static void compress(String pathString, float qualityLevel) {
        var path = Paths.get(pathString);
        if (Files.isRegularFile(path)) {
            compressFile(path, qualityLevel);
        } else {
            try (var paths = Files.walk(path)) {
                // For multiple files, we can check for usage of parallelism, while also maintaining good performance and CPU usage
                paths
                    .filter(Files::isRegularFile)
                    .forEach(p -> compressFile(p, qualityLevel));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void compressFile(Path path, float qualityLevel) {
        try {
            var file = path.toFile();
            var input = ImageIO.read(file);
            var outputStream = new FileOutputStream(buildOutputFileName(path.toString()));

            var writers = ImageIO.getImageWritersByFormatName("jpg");
            var writer = (ImageWriter) writers.next();

            ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(outputStream);
            writer.setOutput(imageOutputStream);

            ImageWriteParam param = writer.getDefaultWriteParam();

            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(qualityLevel);
            writer.write(null, new IIOImage(input, null, null), param);

            outputStream.close();
            imageOutputStream.close();
            writer.dispose();
        } catch (Exception e) {
            // Maybe log here?
            throw new RuntimeException(e);
        }

    }

    private static String buildOutputFileName(String pathString) {
        return getNameOfFile(pathString) + "." + COMPRESSION_SUFFIX + "." + getExtensionOfFile(pathString);
    }

    private static String getNameOfFile(String pathString) {
        return pathString.substring(0, pathString.lastIndexOf("."));
    }

    private static String getExtensionOfFile(String pathString) {
        var segments = pathString.split("\\.");
        return segments[segments.length - 1];
    }

}
