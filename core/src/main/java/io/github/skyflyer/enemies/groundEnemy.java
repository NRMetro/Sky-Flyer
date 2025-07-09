package io.github.skyflyer.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class groundEnemy {
    private final float x;
    private final float y;
    private final Texture texture;

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
