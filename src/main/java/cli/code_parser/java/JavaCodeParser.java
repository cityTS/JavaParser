package cli.code_parser.java;

import cli.code_parser.java.model.CacheModel;
import cli.code_parser.java.utils.HashFileUtil;
import cli.code_parser.java.utils.JsonUtil;
import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaPackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class JavaCodeParser {
    private final CommandOptions commandOptions;

    JavaCodeParser(CommandOptions commandOptions) {
        this.commandOptions = commandOptions;
    }

    private void deleteDoneFile(File file) {
        if (file.exists()) {
            file.delete();
        }
    }

    public void parse() throws IOException, NoSuchAlgorithmException {
        File javaFile = new File(commandOptions.getSourcePath());
        File cacheFile = new File(commandOptions.getOutputPath());
        File doneFileFlag = new File(commandOptions.getOutputPath() + ".DONE");
        CacheModel cacheModel = parseJavaFile(javaFile);
        if (cacheFile.exists()) {
            CacheModel cacheModelReal = JsonUtil.readJsonFile(commandOptions.getOutputPath(), CacheModel.class);
            if (!cacheModelReal.getHash().equals(cacheModel.getHash())) {
                deleteDoneFile(doneFileFlag);
            } else {
                for (String className : cacheModel.getClassMethodNames().keySet()) {
                    for (String methodName : cacheModel.getClassMethodNames().get(className)) {
                        if (cacheModelReal.getClassMethodComment() != null
                                && cacheModelReal.getClassMethodComment().get(className) != null
                                && cacheModelReal.getClassMethodComment().get(className).get(methodName) != null
                        ) {
                            if (cacheModel.getClassMethodComment() == null) {
                                cacheModel.setClassMethodComment(new HashMap<>());
                            }
                            cacheModel.getClassMethodComment().computeIfAbsent(className, k -> new HashMap<>());
                            cacheModel.getClassMethodComment().get(className).put(methodName, cacheModelReal.getClassMethodComment().get(className).get(methodName));
                        } else {
                            deleteDoneFile(doneFileFlag);
                        }
                    }
                }
            }
        }
        saveCache(cacheModel);
    }

    private void saveCache(CacheModel cacheModel) throws IOException {
        JsonUtil.writeJsonFile(commandOptions.getOutputPath(), cacheModel);
    }

    private CacheModel parseJavaFile(File javaFile) throws IOException, NoSuchAlgorithmException {
        JavaProjectBuilder javaProjectBuilder = new JavaProjectBuilder();
        javaProjectBuilder.addSource(javaFile);
        Collection<JavaClass> classes = javaProjectBuilder.getClasses();
        Collection<JavaPackage> packages = javaProjectBuilder.getPackages();
        CacheModel cacheModel = new CacheModel();
        cacheModel.setVersion("1.0");
        cacheModel.setLanguage("Java");
        cacheModel.setPackageName(packages.iterator().next().getName());
        cacheModel.setFileName(javaFile.getName());
        cacheModel.setHash(HashFileUtil.calculateSHA256(javaFile.getPath()));
        cacheModel.setClassNames(classes.stream().map(JavaClass::getName).collect(Collectors.toList()));
        cacheModel.setClassMethodNames(classes.stream()
                .collect(Collectors.toMap(JavaClass::getName,
                        javaClass -> javaClass.getMethods().stream()
                                .map(JavaMethod::toString)
                                .collect(Collectors.toList()))));
        cacheModel.setClassMethodCodeBlock(classes.stream()
                .collect(Collectors.toMap(JavaClass::getName,
                        javaClass -> javaClass.getMethods().stream()
                                .collect(Collectors.toMap(JavaMethod::toString,
                                        JavaMethod::getCodeBlock)))));
        return cacheModel;
    }

    public String getJavaPackage(File file) {
        Pattern packagePattern = Pattern.compile("^package +([\\w.]+);", Pattern.MULTILINE);
        Matcher matcher;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            StringBuilder fileContent = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                fileContent.append(line).append("\n");
            }

            matcher = packagePattern.matcher(fileContent.toString());
            if (matcher.find()) {
                return matcher.group(1);
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, String> getJavaImport(File file) {
        Map<String, String> javaImport = new HashMap<>();
        try (BufferedReader reader = Files.newBufferedReader(file.toPath())) {
            String line;
            Pattern importPattern = Pattern.compile("^import\\s+([\\w.]+);");

            while ((line = reader.readLine()) != null) {
                Matcher matcher = importPattern.matcher(line);
                if (matcher.find()) {
                    String importStatement = matcher.group(1);
                    String[] parts = importStatement.split("\\.");
                    if (parts.length > 0) {
                        String className = parts[parts.length - 1];
                        javaImport.put(className, importStatement);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return javaImport;
    }
}
