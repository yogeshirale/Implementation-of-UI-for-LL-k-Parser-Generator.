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
/*    */ public class NodeReference
/*    */   extends NodeBase
/*    */ {
/*    */   public String refRuleName;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public NodeReference(String name)
/*    */   {
/* 26 */     this.refRuleName = name;
/*    */   }
/*    */   
/*    */   public String nodeType() {
/* 30 */     return "Reference";
/*    */   }
/*    */   
/*    */   public NodeReference clone()
/*    */   {
/* 35 */     NodeReference n = new NodeReference(this.refRuleName);
/* 36 */     n.copyFrom(this);
/* 37 */     return n;
/*    */   }
/*    */   
/*    */   public Object accept(VisitorBase v)
/*    */   {
/* 42 */     return v.visitReference(this);
/*    */   }
/*    */   
/*    */   public String getName()
/*    */   {
/* 47 */     return String.format(this.description.isEmpty() ? "%s %s %s" : "%s %s %s (%s)", new Object[] { this.multiplicity, this.refRuleName, getAttributes(), this.description });
/*    */   }
/*    */ }


/* Location:              /home/yogesh/Downloads/VLL4J.jar!/net/java/vll/vll4j/api/NodeReference.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */