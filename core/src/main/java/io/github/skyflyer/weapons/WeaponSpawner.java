package io.github.skyflyer.weapons;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WeaponSpawner {

    private final WeaponManager weaponManager;

    public WeaponSpawner(WeaponManager weaponManager) {
        this.weaponManager = weaponManager;
    }

    public List<Vector2> getWalkableTiles(TiledMap map, int walkableTileID) {
        List<Vector2> walkableTiles = new ArrayList<>();
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("collision");

        if (layer == null) {
            System.out.println("[Weapon Spawner] ERROR: 'collision' layer has not been found");
            return walkableTiles;
        }

        int width = layer.getWidth();
        int height = layer.getHeight();
        System.out.println("[Weapon Spawner] Scanning " + width + "x" + height + " tiles...");

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                if (cell != null && cell.getTile() != null) {
                    int tileId = cell.getTile().getId();
                    if (tileId == walkableTileID) {
                        walkableTiles.add(new Vector2(x, y));
                    }
                }
            }
        }
        System.out.println("[Weapon Spawner] Found " + walkableTiles.size() + " walkable tiles");
        return walkableTiles;
    }

    public void placeWeapons(TiledMap map, int walkableTileID, int weaponCount) {
        List<Vector2> walkableTiles = getWalkableTiles(map, walkableTileID);
        Collections.shuffle(walkableTiles);

        for (int i = 0; i < weaponCount && i < walkableTiles.size(); i++) {
            Vector2 pos = walkableTiles.get(i);
            System.out.println("[Weapon Spawner] Placing weapon at tile (" + pos.x + ", " + pos.y + ")");
            weaponManager.spawnWeapon(pos.x, pos.y);
        }
    }
}
