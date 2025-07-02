package io.github.skyflyer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class groundEnemy {
    private float x, y;
    private Texture texture;

    public groundEnemy(float x, float y, Texture texture) {
        this.x = x;
        this.y = y;
        this.texture = texture;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, 2.0f, 2.0f);
    }

    public void update(float delta) {
        // TODO: Add movement
    }
}
