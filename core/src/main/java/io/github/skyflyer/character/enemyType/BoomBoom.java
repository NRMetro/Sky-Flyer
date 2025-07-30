package io.github.skyflyer.character.enemyType;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import io.github.skyflyer.character.enemyGeneric.FlyingEnemy;

public class BoomBoom extends FlyingEnemy {

    private final Animation<TextureRegion> flyingLeft;
    private final Animation<TextureRegion> flyingRight;
    private final Animation<TextureRegion> blowingUpLeft;
    private final Animation<TextureRegion> blowingUpRight;
    private float stateTime = 0f;
    private TextureRegion currentFrame;
    private boolean isAttacking = false;

    public BoomBoom() {
        super();
        toRemove = false;

        // Load right facing animation frames
        Array<TextureRegion> rightFrames = new Array<>();
        for (int i = 0; i < 3; i++) {
            Texture frame = new Texture(Gdx.files.internal("Enemies/bomberMovingRight/bomberRight_" + i + ".png"));
            rightFrames.add(new TextureRegion(frame));
        }
        flyingRight = new Animation<>(0.3f, rightFrames, Animation.PlayMode.LOOP);

        // Load right facing animation frames
        Array<TextureRegion> leftFrames = new Array<>();
        for (int i = 0; i < 3; i++) {
            Texture frame = new Texture(Gdx.files.internal("Enemies/bomberMovingLeft/bomberLeft_" + i + ".png"));
            leftFrames.add(new TextureRegion(frame));
        }
        flyingLeft = new Animation<>(0.3f, leftFrames, Animation.PlayMode.LOOP);

        // Load right blowing up facing animation frames
        Array<TextureRegion> rightBlowingUpFrames = new Array<>();
        for (int i = 0; i < 3; i++) {
            Texture frame = new Texture(Gdx.files.internal("Enemies/bomberBlowingUpRight/blowUpRight_" + i + ".png"));
            rightBlowingUpFrames.add(new TextureRegion(frame));
        }
        blowingUpRight = new Animation<>(0.1f, rightBlowingUpFrames, Animation.PlayMode.NORMAL);

        // Load left blowing up -facing animation frames
        Array<TextureRegion> leftBlowingUpFrames = new Array<>();
        for (int i = 0; i < 3; i++) {
            Texture frame = new Texture(Gdx.files.internal("Enemies/bomberBlowingUpLeft/blowUpLeft_" + i + ".png"));
            leftBlowingUpFrames.add(new TextureRegion(frame));
        }
        blowingUpLeft = new Animation<>(0.1f, leftBlowingUpFrames, Animation.PlayMode.NORMAL);
    }

    @Override
    public void update(float delta) {
        position = getPosition().cpy();
        Vector2 playerLoc = getPlayerLocation().cpy();
        Vector2 velocity = playerLoc.cpy().sub(position).nor().scl(8f);
        stateTime += delta;

        if (!isAttacking) {
            getPosition().mulAdd(velocity, delta);

            // Play normal flying animation
            if (velocity.x < 0){
                currentFrame = flyingLeft.getKeyFrame(stateTime, true);
            } else {
                currentFrame = flyingRight.getKeyFrame(stateTime, true);
            }

            // Check if close enough to attack
            if (position.dst(playerLoc) < 0.5f) {
                isAttacking = true;
                stateTime = 0f;
                playerDamage = 2;
            }

        } else if (isAttacking) {
            // Play explosion animation once
            boolean finished;
            if (velocity.x < 0) {
                currentFrame = blowingUpLeft.getKeyFrame(stateTime, false);
                finished = blowingUpLeft.isAnimationFinished(stateTime);
            } else {
                currentFrame = blowingUpRight.getKeyFrame(stateTime, false);
                finished = blowingUpRight.isAnimationFinished(stateTime);
            }

            if (finished) {
                toRemove = true;
            }
        }
    }


    @Override
    public void render(SpriteBatch batch){
        if (currentFrame != null){
            batch.draw(currentFrame, getPosition().x, getPosition().y, 2.0f, 2.0f);
        }
    }
}
