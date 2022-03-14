package game.daniel.dixia.Screens.bro;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import game.daniel.dixia.component.bri.Palabrabri1Component;
import game.daniel.dixia.component.bri.unirver.Palabrabri2Component;
import game.daniel.dixia.component.bri.unirver.Palabrabri3Component;
import game.daniel.dixia.component.bri.unirver.Palabrabri4Component;
import game.daniel.dixia.component.bri.unirver.Palabrabri5Component;
import game.daniel.dixia.component.bri.unirver.Pbrinco1Component;
import game.daniel.dixia.component.bro.Palabrabro1Component;
import game.daniel.dixia.component.bro.unirver.Palabrabro2Component;
import game.daniel.dixia.component.bro.unirver.Palabrabro3Component;
import game.daniel.dixia.component.bro.unirver.Palabrabro4Component;
import game.daniel.dixia.component.bro.unirver.Palabrabro5Component;
import game.daniel.dixia.component.bro.unirver.Pbrocha1Component;
import game.daniel.dixia.script.bri.PbrincoScript;
import game.daniel.dixia.script.bro.PbrochaScript;
import game.daniel.dixia.stage.Bri.HUDunirverbri;
import game.daniel.dixia.stage.bro.HUDunirverbro;
import game.daniel.dixia.system.CameraSystem;
import games.rednblack.editor.renderer.SceneLoader;
import games.rednblack.editor.renderer.resources.AsyncResourceManager;
import games.rednblack.editor.renderer.resources.ResourceManagerLoader;
import games.rednblack.editor.renderer.utils.ComponentRetriever;
import games.rednblack.editor.renderer.utils.ItemWrapper;

public class Unirverbro implements Screen {
    private AssetManager mAssetManager;

    private SceneLoader mSceneLoader;
    private AsyncResourceManager mAsyncResourceManager;

    private Viewport mViewport;
    private OrthographicCamera mCamera;

    private PooledEngine mEngine;

    private HUDunirverbro mHUD;
    private ExtendViewport mHUDViewport;

    public Unirverbro(AsyncResourceManager mAsyncResourceManager, SceneLoader mSceneLoader, Viewport mViewport, AssetManager mAssetManager, OrthographicCamera mCamera) {
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
        mEngine.addSystem(cameraSystem);
        ComponentRetriever.addMapper(Pbrocha1Component.class);

        mCamera = new OrthographicCamera();
        mViewport = new ExtendViewport(15, 8, mCamera);

        mSceneLoader.loadScene("unirverbro", mViewport);

        ItemWrapper root = new ItemWrapper(mSceneLoader.getRoot());

        ItemWrapper player = root.getChild("pbrocha1");
        player.getChild("brocha2").getEntity().add(mEngine.createComponent(Pbrocha1Component.class));
        PbrochaScript pbrochaScript = new PbrochaScript(mEngine);
        player.addScript(pbrochaScript, mEngine);
        cameraSystem.setFocus(player.getEntity());


        mSceneLoader.addComponentByTagName("palabrabro1", Palabrabro1Component.class);
        mSceneLoader.addComponentByTagName("palabrabro2", Palabrabro2Component.class);
        mSceneLoader.addComponentByTagName("palabrabro3", Palabrabro3Component.class);
        mSceneLoader.addComponentByTagName("palabrabro4", Palabrabro4Component.class);
        mSceneLoader.addComponentByTagName("palabrabro5", Palabrabro5Component.class);

        mHUDViewport = new ExtendViewport(768, 576);
        mHUD = new HUDunirverbro(mAssetManager.get("skin/skin.json"), mAsyncResourceManager.getTextureAtlas("main"), mHUDViewport, mSceneLoader.getBatch());
        mHUD.setPbrochaScript(pbrochaScript);

       /* InputAdapter webGlfullscreen = new InputAdapter() {
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

        Gdx.input.setInputProcessor(new InputMultiplexer(webGlfullscreen, mHUD));*/
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
        mViewport.update(width,height);
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
