package io.github.skyflyer.character.enemyType;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import io.github.skyflyer.character.enemyGeneric.Enemy;
import io.github.skyflyer.character.enemyGeneric.GroundEnemy;

public class Grounder extends GroundEnemy{

    public Grounder() {
        super();
        setTexture(new Texture("groundEnemy.png"));
    }


    public void update(float delta) {
        // TODO: Add movement
    }
}
