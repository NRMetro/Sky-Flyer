package io.github.skyflyer.character.enemyGeneric;

import com.badlogic.gdx.math.Vector2;
import io.github.skyflyer.character.GameActor;

public class Enemy extends GameActor implements Comparable<Enemy> {

    float distToPlayer;

    public Enemy(){
        super();
    }

    public void update(float delta) {
        //Do nothing in base
    }

    public void setPosition(float x, float y) {
        setPosition(new Vector2(x, y));
    }
    @Override
    public int compareTo(Enemy o) {
        if(distToPlayer < o.distToPlayer){
            return -1;
        }
        else if(distToPlayer > o.distToPlayer){
            return 1;
        }

        return 0;
    }
}
