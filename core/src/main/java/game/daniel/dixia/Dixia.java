package game.daniel.dixia;


import com.badlogic.ashley.core.PooledEngine;
import  com.badlogic.gdx.Game ;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import  com.badlogic.gdx.scenes.scene2d.ui.Skin ;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import game.daniel.dixia.Screens.Bri.Laberintobri;
import game.daniel.dixia.Screens.Bri.Unirbri;
import game.daniel.dixia.Screens.Bri.Unirverbri;
import game.daniel.dixia.Screens.Platform;
import game.daniel.dixia.Screens.bra.Laberintobra;
import game.daniel.dixia.Screens.bra.Unirbra;
import game.daniel.dixia.Screens.bra.Unirverbra;
import game.daniel.dixia.Screens.bre.Laberintobre;
import game.daniel.dixia.Screens.bre.Unirbre;
import game.daniel.dixia.Screens.bre.Unirverbre;
import game.daniel.dixia.Screens.bro.Laberintobro;
import game.daniel.dixia.Screens.bro.Unirbro;
import game.daniel.dixia.Screens.bro.Unirverbro;
import game.daniel.dixia.Screens.bru.Laberintobru;
import game.daniel.dixia.Screens.bru.Unirbru;
import game.daniel.dixia.Screens.bru.Unirverbru;
import games.rednblack.editor.renderer.SceneLoader;
import games.rednblack.editor.renderer.resources.AsyncResourceManager;
import games.rednblack.editor.renderer.resources.ResourceManagerLoader;

public class Dixia extends Game {

    private AssetManager mAssetManager;

    private SceneLoader mSceneLoader;
    private AsyncResourceManager mAsyncResourceManager;

    private Viewport mViewport;
    private OrthographicCamera mCamera;

    private PooledEngine mEngine;

    public Platform platform = new Platform(mAsyncResourceManager,mSceneLoader,mViewport,mAssetManager,mCamera);
    //unir
    public Unirbra unirbra = new Unirbra(mAsyncResourceManager,mSceneLoader,mViewport,mAssetManager,mCamera);
    public Unirbre unirbre = new Unirbre(mAsyncResourceManager,mSceneLoader,mViewport,mAssetManager,mCamera);
    public Unirbri unirbri = new Unirbri(mAsyncResourceManager,mSceneLoader,mViewport,mAssetManager,mCamera);
    public Unirbro unirbro = new Unirbro(mAsyncResourceManager,mSceneLoader,mViewport,mAssetManager,mCamera);
    public Unirbru unirbru = new Unirbru(mAsyncResourceManager,mSceneLoader,mViewport,mAssetManager,mCamera);
    //laberinto
    public Laberintobra laberintobra = new Laberintobra(mAsyncResourceManager,mSceneLoader,mViewport,mAssetManager,mCamera);
    public Laberintobre laberintobre = new Laberintobre(mAsyncResourceManager,mSceneLoader,mViewport,mAssetManager,mCamera);
    public Laberintobri laberintobri = new Laberintobri(mAsyncResourceManager,mSceneLoader,mViewport,mAssetManager,mCamera);
    public Laberintobro laberintobro = new Laberintobro(mAsyncResourceManager,mSceneLoader,mViewport,mAssetManager,mCamera);
    public Laberintobru laberintobru = new Laberintobru(mAsyncResourceManager,mSceneLoader,mViewport,mAssetManager,mCamera);
    //unir vertical
    public Unirverbra unirverbra = new Unirverbra(mAsyncResourceManager,mSceneLoader,mViewport,mAssetManager,mCamera);
    public Unirverbre unirverbre = new Unirverbre(mAsyncResourceManager,mSceneLoader,mViewport,mAssetManager,mCamera);
    public Unirverbri unirverbri = new Unirverbri(mAsyncResourceManager,mSceneLoader,mViewport,mAssetManager,mCamera);
    public Unirverbro unirverbro = new Unirverbro(mAsyncResourceManager,mSceneLoader,mViewport,mAssetManager,mCamera);
    public Unirverbru unirverbru = new Unirverbru(mAsyncResourceManager,mSceneLoader,mViewport,mAssetManager,mCamera);

    @Override
    public void create() {

        //Create a basic camera and a viewport
        mCamera = new OrthographicCamera();
        //mViewport = new ExtendViewport(192, 120, 0, 0, mCamera);
        mViewport = new ExtendViewport(15, 8, mCamera);

        mAssetManager = new AssetManager();
        // set asset manager logger

        // Sets a new AssetLoader for the given type
        mAssetManager.setLoader(AsyncResourceManager.class, new ResourceManagerLoader(mAssetManager.getFileHandleResolver()));
        // Adds the given asset to the loading queue of the AssetManager
        mAssetManager.load("project.dt", AsyncResourceManager.class);
        mAssetManager.load("skin/skin.json", Skin.class);

        mAssetManager.finishLoading();

        // resourceManager will be init. here from asset manager
        mAsyncResourceManager = mAssetManager.get("project.dt", AsyncResourceManager.class);

        // Initialize HyperLap2D's SceneLoader, all assets will be loaded here
        mSceneLoader = new SceneLoader(mAsyncResourceManager);

        //-----------------


        // add components to mapper

        setScreen(platform);


    }


    @Override
    public void dispose() {
        super.dispose();
        this.mAssetManager=mAssetManager;
        this.mSceneLoader=mSceneLoader;
        this.mAsyncResourceManager=mAsyncResourceManager;
        this.mViewport=mViewport;
        this.mCamera=mCamera;
        this.mEngine=mEngine;

    }
}