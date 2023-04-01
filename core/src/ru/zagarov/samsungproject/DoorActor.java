package ru.zagarov.samsungproject;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class DoorActor extends Actor {

    private Texture texture;
    private TextureRegion textureRegion;
    public Rectangle hitbox;

    public DoorActor(float startX, float startY) {

        setX(startX);
        setY(startY);


        texture = new Texture("door.png");
        textureRegion = new TextureRegion(texture);


        setWidth(texture.getWidth());
        setHeight(texture.getHeight());


        hitbox = new Rectangle(startX, startY, getWidth(), getHeight());


    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
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
}

