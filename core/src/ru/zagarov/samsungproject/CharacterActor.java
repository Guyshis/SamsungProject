package ru.zagarov.samsungproject;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
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

    private  ImageButton menuButton;

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
    public static  float actorX;
    public static  float actorY;
    private boolean check = true;
    private Animation<TextureRegion> animation;
    private Animation<TextureRegion> animation1;
    private Animation<TextureRegion> animation3;
    private Animation<TextureRegion> animation4;
    private Animation<TextureRegion> Tanimation;
    private Animation<TextureRegion> Tanimation1;
    private float deltaTime;
    private boolean checkAnim;
    private TextureRegion tr;
    private float startY = 31;
    public static boolean openDoor = false;






    public CharacterActor(ImageButton leftButton, ImageButton rightButton, ImageButton jumpButton, ImageButton menuButton, Game game, BaseRoomScreen baseRoomScreen) {
        System.out.println(LevelsScreen.EighthLevelCheck);
        if(LevelsScreen.EighthLevelCheck){
            texture = new Texture("Character_small.png");
            startY = 40;
        }else{
            texture = new Texture("Character1.png");
        }






        this.baseRoomScreen = baseRoomScreen;


        body = baseRoomScreen.createBody(
                BodyDef.BodyType.DynamicBody,
                20,
                40,
                texture.getWidth(),
                texture.getHeight(),
                true,
                "actor"
        );
        body1 = baseRoomScreen.createBodyLeg(
                BodyDef.BodyType.DynamicBody,
                21,
                startY,
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
        this.menuButton = menuButton;
        this.game = game;
        textureRegion = new TextureRegion(texture);
        setWidth(texture.getWidth());
        setHeight(texture.getHeight());
        setOriginX(texture.getWidth() / 2f);
        setOriginY(texture.getHeight() / 2f);

        hitbox = new Rectangle(getX(), getY(), getWidth(), getHeight());


        animation = getAnimation();
        animation1 = getAnimation1();
        animation3 = getAnimation3();
        animation4 = getAnimation4();



    }



    private Animation<TextureRegion> getAnimation(){
        Array<TextureRegion> array = new Array<>();
        for (int i = 1; i <= 2; i++) {
            String fileName = "Character-" + i + ".png";
            Texture t = new Texture(fileName);
            TextureRegion tr = new TextureRegion(t);
            array.add(tr);
        }
        Animation<TextureRegion> anim = new Animation<>(1f / 500, array, Animation.PlayMode.LOOP_PINGPONG);
        return anim;
    }
    private Animation<TextureRegion> getAnimation1(){
        Array<TextureRegion> array = new Array<>();
        for (int i = 1; i <= 2; i++) {
            String fileName = "Character1-" + i + ".png";
            Texture t = new Texture(fileName);
            TextureRegion tr = new TextureRegion(t);
            array.add(tr);
        }
        Animation<TextureRegion> anim = new Animation<>(1f / 500, array, Animation.PlayMode.LOOP_PINGPONG);
        return anim;
    }

    private Animation<TextureRegion> getAnimation3(){
        Array<TextureRegion> array = new Array<>();
        for (int i = 1; i <= 2; i++) {
            int ii = i + 2;
            String fileName = "Character-" + ii + ".png";
            Texture t = new Texture(fileName);
            TextureRegion tr = new TextureRegion(t);
            array.add(tr);
        }
        Animation<TextureRegion> anim = new Animation<>(1f / 500, array, Animation.PlayMode.LOOP_PINGPONG);
        return anim;
    }

    private Animation<TextureRegion> getAnimation4(){
        Array<TextureRegion> array = new Array<>();
        for (int i = 1; i <= 2; i++) {
            int ii = i + 2;
            String fileName = "Character1-" + ii + ".png";
            Texture t = new Texture(fileName);
            TextureRegion tr = new TextureRegion(t);
            array.add(tr);
        }
        Animation<TextureRegion> anim = new Animation<>(1f / 500, array, Animation.PlayMode.LOOP_PINGPONG);
        return anim;
    }


    @Override
    public void act(float delta) {
        deltaTime += delta/4;
        float characterVelocityX = body.getLinearVelocity().x;
        float characterVelocityY = 0;
        actorX = getX();
        actorY = getY();


        Vector2 currentPosition = body.getPosition();
        setX(currentPosition.x - getWidth()/2f);
        setY(currentPosition.y - getHeight()/2f);


//        if(menuButton.isPressed()){
//            game.setScreen(new LevelsScreen(game));
//        }



        if (leftButton.isPressed() || Gdx.input.isKeyPressed(Input.Keys.A)) {
            body.setLinearVelocity(-characterSpeed, body.getLinearVelocity().y);


            if (!shouldFlip) {
                textureRegion.flip(true, false);
                shouldFlip = true;
            }
            checkAnim = true;

        } else if (rightButton.isPressed() || Gdx.input.isKeyPressed(Input.Keys.D)) {
            body.setLinearVelocity(characterSpeed, body.getLinearVelocity().y);
            if (shouldFlip) {
                textureRegion.flip(true, false);
                shouldFlip = false;
            }
            checkAnim = true;
        } else {
            body.setLinearVelocity(0, body.getLinearVelocity().y);
            checkAnim = false;


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
        if (!LevelsScreen.SecondLevelCheck && !LevelsScreen.ThirdLevelCheck && !LevelsScreen.NinethLevelCheck){
            checkOverlap();
        }

        if (!LevelsScreen.SecondLevelCheck && !LevelsScreen.ThirdLevelCheck  && !LevelsScreen.NinethLevelCheck){
            checkKey();
        }
        if (check){
            if(LevelsScreen.NinethLevelCheck){
                texture = new Texture("key.png");
            } else{
                texture = new Texture("Character.png");
            }

            check = false;
            textureRegion = new TextureRegion(texture);
        }
        if(!LevelsScreen.NinethLevelCheck){
            checkDoor();
        }


        if(LevelsScreen.NinethLevelCheck){
            checkOverlapNine();
        }




    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if(!check_key_in_arm){
            Tanimation = animation;
            Tanimation1 = animation1;
        }else{
            Tanimation = animation3;
            Tanimation1 = animation4;
        }



        if (checkAnim){
            if (!shouldFlip){
                tr = Tanimation.getKeyFrame(deltaTime, true);
            } else{
                tr = Tanimation1.getKeyFrame(deltaTime, true);
            }

        } else{
            tr = textureRegion;
        }

        if(LevelsScreen.NinethLevelCheck){
            tr = textureRegion;
        }



        batch.draw(
                tr, //картинка которую будк отбражать
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
                    if (check_key_in_arm) {
                        openDoor = true;
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
                    if (leftButton.isPressed() || Gdx.input.isKeyPressed(Input.Keys.A)){
                        textureRegion.flip(true, false);
                    }
                    key.remove();



                }
            }



        }
    }


    private void checkOverlapNine() {
        Stage stage = getStage();
        Array<Actor> actors = stage.getActors();
        for (Actor actor : actors) {
            if (actor instanceof DoorActor) {
                DoorActor door = (DoorActor) actor;
                if (door.hitbox.overlaps(hitbox)) {
                    door.remove();
                    baseRoomScreen.world.destroyBody(door.body);
                }
            }
        }
    }





    private void checkDoor() {
        Stage stage = getStage();
        Array<Actor> actors = stage.getActors();
        for (Actor actor : actors) {
            if (actor instanceof TeleportActor) {
                TeleportActor teleportActor = (TeleportActor) actor;
                if (teleportActor.hitbox.overlaps(hitbox)) {

                    if(LevelsScreen.TenthLevelCheck){
                        game.setScreen(new EndScreen(game));
                    }
                    else if(LevelsScreen.NinethLevelCheck){
                        openDoor = false;
                        LevelsScreen.NinethLevelCheck= false;
                        LevelsScreen.TenthLevelCheck = true;
                        game.setScreen(new TenthScreen(game));
                    }
                    else if(LevelsScreen.EighthLevelCheck){
                        openDoor = false;
                        LevelsScreen.EighthLevelCheck= false;
                        LevelsScreen.NinethLevelCheck = true;
                        game.setScreen(new NinthScreen(game));

                    }
                    else if(LevelsScreen.SeventhLevelCheck){
                        openDoor = false;
                        LevelsScreen.SeventhLevelCheck = false;
                        LevelsScreen.EighthLevelCheck = true;
                        game.setScreen(new EighthScreen(game));
                    }
                    else if(LevelsScreen.SixthLevelCheck){
                        openDoor = false;
                        LevelsScreen.SixthLevelCheck = false;
                        LevelsScreen.SeventhLevelCheck = true;
                        game.setScreen(new SeventhScreen(game));
                    }
                    else if(LevelsScreen.FifthLevelCheck){
                        openDoor = false;
                        LevelsScreen.FifthLevelCheck = false;
                        LevelsScreen.SixthLevelCheck = true;
                        game.setScreen(new SixthScreen(game));
                    }
                    else if(LevelsScreen.FourthLevelCheck){
                        openDoor = false;
                        LevelsScreen.FourthLevelCheck = false;
                        LevelsScreen.FifthLevelCheck = true;
                        game.setScreen(new FifthScreen(game));
                    }
                    else if(LevelsScreen.ThirdLevelCheck){
                        openDoor = false;
                        LevelsScreen.ThirdLevelCheck = false;
                        LevelsScreen.FourthLevelCheck = true;
                        game.setScreen(new FourthScreen(game));
                    }
                    else if(LevelsScreen.SecondLevelCheck){
                        openDoor = false;
                        LevelsScreen.SecondLevelCheck = false;
                        LevelsScreen.ThirdLevelCheck = true;
                        game.setScreen(new ThirdScreen(game));

                    }
                    else if(LevelsScreen.FirstLevelCheck){
                        openDoor = false;
                        LevelsScreen.FirstLevelCheck = false;
                        LevelsScreen.SecondLevelCheck = true;
                        game.setScreen(new SecondScreen(game));
                    }

                }
            }
        }
    }



}





































































































































