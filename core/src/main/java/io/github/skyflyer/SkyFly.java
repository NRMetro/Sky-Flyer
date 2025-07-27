package io.github.skyflyer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ObjectMap;
import io.github.skyflyer.screens.MainMenuScreen;
import io.github.skyflyer.screens.ScreenController;
import io.github.skyflyer.serial.SaveFile;

public class SkyFly extends Game {

    SaveFile saveFile = new SaveFile();

    @Override
    public void create() {
        ScreenController screens = new ScreenController(this);

        Json json = new Json();

        FileHandle file = Gdx.files.local("save.json");

        if(file.exists()) {
            String input = Gdx.files.local("save.json").readString();
            saveFile = json.fromJson(SaveFile.class, input);
        }
        else{
            String output = json.prettyPrint(saveFile);
            Gdx.files.local("save.json").writeString(output, false);
        }




        System.out.println("Starting SkyFly");
        screens.switchToMainMenu();

    }

    public ObjectMap<String, String> getSettings() {
        return saveFile.getSettings();
    }

    public void changeUnlock(String unlock,int value) {
        saveFile.changeUnlock(unlock,value);
    }
    public void changeSetting(String name,String value){
        saveFile.changeSetting(name,value);
    }

    public void increaseTrophies(int amount){
        saveFile.addTrophies(amount);
    }

    public void decreaseTrophies(int amount){
        saveFile.decreaseTrophies(amount);
    }

    public int getTrophies(){
        return saveFile.getTrophies();
    }

    public ObjectMap<String, Integer> getUnlocks() {
        return saveFile.getUnlocks();
    }
}
