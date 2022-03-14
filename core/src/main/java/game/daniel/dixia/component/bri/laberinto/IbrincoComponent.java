package game.daniel.dixia.component.bri.laberinto;

import games.rednblack.editor.renderer.components.BaseComponent;

public class IbrincoComponent implements BaseComponent {

    public int touchedPlatforms = 0;
    public int touchedWords = 0;
    @Override
    public void reset() {
        touchedPlatforms = 0;
        touchedWords = 0;
    }

}
