/*     */ package net.java.vll.vll4j.gui;
/*     */ 
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.script.ScriptException;
/*     */ import javax.swing.AbstractAction;
/*     */ import javax.swing.Action;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JFileChooser;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.SwingUtilities;
/*     */ import net.java.vll.vll4j.api.Forest;
/*     */ import net.java.vll.vll4j.api.NodeBase;
/*     */ import net.java.vll.vll4j.api.VisitorParserGeneration;
/*     */ import net.java.vll.vll4j.combinator.PackratParsers;
import net.java.vll.vll4j.combinator.Parsers;
/*     */ import net.java.vll.vll4j.combinator.Parsers.ParseResult;
/*     */ import net.java.vll.vll4j.combinator.Parsers.Parser;
/*     */ import net.java.vll.vll4j.combinator.Reader;
/*     */ import net.java.vll.vll4j.combinator.Utils;
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
/*     */ public class ManagerTesting
/*     */ {
/*     */   ManagerTesting(Vll4jGui gui)
/*     */   {
/*  41 */     this.gui = gui;
/*     */   }
/*     */   
/*     */   private void enableTestControls(boolean enable) {
/*  45 */     this.parseFileAction.setEnabled(enable);
/*  46 */     this.parseInputAction.setEnabled(enable);
/*  47 */     this.treeHandlerBasicAction.setEnabled(enable);
/*  48 */     this.treeHandlerStructuredAction.setEnabled(enable);
/*  49 */     this.traceAllAction.setEnabled(enable);
/*  50 */     this.useCharSequenceAction.setEnabled(enable);
/*  51 */     this.useStringAction.setEnabled(enable);
/*     */   }
/*     */   
/*  54 */   Action parseInputAction = new AbstractAction("Parse text", Resources.alignLeft16)
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/*  57 */       ManagerTesting.this.myThread = new Thread()
/*     */       {
/*     */         public void run() {
/*  60 */           ManagerTesting.this.parseStopAction.setEnabled(true);
/*  61 */           ManagerTesting.this.enableTestControls(false);
/*  62 */           ManagerTesting.this.runner(false);
/*  63 */           ManagerTesting.this.enableTestControls(true);
/*  64 */           ManagerTesting.this.parseStopAction.setEnabled(false);
/*  65 */           System.out.flush();
/*     */         }
/*  67 */       };
/*  68 */       ManagerTesting.this.myThread.start();
/*     */     }
/*     */   };
/*     */   
/*  72 */   Action parseFileAction = new AbstractAction("Parse file", Resources.host16)
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/*  75 */       ManagerTesting.this.myThread = new Thread()
/*     */       {
/*     */         public void run() {
/*  78 */           ManagerTesting.this.parseStopAction.setEnabled(true);
/*  79 */           ManagerTesting.this.enableTestControls(false);
/*  80 */           if (ManagerTesting.this.fileChooser == null) {
/*  81 */             ManagerTesting.this.fileChooser = new JFileChooser();
/*  82 */             ManagerTesting.this.fileChooser.setDialogTitle("Open");
/*  83 */             ManagerTesting.this.fileChooser.setFileSelectionMode(2);
/*  84 */             ManagerTesting.this.fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
/*     */           }
/*  86 */           if (ManagerTesting.this.fileChooser.showOpenDialog(ManagerTesting.this.gui) == 0) {
/*     */             try {
/*  88 */               ManagerTesting.this.runner(true);
/*     */             } catch (Throwable e) {
/*  90 */               e.printStackTrace();
/*     */             }
/*     */           }
/*  93 */           ManagerTesting.this.enableTestControls(true);
/*  94 */           ManagerTesting.this.parseStopAction.setEnabled(false);
/*  95 */           System.out.flush();
/*     */         }
/*  97 */       };
/*  98 */       ManagerTesting.this.myThread.start();
/*     */     }
/*     */   };
/*     */   
/* 102 */   Action parseStopAction = new AbstractAction("Stop parsing", Resources.stop16)
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 105 */       ManagerTesting.this.visitorParserGenerator.stopFlag[0] = true;
/*     */     }
/*     */   };
/*     */   
/* 109 */   Action treeHandlerBasicAction = new AbstractAction("Basic")
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 112 */       ManagerTesting.this.printStructured = false;
/*     */     }
/*     */   };
/*     */   
/* 116 */   Action treeHandlerStructuredAction = new AbstractAction("Structured")
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 119 */       ManagerTesting.this.printStructured = true;
/*     */     }
/*     */   };
/*     */   
/* 123 */   Action treeHandlerCustomAction = new AbstractAction("Custom")
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 126 */       ManagerTesting.this.printStructured = false;
/*     */     }
/*     */   };
/*     */   
/* 130 */   Action useCharSequenceAction = new AbstractAction("CharSequence")
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 133 */       ManagerTesting.this.useRichCharSequence = true;
/*     */     }
/*     */   };
/*     */   
/* 137 */   Action useStringAction = new AbstractAction("String")
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 140 */       ManagerTesting.this.useRichCharSequence = false;
/*     */     }
/*     */   };
/*     */   
/* 144 */   Action traceAllAction = new AbstractAction("Trace all")
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 147 */       ManagerTesting.this.traceAll = (!ManagerTesting.this.traceAll);
/*     */     }
/*     */   };
/*     */   
/* 151 */   Action logClearAction = new AbstractAction("Clear log", Resources.clear16)
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 154 */       ManagerTesting.this.gui.theTestingPanel.logClear();
/* 155 */       ManagerTesting.this.gui.theTestingPanel.logStatus.setText("");
/*     */     }
/*     */   };
/*     */   
/* 159 */   Action logCopyAction = new AbstractAction("Copy log", Resources.copy16)
/*     */   {
/*     */ 
/* 162 */     public void actionPerformed(ActionEvent e) { ManagerTesting.this.gui.theTestingPanel.logCopy(); }
/*     */   };
/*     */   private Vll4jGui gui;
/*     */   private VisitorParserGeneration visitorParserGenerator;
/*     */   
/* 167 */   private void appendStatus(final String status, final boolean reset) { SwingUtilities.invokeLater(new Thread()
/*     */     {
/*     */       public void run() {
/* 170 */         if (reset) {
/* 171 */           ManagerTesting.this.gui.theTestingPanel.logStatus.setText(status);
/*     */         } else {
/* 173 */           ManagerTesting.this.gui.theTestingPanel.logStatus.setText(ManagerTesting.this.gui.theTestingPanel.logStatus.getText() + status);
/*     */         }
/*     */       }
/*     */     }); }
/*     */   
/*     */   private File[] dredgeFiles(File f) {
/* 179 */     if (f.isDirectory()) {
/* 180 */       List<File> lf = new ArrayList();
/* 181 */       dredgeFiles(f, lf);
/* 182 */       return (File[])lf.toArray(new File[lf.size()]);
/*     */     }
/* 184 */     return new File[] { f };
/*     */   }
/*     */   
/*     */   private void dredgeFiles(File root, List<File> lf) {
/* 188 */     File[] files = root.listFiles();
/* 189 */     for (File f : files) {
/* 190 */       if (f.isDirectory()) {
/* 191 */         dredgeFiles(f, lf);
/*     */       } else {
/* 193 */         lf.add(f);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void runner(boolean fromFile) {
/* 199 */     NodeBase apex = this.gui.theTreePanel.rootNode;
/* 200 */     long t0 = System.currentTimeMillis();
/* 201 */     this.visitorParserGenerator = new VisitorParserGeneration(this.gui.theForest, this.gui.packratParsers, this.traceAll);
/* 202 */     this.gui.theForest.bindings.put("vllParserTestInput", this.gui.theTestingPanel.inputArea);
/* 203 */     this.gui.theForest.bindings.put("vllParserLog", this.gui.theTestingPanel.logArea);
/* 204 */     Parsers.Parser<? extends Object> parser = (Parsers.Parser)apex.accept(this.visitorParserGenerator);
/* 205 */     if (!this.visitorParserGenerator.parserGeneratedOk) {
/* 206 */       JOptionPane.showMessageDialog(this.gui, "Can't generate parser\nReview rule definitions", fromFile ? "ERROR - Parse file" : "ERROR - Parse input", 0);
/*     */       
/* 208 */       appendStatus(" Can't generate parser - Review rule definitions", true);
/* 209 */       return;
/*     */     }
/* 211 */     long t1 = System.currentTimeMillis();
/* 212 */     appendStatus(String.format(" Combinators: %d ms", new Object[] { Long.valueOf(t1 - t0) }), true);
/*     */     
/* 214 */     File inFile = null;
/* 215 */     if (fromFile) {
/* 216 */       inFile = this.fileChooser.getSelectedFile();
/*     */     }
/* 218 */     if ((fromFile) && (inFile.isDirectory())) {
/* 219 */       this.gui.theTestingPanel.setMultiFileLog(false);
/* 220 */       inFile = this.fileChooser.getSelectedFile();
/* 221 */       t0 = System.currentTimeMillis();
/* 222 */       int countOk = 0;int countNotOk = 0;
/* 223 */       for (File f : dredgeFiles(inFile)) {
/* 224 */         t1 = System.currentTimeMillis();
/*     */         try {
/* 226 */           ReaderFile readerFile = new ReaderFile(f, this.useRichCharSequence);
/* 227 */           this.gui.theForest.bindings.put("vllSource", readerFile.source());
/* 228 */           Parsers.ParseResult pr = this.gui.packratParsers.parseAll(parser, readerFile);
/* 229 */           long t2 = System.currentTimeMillis();
/* 230 */           if (pr.successful()) {
/* 231 */             countOk++;
/* 232 */             System.out.printf("%s (%d bytes %d ms): OK%n", new Object[] { f.getAbsolutePath(), Long.valueOf(f.length()), Long.valueOf(t2 - t1) });
/*     */           }
/*     */           else {
/* 235 */             countNotOk++;
/* 236 */             System.err.printf("%s (%d bytes %d ms): ERROR (line=%d, col=%d)%n", new Object[] { f.getAbsolutePath(), Long.valueOf(f.length()), Long.valueOf(t2 - t1), Integer.valueOf(pr.next().line()), Integer.valueOf(pr.next().column()) });
/*     */           }
/*     */         }
/*     */         catch (Throwable t)
/*     */         {
/* 241 */           countNotOk++;
/* 242 */           if ((t.getCause() instanceof ScriptException)) {
/* 243 */             t.printStackTrace();
/* 244 */             JOptionPane.showMessageDialog(this.gui, "Error in user-provided action-code\nDetails in stack-trace", "Action-code error", 0);
/*     */             
/* 246 */             break; }
/* 247 */           if ((t.getCause() instanceof StackOverflowError)) {
/* 248 */             System.err.printf("%s: ERROR: %s%n", new Object[] { f.getAbsolutePath(), t.getMessage() });
/*     */           } else {
/* 250 */             if ((t.getCause() instanceof IOException)) {
/* 251 */               System.err.printf("User-Requested STOP%n", new Object[0]);
/* 252 */               break;
/*     */             }
/* 254 */             System.err.printf("Internal error: %s(%s)%n", new Object[] { t.getClass().getName(), t.getMessage() });
/* 255 */             t.printStackTrace();
/*     */           }
/*     */         }
/* 258 */         appendStatus(String.format(" %d Ok, %d NOk in %d ms", new Object[] { Integer.valueOf(countOk), Integer.valueOf(countNotOk), Long.valueOf(t1 - t0) }), true);
/*     */       }
/*     */     } else {
/* 261 */       this.gui.theTestingPanel.setMultiFileLog(true);
/* 262 */       t0 = System.currentTimeMillis();
/*     */       try {
/* 264 */         Reader reader = fromFile ? new ReaderFile(inFile, this.useRichCharSequence) : new ReaderTextArea(this.gui.theTestingPanel.inputArea, this.useRichCharSequence);
/*     */         
/* 266 */         this.gui.theForest.bindings.put("vllSource", reader.source());
/* 267 */         Parsers.ParseResult pr = this.gui.packratParsers.parseAll(parser, reader);
/* 268 */         t1 = System.currentTimeMillis();
/* 269 */         appendStatus(String.format(", Parser: %d ms", new Object[] { Long.valueOf(t1 - t0) }), false);
/* 270 */         if (pr.successful()) {
/* 271 */           t0 = System.currentTimeMillis();
/* 272 */           String ast = Utils.dumpValue(pr.get(), this.printStructured);
/* 273 */           t1 = System.currentTimeMillis();
/* 274 */           appendStatus(String.format(", AST->String: %d ms", new Object[] { Long.valueOf(t1 - t0) }), false);
/* 275 */           t0 = System.currentTimeMillis();
/* 276 */           System.out.println(ast);
/* 277 */           System.out.println();
/* 278 */           t1 = System.currentTimeMillis();
/* 279 */           appendStatus(String.format(", Printing: %d ms", new Object[] { Long.valueOf(t1 - t0) }), false);
/*     */         } else {
/* 281 */           System.err.printf("%s%n", new Object[] { this.gui.packratParsers.dumpResult(pr) });
/*     */         }
/*     */       } catch (Throwable t) {
/* 284 */         if ((t.getCause() instanceof ScriptException)) {
/* 285 */           JOptionPane.showMessageDialog(this.gui, "Error in user-provided action-code\nSee details in stack-trace", "Action-code error", 0);
/*     */           
/* 287 */           t.printStackTrace();
/* 288 */         } else if ((t.getCause() instanceof IOException)) {
/* 289 */           System.err.printf("User-Requested STOP%n", new Object[0]);
/*     */         } else {
/* 291 */           System.err.printf("Internal error: %s(%s)%n", new Object[] { t.getClass().getName(), t.getMessage() });
/* 292 */           t.printStackTrace();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/* 300 */   private Thread myThread = null;
/* 301 */   private JFileChooser fileChooser = null;
/* 302 */   private boolean traceAll = false;
/* 303 */   private boolean useRichCharSequence = false;
/* 304 */   private boolean printStructured = false;
/*     */ }


/* Location:              /home/yogesh/Downloads/VLL4J.jar!/net/java/vll/vll4j/gui/ManagerTesting.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */
