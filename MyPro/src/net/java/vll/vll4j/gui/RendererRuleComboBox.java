/*    */ package net.java.vll.vll4j.gui;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.Component;
/*    */ import java.awt.Graphics;
/*    */ import java.util.Map;
/*    */ import javax.swing.Icon;
/*    */ import javax.swing.JList;
/*    */ import javax.swing.plaf.basic.BasicComboBoxRenderer;
/*    */ import net.java.vll.vll4j.api.Forest;
/*    */ import net.java.vll.vll4j.api.NodeRoot;
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
/*    */ public class RendererRuleComboBox
/*    */   extends BasicComboBoxRenderer
/*    */ {
/*    */   private Vll4jGui gui;
/*    */   private NodeRoot rootNode;
/*    */   
/*    */   public RendererRuleComboBox(Vll4jGui gui)
/*    */   {
/* 34 */     this.gui = gui;
/* 35 */     setOpaque(true);
/*    */   }
/*    */   
/*    */ 
/*    */   public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
/*    */   {
/* 41 */     this.ruleName = value.toString();
/* 42 */     this.rootNode = ((NodeRoot)this.gui.theForest.ruleBank.get(this.ruleName));
/* 43 */     this.visitorRuleInfo.visitRoot(this.rootNode);
/* 44 */     setText(this.ruleName);
/* 45 */     setIcon(this.myIcon);
/* 46 */     return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
/*    */   }
/*    */   
/*    */   public void paint(Graphics g) {
/* 50 */     super.paint(g);
/* 51 */     this.visitorRuleInfo.visitRoot(this.rootNode);
/* 52 */     if (this.visitorRuleInfo.hasErrors) {
/* 53 */       g.setColor(Color.red);
/* 54 */       g.drawRect(2, 2, 12, 12);
/* 55 */       g.drawRect(1, 1, 14, 14);
/*    */     }
/* 57 */     if (this.visitorRuleInfo.hasActions) {
/* 58 */       g.setColor(this.visitorRuleInfo.isTester ? Color.magenta : Color.green.darker());
/* 59 */       g.drawRect(4, 4, 8, 8);
/* 60 */       g.fillRect(4, 4, 8, 8);
/* 61 */       g.setColor(Color.white);
/* 62 */       g.drawLine(6, 5, 9, 8);
/* 63 */       g.drawLine(9, 8, 6, 11);
/* 64 */       g.drawLine(7, 5, 10, 8);
/* 65 */       g.drawLine(10, 8, 7, 11);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/* 71 */   private Icon myIcon = Resources.rule;
/* 72 */   String ruleName = "";
/* 73 */   private VisitorRuleInfo visitorRuleInfo = new VisitorRuleInfo();
/*    */ }


/* Location:              /home/yogesh/Downloads/VLL4J.jar!/net/java/vll/vll4j/gui/RendererRuleComboBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */