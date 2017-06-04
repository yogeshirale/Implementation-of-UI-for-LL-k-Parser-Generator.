/*    */ package net.java.vll.vll4j.gui;
/*    */ 
/*    */ import java.util.TreeSet;
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
/*    */ public class VisitorRuleSearch
/*    */   extends VisitorBase
/*    */ {
/*    */   public VisitorRuleSearch(String ruleToFind)
/*    */   {
/* 29 */     this.ruleToFind = ruleToFind;
/*    */   }
/*    */   
/*    */   private String getRuleName(NodeBase node) {
/* 33 */     while (!(node instanceof NodeRoot)) {
/* 34 */       node = (NodeBase)node.getParent();
/*    */     }
/* 36 */     return ((NodeRoot)node).ruleName;
/*    */   }
/*    */   
/*    */   public Object visitChoice(NodeChoice n)
/*    */   {
/* 41 */     visitAllChildNodes(n);
/* 42 */     return null;
/*    */   }
/*    */   
/*    */   public Object visitLiteral(NodeLiteral n)
/*    */   {
/* 47 */     return null;
/*    */   }
/*    */   
/*    */   public Object visitReference(NodeReference n)
/*    */   {
/* 52 */     if (n.refRuleName.equals(this.ruleToFind))
/* 53 */       this.ruleSet.add(getRuleName(n));
/* 54 */     return null;
/*    */   }
/*    */   
/*    */   public Object visitRegex(NodeRegex n)
/*    */   {
/* 59 */     return null;
/*    */   }
/*    */   
/*    */   public Object visitRepSep(NodeRepSep n)
/*    */   {
/* 64 */     visitAllChildNodes(n);
/* 65 */     return null;
/*    */   }
/*    */   
/*    */   public Object visitRoot(NodeRoot n)
/*    */   {
/* 70 */     visitAllChildNodes(n);
/* 71 */     return null;
/*    */   }
/*    */   
/*    */   public Object visitSemPred(NodeSemPred n)
/*    */   {
/* 76 */     return null;
/*    */   }
/*    */   
/*    */   public Object visitSequence(NodeSequence n)
/*    */   {
/* 81 */     visitAllChildNodes(n);
/* 82 */     return null;
/*    */   }
/*    */   
/*    */   public Object visitWildCard(NodeWildCard n)
/*    */   {
/* 87 */     return null;
/*    */   }
/*    */   
/* 90 */   TreeSet<String> ruleSet = new TreeSet();
/*    */   private String ruleToFind;
/*    */ }


/* Location:              /home/yogesh/Downloads/VLL4J.jar!/net/java/vll/vll4j/gui/VisitorRuleSearch.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */