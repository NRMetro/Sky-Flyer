package io.github.skyflyer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture backgroundTexture;
    private Texture characterTexture;
    private Sprite characterSprite;
    SpriteBatch spriteBatch;
    FitViewport viewport;

    @Override
    public void create() {
        batch = new SpriteBatch();

        characterTexture = new Texture("bucket.png");
        backgroundTexture = new Texture("libgdx.png");

        characterSprite = new Sprite(characterTexture); // Initialize the sprite based on the texture
        characterSprite.setSize(1, 1); // Define the size of the sprite

        spriteBatch = new SpriteBatch();
        viewport = new FitViewport(8, 5);

    }

    @Override
    public void render() {
        // organize code into three methods
        input();
        logic();
        draw();
    }

    private void input() {
        float speed = .7f;
        float delta = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            characterSprite.translateX(speed * delta); // Move the bucket right
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            characterSprite.translateX(-(speed * delta)); // Move the bucket left
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            characterSprite.translateY(speed * delta); // Move the bucket up
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            characterSprite.translateY(-(speed * delta)); // Move the bucket down
        }


        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        spriteBatch.end();
    }

    private void logic() {

    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        spriteBatch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight);
        characterSprite.draw(spriteBatch); // Sprites have their own draw method

        spriteBatch.end();
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true); // true centers the camera
    }

    @Override
    public void dispose() {
        batch.dispose();

    }
}
