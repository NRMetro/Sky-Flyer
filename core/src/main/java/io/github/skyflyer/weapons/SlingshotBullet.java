package io.github.skyflyer.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import io.github.skyflyer.character.GameActor;
import io.github.skyflyer.character.Player;
import io.github.skyflyer.character.enemyGeneric.Enemy;
import io.github.skyflyer.screens.GameScreen;

public class SlingshotBullet extends GameActor {

    private final Vector2 velocity;
    private float distanceTraveled = 0f;
    private final int damage;
    private boolean toRemove = false;
    private final GameScreen screen;

    public SlingshotBullet(Vector2 position, Vector2 velocity, int damage, GameScreen screen){
        setTexture(new Texture("Weapons/slingshotBullet.png"));
        setPosition(position.cpy());
        this.velocity = velocity.cpy();
        this.damage = damage;
        this.screen = screen;
    }

    public void update(float delta){
        getPosition().mulAdd(velocity, delta);
        distanceTraveled += velocity.len() * delta;

        for (Enemy enemy : screen.getEnemies()){
            if (enemy.getPosition().dst(getPosition()) < 1.0f){
                enemy.takeDamage(damage);
                toRemove = true;
                break;
            }
        }

        if (distanceTraveled > 20f){
            toRemove = true;
        }
    }

    public void render(SpriteBatch batch){
        batch.draw(getTexture(), getPosition().x, getPosition().y, 1.5f, 1.5f);
    }

    public boolean isToRemove(){
        return toRemove;
    }
}
