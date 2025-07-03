package io.github.skyflyer.character;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class groundEnemySpawner {

    private final io.github.skyflyer.character.groundEnemyManager groundEnemyManager;

    public groundEnemySpawner(groundEnemyManager groundEnemyManager) {
        this.groundEnemyManager = groundEnemyManager;
    }

    public List<Vector2> getWalkableTiles(TiledMap map, int walkableTileID) {
        List<Vector2> walkableTiles = new ArrayList<>();
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("collision");

        if (layer == null) {
            System.out.println("[Ground Spawner] ERROR: 'collision' layer has not been found");
            return walkableTiles;
        }

        int width = layer.getWidth();
        int height = layer.getHeight();
        System.out.println("[Ground Spawner]ground Spawner Scanning " + width + "x" + height + "tiles...");

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
        System.out.println("Ground Spawner found " + walkableTiles.size());
        return walkableTiles;
    }

    public void placeEnemies(TiledMap map, int walkableTileID, int enemyCount) {
        List<Vector2> walkableTiles = getWalkableTiles(map, walkableTileID);
        Collections.shuffle(walkableTiles);

        for (int i = 0; i < enemyCount && i < walkableTiles.size(); i++) {
            Vector2 pos = walkableTiles.get(i);
            System.out.println("[Ground Spawner] Placing enemy at tile (" + pos.x + ", " + pos.y + ")");
            groundEnemyManager.spawnEnemy(pos.x, pos.y);
        }
    }
}
