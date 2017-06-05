package backend.libraries;

import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Stack;

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
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 * JPanel and methods for a JTree
 * 
 * @author Niklas | https://github.com/AnonymerNiklasistanonym
 * @version 0.7 (beta)
 */
public class FileTree extends JPanel {

	private static final long serialVersionUID = 1L;

	private DefaultMutableTreeNode rootNode;
	private DefaultTreeModel treeModel;
	private JTree tree;

	public FileTree() {
		// layout for a full height/width JTree
		super(new GridLayout(1, 0));

		rootNode = new DefaultMutableTreeNode("Root Node");
		treeModel = new DefaultTreeModel(rootNode);
		treeModel.addTreeModelListener(new MyTreeModelListener());
		tree = new JTree(treeModel);
		tree.setEditable(false);
		// tree.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
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
				if (getSelectedNodePath() != null)
					FileTreeWindow.setLabel(" >> " + getSelectedNodePath().toString());
			}
		});
	}

	public Path[] getCurrentSelectedPaths() {

		ArrayList<Path> stackWithAllPaths = new ArrayList<Path>();
		Stack<DefaultMutableTreeNode> stackWithAllNodes = new Stack<DefaultMutableTreeNode>();
		stackWithAllNodes.add((DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent());

		while (!stackWithAllNodes.isEmpty()) {
			DefaultMutableTreeNode a = stackWithAllNodes.pop();

			String filePath = "";
			for (int i = 1; i < a.getPath().length; i++) {
				filePath += a.getPath()[i];
				System.out.println(filePath);
				if (i + 1 < a.getPath().length) {
					filePath += File.separator;
				}
			}

			stackWithAllPaths.add(Paths.get(filePath));

			for (int i = 0; i < a.getChildCount(); i++) {
				stackWithAllNodes.add((DefaultMutableTreeNode) a.getChildAt(i));
			}
		}

		return stackWithAllPaths.toArray(new Path[stackWithAllPaths.size()]);
	}

	public Path getSelectedNodePath() {
		String jTreeVarSelectedPath = "";
		try {
			Object[] paths = tree.getSelectionPath().getPath();
			for (int i = 1; i < paths.length; i++) {
				jTreeVarSelectedPath += paths[i];
				System.out.println(jTreeVarSelectedPath);
				if (i + 1 < paths.length) {
					jTreeVarSelectedPath += File.separator;
				}
			}
			return Paths.get(jTreeVarSelectedPath);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Removes all nodes (but not the root node)
	 */
	public void clear() {
		rootNode.removeAllChildren();
		treeModel.reload();
	}

	/** Add child to the currently selected node. */
	/**
	 * Add a node to the JTree
	 * 
	 * @param child
	 *            (Object
	 * @return
	 */
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

		// check if the parent is null - if yes the root node is the parent
		if (parent == null)
			parent = rootNode;

		// add note to the JTree
		treeModel.insertNodeInto(childNode, parent, parent.getChildCount());

		// if wanted make the node visible
		if (shouldBeVisible)
			tree.scrollPathToVisible(new TreePath(childNode.getPath()));

		return childNode;
	}

	public DefaultMutableTreeNode searchNode(String nodeStr, String rootF) {
		return searchNode(nodeStr, rootF, true);
	}

	public DefaultMutableTreeNode searchNode(String nodeStr) {
		return searchNode(nodeStr, null, false);
	}

	public DefaultMutableTreeNode searchNode(String nodeStr, String rootF, boolean advanced) {

		if (advanced) {
			// System.out.println("Search for: " + nodeStr + " with the
			// \"root\": " + rootF);
		} else {
			// System.out.println("Search for: " + nodeStr);
		}
		DefaultMutableTreeNode node = null;

		@SuppressWarnings("unchecked")
		Enumeration<DefaultMutableTreeNode> e = rootNode.breadthFirstEnumeration();

		while (e.hasMoreElements()) {
			node = e.nextElement();
			if (nodeStr.equals(node.getUserObject().toString())) {

				if (advanced) {
					// System.out.println("Found a node with the same name: " +
					// node.getUserObject().toString());

					DefaultMutableTreeNode walkinNode = (DefaultMutableTreeNode) node;
					while (walkinNode.getParent() != null && walkinNode.getParent() != rootNode) {

						walkinNode = (DefaultMutableTreeNode) walkinNode.getParent();

						// System.out.print(walkinNode.getUserObject().toString()
						// + ",");
					}

					if (!rootF.equals(walkinNode.getUserObject().toString())) {
						// System.out.println(walkinNode.getUserObject().toString()
						// + " != " + rootF);
						// System.out.println("Root nooot found");
					} else {
						// System.out.println("Root already found");
						return node;
					}
				} else {
					return node;
				}
			}
		}
		System.out.println("Create new Node!");
		return null;
	}

	class MyTreeModelListener implements TreeModelListener {

		public void treeNodeSelected(TreeModelEvent e) {
			// System.out.println(e.getChildIndices()[0]);
		}

		public void treeNodesChanged(TreeModelEvent e) {
		}

		public void treeNodesInserted(TreeModelEvent e) {
			// uncomment this if you want to expand the source folders:
			// tree.expandPath(e.getTreePath());

			// select atomically the new node
			tree.setSelectionPath(e.getTreePath());
		}

		public void treeNodesRemoved(TreeModelEvent e) {
		}

		public void treeStructureChanged(TreeModelEvent e) {
		}
	}
}
