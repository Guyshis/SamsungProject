package ru.zagarov.samsungproject;

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
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;


public class ThirdScreen implements Screen {

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



    @Override
    public void show() {
        Box2D.init();
        world = new World(new Vector2(0, -10F), true);
        debugRenderer = new Box2DDebugRenderer();
        body = createBody();
        body.setTransform(100, 100, 0);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
//        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT , camera);
//        viewport.apply(true);
        shapeRenderer = new ShapeRenderer();

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        world.step(delta, 6, 2);
        body.setAwake(true);
        debugRenderer.render(world, camera.combined);

    }

    @Override
    public void resize(int width, int height) {

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

    }


    private Body createBody() {
        BodyDef def = new BodyDef();
        //будет ли двигаться объект DynamicBody - может, STATIC BODY - НЕ МОЖЕТ
        def.type = BodyDef.BodyType.DynamicBody;
        //создаем физ тело в физ мире
        Body box = world.createBody(def);
        PolygonShape poly = new PolygonShape();

        // укеазываем половину ширины и проловину высоты
        poly.setAsBox(60/UNITS_PER_METER, 60/UNITS_PER_METER);
        box.createFixture(poly, 1);
        poly.dispose();
        return box;
    }

}
