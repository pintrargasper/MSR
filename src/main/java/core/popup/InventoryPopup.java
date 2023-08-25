package core.popup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import core.Background;
import core.GameData;
import core.Language;
import core.Utils;
import core.database.SkinConnection;
import core.objects.CustomSkin;
import core.views.InventoryView;

import java.util.ArrayList;

public class InventoryPopup extends Table implements Screen {

    private final InventoryView inventoryView;
    private final Skin skin;
    private final Table mainTable, innerTable, emptyTable, combineTable;
    private final Label titleLabel, errorLabel;
    private final TextButton saveButton, closeButton;
    private CustomSkin currentWeapon;
    private BasicPopup basicPopup;
    private String player, bullet, cursor, aim, weapon;

    public InventoryPopup(InventoryView inventoryView, Stage stage, Utils utils, Skin skin) {
        this.inventoryView = inventoryView;
        this.skin = skin;
        this.mainTable = new Table();
        this.innerTable = new Table();
        this.emptyTable = new Table();
        this.combineTable = new Table();
        this.titleLabel = new Label(Language.get("label_save_active_skin"), skin.get("medium-title", Label.LabelStyle.class));
        this.errorLabel = new Label("", skin);
        this.saveButton = new TextButton(Language.get("button_save"), skin);
        this.closeButton = new TextButton(Language.get("button_inventory_close"), skin);
        this.basicPopup = new BasicPopup(stage, skin);

        mainTable.setBackground(Background.setBackground(Background.strongRed));
        innerTable.setBackground(Background.setBackground(Background.white));

        titleLabel.setAlignment(Align.center);
        errorLabel.setAlignment(Align.center);

        titleLabel.setWrap(true);

        combineTable.add(saveButton).pad(5, 10, 5, 10).height(50).width(200);
        combineTable.add(closeButton).pad(5, 10, 5, 10).height(50).width(200);

        this.setPosition((Gdx.graphics.getWidth() - getWidth()) / 2, (Gdx.graphics.getHeight() - getHeight()) / 2);

        saveButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String message;
                if (SkinConnection.saveActiveSkins(player, bullet, cursor, aim, weapon).matches("1")) {
                    GameData.CURRENT_PLAYER_SKIN = player;
                    GameData.CURRENT_BULLET_SKIN = bullet;
                    GameData.CURRENT_CURSOR_SKIN = cursor;
                    GameData.CURRENT_AIM_SKIN = aim;
                    GameData.CURRENT_WEAPON_SKIN = weapon;
                    GameData.CURRENT_WEAPON_SPEED = currentWeapon.getSpeed();
                    GameData.CURRENT_WEAPON_EFFECT = currentWeapon.getSoundEffect();
                    GameData.CURRENT_WEAPON_WIDTH = currentWeapon.getWidth();
                    GameData.CURRENT_WEAPON_HEIGHT = currentWeapon.getHeight();

                    message = Language.get("string_skins_saved");
                } else {
                    message = Language.get("string_something_wrong");
                }

                inventoryView.closePopup();
                basicPopup.setPopup(message);
                stage.addActor(basicPopup);
            }
        });

        closeButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                utils.disableAll(stage, false);
                inventoryView.closePopup();
            }
        });
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {

    }

    @Override
    public void resize(int width, int height) {
        setPosition(width / 2f, height / 2f);
        basicPopup.resize(width, height);
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

    public void setPopup(String player, String bullet, String cursor, String aim, String weapon, ArrayList<CustomSkin> activeSkinsList) {
        this.player = player;
        this.bullet = bullet;
        this.cursor = cursor;
        this.aim = aim;
        this.weapon = weapon;
        this.currentWeapon = activeSkinsList.stream().filter(e -> e.getPicture().contains("weapon")).findFirst().get();

        this.clear();
        mainTable.clear();
        innerTable.clear();

        innerTable.add(titleLabel).pad(10, 10, 5, 10).growX();
        innerTable.row();
        for (CustomSkin skin : activeSkinsList) {
            innerTable.add(new Label(Language.get("label_chosen_skin") + ": " + skin.getName(), this.skin)).pad(0, 10, 5, 10).growX();
            innerTable.row();
        }
        innerTable.row();
        innerTable.add(emptyTable).growY();
        innerTable.row();
        innerTable.add(combineTable).growX().growY();

        mainTable.add(innerTable).pad(20);
        this.add(mainTable);
    }
}