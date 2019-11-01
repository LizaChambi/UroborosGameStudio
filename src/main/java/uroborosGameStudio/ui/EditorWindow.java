package uroborosGameStudio.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.team.uroboros.uroboros.engine.ui.Canvas;

import uroborosGameStudio.domain.Body;
import uroborosGameStudio.domain.Circle;
import uroborosGameStudio.domain.Physics;
import uroborosGameStudio.domain.Rectangle;
import uroborosGameStudio.domain.SceneWrapper;
import uroborosGameStudio.ui.componentListeners.BtnDeleteAL;
import uroborosGameStudio.ui.componentListeners.BtnEditDimensionImageActionListener;
import uroborosGameStudio.ui.componentListeners.BtnEditImageActionListener;
import uroborosGameStudio.ui.componentListeners.BtnEditNameAL;
import uroborosGameStudio.ui.componentListeners.BtnEditPositionAL;
import uroborosGameStudio.ui.componentListeners.BtnGlobalBehaviorsActionListener;
import uroborosGameStudio.ui.componentListeners.BtnNewActorAL;
import uroborosGameStudio.ui.componentListeners.BtnNewBehaviorActionListener;
import uroborosGameStudio.ui.componentListeners.BtnNewSceneAL;
import uroborosGameStudio.ui.componentListeners.BtnOpenAudioActionListener;
import uroborosGameStudio.ui.componentListeners.BtnOpenImageActionListener;
import uroborosGameStudio.ui.componentListeners.BtnPlayAL;
import uroborosGameStudio.ui.componentListeners.BtnRemoveBehaviorActionListener;
import uroborosGameStudio.ui.componentListeners.BtnRemoveColliderActionListener;
import uroborosGameStudio.ui.componentListeners.BtnSaveProjectAL;
import uroborosGameStudio.ui.componentListeners.BtnStopActionListener;
import uroborosGameStudio.ui.componentListeners.ClearConsoleActionListener;
import uroborosGameStudio.ui.componentListeners.CloseProjectAL;
import uroborosGameStudio.ui.componentListeners.CodeFieldListener;
import uroborosGameStudio.ui.componentListeners.CreateNewProjectAL;
import uroborosGameStudio.ui.componentListeners.NewCollisionActionListener;
import uroborosGameStudio.ui.componentListeners.OpenEditSpriteSheetActionListener;
import uroborosGameStudio.ui.componentListeners.OpenProjectActionListener;
import uroborosGameStudio.ui.componentListeners.PhysicsBodyAL;
import uroborosGameStudio.ui.componentListeners.RadioButtonML;
import uroborosGameStudio.ui.componentListeners.SceneTreePanelTSL;
import uroborosGameStudio.ui.componentListeners.SelectedBehaviorFileActionListener;
import uroborosGameStudio.ui.componentListeners.SelectedCollitionActionListener;
import uroborosGameStudio.ui.components.JavaScriptEditor;
import uroborosGameStudio.ui.components.TextAreaOutputStream;
import uroborosGameStudio.ui.components.UGSRadioButton;

public class EditorWindow extends AbstractWindowFrame {

