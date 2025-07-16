package io.github.skyflyer.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import io.github.skyflyer.character.Player;

public class Slingshot extends Weapon {

    public Slingshot(Texture texture, Vector2 position, int damage) {
        super(texture, position, damage);
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y, 3.0f, 3.0f); // Size can be adjusted
    }

    @Override
    public void update() {
    }

    @Override
    public void activate(Player player) {
        Vector2 startPos = player.getTilePosition();
        Vector2 velocity = player.isFacingRight() ? new Vector2(10, 0) : new Vector2(-10, 0);
        SlingshotBullet projectile = new SlingshotBullet(startPos, velocity, damage, player.getScreen());
        player.getScreen().addBullets(projectile);
    }
}
