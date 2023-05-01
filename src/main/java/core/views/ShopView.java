package core.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import core.*;
import core.objects.CustomSkin;
import core.objects.Mission;
import core.popup.BasicPopup;
import core.popup.ShopPopup;

import java.util.ArrayList;

public class ShopView {

    private final Stage stage;
    private final Skin skin;
    private final Table mainTable, currentTable, combineTable, sideTable, emptyTable;
    private final ScrollPane scrollPane;
    private final Label noNewMissionsLabel;
    private Label noNewSkinsLabel;
    private final TextButton missionButton, playerButton, bulletButton, cursorButton, aimButton, weaponButton;
    private final NavigationBar navigationBar;
    public static final ArrayList<Mission> boughtMissions = new ArrayList<>();
    public static final ArrayList<CustomSkin> boughtSkins = new ArrayList<>();
    private final Utils utils;
    private final BasicPopup basicPopup;
    private final ShopPopup shopPopup;

    public ShopView(Stage stage) {
        this.stage = stage;
        this.skin = new Skin(Gdx.files.internal(GameData.SKIN));
        this.mainTable = new Table();
        this.currentTable = new Table();
        this.combineTable = new Table();
        this.sideTable = new Table();
        this.emptyTable = new Table();
        this.scrollPane = new ScrollPane(currentTable, skin);
        this.noNewMissionsLabel = new Label(Language.get("label_no_mission"), skin.get("big-title", Label.LabelStyle.class));
        this.missionButton = new TextButton(Language.get("button_shop_missions"), skin);
        this.playerButton = new TextButton(Language.get("button_player"), skin);
        this.bulletButton = new TextButton(Language.get("button_bullet"), skin);
        this.cursorButton = new TextButton(Language.get("button_cursor"), skin);
        this.aimButton = new TextButton(Language.get("button_aim"), skin);
        this.weaponButton = new TextButton(Language.get("button_weapon"), skin);
        this.navigationBar = new NavigationBar();
        this.utils = new Utils();
        this.basicPopup = new BasicPopup(stage, skin);
        this.shopPopup = new ShopPopup(basicPopup, stage, utils, skin);
    }

