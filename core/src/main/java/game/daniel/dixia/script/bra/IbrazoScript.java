package game.daniel.dixia.script.bra;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.viewport.Viewport;

import game.daniel.dixia.Dixia;
import game.daniel.dixia.Screens.Platform;
import game.daniel.dixia.Screens.bra.Unirbra;
import game.daniel.dixia.component.BonesComponent;
import game.daniel.dixia.component.ChipbraComponent;
import game.daniel.dixia.component.ChipbreComponent;
import game.daniel.dixia.component.ChipbriComponent;
import game.daniel.dixia.component.ChipbroComponent;
import game.daniel.dixia.component.ChipbruComponent;
import game.daniel.dixia.component.bra.Palabrabra1Component;
import game.daniel.dixia.component.bra.laberinto.IbrazoComponent;
import games.rednblack.editor.renderer.SceneLoader;
import games.rednblack.editor.renderer.components.DimensionsComponent;
import games.rednblack.editor.renderer.components.MainItemComponent;
import games.rednblack.editor.renderer.components.TransformComponent;
import games.rednblack.editor.renderer.components.physics.PhysicsBodyComponent;
import games.rednblack.editor.renderer.physics.PhysicsContact;
import games.rednblack.editor.renderer.resources.AsyncResourceManager;
import games.rednblack.editor.renderer.scripts.BasicScript;
import games.rednblack.editor.renderer.utils.ComponentRetriever;
import games.rednblack.editor.renderer.utils.ItemWrapper;
import game.daniel.dixia.component.PlayerComponent;

public class IbrazoScript extends BasicScript implements InputProcessor {


    public static final int LEFT = 1;
    public static final int RIGHT = -1;
    public static final int JUMP = 0;


    private Dixia dixia;


    private Entity animEntity;
    private PhysicsBodyComponent mPhysicsBodyComponent;

    private final Vector2 impulse = new Vector2(0, 0);
    private final Vector2 speed = new Vector2(0, 0);

    private final PooledEngine mEngine;

    public IbrazoScript(PooledEngine engine) {
        mEngine = engine;
    }

    @Override
    public void init(Entity item) {
        super.init(item);

        ItemWrapper itemWrapper = new ItemWrapper(item);
        animEntity = itemWrapper.getChild("ibrazo").getEntity();
        //Sprite sprite = animEntity;
        mPhysicsBodyComponent = ComponentRetriever.get(item, PhysicsBodyComponent.class);
    }

    @Override
    public void act(float delta) {


    }


    public void movePlayer(int direction) {
        Body body = mPhysicsBodyComponent.body;

        speed.set(body.getLinearVelocity());


        switch (direction) {
            case LEFT:
                impulse.set(-3, speed.y);
                break;
            case RIGHT:
                impulse.set(3, speed.y);
                break;
            case JUMP:
                TransformComponent transformComponent = ComponentRetriever.get(entity, TransformComponent.class);
                impulse.set(speed.x, transformComponent.y < 15 ? 3 : speed.y);
                break;

        }

        body.applyLinearImpulse(impulse.sub(speed), body.getWorldCenter(), true);
    }

    public IbrazoComponent getPlayerComponent() {
        return ComponentRetriever.get(animEntity, IbrazoComponent.class);
    }

    @Override
    public void dispose() {

    }
/*
    @Override
    public void beginContact(Entity contactEntity, Fixture contactFixture, Fixture ownFixture, Contact contact) {

        MainItemComponent mainItemComponent = ComponentRetriever.get(contactEntity, MainItemComponent.class);

        IbrazoComponent playerComponent = ComponentRetriever.get(animEntity, IbrazoComponent.class);

        if (mainItemComponent.tags.contains("lineas"))
            playerComponent.touchedPlatforms++;

        Palabrabra1Component palabrabra = ComponentRetriever.get(contactEntity, Palabrabra1Component.class);
        if (palabrabra != null) {
            playerComponent.touchedWords += palabrabra.value;
            mEngine.removeEntity(contactEntity);
        }
    }

    @Override
    public void endContact(Entity contactEntity, Fixture contactFixture, Fixture ownFixture, Contact contact) {
        MainItemComponent mainItemComponent = ComponentRetriever.get(contactEntity, MainItemComponent.class);

        IbrazoComponent playerComponent = ComponentRetriever.get(animEntity, IbrazoComponent.class);

        if (mainItemComponent.tags.contains("lineas"))
            playerComponent.touchedPlatforms--;
    }

    @Override
    public void preSolve(Entity contactEntity, Fixture contactFixture, Fixture ownFixture, Contact contact) {
        TransformComponent transformComponent = ComponentRetriever.get(this.entity, TransformComponent.class);

        TransformComponent colliderTransform = ComponentRetriever.get(contactEntity, TransformComponent.class);
        DimensionsComponent colliderDimension = ComponentRetriever.get(contactEntity, DimensionsComponent.class);

        if (transformComponent.y < colliderTransform.y + colliderDimension.height) {
            contact.setFriction(0);
        } else {
            contact.setFriction(1);
        }
    }

    @Override
    public void postSolve(Entity contactEntity, Fixture contactFixture, Fixture ownFixture, Contact contact) {

    }
*/
    @Override
    public boolean keyDown(int keycode) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            movePlayer(LEFT);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            movePlayer(RIGHT);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            movePlayer(JUMP);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            movePlayer(JUMP);
        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
       if(pointer<20){
           System.out.println("hola");

       }
       else{
           System.out.println("adios");

       }


      return true;
        }






    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
