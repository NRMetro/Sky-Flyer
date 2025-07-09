package io.github.skyflyer.character;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class GameActor {
    protected Vector2 position;
    protected Texture texture;

    public Vector2 getPosition() {
        return position;
    }

    protected void setPosition(Vector2 position) {
        System.out.println(position.x + " " + position.y);
        this.position = position.cpy();
    }

    protected void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x - texture.getWidth() / 32f, position.y - texture.getHeight() / 32f,2f,2f);
    }

    public void dispose() {
        texture.dispose();
    }

}
