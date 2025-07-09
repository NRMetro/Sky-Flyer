package io.github.skyflyer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import io.github.skyflyer.enemies.GameActor;
import io.github.skyflyer.screens.GameScreen;
import io.github.skyflyer.weapons.Weapon;

public class Player extends GameActor {

    private final GameScreen screen;
    private final float dashCooldown;
    private float timeSeconds;
    private Weapon currentWeapon;

    public Player(float x, float y, GameScreen screen) {
        setPosition(new Vector2(x, y));
        setTexture(new Texture(Gdx.files.internal("SkyFlyer/SkyFlyer.png")));
        this.screen = screen;
        dashCooldown = 3;
        timeSeconds = 3;
        this.currentWeapon = null;
    }

    public void update(float delta) {
        float speed = 8f;
        float dx = 0;
        float dy = 0;
        float dash = 0;

        if(Gdx.input.isKeyJustPressed(Input.Keys.CONTROL_LEFT) && timeSeconds >= dashCooldown) {
            dash = 3;
            timeSeconds = 0;
        }

        timeSeconds += Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Input.Keys.W)) dy += speed * delta + dash;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) dy -= speed * delta + dash;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) dx -= speed * delta + dash;
        if (Gdx.input.isKeyPressed(Input.Keys.D)) dx += speed * delta + dash;

        if(dx != 0 || dy != 0) {
            move(dx,dy);
        }

    }

    private void move(float dx, float dy) {
        //System.out.println("MOVING TO " + dx + " " + dy);
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
        if (currentWeapon != null){
            currentWeapon = null;
        }
        currentWeapon = newWeapon;
    }

}
