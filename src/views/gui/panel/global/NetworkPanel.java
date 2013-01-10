package views.gui.panel.global;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import listeners.BackToMenuListener;

import views.gui.panel.BasicPanel;
import views.gui.panel.Constant;
import views.gui.panel.LoungeTreeNode;
import views.gui.threads.RefreshLoungeList;
import controller.MainController;
import events.LoungeFoundEvent;

public class NetworkPanel extends BasicPanel {

	// ------------ ATTRIBUTES ------------ //
	private static final long serialVersionUID = 7321248751774786656L;

	private final int OFFSET = 20;	
	
	private ArrayList<LoungeTreeNode> map;
	
	protected DefaultMutableTreeNode rootNode;
    protected DefaultTreeModel treeModel;
    protected JTree tree;
	
	private JPanel loungeListPanel;
	private JPanel menuPanel;
	
	private JTree treeElement = null;
	
	private JButton backToMainMenu = new JButton("Back to main menu.");
	private JButton joinGame = new JButton("Join the game.");
	private JTextField textField = new JTextField();
	
	
	// ------------ CONSTRUCTORS ------------ //
	public NetworkPanel(GraphicsDevice d, MainController c) {
		super(d, c);
		this.initElements();
	}
	
	
	// ------------ METHODS ------------ //
	@Override
	public void initElements() {
		
		this.setLayout(new BorderLayout());
		this.setBounds(0,0,this.getScreenDimension().width, this.getScreenDimension().height);
		this.setBackground(Constant.SKYCOLOR);
		
		
		this.rootNode = new DefaultMutableTreeNode("List of found servers");
        this.treeModel = new DefaultTreeModel(this.rootNode);
		this.tree = new JTree(this.treeModel);
        this.tree.setEditable(false);
        this.tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        this.tree.setShowsRootHandles(true);
		
		this.map = new ArrayList<LoungeTreeNode>();
		
		this.setLoungeListPanel();
		this.setMenuPanel();
		this.setListeners();
		
		RefreshLoungeList t = new RefreshLoungeList(this);
	}
	
	public void setTrees() {
		
		this.treeElement = new JTree(this.treeModel);
		
		this.editTreeStructure();
		
		for (int j = 0; j < this.treeElement.getRowCount() ; j++) {
			this.treeElement.expandRow(j);
		}
		
		JPanel t = new JPanel();
		t.setLayout(new BorderLayout());
		t.add(this.treeElement, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane(tree);
		
		this.loungeListPanel.add(scrollPane, BorderLayout.CENTER);
		this.loungeListPanel.validate();
	}
	
	private void setLoungeListPanel() {
	
		this.loungeListPanel = new JPanel();
		this.loungeListPanel.setLayout(new BorderLayout());
		this.loungeListPanel.setBackground(Color.gray);
		this.setTrees();
		
		this.add(this.loungeListPanel, BorderLayout.CENTER);
	}
	
	private void setMenuPanel() {
		
		this.menuPanel = new JPanel();
		this.menuPanel.setLayout(new GridLayout(1,1));
		
		this.menuPanel.add(this.backToMainMenu);
		
		this.add(this.menuPanel, BorderLayout.SOUTH);
	}

	@Override
	public void animate() {
		// TODO Auto-generated method stub
	}

	@Override
	public void setListeners() {
		
		MouseListener ml = new MouseAdapter() {
		     public void mousePressed(MouseEvent e) {
		         int selRow = tree.getRowForLocation(e.getX(), e.getY());
		         TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
		         if(selRow != -1) {
		             if(e.getClickCount() == 2) {
		            	 LoungeTreeNode selectedNode = (LoungeTreeNode) selPath.getLastPathComponent();
		            	 controller.initializeMultiplayerGame(selectedNode.getIpAdress(), selectedNode.getPort(), selectedNode.getLoungeName());
		             }
		         }
		     }
		 };
		 
		this.tree.addMouseListener(ml);
		this.backToMainMenu.addActionListener(new BackToMenuListener(controller));
	}
	
	
	public void editLoungeList(LoungeFoundEvent e) {

		LoungeTreeNode newLounge = new LoungeTreeNode(e.getIpAdress(), e.getPort(), e.getLoungeName());
		
		if(!this.map.contains(newLounge)) {
			this.map.add(newLounge);
		}
		else {
			this.map.get(this.map.lastIndexOf(newLounge)).initialiseTimer();
		}
		
		this.editTreeStructure();
	}
	
	public void editTreeStructure() {
		
		LinkedList<LoungeTreeNode> removableLounges = new LinkedList<LoungeTreeNode>();
	
		for (LoungeTreeNode lounge : this.map) {
			if(!lounge.isDisplayed()) {
				this.treeModel.insertNodeInto(lounge, this.rootNode, this.rootNode.getChildCount());
				lounge.setDisplayed(true);
			}
			else {
				if(!lounge.isStillValid()) {
					lounge.removeFromParent();
					removableLounges.add(lounge);
				}
			}
		}
		
		for (LoungeTreeNode loungeTreeNode : removableLounges) {
			this.map.remove(loungeTreeNode);
		}
		
		this.treeModel.reload();
			
	}
}
