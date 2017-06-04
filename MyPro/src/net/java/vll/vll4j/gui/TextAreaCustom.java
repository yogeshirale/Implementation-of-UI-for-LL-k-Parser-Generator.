/*    */ package net.java.vll.vll4j.gui;
/*    */ 
/*    */ import java.awt.Font;
/*    */ import javax.swing.JTextArea;
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
/*    */ 
/*    */ 
/*    */ public class TextAreaCustom
/*    */   extends JTextArea
/*    */ {
/*    */   TextAreaCustom()
/*    */   {
/* 29 */     setFont(new Font("Monospaced", getFont().getStyle(), getFont().getSize()));
/* 30 */     addMouseListener(new PopupListenerText(this));
/*    */   }
/*    */   
/* 33 */   PopupMenuText popupMenuText = new PopupMenuText(this);
/*    */ }


/* Location:              /home/yogesh/Downloads/VLL4J.jar!/net/java/vll/vll4j/gui/TextAreaCustom.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */