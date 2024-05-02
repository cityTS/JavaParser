package cli.code_parser.java;

import com.beust.jcommander.Parameter;

public class CommandOptions {
    @Parameter(names = { "--java" }, description = "Java文件路径", required = true)
    private String sourcePath;

    @Parameter(names = { "--cache" }, description = "缓存文件路径", required = true)
    private String outputPath;

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }
}
