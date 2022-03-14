package game.daniel.dixia.component.bre.laberinto;

import games.rednblack.editor.renderer.components.BaseComponent;

public class IbrevaComponent  implements BaseComponent {

    public int touchedPlatforms = 0;
    public int touchedWords = 0;
    @Override
    public void reset() {
        touchedPlatforms = 0;
        touchedWords = 0;
    }

}