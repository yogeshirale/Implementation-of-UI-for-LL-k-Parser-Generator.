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
/*    */ public class NodeSequence
/*    */   extends NodeBase
/*    */ {
/*    */   public String nodeType()
/*    */   {
/* 26 */     return "Sequence";
/*    */   }
/*    */   
/*    */   public Object accept(VisitorBase v)
/*    */   {
/* 31 */     return v.visitSequence(this);
/*    */   }
/*    */   
/*    */   public NodeSequence clone()
/*    */   {
/* 36 */     NodeSequence n = new NodeSequence();
/* 37 */     for (int i = 0; i < getChildCount(); i++) {
/* 38 */       n.add((NodeBase)((NodeBase)getChildAt(i)).clone());
/*    */     }
/* 40 */     n.copyFrom(this);
/* 41 */     return n;
/*    */   }
/*    */   
/*    */   public String getName()
/*    */   {
/* 46 */     return String.format(this.description.isEmpty() ? "%s %s" : "%s %s (%s)", new Object[] { this.multiplicity, getAttributes(), this.description });
/*    */   }
/*    */   
/*    */ 
/* 50 */   public int commitIndex = Integer.MAX_VALUE;
/*    */   long dropMap;
/*    */ }


/* Location:              /home/yogesh/Downloads/VLL4J.jar!/net/java/vll/vll4j/api/NodeSequence.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */