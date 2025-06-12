package io.github.skyflyer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Player {

    public Vector2 position;
    private Texture texture;

    public Player(float x, float y) {
        position = new Vector2(x, y);
        texture = new Texture(Gdx.files.internal("SkyFlyerDraft.png"));
    }
    public void update(float delta) {
        float speed = 5f;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) position.y += speed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) position.y -= speed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) position.x -= speed * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) position.x += speed * delta;
    }
    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x - texture.getWidth() / 16f, position.y - texture.getHeight() / 16f,2f,2f);
    }

    public void dispose() {
        texture.dispose();
    }
}
