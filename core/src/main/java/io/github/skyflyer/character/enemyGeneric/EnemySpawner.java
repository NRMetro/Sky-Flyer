package io.github.skyflyer.character.enemyGeneric;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;
import io.github.skyflyer.screens.GameScreen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EnemySpawner {

    private final List<EnemyManager<? extends Enemy>> enemyManagers;
    private int playerDamage = 0;

    public EnemySpawner(EnemyManager<? extends Enemy> enemyManager) {
        this.enemyManagers = new ArrayList<>();
        enemyManagers.add(enemyManager);
    }

    public EnemySpawner(List<EnemyManager<? extends Enemy>> enemyManagers){
        this.enemyManagers = new ArrayList<>(enemyManagers);
    }

    public List<Vector2> getWalkableTiles(TiledMap map, int walkableTileID) {
        List<Vector2> walkableTiles = new ArrayList<>();
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("collision");

        if (layer == null) {
            //System.out.println("[Enemy Spawner] ERROR: 'collision' layer has not been found");
            return walkableTiles;
        }

        int width = layer.getWidth();
        int height = layer.getHeight();
        //System.out.println("[Enemy Spawner]Enemy Spawner Scanning " + width + "x" + height + "tiles...");

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Cell cell = layer.getCell(x, y);
                if (cell != null && cell.getTile() != null) {
                    int tileId = cell.getTile().getId();
                    if (tileId == walkableTileID) {
                        walkableTiles.add(new Vector2(x, y));
                    }
                }
            }
        }
        //System.out.println("Enemy Spawner found " + walkableTiles.size());
        return walkableTiles;
    }

    public void placeEnemies(TiledMap map, int walkableTileID, int enemyCount) {
        int managerCount = enemyManagers.size();
        List<Vector2> walkableTiles = getWalkableTiles(map, walkableTileID);
        Collections.shuffle(walkableTiles);

        int counter = 0;
        for (int i = 0; i < enemyCount && i < walkableTiles.size(); i++) {
            Vector2 pos = walkableTiles.get(i);
            //System.out.println("[Enemy Spawner] Placing enemy at tile (" + pos.x + ", " + pos.y + ")");
            enemyManagers.get(counter).spawnEnemy(pos.x, pos.y);
            counter++;
            if(counter == managerCount){counter = 0;}
        }
    }

    public void render(SpriteBatch batch) {
        for(EnemyManager<? extends Enemy> manager: enemyManagers){
            manager.render(batch);
        }

    }

    public void update(Float delta) {
        for(EnemyManager<? extends Enemy> manager: enemyManagers){
            manager.update(delta);
            playerDamage += manager.getPlayerDamage();
            manager.setPlayerDamage(0);
        }
    }

    public void checkDistances(Vector2 position) {
        for(EnemyManager<? extends Enemy> manager: enemyManagers){
            manager.checkDistances(position);
        }
    }

    public void injectGameScreen(GameScreen screen) {
        for (EnemyManager<? extends Enemy> manager : enemyManagers) {
            for (Enemy e : manager.getEnemies()) {
                e.setGameScreen(screen); // inject if supported
            }
        }
    }


    public List<EnemyManager<? extends Enemy>> getEnemyManagers() {
        return enemyManagers;
    }

    public int getPlayerDamage() {
        return playerDamage;
    }

    public void setPlayerDamage(int playerDamage) {
        this.playerDamage = playerDamage;
    }
}
