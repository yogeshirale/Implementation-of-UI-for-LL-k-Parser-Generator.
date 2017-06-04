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
/*    */ public class NodeRegex
/*    */   extends NodeBase
/*    */ {
/*    */   public String regexName;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public NodeRegex(String name)
/*    */   {
/* 26 */     this.regexName = name;
/*    */   }
/*    */   
/*    */   public String nodeType() {
/* 30 */     return "Regex";
/*    */   }
/*    */   
/*    */   public NodeRegex clone()
/*    */   {
/* 35 */     NodeRegex n = new NodeRegex(this.regexName);
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
/* 47 */     return v.visitRegex(this);
/*    */   }
/*    */   
/*    */   public String getName()
/*    */   {
/* 52 */     return String.format(this.description.isEmpty() ? "%s %s %s" : "%s %s %s (%s)", new Object[] { this.multiplicity, this.regexName, getAttributes(), this.description });
/*    */   }
/*    */ }


/* Location:              /home/yogesh/Downloads/VLL4J.jar!/net/java/vll/vll4j/api/NodeRegex.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */