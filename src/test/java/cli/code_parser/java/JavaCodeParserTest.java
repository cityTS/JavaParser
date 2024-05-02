package cli.code_parser.java;

import cli.code_parser.java.model.CacheModel;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class JavaCodeParserTest {
    @Test
    public void testParseJavaFile() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        CommandOptions commandOptions = new CommandOptions();
        JavaCodeParser javaCodeParser = new JavaCodeParser(commandOptions);
        Method parseJavaFile = JavaCodeParser.class.getDeclaredMethod("parseJavaFile", File.class);
        parseJavaFile.setAccessible(true);

        CacheModel cacheModel = (CacheModel) parseJavaFile.invoke(javaCodeParser, new File("C:\\Users\\sct\\IdeaProjects\\untitled\\src\\main\\resources\\FixBug.java"));

        System.out.println(cacheModel);
    }

    @Test
    public void testGetPackage() {
        CommandOptions commandOptions = new CommandOptions();
        JavaCodeParser javaCodeParser = new JavaCodeParser(commandOptions);
        System.out.println(javaCodeParser.getJavaPackage(new File("C:\\Users\\sct\\IdeaProjects\\untitled\\src\\main\\resources\\FixBug.java")));
    }

    @Test
    public void testGetJavaImport() {
        CommandOptions commandOptions = new CommandOptions();
        JavaCodeParser javaCodeParser = new JavaCodeParser(commandOptions);
        System.out.println(javaCodeParser.getJavaImport(new File("C:\\Users\\sct\\IdeaProjects\\untitled\\src\\main\\resources\\FixBug.java")));
    }

}
