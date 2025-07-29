package io.github.skyflyer.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import io.github.skyflyer.screens.GameScreen;
import io.github.skyflyer.weapons.Weapon;

public class Player extends GameActor {

    private final GameScreen screen;
    private final float dashCooldown;
    private float timeSeconds;
    private Weapon currentWeapon;
    private int health;
    private boolean holdingWeapon = false;

    private final Animation<TextureRegion> flyingLeftWithSword;
    private final Animation<TextureRegion> flyingRightWithSword;
    private final Animation<TextureRegion> flyingRightAnimation;
    private final Animation<TextureRegion> flyingLeftAnimation;
    private final Animation<TextureRegion> flyingLeftWithKnuckles;
    private final Animation<TextureRegion> flyingRightWithKnuckles;
    private final Animation<TextureRegion> flyingRightWithSlingshot;
    private final Animation<TextureRegion> flyingLeftWithSlingshot;
    private final Animation<TextureRegion> attackRightWithSword;
    private final Animation<TextureRegion> attackLeftWithSword;
    private final Animation<TextureRegion> attackRightWithKnuckles;
    private final Animation<TextureRegion> attackLeftWithKnuckles;
    private final Animation<TextureRegion> attackLeftWithSlingshot;
    private final Animation<TextureRegion> attackRightWithSlingshot;

    private float flyingTime = 0;
    private boolean facingRight = true;
    private boolean isAttacking = false;
    private float attackTime = 0f;
    private boolean canAttack = true;

