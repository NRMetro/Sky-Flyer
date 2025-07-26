package io.github.skyflyer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.github.skyflyer.SkyFly;

public class ShopScreen extends SkyScreen {
    Stage stage;
    private Skin skin;

    public ShopScreen(SkyFly game) {
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

        TextButton backButton = new TextButton("<-", skin,"small");
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                MainMenuScreen mainScreen = new MainMenuScreen(game);
                game.setScreen(mainScreen);
            }
        });
        table.add(backButton).padBottom(20);
        table.row();

        Label gameName = new Label("UPGRADES SHOP", skin, "big");
        table.add(gameName).padBottom(20);
        table.row();

        Label money = new Label("Money in Bank: " + game.getTrophies(), skin);
        table.add(money).padBottom(100);
        table.row();

        Label incHearts = new Label("Increase Hearts: (10 Trophies)", skin);
        table.add(incHearts).padBottom(20);
        TextButton increaseHearts = new TextButton("+", skin,"small");
        increaseHearts.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                if(game.getTrophies() >= 10){
                    game.changeUnlock("additionalHearts",1);
                    game.decreaseTrophies(10);
                    money.setText("Money in Bank: " + game.getTrophies());
                }
            }
        });
        table.add(increaseHearts).padBottom(20);
        table.row();

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
