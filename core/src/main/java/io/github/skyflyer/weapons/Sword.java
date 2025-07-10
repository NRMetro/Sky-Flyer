package io.github.skyflyer.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import io.github.skyflyer.character.Player;

public class Sword extends Weapon{

    public Sword(Texture texture, Vector2 position, int damage){
        super(texture, position, damage);
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y, 3.0f, 3.0f);
    }

    @Override
    public void update() {

    }

    @Override
    public void activate(Player player) {

    }
}
