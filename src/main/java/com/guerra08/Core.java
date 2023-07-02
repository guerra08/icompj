package com.guerra08;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class Core {

    private static final String SUFFIX = "comp";

    // TODO: Clean this up
    public static void compress(String pathString, float qualityLevel) throws IOException {
        var path = Paths.get(pathString);
        if (Files.isRegularFile(path)) {
            var file = path.toFile();
            var input = ImageIO.read(file);
            var outputStream = new FileOutputStream(buildOutputFileName(pathString));

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
        }
    }

    private static String buildOutputFileName(String pathString) {
        return getNameOfFile(pathString) + "." + SUFFIX + "." + getExtensionOfFile(pathString);
    }

    private static String getNameOfFile(String pathString) {
        return pathString.substring(0, pathString.lastIndexOf("."));
    }

    private static String getExtensionOfFile(String pathString) {
        var segments = pathString.split("\\.");
        return segments[segments.length - 1];
    }

}
