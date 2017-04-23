package mini_paint;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 4109910200999839693L;
	private JPanel contentPane;
	private JPanel drawPanel = new DrawPanel();
	private FileNameExtensionFilter fileExtensionFilter = new FileNameExtensionFilter("Arquivo de desenho", "minipaint");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		setTitle("Mini-Paint POO");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menuFile = new JMenu("Arquivo");
		menuBar.add(menuFile);
		
		JMenuItem optOpen = new JMenuItem("Abrir desenho");
		optOpen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				final JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Abrir desenho");
				fileChooser.setFileFilter(fileExtensionFilter);
				int result = fileChooser.showOpenDialog(rootPane);
				if (result == JFileChooser.APPROVE_OPTION) {
					try {
						FileInputStream fileInput = new FileInputStream(fileChooser.getSelectedFile());
						ObjectInputStream objectInput = new ObjectInputStream(fileInput);
						Object[] shapes = (Object[]) objectInput.readObject();
						for (Object s : shapes) {
							DrawPanel.shapes.addElement((Shape) s);
						}
						objectInput.close();
						drawPanel.repaint();
					} catch (FileNotFoundException err) {
						err.printStackTrace();
						JOptionPane.showMessageDialog(rootPane, "Arquivo não encontrado", "Arquivo não encontrado", JOptionPane.ERROR_MESSAGE);
					} catch (IOException err) {
						err.printStackTrace();
						JOptionPane.showMessageDialog(rootPane, "Erro na leitura/escrita", "Erro desconhecido na leitura/escrita", JOptionPane.ERROR_MESSAGE);
					} catch (ClassNotFoundException err) {
						err.printStackTrace();
						JOptionPane.showMessageDialog(rootPane, "Erro interno", "Erro interno", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(rootPane, "Selecione um arquivo para carregar o desenho!", "Selecione um arquivo", JOptionPane.WARNING_MESSAGE);
				}
			}
			
		});
		menuFile.add(optOpen);
		
		JMenuItem optSave = new JMenuItem("Salvar desenho");
		optSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				final JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Salvar desenho");
				fileChooser.setFileFilter(fileExtensionFilter);
				int result = fileChooser.showSaveDialog(rootPane);
				if (result == JFileChooser.APPROVE_OPTION) {
					try {
						File file = fileChooser.getSelectedFile();
						FileOutputStream fileOutput = new FileOutputStream(file.toString() + "." + fileExtensionFilter.getExtensions()[0]);
						ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
						Object[] shapes = DrawPanel.shapes.toArray();
						objectOutput.writeObject(shapes);
						objectOutput.close();
					} catch (FileNotFoundException err) {
						err.printStackTrace();
						JOptionPane.showMessageDialog(rootPane, "Arquivo não encontrado", "Arquivo não encontrado", JOptionPane.ERROR_MESSAGE);
					} catch (IOException err) {
						err.printStackTrace();
						JOptionPane.showMessageDialog(rootPane, "Erro na leitura/escrita", "Erro desconhecido na leitura/escrita", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(rootPane, "Selecione um arquivo para salvar o desenho!", "Selecione um arquivo", JOptionPane.WARNING_MESSAGE);
				}
			}
			
		});
		menuFile.add(optSave);
		
		JMenuItem optClose = new JMenuItem("Fechar");
		optClose.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
			
		});
		menuFile.add(optClose);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		String[] geometricForms = {"C\u00EDrculo", "Ret\u00E2ngulo", "Tri\u00E2ngulo"};
		JComboBox<String> geometricForm = new JComboBox<>(geometricForms);
		GridBagConstraints gbc_geometricForm = new GridBagConstraints();
		gbc_geometricForm.insets = new Insets(0, 0, 5, 5);
		gbc_geometricForm.anchor = GridBagConstraints.WEST;
		gbc_geometricForm.gridx = 0;
		gbc_geometricForm.gridy = 1;
		contentPane.add(geometricForm, gbc_geometricForm);
		
		drawPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DrawPanel dp = (DrawPanel) e.getComponent();
				String selectedForm = (String) geometricForm.getSelectedItem();
				Shape s;
				switch (selectedForm) {
				case "C\u00EDrculo":
					s = new Circle();
					break;
				case "Tri\u00E2ngulo":
					s = new Triangle();
					break;
				default:
					s = new Rectangle();
				}
				if (s.setPropertiesDialog(e.getX(), e.getY(), DrawPanel.getSelectedColor())) {
					dp.drawShape(s);
				}
			}
		});
		drawPanel.setBackground(Color.WHITE);
		GridBagConstraints gbc_drawPanel = new GridBagConstraints();
		gbc_drawPanel.insets = new Insets(0, 0, 0, 5);
		gbc_drawPanel.fill = GridBagConstraints.BOTH;
		gbc_drawPanel.gridx = 0;
		gbc_drawPanel.gridy = 2;
		contentPane.add(drawPanel, gbc_drawPanel);
		
		ColorItem[] colors = new ColorItem[DrawPanel.colors.size()];
		colors = DrawPanel.colors.toArray(colors);
		JComboBox<ColorItem> color = new JComboBox<>(colors);
		color.setToolTipText("Selecione uma cor");
		color.addItemListener(new ColorChangeListener());
		DrawPanel.addSelectedColorListener(new SelectedColorListener() {

			@Override
			public void selectedColorChanged(ColorItem newSelectedColor) {
				color.setSelectedItem(newSelectedColor);
			}
			
		});
		GridBagConstraints gbc_color = new GridBagConstraints();
		gbc_color.insets = new Insets(0, 0, 5, 5);
		gbc_color.anchor = GridBagConstraints.WEST;
		gbc_color.gridx = 0;
		gbc_color.gridy = 0;
		contentPane.add(color, gbc_color);
		
		JList<Shape> shapesDrawn = new JList<>(DrawPanel.shapes);
		GridBagConstraints gbc_shapesDrawn = new GridBagConstraints();
		gbc_shapesDrawn.fill = GridBagConstraints.BOTH;
		gbc_shapesDrawn.gridx = 1;
		gbc_shapesDrawn.gridy = 2;
		contentPane.add(shapesDrawn, gbc_shapesDrawn);
		
		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Shape s = shapesDrawn.getSelectedValue();
				s.setPropertiesDialog(s.x, s.y, s.color);
				drawPanel.repaint();
			}
			
		});
		GridBagConstraints gbc_btnEditar = new GridBagConstraints();
		gbc_btnEditar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnEditar.insets = new Insets(0, 0, 5, 0);
		gbc_btnEditar.gridx = 1;
		gbc_btnEditar.gridy = 0;
		contentPane.add(btnEditar, gbc_btnEditar);
		
		JButton btnApagar = new JButton("Apagar");
		btnApagar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				DrawPanel.shapes.removeElementAt(shapesDrawn.getSelectedIndex());
				drawPanel.repaint();
			}
			
		});
		GridBagConstraints gbc_btnApagar = new GridBagConstraints();
		gbc_btnApagar.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnApagar.insets = new Insets(0, 0, 5, 0);
		gbc_btnApagar.gridx = 1;
		gbc_btnApagar.gridy = 1;
		contentPane.add(btnApagar, gbc_btnApagar);
	}

}
