/*    */ package net.java.vll.vll4j.gui;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.Component;
/*    */ import java.awt.Graphics;
/*    */ import javax.swing.Icon;
/*    */ import javax.swing.ImageIcon;
/*    */ import javax.swing.JLabel;
/*    */ import javax.swing.JTree;
/*    */ import javax.swing.tree.DefaultTreeCellRenderer;
/*    */ import net.java.vll.vll4j.api.NodeBase;
/*    */ import net.java.vll.vll4j.api.NodeChoice;
/*    */ import net.java.vll.vll4j.api.NodeLiteral;
/*    */ import net.java.vll.vll4j.api.NodeReference;
/*    */ import net.java.vll.vll4j.api.NodeRegex;
/*    */ import net.java.vll.vll4j.api.NodeRepSep;
/*    */ import net.java.vll.vll4j.api.NodeRoot;
/*    */ import net.java.vll.vll4j.api.NodeSemPred;
/*    */ import net.java.vll.vll4j.api.NodeSequence;
/*    */ import net.java.vll.vll4j.api.NodeWildCard;
/*    */ import net.java.vll.vll4j.api.VisitorValidation;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RendererRuleNode
/*    */   extends DefaultTreeCellRenderer
/*    */ {
/*    */   NodeBase theNode;
/*    */   
/*    */   public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus)
/*    */   {
/* 37 */     this.theNode = ((NodeBase)value);
/* 38 */     this.isDropped = this.theNode.isDropped;
/* 39 */     JLabel c = (JLabel)super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
/* 40 */     c.setText(this.theNode.getName());
/* 41 */     this.validationString = ((String)this.theNode.accept(this.visitorNodeValidation));
/* 42 */     c.setToolTipText(this.validationString);
/* 43 */     return c;
/*    */   }
/*    */   
/*    */   public Icon getLeafIcon() {
/* 47 */     return getNodeIcon();
/*    */   }
/*    */   
/*    */   public Icon getOpenIcon() {
/* 51 */     return getNodeIcon();
/*    */   }
/*    */   
/*    */ 
/* 55 */   public Icon getClosedIcon() { return getNodeIcon(); }
/*    */   
/*    */   private Icon getNodeIcon() {
/* 58 */     if ((this.theNode instanceof NodeChoice))
/* 59 */       return Resources.choice;
/* 60 */     if ((this.theNode instanceof NodeLiteral)) {
/* 61 */       String litName = ((NodeLiteral)this.theNode).literalName;
/* 62 */       return litName.endsWith("_") ? Resources.literalLocal : Resources.literal; }
/* 63 */     if ((this.theNode instanceof NodeReference))
/* 64 */       return Resources.reference;
/* 65 */     if ((this.theNode instanceof NodeRegex)) {
/* 66 */       String regName = ((NodeRegex)this.theNode).regexName;
/* 67 */       return regName.endsWith("_") ? Resources.regexLocal : Resources.regex; }
/* 68 */     if ((this.theNode instanceof NodeRepSep))
/* 69 */       return Resources.repSep;
/* 70 */     if ((this.theNode instanceof NodeRoot))
/* 71 */       return Resources.root;
/* 72 */     if ((this.theNode instanceof NodeSemPred))
/* 73 */       return Resources.semPred;
/* 74 */     if ((this.theNode instanceof NodeSequence))
/* 75 */       return Resources.sequence;
/* 76 */     if ((this.theNode instanceof NodeWildCard)) {
/* 77 */       return Resources.wildCard;
/*    */     }
/* 79 */     return null;
/*    */   }
/*    */   
/*    */   public void paintComponent(Graphics g) {
/* 83 */     super.paintComponent(g);
/* 84 */     this.validationString = ((String)this.theNode.accept(this.visitorNodeValidation));
/* 85 */     setToolTipText(this.validationString);
/* 86 */     if (this.validationString != null) {
/* 87 */       g.drawImage(Resources.errorMark.getImage(), 0, 0, null);
/*    */     }
/* 89 */     if (this.isDropped) {
/* 90 */       g.setColor(Color.black);
/* 91 */       g.drawLine(0, getHeight(), getHeight(), 0);
/* 92 */       g.setColor(Color.white);
/* 93 */       g.drawLine(0, getHeight() + 1, getHeight(), 1);
/* 94 */       g.drawLine(0, getHeight() - 1, getHeight(), -1);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/* 99 */   private VisitorValidation visitorNodeValidation = new VisitorValidation();
/*    */   private String validationString;
/*    */   private boolean isDropped;
/*    */ }


/* Location:              /home/yogesh/Downloads/VLL4J.jar!/net/java/vll/vll4j/gui/RendererRuleNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */