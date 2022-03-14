package game.daniel.dixia.script.bru;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;

import game.daniel.dixia.Dixia;
import game.daniel.dixia.component.bro.laberinto.IbrochaComponent;
import game.daniel.dixia.component.bru.Palabrabru1Component;
import game.daniel.dixia.component.bru.laberinto.IbrujaComponent;
import games.rednblack.editor.renderer.components.DimensionsComponent;
import games.rednblack.editor.renderer.components.MainItemComponent;
import games.rednblack.editor.renderer.components.TransformComponent;
import games.rednblack.editor.renderer.components.physics.PhysicsBodyComponent;
import games.rednblack.editor.renderer.physics.PhysicsContact;
import games.rednblack.editor.renderer.scripts.BasicScript;
import games.rednblack.editor.renderer.utils.ComponentRetriever;
import games.rednblack.editor.renderer.utils.ItemWrapper;

public class IbrujaScript extends BasicScript implements PhysicsContact {


    public static final int LEFT = 1;
    public static final int RIGHT = -1;
    public static final int JUMP = 0;


    private Dixia dixia;


    private Entity animEntity;
    private PhysicsBodyComponent mPhysicsBodyComponent;

    private final Vector2 impulse = new Vector2(0, 0);
    private final Vector2 speed = new Vector2(0, 0);

    private final PooledEngine mEngine;

    public IbrujaScript(PooledEngine engine) {
        mEngine = engine;
    }

    @Override
    public void init(Entity item) {
        super.init(item);

        ItemWrapper itemWrapper = new ItemWrapper(item);
        animEntity = itemWrapper.getChild("ibruja1").getEntity();

        mPhysicsBodyComponent = ComponentRetriever.get(item, PhysicsBodyComponent.class);
    }

    @Override
    public void act(float delta) {
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

    public IbrujaComponent getPlayerComponent() {
        return ComponentRetriever.get(animEntity, IbrujaComponent.class);
    }

    @Override
    public void dispose() {

    }

    @Override
    public void beginContact(Entity contactEntity, Fixture contactFixture, Fixture ownFixture, Contact contact) {

        MainItemComponent mainItemComponent = ComponentRetriever.get(contactEntity, MainItemComponent.class);

        IbrujaComponent playerComponent = ComponentRetriever.get(animEntity, IbrujaComponent.class);

        if (mainItemComponent.tags.contains("lineas"))
            playerComponent.touchedPlatforms++;

        Palabrabru1Component palabrabru = ComponentRetriever.get(contactEntity, Palabrabru1Component.class);
        if (palabrabru != null) {
            playerComponent.touchedWords += palabrabru.value;
            mEngine.removeEntity(contactEntity);
        }
    }

    @Override
    public void endContact(Entity contactEntity, Fixture contactFixture, Fixture ownFixture, Contact contact) {
        MainItemComponent mainItemComponent = ComponentRetriever.get(contactEntity, MainItemComponent.class);

        IbrujaComponent playerComponent = ComponentRetriever.get(animEntity, IbrujaComponent.class);

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
}

