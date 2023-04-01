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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.utils.Array;


public class CharacterActor extends Actor {

    private static final float JUMP_SPEED = 10.0f;

    private ImageButton leftButton;

    private ImageButton rightButton;

    private ImageButton jumpButton;

    public TextureRegion textureRegion;

    public Texture texture;

    public Rectangle hitbox;




    protected boolean isJumping = false;
    protected boolean isFalling = false;


    private int baseX = 0;
    private int baseY = 16;
    private boolean shouldFlip = false;

    private int speed = 5;

    public boolean check_key_in_arm = false;


    private Game game;


    private float jumpForce = 10.0f;
    private boolean canJump = true;
    private boolean checkJump = true;
    private float jumpHeight = 120;

    private Body body;


    public CharacterActor(ImageButton leftButton, ImageButton rightButton, ImageButton jumpButton, Game game, BaseRoomScreen baseRoomScreen) {
        texture = new Texture("Character.png");

        body = baseRoomScreen.createBody(BodyDef.BodyType.DynamicBody, 200, 200, texture.getWidth(), texture.getHeight());

        setX(baseX);
        setY(baseY);

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

        System.out.println(body.getPosition());
        Vector2 currentPosition = body.getPosition();
        setX(currentPosition.x);
        setY(currentPosition.y);

        if (rightButton.isPressed() || Gdx.input.isKeyPressed(Input.Keys.D)) {
            moveBy(speed, 0);
            if (shouldFlip) {
                textureRegion.flip(true, false);
                shouldFlip = false;
            }

        }
        if (leftButton.isPressed() || Gdx.input.isKeyPressed(Input.Keys.A)) {
            moveBy(-speed, 0);
            if (!shouldFlip) {
                textureRegion.flip(true, false);
                shouldFlip = true;
            }
        }

        if (jumpButton.isPressed() || Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            checkJump = true;
        }
        if ((jumpButton.isPressed() || Gdx.input.isKeyPressed(Input.Keys.SPACE)) && !isJumping && !isFalling) {
            isJumping = true;
        }
        if (isJumping) {
            moveBy(0, 5);
            if (getY() > baseY + jumpHeight) {
                isJumping = false;
                isFalling = true;
            }
        }
        if (isFalling) {

            moveBy(0, -5);
            if (getY() <= baseY) {
                isFalling = false;
                setY(baseY);
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


    private void checkOverlap() {
        Stage stage = getStage();
        Array<Actor> actors = stage.getActors();
        for (Actor actor : actors) {
            if (actor instanceof DoorActor) {
                DoorActor door = (DoorActor) actor;
                if (door.hitbox.overlaps(hitbox)) {
                    if (check_key_in_arm) {
                        door.remove();
                        texture = new Texture("Character.png");
                        textureRegion = new TextureRegion(texture);
                        check_key_in_arm = false;
                        game.setScreen(new MenuScreen(game));
                    } else {
                        setX(1000 - getWidth());
                    }
                }
            }
        }
    }
}