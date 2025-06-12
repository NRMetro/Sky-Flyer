package io.github.skyflyer.screens;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import io.github.skyflyer.Player;


public class GameScreen extends SkyScreen {

    TiledMap map;
    OrthogonalTiledMapRenderer renderer;
    OrthographicCamera camera;
    Player player;
    SpriteBatch batch;

    public GameScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        this.map = new TmxMapLoader().load("TileMap.tmx");
        float unitScale = 1 / 16f;
        renderer = new OrthogonalTiledMapRenderer(map, unitScale);

        player = new Player(1, 17); // start at some world coordinate
        batch = new SpriteBatch();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, (Gdx.graphics.getWidth() * unitScale) / 2f, (Gdx.graphics.getWidth() * unitScale) / 2f);
        camera.update();

        renderer.setView(camera);
    }

    @Override
    public void render(float delta) {
        System.out.println("Player: " + player.position + " | Camera: " + camera.position);
        player.update(delta);

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1); // dark gray background
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.position.set(player.position.x, player.position.y, 0);
        camera.update();
        renderer.setView(camera);

        renderer.render();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        player.render(batch);
        batch.end();
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new MainMenuScreen(game));
        }
    }

    @Override
    public void dispose() {
        Gdx.app.debug("SkyFly", "dispose game screen");
        map.dispose();
        renderer.dispose();
        player.dispose();
        batch.dispose();
    }

    public void setMap(String fileName){
        map = new TmxMapLoader().load(fileName);
    }
}
