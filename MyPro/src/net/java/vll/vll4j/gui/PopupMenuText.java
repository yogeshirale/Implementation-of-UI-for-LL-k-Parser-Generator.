/*    */ package net.java.vll.vll4j.gui;
/*    */ 
/*    */ import java.awt.event.ActionEvent;
/*    */ import java.awt.event.KeyEvent;
/*    */ import javax.swing.AbstractAction;
/*    */ import javax.swing.Action;
/*    */ import javax.swing.Icon;
/*    */ import javax.swing.JMenuItem;
/*    */ import javax.swing.JPopupMenu;
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
/*    */ 
/*    */ public class PopupMenuText
/*    */   extends JPopupMenu
/*    */ {
/*    */   PopupMenuText(TextAreaCustom textComponent)
/*    */   {
/* 32 */     this.textComponent = textComponent;
/* 33 */     createPopup();
/*    */   }
/*    */   
/*    */   private void createPopup() {
/* 37 */     this.cutItem = new JMenuItem(this.cutAction);
/* 38 */     add(this.cutItem);
/* 39 */     this.copyItem = new JMenuItem(this.copyAction);
/* 40 */     add(this.copyItem);
/* 41 */     this.pasteItem = new JMenuItem(this.pasteAction);
/* 42 */     add(this.pasteItem);
/* 43 */     this.clearItem = new JMenuItem(this.clearAction);
/* 44 */     add(this.clearItem);
/* 45 */     this.selectAllItem = new JMenuItem(this.selectAllAction);
/* 46 */     add(this.selectAllItem);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/* 52 */   private Action cutAction = new AbstractAction("Cut", Resources.cut16) {
/*    */     public void actionPerformed(ActionEvent evt) {
/* 54 */       PopupMenuText.this.textComponent.cut();
/* 55 */       PopupMenuText.this.textComponent.dispatchEvent(new KeyEvent(PopupMenuText.this.textComponent, 400, 0L, 0, 0, '\033'));
/*    */     }
/*    */   };
/*    */   
/*    */ 
/* 60 */   private Action copyAction = new AbstractAction("Copy", Resources.copy16) {
/*    */     public void actionPerformed(ActionEvent evt) {
/* 62 */       PopupMenuText.this.textComponent.copy();
/*    */     }
/*    */   };
/*    */   
/* 66 */   private Action pasteAction = new AbstractAction("Paste", Resources.paste16) {
/*    */     public void actionPerformed(ActionEvent evt) {
/* 68 */       PopupMenuText.this.textComponent.paste();
/* 69 */       PopupMenuText.this.textComponent.dispatchEvent(new KeyEvent(PopupMenuText.this.textComponent, 400, 0L, 0, 0, '\033'));
/*    */     }
/*    */   };
/*    */   
/*    */ 
/* 74 */   private Action clearAction = new AbstractAction("Clear", Resources.clear16) {
/*    */     public void actionPerformed(ActionEvent evt) {
/* 76 */       PopupMenuText.this.textComponent.setText("");
/* 77 */       PopupMenuText.this.textComponent.dispatchEvent(new KeyEvent(PopupMenuText.this.textComponent, 400, 0L, 0, 0, '\033'));
/*    */     }
/*    */   };
/*    */   
/*    */ 
/* 82 */   private Action selectAllAction = new AbstractAction("Select All") {
/*    */     public void actionPerformed(ActionEvent evt) {
/* 84 */       PopupMenuText.this.textComponent.setSelectionStart(0);
/* 85 */       PopupMenuText.this.textComponent.setSelectionEnd(PopupMenuText.this.textComponent.getText().length());
/* 86 */       PopupMenuText.this.textComponent.selectAll();
/*    */     }
/*    */   };
/*    */   TextAreaCustom textComponent;
/*    */   JMenuItem cutItem;
/*    */   JMenuItem copyItem;
/*    */   JMenuItem pasteItem;
/*    */   JMenuItem clearItem;
/*    */   JMenuItem selectAllItem;
/*    */   
/*    */   void adjustMenu() {}
/*    */ }


/* Location:              /home/yogesh/Downloads/VLL4J.jar!/net/java/vll/vll4j/gui/PopupMenuText.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */