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
/*    */ public class NodeRepSep
/*    */   extends NodeBase
/*    */ {
/*    */   public NodeRepSep()
/*    */   {
/* 26 */     this.multiplicity = Multiplicity.ZeroOrMore;
/*    */   }
/*    */   
/*    */   public String nodeType() {
/* 30 */     return "RepSep";
/*    */   }
/*    */   
/*    */   public NodeRepSep clone()
/*    */   {
/* 35 */     NodeRepSep n = new NodeRepSep();
/* 36 */     for (int i = 0; i < getChildCount(); i++) {
/* 37 */       n.add((NodeBase)((NodeBase)getChildAt(i)).clone());
/*    */     }
/* 39 */     n.copyFrom(this);
/* 40 */     return n;
/*    */   }
/*    */   
/*    */   public Object accept(VisitorBase v)
/*    */   {
/* 45 */     return v.visitRepSep(this);
/*    */   }
/*    */   
/*    */   public String getName()
/*    */   {
/* 50 */     return String.format(this.description.isEmpty() ? "%s %s" : "%s %s (%s)", new Object[] { this.multiplicity, getAttributes(), this.description });
/*    */   }
/*    */ }


/* Location:              /home/yogesh/Downloads/VLL4J.jar!/net/java/vll/vll4j/api/NodeRepSep.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */