/*    */ package net.java.vll.vll4j.combinator;
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
/*    */ class Some<T>
/*    */   extends Option<T>
/*    */ {
/*    */   private T t;
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
/* 35 */   public Some(T t) { this.t = t; }
/*    */   
/* 37 */   public boolean isEmpty() { return false; }
/*    */   
/* 39 */   public T get() { return (T)this.t; }
/*    */ }


/* Location:              /home/yogesh/Downloads/VLL4J.jar!/net/java/vll/vll4j/combinator/Some.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */