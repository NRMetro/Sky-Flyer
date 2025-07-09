package io.github.skyflyer.weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class WeaponManager {

    private final List<Weapon> weapons = new ArrayList<>();
    private final Texture knucklesTexture;
    private final Texture swordTexture;
    private final Texture slingshotTexture;

    public WeaponManager(Texture knucklesTexture, Texture swordTexture, Texture slingshotTexture) {
        this.knucklesTexture = knucklesTexture;
        this.swordTexture = swordTexture;
        this.slingshotTexture = slingshotTexture;
    }

    // Spawn weapons (creating specific weapon types)
    public void spawnWeapon(float tileX, float tileY) {
        System.out.println("[Weapon Manager] Spawning weapon at (" + tileX + ", " + tileY + ")");
        float worldX = tileX * 1f;
        float worldY = tileY * 1f + 0.8f;

        // Randomly pick a weapon type
        int randomWeapon = (int) (Math.random() * 3);
        Weapon weapon;

        switch (randomWeapon) {
            case 0:
                weapon = new Knuckles(knucklesTexture, new Vector2(worldX, worldY), 10);
                break;
            case 1:
                weapon = new Sword(swordTexture, new Vector2(worldX, worldY), 20);
                break;
            case 2:
            default:
                weapon = new SlingShot(slingshotTexture, new Vector2(worldX, worldY), 15);
                break;
        }

        weapons.add(weapon);
    }

    public void update(float delta) {
        for (Weapon weapon : weapons) {
            weapon.update();
        }
    }

    public void render(SpriteBatch batch) {
        for (Weapon weapon : weapons) {
            weapon.draw(batch);
        }
    }

    public List<Weapon> getWeapons() {
        return weapons;
    }

    // Dispose method to dispose of textures
    public void dispose() {
        knucklesTexture.dispose();
        swordTexture.dispose();
        slingshotTexture.dispose();
    }
}

