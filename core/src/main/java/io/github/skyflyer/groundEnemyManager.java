package io.github.skyflyer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

public class groundEnemyManager {

    private final List<groundEnemy> groundEnemies = new ArrayList<>();
    private final Texture groundEnemyTexture;

    public groundEnemyManager(Texture enemyTexture) {
        this.groundEnemyTexture = enemyTexture;
    }

    public void spawnEnemy(float tileX, float tileY) {
        System.out.println("[Ground Enemy Manager] Spawning enemy at (" + tileX + ", " + tileY + ")");
        float worldX = tileX * 1f;
        float worldY = tileY * 1f + 0.8f;
        groundEnemies.add(new groundEnemy(worldX, worldY, groundEnemyTexture));
    }

    public void update(float delta) {
        for (groundEnemy enemy : groundEnemies) {
            enemy.update(delta);
        }
    }

    public void render(SpriteBatch batch) {
        for (groundEnemy enemy : groundEnemies) {
            enemy.draw(batch);
        }
    }

    public List<groundEnemy> getGroundEnemies() {
        return groundEnemies;
    }
}

