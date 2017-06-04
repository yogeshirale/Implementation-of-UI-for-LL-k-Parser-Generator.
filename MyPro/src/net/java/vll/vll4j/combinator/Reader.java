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
/*    */ public abstract class Reader
/*    */ {
/*    */   public CharSequence source()
/*    */   {
/* 32 */     throw new NoSuchMethodError("not a char sequence reader");
/*    */   }
/*    */   
/*    */   public abstract boolean atEnd();
/*    */   
/*    */   public abstract char first();
/*    */   
/*    */   public abstract Reader rest();
/*    */   
/*    */   public int offset() {
/* 42 */     throw new NoSuchMethodError("not a char sequence reader");
/*    */   }
/*    */   
/*    */   public abstract int line();
/*    */   
/*    */   public abstract int column();
/*    */   
/*    */   public Reader drop(int n) {
/* 50 */     Reader r = this;
/* 51 */     for (int cnt = n; 
/* 52 */         cnt > 0; 
/* 53 */         cnt--) { r = r.rest();
/*    */     }
/* 55 */     return r;
/*    */   }
/*    */ }


/* Location:              /home/yogesh/Downloads/VLL4J.jar!/net/java/vll/vll4j/combinator/Reader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */