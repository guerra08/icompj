package com.guerra08;

import picocli.CommandLine.Help.Ansi;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;
import java.util.function.Predicate;

import static com.guerra08.Constants.ACCEPTED_FORMATS;
import static com.guerra08.Constants.COMPRESSION_SUFFIX;
import static com.guerra08.FileUtils.getExtensionOfFile;
import static com.guerra08.FileUtils.getNameOfFile;

public final class Core {

    public static void compress(String pathString, float qualityLevel) {
        try {
            FileUtils.getPaths(pathString)
                .parallelStream()
                .filter(hasValidFormatPredicate())
                .forEach(p -> compressFile(p, qualityLevel));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void compressFile(Path path, float qualityLevel) {
        try {
            var outputStream = new FileOutputStream(buildOutputFileName(path.toString()));
            ImageWriter writer = getImageWriter(getExtensionOfFile(path.toString()));

            ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(outputStream);
            writer.setOutput(imageOutputStream);

            writer.write(null,
                new IIOImage(ImageIO.read(path.toFile()), null, null),
                configureWriteParam(qualityLevel, writer)
            );

            outputStream.close();
            imageOutputStream.close();
            writer.dispose();
        } catch (Exception e) {
            String errorMessage = Ansi.AUTO.string("@|red,bold Unable to compress file " + path + ".|@");
            System.out.println(errorMessage);
            throw new RuntimeException(e);
        }

    }

    public static Predicate<Path> hasValidFormatPredicate() {
        return hasValidFormatPredicate(ACCEPTED_FORMATS);
    }

    public static Predicate<Path> hasValidFormatPredicate(Set<String> formats) {
        return p -> formats.contains(getExtensionOfFile(p.toString()));
    }

    private static String buildOutputFileName(String pathString) {
        return getNameOfFile(pathString) + "." + COMPRESSION_SUFFIX + "." + getExtensionOfFile(pathString);
    }

    private static ImageWriter getImageWriter(String format) {
        var writers = ImageIO.getImageWritersByFormatName(format);
        return writers.next();
    }

    private static ImageWriteParam configureWriteParam(float qualityLevel, ImageWriter writer) {
        ImageWriteParam param = writer.getDefaultWriteParam();

        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(qualityLevel);
        return param;
    }

}
