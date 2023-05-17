package ru.zagarov.samsungproject;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.Array;


public class CharacterActor extends Actor {

    private static final float JUMP_SPEED = 10.0f;

    boolean isJumping = true;

    private ImageButton leftButton;

    private ImageButton rightButton;

    private ImageButton jumpButton;

    public TextureRegion textureRegion;

    public Texture texture;

    public Rectangle hitbox;


    private float jumpForce = 500.0f;


    private int baseX = 0;
    private int baseY = 16;
    private boolean shouldFlip = false;

    private int speed = 5;

    public static boolean check_key_in_arm = false;


    private Game game;

    private boolean checkJump = true;

    private float jumpHeight = 120;

    private Body body;
    private Body body1;

    private BaseRoomScreen baseRoomScreen;

    private boolean changeTextureKey = true;
    private LevelsScreen levelsScreen;
    public static float characterSpeed = 40f;


    public CharacterActor(ImageButton leftButton, ImageButton rightButton, ImageButton jumpButton, Game game, BaseRoomScreen baseRoomScreen) {

        texture = new Texture("Character.png");


        this.baseRoomScreen = baseRoomScreen;


        body = baseRoomScreen.createBody(
                BodyDef.BodyType.DynamicBody,
                20,
                48,
                texture.getWidth(),
                texture.getHeight(),
                true,
                "actor"
        );
        body1 = baseRoomScreen.createBodyLeg(
                BodyDef.BodyType.DynamicBody,
                21,
                40,
                texture.getWidth() - 3,
                0f
        );
//        // создаем соединительный сустав типа "DistanceJoint"
//        DistanceJointDef jointDef = new DistanceJointDef();
//        jointDef.initialize(body, body1, body.getWorldCenter(), body1.getWorldCenter()); // устанавливаем начальную и конечную точки соединения
//        jointDef.collideConnected = true; // настраиваем поведение столкновений
//
//        // задаем ограничения на длину соединения
//        float minLength = 30f; // минимальная длина
//        float maxLength = 30f; // максимальная длина
//        jointDef.length = MathUtils.clamp(jointDef.length, minLength, maxLength);
//
//        baseRoomScreen.world.createJoint(jointDef); // создаем сустав в мире Box2D


        // создаем соединительный сустав типа "RevoluteJoint"
        RevoluteJointDef jointDef = new RevoluteJointDef();
        jointDef.initialize(body, body1, new Vector2(20, 40)); // x и y - координаты точки соединения
        jointDef.collideConnected = true; // устанавливаем разрешение на столкновения

        baseRoomScreen.world.createJoint(jointDef); // создаем сустав в мире Box2D



        this.leftButton = leftButton;
        this.rightButton = rightButton;
        this.jumpButton = jumpButton;
        this.game = game;
        textureRegion = new TextureRegion(texture);
        setWidth(texture.getWidth());
        setHeight(texture.getHeight());
        setOriginX(texture.getWidth() / 2f);
        setOriginY(texture.getHeight() / 2f);

        hitbox = new Rectangle(getX(), getY(), getWidth(), getHeight());


    }

