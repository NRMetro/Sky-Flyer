package io.github.skyflyer.character.enemyType;

import com.badlogic.gdx.graphics.Texture;
import io.github.skyflyer.character.enemyGeneric.FlyingEnemy;

public class PewPew extends FlyingEnemy {

    public PewPew() {
        super();
        setTexture(new Texture("SkyFlyerDraft.png"));
    }

    public void update(float delta) {
        // TODO: Add movement
    }
}
