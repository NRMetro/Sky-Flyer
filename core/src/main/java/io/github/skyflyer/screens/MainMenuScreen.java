package io.github.skyflyer.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.skyflyer.Sky;
import io.github.skyflyer.SkyFly;

public class MainMenuScreen extends SkyScreen {
    TextureRegion title;
    SpriteBatch batch;
    float time = 0;

    public MainMenuScreen(Game game){
        super(game);
    }

    @Override
    public void show() {
        System.out.println("Starting Main Menu");
        title = new TextureRegion(new Texture(Gdx.files.internal("libgdx.png")), 0, 0, 480, 320);
        batch = new SpriteBatch();
        batch.getProjectionMatrix().setToOrtho2D(0, 0, 1920, 1080);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1); // dark gray background
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(title, 0, 0);
        batch.end();

        time += delta;
        if (time > 1) {
            if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY) || Gdx.input.justTouched()) {
                GameScreen gameScreen = new GameScreen(game);
                gameScreen.setMap("maps/FlyMap1.tmx");//Use this later for loading different maps in the game
                game.setScreen(gameScreen);
            }
        }
    }


    @Override
    public void hide() {
        Gdx.app.debug("SkyFlyer", "dispose main menu");
        batch.dispose();
        title.getTexture().dispose();
    }


}
