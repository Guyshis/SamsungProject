package ru.zagarov.samsungproject;

import static ru.zagarov.samsungproject.MyGdxGame.SCREEN_HEIGHT;
import static ru.zagarov.samsungproject.MyGdxGame.SCREEN_WIDTH;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


public class ThirdScreen extends BaseRoomScreen {

    private MouseJoint mouseJoint = null;

    private TiledMap map;

    private World world;
    private OrthogonalTiledMapRenderer renderer;

    private OrthographicCamera orthographicCamera;

    private SpriteBatch batch;
    private BitmapFont font;

    private KeyActor keyActor;
    private Vector2 dragOffset;
    private boolean isDragging;


    public ThirdScreen(Game myGdxGame) {
        super(myGdxGame);

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(new MyInputProcessor());

        Gdx.input.setInputProcessor(multiplexer);
        dragOffset = new Vector2();
        stage.addActor(new GameMenuScreen(myGdxGame));
        stage.addActor(new FloorActor(myGdxGame));
    }

    @Override
    public void show() {

        super.show();
        batch = new SpriteBatch();
        font = new BitmapFont();
        keyActor = new KeyActor(225, 150, this, BodyDef.BodyType.StaticBody, myGdxGame);

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
            }
        }


        Texture leftTexture = new Texture("left3.png");
        ImageButton leftButton = new ImageButton(new TextureRegionDrawable(leftTexture));
        leftButton.setPosition(0, 0);


        Texture rightTexture = new Texture("right3.png");
        ImageButton rightButton = new ImageButton(new TextureRegionDrawable(rightTexture));
        rightButton.setPosition(leftTexture.getWidth() *2.5f, 0);


        Texture upTexture = new Texture("up3.png");
        ImageButton upButton = new ImageButton(new TextureRegionDrawable(upTexture));
        upButton.setPosition(SCREEN_WIDTH - upTexture.getWidth(), 0);

        Texture menuButtonTexture = new Texture("menuButtonTexture.png");
        ImageButton menuButton = new ImageButton(new TextureRegionDrawable(menuButtonTexture));
        menuButton.setPosition(SCREEN_WIDTH - menuButtonTexture.getWidth(), SCREEN_HEIGHT - menuButtonTexture.getHeight());


        CharacterActor characterActor = new CharacterActor(leftButton, rightButton, upButton, menuButton, myGdxGame, this);

   //     stage.addActor(menuButton);

        stage.addActor(new DoorActor(275, 32, this));
        stage.addActor(new TeleportActor(318, 32, this));
        stage.addActor(keyActor);
        stage.addActor(characterActor);
        stage.addActor(rightButton);
        stage.addActor(leftButton);
        stage.addActor(upButton);



        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("FirstTimeWriting!.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        parameter.color = Color.BLACK;
        font = generator.generateFont(parameter);



    }


    private class MyInputProcessor implements InputProcessor {
        @Override
        public boolean keyDown(int keycode) {
            return false;
        }

        @Override
        public boolean keyUp(int keycode) {
            return false;
        }

        @Override
        public boolean keyTyped(char character) {
            return false;
        }

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
          //  Vector3 unproject = camera.project(vector3);
            System.out.println(stage.screenToStageCoordinates(new Vector2(screenX, screenY)));
            if (keyActor.hitbox.contains(stage.screenToStageCoordinates(new Vector2(screenX, screenY)))){
                isDragging = true;
                keyActor.body.setAwake(false);
                return true;
            }
            // Проверяем, что пользователь нажал на объект, чтобы начать перемещение
//                System.out.println(screenX / (ras_x / 320));
//                System.out.println((Gdx.graphics.getHeight() - screenY) / (ras_y / 176));
                // Вычисляем смещение между позицией курсора и позицией объекта
                //dragOffset.set(screenX, Gdx.graphics.getHeight() - screenY).sub(keyActor.getPosition());

            return false;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            // Проверяем, что объект перемещается и обновляем его позицию в соответствии с положением курсора
            if (isDragging){
                keyActor.body.setTransform(stage.screenToStageCoordinates(new Vector2(screenX, screenY)), 0f);
                keyActor.body.setAwake(false);
            }
            return true;
        }

        @Override
        public boolean mouseMoved(int screenX, int screenY) {
            return false;

        }

        @Override
        public boolean scrolled(float amountX, float amountY) {
            return false;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            // Останавливаем перемещение объекта
            keyActor.body.setAwake(true);
            isDragging = false;
            return true;
        }

        // Другие методы интерфейса InputProcessor...
    }


    @Override
    public void hide() {
        batch.dispose();
        font.dispose();
    }


    @Override
    public void render(float delta) {
        super.render(delta);

        batch.setProjectionMatrix(stage.getCamera().combined);
        batch.begin();
        font.draw(batch, "Take key)", 30, 150);
        batch.end();
        renderer.render();

        //spriteBatch.begin();
        //spriteBatch.draw(keyActor.getTexture(), keyActor.getPosition().x, keyActor.getPosition().y, keyActor.getWidth(), keyActor.getHeight());
        //spriteBatch.end();
    }

}
