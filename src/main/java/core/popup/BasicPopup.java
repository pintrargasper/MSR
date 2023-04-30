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
import core.Language;

public class BasicPopup extends Table implements Screen {

    private final Table mainTable, innerTable, emptyTable;
    private final Label titleLabel;
    private final TextButton closeButton;

    public BasicPopup(Stage stage, Skin skin) {
        this.mainTable = new Table();
        this.innerTable = new Table();
        this.emptyTable = new Table();
        this.titleLabel = new Label(Language.get("label_successfully_saved"), skin.get("medium-title", Label.LabelStyle.class));
        this.closeButton = new TextButton(Language.get("button_basic_close"), skin);

        mainTable.setBackground(Background.setBackground(Background.strongRed));
        innerTable.setBackground(Background.setBackground(Background.white));

        titleLabel.setAlignment(Align.center);

        innerTable.add(titleLabel).pad(10).growX();
        innerTable.row();
        innerTable.add(emptyTable).growY();
        innerTable.row();
        innerTable.add(closeButton).width(200).height(50);

        mainTable.add(innerTable).pad(20);
        this.add(mainTable);

        this.setPosition((Gdx.graphics.getWidth() - getWidth()) / 2, (Gdx.graphics.getHeight() - getHeight()) / 2);

        closeButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                closePopup(stage);
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

    public void setPopup(String error) {
        titleLabel.setText(error);
    }

    public void closePopup(Stage stage) {
        var actors = stage.getActors();
        actors.get(actors.size - 1).remove();
    }
}