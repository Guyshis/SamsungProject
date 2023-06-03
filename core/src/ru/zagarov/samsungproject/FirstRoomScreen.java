package ru.zagarov.samsungproject;

import static ru.zagarov.samsungproject.MyGdxGame.SCREEN_HEIGHT;
import static ru.zagarov.samsungproject.MyGdxGame.SCREEN_WIDTH;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class FirstRoomScreen extends BaseRoomScreen {

    public FirstRoomScreen(Game myGdxGame) {
        super(myGdxGame);
        this.myGdxGame = myGdxGame;
        stage.addActor(new BackgroundActor());
//        stage.addActor(new KeyActor(100, 127));
     //   stage.addActor(new DoorActor(1000, 127));


        Texture leftTexture = new Texture("left.png");
        ImageButton leftButton = new ImageButton(new TextureRegionDrawable(leftTexture));
        leftButton.setPosition(0, 0);

        Texture rightTexture = new Texture("right.png");
        ImageButton rightButton = new ImageButton(new TextureRegionDrawable(rightTexture));
        rightButton.setPosition(4 * leftTexture.getWidth(), 0);

        Texture upTexture = new Texture("up.png");
        ImageButton upButton = new ImageButton(new TextureRegionDrawable(upTexture));
        upButton.setPosition(SCREEN_WIDTH - upTexture.getWidth(), 0);

        Texture menuButtonTexture = new Texture("menuButtonTexture.png");
        ImageButton menuButton = new ImageButton(new TextureRegionDrawable(menuButtonTexture));
        menuButton.setPosition(SCREEN_WIDTH - menuButtonTexture.getWidth(), SCREEN_HEIGHT - menuButtonTexture.getHeight());



        stage.addActor(new CharacterActor(leftButton, rightButton, upButton, menuButton, myGdxGame, this));
        stage.addActor(leftButton);
        stage.addActor(rightButton);
        stage.addActor(upButton);
    }


}
