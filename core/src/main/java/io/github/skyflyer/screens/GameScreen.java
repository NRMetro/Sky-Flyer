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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import io.github.skyflyer.character.Player;
import io.github.skyflyer.character.enemyGeneric.Enemy;
import io.github.skyflyer.character.enemyGeneric.EnemyManager;
import io.github.skyflyer.character.enemyGeneric.EnemySpawner;
import io.github.skyflyer.character.enemyType.Grounder;
import io.github.skyflyer.character.enemyType.BoomBoom;

import io.github.skyflyer.weapons.SlingshotBullet;
import io.github.skyflyer.weapons.Weapon;
import io.github.skyflyer.weapons.WeaponManager;
import io.github.skyflyer.weapons.WeaponSpawner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class GameScreen extends SkyScreen {

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private Player player;
    private SpriteBatch batch;
    private EnemySpawner enemySpawner;
    private WeaponManager weaponManager;
    private WeaponSpawner weaponSpawner;
    private Stage stage;
    private Boolean endless = true;
    private Table heartTable;
    private ArrayList<Image> hearts = new ArrayList<>();
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private int fileNumber;
    private int totalMaps = 3;
    private List<SlingshotBullet> playerBullets = new ArrayList<>();

    public GameScreen(Game game) {
        super(game);
        fileNumber = 1;
        setMap();
    }

    @Override
    public void show() {
        if(map == null) {
            System.out.println("map is null");
            map = new TmxMapLoader().load("maps/FlyMap1.tmx");
        }

        if(player == null) {
            player = new Player(1, 195,this); // start at some world coordinate
        }

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        heartTable = new Table();
        heartTable.setFillParent(true);
        stage.addActor(heartTable);

        heartTable.setDebug(true);
        heartTable.top().left();

        Texture heartTexture = new Texture(Gdx.files.internal("heart.png"));
        for(int i = 0; i < player.getHealth(); i++) {
            hearts.add( new Image(heartTexture));
            heartTable.add(hearts.get(i)).pad(5);
        }
        heartTable.row();

        float unitScale = 1 / 32f;
        renderer = new OrthogonalTiledMapRenderer(map, unitScale);

        batch = new SpriteBatch();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, (Gdx.graphics.getWidth() * unitScale) / 2f, (Gdx.graphics.getWidth() * unitScale) / 2f);
        camera.update();

        renderer.setView(camera);

        List<EnemyManager<? extends Enemy>> managers = new ArrayList<>();
        managers.add(new EnemyManager<>(Grounder::new));
        managers.add(new EnemyManager<>(BoomBoom::new));

        enemySpawner = new EnemySpawner(managers);
        enemySpawner.placeEnemies(map, 1, 30);

        // Initialize weapon manager and spawner
        Texture knucklesTexture = new Texture("Weapons/knuckles.png");
        Texture swordTexture = new Texture("Weapons/sword.png");
        Texture slingshotTexture = new Texture("Weapons/slingshot.png");
        weaponManager = new WeaponManager(knucklesTexture, swordTexture, slingshotTexture);
        weaponSpawner = new WeaponSpawner(weaponManager);

        // Place weapons in the game world
        weaponSpawner.placeWeapons(map, 1, 60);

    }

    @Override
    public void render(float delta) {
        //System.out.println("Player: " + player.position + " | Camera: " + camera.position);
        player.update(delta);
        checkWeaponPickup();

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

        if(enemySpawner.getPlayerDamage() > 0){
            playerHit(enemySpawner.getPlayerDamage());
            enemySpawner.setPlayerDamage(0);
        }

        weaponManager.update(delta);
        weaponManager.render(batch);

        player.render(batch);
        for(Enemy e: enemies){
            e.render(batch);
        }

        Iterator<SlingshotBullet> iter = playerBullets.iterator();
        while(iter.hasNext()){
            SlingshotBullet b = iter.next();
            b.update(delta);
            b.render(batch);
            if (b.isToRemove()) iter.remove();
        }

        batch.end();

        stage.act(delta);
        stage.draw();

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new MainMenuScreen(game));
            dispose();
        }
    }

    private void playerHit(int playerDamage) {
//        System.out.println("playerHit with damage: " + playerDamage);
        int i = 0;
        int health = player.getHealth();
//        System.out.println("health: " + health);
        while(i < playerDamage && health > 0){
            heartTable.removeActor(hearts.get(health - 1));
            player.removeHealth(1);
            health--;
            i++;
        }
        if(health == 0){
            System.out.println("Game Over");
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

    public void setMap(){
        String filename = "maps/FlyMap" + fileNumber + ".tmx";
        map = new TmxMapLoader().load(filename);
        show();
    }

    public void isFinish(float x, float y) {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("finish");
        int tileX = (int) (x);
        int tileY = (int) (y);

        TiledMapTileLayer.Cell cell = layer.getCell(tileX, tileY);

        if(cell != null && cell.getTile() != null) {
            if (cell.getTile().getProperties() != null) {
                MapProperties properties = cell.getTile().getProperties();
                //System.out.println(properties.containsKey("solid") + " " + properties.get("solid", Boolean.class));
                if(properties.containsKey("finish") && properties.get("finish", Boolean.class)){
                    fileNumber++;
                    if(fileNumber == totalMaps && endless == true) {
                        fileNumber = 1;
                    }
                    setMap();
                }

            }
        }

    }

    private void checkWeaponPickup() {
        Vector2 playerTilePos = player.getTilePosition();

        Iterator<Weapon> iterator = weaponManager.getWeapons().iterator();

        while (iterator.hasNext()) {
            Weapon weapon = iterator.next();
            Vector2 weaponTilePos = new Vector2(Math.round(weapon.getPosition().x), Math.round(weapon.getPosition().y));

            if (playerTilePos.dst(weaponTilePos) <= 1.0f) {
                player.pickUpWeapon(weapon);
                weaponManager.removeWeapon(weapon);
                break;
            }
        }
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

    public List<Enemy> getEnemies(){
        List<Enemy> allEnemies = new ArrayList<>();
        for (EnemyManager<? extends Enemy> manager : enemySpawner.getEnemyManagers()) {
            allEnemies.addAll(manager.getEnemies());
        }
        return allEnemies;
    }

    public void addBullets(SlingshotBullet bullet){
        playerBullets.add(bullet);
    }


    public void addEnemy(Enemy e){
        enemies.add(e);
    }
}
