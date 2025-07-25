package io.github.skyflyer.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.github.skyflyer.SkyFly;

public class MainMenuScreen extends SkyScreen {
    private Stage stage;
    private Skin skin;

    public MainMenuScreen(SkyFly game){
        super(game);
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("skin/glassy/skin/glassy-ui.json"));

        Texture bgTexture = new Texture(Gdx.files.internal("mainBackground.png"));
        Drawable background = new TextureRegionDrawable(new TextureRegion(bgTexture));

        Table table = new Table();
        table.setFillParent(true);
        table.setBackground(background);
        stage.addActor(table);

        Label gameName = new Label("SKY FLYER", skin, "big");
        table.add(gameName).padBottom(20);
        table.row();

        Label money = new Label("Money in Bank: " + game.getMoney(), skin);
        table.add(money).padBottom(100);
        table.row();

        TextButton newGameButton = new TextButton("New Game", skin);
        newGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GameScreen gameScreen = new GameScreen(game);
                game.setScreen(gameScreen);
            }
        });
        table.add(newGameButton).padBottom(10);
        table.row();

        TextButton contGameButton = new TextButton("Continue Game", skin);
        contGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Implement Continue Game Button");
            }
        });
        table.add(contGameButton).padBottom(10);
        table.row();

        TextButton settingsButton = new TextButton("Settings", skin);
        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Implement Settings Button");
            }
        });
        table.add(settingsButton).padBottom(10);
        table.row();

        TextButton exitButton = new TextButton("Exit", skin);
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
        table.add(exitButton);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen
        stage.act(delta);
        stage.draw();
    }


    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }


}
