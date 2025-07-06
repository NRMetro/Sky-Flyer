package io.github.skyflyer.character.enemyGeneric;

import com.badlogic.gdx.math.Vector2;
import io.github.skyflyer.character.GameActor;

public class Enemy extends GameActor implements Comparable<Enemy> {

    private float distToPlayer;
    private Vector2 playerLocation;
    private boolean active;

    public Enemy(){
        super();
        active = false;
    }

    public void update(float delta) {
        //Do nothing in base
    }

    public float calculateDistance(Vector2 target) {
        distToPlayer = target.dst(position);
        playerLocation = target.cpy();

        return distToPlayer;
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

    public float getDistToPlayer() {
        return distToPlayer;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Vector2 getPlayerLocation() {

        return playerLocation;
    }
}
