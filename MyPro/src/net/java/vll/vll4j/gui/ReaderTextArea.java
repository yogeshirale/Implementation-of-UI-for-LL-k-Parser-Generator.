/*    */ package net.java.vll.vll4j.gui;
/*    */ 
/*    */ import javax.swing.JTextArea;
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
/*    */ public class ReaderTextArea
/*    */   extends Reader
/*    */ {
/*    */   private boolean useRichCharSequence;
/*    */   private JTextArea textArea;
/*    */   
/*    */   public ReaderTextArea(JTextArea textArea, boolean useRichCharSequence)
/*    */   {
/* 30 */     this.textArea = textArea;
/* 31 */     this.useRichCharSequence = useRichCharSequence;
/* 32 */     this.source = (useRichCharSequence ? new RichCharSequence(textArea.getText()) : textArea.getText());
/*    */   }
/*    */   
/*    */   public CharSequence source() {
/* 36 */     if (this.useRichCharSequence) {
/* 37 */       if (this.source.equals(this.textArea.getText())) {
/* 38 */         return this.source;
/*    */       }
/* 40 */       this.source = new RichCharSequence(this.textArea.getText());
/* 41 */       return this.source;
/*    */     }
/*    */     
/* 44 */     return this.textArea.getText();
/*    */   }
/*    */   
/* 47 */   public int offset() { return this.offset; }
/*    */   
/* 49 */   public boolean atEnd() { return this.offset >= this.textArea.getText().length(); }
/*    */   
/* 51 */   public char first() { return this.textArea.getText().charAt(this.offset); }
/*    */   
/*    */   public ReaderTextArea rest() {
/* 54 */     return drop(1);
/*    */   }
/*    */   
/* 57 */   public int line() { return this.line; }
/*    */   
/* 59 */   public int column() { return this.column; }
/*    */   
/*    */   public ReaderTextArea drop(int nbrToDrop) {
/* 62 */     if (this.offset + nbrToDrop > this.textArea.getText().length())
/* 63 */       throw new IllegalArgumentException();
/* 64 */     ReaderTextArea csr = new ReaderTextArea(this.textArea, this.useRichCharSequence);
/* 65 */     this.offset += nbrToDrop;csr.line = this.line;csr.column = this.column;
/* 66 */     String src = this.textArea.getText();
/* 67 */     for (int i = 0; i < nbrToDrop; i++) {
/* 68 */       if (src.charAt(this.offset + i) == '\n') {
/* 69 */         csr.line += 1;
/* 70 */         csr.column = 1;
/*    */       } else {
/* 72 */         csr.column += 1;
/*    */       }
/*    */     }
/* 75 */     return csr;
/*    */   }
/*    */   
/*    */ 
/* 79 */   private int offset = 0;
/* 80 */   private int line = 1; private int column = 1;
/*    */   private CharSequence source;
/*    */ }


/* Location:              /home/yogesh/Downloads/VLL4J.jar!/net/java/vll/vll4j/gui/ReaderTextArea.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */