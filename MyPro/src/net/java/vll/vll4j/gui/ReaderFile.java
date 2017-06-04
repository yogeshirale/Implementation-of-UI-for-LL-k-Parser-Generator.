/*    */ package net.java.vll.vll4j.gui;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.File;
/*    */ import java.io.FileReader;
/*    */ import java.io.IOException;
/*    */ import net.java.vll.vll4j.RichCharSequence;
/*    */ import net.java.vll.vll4j.combinator.Reader;
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
/*    */ public class ReaderFile
/*    */   extends Reader
/*    */ {
/*    */   public ReaderFile(File theFile, boolean useRichCharSequence)
/*    */   {
/* 33 */     if (!theFile.exists())
/* 34 */       throw new IllegalArgumentException("Nonexistent file");
/* 35 */     BufferedReader br = null;
/* 36 */     FileReader fr = null;
/*    */     try {
/* 38 */       fr = new FileReader(theFile);
/* 39 */       br = new BufferedReader(fr);
/* 40 */       StringBuilder sb = new StringBuilder();
/*    */       String line;
/* 42 */       while ((line = br.readLine()) != null)
/* 43 */         sb.append(line).append('\n');
/* 44 */       this.buffer = (useRichCharSequence ? new RichCharSequence(sb.toString()) : sb.toString()); return;
/*    */     } catch (Exception ex) {}finally {
/*    */       try {
/* 47 */         fr.close(); } catch (IOException ex) {}
/* 48 */       try { br.close();
/*    */       } catch (IOException ex) {}
/*    */     }
/*    */   }
/*    */   
/* 53 */   private ReaderFile(CharSequence b) { this.buffer = b; }
/*    */   
/*    */ 
/*    */ 
/* 57 */   public CharSequence source() { return this.buffer; }
/*    */   
/* 59 */   public int offset() { return this.offset; }
/*    */   
/* 61 */   public boolean atEnd() { return this.offset >= this.buffer.length(); }
/*    */   
/* 63 */   public char first() { return this.buffer.charAt(this.offset); }
/*    */   
/*    */   public ReaderFile rest() {
/* 66 */     return drop(1);
/*    */   }
/*    */   
/* 69 */   public int line() { return this.line; }
/*    */   
/* 71 */   public int column() { return this.column; }
/*    */   
/*    */   public ReaderFile drop(int nbrToDrop) {
/* 74 */     CharSequence src = this.buffer;
/* 75 */     if (this.offset + nbrToDrop > src.length())
/* 76 */       throw new IllegalArgumentException();
/* 77 */     ReaderFile csr = new ReaderFile(this.buffer);
/* 78 */     this.offset += nbrToDrop;csr.line = this.line;csr.column = this.column;
/* 79 */     for (int i = 0; i < nbrToDrop; i++) {
/* 80 */       if (src.charAt(this.offset + i) == '\n') {
/* 81 */         csr.line += 1;
/* 82 */         csr.column = 1;
/*    */       } else {
/* 84 */         csr.column += 1;
/*    */       }
/*    */     }
/* 87 */     return csr;
/*    */   }
/*    */   
/* 90 */   private int offset = 0;
/* 91 */   private int line = 1; private int column = 1;
/*    */   private CharSequence buffer;
/*    */ }


/* Location:              /home/yogesh/Downloads/VLL4J.jar!/net/java/vll/vll4j/gui/ReaderFile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */