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
/*    */ public class NodeChoice
/*    */   extends NodeBase
/*    */ {
/*    */   public String nodeType()
/*    */   {
/* 26 */     return "Choice";
/*    */   }
/*    */   
/*    */   public NodeChoice clone()
/*    */   {
/* 31 */     NodeChoice n = new NodeChoice();
/* 32 */     for (int i = 0; i < getChildCount(); i++) {
/* 33 */       n.add((NodeBase)((NodeBase)getChildAt(i)).clone());
/*    */     }
/* 35 */     n.copyFrom(this);
/* 36 */     return n;
/*    */   }
/*    */   
/*    */   public Object accept(VisitorBase v)
/*    */   {
/* 41 */     return v.visitChoice(this);
/*    */   }
/*    */   
/*    */   public String getName()
/*    */   {
/* 46 */     return String.format(this.description.isEmpty() ? "%s %s" : "%s %s (%s)", new Object[] { this.multiplicity, getAttributes(), this.description });
/*    */   }
/*    */ }


/* Location:              /home/yogesh/Downloads/VLL4J.jar!/net/java/vll/vll4j/api/NodeChoice.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */