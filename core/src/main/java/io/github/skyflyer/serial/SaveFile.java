package io.github.skyflyer.serial;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;

public class SaveFile implements Json.Serializable {
    private int trophies;
    private ObjectMap<String, Integer> unlocks = new ObjectMap<>();
    private ObjectMap<String, String> settings = new ObjectMap<>();

    public SaveFile() {
        trophies = 0;
        settings.put("endless", "false");
    }

    @Override
    public void write(Json json) {
        json.writeValue("trophies", trophies);
        json.writeValue("unlocks", unlocks, ObjectMap.class, String.class);
        json.writeValue("settings", settings, ObjectMap.class, String.class);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        trophies = jsonData.getInt("trophies", 0);

        JsonValue unlocksValue = jsonData.get("unlocks");
        if (unlocksValue != null) {
            for (JsonValue entry : unlocksValue) {
                unlocks.put(entry.name, entry.asInt());
            }
        }

        JsonValue settingsValue = jsonData.get("settings");
        if (settingsValue != null) {
            for (JsonValue entry : settingsValue) {
                settings.put(entry.name, entry.asString());
            }
        }
    }
    public int getTrophies() { return trophies; }
    public ObjectMap<String, Integer> getUnlocks() { return unlocks; }
    public ObjectMap<String, String> getSettings() { return settings; }

    public void addTrophies(int trophies) {
        this.trophies += trophies;
    }
}
