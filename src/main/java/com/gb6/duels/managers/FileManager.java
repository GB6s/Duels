package com.gb6.duels.managers;

import com.gb6.duels.utils.GsonFactory;
import com.google.gson.reflect.TypeToken;

import java.io.*;

import static com.gb6.duels.utils.Constants.INSTANCE;

public class FileManager {

    public static <T> T readJSON(String file, TypeToken<T> type) {
        try {
            return GsonFactory.getPrettyGson().fromJson(new FileReader(INSTANCE.getDataFolder().getAbsolutePath() + File.separator + file + ".json"), type.getType());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> void writeJSON(String file, T t) {
        try (OutputStream os = new FileOutputStream(INSTANCE.getDataFolder().getAbsolutePath() + File.separator + file + ".json")) {
            os.write(GsonFactory.getPrettyGson().toJson(t).getBytes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void createFile(String file) {
        try {
            new File(INSTANCE.getDataFolder().getAbsolutePath() + File.separator + file + ".json").createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
