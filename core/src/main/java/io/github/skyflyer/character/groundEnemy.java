package io.github.skyflyer.character;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class groundEnemy extends Enemy{

    public groundEnemy(float x, float y, Texture texture) {
        setPosition(new Vector2(x, y));
        setTexture(texture);
    }

    public void update(float delta) {
        // TODO: Add movement
    }
}
