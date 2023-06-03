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
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class BaseRoomScreen implements Screen {


    private static final float WORLD_WIDTH = 320;
    private static final float WORLD_HEIGHT = 176;

    //1px = 1 metr
    // меняем на 1px = 1metr
    public static final float UNITS_PER_METER = 1f;

    //физ мир
    public World world;
    private Box2DDebugRenderer debugRenderer;
    private Body body;

    protected OrthographicCamera camera;

    private FitViewport viewport;

    private ShapeRenderer shapeRenderer;
    public Stage stage;
    public Game myGdxGame;
    public float gravity = -50f;
    public float gravityX = 0f;

    private boolean isMoving = false;



    public BaseRoomScreen(Game myGdxGame) {
        this.myGdxGame = myGdxGame;
        viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

    }



    @Override
    public void show() {
        float timeStep = 1/300f; // установка времени обновления в 1/300 секунды
        int velocityIterations = 6; // количество итераций для вычисления скоростей объектов
        int positionIterations = 2; // количество итераций для вычисления позиций объектов

        Box2D.init();

        world = new World(new Vector2(gravityX, gravity), true);
        world.setContactListener(new MyContactListener());
        world.step(timeStep, velocityIterations, positionIterations); // обновление симуляции
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
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act();
        stage.draw();
        float timeStep = 1 / 60f; // шаг времени
        int velocityIterations = 6; // количество итераций для вычисления скоростей
        int positionIterations = 2; // количество итераций для вычисления позиций
        if(LevelsScreen.SixthLevelCheck){
            for (int i = 0; i < 7; i++) {
                world.step(timeStep, velocityIterations, positionIterations);
            }
        } else{
            world.step(timeStep, velocityIterations, positionIterations);
            world.step(timeStep, velocityIterations, positionIterations);
        }



        //debugRenderer.render(world, camera.combined);


    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.update();
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



    public Body createBody(BodyDef.BodyType type, float startX, float startY, float width, float height, boolean rotation,String UserData) {


        BodyDef def = new BodyDef();
        //будет ли двигаться объект DynamicBody - может, STATIC BODY - НЕ МОЖЕТ
        def.type = type;
        //создаем физ тело в физ мире

        Body body = world.createBody(def);
        body.setTransform(startX, startY, 0);
//        body.setFixedRotation(rotation);
        def.gravityScale = 0;

        PolygonShape poly = new PolygonShape();

        // укеазываем половину ширины и проловину высоты
//        poly.setAsBox(width, height);
        poly.setAsBox(width * UNITS_PER_METER / 2f, height * UNITS_PER_METER / 2f);
        body.createFixture(poly, 0);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = poly;
        fixtureDef.density = 0.0f; // плотность объекта
        fixtureDef.friction = 0.1f; // коэффициент трения объекта


        body.createFixture(fixtureDef);


        body.setLinearDamping(1.0f);
        body.setAngularDamping(1.0f);
        Fixture fixture = body.createFixture(fixtureDef);



        if (UserData == "actor"){

            fixture.setUserData("Actor"); // установите пользовательские данные для фикстуры
        }

        if (UserData == "key"){
            fixtureDef.isSensor = true;
            fixture.setUserData("objects");

        }


        poly.dispose();
        return body;
    }

    public Body createDoorBody(float x, float y, float height, float width) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((x + width / 2) / UNITS_PER_METER, (y + height / 2)/ UNITS_PER_METER);
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / UNITS_PER_METER / 2, height / UNITS_PER_METER / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 0f;
        body.createFixture(fixtureDef);

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData("door"); // установите пользовательские данные для фикстуры

        shape.dispose();
        return body;
    }
    public Body createBodyLeg(BodyDef.BodyType type, float startX, float startY, float width, float height) {
        BodyDef def = new BodyDef();
        //будет ли двигаться объект DynamicBody - может, STATIC BODY - НЕ МОЖЕТ
        def.type = type;
        //создаем физ тело в физ мире

        Body body = world.createBody(def);
        body.setTransform(startX, startY, 0);
        body.setFixedRotation(true);


        PolygonShape poly = new PolygonShape();

        // укеазываем половину ширины и проловину высоты
//        poly.setAsBox(width, height);
        poly.setAsBox(width * UNITS_PER_METER / 2f, height * UNITS_PER_METER / 2f);
        body.createFixture(poly, 0);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = poly;
        fixtureDef.density = 0f; // плотность объекта
        fixtureDef.friction = 0.0f; // коэффициент трения объекта
        body.createFixture(fixtureDef);


        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData("JumpActor"); // установите пользовательские данные для фикстуры

        body.setLinearDamping(1.0f);
        body.setAngularDamping(1.0f);


        poly.dispose();
        return body;


    }


    public Body createStaticBody(float x, float y, float height, float width) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((x + width / 2) / UNITS_PER_METER, (y + height / 2)/ UNITS_PER_METER);
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / UNITS_PER_METER / 2, height / UNITS_PER_METER / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 0f;
        body.createFixture(fixtureDef);

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData("objects"); // установите пользовательские данные для фикстуры

        shape.dispose();
        return body;
    }


    public void createJumpBody(float x, float y, float height, float width) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((x + width / 2) / UNITS_PER_METER, (y + height / 2)/ UNITS_PER_METER);
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / UNITS_PER_METER / 2, height / UNITS_PER_METER / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.0f;
        fixtureDef.restitution = 0f;
        body.createFixture(fixtureDef);

        shape.dispose();
    }



}
