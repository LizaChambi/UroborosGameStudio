package uroborosGameStudio.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SpinnerListModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.team.uroboros.uroboros.engine.ui.Canvas;

import uroborosGameStudio.domain.appModel.MainWindowModel;
import uroborosGameStudio.ui.componentListeners.ActorNameAdapterListener;
import uroborosGameStudio.ui.componentListeners.BtnAddActorActionListener;
import uroborosGameStudio.ui.componentListeners.BtnOpenImageActionListener;
import uroborosGameStudio.ui.componentListeners.CloseWindowDialogAL;
import uroborosGameStudio.ui.componentListeners.NumberWHAdapter;
import uroborosGameStudio.ui.components.ButtonUGS;

public class NewActorDialog extends JDialog 
{
	private static final long serialVersionUID = 1L;
	private final JPanel globalPanel = new JPanel();
	private MainWindowModel model;
	private Canvas canvas;
	private JTree treeScenes;
	private JPanel headerPanel;
	private JPanel propertiesPanel;
	private JPanel buttonPanel;
	private JPanel panelName;
	private JTextField textFieldName;
	private JPanel panelImage;
	private JLabel lblImage;
	private JTextField textFieldImagen;
	private JButton btnOpenImage;
	private JButton okButton = new JButton("Crear");
	private JLabel lblError;
	private JPanel panelSpriteSheet = new JPanel();
	private JLabel lblAncho;
	private JTextField textFieldWidth;
	private JLabel lblHigh;
	private JTextField textFieldHeight;
	private JLabel lblNewLabel;
	private JCheckBox cbxFramesEnable;
	private JLabel lblFrames;
	private JPanel panelNumFrames;
	private JTextField textFieldNumFrames;
	private JPanel panelTitleFrame;
	private JPanel panelDimensionFrame;
	private Boolean enableNewActor = false;
	private JLabel lblNumberError;
	private JPanel panelRatio;
	private JSpinner spinnerRatio;

	public NewActorDialog(MainWindowModel model, JTree treeScenes, Canvas canvas) 
	{
		initializedDialog(model, treeScenes, canvas);
		initializedHeaderPanel();
		titleLabel();
		
		initializedPropertiesPanel();
		initializedButtonPanel();
		
		properties();
		buttons();
	}

	private void properties() 
	{
		propertyName();
		propertyImage();
		propertyFrames();
	}

