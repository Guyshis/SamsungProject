package ru.zagarov.samsungproject;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class EndScreen extends BaseRoomScreen{
    private TiledMap map;


    private OrthogonalTiledMapRenderer renderer;

    private OrthographicCamera orthographicCamera;

    private SpriteBatch batch;
    private BitmapFont font;

    public EndScreen(Game myGdxGame) {
        super(myGdxGame);
        stage.addActor(new GameMenuScreen(myGdxGame));




    }

    @Override
    public void show() {
        super.show();
        batch = new SpriteBatch();
        font = new BitmapFont();



        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("FirstTimeWriting!.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;
        parameter.color = Color.BLACK;
        font = generator.generateFont(parameter);

    }


    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }


    @Override
    public void render(float delta) {
        super.render(delta);


        batch.setProjectionMatrix(stage.getCamera().combined);

        batch.begin();
        font.draw(batch, "Thank you ", 10, 150);
        font.draw(batch, "for gaming!", 10, 70);

        batch.end();



        //renderer.setView(orthographicCamera);


    }


}

