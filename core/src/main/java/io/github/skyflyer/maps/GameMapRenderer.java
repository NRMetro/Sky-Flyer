package io.github.skyflyer.maps;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;

public class GameMapRenderer extends OrthogonalTiledMapRenderer {

    public GameMapRenderer(TiledMap map) {
        super(map);
    }

    @Override
    public void setView(OrthographicCamera orthographicCamera) {

    }

    @Override
    public void setView(Matrix4 matrix4, float v, float v1, float v2, float v3) {

    }

    @Override
    public void render() {

    }

    @Override
    public void render(int[] ints) {

    }

    public void dispose () {

    }

}
