package io.github.skyflyer.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import io.github.skyflyer.Player;

public class SlingShot extends Weapon {

    public SlingShot(Texture texture, Vector2 position, int damage) {
        super(texture, position, damage);
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y, 3.0f, 3.0f); // Size can be adjusted
    }

    @Override
    public void update() {
        // Implement any behavior specific to slingshot, e.g., projectile movement
    }

    @Override
    public void activate(Player player) {
        // Implement behavior when player activates the slingshot, e.g., shooting a projectile
    }
}
