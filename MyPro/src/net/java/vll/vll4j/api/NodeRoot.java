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
/*    */ public class NodeRoot
/*    */   extends NodeBase
/*    */ {
/*    */   public String ruleName;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public NodeRoot(String ruleName)
/*    */   {
/* 26 */     this.ruleName = ruleName;
/*    */   }
/*    */   
/*    */   public String nodeType() {
/* 30 */     return "Root";
/*    */   }
/*    */   
/*    */   public NodeRoot clone()
/*    */   {
/* 35 */     NodeRoot n = new NodeRoot(this.ruleName);
/* 36 */     for (int i = 0; i < getChildCount(); i++) {
/* 37 */       n.add((NodeBase)((NodeBase)getChildAt(i)).clone());
/*    */     }
/* 39 */     n.copyFrom(this);
/* 40 */     return n;
/*    */   }
/*    */   
/*    */   public String nodeName() {
/* 44 */     return this.ruleName;
/*    */   }
/*    */   
/*    */   public Object accept(VisitorBase v)
/*    */   {
/* 49 */     return v.visitRoot(this);
/*    */   }
/*    */   
/*    */   public String getName()
/*    */   {
/* 54 */     return String.format(this.description.isEmpty() ? "%s %s" : "%s %s (%s)", new Object[] { this.ruleName, getAttributes(), this.description });
/*    */   }
/*    */   
/*    */ 
/*    */ 
/* 59 */   public boolean isPackrat = false;
/*    */ }


/* Location:              /home/yogesh/Downloads/VLL4J.jar!/net/java/vll/vll4j/api/NodeRoot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */