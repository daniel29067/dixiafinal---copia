package game.daniel.dixia.component;

import games.rednblack.editor.renderer.components.BaseComponent;

public class ChipbreComponent implements BaseComponent {
    public int value =1;

    @Override
    public void reset() {
        value =1;
    }

}
