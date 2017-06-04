/*     */ package net.java.vll.vll4j.combinator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CharSequenceReader
/*     */   extends Reader
/*     */ {
/*     */   private CharSequence source;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public CharSequenceReader(CharSequence source, int offset)
/*     */   {
/*  32 */     assert (offset >= 0);
/*  33 */     this.source = source;
/*  34 */     this.offset = offset;
/*     */   }
/*     */   
/*     */   public CharSequenceReader(CharSequence source) {
/*  38 */     this.source = source;
/*  39 */     this.offset = 0;
/*     */   }
/*     */   
/*     */   public CharSequence source()
/*     */   {
/*  44 */     return this.source;
/*     */   }
/*     */   
/*     */   public int offset()
/*     */   {
/*  49 */     return this.offset;
/*     */   }
/*     */   
/*     */   public boolean atEnd()
/*     */   {
/*  54 */     return this.offset >= this.source.length();
/*     */   }
/*     */   
/*     */   public char first()
/*     */   {
/*  59 */     return this.offset < this.source.length() ? this.source.charAt(this.offset) : EofCh;
/*     */   }
/*     */   
/*     */   public Reader rest()
/*     */   {
/*  64 */     return drop(1);
/*     */   }
/*     */   
/*     */   public int line()
/*     */   {
/*  69 */     return this.line;
/*     */   }
/*     */   
/*     */   public int column()
/*     */   {
/*  74 */     return this.column;
/*     */   }
/*     */   
/*     */   public CharSequenceReader drop(int nbrToDrop)
/*     */   {
/*  79 */     if (this.offset + nbrToDrop > this.source.length()) {
/*  80 */       throw new IllegalArgumentException();
/*     */     }
/*  82 */     CharSequenceReader csr = new CharSequenceReader(this.source);
/*  83 */     csr.line = this.line;
/*  84 */     csr.column = this.column;
/*  85 */     this.offset += nbrToDrop;
/*  86 */     for (int i = 0; i < nbrToDrop; i++) {
/*  87 */       if (this.source.charAt(this.offset + i) == '\n') {
/*  88 */         csr.line += 1;
/*  89 */         csr.column = 1;
/*     */       } else {
/*  91 */         csr.column += 1;
/*     */       }
/*     */     }
/*  94 */     return csr;
/*     */   }
/*     */   
/*     */ 
/*  98 */   private int offset = 0;
/*  99 */   private int line = 1; private int column = 1;
/* 100 */   private static char EofCh = '\032';
/*     */ }


/* Location:              /home/yogesh/Downloads/VLL4J.jar!/net/java/vll/vll4j/combinator/CharSequenceReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */