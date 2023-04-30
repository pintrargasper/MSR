package core.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import core.Background;
import core.GameData;
import core.Language;
import core.objects.CustomSkin;
import core.popup.InventoryPopup;

import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class InventoryView {

    private final Stage stage;
    private final Skin skin;
    private final Table mainTable, currentTable, combineTable, sideTable, emptyTable;
    private final ScrollPane scrollPane;
    private final TextButton playerButton, bulletButton, cursorButton, aimButton, weaponButton, saveButton;
    private final NavigationBar navigationBar;
    private InventoryPopup inventoryPopup;
    private String player, bullet, cursor, aim, weapon;
    private final ArrayList<TextButton> playerButtonsList;

    public InventoryView(Stage stage) {
        this.stage = stage;
        this.skin = new Skin(Gdx.files.internal(GameData.SKIN));
        this.mainTable = new Table();
        this.currentTable = new Table();
        this.combineTable = new Table();
        this.sideTable = new Table();
        this.emptyTable = new Table();
        this.scrollPane = new ScrollPane(currentTable, skin);
        this.playerButton = new TextButton(Language.get("button_player"), skin);
        this.bulletButton = new TextButton(Language.get("button_bullet"), skin);
        this.cursorButton = new TextButton(Language.get("button_cursor"), skin);
        this.aimButton = new TextButton(Language.get("button_aim"), skin);
        this.weaponButton = new TextButton(Language.get("button_weapon"), skin);
        this.saveButton = new TextButton(Language.get("button_save_data"), skin);
        this.inventoryPopup = new InventoryPopup(this, stage, skin);
        this.navigationBar = new NavigationBar();
        this.player = GameData.CURRENT_PLAYER_SKIN;
        this.bullet = GameData.CURRENT_BULLET_SKIN;
        this.cursor =  GameData.CURRENT_CURSOR_SKIN;
        this.aim =  GameData.CURRENT_AIM_SKIN;
        this.weapon =  GameData.CURRENT_WEAPON_SKIN;
        this.playerButtonsList = new ArrayList<>();
    }

    public Table getView(Stage stage, ArrayList<CustomSkin> playerList, ArrayList<CustomSkin> bulletList, ArrayList<CustomSkin> cursorList, ArrayList<CustomSkin> aimList, ArrayList<CustomSkin> weaponList) {
        mainTable.setFillParent(true);
        mainTable.setBackground(Background.setBackground(Background.lightGray));

        currentTable.add(getSkins(stage, playerList, "player")).minWidth(GameData.SCROLL_PANE_SIZE).maxWidth(GameData.SCROLL_PANE_SIZE).top();

        combineTable.add(getSide(stage, playerList, bulletList, cursorList, aimList, weaponList)).growY().fill();
        combineTable.add(scrollPane).top();

        mainTable.add(navigationBar.basicNavigationBar()).pad(0, 0, 50, 0).growX();
        mainTable.row();
        mainTable.add(combineTable).left();
        mainTable.row();
        mainTable.add(emptyTable).growY();

        return mainTable;
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
            TextButton customSkinButton = new TextButton(Language.get("button_buy"), skin);
            Image notFoundImage;

            skinNameLabel.setAlignment(Align.center);

            var currentSkin = type.matches("player") ? player : type.matches("bullet") ? bullet : type.matches("cursor") ? cursor : type.matches("aim") ? aim : weapon;
            var text = currentSkin.matches(customSkin.getPicture().split("-")[1]) ? Language.get("button_use_in_use") : Language.get("button_use_use");
            customSkinButton.setText(text);

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
            productTable.add(customSkinButton).pad(0, 0, 0, 0).height(50).growX();

            if (index++ % 5 == 0) scrollPaneTable.row();
            scrollPaneTable.add(productTable).pad(0, 10, 10, 10).width(250).growY();

            customSkinButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    var currentSkin = customSkin.getPicture().split("-")[1];

                    switch (type) {
                        case "player" -> {
                            player = currentSkin;
                        }
                        case "bullet" -> {
                            bullet = currentSkin;
                        }
                        case "cursor" -> {
                            cursor = currentSkin;
                        }
                        case "aim" -> {
                            aim = currentSkin;
                        }
                        case "weapon" -> {
                            weapon = currentSkin;
                        }
                    }

                    for (TextButton textButton : playerButtonsList) {
                        if (textButton.getText().toString().matches(Language.get("button_use_in_use"))) {
                            textButton.setText(Language.get("button_use_use"));
                        }
                    }
                    customSkinButton.setText(Language.get("button_use_in_use"));
                }
            });
            playerButtonsList.add(customSkinButton);
        }

        int rowCount = 5;
        int listSize = customSkinsList.size();
        if (listSize == 1 || listSize == 2 || listSize == 3 || listSize == 4) for(int i = 0; i < rowCount - listSize; i++) scrollPaneTable.add(new Table()).pad(0, 10, 10, 10).width(250).growY().fill();

        return skinsScrollPane;
    }

    private Table getSide(Stage stage, ArrayList<CustomSkin> playerList, ArrayList<CustomSkin> bulletList, ArrayList<CustomSkin> cursorList, ArrayList<CustomSkin> aimList, ArrayList<CustomSkin> weaponList) {
        sideTable.setBackground(Background.setBackground(Background.lightGray));

        sideTable.add(playerButton).pad(10, 10, 10, 10).width(200).height(50);
        sideTable.row();
        sideTable.add(bulletButton).pad(0, 10, 10, 10).width(200).height(50);
        sideTable.row();
        sideTable.add(cursorButton).pad(0, 10, 10, 10).width(200).height(50);
        sideTable.row();
        sideTable.add(aimButton).pad(0, 10, 10, 10).width(200).height(50);
        sideTable.row();
        sideTable.add(weaponButton).pad(0, 10, 10, 10).width(200).height(50);
        sideTable.row();
        sideTable.add(saveButton).pad(0, 10, 10, 10).width(200).height(50);
        sideTable.row();
        sideTable.add(emptyTable).growY();

        playerButton.addListener(new ChangeListener() {
            public void changed(ChangeEvent event, Actor actor) {
                currentTable.clear();
                currentTable.add(getSkins(stage, playerList, "player"));
            }
        });

        bulletButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                currentTable.clear();
                currentTable.add(getSkins(stage, bulletList, "bullet"));
            }
        });

        cursorButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                currentTable.clear();
                currentTable.add(getSkins(stage, cursorList, "cursor"));
            }
        });

        aimButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                currentTable.clear();
                currentTable.add(getSkins(stage, aimList, "aim"));
            }
        });

        weaponButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                currentTable.clear();
                currentTable.add(getSkins(stage, weaponList, "weapon"));
            }
        });

        saveButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                var activeSkinsList = new ArrayList<CustomSkin>();
                Pattern pattern = Pattern.compile("(player-" + player + "|bullet-" + bullet + "|cursor-" + cursor + "|aim-" + aim + "|weapon-" + weapon + ")");
                for (CustomSkin skin1 : GameData.OWNED_SKINS_LIST) {
                    if (pattern.matcher(skin1.getPicture()).matches()) {
                        activeSkinsList.add(skin1);
                    }
                }
                inventoryPopup.setPopup(player, bullet, cursor, aim, weapon, activeSkinsList);
                stage.addActor(inventoryPopup);
            }
        });

        return sideTable;
    }

    public void resize(int width, int height) {
        inventoryPopup.resize(width, height);
    }

    public void closePopup() {
        var actors = stage.getActors();
        actors.get(actors.size - 1).remove();
    }
}