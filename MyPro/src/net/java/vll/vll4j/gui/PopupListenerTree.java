/*    */ package net.java.vll.vll4j.gui;
/*    */ 
/*    */ import java.awt.event.MouseAdapter;
/*    */ import java.awt.event.MouseEvent;
/*    */ import javax.swing.JTree;
/*    */ import javax.swing.tree.TreePath;
/*    */ import net.java.vll.vll4j.api.NodeBase;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PopupListenerTree
/*    */   extends MouseAdapter
/*    */ {
/*    */   PanelRuleTree treePanel;
/*    */   
/*    */   PopupListenerTree(PanelRuleTree treePanel)
/*    */   {
/* 31 */     this.treePanel = treePanel;
/*    */   }
/*    */   
/*    */   public void mousePressed(MouseEvent me)
/*    */   {
/* 36 */     popup(me);
/*    */   }
/*    */   
/*    */   public void mouseReleased(MouseEvent me)
/*    */   {
/* 41 */     popup(me);
/*    */   }
/*    */   
/*    */   void popup(MouseEvent e) {
/* 45 */     if (e.isPopupTrigger()) {
/* 46 */       TreePath path = this.treePanel.theTree.getPathForLocation(e.getX(), e.getY());
/* 47 */       if (path != null) {
/* 48 */         this.treePanel.theTree.setSelectionPath(path);
/* 49 */         this.treePanel.selectedNode = ((NodeBase)path.getLastPathComponent());
/* 50 */         this.treePanel.treePopupMenu.adjustMenu();
/* 51 */         this.treePanel.treePopupMenu.show(e.getComponent(), e.getX(), e.getY());
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              /home/yogesh/Downloads/VLL4J.jar!/net/java/vll/vll4j/gui/PopupListenerTree.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */