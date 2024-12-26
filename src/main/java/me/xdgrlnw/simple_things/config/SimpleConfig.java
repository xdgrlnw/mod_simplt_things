package me.xdgrlnw.simple_things.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.xdgrlnw.simple_things.config.data.ClientData;
import me.xdgrlnw.simple_things.config.data.CommonData;
import me.xdgrlnw.simple_things.config.data.ServerListData;
import me.xdgrlnw.simple_things.util.SimpleLogger;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SimpleConfig {

    private static final String CONFIG_DIR = "config/simple_things/";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static final String CLIENT_FILE = CONFIG_DIR + "client_config.json";
    private static final String COMMON_FILE = CONFIG_DIR + "common_config.json";
    private static final String SERVER_LIST = CONFIG_DIR + "default_servers.json";

    public static ClientData client = new ClientData();
    public static CommonData common = new CommonData();
    public static ServerListData serverList = new ServerListData();

    public static void init() {
        EnvType envType = FabricLoader.getInstance().getEnvironmentType();

        SimpleConfigUtil.updateLoggingStatus();
        loadConfig(COMMON_FILE, CommonData.class, common);
        if (envType == EnvType.CLIENT) {
            loadConfig(CLIENT_FILE, ClientData.class, client);
            loadConfig(SERVER_LIST, ServerListData.class, serverList);
        } else SimpleLogger.log("Unsupported environment type: " + envType);
    }

    public static void save() {
        EnvType envType = FabricLoader.getInstance().getEnvironmentType();

        saveConfig(COMMON_FILE, common);
        if (envType == EnvType.CLIENT) {
            saveConfig(CLIENT_FILE, client);
        } else SimpleLogger.log("Unsupported environment type: " + envType);
    }

    private static <T> void loadConfig(String fileName, Class<T> type, T data) {
        try {
            File file = new File(fileName);
            if (file.exists()) {
                try (FileReader reader = new FileReader(file)) {
                    T loadedData = GSON.fromJson(reader, type);
                    if (loadedData != null) {
                        data = loadedData;
                        SimpleLogger.log(fileName + " loaded.");
                    }
                }
            } else {
                SimpleLogger.log(fileName + " not found. Creating a new one...");
                saveConfig(fileName, data);
            }
        } catch (IOException e) {
            SimpleLogger.logError("Failed to load " + fileName + ": " + e.getMessage());
        }
    }

    private static <T> void saveConfig(String fileName, T data) {
        try {
            Files.createDirectories(Paths.get(CONFIG_DIR));
            try (FileWriter writer = new FileWriter(fileName)) {
                GSON.toJson(data, writer);
                SimpleLogger.log(fileName + " saved.");
            }
        } catch (IOException e) {
            SimpleLogger.logError("Failed to save " + fileName + ": " + e.getMessage());
        }
    }
}