    public Table getView(Stage stage, ArrayList<Mission> missionsList, ArrayList<CustomSkin> playerList, ArrayList<CustomSkin> bulletList, ArrayList<CustomSkin> cursorList, ArrayList<CustomSkin> aimList, ArrayList<CustomSkin> weaponList) {
        mainTable.setFillParent(true);
        mainTable.setBackground(Background.setBackground(Background.lightGray));

        currentTable.add(getMissions(stage, missionsList)).minWidth(GameData.SCROLL_PANE_SIZE).maxWidth(GameData.SCROLL_PANE_SIZE).top();

        combineTable.add(getSide(stage, missionsList, playerList, bulletList, cursorList, aimList, weaponList)).growY().fill();
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
            TextButton buyButton = new TextButton(Language.get("button_buy"), skin);
            Image notFoundImage;

            missionNameLabel.setAlignment(Align.center);
            missionPriceLabel.setAlignment(Align.center);

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
            productTable.add(missionPriceLabel).pad(10, 10, 10, 10).growX();
            productTable.row();
            productTable.add(buyButton).pad(0, 0, 0, 0).height(50).growX();

            if (index++ % 5 == 0) scrollPaneTable.row();
            scrollPaneTable.add(productTable).pad(0, 10, 10, 10).width(250).growY();

            buyButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if (!buyButton.getText().toString().equals(Language.get("button_buy_purchased"))) {
                        utils.disableAll(stage, true);
                        shopPopup.setPopup(mission.getName(), Money.format(mission.getPrice()), "Mission", mission, buyButton);
                        stage.addActor(shopPopup);
                    }
                }
            });
        }

        int rowCount = 5;
        int listSize = missionsList.size();
        if (listSize == 1 || listSize == 2 || listSize == 3 || listSize == 4) for(int i = 0; i < rowCount - listSize; i++) scrollPaneTable.add(new Table()).pad(0, 10, 10, 10).width(250).growY().fill();

        if (listSize == 0) {
            scrollPaneTable.add(new Table()).pad(0, 10, 10, 10).width(250).growY().fill();
            scrollPaneTable.row();
            scrollPaneTable.add(noNewMissionsLabel).growX().growY();
        }

        return skinsScrollPane;
    }

    private ScrollPane getSkins(Stage stage, ArrayList<CustomSkin> customSkinsList, String type) {
        Table scrollPaneTable = new Table();
        ScrollPane skinsScrollPane = new ScrollPane(scrollPaneTable, skin);
        Image skinsImage;

        scrollPaneTable.setBackground(Background.setBackground(Background.lightGray));

        skinsScrollPane.setFadeScrollBars(false);
        skinsScrollPane.setFlickScroll(false);
        skinsScrollPane.setScrollingDisabled(true, false);

        stage.setScrollFocus(skinsScrollPane);

        int index = 0;
        for (CustomSkin customSkin : customSkinsList) {
            Table imageTable = new Table();
            Table productTable = new Table();
            Label skinNameLabel = new Label(customSkin.getName(), skin);
            Label skinPriceLabel = new Label(Language.get("label_price") + ": " + Money.format(customSkin.getPrice()), skin);
            TextButton buyButton = new TextButton(Language.get("button_buy"), skin);
            Image notFoundImage;

            skinNameLabel.setAlignment(Align.center);
            skinPriceLabel.setAlignment(Align.center);

            try {
                var skinName = customSkin.getPicture().split("-")[0];
                var skinNumber = customSkin.getPicture().split("-")[1];
                skinsImage = new Image(new Texture("pictures/skins/" + skinName + "/" + skinNumber + "/" + customSkin.getPicture() + "-stand.png"));
            } catch (Exception e) {
                notFoundImage = new Image(new Texture(new Pixmap(1, 1, Pixmap.Format.RGB565)));
                skinsImage = notFoundImage;
            }
            skinsImage.setAlign(Align.center);

            var width = type.matches("weapon") ? 200 : 100;
            var height = type.matches("bullet") ? 25 : 100;
            imageTable.add(skinsImage).width(width).height(height);

            productTable.setBackground(Background.setBackground(Background.white));
            productTable.add(skinNameLabel).pad(10, 10, 10, 10).growX();
            productTable.row();
            productTable.add(imageTable).pad(0, 10, 10, 10).width(100).height(100);
            productTable.row();
            productTable.add(skinPriceLabel).pad(10, 10, 10, 10).growX();
            productTable.row();
            productTable.add(buyButton).pad(0, 0, 0, 0).height(50).growX();

            if (index++ % 5 == 0) scrollPaneTable.row();
            scrollPaneTable.add(productTable).pad(0, 10, 10, 10).width(250).growY();

            buyButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    if (!buyButton.getText().toString().equals(Language.get("button_buy_purchased"))) {
                        utils.disableAll(stage, true);
                        shopPopup.setPopup(customSkin.getName(), Money.format(customSkin.getPrice()), "CustomSkin", customSkin, buyButton);
                        stage.addActor(shopPopup);
                    }
                }
            });
        }

        int rowCount = 5;
        int listSize = customSkinsList.size();
        if (listSize == 1 || listSize == 2 || listSize == 3 || listSize == 4) for(int i = 0; i < rowCount - listSize; i++) scrollPaneTable.add(new Table()).pad(0, 10, 10, 10).width(250).growY().fill();

        if (listSize == 0) {
            var text = type.matches("player") ? "label_no_player_skins" : (type.matches("bullet") ? "label_no_bullet_skis" : (type.matches("cursor") ? "label_no_cursor_skins" : (type.matches("aim") ? "label_no_aim_skins" : "label_no_weapon_skins")));
            noNewSkinsLabel = new Label(Language.get(text), skin.get("big-title", Label.LabelStyle.class));
            scrollPaneTable.add(new Table()).pad(0, 10, 10, 10).width(250).growY().fill();
            scrollPaneTable.row();
            scrollPaneTable.add(noNewSkinsLabel).growX().growY();
        }

        return skinsScrollPane;
    }

    private Table getSide(Stage stage, ArrayList<Mission> missionList, ArrayList<CustomSkin> playerList, ArrayList<CustomSkin> bulletList, ArrayList<CustomSkin> cursorList, ArrayList<CustomSkin> aimList, ArrayList<CustomSkin> weaponList) {
        sideTable.setBackground(Background.setBackground(Background.lightGray));

        sideTable.add(missionButton).pad(10, 10, 10, 10).width(200).height(50);
        sideTable.row();
        sideTable.add(playerButton).pad(0, 10, 10, 10).width(200).height(50);
        sideTable.row();
        sideTable.add(bulletButton).pad(0, 10, 10, 10).width(200).height(50);
        sideTable.row();
        sideTable.add(cursorButton).pad(0, 10, 10, 10).width(200).height(50);
        sideTable.row();
        sideTable.add(aimButton).pad(0, 10, 10, 10).width(200).height(50);
        sideTable.row();
        sideTable.add(weaponButton).pad(0, 10, 10, 10).width(200).height(50);
        sideTable.row();
        sideTable.add(emptyTable).growY();

        missionButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                currentTable.clear();
                missionList.removeAll(boughtMissions);
                currentTable.add(getMissions(stage, missionList));
            }
        });

        playerButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                currentTable.clear();
                playerList.removeAll(boughtSkins);
                currentTable.add(getSkins(stage, playerList, "player"));
            }
        });

        bulletButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                currentTable.clear();
                bulletList.removeAll(boughtSkins);
                currentTable.add(getSkins(stage, bulletList, "bullet"));
            }
        });

        cursorButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                currentTable.clear();
                cursorList.removeAll(boughtSkins);
                currentTable.add(getSkins(stage, cursorList, "cursor"));
            }
        });

        aimButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                currentTable.clear();
                aimList.removeAll(boughtSkins);
                currentTable.add(getSkins(stage, aimList, "aim"));
            }
        });

        weaponButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                currentTable.clear();
                weaponList.removeAll(boughtSkins);
                currentTable.add(getSkins(stage, weaponList, "weapon"));
            }
        });
        return sideTable;
    }

    public void resize(int width, int height) {
        shopPopup.resize(width, height);
        basicPopup.resize(width, height);
    }
}