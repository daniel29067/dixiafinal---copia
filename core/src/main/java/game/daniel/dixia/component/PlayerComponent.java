package game.daniel.dixia.component;

import games.rednblack.editor.renderer.components.BaseComponent;

public class PlayerComponent implements BaseComponent {

    public int touchedPlatforms = 0;
    public int chipbra = 0;
    public int bonesColllled = 0;
    @Override
    public void reset() {
        touchedPlatforms = 0;
        bonesColllled = 0;
        chipbra = 0;
    }

    public int getChipbra() {
        return chipbra;
    }
}
