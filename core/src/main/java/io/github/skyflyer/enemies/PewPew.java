package io.github.skyflyer.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class PewPew extends FlyingEnemy{

    public PewPew(float x, float y) {
        super();
        setPosition(x, y);
        setTexture(new Texture(Gdx.files.internal("SkyFlyerDraft.png")));
    }

}
