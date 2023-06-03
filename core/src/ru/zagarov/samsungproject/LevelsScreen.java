package ru.zagarov.samsungproject;

import static ru.zagarov.samsungproject.MyGdxGame.SCREEN_HEIGHT;
import static ru.zagarov.samsungproject.MyGdxGame.SCREEN_WIDTH;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;


public class LevelsScreen implements Screen {

    private Stage stage;
    public Game myGdxGame;
    public FitViewport viewport;
    private SpriteBatch spriteBatch; // спрайтбатч для отрисовки текста на экране
    private BitmapFont font1; // шрифт для отображения текста
    private Button button;
    public static boolean SecondLevelCheck = false;
    public static boolean ThirdLevelCheck = false;
    public static boolean SeventhLevelCheck = false;
    public static boolean FirstLevelCheck = true;
    public static boolean FourthLevelCheck = false;
    public static boolean FifthLevelCheck = false;
    public static boolean SixthLevelCheck = false;
    public static boolean EighthLevelCheck = false;
    public static boolean NinethLevelCheck = false;
    public static boolean TenthLevelCheck = false;



    public LevelsScreen(Game myGdxGame) {
        this.myGdxGame = myGdxGame;
        viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);


        stage.addActor(new LevelsGroundActor());

        int n = 10;

        String[] arr = new String[n];

        for (int i = 1; i <= n; i++) {
            arr[i-1] = Integer.toString(i);
        }


        Texture upTexture = new Texture("buttonUp.png");
        TextureRegionDrawable upDrawable = new TextureRegionDrawable(new TextureRegion(upTexture));

        Texture downTexture = new Texture("buttonDown.png");
        TextureRegionDrawable downDrawable = new TextureRegionDrawable(new TextureRegion(downTexture));


        int buttonX = 50;
        int buttonY = 150;
        final int buttonWidth = 25;
        final int buttonHeight = 25;
        for (int i = 0; i < n; i++) {
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Roboto-Regular.ttf"));
            FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            parameter.size = 20;
            parameter.color = Color.BLACK;
            font1 = generator.generateFont(parameter);
            TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle(upDrawable, downDrawable, null, font1);
            TextButton textButton = new TextButton(arr[i], textButtonStyle);
            textButton.setX(buttonX);
            textButton.setY(buttonY);
            textButton.setHeight(buttonHeight);
            textButton.setWidth(buttonWidth);
            final int level = i;
            textButton.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    openLevel(level);
                }
            });


            if (textButton.isPressed()) {
                System.out.println(arr[i]);
            }
            buttonX += 75;
            if (buttonX == 350) {
                buttonX = 50;
                buttonY -= 50;
            }
            stage.addActor(textButton);

        }

    }

    private void openLevel(int level) {
        switch (level){
            case 0:
                System.out.println("1");
                FirstLevelCheck = true;
                myGdxGame.setScreen(new FirstScreen(myGdxGame));

                break;
            case 1:
                System.out.println("2");
                SecondLevelCheck = true;
                myGdxGame.setScreen(new SecondScreen(myGdxGame));


                break;
            case 2:
                System.out.println("3");
                ThirdLevelCheck = true;
                myGdxGame.setScreen(new ThirdScreen(myGdxGame));

                break;
            case 3:
                System.out.println("4");
                FourthLevelCheck = true;
                myGdxGame.setScreen(new FourthScreen(myGdxGame));

                break;
            case 4:
                System.out.println("5");
                FifthLevelCheck = true;
                myGdxGame.setScreen(new FifthScreen(myGdxGame));

                break;
            case 5:
                System.out.println("6");
                SixthLevelCheck = true;
                myGdxGame.setScreen(new SixthScreen(myGdxGame));


                break;
            case 6:
                System.out.println("7");
                SeventhLevelCheck = true;
                myGdxGame.setScreen(new SeventhScreen(myGdxGame));

                break;
            case 7:
                System.out.println("8");
                EighthLevelCheck = true;
                myGdxGame.setScreen(new EighthScreen(myGdxGame));

                break;
            case 8:
                System.out.println("9");
                NinethLevelCheck = true;
                myGdxGame.setScreen(new NinthScreen(myGdxGame));

                break;
            case 9:
                System.out.println("10");
                TenthLevelCheck = true;
                myGdxGame.setScreen(new TenthScreen(myGdxGame));

                break;

        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        stage.dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}