package io.github.skyflyer.character.enemyType;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import io.github.skyflyer.character.enemyGeneric.FlyingEnemy;

public class BoomBoom extends FlyingEnemy {

    public BoomBoom() {
        super();
        setTexture(new Texture("SkyFlyer/SkyFlyer.png"));
        toRemove = false;
    }

    @Override
    public void update(float delta) {
        position = getPosition().cpy();
        int projX = (int) position.x;
        int projY = (int) position.y;

        float speed = 8f;
        Vector2 playerLoc = getPlayerLocation();
        int playX = (int) playerLoc.x;
        int playY = (int) playerLoc.y;


        Vector2 velocity = new Vector2(playerLoc.sub(position).nor()).scl(speed);

        getPosition().mulAdd(velocity, delta);

        if(projX == playX && projY == playY){
//            System.out.println("Player hit");
            playerDamage = 3;
            toRemove = true;
        }
    }
}
