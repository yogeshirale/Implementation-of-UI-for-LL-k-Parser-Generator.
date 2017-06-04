/*     */ package net.java.vll.vll4j.gui;
/*     */ 
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
/*     */ import net.java.vll.vll4j.api.VisitorValidation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VisitorRuleInfo
/*     */   extends VisitorBase
/*     */ {
/*     */   public Object visitChoice(NodeChoice n)
/*     */   {
/*  29 */     if (!n.actionText.trim().isEmpty())
/*  30 */       this.hasActions = true;
/*  31 */     if (this.visitorValidation.visitChoice(n) != null)
/*  32 */       this.hasErrors = true;
/*  33 */     visitAllChildNodes(n);
/*  34 */     return null;
/*     */   }
/*     */   
/*     */   public Object visitLiteral(NodeLiteral n)
/*     */   {
/*  39 */     if (!n.actionText.trim().isEmpty())
/*  40 */       this.hasActions = true;
/*  41 */     if (this.visitorValidation.visitLiteral(n) != null)
/*  42 */       this.hasErrors = true;
/*  43 */     return null;
/*     */   }
/*     */   
/*     */   public Object visitReference(NodeReference n)
/*     */   {
/*  48 */     if (!n.actionText.trim().isEmpty())
/*  49 */       this.hasActions = true;
/*  50 */     if (this.visitorValidation.visitReference(n) != null)
/*  51 */       this.hasErrors = true;
/*  52 */     return null;
/*     */   }
/*     */   
/*     */   public Object visitRegex(NodeRegex n)
/*     */   {
/*  57 */     if (!n.actionText.trim().isEmpty())
/*  58 */       this.hasActions = true;
/*  59 */     if (this.visitorValidation.visitRegex(n) != null)
/*  60 */       this.hasErrors = true;
/*  61 */     return null;
/*     */   }
/*     */   
/*     */   public Object visitRepSep(NodeRepSep n)
/*     */   {
/*  66 */     if (!n.actionText.trim().isEmpty())
/*  67 */       this.hasActions = true;
/*  68 */     if (this.visitorValidation.visitRepSep(n) != null)
/*  69 */       this.hasErrors = true;
/*  70 */     visitAllChildNodes(n);
/*  71 */     return null;
/*     */   }
/*     */   
/*     */   public Object visitRoot(NodeRoot n)
/*     */   {
/*  76 */     this.hasErrors = (this.hasActions = this.isTester = false);
/*  77 */     if (n != null) {
/*  78 */       if (!n.actionText.trim().isEmpty())
/*  79 */         this.hasActions = true;
/*  80 */       if (this.visitorValidation.visitRoot(n) != null)
/*  81 */         this.hasErrors = true;
/*  82 */       if (n.getChildCount() != 0) {
/*  83 */         NodeBase c = (NodeBase)n.getChildAt(0);
/*  84 */         String action = c.actionText.trim();
/*  85 */         if ((!action.isEmpty()) && (
/*  86 */           (action.contains("vllParserTestInput")) || (action.contains("vllParserLog")))) {
/*  87 */           this.isTester = true;
/*     */         }
/*     */       }
/*  90 */       visitAllChildNodes(n);
/*     */     }
/*  92 */     return null;
/*     */   }
/*     */   
/*     */   public Object visitSemPred(NodeSemPred n)
/*     */   {
/*  97 */     if (this.visitorValidation.visitSemPred(n) != null)
/*  98 */       this.hasErrors = true;
/*  99 */     return null;
/*     */   }
/*     */   
/*     */   public Object visitSequence(NodeSequence n)
/*     */   {
/* 104 */     if (!n.actionText.trim().isEmpty())
/* 105 */       this.hasActions = true;
/* 106 */     if (this.visitorValidation.visitSequence(n) != null)
/* 107 */       this.hasErrors = true;
/* 108 */     visitAllChildNodes(n);
/* 109 */     return null;
/*     */   }
/*     */   
/*     */   public Object visitWildCard(NodeWildCard n)
/*     */   {
/* 114 */     if (!n.actionText.trim().isEmpty())
/* 115 */       this.hasActions = true;
/* 116 */     if (this.visitorValidation.visitWildCard(n) != null)
/* 117 */       this.hasErrors = true;
/* 118 */     return null;
/*     */   }
/*     */   
/* 121 */   public boolean hasErrors = false; public boolean hasActions = false;
/* 122 */   private VisitorValidation visitorValidation = new VisitorValidation();
/*     */   public boolean isTester;
/*     */ }


/* Location:              /home/yogesh/Downloads/VLL4J.jar!/net/java/vll/vll4j/gui/VisitorRuleInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */