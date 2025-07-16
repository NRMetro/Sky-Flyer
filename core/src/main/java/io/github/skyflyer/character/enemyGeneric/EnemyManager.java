package io.github.skyflyer.character.enemyGeneric;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class EnemyManager<T extends Enemy> {

    private final List<T> enemies = new ArrayList<>();
    private final Supplier<T> enemySupplier;
    private int playerDamage = 0;

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
        List<T> toRemove = new ArrayList<>();
        for (T enemy : enemies) {
            if(enemy.playerDamage > 0){
                playerDamage += enemy.playerDamage;
                enemy.playerDamage = 0;
            }
            if(enemy.toRemove){
                toRemove.add(enemy);
            }
            else if(enemy.isActive()){
                enemy.update(delta);
            }
        }
        enemies.removeAll(toRemove);
    }

    public void render(SpriteBatch batch) {
        for (T enemy : enemies) {
            enemy.render(batch);
        }
    }

    public List<T> getEnemies() {
        return enemies;
    }

    public int getPlayerDamage() {
        return playerDamage;
    }

    public void setPlayerDamage(int playerDamage) {
        this.playerDamage = playerDamage;
    }

    public void checkDistances(Vector2 position) {
        for (T enemy : enemies) {
            float distance = enemy.calculateDistance(position);
            enemy.setActive(distance < 20);
        }
    }
}

