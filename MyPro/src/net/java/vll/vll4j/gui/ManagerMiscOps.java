/*    */ package net.java.vll.vll4j.gui;
/*    */ 
/*    */ import java.awt.event.ActionEvent;
/*    */ import java.util.regex.Pattern;
/*    */ import javax.swing.AbstractAction;
/*    */ import javax.swing.Action;
/*    */ import javax.swing.JOptionPane;
/*    */ import net.java.vll.vll4j.combinator.PackratParsers;
/*    */ import net.java.vll.vll4j.combinator.Utils;
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
/*    */ public class ManagerMiscOps
/*    */ {
/*    */   ManagerMiscOps(Vll4jGui gui)
/*    */   {
/* 33 */     this.gui = gui;
/*    */   }
/*    */   
/* 36 */   Action globalsWhitespaceAction = new AbstractAction("Whitespace") {
/*    */     public void actionPerformed(ActionEvent e) {
/* 38 */       String ws = (String)JOptionPane.showInputDialog(ManagerMiscOps.this.gui, "Enter whitespace pattern", "WhiteSpace", 3, null, null, ManagerMiscOps.this.gui.packratParsers.whiteSpaceRegex);
/*    */       
/* 40 */       if ((ws == null) || (ws.equals(ManagerMiscOps.this.gui.packratParsers.whiteSpaceRegex)))
/* 41 */         return;
/*    */       try {
/* 43 */         Pattern.compile(Utils.unEscape(ws));
/*    */       } catch (Exception ex) {
/* 45 */         JOptionPane.showMessageDialog(ManagerMiscOps.this.gui, ex.toString(), "ERROR - WhiteSpace", 0);
/* 46 */         return;
/*    */       }
/* 48 */       ManagerMiscOps.this.gui.packratParsers.whiteSpaceRegex = ws;
/* 49 */       ManagerMiscOps.this.gui.packratParsers.resetWhitespace();
/*    */     }
/*    */   };
/*    */   
/* 53 */   Action globalsCommentAction = new AbstractAction("Comment") {
/*    */     public void actionPerformed(ActionEvent e) {
/* 55 */       String cmts = (String)JOptionPane.showInputDialog(ManagerMiscOps.this.gui, "Enter comment pattern", "Comments", 3, null, null, ManagerMiscOps.this.gui.packratParsers.commentSpecRegex);
/*    */       
/* 57 */       if ((cmts == null) || (cmts.equals(ManagerMiscOps.this.gui.packratParsers.commentSpecRegex)))
/* 58 */         return;
/*    */       try {
/* 60 */         Pattern.compile(Utils.unEscape(cmts));
/*    */       } catch (Exception ex) {
/* 62 */         JOptionPane.showMessageDialog(ManagerMiscOps.this.gui, ex.toString(), "ERROR - WhiteSpace", 0);
/* 63 */         return;
/*    */       }
/* 65 */       ManagerMiscOps.this.gui.packratParsers.commentSpecRegex = cmts;
/* 66 */       ManagerMiscOps.this.gui.packratParsers.resetWhitespace();
/*    */     }
/*    */   };
/*    */   private Vll4jGui gui;
/*    */ }


/* Location:              /home/yogesh/Downloads/VLL4J.jar!/net/java/vll/vll4j/gui/ManagerMiscOps.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */