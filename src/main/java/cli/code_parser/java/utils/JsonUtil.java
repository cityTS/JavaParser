package cli.code_parser.java.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class JsonUtil {
    private static String readFile(String filePath) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }

        bufferedReader.close();

        return stringBuilder.toString();
    }

    public static <T> T readJsonFile(String filePath, Class<T> clazz) throws IOException {
        String jsonText = readFile(filePath);
        return JSON.parseObject(jsonText, clazz);
    }

    public static <T> void writeJsonFile(String filePath, T model) throws IOException {
        String jsonString = JSON.toJSONString(model, JSONWriter.Feature.PrettyFormat);
        try (
                FileOutputStream fileOutputStream = new FileOutputStream(filePath);
                FileChannel fileChannel = fileOutputStream.getChannel();
                FileLock fileLock = fileChannel.lock()
        ) {
            fileOutputStream.write(jsonString.getBytes());
        }
    }
}
