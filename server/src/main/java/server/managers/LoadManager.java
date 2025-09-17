package server.managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.JsonParseException;

import java.io.*;
import java.time.LocalDate;
import java.util.Collection;

import java.util.NoSuchElementException;
import java.util.PriorityQueue;

import common.model.City;
import server.ServerApp;
import server.util.LocalDateAdapter;

/**
 * Использует файл для сохранения и загрузки коллекции.
 */
public class LoadManager {
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();

    private final String fileName;

    public LoadManager(String fileName) {
        if (!(new File(fileName).exists()))  fileName = "city/" + fileName;
        this.fileName = fileName;
    }

    /**
     * Записывает коллекцию в файл.
     * @param collection коллекция
     */
    public void writeCollection(Collection<City> collection) {
        try (PrintWriter collectionPrintWriter = new PrintWriter(new File(fileName))) {
            collectionPrintWriter.println(gson.toJson(collection));
            ServerApp.logger.info("Коллекция успешна сохранена в файл!");
        } catch (IOException exception) {
            ServerApp.logger.error("Загрузочный файл не может быть открыт!");
        }
    }

    /**
     * Считывает коллекцию из файла.
     * @return Считанная коллекция
     */
    public Collection<City> readCollection() {
        if (fileName != null && !fileName.isEmpty()) {
            try (var fileReader = new FileReader(fileName)) {
                var collectionType = new TypeToken<PriorityQueue<City>>() {}.getType();
                var reader = new BufferedReader(fileReader);

                var jsonString = new StringBuilder();

                String line;
                while((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (!line.equals("")) {
                        jsonString.append(line);
                    }
                }

                if (jsonString.isEmpty()) {
                    jsonString = new StringBuilder("[]");
                }

                PriorityQueue<City> collection = gson.fromJson(jsonString.toString(), collectionType);

                ServerApp.logger.info("Коллекция успешна загружена!");
                return collection;

            } catch (FileNotFoundException exception) {
                ServerApp.logger.error("Загрузочный файл не найден!");
            } catch (NoSuchElementException exception) {
                ServerApp.logger.error("Загрузочный файл пуст!");
            } catch (JsonParseException exception) {
                ServerApp.logger.error("В загрузочном файле не обнаружена необходимая коллекция!");
            } catch (IllegalStateException | IOException exception) {
                ServerApp.logger.error("Непредвиденная ошибка!");
                System.exit(0);
            }
        } else {
            ServerApp.logger.error("Аргумент командной строки с загрузочным файлом не найден!");
        }
        return new PriorityQueue<>();
    }
}