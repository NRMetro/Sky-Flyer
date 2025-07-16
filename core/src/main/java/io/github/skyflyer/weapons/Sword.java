package io.github.skyflyer.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import io.github.skyflyer.character.Player;
import io.github.skyflyer.character.enemyGeneric.Enemy;

public class Sword extends Weapon{

    public Sword(Texture texture, Vector2 position, int damage){
        super(texture, position, damage);
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y, 3.0f, 3.0f);
    }

    @Override
    public void update() {

    }

    @Override
    public void activate(Player player) {
        //System.out.println("Swords being activated!");
        Vector2 playerPos = player.getTilePosition();
        Vector2 attackPos = new Vector2(playerPos);

        if (player.isFacingRight()) {
            attackPos.x += 1;
        } else {
            attackPos.x -= 1;
        }

        //System.out.println("Attack Position: " + attackPos);
        for (Enemy enemy : player.getScreen().getEnemies()) {
            //System.out.println("Enemy Position: " + enemy.getPosition());
            if (enemy.getPosition().dst(attackPos) < 2.0f) {
                enemy.takeDamage(damage);
            }
        }

        //System.out.println("Sword attack on " + attackPos);
    }
}
