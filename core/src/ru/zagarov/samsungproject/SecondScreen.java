package ru.zagarov.samsungproject;

import static ru.zagarov.samsungproject.MyGdxGame.SCREEN_WIDTH;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


public class SecondScreen extends BaseRoomScreen {

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private OrthographicCamera orthographicCamera;

    public SecondScreen(Game myGdxGame) {
        super(myGdxGame);

    }

    @Override
    public void show() {
        super.show();
        //Загрузка карты
        TmxMapLoader loader = new TmxMapLoader();
        TiledMap tiledMap = loader.load("tiledmap1/funGame.tmx");

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
        orthographicCamera.setToOrtho(false, 320, 180);
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
                    createBody(BodyDef.BodyType.StaticBody, x, y, width, height);
                }
            }
        }


        Texture leftTexture = new Texture("left.png");
        ImageButton leftButton = new ImageButton(new TextureRegionDrawable(leftTexture));
        leftButton.setPosition(0, 0);

        Texture rightTexture = new Texture("right.png");
        ImageButton rightButton = new ImageButton(new TextureRegionDrawable(rightTexture));
        rightButton.setPosition(4 * leftTexture.getWidth(), 0);

        Texture upTexture = new Texture("up.png");
        ImageButton upButton = new ImageButton(new TextureRegionDrawable(upTexture));
        upButton.setPosition(SCREEN_WIDTH - upTexture.getWidth(), 0);


        CharacterActor koalaActor = new CharacterActor(leftButton, rightButton, upButton, myGdxGame, this);
        stage.addActor(koalaActor);

    }


    @Override
    public void render(float delta) {
        super.render(delta);
        //renderer.setView(orthographicCamera);
        renderer.render();
    }
}
