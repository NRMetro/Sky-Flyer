package io.github.skyflyer;

import com.badlogic.gdx.Game;
import io.github.skyflyer.screens.MainMenuScreen;

public class SkyFly extends Game {

    @Override
    public void create() {
        System.out.println("Starting SkyFly");
        setScreen(new MainMenuScreen(this));
    }
}
