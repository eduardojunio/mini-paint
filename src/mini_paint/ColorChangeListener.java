package mini_paint;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ColorChangeListener implements ItemListener {
    @Override
    public void itemStateChanged(ItemEvent event) {
       if (event.getStateChange() == ItemEvent.SELECTED) {
          ColorItem colorItem = (ColorItem) event.getItem();
          DrawPanel.setSelectedColor(colorItem);
       }
    }       
}
