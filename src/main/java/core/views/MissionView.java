package core.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import core.*;
import core.objects.Mission;
import core.popup.MissionPopup;

import java.util.ArrayList;

public class MissionView {

    private final Stage stage;
    private final Skin skin;
    private final Table mainTable, currentTable, combineTable, sideTable, emptyTable;
    private final ScrollPane scrollPane;
    private final TextButton allButton, completedButton, uncompletedButton, lockedButton;
    private final NavigationBar navigationBar;
    private final ScreenChanger screenChanger;
    private final MissionPopup missionPopup;

    public MissionView(Stage stage) {
        this.stage = stage;
        this.skin = new Skin(Gdx.files.internal(GameData.SKIN));
        this.mainTable = new Table();
        this.currentTable = new Table();
        this.combineTable = new Table();
        this.sideTable = new Table();
        this.emptyTable = new Table();
        this.scrollPane = new ScrollPane(currentTable, skin);
        this.allButton = new TextButton(Language.get("button_all"), skin);
        this.completedButton = new TextButton(Language.get("button_completed"), skin);
        this.uncompletedButton = new TextButton(Language.get("button_uncompleted"), skin);
        this.lockedButton = new TextButton(Language.get("button_locked"), skin);
        this.navigationBar = new NavigationBar();
        this.screenChanger = new ScreenChanger();
        this.missionPopup = new MissionPopup(this, skin);
    }

    public Table getView(Stage stage, ArrayList<Mission> allMissionList, ArrayList<Mission> completedMissionList, ArrayList<Mission> uncompletedMissionList, ArrayList<Mission> lockedMissionList) {
        mainTable.setFillParent(true);
        mainTable.setBackground(Background.setBackground(Background.lightGray));

        currentTable.add(getMissions(stage, allMissionList)).minWidth(GameData.SCROLL_PANE_SIZE).maxWidth(GameData.SCROLL_PANE_SIZE).top();

        combineTable.add(getSide(stage, allMissionList, completedMissionList, uncompletedMissionList, lockedMissionList)).growY().fill();
        combineTable.add(scrollPane).top();

        mainTable.add(navigationBar.basicNavigationBar()).pad(0, 0, 50, 0).growX();
        mainTable.row();
        mainTable.add(combineTable).left();
        mainTable.row();
        mainTable.add(emptyTable).growY();

        return mainTable;
    }

    private ScrollPane getMissions(Stage stage, ArrayList<Mission> missionsList) {
        Table scrollPaneTable = new Table();
        ScrollPane skinsScrollPane = new ScrollPane(scrollPaneTable, skin);
        Image missionImage;

        scrollPaneTable.setBackground(Background.setBackground(Background.lightGray));

        skinsScrollPane.setFadeScrollBars(false);
        skinsScrollPane.setFlickScroll(false);
        skinsScrollPane.setScrollingDisabled(true, false);

        stage.setScrollFocus(skinsScrollPane);

        int index = 0;
        for (Mission mission : missionsList) {
            Table imageTable = new Table();
            Table productTable = new Table();
            Label missionNameLabel = new Label(mission.getName(), skin);
            Label missionPriceLabel = new Label(Language.get("label_price") + ": " + Money.format(mission.getPrice()), skin);
            TextButton playButton = new TextButton("", skin);
            Image notFoundImage;

            missionNameLabel.setAlignment(Align.center);
            missionPriceLabel.setAlignment(Align.center);

            int completed = mission.getCompleted();
            String missionType = completed == 0  ? Language.get("button_mission_locked") : completed == 1 ? Language.get("button_mission_completed") : Language.get("button_mission_uncompleted");
            playButton.setText(missionType);

            if (completed == 0) {
                playButton.setTouchable(Touchable.disabled);
            }

            try {
                missionImage = new Image(new Texture("pictures/missions/" + mission.getPicture()));
            } catch (Exception e) {
                notFoundImage = new Image(new Texture(new Pixmap(1, 1, Pixmap.Format.RGB565)));
                missionImage = notFoundImage;
            }
            missionImage.setAlign(Align.center);

            //Current Skin

            imageTable.add(missionImage).width(100).height(100);

            productTable.setBackground(Background.setBackground(Background.white));
            productTable.add(missionNameLabel).pad(10, 10, 10, 10).growX();
            productTable.row();
            productTable.add(imageTable).pad(0, 10, 10, 10).width(100).height(100);
            productTable.row();
            productTable.add(playButton).pad(0, 0, 0, 0).height(50).growX();

            if (index++ % 5 == 0) scrollPaneTable.row();
            scrollPaneTable.add(productTable).pad(0, 10, 10, 10).width(250).growY();

            playButton.addListener(new ChangeListener() {
                public void changed(ChangeEvent event, Actor actor) {
                    missionPopup.setPopup(mission);
                    stage.addActor(missionPopup);
                }
            });
        }

        int rowCount = 5;
        int listSize = missionsList.size();
        if (listSize == 1 || listSize == 2 || listSize == 3 || listSize == 4) for(int i = 0; i < rowCount - listSize; i++) scrollPaneTable.add(new Table()).pad(0, 10, 10, 10).width(250).growY().fill();

        if (listSize == 0) {
            scrollPaneTable.add(new Table()).pad(0, 10, 10, 10).width(250).growY().fill();
            scrollPaneTable.row();
            //scrollPaneTable.add(noNewMissionsLabel).growX().growY();
        }

        return skinsScrollPane;
    }

    private Table getSide(Stage stage, ArrayList<Mission> allMissionList, ArrayList<Mission> completedMissionList, ArrayList<Mission> uncompletedMissionList, ArrayList<Mission> lockedMissionList) {
        sideTable.setBackground(Background.setBackground(Background.lightGray));

        sideTable.add(allButton).pad(10, 10, 10, 10).width(200).height(50);
        sideTable.row();
        sideTable.add(completedButton).pad(0, 10, 10, 10).width(200).height(50);
        sideTable.row();
        sideTable.add(uncompletedButton).pad(0, 10, 10, 10).width(200).height(50);
        sideTable.row();
        sideTable.add(lockedButton).pad(0, 10, 10, 10).width(200).height(50);
        sideTable.row();
        sideTable.add(emptyTable).growY();

        allButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                currentTable.clear();
                currentTable.add(getMissions(stage, allMissionList));
            }
        });

        completedButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                currentTable.clear();
                currentTable.add(getMissions(stage, completedMissionList));
            }
        });

        uncompletedButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                currentTable.clear();
                currentTable.add(getMissions(stage, uncompletedMissionList));
            }
        });

        lockedButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                currentTable.clear();
                currentTable.add(getMissions(stage, lockedMissionList));
            }
        });
        return sideTable;
    }

    public void resize(int width, int height) {
        missionPopup.resize(width, height);
    }

    public void closePopup() {
        var actors = stage.getActors();
        actors.get(actors.size - 1).remove();
    }
}