    public Player(float x, float y, GameScreen screen) {
        setPosition(new Vector2(x, y));
        setTexture(new Texture(Gdx.files.internal("SkyFlyer/SkyFlyer.png")));
        this.screen = screen;
        dashCooldown = 3;
        timeSeconds = 3;
        this.currentWeapon = null;
        health = 5;

        // Load right-facing animation frames
        Array<TextureRegion> rightFrames = new Array<>();
        for (int i = 0; i < 3; i++) {
            Texture frame = new Texture(Gdx.files.internal("SkyFlyer/FlyerMovingRight/MovingRight_" + i + ".png"));
            rightFrames.add(new TextureRegion(frame));
        }
        flyingRightAnimation = new Animation<>(0.3f, rightFrames, Animation.PlayMode.LOOP);

        // Load left-facing animation frames
        Array<TextureRegion> leftFrames = new Array<>();
        for (int i = 0; i < 3; i++) {
            Texture frame = new Texture(Gdx.files.internal("SkyFlyer/FlyerMovingLeft/MovingLeft_" + i + ".png"));
            leftFrames.add(new TextureRegion(frame));
        }
        flyingLeftAnimation = new Animation<>(0.3f, leftFrames, Animation.PlayMode.LOOP);

        //Load right sword facing animation frames
        Array<TextureRegion> rightSwordFrames = new Array<>();
        for (int i = 0; i < 3; i++) {
            Texture frame = new Texture(Gdx.files.internal("SkyFlyer/FlyerMovingRightWithSword/swordMovingRight_" + i + ".png"));
            rightSwordFrames.add(new TextureRegion(frame));
        }
        flyingRightWithSword = new Animation<>(0.3f, rightSwordFrames, Animation.PlayMode.LOOP);

        //Load left sword facing animation frames
        Array<TextureRegion> leftSwordFrames = new Array<>();
        for (int i = 0; i < 3; i++) {
            Texture frame = new Texture(Gdx.files.internal("SkyFlyer/FlyerMovingLeftWithSword/swordMovingLeft_" + i + ".png"));
            leftSwordFrames.add(new TextureRegion(frame));
        }
        flyingLeftWithSword = new Animation<>(0.3f, leftSwordFrames, Animation.PlayMode.LOOP);

        //Load left right knuckles facing animation frames
        Array<TextureRegion> rightKnucklesFrames = new Array<>();
        for (int i = 0; i < 3; i++) {
            Texture frame = new Texture(Gdx.files.internal("SkyFlyer/FlyerMovingRightWithKnuckles/knucklesMovingRight_" + i + ".png"));
            rightKnucklesFrames.add(new TextureRegion(frame));
        }
        flyingRightWithKnuckles = new Animation<>(0.3f, rightKnucklesFrames, Animation.PlayMode.LOOP);

        //Load left knuckles facing animation frames
        Array<TextureRegion> leftKnucklesFrames = new Array<>();
        for (int i = 0; i < 3; i++) {
            Texture frame = new Texture(Gdx.files.internal("SkyFlyer/FlyerMovingLeftWithKnuckles/knucklesMovingLeft_" + i + ".png"));
            leftKnucklesFrames.add(new TextureRegion(frame));
        }
        flyingLeftWithKnuckles = new Animation<>(0.3f, leftKnucklesFrames, Animation.PlayMode.LOOP);

        //Load Left slingshot facing animation frames
        Array<TextureRegion> leftSlingshotFrames = new Array<>();
        for (int i = 0; i < 3; i++) {
            Texture frame = new Texture(Gdx.files.internal("SkyFlyer/FlyerMovingLeftWithSlingshot/slingshotMovingLeft_" + i + ".png"));
            leftSlingshotFrames.add(new TextureRegion(frame));
        }
        flyingLeftWithSlingshot = new Animation<>(0.3f, leftSlingshotFrames, Animation.PlayMode.LOOP);

        //Load right slingshot facing animation frames
        Array<TextureRegion> rightSlingshotFrames = new Array<>();
        for (int i = 0; i < 3; i++) {
            Texture frame = new Texture(Gdx.files.internal("SkyFlyer/FlyerMovingRightWithSlingshot/slingshotMovingRight_" + i + ".png"));
            rightSlingshotFrames.add(new TextureRegion(frame));
        }
        flyingRightWithSlingshot = new Animation<>(0.3f, rightSlingshotFrames, Animation.PlayMode.LOOP);

        //Load right facing sword attack animations
        Array<TextureRegion> rightSwordAttackFrames = new Array<>();
        for (int i = 0; i < 3; i++) {
            Texture frame = new Texture(Gdx.files.internal("Weapons/rightSwordAttack/attackMovingRight_" + i + ".png"));
            rightSwordAttackFrames.add(new TextureRegion(frame));
        }
        attackRightWithSword = new Animation<>(0.1f, rightSwordAttackFrames, Animation.PlayMode.NORMAL);

        //Load left facing sword attack animations
        Array<TextureRegion> leftSwordAttackFrames = new Array<>();
        for (int i = 0; i < 3; i++) {
            Texture frame = new Texture(Gdx.files.internal("Weapons/leftSwordAttack/attackMovingLeft_" + i + ".png"));
            leftSwordAttackFrames.add(new TextureRegion(frame));
        }
        attackLeftWithSword = new Animation<>(0.1f, leftSwordAttackFrames, Animation.PlayMode.NORMAL);

        //Load right facing knuckles attack animation
        Array<TextureRegion> rightKnucklesAttackFrames = new Array<>();
        for (int i = 0; i < 3; i++){
            Texture frame = new Texture(Gdx.files.internal("Weapons/rightKnucklesAttack/attackMovingRight_" + i + ".png"));
            rightKnucklesAttackFrames.add(new TextureRegion(frame));
        }
        attackRightWithKnuckles = new Animation<>(0.1f, rightKnucklesAttackFrames, Animation.PlayMode.NORMAL);

        //Load left facing knuckles attack animation
        Array<TextureRegion> leftKnucklesAttackFrames = new Array<>();
        for (int i = 0; i < 3; i++){
            Texture frame = new Texture(Gdx.files.internal("Weapons/leftKnucklesAttack/attackMovingLeft_" + i + ".png"));
            leftKnucklesAttackFrames.add(new TextureRegion(frame));
        }
        attackLeftWithKnuckles = new Animation<>(0.1f, leftKnucklesAttackFrames, Animation.PlayMode.NORMAL);

        //Load left facing slingshot attack animation
        Array<TextureRegion> leftSlingshotAttackFrames = new Array<>();
        for (int i = 0; i < 3; i++){
            Texture frame = new Texture(Gdx.files.internal("Weapons/leftSlingshotAttack/attackMovingLeft_" + i + ".png"));
            leftSlingshotAttackFrames.add(new TextureRegion(frame));
        }
        attackLeftWithSlingshot = new Animation<>(0.1f, leftSlingshotAttackFrames, Animation.PlayMode.NORMAL);

        //Load right facing slingshot attack animation
        Array<TextureRegion> rightSlingshotAttackFrames = new Array<>();
        for (int i = 0; i < 3; i++){
            Texture frame = new Texture(Gdx.files.internal("Weapons/rightSlingshotAttack/attackMovingRight_" + i + ".png"));
            rightSlingshotAttackFrames.add(new TextureRegion(frame));
        }
        attackRightWithSlingshot = new Animation<>(0.1f, rightSlingshotAttackFrames, Animation.PlayMode.NORMAL);

    }

    public void setCoords(float x, float y) {
        setPosition(new Vector2(x, y));
    }

