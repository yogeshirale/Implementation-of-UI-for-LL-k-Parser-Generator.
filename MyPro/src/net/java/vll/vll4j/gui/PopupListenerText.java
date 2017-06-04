/*    */ package net.java.vll.vll4j.gui;
/*    */ 
/*    */ import java.awt.event.MouseAdapter;
/*    */ import java.awt.event.MouseEvent;
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
/*    */ public class PopupListenerText
/*    */   extends MouseAdapter
/*    */ {
/*    */   TextAreaCustom textAreaCustom;
/*    */   
/*    */   PopupListenerText(TextAreaCustom textAreaCustom)
/*    */   {
/* 29 */     this.textAreaCustom = textAreaCustom;
/*    */   }
/*    */   
/*    */   public void mousePressed(MouseEvent me)
/*    */   {
/* 34 */     popup(me);
/*    */   }
/*    */   
/*    */   public void mouseReleased(MouseEvent me)
/*    */   {
/* 39 */     popup(me);
/*    */   }
/*    */   
/*    */   void popup(MouseEvent e) {
/* 43 */     if (e.isPopupTrigger()) {
/* 44 */       int x = e.getX();int y = e.getY();
/* 45 */       if (y + this.textAreaCustom.popupMenuText.getHeight() > this.textAreaCustom.getHeight()) {
/* 46 */         y = this.textAreaCustom.getHeight() - this.textAreaCustom.popupMenuText.getHeight();
/*    */       }
/* 48 */       if (x + this.textAreaCustom.popupMenuText.getWidth() > this.textAreaCustom.getWidth()) {
/* 49 */         x = this.textAreaCustom.getWidth() - this.textAreaCustom.popupMenuText.getWidth();
/*    */       }
/* 51 */       this.textAreaCustom.popupMenuText.show(e.getComponent(), x, y);
/*    */     }
/*    */   }
/*    */ }


/* Location:              /home/yogesh/Downloads/VLL4J.jar!/net/java/vll/vll4j/gui/PopupListenerText.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */