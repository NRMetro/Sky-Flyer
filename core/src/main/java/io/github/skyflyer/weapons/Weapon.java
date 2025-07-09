package io.github.skyflyer.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import io.github.skyflyer.Player;

public abstract class Weapon {
    protected Texture texture;
    protected Vector2 position;
    protected int damage;

    public Weapon(Texture texture, Vector2 position, int damage) {
        this.texture = texture;
        this.position = position;
        this.damage = damage;
    }

    public abstract void draw(SpriteBatch batch);
    public abstract void update();
    public abstract void activate(Player player);

    // Getters for position and damage
    public Vector2 getPosition() {
        return position;
    }

    public int getDamage() {
        return damage;
    }
}

