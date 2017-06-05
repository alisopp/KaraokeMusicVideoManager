package backend.libraries;

import java.awt.GridLayout;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

public class FileTree extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected DefaultMutableTreeNode rootNode;
	protected DefaultTreeModel treeModel;
	protected JTree tree;
	private Toolkit toolkit = Toolkit.getDefaultToolkit();

	public FileTree() {
		// layout for a full height/width JTree
		super(new GridLayout(1, 0));

		rootNode = new DefaultMutableTreeNode("Root Node");
		treeModel = new DefaultTreeModel(rootNode);
		treeModel.addTreeModelListener(new MyTreeModelListener());
		tree = new JTree(treeModel);
		tree.setEditable(false);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
		// tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		// root should not be visible
		tree.setRootVisible(false);
		// expand the root node
		tree.expandPath(new TreePath(rootNode.getPath()));

		// scroll pane for the JTree
		JScrollPane scrollPane = new JScrollPane(tree);
		add(scrollPane);

		// try to set a custom leaf image
		try {
			ImageIcon imageIcon = null;

			imageIcon = new ImageIcon(ImageIO.read(FileTree.class.getResource("/tree_symbol_music_video.png")));

			DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();

			if (imageIcon != null)
				renderer.setLeafIcon(imageIcon);

			tree.setCellRenderer(renderer);

		} catch (IOException e) {
			e.printStackTrace();
		}

		tree.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {

				for (TreePath a : e.getPaths()) {
					System.out.println(a.toString());

				}
			}
		});
	}

	public Path getSelectedNodePath() {
		String jTreeVarSelectedPath = "";
		Object[] paths = tree.getSelectionPath().getPath();
		for (int i = 1; i < paths.length; i++) {
			jTreeVarSelectedPath += paths[i];
			System.out.println(jTreeVarSelectedPath);
			if (i + 1 < paths.length) {
				jTreeVarSelectedPath += File.separator;
			}
		}
		System.out.println(jTreeVarSelectedPath);

		return Paths.get(jTreeVarSelectedPath);
	}

	/**
	 * Removes all nodes (but not the root node)
	 */
	public void clear() {
		rootNode.removeAllChildren();
		treeModel.reload();
	}

	/** Remove the currently selected node. */
	public void removeCurrentNode() {

		for (TreePath currentSelection : tree.getSelectionPaths()) {
			if (currentSelection != null) {
				DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) (currentSelection.getLastPathComponent());
				MutableTreeNode parent = (MutableTreeNode) (currentNode.getParent());
				if (parent != null) {
					treeModel.removeNodeFromParent(currentNode);
					return;
				} else {
					currentNode.setAllowsChildren(false);
				}
			}
		}
		toolkit.beep();
	}

	/** Add child to the currently selected node. */
	public DefaultMutableTreeNode addObject(Object child) {
		DefaultMutableTreeNode parentNode = null;
		TreePath parentPath = tree.getSelectionPath();

		if (parentPath == null) {
			parentNode = rootNode;
		} else {
			parentNode = (DefaultMutableTreeNode) (parentPath.getLastPathComponent());
		}

		return addObject(parentNode, child, true);
	}

	public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, Object child) {
		return addObject(parent, child, false);
	}

	public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, Object child, boolean shouldBeVisible) {
		DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);

		if (parent == null) {
			parent = rootNode;
		}

		// It is key to invoke this on the TreeModel, and NOT
		// DefaultMutableTreeNode
		treeModel.insertNodeInto(childNode, parent, parent.getChildCount());

		// Make sure the user can see the lovely new node.
		if (shouldBeVisible) {
			tree.scrollPathToVisible(new TreePath(childNode.getPath()));
		}
		return childNode;
	}

	public DefaultMutableTreeNode searchNode(String nodeStr, String rootF) {
		System.out.println("Search for: " + nodeStr + " with " + rootF);
		DefaultMutableTreeNode node = null;
		Enumeration<DefaultMutableTreeNode> e = rootNode.breadthFirstEnumeration();

		while (e.hasMoreElements()) {
			node = e.nextElement();
			if (nodeStr.equals(node.getUserObject().toString())) {

				System.out.println("Found a node with the same name: " + node.getUserObject().toString());

				DefaultMutableTreeNode walkinNode = (DefaultMutableTreeNode) node;
				while (walkinNode.getParent() != null && walkinNode.getParent() != rootNode) {

					walkinNode = (DefaultMutableTreeNode) walkinNode.getParent();

					System.out.print(walkinNode.getUserObject().toString() + ",");
				}

				if (!rootF.equals(walkinNode.getUserObject().toString())) {
					System.out.println(walkinNode.getUserObject().toString() + " != " + rootF);
					System.out.println("Root nooot found");
				} else {
					System.out.println("Root already found");
					return node;
				}
			}
		}
		System.out.println("create new Node!!!");
		return null;
	}

	public DefaultMutableTreeNode searchNode(String nodeStr) {
		DefaultMutableTreeNode node = null;
		@SuppressWarnings("unchecked")
		Enumeration<DefaultMutableTreeNode> e = rootNode.breadthFirstEnumeration();
		while (e.hasMoreElements()) {
			node = e.nextElement();
			if (nodeStr.equals(node.getUserObject().toString())) {
				return node;
			}
		}
		return null;
	}

	class MyTreeModelListener implements TreeModelListener {

		public void treeNodeSelected(TreeModelEvent e) {
			System.out.println(e.getChildIndices()[0]);
		}

		public void treeNodesChanged(TreeModelEvent e) {
			DefaultMutableTreeNode node;
			node = (DefaultMutableTreeNode) (e.getTreePath().getLastPathComponent());

			/*
			 * If the event lists children, then the changed node is the child
			 * of the node we've already gotten. Otherwise, the changed node and
			 * the specified node are the same.
			 */

			int index = e.getChildIndices()[0];
			node = (DefaultMutableTreeNode) (node.getChildAt(index));

			System.out.println("The user has finished editing the node.");
			System.out.println("New value: " + node.getUserObject());
		}

		public void treeNodesInserted(TreeModelEvent e) {
			// tree.expandPath(e.getTreePath());
			// second option
			tree.setSelectionPath(e.getTreePath());
		}

		public void treeNodesRemoved(TreeModelEvent e) {
		}

		public void treeStructureChanged(TreeModelEvent e) {
		}
	}
}
