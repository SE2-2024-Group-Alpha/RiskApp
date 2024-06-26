package se2.alpha.riskapp.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import se2.alpha.riskapp.events.TerritoryAttackEvent;
import se2.alpha.riskapp.events.UpdateFreeTroopsEvent;
import se2.alpha.riskapp.logic.EventBus;

import java.util.ArrayList;
import java.util.List;

public class TroopCardList implements Disposable {
    private Stage stage;
    private OrthographicCamera uiCamera;
    private int screenHeight;
    private int screenWidth;
    private List<Label> infoLabels;
    private Skin skin;
    Label troopLabel;
    private int freeTroops;

    public TroopCardList(int screenHeight, int screenWidth, Skin skin) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.infoLabels = new ArrayList<>();
        this.skin = skin;

        uiCamera = new OrthographicCamera();
        uiCamera.setToOrtho(false, screenWidth, screenHeight);
        stage = new Stage(new ScreenViewport(uiCamera));

        EventBus.registerCallback(UpdateFreeTroopsEvent.class, e -> {
            UpdateFreeTroopsEvent event = (UpdateFreeTroopsEvent) e;
            freeTroops = event.getFreeTroops();
            troopLabel.setText("Troops: " + freeTroops);
        });

    }

//    TODO implement troop and card change
    public void initializeInfoLabels() {
        int troops = 0;
        int cards = 10;

        Label.LabelStyle style = new Label.LabelStyle();
        style.font = skin.getFont("default-font");
        style.font.getData().setScale(3f);
        style.background = skin.newDrawable("white", Color.DARK_GRAY);

        troopLabel = new Label("Troops: " + troops, style);
        troopLabel.setSize(280, 100); // Set the desired size
        troopLabel.setPosition(0, screenHeight - (100 + 250)); // Adjust position vertically
        infoLabels.add(troopLabel);
        stage.addActor(troopLabel);

        Label cardLabel = new Label("Cards: " + cards, style);
        cardLabel.setSize(280, 100); // Set the desired size
        cardLabel.setPosition(0, screenHeight - (200 + 250)); // Adjust position vertically
        infoLabels.add(cardLabel);
        stage.addActor(cardLabel);
    }

    public void draw() {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
