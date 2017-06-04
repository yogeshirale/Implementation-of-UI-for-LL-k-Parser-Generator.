/*     */ package net.java.vll.vll4j.gui;
/*     */ 
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import javax.swing.Action;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JSplitPane;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.event.CaretEvent;
/*     */ import javax.swing.event.CaretListener;
/*     */ import javax.swing.text.Document;
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
/*     */ public class PanelTesting
/*     */   extends JPanel
/*     */ {
/*     */   Vll4jGui theGui;
/*     */   
/*     */   PanelTesting(Vll4jGui theGui)
/*     */   {
/*  39 */     this.theGui = theGui;
/*  40 */     setLayout(new BorderLayout());
/*  41 */     JPanel eastPanel = new JPanel();
/*  42 */     eastPanel.setLayout(new BorderLayout());
/*  43 */     eastPanel.add(new JLabel("Parser Log", 0), "North");
/*  44 */     this.logArea.setOpaque(true);
/*  45 */     this.logArea.setFont(new Font("Monospaced", this.logArea.getFont().getStyle(), this.logArea.getFont().getSize()));
/*     */     
/*  47 */     this.logArea.setEditable(false);
/*  48 */     JPanel logBtnPanel = new JPanel();
/*  49 */     logBtnPanel.setLayout(new BorderLayout());
/*  50 */     logBtnPanel.add(this.logStatus, "Center");
/*  51 */     JButton helpButton2 = new JButton(theGui.theHelpFunctionsManager.displayHelpTestLog)
/*     */     {
/*     */       public Dimension getPreferredSize() {
/*  54 */         int h = super.getPreferredSize().height;
/*  55 */         return new Dimension(h, h);
/*     */       }
/*     */       
/*     */       public Dimension getMinimumSize() {
/*  59 */         return getPreferredSize();
/*     */       }
/*  61 */     };
/*  62 */     logBtnPanel.add(helpButton2, "East");
/*  63 */     eastPanel.add(new JScrollPane(this.logArea), "Center");
/*  64 */     eastPanel.add(logBtnPanel, "South");
/*  65 */     JPanel westPanel = new JPanel();
/*  66 */     westPanel.setLayout(new BorderLayout());
/*  67 */     westPanel.add(new JLabel("Parser Test Input", 0), "North");
/*  68 */     this.inputArea.setLineWrap(true);
/*  69 */     this.inputArea.addCaretListener(new CaretListener() {
/*     */       public void caretUpdate(CaretEvent e) {
/*  71 */         int dot = e.getDot();
/*  72 */         int line = 1;int col = 1;
/*  73 */         for (char ch : PanelTesting.this.inputArea.getText().substring(0, dot).toCharArray()) {
/*  74 */           if (ch == '\n') {
/*  75 */             line++;col = 1;
/*     */           } else {
/*  77 */             col++;
/*     */           }
/*     */         }
/*  80 */         PanelTesting.this.inputStatus.setText(String.format(" Line %d, Column %d", new Object[] { Integer.valueOf(line), Integer.valueOf(col) }));
/*     */       }
/*  82 */     });
/*  83 */     JPanel inputBtnPanel = new JPanel();
/*  84 */     inputBtnPanel.setLayout(new BorderLayout());
/*  85 */     inputBtnPanel.add(this.inputStatus, "Center");
/*  86 */     JButton helpButton1 = new JButton(theGui.theHelpFunctionsManager.displayHelpTestInput)
/*     */     {
/*     */       public Dimension getPreferredSize() {
/*  89 */         int h = super.getPreferredSize().height;
/*  90 */         return new Dimension(h, h);
/*     */       }
/*     */       
/*     */       public Dimension getMinimumSize() {
/*  94 */         return getPreferredSize();
/*     */       }
/*  96 */     };
/*  97 */     inputBtnPanel.add(helpButton1, "East");
/*  98 */     westPanel.add(new JScrollPane(this.inputArea), "Center");
/*  99 */     westPanel.add(inputBtnPanel, "South");
/* 100 */     JSplitPane sp = new JSplitPane(1, westPanel, eastPanel);
/*     */     
/* 102 */     sp.setDividerLocation(theGui.frameWidth / 5);
/* 103 */     add(sp, "Center");
/*     */   }
/*     */   
/*     */   void setMultiFileLog(boolean mf) {
/* 107 */     if (mf) {
/* 108 */       this.logArea.setLineWrap(true);
/*     */     } else {
/* 110 */       this.logArea.setLineWrap(false);
/*     */     }
/*     */   }
/*     */   
/*     */   void logClear() {
/* 115 */     this.logArea.errLines.clear();
/* 116 */     this.logArea.setText("");
/*     */   }
/*     */   
/*     */   void logCopy() {
/* 120 */     int selectionStart = this.logArea.getSelectionStart();
/* 121 */     int selectionEnd = this.logArea.getSelectionEnd();
/* 122 */     this.logArea.setSelectionStart(0);
/* 123 */     this.logArea.setSelectionEnd(this.logArea.getDocument().getLength());
/* 124 */     this.logArea.copy();
/* 125 */     this.logArea.setSelectionStart(selectionStart);
/* 126 */     this.logArea.setSelectionEnd(selectionEnd);
/*     */   }
/*     */   
/*     */   PrintStream getOutStream() {
/* 130 */     OutputStream os = new OutputStream() {
/* 131 */       StringBuilder sb = new StringBuilder();
/*     */       
/* 133 */       public void write(int b) { System.out.flush();
/* 134 */         this.sb.append((char)b);
/* 135 */         if (b == 10) {
/* 136 */           final String line = this.sb.toString();
/* 137 */           this.sb.setLength(0);
/* 138 */           SwingUtilities.invokeLater(new Runnable()
/*     */           {
/*     */             public void run() {
/* 141 */               PanelTesting.this.logArea.append(line);
/* 142 */               int len = PanelTesting.this.logArea.getText().length();
/* 143 */               PanelTesting.this.logArea.select(len, len);
/*     */             }
/*     */           });
/*     */         }
/*     */       }
/* 148 */     };
/* 149 */     return new PrintStream(os, true);
/*     */   }
/*     */   
/*     */   PrintStream getErrStream() {
/* 153 */     OutputStream os = new OutputStream() {
/* 154 */       StringBuilder sb = new StringBuilder();
/*     */       
/* 156 */       public void write(int b) { System.out.flush();
/* 157 */         this.sb.append((char)b);
/* 158 */         if (b == 10) {
/* 159 */           final String line = this.sb.toString().replace("\t", "        ");
/* 160 */           this.sb.setLength(0);
/* 161 */           SwingUtilities.invokeLater(new Runnable()
/*     */           {
/*     */             public void run() {
/* 164 */               PanelTesting.this.logArea.errLines.add(new Integer[] { Integer.valueOf(PanelTesting.this.logArea.getText().length()), Integer.valueOf(line.length()) });
/* 165 */               PanelTesting.this.logArea.append(line);
/* 166 */               int len = PanelTesting.this.logArea.getText().length();
/* 167 */               PanelTesting.this.logArea.select(len, len);
/*     */             }
/*     */           });
/*     */         }
/*     */       }
/* 172 */     };
/* 173 */     return new PrintStream(os, true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/* 178 */   JTextArea inputArea = new TextAreaCustom();
/* 179 */   LogTextArea logArea = new LogTextArea();
/* 180 */   private JLabel inputStatus = new JLabel();
/* 181 */   JLabel logStatus = new JLabel();
/*     */ }


/* Location:              /home/yogesh/Downloads/VLL4J.jar!/net/java/vll/vll4j/gui/PanelTesting.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */