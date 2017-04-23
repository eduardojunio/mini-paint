package mini_paint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JPanel;

public class DrawPanel extends JPanel {
	public static DefaultListModel<Shape> shapes = new DefaultListModel<>();
	public static List<ColorItem> colors = new ArrayList<>();
	
	private static final long serialVersionUID = -6681855519100790894L;
	private static ColorItem selectedColor;
	private static List<SelectedColorListener> selectedColorListeners = new ArrayList<>();
	
	public DrawPanel() {
		DrawPanel.colors.add(new ColorItem("Vermelho", Color.RED));
		DrawPanel.colors.add(new ColorItem("Verde", Color.GREEN));
		DrawPanel.colors.add(new ColorItem("Azul", Color.BLUE));
		selectedColor = DrawPanel.colors.get(0);
	}

	public static void addSelectedColorListener(SelectedColorListener selectedColorListener) {
		DrawPanel.selectedColorListeners.add(selectedColorListener);
	}
	
	public static ColorItem getSelectedColor() {
		return selectedColor;
	}
	
	public static void setSelectedColor(ColorItem colorItem) {
		selectedColor = colorItem;
		for (SelectedColorListener listener : selectedColorListeners) {
			listener.selectedColorChanged(selectedColor);
		}
	}

	public void drawShape(Shape s) {
		DrawPanel.shapes.addElement(s);
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
		super.paintComponent(g);
		Object[] shapes = DrawPanel.shapes.toArray();
		for (Object s : shapes) {
			((Shape) s).draw(g);
		}
	}
}
