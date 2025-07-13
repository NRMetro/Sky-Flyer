package io.github.skyflyer.character.enemyType;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import io.github.skyflyer.character.enemyGeneric.Enemy;
import io.github.skyflyer.character.enemyGeneric.GroundEnemy;
import io.github.skyflyer.character.projectiles.Projectile;

import java.util.ArrayList;
import java.util.List;

public class Grounder extends GroundEnemy{
    private float fireCooldown = .5f;
    private float timeSinceLastShot = 0f;
    private boolean moveRight = true;

    private final List<Projectile> projectiles;

    public Grounder() {
        super();
        setTexture(new Texture("Enemies/groundEnemy.png"));
        projectiles = new ArrayList<>();
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x - texture.getWidth() / 32f, position.y - texture.getHeight() / 32f,2f,2f);
        for (Projectile p : projectiles) {
            p.render(batch);
        }

    }

    public void update(float delta) {
        timeSinceLastShot += delta;
        List<Projectile> toRemove = new ArrayList<>();

        float speed = 2f;
        timeSeconds += delta;

        if(timeSeconds > .5){
            timeSeconds = 0;
            if(moveRight){
                moveRight = false;
            }
            else{
                moveRight = true;
            }
        }


        if(moveRight){
            position.x += speed * delta;
        }
        else{
            position.x -= speed * delta;
        }


        if (timeSinceLastShot > fireCooldown) {
            fireProjectile(getPlayerLocation());
            timeSinceLastShot = 0;
        }

        for (Projectile p : projectiles) {
            if(p.isPlayerhit()){
                playerDamage += 1;
                toRemove.add(p);
            }
            else if(p.isToRemove()){
                toRemove.add(p);
            }
            else{
                p.update(delta,getPlayerLocation());
            }
        }
        projectiles.removeAll(toRemove);
    }

    private void fireProjectile(Vector2 playerLocation) {
        //System.out.println("Fire Projectile");
        float speed = 3f;
        Vector2 velocity = new Vector2(playerLocation.sub(position).nor()).scl(speed);

        Projectile p = new Projectile(position,velocity);
        projectiles.add(p);
    }
}
