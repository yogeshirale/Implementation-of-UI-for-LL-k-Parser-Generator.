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
/*    */ 
/*    */ 
/*    */ 
/*    */ class None<T>
/*    */   extends Option<T>
/*    */ {
/* 45 */   public boolean isEmpty() { return true; }
/*    */   
/* 47 */   public T get() { throw new UnsupportedOperationException(); }
/*    */ }


/* Location:              /home/yogesh/Downloads/VLL4J.jar!/net/java/vll/vll4j/combinator/None.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */