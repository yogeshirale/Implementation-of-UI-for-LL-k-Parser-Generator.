/*     */ package net.java.vll.vll4j.gui;
/*     */ 
/*     */ import javax.swing.Action;
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.JRadioButtonMenuItem;
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
/*     */ 
/*     */ 
/*     */ public class VisitorMenuAdjustment
/*     */   extends VisitorBase
/*     */ {
/*     */   PopupMenuTree theMenu;
/*     */   
/*     */   public VisitorMenuAdjustment(PopupMenuTree theMenu)
/*     */   {
/*  28 */     this.theMenu = theMenu;
/*     */   }
/*     */   
/*     */   void commonSetting(NodeBase n) {
/*  32 */     NodeBase parent = (NodeBase)n.getParent();
/*  33 */     boolean isInSequene = (parent != null) && ((parent instanceof NodeSequence));
/*  34 */     this.theMenu.dropMenuItem.setEnabled(isInSequene);
/*  35 */     this.theMenu.commitMenuItem.setEnabled(isInSequene);
/*  36 */     this.theMenu.commitMenuItem.setSelected((isInSequene) && (((NodeSequence)parent).commitIndex == parent.getIndex(n)));
/*     */     
/*  38 */     this.theMenu.packratMenuItem.setEnabled(false);
/*  39 */     this.theMenu.multiplicityOneItem.setEnabled(true);
/*  40 */     this.theMenu.multiplicityZeroOneItem.setEnabled((parent == null) || (!(parent instanceof NodeChoice)));
/*  41 */     this.theMenu.multiplicityZeroMoreItem.setEnabled((parent == null) || (!(parent instanceof NodeChoice)));
/*  42 */     this.theMenu.multiplicityOneMoreItem.setEnabled(true);
/*  43 */     this.theMenu.multiplicityNotItem.setEnabled((parent == null) || (!(parent instanceof NodeChoice)));
/*  44 */     this.theMenu.multiplicityGuardItem.setEnabled((parent == null) || (!(parent instanceof NodeChoice)));
/*  45 */     this.theMenu.multiplicityMenu.setEnabled(true);
/*  46 */     this.theMenu.traceMenuItem.setSelected(n.isTraced);
/*  47 */     this.theMenu.dropMenuItem.setSelected(n.isDropped);
/*  48 */     this.theMenu.packratMenuItem.setSelected(false);
/*  49 */     if (n.multiplicity == Multiplicity.One) {
/*  50 */       this.theMenu.multiplicityOneItem.setSelected(true);
/*  51 */     } else if (n.multiplicity == Multiplicity.ZeroOrOne) {
/*  52 */       this.theMenu.multiplicityZeroOneItem.setSelected(true);
/*  53 */     } else if (n.multiplicity == Multiplicity.ZeroOrMore) {
/*  54 */       this.theMenu.multiplicityZeroMoreItem.setSelected(true);
/*  55 */     } else if (n.multiplicity == Multiplicity.OneOrMore) {
/*  56 */       this.theMenu.multiplicityOneMoreItem.setSelected(true);
/*  57 */     } else if (n.multiplicity == Multiplicity.Not) {
/*  58 */       this.theMenu.multiplicityNotItem.setSelected(true);
/*  59 */     } else if (n.multiplicity == Multiplicity.Guard) {
/*  60 */       this.theMenu.multiplicityGuardItem.setSelected(true);
/*     */     }
/*  62 */     this.theMenu.cutMenuItem.setEnabled(true);
/*  63 */     this.theMenu.copyMenuItem.setEnabled(true);
/*  64 */     this.theMenu.deleteMenuItem.setEnabled(true);
/*  65 */     this.theMenu.treePanel.addSemPredAction.setEnabled(false);
/*     */   }
/*     */   
/*     */   public Object visitChoice(NodeChoice n)
/*     */   {
/*  70 */     commonSetting(n);
/*  71 */     this.theMenu.addMenu.setEnabled(true);
/*  72 */     this.theMenu.pasteMenuItem.setEnabled(this.theMenu.treePanel.theClipBoard != null);
/*  73 */     this.theMenu.goToItem.setEnabled(false);
/*  74 */     return null;
/*     */   }
/*     */   
/*     */   public Object visitLiteral(NodeLiteral n)
/*     */   {
/*  79 */     commonSetting(n);
/*  80 */     this.theMenu.addMenu.setEnabled(false);
/*  81 */     this.theMenu.pasteMenuItem.setEnabled(false);
/*  82 */     this.theMenu.goToItem.setEnabled(false);
/*  83 */     return null;
/*     */   }
/*     */   
/*     */   public Object visitReference(NodeReference n)
/*     */   {
/*  88 */     commonSetting(n);
/*  89 */     this.theMenu.addMenu.setEnabled(false);
/*  90 */     this.theMenu.pasteMenuItem.setEnabled(false);
/*  91 */     this.theMenu.goToItem.setEnabled(true);
/*  92 */     return null;
/*     */   }
/*     */   
/*     */   public Object visitRegex(NodeRegex n)
/*     */   {
/*  97 */     commonSetting(n);
/*  98 */     this.theMenu.addMenu.setEnabled(false);
/*  99 */     this.theMenu.pasteMenuItem.setEnabled(false);
/* 100 */     this.theMenu.goToItem.setEnabled(true);
/* 101 */     return null;
/*     */   }
/*     */   
/*     */   public Object visitRepSep(NodeRepSep n)
/*     */   {
/* 106 */     commonSetting(n);
/* 107 */     this.theMenu.addMenu.setEnabled(n.getChildCount() < 2);
/* 108 */     this.theMenu.pasteMenuItem.setEnabled((n.getChildCount() < 2) && (this.theMenu.treePanel.theClipBoard != null));
/* 109 */     this.theMenu.multiplicityOneItem.setEnabled(false);
/* 110 */     this.theMenu.multiplicityZeroOneItem.setEnabled(false);
/* 111 */     this.theMenu.multiplicityNotItem.setEnabled(false);
/* 112 */     this.theMenu.multiplicityGuardItem.setEnabled(false);
/* 113 */     this.theMenu.goToItem.setEnabled(false);
/* 114 */     return null;
/*     */   }
/*     */   
/*     */   public Object visitRoot(NodeRoot n)
/*     */   {
/* 119 */     commonSetting(n);
/* 120 */     this.theMenu.packratMenuItem.setEnabled(true);
/* 121 */     this.theMenu.packratMenuItem.setSelected(n.isPackrat);
/* 122 */     this.theMenu.addMenu.setEnabled(n.getChildCount() == 0);
/* 123 */     this.theMenu.multiplicityMenu.setEnabled(false);
/* 124 */     this.theMenu.goToItem.setEnabled(false);
/* 125 */     this.theMenu.pasteMenuItem.setEnabled((n.getChildCount() == 0) && (this.theMenu.treePanel.theClipBoard != null));
/* 126 */     this.theMenu.cutMenuItem.setEnabled(false);
/* 127 */     this.theMenu.copyMenuItem.setEnabled(false);
/* 128 */     this.theMenu.deleteMenuItem.setEnabled(false);
/* 129 */     return null;
/*     */   }
/*     */   
/*     */   public Object visitSemPred(NodeSemPred n)
/*     */   {
/* 134 */     commonSetting(n);
/* 135 */     this.theMenu.addMenu.setEnabled(false);
/* 136 */     this.theMenu.pasteMenuItem.setEnabled(false);
/* 137 */     this.theMenu.goToItem.setEnabled(true);
/* 138 */     this.theMenu.multiplicityMenu.setEnabled(false);
/* 139 */     return null;
/*     */   }
/*     */   
/*     */   public Object visitSequence(NodeSequence n)
/*     */   {
/* 144 */     commonSetting(n);
/* 145 */     this.theMenu.addMenu.setEnabled(true);
/* 146 */     this.theMenu.goToItem.setEnabled(false);
/* 147 */     this.theMenu.pasteMenuItem.setEnabled(this.theMenu.treePanel.theClipBoard != null);
/* 148 */     this.theMenu.treePanel.addSemPredAction.setEnabled(true);
/* 149 */     return null;
/*     */   }
/*     */   
/*     */   public Object visitWildCard(NodeWildCard n)
/*     */   {
/* 154 */     commonSetting(n);
/* 155 */     this.theMenu.addMenu.setEnabled(false);
/* 156 */     this.theMenu.goToItem.setEnabled(false);
/* 157 */     this.theMenu.pasteMenuItem.setEnabled(false);
/* 158 */     this.theMenu.treePanel.addSemPredAction.setEnabled(false);
/* 159 */     return null;
/*     */   }
/*     */ }


/* Location:              /home/yogesh/Downloads/VLL4J.jar!/net/java/vll/vll4j/gui/VisitorMenuAdjustment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */