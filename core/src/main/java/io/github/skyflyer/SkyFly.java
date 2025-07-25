package io.github.skyflyer;

import com.badlogic.gdx.Game;
import io.github.skyflyer.screens.MainMenuScreen;

public class SkyFly extends Game {

    int money = 0;

    @Override
    public void create() {
        System.out.println("Starting SkyFly");
        setScreen(new MainMenuScreen(this));
    }

    public void increaseMoney(int amount){
        money += amount;
    }

    public int getMoney(){
        return money;
    }
}
