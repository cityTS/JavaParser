package cli.code_parser.java;

import com.beust.jcommander.JCommander;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class Main {
    private static CommandOptions initCommandOptions(String[] args) {
        CommandOptions commandOptions = new CommandOptions();
        JCommander commander = JCommander.newBuilder()
                .addObject(commandOptions)
                .build();
        commander.parse(args);
        return commandOptions;
    }

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        CommandOptions commandOptions = initCommandOptions(args);
        new JavaCodeParser(commandOptions).parse();
    }
}
