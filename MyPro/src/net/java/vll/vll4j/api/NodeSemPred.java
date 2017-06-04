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
/*    */ 
/*    */ 
/*    */ public class NodeSemPred
/*    */   extends NodeBase
/*    */ {
/*    */   public String nodeType()
/*    */   {
/* 29 */     return "SemPred";
/*    */   }
/*    */   
/*    */   public NodeSemPred clone()
/*    */   {
/* 34 */     NodeSemPred n = new NodeSemPred();
/* 35 */     n.copyFrom(this);
/* 36 */     return n;
/*    */   }
/*    */   
/*    */   public boolean getAllowsChildren()
/*    */   {
/* 41 */     return false;
/*    */   }
/*    */   
/*    */   public Object accept(VisitorBase v)
/*    */   {
/* 46 */     return v.visitSemPred(this);
/*    */   }
/*    */   
/*    */   public String getName()
/*    */   {
/* 51 */     return String.format(this.description.isEmpty() ? "%s %s" : "%s %s (%s)", new Object[] { this.multiplicity, getAttributes(), this.description });
/*    */   }
/*    */ }


/* Location:              /home/yogesh/Downloads/VLL4J.jar!/net/java/vll/vll4j/api/NodeSemPred.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */