package io.github.skyflyer.screens;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import io.github.skyflyer.character.Player;
import io.github.skyflyer.character.enemyGeneric.Enemy;
import io.github.skyflyer.character.enemyGeneric.EnemyManager;
import io.github.skyflyer.character.enemyGeneric.EnemySpawner;
import io.github.skyflyer.character.enemyType.Grounder;
import io.github.skyflyer.character.enemyType.PewPew;

import java.util.ArrayList;
import java.util.List;


public class GameScreen extends SkyScreen {

    TiledMap map;
    OrthogonalTiledMapRenderer renderer;
    OrthographicCamera camera;
    Player player;
    SpriteBatch batch;
    EnemySpawner enemySpawner;
    Texture groundEnemyTexture;

    public GameScreen(Game game) {
        super(game);
    }

    public GameScreen(Game game,String filename) {
        super(game);
        setMap(filename);
    }

    @Override
    public void show() {
        if(map == null) {
            System.out.println("map is null");
            map = new TmxMapLoader().load("maps/FlyMap1.tmx");
        }
        float unitScale = 1 / 32f;
        renderer = new OrthogonalTiledMapRenderer(map, unitScale);

        player = new Player(1, 195,this); // start at some world coordinate
        batch = new SpriteBatch();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, (Gdx.graphics.getWidth() * unitScale) / 2f, (Gdx.graphics.getWidth() * unitScale) / 2f);
        camera.update();

        renderer.setView(camera);

        List<EnemyManager<? extends Enemy>> managers = new ArrayList<>();
        managers.add(new EnemyManager<>(Grounder::new));
        managers.add(new EnemyManager<>(PewPew::new));

        enemySpawner = new EnemySpawner(managers);
        enemySpawner.placeEnemies(map, 1, 30);

    }

    @Override
    public void render(float delta) {
        //System.out.println("Player: " + player.position + " | Camera: " + camera.position);
        player.update(delta);

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1); // dark gray background
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Vector2 position = player.getPosition();
        camera.position.set(position.x, position.y, 0);
        camera.update();
        renderer.setView(camera);

        renderer.render();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        enemySpawner.checkDistances(position);


        enemySpawner.update(delta);
        enemySpawner.render(batch);

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
        groundEnemyTexture.dispose();
    }

    public void setMap(String fileName){
        map = new TmxMapLoader().load(fileName);
    }

    public boolean isTileSolid(float x, float y) {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("collision");

        int tileX = (int) (x);
        int tileY = (int) (y);
        //System.out.println(tileX + " " + tileY);

        /*
            ARBITRARY NUMBERS THAT NEED TO BE SORTED BETTER WHEN
            WE HAVE A BETTER IDEA FOR HOW OUR MAPS WILL EXIST
         */
        if(x < 0 || y < 0 || x > 200 || y > 200){
            return true;
        }
        TiledMapTileLayer.Cell cell = layer.getCell(tileX, tileY);

        if(cell != null && cell.getTile() != null) {
            if(cell.getTile().getProperties() != null) {
                MapProperties properties = cell.getTile().getProperties();
                //System.out.println(properties.containsKey("solid") + " " + properties.get("solid", Boolean.class));
                return properties.containsKey("solid") && properties.get("solid", Boolean.class);
            }

        }
        return false;
    }
}
