package mini_paint;

import java.awt.Color;
import java.io.Serializable;

public class ColorItem implements Serializable {
	
	private static final long serialVersionUID = 8895879234340726954L;
	private String label;
	private Color value;

    public ColorItem(String label, Color value) {
    	this.label = label;
    	this.value = value;
    }

    public String getLabel() {
		return label;
	}

	public Color getValue() {
		return value;
	}

	@Override
    public String toString() {
        return label;
    }
}
