package io.github.skyflyer.maps;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;


public class GameMap{
    TiledMap map;

    public GameMap() {
        this.map = new TmxMapLoader().load("TileMap.tmx");
    }

    public void dispose(){
        map.dispose();
    }
}
