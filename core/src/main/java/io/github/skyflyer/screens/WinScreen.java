package io.github.skyflyer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.github.skyflyer.SkyFly;

public class WinScreen extends SkyScreen {

    Stage stage;
    public WinScreen(SkyFly game) {
        super(game);
    }

    @Override
    public void show() {
        game.increaseTrophies(10);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Texture bgTexture = new Texture(Gdx.files.internal("winBackground.png"));
        Drawable background = new TextureRegionDrawable(new TextureRegion(bgTexture));

        Skin skin = new Skin(Gdx.files.internal("skin/glassy/skin/glassy-ui.json"));

        Table table = new Table();
        table.setFillParent(true);
        table.setBackground(background);
        table.setTouchable(Touchable.enabled);
        table.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });
        stage.addActor(table);

        Label deathMsg = new Label("YOU HAVE CLEARED THIS RUN", skin, "big");
        table.add(deathMsg).padBottom(120);
        table.row();

        Label money = new Label("YOU EARNED 10 TROPHIES", skin);
        table.add(money).padBottom(120);
        table.row();

        Label gameName = new Label("Click to continue to Main Menu", skin);
        table.add(gameName).padBottom(120);
        table.row();


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // Clear the screen
        stage.act(delta);
        stage.draw();
    }

}
