package core.popup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import core.Background;
import core.GameData;
import core.Language;
import core.database.MissionConnection;
import core.database.SkinConnection;
import core.objects.CustomSkin;
import core.objects.Mission;
import core.views.NavigationBar;
import core.views.ShopView;

public class ShopPopup extends Table implements Screen {

    private final Stage stage;
    private final BasicPopup basicPopup;
    private final Table mainTable, innerTable, emptyTable, combineTable;
    private final Label titleLabel, descriptionLabel, priceLabel;
    private final TextButton buyButton, closeButton;
    private TextButton openButton;
    private Mission mission;
    private CustomSkin customSkin;
    private String type;

    public ShopPopup(BasicPopup basicPopup, Stage stage, Skin skin) {
        this.stage = stage;
        this.basicPopup = basicPopup;
        this.mainTable = new Table();
        this.innerTable = new Table();
        this.emptyTable = new Table();
        this.combineTable = new Table();
        this.titleLabel = new Label("", skin.get("medium-title", Label.LabelStyle.class));
        this.descriptionLabel = new Label(Language.get("label_buy_item"), skin);
        this.priceLabel = new Label("", skin);
        this.buyButton = new TextButton(Language.get("button_shop_buy"), skin);
        this.closeButton = new TextButton(Language.get("button_shop_close"), skin);

        mainTable.setBackground(Background.setBackground(Background.strongRed));
        innerTable.setBackground(Background.setBackground(Background.white));

        titleLabel.setAlignment(Align.center);
        descriptionLabel.setAlignment(Align.center);
        priceLabel.setAlignment(Align.center);

        titleLabel.setWrap(true);
        descriptionLabel.setWrap(true);
        priceLabel.setWrap(true);

        combineTable.add(buyButton).pad(5, 10, 5, 10).height(50).width(200);
        combineTable.add(closeButton).pad(5, 10, 5, 10).height(50).width(200);

        innerTable.add(titleLabel).pad(10, 10, 5, 10).growX();
        innerTable.row();
        innerTable.add(descriptionLabel).pad(0, 10, 5, 10).growX();
        innerTable.row();
        innerTable.add(priceLabel).pad(0, 10, 5, 10).growX();
        innerTable.add(emptyTable).growY();
        innerTable.row();
        innerTable.add(combineTable).growX().growY();

        mainTable.add(innerTable).pad(20);
        this.add(mainTable);

        this.setPosition((Gdx.graphics.getWidth() - getWidth()) / 2, (Gdx.graphics.getHeight() - getHeight()) / 2);

        buyButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                var account = GameData.PLAYER_ACCOUNT;
                if (type.matches("Mission")) {
                    var result = MissionConnection.buyMission(mission.getId());
                    if (result.matches("1")) {
                        ShopView.boughtMissions.add(mission);
                        mission.setCompleted(2);
                        account.setMoney(account.getMoney() - mission.getPrice());
                        closePopup();
                        openBasicPopup(Language.get("string_mission_bought_successfully"));
                        return;
                    } else if (result.matches("0")) {
                        closePopup();
                        openBasicPopup(Language.get("string_not_enough_money"));
                        return;
                    } else {
                        closePopup();
                        openBasicPopup(Language.get("string_shop_something_wrong"));
                        return;
                    }
                } else {
                    var result = SkinConnection.buySkin(customSkin.getId());
                    if (result.matches("1")) {
                        ShopView.boughtSkins.add(customSkin);
                        GameData.OWNED_SKINS_LIST.add(customSkin);
                        GameData.SKINS_LIST.removeAll(ShopView.boughtSkins);
                        account.setMoney(account.getMoney() - customSkin.getPrice());
                        closePopup();
                        openBasicPopup(Language.get("string_skin_bought_successfully"));
                    }  else if (result.matches("0")) {
                        closePopup();
                        openBasicPopup(Language.get("string_not_enough_money"));
                        return;
                    } else {
                        closePopup();
                        openBasicPopup(Language.get("string_shop_something_wrong"));
                        return;
                    }
                }
                NavigationBar.updateMoney();
                openButton.setText(Language.get("button_buy_purchased"));
                closePopup();
            }
        });

        closeButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                closePopup();
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

    public void setPopup(String title, String price, String type, Mission mission, TextButton openButton) {
        this.titleLabel.setText(title);
        this.priceLabel.setText(price);
        this.type = type;
        this.mission = mission;
        this.openButton = openButton;
    }

    public void setPopup(String title, String price, String type, CustomSkin customSkin, TextButton openButton) {
        this.titleLabel.setText(title);
        this.priceLabel.setText(price);
        this.type = type;
        this.customSkin = customSkin;
        this.openButton = openButton;
    }

    private void openBasicPopup(String message) {
        basicPopup.setPopup(message);
        stage.addActor(basicPopup);
    }

    private void closePopup() {
        var actors = stage.getActors();
        actors.get(actors.size - 1).remove();
    }
}