	private int idScene = 1;
	private Canvas canvas = new Canvas();
	private Dimension resolution = Toolkit.getDefaultToolkit().getScreenSize();
	private JPanel northPanel;
	private JPanel centerPanel;
	private JPanel principalPanel;
	private JPanel buttonPanel;
	private JScrollPane eastPanel;
	private JPanel gameEditorPanel;
	private JPanel treePlayPanel;
	private JPanel editorPanel;
	private JPanel propertiesEditPanel;
	private JPanel editNamePanel;
	private JavaScriptEditor javaScriptEditor = new JavaScriptEditor();
	private JTextArea textArea = javaScriptEditor.getJSTextArea();
	private JScrollPane scroollPanel;
	private JPanel playPanel;
	private JTree treeScenes = new JTree();
	private JTextField nameTextField = new JTextField("");
	private JTextField posXTextField = new JTextField("0");
	private JTextField posYTextField = new JTextField("0");
	private JPanel panelEditPosition;
	private JPanel panelEditImage;
	private JLabel lblImage;
	private JPanel panelEditDimension;
	private JLabel lblDimension;
	private JTextField textFieldPathImage = new JTextField("");
	private JButton btnSetImage;
	private JTextField textFieldWidth = new JTextField("0");
	private JTextField textFieldHigh = new JTextField("0");
	private JButton btnEditDimension;
	private JButton btnEditImage;
	private JPanel menuPanel;
	private JMenuBar menuBar;
	private JTable table = new JTable();
	private JPanel BehaviorButtonsPanel;
	private JButton btnNewBehavior;
	private JButton btnGlobalBehavior;
	private JButton btnDeleteBehavior;
	private JPanel behaviorPanel;
	private JScrollPane tableBehaviorScrollPanel;
	private JPanel collisionPanel;
	private JTable tableCollision = new JTable();
	private JPanel tableCollisionPanel;
	private JButton btnAddCollider;
	private JButton btnRemoveCollider;
	private JTabbedPane tabbedPanel;
	private JScrollPane tableCollisionScrollPanel;
	private JPanel propertiesCollisionPanel;
	private JLabel lblBodyMaterial;
	private JPanel bodyMaterialPanel;
	private JPanel bodyTypePanel;
	private JPanel typePhysicPanel;
	private JPanel informationPanel;
	private JLabel lblInformation;
	private JComboBox cboxSelectBody = new JComboBox();
	private UGSRadioButton rdStatic = new UGSRadioButton("Estático", Physics.STATIC);
	private UGSRadioButton rdKinematic = new UGSRadioButton("Cinemático", Physics.KINEMATIC);
	private UGSRadioButton rdDinamic = new UGSRadioButton("Dinámico", Physics.DYNAMIC);
	private JLabel lblErrorNumber;
	private JPanel panelEditAudio;
	private JTextField textFieldPathAudio = new JTextField();
	private JLabel lblErrorDimension;
	private JButton btnStop = new JButton("Detener", new ImageIcon("images/Stop-Normal-Yellow-icon.png"));
	private JButton btnPlay;
	private JPanel consolePanel;
	private JTextArea txtConsole = new JTextArea();
	private JScrollPane scrollPane;
	private TextAreaOutputStream taos;

	public EditorWindow() 
	{
		super();
		this.initializeFrame();
	
		this.initializeGlobalPanel();
		this.initializeNorthPanel();
		this.initializeMenuPanel();
		this.initializeMenuBar();
		this.initializeButtonPanel();
		this.toolbar();

		this.initializeCenterPanel();
		
		this.initializeTextPanel();
		this.initializeGameEditorPanel();
		
		this.initializeTreePlayPanel();
	
		this.editorPanel();
		this.initializePropertiesEditPanel();
		this.optionsEditorPanel();
		this.behaviorSettingPanel();
		
		this.initializeTreePanel();
		this.initializePlayPanel();
		
		this.initializeCanvas();
		this.initializeCodeTextArea();
		
		this.frame.setJMenuBar(menuBar);
		this.frame.pack();
	}
	
	private void initializeFrame() {
		this.frame.setTitle("Uroboros Game Studio - " + model.getPathProject());
		this.frame.setSize(this.resolution);
		this.frame.setPreferredSize(this.resolution);
		this.frame.setMinimumSize(new Dimension(800,600));
		this.frame.setResizable(true);
	}
	
	private void initializeGlobalPanel() {
		principalPanel = new JPanel(new BorderLayout());
		principalPanel.setPreferredSize(new Dimension(resolution.width, resolution.height-30) );
		this.frame.getContentPane().add(principalPanel, BorderLayout.CENTER);
	}
	
	private void initializeMenuPanel() {
		menuPanel = new JPanel();
		menuPanel.setPreferredSize(new Dimension(800, 35));
	}
	
	private void initializeMenuBar() {
		menuBar = new JMenuBar();
		optionsFile();
		optionEdit();
		optionsHelp();
	}

	private void optionEdit() 
	{
		JMenu edit = new JMenu("Edición");
		
		JMenuItem editItem = new JMenuItem("Borrar");
		editItem.setEnabled(true);
		editItem.addActionListener(new BtnDeleteAL(treeScenes, canvas, nameTextField, posXTextField, posYTextField, textFieldPathImage, textFieldWidth, textFieldHigh, model));
		edit.add(editItem);
		
		editItem = new JMenuItem("Configurar Animación...");
		editItem.setEnabled(true);
		editItem.addActionListener(new OpenEditSpriteSheetActionListener(treeScenes, canvas, model));
		edit.add(editItem);
		
		menuBar.add(edit);
	}

