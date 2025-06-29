package io.github.skyflyer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import io.github.skyflyer.screens.GameScreen;
import io.github.skyflyer.screens.SkyScreen;

public class Player {

    public Vector2 position;
    private Texture texture;
    private GameScreen screen;
    private float dashCooldown;
    private float timeSeconds;

    public Player(float x, float y, GameScreen screen) {
        position = new Vector2(x, y);
        texture = new Texture(Gdx.files.internal("SkyFlyerDraft.png"));
        this.screen = screen;
        dashCooldown = 3;
        timeSeconds = 3;
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
        float x = (int) position.x + dx;
        float y = (int) position.y + dy;
        boolean solid = screen.isTileSolid((int) position.x + 3 * dx, (int) position.y + 2 * dy);

        if(!solid){
            position.x += dx;
            position.y += dy;
        }
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x - texture.getWidth() / 32f, position.y - texture.getHeight() / 32f,2f,2f);
    }

    public void dispose() {
        texture.dispose();
    }
}
