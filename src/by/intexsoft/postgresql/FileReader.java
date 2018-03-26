package by.intexsoft.postgresql;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 *  Класс считывает файл по указанному пути.
 *  @autor Вершадов Денис
 *  @version 1.0
 */
public class FileReader {
    /**
     *  Метод считывает файл в строку по указанному пути. {@link #readFile(String)}
     *  @param filePath - путь к файлу.
     *  @return stringFile - содержимое файла.
     */
    public static String readFile(String filePath) {
        String stringFile = "";
        try (
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(
                                new FileInputStream(filePath), StandardCharsets.UTF_8
                        )
                )
        ) {
            String line;
            while ((line = reader.readLine()) != null) stringFile += line;
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Файл по указанному пути не найден:");
            System.out.println(filePath);
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: ");
            System.out.println(e);
        }
        return stringFile;
    }
}
