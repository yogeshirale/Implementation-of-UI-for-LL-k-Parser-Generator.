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
/*    */ public class NodeLiteral
/*    */   extends NodeBase
/*    */ {
/*    */   public String literalName;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public NodeLiteral(String name)
/*    */   {
/* 26 */     this.literalName = name;
/*    */   }
/*    */   
/*    */   public String nodeType() {
/* 30 */     return "Literal";
/*    */   }
/*    */   
/*    */   public NodeLiteral clone()
/*    */   {
/* 35 */     NodeLiteral n = new NodeLiteral(this.literalName);
/* 36 */     n.copyFrom(this);
/* 37 */     return n;
/*    */   }
/*    */   
/*    */   public boolean getAllowsChildren()
/*    */   {
/* 42 */     return false;
/*    */   }
/*    */   
/*    */   public Object accept(VisitorBase v)
/*    */   {
/* 47 */     return v.visitLiteral(this);
/*    */   }
/*    */   
/*    */   public String getName()
/*    */   {
/* 52 */     return String.format(this.description.isEmpty() ? "%s %s %s" : "%s %s %s (%s)", new Object[] { this.multiplicity, this.literalName, getAttributes(), this.description });
/*    */   }
/*    */ }


/* Location:              /home/yogesh/Downloads/VLL4J.jar!/net/java/vll/vll4j/api/NodeLiteral.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */