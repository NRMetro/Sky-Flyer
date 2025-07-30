package io.github.skyflyer.screens;

import com.badlogic.gdx.Game;
import io.github.skyflyer.SkyFly;

public class ScreenController{
    private final SkyScreen mainMenu;
    private final GameScreen gameScreen;
    private final SkyScreen shop;
    private final SkyScreen settings;
    private final SkyScreen death;
    private final SkyScreen win;
    private final Game game;

    public ScreenController(SkyFly game) {
        this.game = game;
        mainMenu = new MainMenuScreen(game);
        gameScreen = new GameScreen(game);
        shop = new ShopScreen(game);
        settings = new SettingsScreen(game);
        death = new DeathScreen(game);
        win = new WinScreen(game);
    }

    public void switchToMainMenu(){game.setScreen(mainMenu);}

    public void switchToGame(){
        gameScreen.newGame();
        game.setScreen(gameScreen);
    }

    public void switchToShop(){game.setScreen(shop);}

    public void switchToSettings(){game.setScreen(settings);}

    public void switchToWin(){game.setScreen(win);}

    public void switchToDeath(){game.setScreen(death);}
}
