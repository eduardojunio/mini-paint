package mini_paint;

import java.awt.Graphics;
import java.io.Serializable;

public abstract class Shape implements Serializable {
	
	private static final long serialVersionUID = -715285726621818108L;
	protected double x, y;
	protected ColorItem color;

	public abstract void draw(Graphics g);
	public abstract boolean setPropertiesDialog(double x, double y, ColorItem c);
	
	public final boolean setPropertiesDialog() {
		return setPropertiesDialog(0, 0, DrawPanel.getSelectedColor());
	}
}
