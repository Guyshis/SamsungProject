package ru.zagarov.samsungproject;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
//import ru.zagarov.fungame.TiledScreen1.TiledScreen;

public class MenuGroundActor extends Actor {

    private Texture texture;
    public ImageButton startButton;
    public boolean checkStartMenu = false;
    private Game game;

    public MenuGroundActor(ImageButton startButton, Game game) {
        texture = new Texture("MenuGround.png");
        this.startButton = startButton;
        this.game = game;
    }

    @Override
    public void act(float delta) {
        if(startButton.isPressed()){
            game.setScreen(new SecondScreen(game));
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, 0, 0, MyGdxGame.SCREEN_WIDTH, MyGdxGame.SCREEN_HEIGHT);
    }
}

