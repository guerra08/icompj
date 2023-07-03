package com.guerra08;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "icompj",
    mixinStandardHelpOptions = true,
    description = "icompj is a tool to help you compress images in the terminal.",
    version = "0.0.1")
public class IcompjCommand implements Runnable {

    @Parameters(paramLabel = "<path>", description = "Path of the file or directory. Currently accepts [jpg/jpeg and png] images.")
    String path;

    @Option(names = {"-l", "--level"}, description = "The level of quality, from 0.0 (highest compression) to 1.0 (highest quality). 0.8 is the default.")
    float qualityLevel = 0.8f;

    @Override
    public void run() {
        Core.compress(path, qualityLevel);
    }

}
