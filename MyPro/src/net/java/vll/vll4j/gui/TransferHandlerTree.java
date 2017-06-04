/*     */ package net.java.vll.vll4j.gui;
/*     */ 
/*     */ import java.awt.datatransfer.DataFlavor;
/*     */ import java.awt.datatransfer.Transferable;
/*     */ import java.awt.datatransfer.UnsupportedFlavorException;
/*     */ import java.io.IOException;
/*     */ import javax.swing.JComponent;
/*     */ import javax.swing.JTree;
/*     */ import javax.swing.JTree.DropLocation;
/*     */ import javax.swing.TransferHandler;
/*     */ import javax.swing.TransferHandler.TransferSupport;
/*     */ import javax.swing.tree.DefaultTreeModel;
/*     */ import javax.swing.tree.TreePath;
/*     */ import net.java.vll.vll4j.api.NodeBase;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class TransferHandlerTree
/*     */   extends TransferHandler
/*     */ {
/*     */   public TransferHandlerTree()
/*     */   {
/*     */     try
/*     */     {
/*  38 */       this.dataFlavors = new DataFlavor[] { new DataFlavor(mimeType(NodeBase.class)) };
/*     */     } catch (Exception e) {
/*  40 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean canImport(TransferHandler.TransferSupport support)
/*     */   {
/*  46 */     if (!support.isDrop())
/*  47 */       return false;
/*  48 */     support.setShowDropLocation(true);
/*  49 */     if (!support.isDataFlavorSupported(this.dataFlavors[0])) {
/*  50 */       return false;
/*     */     }
/*  52 */     JTree.DropLocation dl = (JTree.DropLocation)support.getDropLocation();
/*  53 */     Object par1 = dl.getPath().getLastPathComponent();
/*     */     
/*  55 */     if ((this.nodeToRemove == null) || (this.nodeToRemove.getParent() == null) || (par1 != this.nodeToRemove.getParent())) {
/*  56 */       return false;
/*     */     }
/*  58 */     return true;
/*     */   }
/*     */   
/*     */   public Transferable createTransferable(JComponent c)
/*     */   {
/*  63 */     TreePath path = ((JTree)c).getSelectionPath();
/*  64 */     if (path != null) {
/*  65 */       final NodeBase node = (NodeBase)path.getLastPathComponent();
/*  66 */       this.nodeToRemove = node;
/*  67 */       new Transferable()
/*     */       {
/*     */         public DataFlavor[] getTransferDataFlavors() {
/*  70 */           return TransferHandlerTree.this.dataFlavors;
/*     */         }
/*     */         
/*     */         public boolean isDataFlavorSupported(DataFlavor flavor) {
/*  74 */           return TransferHandlerTree.this.dataFlavors[0].equals(flavor);
/*     */         }
/*     */         
/*     */         public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
/*  78 */           if (!isDataFlavorSupported(flavor)) {
/*  79 */             throw new UnsupportedFlavorException(flavor);
/*     */           }
/*  81 */           return node.clone();
/*     */         }
/*     */       };
/*     */     }
/*  85 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public void exportDone(JComponent source, Transferable data, int action)
/*     */   {
/*  91 */     if ((action & 0x2) == 2) {
/*  92 */       DefaultTreeModel model = (DefaultTreeModel)((JTree)source).getModel();
/*  93 */       model.removeNodeFromParent(this.nodeToRemove);
/*     */     }
/*     */   }
/*     */   
/*     */   public int getSourceActions(JComponent c)
/*     */   {
/*  99 */     return 2;
/*     */   }
/*     */   
/*     */   public boolean importData(TransferHandler.TransferSupport support)
/*     */   {
/* 104 */     if (!canImport(support)) {
/* 105 */       return false;
/*     */     }
/* 107 */     NodeBase node = null;
/*     */     try {
/* 109 */       Transferable t = support.getTransferable();
/* 110 */       node = (NodeBase)t.getTransferData(this.dataFlavors[0]);
/*     */     } catch (Exception ex) {
/* 112 */       ex.printStackTrace();
/*     */     }
/*     */     
/* 115 */     JTree.DropLocation dl = (JTree.DropLocation)support.getDropLocation();
/* 116 */     int childIndex = dl.getChildIndex();
/* 117 */     TreePath dest = dl.getPath();
/* 118 */     NodeBase parent = (NodeBase)dest.getLastPathComponent();
/* 119 */     JTree tree = (JTree)support.getComponent();
/* 120 */     DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
/*     */     
/* 122 */     int index = childIndex;
/* 123 */     if (childIndex == -1) {
/* 124 */       index = parent.getChildCount();
/*     */     }
/*     */     
/* 127 */     model.insertNodeInto(node, parent, index);
/* 128 */     return true;
/*     */   }
/*     */   
/*     */   private String mimeType(Class c) {
/* 132 */     String mt = String.format("%s;class=\"%s\"", new Object[] { "application/x-java-jvm-local-objectref", c.getName() });
/* 133 */     return mt;
/*     */   }
/*     */   
/* 136 */   NodeBase nodeToRemove = null;
/* 137 */   DataFlavor[] dataFlavors = null;
/*     */ }


/* Location:              /home/yogesh/Downloads/VLL4J.jar!/net/java/vll/vll4j/gui/TransferHandlerTree.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */