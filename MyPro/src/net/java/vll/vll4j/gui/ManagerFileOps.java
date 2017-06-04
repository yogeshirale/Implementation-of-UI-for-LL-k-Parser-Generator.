/*     */ package net.java.vll.vll4j.gui;
/*     */ 
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import javax.swing.AbstractAction;
/*     */ import javax.swing.Action;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JFileChooser;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.filechooser.FileFilter;
/*     */ import net.java.vll.vll4j.api.Forest;
/*     */ import net.java.vll.vll4j.api.NodeBase;
/*     */ import net.java.vll.vll4j.combinator.PackratParsers;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ManagerFileOps
/*     */ {
/*     */   ManagerFileOps(Vll4jGui gui)
/*     */   {
/*  40 */     this.gui = gui;
/*     */   }
/*     */   
/*  43 */   Action fileNewAction = new AbstractAction("New", Resources.new16)
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/*  46 */       ManagerFileOps.this.grammarFile = null;
/*  47 */       ManagerFileOps.this.gui.reset(true);
/*  48 */       ManagerFileOps.this.gui.theRuleManager.reset();
/*     */     }
/*     */   };
/*     */   
/*     */   void openInputStream(InputStream is, boolean tokensOnly) {
/*     */     try {
/*  54 */       this.gui.theForest.openInputStream(is, tokensOnly);
/*  55 */       if (!tokensOnly) {
/*  56 */         this.gui.packratParsers.whiteSpaceRegex = this.gui.theForest.whiteSpace;
/*  57 */         this.gui.packratParsers.commentSpecRegex = this.gui.theForest.comment;
/*  58 */         this.gui.packratParsers.resetWhitespace();
/*  59 */         this.gui.theRuleManager.theComboBox.setAction(null);
/*  60 */         this.gui.theRuleManager.theComboBox.removeAllItems();
/*  61 */         for (String ruleName : this.gui.theForest.ruleBank.keySet()) {
/*  62 */           this.gui.theRuleManager.theComboBox.addItem(ruleName);
/*     */         }
/*  64 */         this.gui.theRuleManager.theComboBox.setMaximumSize(this.gui.theRuleManager.theComboBox.getPreferredSize());
/*  65 */         this.gui.theRuleManager.theComboBox.setAction(this.gui.theRuleManager.comboBoxAction);
/*  66 */         this.gui.theTreePanel.setRuleName((String)this.gui.theRuleManager.theComboBox.getItemAt(0));
/*     */       }
/*     */     } catch (Exception ex) {
/*  69 */       ex.printStackTrace();
/*  70 */       JOptionPane.showMessageDialog(this.gui, String.format("Error processing file: %s", new Object[] { ex }), "ERROR - File open", 0);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*  75 */   Action fileOpenAction = new AbstractAction("Open", Resources.open16)
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/*  78 */       JFileChooser fc = ManagerFileOps.this.getFileChooser("Open");
/*  79 */       if (fc.showOpenDialog(ManagerFileOps.this.gui) != 0) {
/*  80 */         return;
/*     */       }
/*  82 */       ManagerFileOps.this.grammarFile = fc.getSelectedFile();
/*     */       try {
/*  84 */         InputStream is = new FileInputStream(ManagerFileOps.this.grammarFile);
/*  85 */         ManagerFileOps.this.gui.reset(false);
/*  86 */         ManagerFileOps.this.openInputStream(is, false);
/*  87 */         ManagerFileOps.this.gui.setGrammarName(ManagerFileOps.this.grammarFile.getName());
/*  88 */         ManagerFileOps.this.gui.theRuleManager.reset();
/*  89 */         is.close();
/*     */       } catch (Exception ex) {
/*  91 */         JOptionPane.showMessageDialog(ManagerFileOps.this.gui, String.format("%s", new Object[] { ex }), "ERROR - File open", 0);
/*     */         
/*  93 */         ex.printStackTrace();
/*     */       }
/*     */     }
/*     */   };
/*     */   
/*  98 */   Action importTokenAction = new AbstractAction("Import tokens", Resources.import16)
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 101 */       JFileChooser fc = ManagerFileOps.this.getFileChooser("Import tokens");
/* 102 */       if (fc.showOpenDialog(ManagerFileOps.this.gui) != 0) {
/* 103 */         return;
/*     */       }
/* 105 */       ManagerFileOps.this.grammarFile = fc.getSelectedFile();
/*     */       try {
/* 107 */         InputStream is = new FileInputStream(ManagerFileOps.this.grammarFile);
/* 108 */         ManagerFileOps.this.openInputStream(is, true);
/* 109 */         is.close();
/*     */       } catch (Exception ex) {
/* 111 */         JOptionPane.showMessageDialog(ManagerFileOps.this.gui, String.format("%s", new Object[] { ex }), "ERROR - Import tokens", 0);
/*     */         
/* 113 */         ex.printStackTrace();
/*     */       }
/*     */     }
/*     */   };
/*     */   
/*     */   private void writeFile(boolean tokensOnly) {
/* 119 */     PrintWriter pw = null;
/*     */     try {
/* 121 */       pw = new PrintWriter(this.grammarFile);
/*     */     } catch (Exception ex) {
/* 123 */       JOptionPane.showMessageDialog(this.gui, ex, "ERROR - Save file", 0);
/* 124 */       return;
/*     */     }
/* 126 */     pw.println("<?xml version=\"1.0\"?>\n");
/* 127 */     pw.println("<!-- *****************  Do not edit  ***************** -->");
/* 128 */     pw.println("<!-- Generated by VisualLangLab (http://vll.java.net/) -->");
/* 129 */     pw.println("<!-- *****************  Do not edit  ***************** -->");
/* 130 */     pw.println("<VLL-Grammar>");
/* 131 */     pw.println("  <Tokens>");
/* 132 */     for (Map.Entry<String, String> me : this.gui.theForest.tokenBank.entrySet()) {
/* 133 */       String value = (String)me.getValue();
/* 134 */       if (value.startsWith("L")) {
/* 135 */         pw.printf("    <Literal Name=\"%s\" Pattern=\"%s\" />%n", new Object[] { Utils.encode4xml((String)me.getKey()), Utils.encode4xml(value.substring(1)) });
/*     */       }
/*     */       else {
/* 138 */         pw.printf("    <Regex Name=\"%s\" Pattern=\"%s\" />%n", new Object[] { Utils.encode4xml((String)me.getKey()), Utils.encode4xml(value).substring(1) });
/*     */       }
/*     */     }
/*     */     
/* 142 */     pw.println("  </Tokens>");
/* 143 */     if (!tokensOnly) {
/* 144 */       pw.printf("  <Whitespace>%s</Whitespace>%n", new Object[] { Utils.encode4xml(this.gui.packratParsers.whiteSpaceRegex) });
/*     */       
/* 146 */       pw.printf("  <Comments>%s</Comments>%n", new Object[] { Utils.encode4xml(this.gui.packratParsers.commentSpecRegex) });
/*     */       
/* 148 */       pw.println("  <Parsers>");
/* 149 */       VisitorXmlGeneration xmlWriter = new VisitorXmlGeneration(pw);
/* 150 */       for (Map.Entry<String, NodeBase> me : this.gui.theForest.ruleBank.entrySet()) {
/* 151 */         ((NodeBase)me.getValue()).accept(xmlWriter);
/*     */       }
/* 153 */       pw.println("  </Parsers>");
/*     */     }
/* 155 */     pw.println("</VLL-Grammar>");
/* 156 */     pw.close();
/* 157 */     this.gui.setGrammarName(this.grammarFile.getName());
/*     */   }
/*     */   
/* 160 */   Action fileSaveAction = new AbstractAction("Save", Resources.save16)
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 163 */       if (ManagerFileOps.this.grammarFile == null) {
/* 164 */         JFileChooser fc = ManagerFileOps.this.getFileChooser("Save");
/* 165 */         if (fc.showOpenDialog(ManagerFileOps.this.gui) != 0) {
/* 166 */           return;
/*     */         }
/* 168 */         ManagerFileOps.this.grammarFile = fc.getSelectedFile();
/* 169 */         if ((ManagerFileOps.this.grammarFile.exists()) && 
/* 170 */           (0 != JOptionPane.showConfirmDialog(ManagerFileOps.this.gui, "Overwrite existing file?", "Save As", 0, 3))) {
/* 171 */           return;
/*     */         }
/*     */       }
/*     */       
/* 175 */       ManagerFileOps.this.writeFile(false);
/*     */     }
/*     */   };
/*     */   
/* 179 */   Action fileSaveAsAction = new AbstractAction("Save As", Resources.saveAs16)
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 182 */       JFileChooser fc = ManagerFileOps.this.getFileChooser("Save As");
/* 183 */       if (fc.showOpenDialog(ManagerFileOps.this.gui) != 0) {
/* 184 */         return;
/*     */       }
/* 186 */       ManagerFileOps.this.grammarFile = fc.getSelectedFile();
/* 187 */       if ((ManagerFileOps.this.grammarFile.exists()) && 
/* 188 */         (0 != JOptionPane.showConfirmDialog(ManagerFileOps.this.gui, "Overwrite existing file?", "Save As", 0, 3))) {
/* 189 */         return;
/*     */       }
/*     */       
/* 192 */       ManagerFileOps.this.writeFile(false);
/*     */     }
/*     */   };
/*     */   
/* 196 */   Action exportTokenAction = new AbstractAction("Export tokens", Resources.export16)
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 199 */       JFileChooser fc = ManagerFileOps.this.getFileChooser("Export tokens");
/* 200 */       if (fc.showOpenDialog(ManagerFileOps.this.gui) != 0) {
/* 201 */         return;
/*     */       }
/* 203 */       ManagerFileOps.this.grammarFile = fc.getSelectedFile();
/* 204 */       if ((ManagerFileOps.this.grammarFile.exists()) && 
/* 205 */         (0 != JOptionPane.showConfirmDialog(ManagerFileOps.this.gui, "Overwrite existing file?", "Save As", 0, 3))) {
/* 206 */         return;
/*     */       }
/*     */       
/* 209 */       ManagerFileOps.this.writeFile(true);
/*     */     }
/*     */   };
/*     */   
/* 213 */   Action fileExitAction = new AbstractAction("Exit")
/*     */   {
/*     */ 
/* 216 */     public void actionPerformed(ActionEvent e) { System.exit(0); }
/*     */   };
/*     */   Vll4jGui gui;
/*     */   private JFileChooser fileChooser;
/*     */   
/* 221 */   private JFileChooser getFileChooser(String title) { if (this.fileChooser == null) {
/* 222 */       this.fileChooser = new JFileChooser();
/* 223 */       this.fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
/* 224 */       this.fileChooser.setFileSelectionMode(0);
/* 225 */       this.fileChooser.setFileFilter(new FileFilter() {
/*     */         public String getDescription() {
/* 227 */           return "VisualLangLab grammar";
/*     */         }
/*     */         
/* 230 */         public boolean accept(File f) { return (f.isDirectory()) || (f.getName().endsWith(".vll")) || (f.getName().endsWith(".VLL")); }
/*     */       });
/*     */     }
/*     */     
/* 234 */     this.fileChooser.setDialogTitle(title);
/* 235 */     this.fileChooser.setApproveButtonText((title.charAt(0) == 'S') || (title.charAt(0) == 'E') ? "Save" : "Open");
/*     */     
/* 237 */     return this.fileChooser;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/* 242 */   private File grammarFile = null;
/*     */ }


/* Location:              /home/yogesh/Downloads/VLL4J.jar!/net/java/vll/vll4j/gui/ManagerFileOps.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */