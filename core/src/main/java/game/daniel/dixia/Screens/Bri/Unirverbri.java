package game.daniel.dixia.Screens.Bri;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import game.daniel.dixia.component.bre.Palabrabre1Component;
import game.daniel.dixia.component.bre.unirver.Palabrabre2Component;
import game.daniel.dixia.component.bre.unirver.Palabrabre3Component;
import game.daniel.dixia.component.bre.unirver.Palabrabre4Component;
import game.daniel.dixia.component.bre.unirver.Palabrabre5Component;
import game.daniel.dixia.component.bre.unirver.Pbreva1Component;
import game.daniel.dixia.component.bri.Palabrabri1Component;
import game.daniel.dixia.component.bri.unirver.Palabrabri2Component;
import game.daniel.dixia.component.bri.unirver.Palabrabri3Component;
import game.daniel.dixia.component.bri.unirver.Palabrabri4Component;
import game.daniel.dixia.component.bri.unirver.Palabrabri5Component;
import game.daniel.dixia.component.bri.unirver.Pbrinco1Component;
import game.daniel.dixia.script.bre.PbrevaScript;
import game.daniel.dixia.script.bri.PbrincoScript;
import game.daniel.dixia.stage.Bri.HUDunirverbri;
import game.daniel.dixia.stage.bre.HUDunirverbre;
import game.daniel.dixia.system.CameraSystem;
import games.rednblack.editor.renderer.SceneLoader;
import games.rednblack.editor.renderer.resources.AsyncResourceManager;
import games.rednblack.editor.renderer.resources.ResourceManagerLoader;
import games.rednblack.editor.renderer.utils.ComponentRetriever;
import games.rednblack.editor.renderer.utils.ItemWrapper;

public class Unirverbri implements Screen {
    private AssetManager mAssetManager;

    private SceneLoader mSceneLoader;
    private AsyncResourceManager mAsyncResourceManager;

    private Viewport mViewport;
    private OrthographicCamera mCamera;

    private PooledEngine mEngine;

    private HUDunirverbri mHUD;
    private ExtendViewport mHUDViewport;

    public Unirverbri(AsyncResourceManager mAsyncResourceManager, SceneLoader mSceneLoader, Viewport mViewport, AssetManager mAssetManager, OrthographicCamera mCamera) {
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
        ComponentRetriever.addMapper(Pbrinco1Component.class);

        mCamera = new OrthographicCamera();
        mViewport = new ExtendViewport(15, 8, mCamera);

        mSceneLoader.loadScene("unirverbri", mViewport);

        ItemWrapper root = new ItemWrapper(mSceneLoader.getRoot());

        ItemWrapper player = root.getChild("pbrinco1");
        player.getChild("brinco2").getEntity().add(mEngine.createComponent(Pbrinco1Component.class));
        PbrincoScript pbrincoScript = new PbrincoScript(mEngine);
        player.addScript(pbrincoScript, mEngine);
        cameraSystem.setFocus(player.getEntity());


        mSceneLoader.addComponentByTagName("palabrabri1", Palabrabri1Component.class);
        mSceneLoader.addComponentByTagName("palabrabri2", Palabrabri2Component.class);
        mSceneLoader.addComponentByTagName("palabrabri3", Palabrabri3Component.class);
        mSceneLoader.addComponentByTagName("palabrabri4", Palabrabri4Component.class);
        mSceneLoader.addComponentByTagName("palabrabri5", Palabrabri5Component.class);

        mHUDViewport = new ExtendViewport(768, 576);
        mHUD = new HUDunirverbri(mAssetManager.get("skin/skin.json"), mAsyncResourceManager.getTextureAtlas("main"), mHUDViewport, mSceneLoader.getBatch());
        mHUD.setPbrincoScript(pbrincoScript);

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