	private void optionsHelp() 
	{
		JMenu help = new JMenu("Ayuda");
		
		JMenuItem helpItem1 = new JMenuItem("Guía inicial");
		helpItem1.setEnabled(false);
		help.add(helpItem1);
		
		JMenuItem helpItem2 = new JMenuItem("Acerca de Uroboros Game Studio");
		helpItem2.setEnabled(false);
		help.add(helpItem2);
		
		menuBar.add(help);
	}

	private void optionsFile() {
		JMenu menu = new JMenu("Archivo");
		menu.setMnemonic(KeyEvent.VK_N);
		menuBar.add(menu);
		
		JMenuItem menuItem = new JMenuItem("Nuevo Proyecto", KeyEvent.VK_N);
		menuItem.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_1, ActionEvent.ALT_MASK));
		menuItem.setEnabled(true);
		menuItem.addActionListener(new CreateNewProjectAL(frame, model));
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Abrir Proyecto", KeyEvent.VK_A);
		menuItem.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		menuItem.setEnabled(true);
		menuItem.addActionListener(new OpenProjectActionListener(model, frame, centerPanel));
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Guardar Proyecto", KeyEvent.VK_G);
		menuItem.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		menuItem.setEnabled(true);
		menuItem.addActionListener(new BtnSaveProjectAL(this.getModelObject()));
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Salir", KeyEvent.VK_R);
		menuItem.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_X, ActionEvent.ALT_MASK));
		menuItem.addActionListener(new CloseProjectAL(frame));
		menuItem.setEnabled(true);
		menu.add(menuItem);
		
		menuPanel.add(menuBar);
	}
	
	private void initializeButtonPanel() {
		buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(800, 40));
		FlowLayout fl_buttonPanel = new FlowLayout(FlowLayout.LEADING, 5, 5);
		buttonPanel.setLayout(fl_buttonPanel);
		this.northPanel.add(buttonPanel, BorderLayout.SOUTH);
	}
	
	private void toolbar() 
	{
		JButton btnNewScena = new JButton("Nueva Escena", new ImageIcon("images/add-icon.png"));
		btnNewScena.addActionListener(new BtnNewSceneAL(treeScenes, idScene, canvas));
		buttonPanel.add(btnNewScena);
		
		JButton btnNewActor = new JButton("Nuevo Actor", new ImageIcon("images/user-add-icon.png"));
		btnNewActor.addActionListener(new BtnNewActorAL(treeScenes, canvas, model));
		buttonPanel.add(btnNewActor);
		
		JButton btnSave = new JButton("Guardar", new ImageIcon("images/Save-as-icon.png"));
		btnSave.addActionListener(new BtnSaveProjectAL(this.getModelObject()));
		buttonPanel.add(btnSave);
		
		btnPlay = new JButton("Ejecutar", new ImageIcon("images/Play-Green-icon.png"));
		btnPlay.setEnabled(false);
		btnPlay.addActionListener(new BtnPlayAL(canvas, model, btnStop, btnPlay));
		buttonPanel.add(btnPlay);
		btnStop.setEnabled(false);
		
//		btnStop = new JButton("Detener", new ImageIcon("images/stop-icon.png"));
		btnStop.addActionListener(new BtnStopActionListener(treeScenes, canvas, model, btnPlay, btnStop));
		buttonPanel.add(btnStop);
		
		JButton btnRemove = new JButton("Eliminar", new ImageIcon("images/Actions-stop-icon.png"));
		btnRemove.addActionListener(new BtnDeleteAL(treeScenes, canvas, nameTextField, posXTextField, posYTextField, textFieldPathImage, textFieldWidth, textFieldHigh, model));
		buttonPanel.add(btnRemove);
	}

	private void initializeCanvas() 
	{
		this.canvas.setFocusable(true);
		this.canvas.setPreferredSize(new Dimension(666, 400));
		this.canvas.setFocusTraversalKeysEnabled(true);
		this.canvas.setBackground(Color.GREEN);
		this.playPanel.add(this.canvas);
	}
	
	private void initializePlayPanel() {
		playPanel = new JPanel(new BorderLayout());
		playPanel.setPreferredSize(new Dimension(666, 400));
		this.treePlayPanel.add(playPanel, BorderLayout.CENTER);
	}
	
	private void initializeCodeTextArea() {
		textArea.addKeyListener(new CodeFieldListener(model, table, tableCollision, textArea));
		textArea.setText("Editor de texto...");
	}

	private void initializeTreePanel() {
		scroollPanel = new JScrollPane(this.treeScenes);
		scroollPanel.setPreferredSize(new Dimension(307, 400));
		DefaultMutableTreeNode root = this.createTreeNode();
		DefaultTreeModel tree = new DefaultTreeModel(root);
		treeScenes.addTreeSelectionListener(new SceneTreePanelTSL(treeScenes,nameTextField,canvas, model, posXTextField, posYTextField, textFieldPathImage,textFieldWidth,textFieldHigh, table, cboxSelectBody, rdStatic, rdKinematic, rdDinamic, tableCollision, textArea, textFieldPathAudio, btnPlay));
		treeScenes.setModel(tree);
		this.treePlayPanel.add(scroollPanel, BorderLayout.LINE_START);
	}
	
	private DefaultMutableTreeNode createTreeNode() 
	{
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(model.getProject());
		for (int i=0; i < model.cantScenes(); i++)
		{
			SceneWrapper scene = model.getSceneIn(i);
			DefaultMutableTreeNode child1 = new DefaultMutableTreeNode();
			child1.setUserObject(scene);
			for (int subI=0; subI<scene.cantActors();subI++)
			{
				DefaultMutableTreeNode child11 = new DefaultMutableTreeNode();
				child11.setUserObject(scene.getActorIn(subI));
				child1.add(child11);
			}
			root.add(child1);
		}
		return root;
	}

	private void editorPanel() 
	{
		this.inicializeEditorPanel();
		this.tableCollisionPanel();
		this.propertiesCollisionPanel();
		this.initializeConsoleLogPanel();
		this.consoleLogProperties();
	}

	private void consoleLogProperties() {
		
		JButton btnClearConsole = new JButton("Limpiar consola");
		btnClearConsole.addActionListener(new ClearConsoleActionListener(txtConsole));
		btnClearConsole.setBounds(10, 5, 150, 25);
		consolePanel.add(btnClearConsole);
		
		taos = new TextAreaOutputStream(txtConsole);
		txtConsole.setBorder(new EmptyBorder(5, 5, 5, 5));
		txtConsole.setEditable(false);
		txtConsole.setForeground(Color.WHITE);
		txtConsole.setBackground(Color.BLACK);
		txtConsole.setFont(new Font("Cousine", Font.PLAIN, 12));
		
		scrollPane = new JScrollPane(this.txtConsole);
		scrollPane.setBounds(10, 32, 950, 228);
		scrollPane.setPreferredSize(new Dimension(945, 240));
		consolePanel.add(scrollPane);
	}

	private void tableCollisionPanel() 
	{
		this.inicializeCollisionPanel();
		this.inicializeTableCollisionPanel();
		this.tableCollision();
		this.buttonsCollisionTable();
	}
	
	private void initializeConsoleLogPanel() {
		consolePanel = new JPanel();
		consolePanel.setPreferredSize(new Dimension(973, 263));
		consolePanel.setLayout(null);
		tabbedPanel.addTab("Console Log", consolePanel);
		tabbedPanel.setIconAt(2, new ImageIcon(EditorWindow.class.getResource("/com/sun/java/swing/plaf/windows/icons/Computer.gif")));
	}

	private void buttonsCollisionTable() 
	{
		JPanel buttonsColliderPanel = new JPanel();
		buttonsColliderPanel.setBounds(5, 228, 500, 35);
		tableCollisionPanel.add(buttonsColliderPanel);
		buttonsColliderPanel.setLayout(null);
		
		btnAddCollider = new JButton("Nuevo");
		btnAddCollider.addActionListener(new NewCollisionActionListener(model, treeScenes,canvas, table, tableCollision));
		btnAddCollider.setBounds(0, 5, 78, 25);
		buttonsColliderPanel.add(btnAddCollider);
		
		btnRemoveCollider = new JButton("Eliminar");
		btnRemoveCollider.addActionListener(new BtnRemoveColliderActionListener(textArea, model, tableCollision, principalPanel));
		btnRemoveCollider.setBounds(410, 5, 90, 25);
		buttonsColliderPanel.add(btnRemoveCollider);
	}

	private void inicializeEditorPanel() 
	{
		tabbedPanel = new JTabbedPane();	
		editorPanel = new JPanel();
		editorPanel.setPreferredSize(new Dimension(973, 263));
		tabbedPanel.addTab("Propiedades", editorPanel);
		tabbedPanel.setIconAt(0, new ImageIcon("images/Pen-icon.png"));
		this.gameEditorPanel.add(tabbedPanel, BorderLayout.SOUTH);
	}

	private void propertiesCollisionPanel() 
	{
		this.inicializePropertiesCollisionPanel();
		this.bodyFigure();
		this.physicType();
	}

	private void physicType() 
	{
		this.inicializePhysicTypePanel();
		this.titlePhysicType();
		this.selectType();
		this.informationSelectedType();
	}

	private void titlePhysicType() {
		JLabel lblPhysics = new JLabel("Tipo de física aplicada al cuerpo:");
		lblPhysics.setBounds(5, 5, 232, 15);
		bodyTypePanel.add(lblPhysics);
	}

	private void informationSelectedType() {
		informationPanel = new JPanel();
		informationPanel.setBounds(5, 62, 446, 52);
		FlowLayout flowLayout_2 = (FlowLayout) informationPanel.getLayout();
		flowLayout_2.setAlignment(FlowLayout.LEADING);
		bodyTypePanel.add(informationPanel);
		
		lblInformation = new JLabel("");
		lblInformation.setFont(new Font("Droid Sans", Font.BOLD, 12));
		informationPanel.add(lblInformation);
	}

	private void selectType() {
		this.inicializeTypePhysicPanel();
		
		rdStatic.addMouseListener(new RadioButtonML(model, rdStatic.getText()));
		typePhysicPanel.add(rdStatic);
		rdStatic.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		rdKinematic.addMouseListener(new RadioButtonML(model, rdKinematic.getText()));
		typePhysicPanel.add(rdKinematic);
		rdKinematic.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		rdDinamic.addMouseListener(new RadioButtonML(model, rdDinamic.getText()));
		typePhysicPanel.add(rdDinamic);
		rdDinamic.setFont(new Font("Dialog", Font.PLAIN, 12));
		
		ButtonGroup typeGroup = new ButtonGroup();
		typeGroup.add(rdStatic);
		typeGroup.add(rdKinematic);
		typeGroup.add(rdDinamic);
	}

	private void inicializeTypePhysicPanel() {
		typePhysicPanel = new JPanel();
		typePhysicPanel.setBounds(5, 25, 272, 33);
		FlowLayout flowLayout_1 = (FlowLayout) typePhysicPanel.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEADING);
		bodyTypePanel.add(typePhysicPanel);
	}

	private void inicializePhysicTypePanel() {
		bodyTypePanel = new JPanel();
		bodyTypePanel.setBounds(0, 38, 451, 114);
		bodyTypePanel.setLayout(null);
		propertiesCollisionPanel.add(bodyTypePanel);
	}

	private void bodyFigure() 
	{
		this.inicializeBodyFigurePanel();
		
		lblBodyMaterial = new JLabel("Cuerpo:");
		bodyMaterialPanel.add(lblBodyMaterial);
		lblBodyMaterial.setFont(new Font("Dialog", Font.BOLD, 12));
	
		cboxSelectBody.setModel(new DefaultComboBoxModel(new Body[] {new Circle(), new Rectangle()}));
		cboxSelectBody.setFont(new Font("Dialog", Font.PLAIN, 12));
		cboxSelectBody.setSelectedItem(null);
		cboxSelectBody.addActionListener(new PhysicsBodyAL(model, cboxSelectBody));
		bodyMaterialPanel.add(cboxSelectBody);
	}

	private void inicializeBodyFigurePanel() {
		bodyMaterialPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) bodyMaterialPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEADING);
		bodyMaterialPanel.setBounds(0, 0, 451, 36);
		propertiesCollisionPanel.add(bodyMaterialPanel);
	}

	private void inicializePropertiesCollisionPanel() 
	{
		propertiesCollisionPanel = new JPanel();
		propertiesCollisionPanel.setBounds(5, 12, 463, 239);
		propertiesCollisionPanel.setLayout(null);
		collisionPanel.add(propertiesCollisionPanel);
	}

	private void inicializeTableCollisionPanel() 
	{
		tableCollisionPanel = new JPanel();
		tableCollisionPanel.setBorder(new TitledBorder(null, "Tabla de colisiones", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		tableCollisionPanel.setBounds(456, 0, 510, 263);
		tableCollisionPanel.setLayout(null);
		collisionPanel.add(tableCollisionPanel);
	}

	private void tableCollision() 
	{
		tableCollisionScrollPanel = new JScrollPane();
		tableCollisionScrollPanel.setBounds(5, 20, 500, 208);
		
		tableCollision.setAlignmentX(Component.LEFT_ALIGNMENT);
		tableCollision.addMouseListener(new SelectedCollitionActionListener(table, textArea, tableCollision, model));
		tableCollision.setModel(new DefaultTableModel(
			new Object[][] {},
			new String[] { "Nombre", "Descripci\u00F3n"}
		));
		
		tableCollisionScrollPanel.setViewportView(tableCollision);
		tableCollisionPanel.add(tableCollisionScrollPanel);
	}

	private void inicializeCollisionPanel() 
	{
		collisionPanel = new JPanel();
		collisionPanel.setPreferredSize(new Dimension(973, 263));
		collisionPanel.setLayout(null);
		tabbedPanel.addTab("Cuerpos", collisionPanel);
		tabbedPanel.setIconAt(1, new ImageIcon(EditorWindow.class.getResource("/com/sun/java/swing/plaf/windows/icons/HardDrive.gif")));
	}
	
	private void initializePropertiesEditPanel() 
	{
		this.inicializeOptionsEditionPanel();
		this.inicializeBehaviorSettingPanel();
	}

	private void inicializeOptionsEditionPanel() 
	{
		editorPanel.setLayout(new GridLayout(0, 2, 0, 0));
		propertiesEditPanel = new JPanel();
		propertiesEditPanel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Panel de configuraci\u00F3n", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		propertiesEditPanel.setPreferredSize(new Dimension(973, 35));
		this.editorPanel.add(propertiesEditPanel);
	}

	private void inicializeBehaviorSettingPanel() 
	{
		behaviorPanel = new JPanel();
		behaviorPanel.setBorder(new TitledBorder(new LineBorder(new Color(184, 207, 229)), "Habilidades y comportamientos", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		behaviorPanel.setLayout(null);
		editorPanel.add(behaviorPanel);
	}

	private void behaviorSettingPanel() 
	{
		tableBehaviorScrollPanel = new JScrollPane();
		tableBehaviorScrollPanel.setBounds(5, 23, 475, 195);
		behaviorPanel.add(tableBehaviorScrollPanel);
		
		this.inicializeTable();
		
		BehaviorButtonsPanel = new JPanel();
		BehaviorButtonsPanel.setBounds(5, 223, 475, 35);
		behaviorPanel.add(BehaviorButtonsPanel);
		
		btnNewBehavior = new JButton("Nuevo");
		btnNewBehavior.addActionListener(new BtnNewBehaviorActionListener(model, treeScenes, canvas, table));
		btnNewBehavior.setBounds(5, 5, 78, 25);
		btnNewBehavior.setHorizontalAlignment(SwingConstants.LEADING);
		BehaviorButtonsPanel.add(btnNewBehavior);
		
		btnGlobalBehavior = new JButton("Agregar globales");
		btnGlobalBehavior.setBounds(88, 5, 156, 25);
		btnGlobalBehavior.addActionListener(new BtnGlobalBehaviorsActionListener());
		btnGlobalBehavior.setHorizontalAlignment(SwingConstants.LEADING);
		BehaviorButtonsPanel.add(btnGlobalBehavior);
		
		btnDeleteBehavior = new JButton("Eliminar");
		btnDeleteBehavior.setBounds(373, 5, 90, 25);
		btnDeleteBehavior.addActionListener(new BtnRemoveBehaviorActionListener(textArea, treeScenes, canvas, table, principalPanel));
		btnDeleteBehavior.setHorizontalAlignment(SwingConstants.TRAILING);
		BehaviorButtonsPanel.setLayout(null);
		BehaviorButtonsPanel.add(btnDeleteBehavior);
	}

	private void inicializeTable() 
	{
		table.setBounds(0, 0, 225, 64);
		table.addMouseListener(new SelectedBehaviorFileActionListener(tableCollision, textArea, table, model));
		table.setModel(new DefaultTableModel(
			new Object[][] {},
			new String[] { "Nombre", "Descripci\u00F3n", "Global", "Tipo"}
		));
		tableBehaviorScrollPanel.setViewportView(table);
	}
	
	private void optionsEditorPanel() 
	{
		editName();
		editPosition();
		editImage();
		editDimension();
		editAudio();
	}

	private void editDimension() {
		inicializedEditDimensionPanel();
		
		lblDimension = new JLabel("Dimensiones:");
		lblDimension.setBounds(5, 29, 102, 15);
		panelEditDimension.add(lblDimension);
		
		lblErrorDimension = new JLabel("");
		lblErrorDimension.setBounds(117, 11, 226, 14);
		lblErrorDimension.setForeground(Color.RED);
		lblErrorDimension.setFont(new Font("Dialog", Font.PLAIN, 11));
		panelEditDimension.add(lblErrorDimension);

		textFieldWidth = new JTextField();
		textFieldWidth.setBounds(117, 26, 112, 19);
		textFieldWidth.setText("0");
		panelEditDimension.add(textFieldWidth);
		textFieldWidth.setColumns(10);
		
		textFieldHigh = new JTextField();
		textFieldHigh.setBounds(231, 26, 112, 19);
		textFieldHigh.setText("0");
		panelEditDimension.add(textFieldHigh);
		textFieldHigh.setColumns(10);
		
		btnEditDimension = new JButton("Editar");
		btnEditDimension.setBounds(349, 23, 76, 25);
		btnEditDimension.addActionListener(new BtnEditDimensionImageActionListener(treeScenes, canvas, textFieldWidth, textFieldHigh, model, lblErrorDimension));
		panelEditDimension.add(btnEditDimension);
		
	}

	private void editAudio() 
	{
		panelEditAudio = new JPanel();
		panelEditAudio.setBounds(5, 209, 473, 48);
		panelEditAudio.setLayout(null);
		propertiesEditPanel.add(panelEditAudio);
		
		JLabel lblAudio = new JLabel("Audio:");
		lblAudio.setBounds(5, 15, 45, 15);
		panelEditAudio.add(lblAudio);
		
		textFieldPathAudio.setEditable(false);
		textFieldPathAudio.setBounds(60, 12, 284, 19);
		textFieldPathAudio.setColumns(10);
		panelEditAudio.add(textFieldPathAudio);
		
		JButton btnSetAudio = new JButton("Audio...");
		btnSetAudio.setBounds(354, 10, 97, 25);
		btnSetAudio.addActionListener(new BtnOpenAudioActionListener(textFieldPathAudio,principalPanel, model));
		panelEditAudio.add(btnSetAudio);
	}

	private void inicializedEditDimensionPanel() {
		panelEditDimension = new JPanel();
		panelEditDimension.setLayout(null);
		panelEditDimension.setBounds(5, 161, 473, 48);
		propertiesEditPanel.add(panelEditDimension);
	}

	private void editImage() {
		inicializedEditImagePanel();
		panelEditImage.setLayout(null);
		
		lblImage = new JLabel("Imágen:");
		lblImage.setBounds(5, 28, 57, 15);
		panelEditImage.add(lblImage);
		
		textFieldPathImage = new JTextField();
		textFieldPathImage.setEditable(false);
		textFieldPathImage.setBounds(77, 26, 165, 19);
		panelEditImage.add(textFieldPathImage);
		textFieldPathImage.setColumns(10);
		
		btnSetImage = new JButton("Imagen...");
		btnSetImage.addActionListener(new BtnOpenImageActionListener(textFieldPathImage,principalPanel, btnEditImage));
		btnSetImage.setBounds(247, 23, 101, 25);
		panelEditImage.add(btnSetImage);
		
		btnEditImage = new JButton("Editar");
		btnEditImage.addActionListener(new BtnEditImageActionListener(treeScenes, canvas, textFieldPathImage, model));
		btnEditImage.setBounds(355, 23, 76, 25);
		panelEditImage.add(btnEditImage);
	}

	private void inicializedEditImagePanel() {
		panelEditImage = new JPanel();
		panelEditImage.setBounds(5, 113, 473, 48);
		propertiesEditPanel.add(panelEditImage);
	}

	private void editPosition() {
		inicializedEditPositionPanel();
		panelEditPosition.setLayout(null);
		
		JLabel lblPosition = new JLabel("Posición (x, y):");
		panelEditPosition.add(lblPosition);
		lblPosition.setBounds(5, 29, 102, 15);
		
		lblErrorNumber = new JLabel("");
		lblErrorNumber.setForeground(Color.RED);
		lblErrorNumber.setFont(new Font("Dialog", Font.PLAIN, 11));
		lblErrorNumber.setBounds(117, 11, 189, 14);
		panelEditPosition.add(lblErrorNumber);
		
		posXTextField = new JTextField("0");
		posXTextField.addActionListener(new BtnEditPositionAL(treeScenes,posXTextField, posYTextField, canvas, model, lblErrorNumber));
		panelEditPosition.add(posXTextField);
		posXTextField.setBounds(117, 26, 112, 19);
		posXTextField.setColumns(5);
		
		posYTextField = new JTextField("0");
		posYTextField.addActionListener(new BtnEditPositionAL(treeScenes,posXTextField, posYTextField, canvas, model, lblErrorNumber));
		panelEditPosition.add(posYTextField);
		posYTextField.setBounds(231, 26, 112, 19);
		posYTextField.setColumns(5);
		
		JButton btnEditPosition = new JButton("Editar");
		panelEditPosition.add(btnEditPosition);
		btnEditPosition.setBounds(349, 23, 76, 25);
		btnEditPosition.addActionListener(new BtnEditPositionAL(treeScenes,posXTextField, posYTextField, canvas, model, lblErrorNumber));

	}

	private void inicializedEditPositionPanel() {
		panelEditPosition = new JPanel();
		panelEditPosition.setBounds(5, 64, 473, 48);
		propertiesEditPanel.add(panelEditPosition);
	}

	private void editName() {
		inicializedEditNamePanel();
		editNamePanel.setLayout(null);
		
		JLabel lblName = new JLabel("Nombre:");
		lblName.setBounds(5, 25, 72, 15);
		editNamePanel.add(lblName);
		
		JLabel lblErrorName = new JLabel("");
		lblErrorName.setForeground(Color.RED);
		lblErrorName.setFont(new Font("Dialog", Font.PLAIN, 11));
		lblErrorName.setBounds(87, 5, 250, 14);
		editNamePanel.add(lblErrorName);

		nameTextField = new JTextField("");
		nameTextField.setBounds(87, 21, 150, 20);
		nameTextField.setColumns(10);
		nameTextField.addActionListener(new BtnEditNameAL(treeScenes, nameTextField, canvas, model, lblErrorName));
		editNamePanel.add(nameTextField);
		
	}

	private void inicializedEditNamePanel() 
	{
		propertiesEditPanel.setLayout(null);
		editNamePanel = new JPanel();
		editNamePanel.setLayout(null);
		editNamePanel.setBounds(5, 22, 473, 43);
		editNamePanel.setPreferredSize(new Dimension(973, 228));
		propertiesEditPanel.add(editNamePanel);
	}

	private void initializeTreePlayPanel() {
		BorderLayout bl_treePlayPanel = new BorderLayout();
		bl_treePlayPanel.setHgap(5);
		bl_treePlayPanel.setVgap(5);
		treePlayPanel = new JPanel(bl_treePlayPanel);
		treePlayPanel.setPreferredSize(new Dimension(973, 400));
		this.gameEditorPanel.add(treePlayPanel, BorderLayout.WEST);
	}

	private void initializeGameEditorPanel() {
		BorderLayout bl_gameEditorPanel = new BorderLayout();
		bl_gameEditorPanel.setVgap(5);
		bl_gameEditorPanel.setHgap(5);
		gameEditorPanel = new JPanel(bl_gameEditorPanel);
		gameEditorPanel.setPreferredSize(new Dimension(973, 663));
		this.centerPanel.add(gameEditorPanel, BorderLayout.CENTER);
	}

	private void initializeTextPanel() {
		eastPanel = new JScrollPane(this.textArea);
		eastPanel.setPreferredSize(new Dimension (393, 663) );
		this.centerPanel.add(eastPanel, BorderLayout.EAST);
	}
	
	private void initializeNorthPanel() {
		northPanel = new JPanel(new BorderLayout());
		northPanel.setPreferredSize(new Dimension(resolution.width, 40) );
		this.principalPanel.add(northPanel, BorderLayout.NORTH);
	}
	
	private void initializeCenterPanel() {
		centerPanel = new JPanel(new BorderLayout());
		centerPanel.setPreferredSize(new Dimension(resolution.width, 663));
		this.principalPanel.add(centerPanel, BorderLayout.CENTER);
	}
	
	@Override
	protected void open() {
		super.open();
		if(this.getFrame().isVisible()) { this.canvas.onFrameVisible(); }
	}
}
