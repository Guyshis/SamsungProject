package ru.zagarov.samsungproject;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;


public class TeleportActor extends Actor{
    private Texture texture;
    private TextureRegion textureRegion;
    public static Rectangle hitbox;

    static Body body;
    private BaseRoomScreen baseRoomScreen;
    private boolean isDragging;

    public TeleportActor(float startX, float startY, BaseRoomScreen baseRoomScreen) {
        texture = new Texture("door1.png");

        this.baseRoomScreen = baseRoomScreen;

        body = baseRoomScreen.createDoorBody(startX, startY, texture.getHeight(), texture.getWidth());


        setX(startX);
        setY(startY);

        textureRegion = new TextureRegion(texture);
        setWidth(texture.getWidth());
        setHeight(texture.getHeight());
        setOriginX(texture.getWidth() / 2f);
        setOriginY(texture.getHeight() / 2f);



        hitbox = new Rectangle(startX-1, startY-1, getWidth()+2, getHeight()+2);

    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
//        batch.draw(
//                textureRegion, //картинка которую будк отбражать
//                getX(), // координаты нижней левой точки картинки по Х
//                getY(), // координаты нижней левой точки картинки по У
//                getOriginX(), // это центр вращения Х
//                getOriginY(), // это центр вращения У
//                getWidth(),
//                getHeight(),
//                getScaleX(),
//                getScaleY(),
//                getRotation()
//        );
    }
}