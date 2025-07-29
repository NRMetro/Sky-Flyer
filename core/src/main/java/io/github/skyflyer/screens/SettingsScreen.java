package io.github.skyflyer.screens;

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
import com.badlogic.gdx.utils.ObjectMap;
import io.github.skyflyer.SkyFly;

public class SettingsScreen extends SkyScreen {
    private Stage stage;
    private Skin skin;
    private ScreenController screens;

    public SettingsScreen(SkyFly game, ScreenController screenController) {
        super(game);
        this.screens = screenController;
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("skin/glassy/skin/glassy-ui.json"));

        Texture bgTexture = new Texture(Gdx.files.internal("mainBackground.png"));
        Drawable background = new TextureRegionDrawable(new TextureRegion(bgTexture));

        ObjectMap<String, String> settings = game.getSettings();
        String endless = settings.get("endless");


        Table table = new Table();
        table.setFillParent(true);
        table.setBackground(background);
        stage.addActor(table);

        Table title = new Table();

        TextButton backButton = new TextButton("<-", skin,"small");
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                screens.switchToMainMenu();
            }
        });
        title.add(backButton).padRight(10);
        Label gameName = new Label("Settings", skin, "big");
        title.add(gameName);

        table.add(title).padBottom(10);
        table.row();

        CheckBox endlessMode = new CheckBox("Endless Mode", skin);
        endlessMode.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(endlessMode.isChecked()){
                    game.changeSetting("endless","true");
                }
                else{
                    game.changeSetting("endless","false");
                }
            }
        });
        table.add(endlessMode).padBottom(10);
        table.row();

        if(endless.equals("true")){
            endlessMode.setChecked(true);
        }

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
