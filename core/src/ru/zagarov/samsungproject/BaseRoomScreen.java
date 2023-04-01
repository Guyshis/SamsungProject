package ru.zagarov.samsungproject;

import static ru.zagarov.samsungproject.MyGdxGame.SCREEN_HEIGHT;
import static ru.zagarov.samsungproject.MyGdxGame.SCREEN_WIDTH;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class BaseRoomScreen implements Screen {


    private static final float WORLD_WIDTH = 960;
    private static final float WORLD_HEIGHT = 544;

    //1px = 1 metr
    // меняем на 1px = 1metr
    private static final float UNITS_PER_METER = 16F;

    //физ мир
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private Body body;

    private OrthographicCamera camera;

    private FitViewport viewport;

    private ShapeRenderer shapeRenderer;
    public Stage stage;
    public Game myGdxGame;


    public BaseRoomScreen(Game myGdxGame) {
        this.myGdxGame = myGdxGame;
        viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

    }



    @Override
    public void show() {
        Box2D.init();
        world = new World(new Vector2(0, -10F), true);
        debugRenderer = new Box2DDebugRenderer();
//        body = createBody(BodyDef.BodyType.DynamicBody);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
//        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT , camera);
//        viewport.apply(true);
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 0, 0, 1);
        stage.act();
        stage.draw();
        world.step(delta, 6, 2);
        debugRenderer.render(world, camera.combined);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }



    public Body createBody(BodyDef.BodyType type, float startX, float startY, float width, float height) {
        BodyDef def = new BodyDef();
        //будет ли двигаться объект DynamicBody - может, STATIC BODY - НЕ МОЖЕТ
        def.type = type;
        //создаем физ тело в физ мире


        Body box = world.createBody(def);
        box.setTransform(startX, startY, 0);
        PolygonShape poly = new PolygonShape();

        // укеазываем половину ширины и проловину высоты
        poly.setAsBox(width, height);
        //poly.setAsBox(width/UNITS_PER_METER, height/UNITS_PER_METER);
        box.createFixture(poly, 1);
        poly.dispose();
        return box;
    }
}
