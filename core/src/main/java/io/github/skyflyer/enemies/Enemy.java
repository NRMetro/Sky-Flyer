package io.github.skyflyer.enemies;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Enemy extends GameActor{

    public Enemy(){
        super();
    }

    protected void setPosition(float x, float y) {
        super.setPosition(new Vector2(x/32, y/32));
    }
}
