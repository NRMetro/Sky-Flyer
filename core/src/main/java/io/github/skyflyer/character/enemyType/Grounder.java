package io.github.skyflyer.character.enemyType;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import io.github.skyflyer.character.enemyGeneric.Enemy;
import io.github.skyflyer.character.enemyGeneric.GroundEnemy;
import io.github.skyflyer.character.projectiles.Projectile;
import io.github.skyflyer.screens.GameScreen;

import java.util.ArrayList;
import java.util.List;

public class Grounder extends GroundEnemy {
    private float fireCooldown = 0.5f;
    private float timeSinceLastShot = 0f;
    private boolean moveRight = true;
    private float timeSeconds = 0f;
    private GameScreen gameScreenRef;

    private final List<Projectile> projectiles;
    private final Animation<TextureRegion> grounderShootingRight;
    private final Animation<TextureRegion> grounderShootingLeft;

    public Grounder() {
        super();

        Array<TextureRegion> rightGrounderShootingFrames = new Array<>();
        for (int i = 0; i < 4; i++) {
            Texture frame = new Texture(Gdx.files.internal("Enemies/grounderShootingRight/grounderAttackRight_" + i + ".png"));
            rightGrounderShootingFrames.add(new TextureRegion(frame));
        }
        grounderShootingRight = new Animation<>(0.1f, rightGrounderShootingFrames, Animation.PlayMode.NORMAL);

        Array<TextureRegion> leftGrounderShootingFrames = new Array<>();
        for (int i = 0; i < 4; i++) {
            Texture frame = new Texture(Gdx.files.internal("Enemies/grounderShootingLeft/grounderAttackLeft_" + i + ".png"));
            leftGrounderShootingFrames.add(new TextureRegion(frame));
        }
        grounderShootingLeft = new Animation<>(0.1f, leftGrounderShootingFrames, Animation.PlayMode.NORMAL);

        projectiles = new ArrayList<>();
    }

    @Override
    public void render(SpriteBatch batch) {
        TextureRegion currentFrame = moveRight ?
            grounderShootingRight.getKeyFrame(timeSeconds, false) :
            grounderShootingLeft.getKeyFrame(timeSeconds, false);

        batch.draw(currentFrame, position.x, position.y - 1, 2f, 2f);

        for (Projectile p : projectiles) {
            p.render(batch);
        }
    }

    public void update(float delta) {
        timeSinceLastShot += delta;
        timeSeconds += delta;

        float speed = 2f;

        // Change direction every 0.5s
        if (timeSeconds > 0.5f) {
            moveRight = !moveRight;
            timeSeconds = 0;
        }

        if (moveRight) {
            position.x += speed * delta;
        } else {
            position.x -= speed * delta;
        }

        // Fire projectile if cooldown met
        if (timeSinceLastShot > fireCooldown) {
            Projectile p = fireProjectile(getPlayerLocation());
            if (p != null && gameScreenRef != null) {
                gameScreenRef.addEnemyProjectile(p);
            }
            timeSinceLastShot = 0;
        }


        List<Projectile> toRemove = new ArrayList<>();
        for (Projectile p : projectiles) {
            if (p.isPlayerhit()) {
                playerDamage += 1;
                toRemove.add(p);
            } else if (p.isToRemove()) {
                toRemove.add(p);
            } else {
                p.update(delta, getPlayerLocation());
            }
        }
        projectiles.removeAll(toRemove);
    }

    private Projectile fireProjectile(Vector2 playerLocation) {
        float speed = 3f;
        Vector2 velocity = new Vector2(playerLocation.sub(position).nor()).scl(speed);
        return new Projectile(position.cpy(), velocity);
    }

    @Override
    public void setGameScreen(GameScreen screen){
        this.gameScreenRef = screen;
    }
}
