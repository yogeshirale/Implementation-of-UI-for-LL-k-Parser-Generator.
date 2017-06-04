/*    */ package net.java.vll.vll4j.gui;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import net.java.vll.vll4j.api.NodeChoice;
/*    */ import net.java.vll.vll4j.api.NodeLiteral;
/*    */ import net.java.vll.vll4j.api.NodeReference;
/*    */ import net.java.vll.vll4j.api.NodeRegex;
/*    */ import net.java.vll.vll4j.api.NodeRepSep;
/*    */ import net.java.vll.vll4j.api.NodeRoot;
/*    */ import net.java.vll.vll4j.api.NodeSemPred;
/*    */ import net.java.vll.vll4j.api.NodeSequence;
/*    */ import net.java.vll.vll4j.api.NodeWildCard;
/*    */ import net.java.vll.vll4j.api.VisitorBase;
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
/*    */ public class VisitorRuleRenaming
/*    */   extends VisitorBase
/*    */ {
/*    */   public VisitorRuleRenaming(String currentName, String newName)
/*    */   {
/* 29 */     this.currentName = currentName;
/* 30 */     this.newName = newName;
/*    */   }
/*    */   
/*    */   public Object visitChoice(NodeChoice n)
/*    */   {
/* 35 */     visitAllChildNodes(n);
/* 36 */     return null;
/*    */   }
/*    */   
/*    */   public Object visitLiteral(NodeLiteral n)
/*    */   {
/* 41 */     return null;
/*    */   }
/*    */   
/*    */   public Object visitReference(NodeReference n)
/*    */   {
/* 46 */     if (n.refRuleName.equals(this.currentName))
/* 47 */       n.refRuleName = this.newName;
/* 48 */     return null;
/*    */   }
/*    */   
/*    */   public Object visitRegex(NodeRegex n)
/*    */   {
/* 53 */     return null;
/*    */   }
/*    */   
/*    */   public Object visitRepSep(NodeRepSep n)
/*    */   {
/* 58 */     visitAllChildNodes(n);
/* 59 */     return null;
/*    */   }
/*    */   
/*    */   public Object visitRoot(NodeRoot n)
/*    */   {
/* 64 */     visitAllChildNodes(n);
/* 65 */     if (n.ruleName.equals(this.currentName))
/* 66 */       n.ruleName = this.newName;
/* 67 */     return null;
/*    */   }
/*    */   
/*    */   public Object visitSemPred(NodeSemPred n)
/*    */   {
/* 72 */     return null;
/*    */   }
/*    */   
/*    */   public Object visitSequence(NodeSequence n)
/*    */   {
/* 77 */     visitAllChildNodes(n);
/* 78 */     return null;
/*    */   }
/*    */   
/*    */   public Object visitWildCard(NodeWildCard n)
/*    */   {
/* 83 */     return null;
/*    */   }
/*    */   
/* 86 */   ArrayList<String> ruleList = new ArrayList();
/*    */   private String currentName;
/*    */   private String newName;
/*    */ }


/* Location:              /home/yogesh/Downloads/VLL4J.jar!/net/java/vll/vll4j/gui/VisitorRuleRenaming.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */