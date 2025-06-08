package io.github.skyflyer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class PlayerController {
    private Sprite characterSprite;


    private void input() {
        float speed = .7f;
        float delta = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            characterSprite.translateX(speed * delta); // Move the character right
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            characterSprite.translateX(-(speed * delta)); // Move the character left
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            characterSprite.translateY(speed * delta); // Move the character up
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            characterSprite.translateY(-(speed * delta)); // Move the character down
        }
    }
}
