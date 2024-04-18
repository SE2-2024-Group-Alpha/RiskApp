package se2.alpha.riskapp.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.utils.Disposable;
import se2.alpha.riskapp.GestureHandler;

public class GameMap implements Disposable {
    SpriteBatch batch;
    OrthographicCamera camera;
    float waterSpeedX = 0.1f, waterSpeedY = 0.05f, waterOffsetX = 0, waterOffsetY = 0;
    int screenHeight;
    int screenWidth;
    Texture background, waterTexture;
    float screenScaleFactor;
    GestureHandler gestureHandler;

    public GameMap(int screenHeight, int screenWidth) {
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera(screenWidth, screenHeight);
        this.camera.setToOrtho(false);

        background = new Texture("map.png");
        waterTexture = new Texture("woah.png");
        waterTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        screenScaleFactor = (float) Gdx.graphics.getHeight() / background.getHeight();
        gestureHandler = new GestureHandler(camera, background, screenScaleFactor);
        Gdx.input.setInputProcessor(new GestureDetector(gestureHandler));
    }

    public void draw() {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        waterOffsetX += waterSpeedX * Gdx.graphics.getDeltaTime();
        waterOffsetY += waterSpeedY * Gdx.graphics.getDeltaTime();
        float bgWidthScaled = background.getWidth() * screenScaleFactor;
        float bgHeightScaled = Gdx.graphics.getHeight();
        batch.begin();
        batch.draw(waterTexture, 0, 0, bgWidthScaled, bgHeightScaled, waterOffsetX, waterOffsetY, (float) ((bgWidthScaled / waterTexture.getWidth() + waterOffsetX)*1.5), (float) ((bgHeightScaled / waterTexture.getHeight() + waterOffsetY)*1.5));
        batch.draw(background, 0, 0, background.getWidth() * screenScaleFactor, Gdx.graphics.getHeight());
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
    }
}