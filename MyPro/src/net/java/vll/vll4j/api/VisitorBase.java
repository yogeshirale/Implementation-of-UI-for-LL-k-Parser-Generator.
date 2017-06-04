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
/*    */ 
/*    */ public abstract class VisitorBase
/*    */ {
/*    */   protected void visitAllChildNodes(NodeBase n)
/*    */   {
/* 26 */     int childCount = n.getChildCount();
/* 27 */     for (int i = 0; i < childCount; i++) {
/* 28 */       this.depth += 1;
/* 29 */       ((NodeBase)n.getChildAt(i)).accept(this);
/* 30 */       this.depth -= 1;
/*    */     }
/*    */   }
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
/* 44 */   protected int depth = 0;
/*    */   
/*    */   public abstract Object visitChoice(NodeChoice paramNodeChoice);
/*    */   
/*    */   public abstract Object visitLiteral(NodeLiteral paramNodeLiteral);
/*    */   
/*    */   public abstract Object visitReference(NodeReference paramNodeReference);
/*    */   
/*    */   public abstract Object visitRegex(NodeRegex paramNodeRegex);
/*    */   
/*    */   public abstract Object visitRepSep(NodeRepSep paramNodeRepSep);
/*    */   
/*    */   public abstract Object visitRoot(NodeRoot paramNodeRoot);
/*    */   
/*    */   public abstract Object visitSemPred(NodeSemPred paramNodeSemPred);
/*    */   
/*    */   public abstract Object visitSequence(NodeSequence paramNodeSequence);
/*    */   
/*    */   public abstract Object visitWildCard(NodeWildCard paramNodeWildCard);
/*    */ }


/* Location:              /home/yogesh/Downloads/VLL4J.jar!/net/java/vll/vll4j/api/VisitorBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */