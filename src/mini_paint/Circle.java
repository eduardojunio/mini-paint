package mini_paint;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Circle extends Shape {

	private static final long serialVersionUID = 3969594335465310735L;
	protected double radius = 20;

	public boolean setPropertiesDialog(double x, double y, ColorItem c) {
	      JTextField xField = new JTextField(5);
	      JTextField yField = new JTextField(5);
	      JTextField radiusField = new JTextField(5);
	      
	      ColorItem[] colors = new ColorItem[DrawPanel.colors.size()];
	      colors = DrawPanel.colors.toArray(colors);
	      JComboBox<ColorItem> colorField = new JComboBox<>(colors);
	      colorField.setToolTipText("Selecione uma cor");
	      colorField.setSelectedItem(c);
	      colorField.addItemListener(new ColorChangeListener());

	      JPanel dialogPanel = new JPanel();
	      dialogPanel.add(new JLabel("Coordenada X:"));
	      dialogPanel.add(xField);
	      dialogPanel.add(Box.createHorizontalStrut(10));
	      dialogPanel.add(new JLabel("Coordenada Y:"));
	      dialogPanel.add(yField);
	      dialogPanel.add(Box.createHorizontalStrut(10));
	      dialogPanel.add(new JLabel("Raio:"));
	      dialogPanel.add(radiusField);
	      dialogPanel.add(Box.createHorizontalStrut(10));
	      dialogPanel.add(new JLabel("Cor:"));
	      dialogPanel.add(colorField);
	      
	      xField.setText(String.valueOf(x));
	      yField.setText(String.valueOf(y));
	      radiusField.setText(String.valueOf(radius));

	      int result = JOptionPane.showConfirmDialog(null, dialogPanel, 
	               "Propriedades do c\u00EDrculo", JOptionPane.OK_CANCEL_OPTION);
	      if (result == JOptionPane.OK_OPTION) {
	    	  this.x = Double.parseDouble(xField.getText());
	    	  this.y = Double.parseDouble(yField.getText());
	    	  this.radius = Double.parseDouble(radiusField.getText());
	    	  this.color = (ColorItem) colorField.getSelectedItem();
	    	  return true;
	      }
	      
	      return false;
	}
	
	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		Ellipse2D circle = new Ellipse2D.Double(x - radius, y - radius, 2.0 * radius, 2.0 * radius);
		g2.setColor(color.getValue());
		g2.fill(circle);
		g2.draw(circle);
	}
}
