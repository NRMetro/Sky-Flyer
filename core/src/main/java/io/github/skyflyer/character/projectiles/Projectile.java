package io.github.skyflyer.character.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import io.github.skyflyer.character.GameActor;

public class Projectile extends GameActor {
    private final Vector2 velocity;
    private Float distanceTraveled;
    private boolean toRemove = false;

    public Projectile(Vector2 position, Vector2 velocity) {
        setTexture(new Texture("Bullet.png"));
        setPosition(position.cpy());
        this.velocity = velocity.cpy();
        distanceTraveled = 0f;
    }


    public void update(float delta, Vector2 playerLocation)
    {
        position = getPosition().cpy();

        int projX = (int) position.x;
        int projY = (int) position.y;

        int playX = (int) playerLocation.x;
        int playY = (int) playerLocation.y;

        distanceTraveled += velocity.len() * delta;
        getPosition().mulAdd(velocity, delta);

        if(projX == playX && projY == playY){
            System.out.println("Player hit");
            toRemove = true;
        }
        else if(distanceTraveled > 20f){
            toRemove = true;
        }
    }

    public boolean isToRemove() {
        return toRemove;
    }
}
