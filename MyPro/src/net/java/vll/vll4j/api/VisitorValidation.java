/*    */ package net.java.vll.vll4j.api;
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
/*    */ public class VisitorValidation
/*    */   extends VisitorBase
/*    */ {
/*    */   private String checkAction(NodeBase n)
/*    */   {
/* 26 */     if ((n.actionText.trim().isEmpty()) || (n.actionFunction != null)) {
/* 27 */       return null;
/*    */     }
/* 29 */     return "error in action-code";
/*    */   }
/*    */   
/*    */   public String visitChoice(NodeChoice n)
/*    */   {
/* 34 */     if (n.getChildCount() >= 2) {
/* 35 */       return checkAction(n);
/*    */     }
/* 37 */     return "needs 2 or more child nodes, " + checkAction(n);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String visitLiteral(NodeLiteral n)
/*    */   {
/* 44 */     return checkAction(n);
/*    */   }
/*    */   
/*    */   public String visitReference(NodeReference n)
/*    */   {
/* 49 */     return checkAction(n);
/*    */   }
/*    */   
/*    */   public String visitRegex(NodeRegex n)
/*    */   {
/* 54 */     return checkAction(n);
/*    */   }
/*    */   
/*    */   public String visitRepSep(NodeRepSep n)
/*    */   {
/* 59 */     if (n.getChildCount() == 2) {
/* 60 */       return checkAction(n);
/*    */     }
/* 62 */     return "needs 2 child nodes, " + checkAction(n);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String visitRoot(NodeRoot n)
/*    */   {
/* 69 */     if (n.getChildCount() == 1) {
/* 70 */       return checkAction(n);
/*    */     }
/* 72 */     return "needs 1 child node, " + checkAction(n);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String visitSemPred(NodeSemPred n)
/*    */   {
/* 79 */     if (n.actionText.trim().isEmpty()) {
/* 80 */       return "no predicate code";
/*    */     }
/* 82 */     return checkAction(n);
/*    */   }
/*    */   
/*    */   public String visitSequence(NodeSequence n)
/*    */   {
/* 87 */     if (n.getChildCount() > 0) {
/* 88 */       return checkAction(n);
/*    */     }
/* 90 */     return "needs 1 or more child nodes, " + checkAction(n);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String visitWildCard(NodeWildCard n)
/*    */   {
/* 97 */     return null;
/*    */   }
/*    */ }


/* Location:              /home/yogesh/Downloads/VLL4J.jar!/net/java/vll/vll4j/api/VisitorValidation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */