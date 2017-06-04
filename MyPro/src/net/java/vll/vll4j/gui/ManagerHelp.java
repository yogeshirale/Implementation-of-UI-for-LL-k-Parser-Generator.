/*     */ package net.java.vll.vll4j.gui;
/*     */ 
/*     */ import java.awt.Desktop;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.io.InputStream;
/*     */ import java.net.URI;
/*     */ import java.net.URL;
/*     */ import javax.swing.AbstractAction;
/*     */ import javax.swing.Action;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JTabbedPane;
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
/*     */ public class ManagerHelp
/*     */ {
/*     */   ManagerHelp(Vll4jGui gui)
/*     */   {
/*  33 */     this.gui = gui; Object[] 
/*  34 */       tmp269_266 = new Object[1];gui.getClass();tmp269_266[0] = "11.03";this.title = String.format("VisualLangLab %s", tmp269_266);
/*  35 */     if (Desktop.isDesktopSupported()) {
/*  36 */       this.desktop = Desktop.getDesktop();
/*     */     }
/*     */   }
/*     */   
/*  40 */   Action aboutAction = new AbstractAction("About VisualLangLab", Resources.information16)
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/*  43 */       JTabbedPane tp = new JTabbedPane();
/*  44 */       tp.add("VisualLangLab", new JLabel(Resources.splashImage));
/*  45 */       tp.add("Copyright", new JLabel(this.copyright));
/*  46 */       tp.add("Licenses", new JLabel(this.licenses));
/*  47 */       JOptionPane.showMessageDialog(ManagerHelp.this.gui, tp, ManagerHelp.this.title, -1); }
/*     */     
/*  49 */     String copyright = "<html><b>VisualLangLab (http://vll.java.net/)</b><br/>Copyright 2004, 2010, 2012, Sanjay Dasgupta <br/>(sanjay.dasgupta@gmail.com)<br/><br/>VisualLangLab is free software: you can redistribute it and/or <br/>modify it under the terms of the GNU General Public License as <br/>published by the Free Software Foundation, either version 3 of <br/>the License, or (at your option) any later version.<br/><br/>VisualLangLab is distributed in the hope that it will be useful,<br/>but WITHOUT ANY WARRANTY; without even the implied warranty<br/>of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.<br/>See the GNU General Public License for more details.<br/><br/>You should have received a copy of the GNU General Public License<br/>along with VisualLangLab. If not, see http://www.gnu.org/licenses/</html>";
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
/*  62 */     String licenses = "<html>VisualLangLab uses the following public resources:<br/><br/>1) Icon images from the Java look and feel Graphics Repository<br/>(http://java.sun.com/developer/techDocs/hi/repository/)<br/><br/>2) Some elements, rewritten in Java, of the <br/><i>scala.util.parsing.combinator</i> package of the Scala API. <br/>(http://www.scala-lang.org/api/current/index.html#package)<br/><br/>Copyright notices for the use and/or redistribution of these <br/>items are included in the <i>licenses</i> directory of the ZIP and JAR <br/>files of the distribution.<br/></html>";
/*     */   };
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
/*  75 */   Action versionCheck = new AbstractAction("Version check", Resources.tipOfTheDay16)
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/*  78 */       JOptionPane.showMessageDialog(ManagerHelp.this.gui, "New version check coming soon!", "Version check", -1);
/*     */     }
/*     */   };
/*     */   
/*     */ 
/*  83 */   Action sampleTdarExpr = new AbstractAction("TDAR-Expr")
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/*  86 */       JOptionPane.showMessageDialog(ManagerHelp.this.gui, this.msg, "TDAR-Expr", 1);
/*  87 */       InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("net/java/vll/vll4j/gui/resources/TDAR-Expr.vll");
/*     */       
/*  89 */       ManagerHelp.this.gui.reset(false);
/*  90 */       ManagerHelp.this.gui.theFileManager.openInputStream(is, false);
/*  91 */       ManagerHelp.this.gui.setGrammarName("TDAR-Expr");
/*  92 */       ManagerHelp.this.gui.theRuleManager.reset(); }
/*     */     
/*  94 */     String msg = "<html>This example is based on the parser described in <br/>section 3.1, <i>Recognizing Language Syntax</i>, <br/>of the book <i>The Definitive ANTLR Reference</i> <br>(http://pragprog.com/book/tpantlr/the-definitive-antlr-reference)<br/><br/>NOTE: Each 'statement' in the test input <br/>(including the last) must end with a NEWLINE<br/><br/>Read more about this parser at the following url:<br/>&nbsp;&nbsp;&nbsp;&nbsp;http://vll.java.net/examples/a-quick-tour.html";
/*     */   };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 105 */   Action sampleTdarExprActions = new AbstractAction("TDAR-Expr-Action")
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 108 */       JOptionPane.showMessageDialog(ManagerHelp.this.gui, this.msg, "TDAR-Expr-Action", 1);
/* 109 */       InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("net/java/vll/vll4j/gui/resources/TDAR-Expr-Action.vll");
/*     */       
/* 111 */       ManagerHelp.this.gui.reset(false);
/* 112 */       ManagerHelp.this.gui.theFileManager.openInputStream(is, false);
/* 113 */       ManagerHelp.this.gui.setGrammarName("TDAR-Expr-Action");
/* 114 */       ManagerHelp.this.gui.theRuleManager.reset(); }
/*     */     
/* 116 */     String msg = "<html>This example is based on the parser described in <br/>section 3.2, <i>Using Syntax to Drive Action Execution</i>, <br/>of the book <i>The Definitive ANTLR Reference</i> <br>(http://pragprog.com/book/tpantlr/the-definitive-antlr-reference)<br/><br/>Actions (JavaScript functions) are associated with some <br/> rule-tree nodes identified by the <i>action</i> attribute<br/><br/>NOTE: Each 'statement' in the test input <br/>(including the last) must end with a NEWLINE<br/><br/>Read more about this parser at the following url:<br/>&nbsp;&nbsp;&nbsp;&nbsp;http://vll.java.net/examples/a-quick-tour.html";
/*     */   };
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
/* 129 */   Action sampleTdarExprAst = new AbstractAction("TDAR-Expr-AST")
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 132 */       JOptionPane.showMessageDialog(ManagerHelp.this.gui, this.msg, "TDAR-Expr-AST", 1);
/* 133 */       InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("net/java/vll/vll4j/gui/resources/TDAR-Expr-AST.vll");
/*     */       
/* 135 */       ManagerHelp.this.gui.reset(false);
/* 136 */       ManagerHelp.this.gui.theFileManager.openInputStream(is, false);
/* 137 */       ManagerHelp.this.gui.setGrammarName("TDAR-Expr-AST");
/* 138 */       ManagerHelp.this.gui.theRuleManager.reset(); }
/*     */     
/* 140 */     String msg = "<html>This grammar supports discussion of the techinque in section 3.3, <br/><i>Evaluating Expressions Using an AST Intermediate Form</i>, <br/>of the book <i>The Definitive ANTLR Reference</i> <br>(http://pragprog.com/book/tpantlr/the-definitive-antlr-reference)<br/><br/>Further discussion of the use of this grammar can be <br/>found at the following url:<br/>&nbsp;&nbsp;&nbsp;&nbsp;http://vll.java.net/examples/a-quick-tour.html";
/*     */   };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 150 */   Action sampleTdarSimpleTreeInterpreter = new AbstractAction("TDAR-Simple-Tree-Based-Interpreter")
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 153 */       JOptionPane.showMessageDialog(ManagerHelp.this.gui, this.msg, "TDAR-Simple-Tree-Based-Interpreter", 1);
/* 154 */       InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("net/java/vll/vll4j/gui/resources/TDAR-Simple-Tree-Based-Interpreter.vll");
/*     */       
/* 156 */       ManagerHelp.this.gui.reset(false);
/* 157 */       ManagerHelp.this.gui.theFileManager.openInputStream(is, false);
/* 158 */       ManagerHelp.this.gui.setGrammarName("TDAR-Simple-Tree-Based-Interpreter");
/* 159 */       ManagerHelp.this.gui.theRuleManager.reset(); }
/*     */     
/* 161 */     String msg = "<html>This example is based on the parser described at the very end of <br/>section 3.3, <i>Evaluating Expressions Using an AST Intermediate Form</i>, <br/>of the book <i>The Definitive ANTLR Reference</i> <br/>(http://pragprog.com/book/tpantlr/the-definitive-antlr-reference)<br/><br/>Complete details can be found here: <br/> http://www.antlr.org/wiki/display/ANTLR3/Simple+tree-based+interpeter<br/><br/>You can find sample test input at the web-page above. <br/><br/>NOTE: Each 'statement' in the test input <br/>(including the last) must end with a NEWLINE<br/><br/>Read more about this parser at the following url:<br/>&nbsp;&nbsp;&nbsp;&nbsp;http://vll.java.net/examples/a-quick-tour.html";
/*     */   };
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
/* 175 */   Action samplePs2eArithExpr = new AbstractAction("PS2E-ArithExpr")
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 178 */       JOptionPane.showMessageDialog(ManagerHelp.this.gui, this.msg, "PS2E-ArithExpr", 1);
/* 179 */       InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("net/java/vll/vll4j/gui/resources/PS2E-ArithExpr.vll");
/*     */       
/* 181 */       ManagerHelp.this.gui.reset(false);
/* 182 */       ManagerHelp.this.gui.theFileManager.openInputStream(is, false);
/* 183 */       ManagerHelp.this.gui.setGrammarName("PS2E-ArithExpr");
/* 184 */       ManagerHelp.this.gui.theRuleManager.reset(); }
/*     */     
/* 186 */     String msg = "<html>This parser is based on the code at page 760 of \"Programming in Scala\"<br/>(http://www.artima.com/shop/programming_in_scala_2ed)<br/><br/>Another version of this parser with action-code that evaluages the<br>AST to a number is also available (P2SE-ArithExpr-Action).<br/><br/>IMPORTANT: Select the top-level parser (Expr) when running it.<br/>Sample input (remove quotes): \"(2 + 3) * (7 - 3)\"</html>";
/*     */   };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 195 */   Action samplePs2eSimpleJson = new AbstractAction("PS2E-SimpleJSON")
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 198 */       JOptionPane.showMessageDialog(ManagerHelp.this.gui, this.msg, "PS2E-SimpleJSON", 1);
/* 199 */       InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("net/java/vll/vll4j/gui/resources/PS2E-SimpleJSON.vll");
/*     */       
/* 201 */       ManagerHelp.this.gui.reset(false);
/* 202 */       ManagerHelp.this.gui.theFileManager.openInputStream(is, false);
/* 203 */       ManagerHelp.this.gui.setGrammarName("PS2E-SimpleJSON");
/* 204 */       ManagerHelp.this.gui.theRuleManager.reset(); }
/*     */     
/* 206 */     String msg = "<html>This parser is based on the code at page 764 of \"Programming in Scala\"<br/>(http://www.artima.com/shop/programming_in_scala_2ed)<br/><br/>IMPORTANT: Select the top-level parser (Value) when running it.<br/></html>";
/*     */   };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 213 */   Action samplePs2eArithExprAction = new AbstractAction("PS2E-ArithExpr-Action")
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 216 */       JOptionPane.showMessageDialog(ManagerHelp.this.gui, this.msg, "PS2E-ArithExpr-Action", 1);
/* 217 */       InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("net/java/vll/vll4j/gui/resources/PS2E-ArithExpr-Action.vll");
/*     */       
/* 219 */       ManagerHelp.this.gui.reset(false);
/* 220 */       ManagerHelp.this.gui.theFileManager.openInputStream(is, false);
/* 221 */       ManagerHelp.this.gui.setGrammarName("PS2E-ArithExpr-Action");
/* 222 */       ManagerHelp.this.gui.theRuleManager.reset(); }
/*     */     
/* 224 */     String msg = "<html>This parser is also based on the code at page 760 of \"Programming in Scala\"<br/>(http://www.artima.com/shop/programming_in_scala_2ed),<br/>but it also has actions that evaluate the AST to a number.<br><br/>IMPORTANT: Select the top-level parser (Expr) when running it.<br/>Sample input (remove quotes): \"(2 + 3) * (7 - 3)\"</html>";
/*     */   };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 232 */   Action samplePswpPayrollParserCombinators = new AbstractAction("PSWP-Payroll-Parser-Combinators")
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 235 */       JOptionPane.showMessageDialog(ManagerHelp.this.gui, this.msg, "PSWP-Payroll-Parser-Combinators", 1);
/* 236 */       InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("net/java/vll/vll4j/gui/resources/PSWP-Payroll-Parser-Combinators.vll");
/*     */       
/* 238 */       ManagerHelp.this.gui.reset(false);
/* 239 */       ManagerHelp.this.gui.theFileManager.openInputStream(is, false);
/* 240 */       ManagerHelp.this.gui.setGrammarName("PSWP-Payroll-Parser-Combinators");
/* 241 */       ManagerHelp.this.gui.theRuleManager.reset(); }
/*     */     
/* 243 */     String msg = "<html>This example is based on the parser described around page-240 <br/>of the book <i>Programming Scala</i> (http://programmingscala.com/)<br/><br/>An online version of the parser code can be found here: <br/> http://ofps.oreilly.com/titles/9780596155957/DomainSpecificLanguages.html<br/>&nbsp;&nbsp;&nbsp;&nbsp;#_generating_paychecks_with_the_external_dsl<br/><br/>This parser also includes a special rule that serves as a <i>test-harness</i> <br/>by using an action-function to wrap the main rule in a <br/>test-data setup part and a output-checker part. <br/>The rule called <i>PaycheckTester</i> is the test-harness. <br/><br/>Read more about this parser at the following url:<br/>&nbsp;&nbsp;&nbsp;&nbsp;http://vll.java.net/RapidPrototypingForScala.html";
/*     */   };
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
/* 257 */   Action displayHelpMain = new AbstractAction("Web-Site", Resources.icon)
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/*     */       try {
/* 261 */         URI webLink = new URL("http://vll.java.net/").toURI();
/* 262 */         ManagerHelp.this.desktop.browse(webLink);
/*     */       } catch (Exception ex) {
/* 264 */         if (ManagerHelp.this.desktop == null) {
/* 265 */           JOptionPane.showMessageDialog(ManagerHelp.this.gui, "Please visit http://vll.java.net/", "Unsupported: java.awt.Desktop", -1);
/*     */         }
/*     */         else {
/* 268 */           ex.printStackTrace();
/*     */         }
/*     */       }
/*     */     }
/*     */   };
/* 273 */   Action displayHelpRuleTree = new AbstractAction("", Resources.help16)
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 276 */       JLabel hlpLabel = new JLabel(ManagerHelp.this.getHtml("PanelRuleTree.html"));
/* 277 */       if (0 == JOptionPane.showConfirmDialog(ManagerHelp.this.gui, hlpLabel, "Rule Tree - " + ManagerHelp.this.title, 0, -1)) {
/*     */         try
/*     */         {
/* 280 */           URI webLink = new URL("http://vll.java.net/EditingTheGrammarTree.html").toURI();
/* 281 */           ManagerHelp.this.desktop.browse(webLink);
/*     */         } catch (Exception ex) {
/* 283 */           if (ManagerHelp.this.desktop == null) {
/* 284 */             JOptionPane.showMessageDialog(ManagerHelp.this.gui, "Please visit http://vll.java.net/EditingTheGrammarTree.html", "Unsupported: java.awt.Desktop", -1);
/*     */           }
/*     */           else {
/* 287 */             ex.printStackTrace();
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   };
/* 293 */   Action displayHelpAST = new AbstractAction("", Resources.help16)
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 296 */       JLabel hlpLabel = new JLabel(ManagerHelp.this.getHtml("PanelAST.html"));
/* 297 */       if (0 == JOptionPane.showConfirmDialog(ManagerHelp.this.gui, hlpLabel, "Parse Tree (AST) Structure - " + ManagerHelp.this.title, 0, -1)) {
/*     */         try
/*     */         {
/* 300 */           URI webLink = new URL("http://vll.java.net/ASTAndActionCode.html").toURI();
/* 301 */           ManagerHelp.this.desktop.browse(webLink);
/*     */         } catch (Exception ex) {
/* 303 */           if (ManagerHelp.this.desktop == null) {
/* 304 */             JOptionPane.showMessageDialog(ManagerHelp.this.gui, "Please visit http://vll.java.net/ASTAndActionCode.html", "Unsupported: java.awt.Desktop", -1);
/*     */           }
/*     */           else {
/* 307 */             ex.printStackTrace();
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   };
/* 313 */   Action displayHelpActionCode = new AbstractAction("", Resources.help16)
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 316 */       JLabel hlpLabel = new JLabel(ManagerHelp.this.getHtml("PanelActionCode.html"));
/* 317 */       if (0 == JOptionPane.showConfirmDialog(ManagerHelp.this.gui, hlpLabel, "Action Code - " + ManagerHelp.this.title, 0, -1)) {
/*     */         try
/*     */         {
/* 320 */           URI webLink = new URL("http://vll.java.net/ASTAndActionCode.html#ActionCodeDesign").toURI();
/* 321 */           ManagerHelp.this.desktop.browse(webLink);
/*     */         } catch (Exception ex) {
/* 323 */           if (ManagerHelp.this.desktop == null) {
/* 324 */             JOptionPane.showMessageDialog(ManagerHelp.this.gui, "Please visit http://vll.java.net/ASTAndActionCode.html#ActionCodeDesign", "Unsupported: java.awt.Desktop", -1);
/*     */           }
/*     */           else {
/* 327 */             ex.printStackTrace();
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   };
/* 333 */   Action displayHelpTestInput = new AbstractAction("", Resources.help16)
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 336 */       JLabel hlpLabel = new JLabel(ManagerHelp.this.getHtml("PanelTestInput.html"));
/* 337 */       if (0 == JOptionPane.showConfirmDialog(ManagerHelp.this.gui, hlpLabel, "Parser Test Input - " + ManagerHelp.this.title, 0, -1)) {
/*     */         try
/*     */         {
/* 340 */           URI webLink = new URL("http://vll.java.net/TestingParsers.html").toURI();
/* 341 */           ManagerHelp.this.desktop.browse(webLink);
/*     */         } catch (Exception ex) {
/* 343 */           if (ManagerHelp.this.desktop == null) {
/* 344 */             JOptionPane.showMessageDialog(ManagerHelp.this.gui, "Please visit http://vll.java.net/TestingParsers.html", "Unsupported: java.awt.Desktop", -1);
/*     */           }
/*     */           else {
/* 347 */             ex.printStackTrace();
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   };
/* 353 */   Action displayHelpTestLog = new AbstractAction("", Resources.help16)
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 356 */       JLabel hlpLabel = new JLabel(ManagerHelp.this.getHtml("PanelTestLog.html"));
/* 357 */       if (0 == JOptionPane.showConfirmDialog(ManagerHelp.this.gui, hlpLabel, "Parser Log - " + ManagerHelp.this.title, 0, -1))
/*     */         try
/*     */         {
/* 360 */           URI webLink = new URL("http://vll.java.net/TestingParsers.html").toURI();
/* 361 */           ManagerHelp.this.desktop.browse(webLink);
/*     */         } catch (Exception ex) {
/* 363 */           if (ManagerHelp.this.desktop == null) {
/* 364 */             JOptionPane.showMessageDialog(ManagerHelp.this.gui, "Please visit http://vll.java.net/TestingParsers.html", "Unsupported: java.awt.Desktop", -1);
/*     */           }
/*     */           else
/* 367 */             ex.printStackTrace();
/*     */         }
/*     */     }
/*     */   };
/*     */   private Vll4jGui gui;
/*     */   String title;
/*     */   
/* 374 */   private String getHtml(String name) { ClassLoader cl = ClassLoader.getSystemClassLoader();
/* 375 */     URL res = cl.getResource("net/java/vll/vll4j/gui/resources/" + name);
/*     */     try {
/* 377 */       InputStream hs = res.openStream();
/* 378 */       byte[] buf = new byte[hs.available()];
/* 379 */       hs.read(buf);
/* 380 */       return new String(buf);
/*     */     } catch (Exception e) {}
/* 382 */     return "";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 388 */   private Desktop desktop = null;
/*     */ }


/* Location:              /home/yogesh/Downloads/VLL4J.jar!/net/java/vll/vll4j/gui/ManagerHelp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */