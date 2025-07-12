package io.github.skyflyer.character.enemyGeneric;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public class FlyingEnemy extends Enemy {

    public FlyingEnemy(){
        super();
    }


    @Override
    public void setPosition(float x, float y) {
        float height = (float)(3 + (Math.random()*8));
        super.setPosition(new Vector2(x, y + height));
    }
}
