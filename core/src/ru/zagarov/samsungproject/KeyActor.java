package ru.zagarov.samsungproject;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;


public class KeyActor extends Actor {
    private Texture texture;
    private TextureRegion textureRegion;
    public Rectangle hitbox;

    public KeyActor(float startX, float startY) {
        setX(startX);
        setY(startY);

        texture = new Texture("key.png");
        textureRegion = new TextureRegion(texture);

        setWidth(texture.getWidth());
        setHeight(texture.getHeight());
        setOriginX(texture.getWidth() / 2f);
        setOriginY(texture.getHeight() / 2f);

        hitbox = new Rectangle(startX, startY, getWidth(), getHeight());

    }

    private void checkOverlap(){
        Stage stage = getStage();
        Array<Actor> actors = stage.getActors();
        for (Actor actor : actors) {
            if (actor instanceof CharacterActor) {
                CharacterActor characterActor = (CharacterActor) actor;
                if (characterActor.hitbox.overlaps(hitbox)) {
                    boolean beforeIsFlipped = characterActor.textureRegion.isFlipX();
                    characterActor.texture = new Texture("Character_key.png");
                    characterActor.textureRegion = new TextureRegion(characterActor.texture);
                    characterActor.textureRegion.flip(beforeIsFlipped, false);
                    characterActor.check_key_in_arm = true;
                    remove();
                }
            }
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        checkOverlap();
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
}
