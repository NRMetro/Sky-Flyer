package io.github.skyflyer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import io.github.skyflyer.screens.MainMenuScreen;
import io.github.skyflyer.serial.SaveFile;

public class SkyFly extends Game {

    SaveFile saveFile = new SaveFile();

    @Override
    public void create() {
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
        setScreen(new MainMenuScreen(this));


    }


    public void increaseTrophies(int amount){
        saveFile.addTrophies(amount);
    }

    public int getTrophies(){
        return saveFile.getTrophies();
    }
}
