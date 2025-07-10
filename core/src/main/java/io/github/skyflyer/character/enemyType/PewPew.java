package io.github.skyflyer.character.enemyType;

import com.badlogic.gdx.graphics.Texture;
import io.github.skyflyer.character.enemyGeneric.FlyingEnemy;
import io.github.skyflyer.character.projectiles.Projectile;

public class PewPew extends FlyingEnemy {

    public PewPew() {
        super();
        setTexture(new Texture("SkyFlyer/SkyFlyer.png"));
    }

    public void update(float delta) {

    }
}
