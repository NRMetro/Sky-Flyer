package io.github.skyflyer.screens;

import io.github.skyflyer.SkyFly;

public class ScreenController extends SkyScreen{
    private final SkyScreen mainMenu;
    private final GameScreen gameScreen;
    private final SkyScreen shop;
    private final SkyScreen settings;
    private final SkyScreen death;
    private final SkyScreen win;

    public ScreenController(SkyFly game) {
        super(game);
        mainMenu = new MainMenuScreen(game,this);
        gameScreen = new GameScreen(game,this);
        shop = new ShopScreen(game,this);
        settings = new SettingsScreen(game,this);
        death = new DeathScreen(game,this);
        win = new WinScreen(game,this);
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
