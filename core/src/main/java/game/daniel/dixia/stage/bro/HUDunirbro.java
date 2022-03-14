package game.daniel.dixia.stage.bro;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;

import game.daniel.dixia.script.bro.Ipbrocha1Script;

public class HUDunirbro extends Stage {

    private Label mBonesLabel;

    private Ipbrocha1Script mPlayerScript;

    private boolean leftClicked = false;
    private boolean rightClicked = false;

    private int bones = -1;

    public HUDunirbro(Skin skin, TextureAtlas atlas, Viewport viewport, Batch batch) {
        super(viewport, batch);

        Table root = new Table();
        root.pad(10, 20, 10, 20);
        root.setFillParent(true);

        Table BoneCounter = new Table();
        Image bone = new Image(atlas.findRegion("Bonecounter"));
        BoneCounter.add(bone);

        mBonesLabel = new Label("Bones", skin);
        BoneCounter.add(mBonesLabel);

        root.add(BoneCounter).expand().left().top().colspan(3);
        root.row();

        ImageButton leftButton = new ImageButton(skin, "left");
        leftButton.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                leftClicked = true;
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                leftClicked = false;
            }
        });
        root.add(leftButton).left().bottom();

        ImageButton rightButton = new ImageButton(skin, "right");
        rightButton.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rightClicked = true;
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                rightClicked = false;
            }
        });
        root.add(rightButton).left().bottom().padLeft(20);

        ImageButton upButton = new ImageButton(skin, "jump");
        upButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                mPlayerScript.movePlayer(Ipbrocha1Script.JUMP);
            }
        });
        root.add(upButton).expand().right().bottom();

        addActor(root);
    }

    public void setIpbrocha1Script(Ipbrocha1Script ipbrocha1Script) {
        mPlayerScript = ipbrocha1Script;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (leftClicked)
            mPlayerScript.movePlayer(Ipbrocha1Script.LEFT);
        if (rightClicked)
            mPlayerScript.movePlayer(Ipbrocha1Script.RIGHT);

        if (bones != mPlayerScript.getPlayerComponent().touchedWords) {
            bones = mPlayerScript.getPlayerComponent().touchedWords;
        }
    }
}