    @Override
    public void act(float delta) {
        float characterVelocityX = body.getLinearVelocity().x;
        float characterVelocityY = 0;


        Vector2 currentPosition = body.getPosition();
        setX(currentPosition.x - getWidth()/2f);
        setY(currentPosition.y - getHeight()/2f);


//        if (rightButton.isPressed() || Gdx.input.isKeyPressed(Input.Keys.D)) {
//            body.setLinearVelocity(characterSpeed, characterVelocityY);
////            moveBy(speed, 0);
//            if (shouldFlip) {
//                textureRegion.flip(true, false);
//                shouldFlip = false;
//            }
//
//        }
//        if (leftButton.isPressed() || Gdx.input.isKeyPressed(Input.Keys.A)) {
//            if (!isJumping){
//                body.setLinearVelocity(-characterSpeed, characterVelocityY);
//            } else {
//                body.setLinearVelocity(-characterSpeed, 500);
//            }
//
////            moveBy(-speed, 0);
//            if (!shouldFlip) {
//                textureRegion.flip(true, false);
//                shouldFlip = true;
//            }
//        }


        if (leftButton.isPressed() || Gdx.input.isKeyPressed(Input.Keys.A)) {
            body.setLinearVelocity(-characterSpeed, body.getLinearVelocity().y);
            if (!shouldFlip) {
                textureRegion.flip(true, false);
                shouldFlip = true;
            }
        } else if (rightButton.isPressed() || Gdx.input.isKeyPressed(Input.Keys.D)) {
            body.setLinearVelocity(characterSpeed, body.getLinearVelocity().y);
            if (shouldFlip) {
                textureRegion.flip(true, false);
                shouldFlip = false;
            }
        } else {
            body.setLinearVelocity(0, body.getLinearVelocity().y);
        }


        if ((jumpButton.isPressed() || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) && MyContactListener.isActorOnGround) {
            if (rightButton.isPressed() || Gdx.input.isKeyPressed(Input.Keys.D)){
                Vector2 jumpVector = new Vector2(0f, jumpForce); // вектор силы
                body.applyLinearImpulse(jumpVector, body.getWorldCenter(), true); // применение импульса
                body.setLinearVelocity(characterSpeed, body.getLinearVelocity().y);
            } else if (leftButton.isPressed() || Gdx.input.isKeyPressed(Input.Keys.A)){
                Vector2 jumpVector = new Vector2(0f, jumpForce); // вектор силы
                body.applyLinearImpulse(jumpVector, body.getWorldCenter(), true); // применение импульса
                body.setLinearVelocity(-characterSpeed, body.getLinearVelocity().y);
            }else{
                Vector2 jumpVector = new Vector2(0f, jumpForce); // вектор силы
                body.applyLinearImpulse(jumpVector, body.getWorldCenter(), true); // применение импульса
            }
        }



        if (getX() < 0) {
            setX(0);
        }
        if (getY() < 0) {
            setY(0);
        }
        if (getX() > MyGdxGame.SCREEN_WIDTH - getWidth()) {
            setX(MyGdxGame.SCREEN_WIDTH - getWidth());
        }
        if (getY() > MyGdxGame.SCREEN_HEIGHT - getWidth()) {
            setY(MyGdxGame.SCREEN_HEIGHT - getWidth());
        }





        hitbox.setPosition(getX(), getY());
        if (!levelsScreen.SecondLevelCheck && !levelsScreen.ThirdLevelCheck){
            checkOverlap();
        }

        if (!levelsScreen.SecondLevelCheck && !levelsScreen.ThirdLevelCheck){
            checkKey();
        }




    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.draw(
                textureRegion, //картинка которую будк отбражать
                getX(), // координаты нижней левой точки картинки по Х
                getY(), // координаты нижней левой точки картинки по У
                getOriginX(), // это центр вращения Х
                getOriginY(), // это центр вращения У
                getWidth(),
                getHeight(),
                getScaleX(),
                getScaleY(),
                getRotation()
        );
    }


    private void checkOverlap() {
        Stage stage = getStage();
        Array<Actor> actors = stage.getActors();
        for (Actor actor : actors) {
            if (actor instanceof DoorActor) {
                DoorActor door = (DoorActor) actor;
                if (door.hitbox.overlaps(hitbox)) {
                    System.out.println("ok");
                    if (check_key_in_arm) {
                        door.remove();
                        baseRoomScreen.world.destroyBody(door.body);
                        texture = new Texture("Character.png");
                        textureRegion = new TextureRegion(texture);
                        check_key_in_arm = false;
                    }
                }
            }
        }
    }





    private void checkKey() {
        Stage stage = getStage();
        Array<Actor> actors = stage.getActors();
        for (Actor actor : actors) {
            if (actor instanceof KeyActor) {
                KeyActor key = (KeyActor) actor;
                if (key.hitbox.overlaps(hitbox)) {
                    texture = new Texture("Character_key.png");
                    textureRegion = new TextureRegion(texture);
                    baseRoomScreen.world.destroyBody(key.body);
                    check_key_in_arm = true;
                    key.remove();



                }
            }



        }
    }
}





































































































