	private void propertyImage() 
	{
		initializedPanelImage();
		lblImage = new JLabel("Imágen:");
		lblImage.setBounds(5, 10, 57, 15);
		propertiesPanel.add(panelImage);
		panelImage.add(lblImage);
		
		textFieldImagen = new JTextField("");
		textFieldImagen.setEditable(false);
		textFieldImagen.setBounds(71, 8, 269, 19);
		textFieldImagen.setColumns(10);
		panelImage.add(textFieldImagen);
		
		btnOpenImage = new JButton("Abrir...");
		btnOpenImage.addActionListener(new BtnOpenImageActionListener(textFieldImagen, panelImage, okButton));
		btnOpenImage.setBounds(341, 5, 75, 25);
		btnOpenImage.setFont(new Font("Dialog", Font.PLAIN, 12));
		panelImage.setLayout(null);
		panelImage.add(btnOpenImage);
		
		cbxFramesEnable = new JCheckBox("Habilitar animación");
		cbxFramesEnable.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(cbxFramesEnable.isSelected())
				{
					panelSpriteSheet.setVisible(true);
				}
				else
				{
					panelSpriteSheet.setVisible(false);
				}
			}
		});
		cbxFramesEnable.setBounds(0, 36, 420, 23);
		panelImage.add(cbxFramesEnable);
	}

	private void propertyFrames() 
	{
		inicializedPanelFrames();
		numberFrames();
		titleDimensionFrames();
		dimensionFrames();
		ratioSpriteSheets();
	}

	private void ratioSpriteSheets() 
	{
		inicializeRatioPanel();
		panelRatio.setLayout(null);
		
		JLabel lblRatio = new JLabel("Ratio:");
		lblRatio.setBounds(5, 7, 42, 15);
		panelRatio.add(lblRatio);
		
		spinnerRatio = new JSpinner();
		spinnerRatio.setBounds(52, 5, 50, 20);
		spinnerRatio.setModel(new SpinnerListModel(new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}));
		panelRatio.add(spinnerRatio);
	}

	private void inicializeRatioPanel() {
		panelRatio = new JPanel();
		panelRatio.setBounds(5, 122, 411, 30);
		panelSpriteSheet.add(panelRatio);
	}

	private void dimensionFrames() 
	{
		inicializedDimensionFrame();
		lblAncho = new JLabel("Ancho:");
		lblAncho.setBounds(5, 21, 48, 15);
		panelDimensionFrame.add(lblAncho);
		
		lblNumberError = new JLabel("");
		lblNumberError.setForeground(Color.RED);
		lblNumberError.setFont(new Font("Dialog", Font.PLAIN, 11));
		lblNumberError.setBounds(63, 0, 336, 15);
		panelDimensionFrame.add(lblNumberError);

		textFieldWidth = new JTextField("");
		textFieldWidth.addKeyListener(new NumberWHAdapter(okButton, lblNumberError));
		textFieldWidth.setBounds(55, 19, 150, 19);
		textFieldWidth.setColumns(10);
		panelDimensionFrame.add(textFieldWidth);
		
		lblHigh = new JLabel("Alto:");
		lblHigh.setBounds(222, 21, 40, 15);
		panelDimensionFrame.add(lblHigh);
		
		textFieldHeight = new JTextField("");
		textFieldHeight.addKeyListener(new NumberWHAdapter(okButton, lblNumberError));
		textFieldHeight.setBounds(258, 19, 141, 19);
		textFieldHeight.setColumns(10);
		panelDimensionFrame.add(textFieldHeight);
		
//		textFieldName.addKeyListener(new ActorNameAdapterListener(textFieldName, okButton, model,lblError));
	}

	private void titleDimensionFrames() 
	{
		inicializedTitleFrame();
		lblFrames = new JLabel("Dimensión de los sprites:");
		lblFrames.setFont(new Font("Dialog", Font.PLAIN, 12));
		panelTitleFrame.add(lblFrames);
	}

	private void numberFrames() {
		inicializedNumFrames();
		lblNewLabel = new JLabel("Cantidad de sprites:");
		lblNewLabel.setBounds(5, 6, 150, 15);
		panelNumFrames.add(lblNewLabel);
		
		textFieldNumFrames = new JTextField("1");
		textFieldNumFrames.setBounds(157, 5, 242, 18);
		textFieldNumFrames.setPreferredSize(new Dimension(4, 18));
		textFieldNumFrames.setColumns(10);
		panelNumFrames.add(textFieldNumFrames);
	}

	private void inicializedDimensionFrame() {
		panelDimensionFrame = new JPanel();
		panelDimensionFrame.setBounds(5, 75, 411, 45);
		panelDimensionFrame.setLayout(null);
		panelSpriteSheet.add(panelDimensionFrame);
	}

	private void inicializedTitleFrame() 
	{
		panelTitleFrame = new JPanel();
		panelTitleFrame.setBounds(5, 53, 411, 19);
		panelTitleFrame.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		panelSpriteSheet.add(panelTitleFrame);
	}

	private void inicializedNumFrames() {
		panelSpriteSheet.setLayout(null);
		panelNumFrames = new JPanel();
		panelNumFrames.setBounds(5, 22, 411, 30);
		panelNumFrames.setLayout(null);
		panelSpriteSheet.add(panelNumFrames);
	}

	private void inicializedPanelFrames() {
		panelSpriteSheet.setBounds(8, 125, 421, 160);
		panelSpriteSheet.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Sprite sheets", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(64, 64, 64)));
		panelSpriteSheet.setVisible(false);
		propertiesPanel.add(panelSpriteSheet);
	}

	private void propertyName() 
	{
		initializedPanelName();
		JLabel lblName = new JLabel("Nombre:");
		lblName.setBounds(5, 18, 60, 19);
		panelName.add(lblName);
		
		lblError = new JLabel("");
		lblError.setForeground(Color.RED);
		lblError.setFont(new Font("Dialog", Font.PLAIN, 11));
		lblError.setBounds(70, 0, 346, 15);
		panelName.add(lblError);
		
		textFieldName = new JTextField("");
		textFieldName.addKeyListener(new ActorNameAdapterListener(textFieldName, okButton, model,lblError));
		textFieldName.setBounds(70, 18, 346, 19);
		textFieldName.setColumns(10);
		panelName.add(textFieldName);
	}

	private void buttons() 
	{
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		{
			okButton.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) 
				{
					if(enableNewActor) 
					{
						okButton.setEnabled(true);
					}
				}
			});
			okButton.addActionListener(new BtnAddActorActionListener(treeScenes, canvas, spinnerRatio, cbxFramesEnable, textFieldName, textFieldImagen, textFieldNumFrames, textFieldWidth, textFieldHeight, this, model));
			okButton.setEnabled(false);
			buttonPanel.add(okButton);
			getRootPane().setDefaultButton(okButton);
		}
		
		new ButtonUGS("Cancel", new CloseWindowDialogAL(this), buttonPanel);
		
	}

	private void initializedButtonPanel() 
	{
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
	}
	
	private void initializedPropertiesPanel() 
	{
		propertiesPanel = new JPanel();
		propertiesPanel.setBorder(new TitledBorder(null, "Propiedades", TitledBorder.LEADING, TitledBorder.TOP, null, Color.DARK_GRAY));
		propertiesPanel.setLayout(null);
		globalPanel.add(propertiesPanel);
	}

	private void initializedPanelImage() {
		panelImage = new JPanel();
		panelImage.setBounds(5, 60, 428, 59);
		propertiesPanel.add(panelImage);
	}

	private void initializedPanelName() {
		panelName = new JPanel();
		panelName.setBounds(5, 17, 428, 43);
		panelName.setLayout(null);
		propertiesPanel.add(panelName);
	}

	private void initializedHeaderPanel() 
	{
		headerPanel = new JPanel();
		FlowLayout fl_errorPanel = (FlowLayout) headerPanel.getLayout();
		fl_errorPanel.setAlignment(FlowLayout.LEFT);
		globalPanel.add(headerPanel, BorderLayout.NORTH);
	}

	private void titleLabel() 
	{
		JLabel lblTitle = new JLabel("Complete las siguientes propiedades:");
		lblTitle.setFont(new Font("Dialog", Font.PLAIN, 12));
		headerPanel.add(lblTitle);
	}

	private void initializedDialog(MainWindowModel model, JTree treeScenes, Canvas canvas) 
	{
		this.model = model;
		this.treeScenes=treeScenes;
		this.canvas = canvas;
		setTitle("Nuevo Actor");
		setBounds(100, 100, 450, 400);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		globalPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(globalPanel, BorderLayout.CENTER);
		globalPanel.setLayout(new BorderLayout(0, 0));
	}
}
