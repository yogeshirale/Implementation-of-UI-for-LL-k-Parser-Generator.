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
/*    */ 
/*    */ public class VisitorTokenSearch
/*    */   extends VisitorBase
/*    */ {
/*    */   public VisitorTokenSearch(String tokenToFind)
/*    */   {
/* 30 */     this.tokenToFind = tokenToFind;
/*    */   }
/*    */   
/*    */   private String getRuleName(NodeBase node) {
/* 34 */     while (!(node instanceof NodeRoot)) {
/* 35 */       node = (NodeBase)node.getParent();
/*    */     }
/* 37 */     return ((NodeRoot)node).ruleName;
/*    */   }
/*    */   
/*    */   public Object visitChoice(NodeChoice n)
/*    */   {
/* 42 */     visitAllChildNodes(n);
/* 43 */     return null;
/*    */   }
/*    */   
/*    */   public Object visitLiteral(NodeLiteral n)
/*    */   {
/* 48 */     if (n.literalName.equals(this.tokenToFind))
/* 49 */       this.ruleSet.add(getRuleName(n));
/* 50 */     return null;
/*    */   }
/*    */   
/*    */   public Object visitReference(NodeReference n)
/*    */   {
/* 55 */     return null;
/*    */   }
/*    */   
/*    */   public Object visitRegex(NodeRegex n)
/*    */   {
/* 60 */     if (n.regexName.equals(this.tokenToFind))
/* 61 */       this.ruleSet.add(getRuleName(n));
/* 62 */     return null;
/*    */   }
/*    */   
/*    */   public Object visitRepSep(NodeRepSep n)
/*    */   {
/* 67 */     visitAllChildNodes(n);
/* 68 */     return null;
/*    */   }
/*    */   
/*    */   public Object visitRoot(NodeRoot n)
/*    */   {
/* 73 */     visitAllChildNodes(n);
/* 74 */     return null;
/*    */   }
/*    */   
/*    */   public Object visitSemPred(NodeSemPred n)
/*    */   {
/* 79 */     return null;
/*    */   }
/*    */   
/*    */   public Object visitSequence(NodeSequence n)
/*    */   {
/* 84 */     visitAllChildNodes(n);
/* 85 */     return null;
/*    */   }
/*    */   
/*    */   public Object visitWildCard(NodeWildCard n)
/*    */   {
/* 90 */     return null;
/*    */   }
/*    */   
/* 93 */   TreeSet<String> ruleSet = new TreeSet();
/*    */   private String tokenToFind;
/*    */ }


/* Location:              /home/yogesh/Downloads/VLL4J.jar!/net/java/vll/vll4j/gui/VisitorTokenSearch.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */