package cli.code_parser.java.model;


import java.util.List;
import java.util.Map;

public class CacheModel {
    private String version;
    private String language;
    private String fileName;
    private String packageName;
    private String hash;
    private List<String> classNames;
    private Map<String, List<String>> classMethodNames;
    private Map<String, Map<String, String>> classMethodCodeBlock;
    private Map<String, Map<String, String>> classMethodComment;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Map<String, Map<String, String>> getClassMethodComment() {
        return classMethodComment;
    }

    public void setClassMethodComment(Map<String, Map<String, String>> classMethodComment) {
        this.classMethodComment = classMethodComment;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public List<String> getClassNames() {
        return classNames;
    }

    public void setClassNames(List<String> classNames) {
        this.classNames = classNames;
    }

    public Map<String, List<String>> getClassMethodNames() {
        return classMethodNames;
    }

    public void setClassMethodNames(Map<String, List<String>> classMethodNames) {
        this.classMethodNames = classMethodNames;
    }

    public Map<String, Map<String, String>> getClassMethodCodeBlock() {
        return classMethodCodeBlock;
    }

    public void setClassMethodCodeBlock(Map<String, Map<String, String>> classMethodCodeBlock) {
        this.classMethodCodeBlock = classMethodCodeBlock;
    }

    @Override
    public String toString() {
        return "CacheModel{" +
                "version='" + version + '\'' +
                ", language='" + language + '\'' +
                ", fileName='" + fileName + '\'' +
                ", packageName='" + packageName + '\'' +
                ", hash='" + hash + '\'' +
                ", classNames=" + classNames +
                ", classMethodNames=" + classMethodNames +
                ", classMethodCodeBlock=" + classMethodCodeBlock +
                ", classMethodComment=" + classMethodComment +
                '}';
    }
}
