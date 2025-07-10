package io.github.skyflyer.character.enemyGeneric;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class EnemyManager<T extends Enemy> {

    private final List<T> enemies = new ArrayList<>();
    private final Supplier<T> enemySupplier;

    public EnemyManager(Supplier<T> enemySupplier) {
        this.enemySupplier = enemySupplier;
    }

    public void spawnEnemy(float tileX, float tileY) {
        //System.out.println("[Enemy Manager] Spawning enemy at (" + tileX + ", " + tileY + ")");
        float worldY = tileY + 1.8f;
        T newEnemy = enemySupplier.get();
        newEnemy.setPosition(tileX, worldY);
        enemies.add(newEnemy);
    }

    public void update(float delta) {
        for (T enemy : enemies) {
            if(enemy.isActive()){
                enemy.update(delta);
            }
        }
    }

    public void render(SpriteBatch batch) {
        for (T enemy : enemies) {
            enemy.render(batch);
        }
    }

    public List<T> getEnemies() {
        return enemies;
    }

    public void checkDistances(Vector2 position) {
        for (T enemy : enemies) {
            float distance = enemy.calculateDistance(position);
            enemy.setActive(distance < 20);
        }
    }
}

