package ru.zagarov.samsungproject;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;


public class KeyActor extends Actor{
    private Texture texture;
    private TextureRegion textureRegion;
    public static Rectangle hitbox;

    static Body body;
    private static BaseRoomScreen baseRoomScreen;
    private boolean isDragging;
    private Game game;

    public KeyActor(float startX, float startY, BaseRoomScreen baseRoomScreen, BodyDef.BodyType type, Game game) {

        if(LevelsScreen.NinethLevelCheck){
            texture = new Texture("key_Character1.png");
        }else{
            texture = new Texture("key.png");
        }


        this.baseRoomScreen = baseRoomScreen;

        body = baseRoomScreen.createBody(
                type,
                startX,
                startY,
                texture.getWidth(),
                texture.getHeight(),
                false,
                "key"
        );


        textureRegion = new TextureRegion(texture);
        setWidth(texture.getWidth());
        setHeight(texture.getHeight());
        setOriginX(texture.getWidth() / 2f);
        setOriginY(texture.getHeight() / 2f);



        this.game = game;
        hitbox = new Rectangle(startX, startY, getWidth(), getHeight()+1);

    }

    @Override
    public void act(float delta) {

        super.act(delta);
        if (LevelsScreen.SeventhLevelCheck || LevelsScreen.FourthLevelCheck || LevelsScreen.FirstLevelCheck){
            body.setAwake(false);
        }

        if(LevelsScreen.NinethLevelCheck){
            texture = new Texture("key_Character.png");
            textureRegion = new TextureRegion(texture);
        }


        Vector2 currentPosition = body.getPosition();
        setX(currentPosition.x - getWidth()/2f);
        setY(currentPosition.y - getHeight()/2f);
        hitbox.setX(currentPosition.x - getWidth()/2f);
        hitbox.setY(currentPosition.y - getHeight()/2f);







        if(LevelsScreen.SecondLevelCheck || LevelsScreen.ThirdLevelCheck){
            checkOverlapKeyDoor();
        }
        if (LevelsScreen.NinethLevelCheck){
            checkOverlapKeyDoorNine();
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


    public void checkOverlapKeyDoor() {
        Stage stage = getStage();
        Array<Actor> actors = stage.getActors();
        for (Actor actor : actors) {
            if (actor instanceof DoorActor) {
                DoorActor door = (DoorActor) actor;
                if (door.hitbox.overlaps(hitbox)) {
                    door.remove();
                    baseRoomScreen.world.destroyBody(door.body);
                    this.remove();
                    baseRoomScreen.world.destroyBody(body);
                    MyContactListener.isActorOnGround = true;
                }
            }
        }
    }

    public void checkOverlapKeyDoorNine() {
        Stage stage = getStage();
        Array<Actor> actors = stage.getActors();
        for (Actor actor : actors) {
            if (actor instanceof TeleportActor) {
                TeleportActor teleportActor = (TeleportActor) actor;
                if (teleportActor.hitbox.overlaps(hitbox)) {
                    game.setScreen(new TenthScreen(game));
                }
            }
        }
    }




}