    public void update(float delta) {
        float speed = 30f;
        float dx = 0;
        float dy = 0;
        float dash = 0;

        flyingTime += delta;

        if(Gdx.input.isKeyJustPressed(Input.Keys.M) && timeSeconds >= dashCooldown) {
            dash = 3;
            timeSeconds = 0;
        }

        timeSeconds += Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            dy += speed * delta + dash;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            dy -= speed * delta + dash;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            dx -= speed * delta + dash;
            facingRight = false;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            dx += speed * delta + dash;
            facingRight = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.F)) finish();

        if(dx != 0 || dy != 0) {
            move(dx,dy);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !isAttacking && canAttack){
            System.out.println("Space bar pressed!");

            if (holdingWeapon && currentWeapon != null){
                System.out.println("Activating Weapon!");
                currentWeapon.activate(this);
                isAttacking = true;
                canAttack = false;
                attackTime = 0f;
            }
        }
        if (isAttacking){
            attackTime += delta;
            if (attackTime >= 0.6f){
                System.out.println("Attack finished!");
                isAttacking = false;
                canAttack = true;
            }
        }
    }

    public void render(SpriteBatch batch) {
        Animation<TextureRegion> currentAnim;

        if (isAttacking && holdingWeapon && currentWeapon != null) {
            String weaponName = currentWeapon.getClass().getSimpleName();
            switch (weaponName) {
                case "Sword":
                    currentAnim = facingRight ? attackRightWithSword : attackLeftWithSword;
                    break;
                case "Knuckles":
                    currentAnim = facingRight ? attackRightWithKnuckles : attackLeftWithKnuckles;
                    break;
                case "Slingshot":
                    currentAnim = facingRight ? attackRightWithSlingshot : attackLeftWithSlingshot;
                    break;
                default:
                    currentAnim = facingRight ? flyingRightAnimation : flyingLeftAnimation;
                    break;
            }
        } else if (holdingWeapon && currentWeapon != null) {
            String weaponName = currentWeapon.getClass().getSimpleName();
            switch (weaponName) {
                case "Sword":
                    currentAnim = facingRight ? flyingRightWithSword : flyingLeftWithSword;
                    break;
                case "Knuckles":
                    currentAnim = facingRight ? flyingRightWithKnuckles : flyingLeftWithKnuckles;
                    break;
                case "Slingshot":
                    currentAnim = facingRight ? flyingRightWithSlingshot : flyingLeftWithSlingshot;
                    break;
                default:
                    currentAnim = facingRight ? flyingRightAnimation : flyingLeftAnimation;
                    break;
            }
        } else {
            currentAnim = facingRight ? flyingRightAnimation : flyingLeftAnimation;
        }

        TextureRegion currentFrame = currentAnim.getKeyFrame(isAttacking ? attackTime : flyingTime, false);
        batch.draw(currentFrame, position.x, position.y, 2.0f, 2.0f);
    }


    private void finish() {
        screen.isFinish(position.x, position.y);
    }

    private void move(float dx, float dy) {
//        System.out.println("MOVING BY " + dx + " " + dy);
        float x = (int) position.x + 3 * dx;
        float y = (int) position.y + 2 * dy;

        boolean solid = screen.isTileSolid(x, y);

        if(!solid){
            position.x += dx;
            position.y += dy;
//            System.out.println("MOVING TO " + position.x + " " + position.y);
        }
    }

    public boolean isFacingRight(){
        return facingRight;
    }

    public Weapon getCurrentWeapon(){
        return currentWeapon;
    }

    public void pickUpWeapon(Weapon newWeapon){
        this.currentWeapon = newWeapon;
        this.holdingWeapon = true;
        System.out.println("Picked up " + newWeapon.getClass().getSimpleName());
    }

    public int getHealth() {
        return health;
    }

    public void removeHealth(int health) {
        this.health -= health;
    }

    public GameScreen getScreen(){
        return screen;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Vector2 getTilePosition(){
        return new Vector2(Math.round(position.x), Math.round(position.y));
    }

    public void dispose(){
//        for (TextureRegion region : flyingRightAnimation.getKeyFrames()) {
//            region.getTexture().dispose();
//        }
//        for (TextureRegion region : flyingLeftAnimation.getKeyFrames()) {
//            region.getTexture().dispose();
//        }
//        for (TextureRegion region : flyingLeftWithSword.getKeyFrames()) {
//            region.getTexture().dispose();
//        }
//        for (TextureRegion region : flyingRightWithSword.getKeyFrames()) {
//            region.getTexture().dispose();
//        }
//        for (TextureRegion region : flyingLeftWithKnuckles.getKeyFrames()){
//            region.getTexture().dispose();
//        }
//        for (TextureRegion region : flyingRightWithKnuckles.getKeyFrames()){
//            region.getTexture().dispose();
//        }
//        for (TextureRegion region : flyingLeftWithSlingshot.getKeyFrames()){
//            region.getTexture().dispose();
//        }
//        for (TextureRegion region : flyingRightWithSlingshot.getKeyFrames()){
//            region.getTexture().dispose();
//        }
//        for (TextureRegion region : attackLeftWithSword.getKeyFrames()){
//            region.getTexture().dispose();
//        }
//        for (TextureRegion region : attackRightWithSword.getKeyFrames()){
//            region.getTexture().dispose();
//        }
//        for (TextureRegion region : attackRightWithKnuckles.getKeyFrames()){
//            region.getTexture().dispose();
//        }
//        for (TextureRegion region : attackLeftWithKnuckles.getKeyFrames()){
//            region.getTexture().dispose();
//        }
//        for (TextureRegion region : attackRightWithSlingshot.getKeyFrames()){
//            region.getTexture().dispose();
//        }
//        for (TextureRegion region : attackLeftWithSlingshot.getKeyFrames()){
//            region.getTexture().dispose();
//        }
    }
}
