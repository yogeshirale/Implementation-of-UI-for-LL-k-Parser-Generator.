/*     */ package net.java.vll.vll4j.gui;
/*     */ 
/*     */ import java.io.PrintWriter;
/*     */ import net.java.vll.vll4j.api.Multiplicity;
/*     */ import net.java.vll.vll4j.api.NodeBase;
/*     */ import net.java.vll.vll4j.api.NodeChoice;
/*     */ import net.java.vll.vll4j.api.NodeLiteral;
/*     */ import net.java.vll.vll4j.api.NodeReference;
/*     */ import net.java.vll.vll4j.api.NodeRegex;
/*     */ import net.java.vll.vll4j.api.NodeRepSep;
/*     */ import net.java.vll.vll4j.api.NodeRoot;
/*     */ import net.java.vll.vll4j.api.NodeSemPred;
/*     */ import net.java.vll.vll4j.api.NodeSequence;
/*     */ import net.java.vll.vll4j.api.NodeWildCard;
/*     */ import net.java.vll.vll4j.api.VisitorBase;
/*     */ import net.java.vll.vll4j.combinator.Utils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VisitorXmlGeneration
/*     */   extends VisitorBase
/*     */ {
/*     */   private PrintWriter printWriter;
/*     */   
/*     */   VisitorXmlGeneration(PrintWriter printWriter)
/*     */   {
/*  30 */     this.printWriter = printWriter;
/*     */   }
/*     */   
/*     */   private void commonAttributes(NodeBase n, boolean solo) {
/*  34 */     if (n.multiplicity != Multiplicity.One)
/*  35 */       this.printWriter.printf("Mult=\"%s\" ", new Object[] { n.multiplicity });
/*  36 */     if (!n.errorMessage.isEmpty())
/*  37 */       this.printWriter.printf("ErrMsg=\"%s\" ", new Object[] { Utils.encode4xml(n.errorMessage) });
/*  38 */     if (!n.description.isEmpty())
/*  39 */       this.printWriter.printf("Description=\"%s\" ", new Object[] { Utils.encode4xml(n.description) });
/*  40 */     if (!n.actionText.isEmpty())
/*  41 */       this.printWriter.printf("ActionText=\"%s\" ", new Object[] { Utils.encode4xml(n.actionText) });
/*  42 */     if (n.isDropped)
/*  43 */       this.printWriter.printf("Drop=\"true\" ", new Object[0]);
/*  44 */     this.printWriter.println(solo ? "/>" : ">");
/*     */   }
/*     */   
/*     */   private void space() {
/*  48 */     for (int i = 0; i < this.depth; i++) {
/*  49 */       this.printWriter.print("  ");
/*     */     }
/*     */   }
/*     */   
/*     */   public Object visitChoice(NodeChoice n) {
/*  54 */     space();
/*  55 */     this.printWriter.printf("<Choice ", new Object[0]);
/*  56 */     commonAttributes(n, false);
/*  57 */     visitAllChildNodes(n);
/*  58 */     space();
/*  59 */     this.printWriter.println("</Choice>");
/*  60 */     return null;
/*     */   }
/*     */   
/*     */   public Object visitLiteral(NodeLiteral n)
/*     */   {
/*  65 */     space();
/*  66 */     this.printWriter.printf("<Token Ref=\"%s\" ", new Object[] { Utils.encode4xml(n.literalName) });
/*  67 */     commonAttributes(n, true);
/*  68 */     return null;
/*     */   }
/*     */   
/*     */   public Object visitReference(NodeReference n)
/*     */   {
/*  73 */     space();
/*  74 */     this.printWriter.printf("<Reference Ref=\"%s\" ", new Object[] { n.refRuleName });
/*  75 */     commonAttributes(n, true);
/*  76 */     return null;
/*     */   }
/*     */   
/*     */   public Object visitRegex(NodeRegex n)
/*     */   {
/*  81 */     space();
/*  82 */     this.printWriter.printf("<Token Ref=\"%s\" ", new Object[] { Utils.encode4xml(n.regexName) });
/*  83 */     commonAttributes(n, true);
/*  84 */     return null;
/*     */   }
/*     */   
/*     */   public Object visitRepSep(NodeRepSep n)
/*     */   {
/*  89 */     space();
/*  90 */     this.printWriter.printf("<RepSep ", new Object[0]);
/*  91 */     commonAttributes(n, false);
/*  92 */     visitAllChildNodes(n);
/*  93 */     space();
/*  94 */     this.printWriter.println("</RepSep>");
/*  95 */     return null;
/*     */   }
/*     */   
/*     */   public Object visitRoot(NodeRoot n)
/*     */   {
/* 100 */     this.depth = 2;
/* 101 */     space();
/* 102 */     this.printWriter.printf("<Parser Name=\"%s\" ", new Object[] { n.ruleName });
/* 103 */     if (n.isPackrat)
/* 104 */       this.printWriter.print("Packrat=\"true\" ");
/* 105 */     commonAttributes(n, false);
/* 106 */     visitAllChildNodes(n);
/* 107 */     space();
/* 108 */     this.printWriter.println("</Parser>");
/* 109 */     return null;
/*     */   }
/*     */   
/*     */   public Object visitSemPred(NodeSemPred n)
/*     */   {
/* 114 */     space();
/* 115 */     this.printWriter.printf("<SemPred ", new Object[0]);
/* 116 */     commonAttributes(n, true);
/* 117 */     return null;
/*     */   }
/*     */   
/*     */   public Object visitSequence(NodeSequence n)
/*     */   {
/* 122 */     space();
/* 123 */     if (n.commitIndex != Integer.MAX_VALUE) {
/* 124 */       this.printWriter.printf("<Sequence Commit=\"%s\" ", new Object[] { Integer.valueOf(n.commitIndex) });
/*     */     } else
/* 126 */       this.printWriter.printf("<Sequence ", new Object[0]);
/* 127 */     commonAttributes(n, false);
/* 128 */     visitAllChildNodes(n);
/* 129 */     space();
/* 130 */     this.printWriter.println("</Sequence>");
/* 131 */     return null;
/*     */   }
/*     */   
/*     */   public Object visitWildCard(NodeWildCard n)
/*     */   {
/* 136 */     space();
/* 137 */     this.printWriter.printf("<Token ", new Object[0]);
/* 138 */     commonAttributes(n, true);
/* 139 */     return null;
/*     */   }
/*     */ }


/* Location:              /home/yogesh/Downloads/VLL4J.jar!/net/java/vll/vll4j/gui/VisitorXmlGeneration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */