package ru.zagarov.samsungproject;

import static ru.zagarov.samsungproject.MyGdxGame.SCREEN_WIDTH;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class TwelfthScreen extends BaseRoomScreen{
    private TiledMap map;

    private World world;
    private OrthogonalTiledMapRenderer renderer;

    private OrthographicCamera orthographicCamera;

    private SpriteBatch batch;
    private BitmapFont font;

    public TwelfthScreen(Game myGdxGame) {
        super(myGdxGame);


    }

    @Override
    public void show() {
        super.show();
        batch = new SpriteBatch();
        font = new BitmapFont();

        //Загрузка карты
        TmxMapLoader loader = new TmxMapLoader();
        TiledMap tiledMap = loader.load("tiledmap1/SamsungMap2.tmx");

        //Прочитали характеристики карты
        MapProperties properties = tiledMap.getProperties();
        Integer countTilesByWidth = (Integer) properties.get("width");
        Integer countTilesByHeight = (Integer) properties.get("height");
        Integer tileWidth = (Integer) properties.get("tilewidth");
        Integer tileHeight = (Integer) properties.get("tileheight");

        int mapWidth = countTilesByWidth * tileWidth;
        int mapHeight = countTilesByHeight * tileHeight;


        //Настраиваем камеру
        orthographicCamera = (OrthographicCamera) stage.getCamera();
        orthographicCamera.setToOrtho(false, 320, 176);
        //orthographicCamera.position.set
        renderer = new OrthogonalTiledMapRenderer(tiledMap);
        renderer.setView(orthographicCamera);

        //Чтение карты
        // Просматриваем слои
        // 1. Фон
        // 2. Слой плиток (этот слой нам отображает OrthogonalTiledMapRenderer)
        // 3. Слой объектов

        MapLayers layers = tiledMap.getLayers();
        for (MapLayer layer : layers) {
            MapObjects objects = layer.getObjects();
            for (MapObject object : objects) {
                MapProperties objectProperties = object.getProperties();
                float x = (Float) objectProperties.get("x");
                float y = (Float) objectProperties.get("y");
                float height = (Float) objectProperties.get("height");
                float width = (Float) objectProperties.get("width");

                String objType = (String) objectProperties.get("type");
                if (objType.equals("solid")) {
                    createStaticBody(x, y, height, width);
                }
                if (objType.equals("jumping")) {
                    createJumpBody(x, y, height, width);
                }
//                if (objType.equals("key")){
//                    createBody(BodyDef.BodyType.DynamicBody, x, y, height, width);
//                }
            }
        }



        Texture leftTexture = new Texture("left.png");
        ImageButton leftButton = new ImageButton(new TextureRegionDrawable(leftTexture));
        leftButton.setPosition(0, 0);


        Texture rightTexture = new Texture("right.png");
        ImageButton rightButton = new ImageButton(new TextureRegionDrawable(rightTexture));
        rightButton.setPosition(1.5f * leftTexture.getWidth(), 0);


        Texture upTexture = new Texture("up.png");
        ImageButton upButton = new ImageButton(new TextureRegionDrawable(upTexture));
        upButton.setPosition(SCREEN_WIDTH - upTexture.getWidth() - upTexture.getWidth() / 3f, 0);


        CharacterActor characterActor = new CharacterActor(leftButton, rightButton, upButton, myGdxGame, this);
        stage.addActor(characterActor);
        stage.addActor(rightButton);
        stage.addActor(leftButton);
        stage.addActor(upButton);



        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Roboto-Black.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        parameter.color = Color.WHITE;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();

        font.dispose();

    }


    @Override
    public void hide() {
        batch.dispose();
        font.dispose();
    }


    @Override
    public void render(float delta) {
        super.render(delta);
        renderer.render();
        //renderer.setView(orthographicCamera);

        batch.begin();
        font.draw(batch, "1112", 100, 150);
        batch.end();
    }
}
