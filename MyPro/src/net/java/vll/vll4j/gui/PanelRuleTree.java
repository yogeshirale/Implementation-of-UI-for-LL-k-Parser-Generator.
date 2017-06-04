/*     */ package net.java.vll.vll4j.gui;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.swing.AbstractAction;
/*     */ import javax.swing.Action;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JCheckBoxMenuItem;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTree;
/*     */ import javax.swing.ToolTipManager;
/*     */ import javax.swing.event.TreeSelectionEvent;
/*     */ import javax.swing.tree.DefaultTreeModel;
/*     */ import javax.swing.tree.TreePath;
/*     */ import net.java.vll.vll4j.api.Forest;
/*     */ import net.java.vll.vll4j.api.Multiplicity;
/*     */ import net.java.vll.vll4j.api.NodeBase;
/*     */ import net.java.vll.vll4j.api.NodeChoice;
/*     */ import net.java.vll.vll4j.api.NodeLiteral;
/*     */ import net.java.vll.vll4j.api.NodeReference;
/*     */ import net.java.vll.vll4j.api.NodeRegex;
/*     */ import net.java.vll.vll4j.api.NodeRepSep;
/*     */ import net.java.vll.vll4j.api.NodeRoot;
/*     */ import net.java.vll.vll4j.api.NodeSemPred;
/*     */ import net.java.vll.vll4j.api.NodeSequence;
/*     */ import net.java.vll.vll4j.api.NodeWildCard;
/*     */ 
/*     */ public class PanelRuleTree extends JPanel implements javax.swing.event.TreeSelectionListener
/*     */ {
/*     */   PanelRuleTree(Vll4jGui gui)
  {
    setLayout(new BorderLayout());
    this.gui = gui;
    this.selectedNode = (this.rootNode = (NodeRoot)gui.theForest.ruleBank.get("Main"));
    this.theModel = new DefaultTreeModel(this.rootNode);
    this.theTree = new JTree(this.theModel);
    ToolTipManager.sharedInstance().registerComponent(this.theTree);
    this.theTree.setDragEnabled(true);
    this.theTree.setTransferHandler(new TransferHandlerTree());
    this.theTree.setSelectionRow(0);
    this.theTree.getSelectionModel().setSelectionMode(1);
    this.theTree.setDragEnabled(true);
    this.theTree.setDropMode(javax.swing.DropMode.INSERT);
    this.theTree.setTransferHandler(new TransferHandlerTree());
    this.theTree.addTreeSelectionListener(this);
    this.theTree.addMouseListener(this.treePopupListener);
    this.theTree.setShowsRootHandles(true);
    this.theTree.setCellRenderer(new RendererRuleNode());
    add(new javax.swing.JScrollPane(this.theTree), "Center");
    this.treePopupMenu = new PopupMenuTree(this);
    JPanel btnPanel = new JPanel();
    btnPanel.setLayout(new BorderLayout());
    btnPanel.add(this.statusLabel, "Center");
    /*
    this.helpButton = new JButton(gui.theHelpFunctionsManager.displayHelpRuleTree)
    {
      public Dimension getPreferredSize() 
      {
        int h = super.getPreferredSize().height;
        return new Dimension(h, h);
      }
      
      public Dimension getMinimumSize() 
      {
        return getPreferredSize();
      }
    };
    */
   // btnPanel.add(this.helpButton, "East");
    add(btnPanel, "South");
  }
/*     */   void setRuleName(String ruleName) {
/*  77 */     this.selectedNode = ((NodeBase)this.gui.theForest.ruleBank.get(ruleName));
/*  78 */     this.rootNode = ((NodeRoot)this.selectedNode);
/*  79 */     javax.swing.tree.TreeNode[] p = this.selectedNode.getPath();
/*  80 */     TreePath[] tp = new TreePath[p.length];
/*  81 */     for (int i = 0; i < tp.length; i++)
/*  82 */       tp[i] = new TreePath(p[i]);
/*  83 */     this.theTree.removeTreeSelectionListener(this);
/*  84 */     this.theModel.setRoot(this.rootNode);
/*  85 */     this.theTree.setSelectionPaths(tp);
/*  86 */     this.theTree.addTreeSelectionListener(this);
/*  87 */     this.theModel.nodeChanged(this.selectedNode);
/*  88 */     for (int i = 0; i < this.theTree.getRowCount(); i++) {
/*  89 */       this.theTree.expandRow(i);
/*     */     }
/*  91 */     this.gui.theAstPanel.resetView();
/*  92 */     this.gui.theActionCodePanel.resetView();
/*  93 */     this.statusLabel.setText(String.format(" %s/%s", new Object[] { this.selectedNode.nodeType(), this.selectedNode.nodeName() }));
/*     */   }
/*     */   
/*     */   void resetNodeDisplay(final NodeBase node) {
/*  97 */     javax.swing.SwingUtilities.invokeLater(new Runnable()
/*     */     {
/*     */       public void run() {
/* 100 */         PanelRuleTree.this.theModel.nodeChanged(PanelRuleTree.this.selectedNode);
/* 101 */         if (PanelRuleTree.this.associatedNode != null) {
/* 102 */           PanelRuleTree.this.theModel.nodeChanged(PanelRuleTree.this.associatedNode);
/* 103 */           PanelRuleTree.this.associatedNode = null;
/*     */         }
/* 105 */         PanelRuleTree.this.theTree.expandPath(new TreePath(PanelRuleTree.this.selectedNode.getPath()));
/* 106 */         PanelRuleTree.this.theTree.scrollPathToVisible(new TreePath(node));
/* 107 */         PanelRuleTree.this.gui.theAstPanel.resetView();
/*     */       }
/*     */     });
/*     */   }
/*     */   
/* 112 */   Action addChoiceAction = new AbstractAction("Choice", Resources.choice)
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 115 */       NodeChoice newNode = new NodeChoice();
/* 116 */       PanelRuleTree.this.theModel.insertNodeInto(newNode, PanelRuleTree.this.selectedNode, PanelRuleTree.this.selectedNode.getChildCount());
/* 117 */       PanelRuleTree.this.resetNodeDisplay(newNode);
/*     */     }
/*     */   };
/*     */   
/* 121 */   Action addSequenceAction = new AbstractAction("Sequence", Resources.sequence)
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 124 */       NodeSequence newNode = new NodeSequence();
/* 125 */       PanelRuleTree.this.theModel.insertNodeInto(newNode, PanelRuleTree.this.selectedNode, PanelRuleTree.this.selectedNode.getChildCount());
/* 126 */       PanelRuleTree.this.resetNodeDisplay(newNode);
/*     */     }
/*     */   };
/*     */   
/* 130 */   Action addRepSepAction = new AbstractAction("RepSep", Resources.repSep)
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 133 */       NodeRepSep newNode = new NodeRepSep();
/* 134 */       PanelRuleTree.this.theModel.insertNodeInto(newNode, PanelRuleTree.this.selectedNode, PanelRuleTree.this.selectedNode.getChildCount());
/* 135 */       PanelRuleTree.this.resetNodeDisplay(newNode);
/*     */     }
/*     */   };
/*     */   
/* 139 */   Action addSemPredAction = new AbstractAction("SemPred", Resources.predicate)
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 142 */       NodeSemPred newNode = new NodeSemPred();
/* 143 */       PanelRuleTree.this.theModel.insertNodeInto(newNode, PanelRuleTree.this.selectedNode, PanelRuleTree.this.selectedNode.getChildCount());
/* 144 */       PanelRuleTree.this.resetNodeDisplay(newNode);
/*     */     }
/*     */   };
/*     */   
/* 148 */   Action addWildCardAction = new AbstractAction("WildCard", Resources.wildCard)
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 151 */       NodeWildCard newNode = new NodeWildCard();
/* 152 */       PanelRuleTree.this.theModel.insertNodeInto(newNode, PanelRuleTree.this.selectedNode, PanelRuleTree.this.selectedNode.getChildCount());
/* 153 */       PanelRuleTree.this.resetNodeDisplay(newNode);
/*     */     }
/*     */   };
/*     */   
/* 157 */   Action goToAction = new AbstractAction("Go to")
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 160 */       if ((PanelRuleTree.this.selectedNode instanceof NodeReference)) {
/* 161 */         String ruleName = ((NodeReference)PanelRuleTree.this.selectedNode).refRuleName;
/* 162 */         PanelRuleTree.this.gui.theRuleManager.theComboBox.setSelectedItem(ruleName);
/* 163 */       } else if ((PanelRuleTree.this.selectedNode instanceof NodeLiteral)) {
/* 164 */         String ruleName = ((NodeLiteral)PanelRuleTree.this.selectedNode).literalName;
/* 165 */         PanelRuleTree.this.gui.theTokenManager.editToken(ruleName);
/* 166 */       } else if ((PanelRuleTree.this.selectedNode instanceof NodeRegex)) {
/* 167 */         String ruleName = ((NodeRegex)PanelRuleTree.this.selectedNode).regexName;
/* 168 */         PanelRuleTree.this.gui.theTokenManager.editToken(ruleName);
/*     */       }
/*     */     }
/*     */   };
/*     */   
/* 173 */   Action addTokenAction = new AbstractAction("Token", Resources.token)
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 176 */       Object[] names = PanelRuleTree.this.gui.theForest.tokenBank.keySet().toArray();
/* 177 */       if (names.length == 0) {
/* 178 */         JOptionPane.showMessageDialog(PanelRuleTree.this.treePopupMenu, "No tokens defined yet", "ERROR - Add token", 0);
/* 179 */         return;
/*     */       }
/* 181 */       String token = (String)JOptionPane.showInputDialog(PanelRuleTree.this.gui, "Select token", "Add token", 3, null, names, names[0]);
/*     */       
/* 183 */       if (token == null)
/* 184 */         return;
/* 185 */       String pattern = (String)PanelRuleTree.this.gui.theForest.tokenBank.get(token);
/* 186 */       boolean isRegex = pattern.charAt(0) == 'R';
/* 187 */       NodeBase newNode = isRegex ? new NodeRegex(token) : new NodeLiteral(token);
/* 188 */       PanelRuleTree.this.theModel.insertNodeInto(newNode, PanelRuleTree.this.selectedNode, PanelRuleTree.this.selectedNode.getChildCount());
/* 189 */       PanelRuleTree.this.resetNodeDisplay(newNode);
/*     */     }
/*     */   };
/*     */   
/* 193 */   Action addReferenceAction = new AbstractAction("Reference", Resources.reference)
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 196 */       Object[] names = PanelRuleTree.this.gui.theForest.ruleBank.keySet().toArray();
/* 197 */       String rule = (String)JOptionPane.showInputDialog(PanelRuleTree.this.gui, "Select rule", "Add reference", 3, null, names, names[0]);
/*     */       
/* 199 */       if (rule == null)
/* 200 */         return;
/* 201 */       NodeReference newNode = new NodeReference(rule);
/* 202 */       PanelRuleTree.this.theModel.insertNodeInto(newNode, PanelRuleTree.this.selectedNode, PanelRuleTree.this.selectedNode.getChildCount());
/* 203 */       PanelRuleTree.this.resetNodeDisplay(newNode);
/*     */     }
/*     */   };
/*     */   
/* 207 */   Action multiplicityOneAction = new AbstractAction("1 (just one)")
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 210 */       PanelRuleTree.this.selectedNode.multiplicity = Multiplicity.One;
/* 211 */       PanelRuleTree.this.resetNodeDisplay(PanelRuleTree.this.selectedNode);
/*     */     }
/*     */   };
/*     */   
/* 215 */   Action multiplicityZeroOneAction = new AbstractAction("? (0 or 1)")
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 218 */       PanelRuleTree.this.selectedNode.multiplicity = Multiplicity.ZeroOrOne;
/* 219 */       PanelRuleTree.this.resetNodeDisplay(PanelRuleTree.this.selectedNode);
/*     */     }
/*     */   };
/*     */   
/* 223 */   Action multiplicityZeroMoreAction = new AbstractAction("* (0 or more)")
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 226 */       PanelRuleTree.this.selectedNode.multiplicity = Multiplicity.ZeroOrMore;
/* 227 */       PanelRuleTree.this.resetNodeDisplay(PanelRuleTree.this.selectedNode);
/*     */     }
/*     */   };
/*     */   
/* 231 */   Action multiplicityOneMoreAction = new AbstractAction("+ (1 or more)")
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 234 */       PanelRuleTree.this.selectedNode.multiplicity = Multiplicity.OneOrMore;
/* 235 */       PanelRuleTree.this.resetNodeDisplay(PanelRuleTree.this.selectedNode);
/*     */     }
/*     */   };
/*     */   
/* 239 */   Action multiplicityNotAction = new AbstractAction("0 (not)")
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 242 */       PanelRuleTree.this.selectedNode.multiplicity = Multiplicity.Not;
/* 243 */       PanelRuleTree.this.resetNodeDisplay(PanelRuleTree.this.selectedNode);
/*     */     }
/*     */   };
/*     */   
/* 247 */   Action multiplicityGuardAction = new AbstractAction("= (guard)")
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 250 */       PanelRuleTree.this.selectedNode.multiplicity = Multiplicity.Guard;
/* 251 */       PanelRuleTree.this.resetNodeDisplay(PanelRuleTree.this.selectedNode);
/*     */     }
/*     */   };
/*     */   
/* 255 */   Action cutAction = new AbstractAction("Cut", Resources.cut16)
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 258 */       NodeBase parent = (NodeBase)PanelRuleTree.this.selectedNode.getParent();
/* 259 */       TreePath parentPath = PanelRuleTree.this.theTree.getSelectionPath().getParentPath();
/* 260 */       NodeBase nodeToCut = PanelRuleTree.this.selectedNode;
/* 261 */       PanelRuleTree.this.theClipBoard = nodeToCut;
/* 262 */       PanelRuleTree.this.theClipBoard.isDropped = false;
/* 263 */       PanelRuleTree.this.theClipBoard.isTraced = false;
/* 264 */       PanelRuleTree.this.theTree.setSelectionPath(parentPath);
/* 265 */       PanelRuleTree.this.theModel.removeNodeFromParent(nodeToCut);
/* 266 */       PanelRuleTree.this.resetNodeDisplay(parent);
/*     */     }
/*     */   };
/*     */   
/* 270 */   Action copyAction = new AbstractAction("Copy", Resources.copy16)
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 273 */       PanelRuleTree.this.theClipBoard = ((NodeBase)PanelRuleTree.this.selectedNode.clone());
/* 274 */       PanelRuleTree.this.theClipBoard.isDropped = false;
/* 275 */       PanelRuleTree.this.theClipBoard.isTraced = false;
/*     */     }
/*     */   };
/*     */   
/* 279 */   Action pasteAction = new AbstractAction("Paste", Resources.paste16)
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 282 */       NodeBase newNode = (NodeBase)PanelRuleTree.this.theClipBoard.clone();
/* 283 */       PanelRuleTree.this.theModel.insertNodeInto(newNode, PanelRuleTree.this.selectedNode, PanelRuleTree.this.selectedNode.getChildCount());
/* 284 */       PanelRuleTree.this.resetNodeDisplay(PanelRuleTree.this.selectedNode);
/*     */     }
/*     */   };
/*     */   
/* 288 */   Action deleteAction = new AbstractAction("Delete", Resources.delete16)
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 291 */       NodeBase parent = (NodeBase)PanelRuleTree.this.selectedNode.getParent();
/* 292 */       TreePath parentPath = PanelRuleTree.this.theTree.getSelectionPath().getParentPath();
/* 293 */       PanelRuleTree.this.theModel.removeNodeFromParent(PanelRuleTree.this.selectedNode);
/* 294 */       PanelRuleTree.this.theTree.setSelectionPath(parentPath);
/* 295 */       PanelRuleTree.this.resetNodeDisplay(parent);
/*     */     }
/*     */   };
/*     */   
/* 299 */   Action packratAction = new AbstractAction("Packrat")
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 302 */       ((NodeRoot)PanelRuleTree.this.selectedNode).isPackrat = ((JCheckBoxMenuItem)e.getSource()).isSelected();
/* 303 */       PanelRuleTree.this.resetNodeDisplay(PanelRuleTree.this.selectedNode);
/*     */     }
/*     */   };
/*     */   
/* 307 */   Action traceAction = new AbstractAction("Trace")
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 310 */       PanelRuleTree.this.selectedNode.isTraced = ((JCheckBoxMenuItem)e.getSource()).isSelected();
/* 311 */       PanelRuleTree.this.resetNodeDisplay(PanelRuleTree.this.selectedNode);
/*     */     }
/*     */   };
/*     */   
/* 315 */   Action dropAction = new AbstractAction("Drop")
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 318 */       PanelRuleTree.this.selectedNode.isDropped = ((JCheckBoxMenuItem)e.getSource()).isSelected();
/* 319 */       PanelRuleTree.this.resetNodeDisplay(PanelRuleTree.this.selectedNode);
/*     */     }
/*     */   };
/*     */   
/* 323 */   Action commitAction = new AbstractAction("Commit")
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 326 */       NodeSequence parent = (NodeSequence)PanelRuleTree.this.selectedNode.getParent();
/* 327 */       int myIndex = parent.getIndex(PanelRuleTree.this.selectedNode);
/* 328 */       int oldIndex = parent.commitIndex;
/* 329 */       if (parent.commitIndex == myIndex) {
/* 330 */         parent.commitIndex = Integer.MAX_VALUE;
/*     */       } else {
/* 332 */         parent.commitIndex = myIndex;
/*     */       }
/* 334 */       PanelRuleTree.this.associatedNode = ((myIndex == oldIndex) || (oldIndex >= parent.getChildCount()) ? null : (NodeBase)parent.getChildAt(oldIndex));
/*     */       
/* 336 */       PanelRuleTree.this.resetNodeDisplay(PanelRuleTree.this.selectedNode);
/*     */     }
/*     */   };
/*     */   
/* 340 */   Action descriptionAction = new AbstractAction("Description")
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 343 */       String descr = (String)JOptionPane.showInputDialog(PanelRuleTree.this.gui, "Enter description", "Description", 3, null, null, PanelRuleTree.this.selectedNode.description);
/*     */       
/* 345 */       if ((descr == null) || (descr.equals(PanelRuleTree.this.selectedNode.description)))
/* 346 */         return;
/* 347 */       PanelRuleTree.this.selectedNode.description = descr;
/* 348 */       PanelRuleTree.this.resetNodeDisplay(PanelRuleTree.this.selectedNode);
/*     */     }
/*     */   };
/*     */   
/* 352 */   Action errorMessageAction = new AbstractAction("Error message")
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 355 */       String msg = (String)JOptionPane.showInputDialog(PanelRuleTree.this.gui, "Enter error message", "Error message", 3, null, null, PanelRuleTree.this.selectedNode.errorMessage);
/*     */       
/* 357 */       if ((msg == null) || (msg.equals(PanelRuleTree.this.selectedNode.errorMessage)))
/* 358 */         return;
/* 359 */       PanelRuleTree.this.selectedNode.errorMessage = msg;
/* 360 */       PanelRuleTree.this.resetNodeDisplay(PanelRuleTree.this.selectedNode);
/*     */     }
/*     */   };
/*     */   
/* 364 */   Action firstsAction = new AbstractAction("First-k-sets")
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 367 */       VisitorFirstSets vfs = new VisitorFirstSets(PanelRuleTree.this.gui.theForest);
/* 368 */       Set<String>[] firsts = (Set[])PanelRuleTree.this.selectedNode.accept(vfs);
/* 369 */       StringBuilder sb = new StringBuilder();
/* 370 */       for (Set<String> ss : firsts) {
/* 371 */         if (sb.length() > 0)
/* 372 */           sb.append(",\n");
/* 373 */         sb.append('{');
/* 374 */         for (String s : ss) {
/* 375 */           if (sb.charAt(sb.length() - 1) != '{')
/* 376 */             sb.append(", ");
/* 377 */           sb.append(s.equals("") ? "ε" : s);
/*     */         }
/* 379 */         sb.append("}");
/*     */       }
/* 381 */       JOptionPane.showMessageDialog(PanelRuleTree.this.gui, sb.toString(), "First-k-sets", -1); } };
/*     */   PopupMenuTree treePopupMenu;
/*     */   NodeRoot rootNode;
/*     */   private DefaultTreeModel theModel;
/*     */   Vll4jGui gui;
/*     */   JTree theTree;
/*     */   
/* 388 */   public void valueChanged(TreeSelectionEvent e) { this.selectedNode = ((NodeBase)e.getPath().getLastPathComponent());
/* 389 */     this.gui.theActionCodePanel.resetView();
/* 390 */     this.gui.theAstPanel.resetView();
/* 391 */     this.statusLabel.setText(String.format(" %s/%s", new Object[] { this.selectedNode.nodeType(), this.selectedNode.nodeName() }));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 399 */   NodeBase selectedNode = null; NodeBase associatedNode = null;
/* 400 */   PopupListenerTree treePopupListener = new PopupListenerTree(this);
/* 401 */   NodeBase theClipBoard = null;
/* 402 */   private JButton helpButton = null;
/* 403 */   private JLabel statusLabel = new JLabel("  ");
/*     */ }


/* Location:              /home/yogesh/Downloads/VLL4J.jar!/net/java/vll/vll4j/gui/PanelRuleTree.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */