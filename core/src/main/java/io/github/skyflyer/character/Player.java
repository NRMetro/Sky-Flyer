package io.github.skyflyer.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import io.github.skyflyer.screens.GameScreen;
import io.github.skyflyer.weapons.Weapon;

public class Player extends GameActor {

    private final GameScreen screen;
    private final float dashCooldown;
    private float timeSeconds;
    private Weapon currentWeapon;
    private int health;

    private Animation<TextureRegion> flyingRightAnimation;
    private Animation<TextureRegion> flyingLeftAnimation;
    private float flyingTime = 0;
    private boolean facingRight = true;

    public Player(float x, float y, GameScreen screen) {
        setPosition(new Vector2(x, y));
        setTexture(new Texture(Gdx.files.internal("SkyFlyer/SkyFlyer.png")));
        this.screen = screen;
        dashCooldown = 3;
        timeSeconds = 3;
        this.currentWeapon = null;
        health = 5;

        // Load right-facing animation frames
        Array<TextureRegion> rightFrames = new Array<>();
        for (int i = 0; i < 3; i++) {
            Texture frame = new Texture(Gdx.files.internal("SkyFlyer/FlyerMovingRight/MovingRight_" + i + ".png"));
            rightFrames.add(new TextureRegion(frame));
        }
        flyingRightAnimation = new Animation<>(0.3f, rightFrames, Animation.PlayMode.LOOP);

        // Load left-facing animation frames
        Array<TextureRegion> leftFrames = new Array<>();
        for (int i = 0; i < 3; i++) {
            Texture frame = new Texture(Gdx.files.internal("SkyFlyer/FlyerMovingLeft/MovingLeft_" + i + ".png"));
            leftFrames.add(new TextureRegion(frame));
        }
        flyingLeftAnimation = new Animation<>(0.3f, leftFrames, Animation.PlayMode.LOOP);
    }

    public void update(float delta) {
        float speed = 30f;
        float dx = 0;
        float dy = 0;
        float dash = 0;

        flyingTime += delta;

        if(Gdx.input.isKeyJustPressed(Input.Keys.CONTROL_LEFT) && timeSeconds >= dashCooldown) {
            dash = 3;
            timeSeconds = 0;
        }

        timeSeconds += Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Input.Keys.W)) dy += speed * delta + dash;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) dy -= speed * delta + dash;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            dx -= speed * delta + dash;
            facingRight = false;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            dx += speed * delta + dash;
            facingRight = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.F)) finish();

        if(dx != 0 || dy != 0) {
            move(dx,dy);
        }
    }

    public void render(SpriteBatch batch){
        Animation<TextureRegion> currentAnim = facingRight ? flyingRightAnimation : flyingLeftAnimation;
        TextureRegion currentFrame = currentAnim.getKeyFrame(flyingTime, true);
        batch.draw(currentFrame, position.x, position.y, 2.0f, 2.0f);
    }

    private void finish() {
        screen.isFinish(position.x,position.y);
    }

    private void move(float dx, float dy) {
        float x = (int) position.x + 3 * dx;
        float y = (int) position.y + 2 * dy;

        boolean solid = screen.isTileSolid(x, y);

        if(!solid){
            position.x += dx;
            position.y += dy;
        }
    }

    public Weapon getCurrentWeapon(){
        return currentWeapon;
    }

    public void pickUpWeapon(Weapon newWeapon){
        this.currentWeapon = newWeapon;
        System.out.println("Picked up " + newWeapon.getClass().getSimpleName());
    }

    public int getHealth() {
        return health;
    }

    public void removeHealth(int health) {
        this.health -= health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Vector2 getTilePosition(){
        return new Vector2(Math.round(position.x), Math.round(position.y));
    }

    public void dispose(){
        for (TextureRegion region : flyingRightAnimation.getKeyFrames()) {
            region.getTexture().dispose();
        }
        for (TextureRegion region : flyingLeftAnimation.getKeyFrames()) {
            region.getTexture().dispose();
        }
    }
}
