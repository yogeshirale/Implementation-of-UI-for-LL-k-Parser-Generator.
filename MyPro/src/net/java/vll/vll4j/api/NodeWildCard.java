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
/*    */ public class NodeWildCard
/*    */   extends NodeBase
/*    */ {
/*    */   public String nodeType()
/*    */   {
/* 26 */     return "WildCard";
/*    */   }
/*    */   
/*    */   public NodeWildCard clone()
/*    */   {
/* 31 */     NodeWildCard n = new NodeWildCard();
/* 32 */     n.copyFrom(this);
/* 33 */     return n;
/*    */   }
/*    */   
/*    */   public boolean getAllowsChildren()
/*    */   {
/* 38 */     return false;
/*    */   }
/*    */   
/*    */   public Object accept(VisitorBase v)
/*    */   {
/* 43 */     return v.visitWildCard(this);
/*    */   }
/*    */   
/*    */   public String getName()
/*    */   {
/* 48 */     return String.format(this.description.isEmpty() ? "%s %s" : "%s %s (%s)", new Object[] { this.multiplicity, getAttributes(), this.description });
/*    */   }
/*    */ }


/* Location:              /home/yogesh/Downloads/VLL4J.jar!/net/java/vll/vll4j/api/NodeWildCard.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */