package game.daniel.dixia.Screens;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import game.daniel.dixia.Dixia;
import game.daniel.dixia.component.BonesComponent;
import game.daniel.dixia.component.ChipbraComponent;
import game.daniel.dixia.component.ChipbreComponent;
import game.daniel.dixia.component.ChipbriComponent;
import game.daniel.dixia.component.ChipbroComponent;
import game.daniel.dixia.component.ChipbruComponent;
import game.daniel.dixia.component.PlayerComponent;
import game.daniel.dixia.script.PlayerScript;
import game.daniel.dixia.stage.HUD;
import game.daniel.dixia.system.CameraSystem;
import game.daniel.dixia.system.PlayerAnimationSystem;
import games.rednblack.editor.renderer.SceneLoader;
import games.rednblack.editor.renderer.resources.AsyncResourceManager;
import games.rednblack.editor.renderer.resources.ResourceManagerLoader;
import games.rednblack.editor.renderer.utils.ComponentRetriever;
import games.rednblack.editor.renderer.utils.ItemWrapper;

public class Platform implements Screen {


    private AssetManager mAssetManager;
    private PlayerComponent playerComponent;

    private SceneLoader mSceneLoader;
    private AsyncResourceManager mAsyncResourceManager;

    private Viewport mViewport;
    private OrthographicCamera mCamera;

    private PooledEngine mEngine;

    private HUD mHUD;
    private ExtendViewport mHUDViewport;

public Platform ( AsyncResourceManager mAsyncResourceManager, SceneLoader mSceneLoader, Viewport mViewport, AssetManager mAssetManager, OrthographicCamera mCamera) {

    this.mSceneLoader = mSceneLoader;
    this.mAsyncResourceManager = mAsyncResourceManager;
    this.mViewport = mViewport;
    this.mAssetManager = mAssetManager;
    this.mCamera = mCamera;
}
    @Override
    public void show() {
        mAssetManager = new AssetManager();
        mAssetManager.setLoader(AsyncResourceManager.class, new ResourceManagerLoader(mAssetManager.getFileHandleResolver()));
        mAssetManager.load("project.dt", AsyncResourceManager.class);
        mAssetManager.load("skin/skin.json", Skin.class);

        mAssetManager.finishLoading();

        mAsyncResourceManager = mAssetManager.get("project.dt", AsyncResourceManager.class);
        mSceneLoader = new SceneLoader(mAsyncResourceManager);
        mEngine = mSceneLoader.getEngine();
        CameraSystem cameraSystem = new CameraSystem(7, 7, 3, 6);
        mEngine.addSystem(new PlayerAnimationSystem());
        mEngine.addSystem(cameraSystem);
        ComponentRetriever.addMapper(PlayerComponent.class);
        ComponentRetriever.addMapper(BonesComponent.class);
        ComponentRetriever.addMapper(ChipbraComponent.class);
        ComponentRetriever.addMapper(ChipbreComponent.class);
        ComponentRetriever.addMapper(ChipbriComponent.class);
        ComponentRetriever.addMapper(ChipbroComponent.class);
        ComponentRetriever.addMapper(ChipbruComponent.class);

        mCamera = new OrthographicCamera();
        mViewport = new ExtendViewport(15, 8, mCamera);

        mSceneLoader.loadScene("MainScene", mViewport);

        ItemWrapper root = new ItemWrapper(mSceneLoader.getRoot());

        ItemWrapper player = root.getChild("player");
        player.getChild("dog").getEntity().add(mEngine.createComponent(PlayerComponent.class));
        PlayerScript playerScript = new PlayerScript(mEngine);
        player.addScript(playerScript, mEngine);
        cameraSystem.setFocus(player.getEntity());



        mSceneLoader.addComponentByTagName("bones", BonesComponent.class);
        mSceneLoader.addComponentByTagName("chipbra", ChipbraComponent.class);
        mSceneLoader.addComponentByTagName("chipbre", ChipbreComponent.class);
        mSceneLoader.addComponentByTagName("chipbri", ChipbriComponent.class);
        mSceneLoader.addComponentByTagName("chipbro", ChipbroComponent.class);
        mSceneLoader.addComponentByTagName("chipbru", ChipbruComponent.class);

        mHUDViewport = new ExtendViewport(768, 576);
        mHUD = new HUD(mAssetManager.get("skin/skin.json"), mAsyncResourceManager.getTextureAtlas("main"), mHUDViewport, mSceneLoader.getBatch());
        mHUD.setPlayerScript(playerScript);

        InputAdapter webGlfullscreen = new InputAdapter() {
            @Override
            public boolean keyUp (int keycode) {
                if (keycode == Input.Keys.ENTER && Gdx.app.getType() == Application.ApplicationType.WebGL) {
                    if (!Gdx.graphics.isFullscreen()) Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayModes()[0]);
                }
                return true;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                if (Gdx.app.getType() == Application.ApplicationType.WebGL) {
                    if (!Gdx.graphics.isFullscreen()) Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayModes()[0]);
                }
                return super.touchUp(screenX, screenY, pointer, button);
            }
        };

        Gdx.input.setInputProcessor(new InputMultiplexer(webGlfullscreen, mHUD));


    }

    @Override
    public void render(float delta) {

        mCamera.update();

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mViewport.apply();
        mEngine.update(Gdx.graphics.getDeltaTime());

        mHUD.act(Gdx.graphics.getDeltaTime());
        mHUDViewport.apply();
        mHUD.draw();

    }

    @Override
    public void resize(int width, int height) {
        mViewport.update(width, height);
        mHUDViewport.update(width, height, true);

        if (width != 0 && height != 0)
            mSceneLoader.resize(width, height);
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
        mAssetManager.dispose();
    }
}
