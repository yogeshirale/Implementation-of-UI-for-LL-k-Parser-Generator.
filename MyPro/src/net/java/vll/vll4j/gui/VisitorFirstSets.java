/*     */ package net.java.vll.vll4j.gui;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.java.vll.vll4j.api.Forest;
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
/*     */ public class VisitorFirstSets
/*     */   extends VisitorBase
/*     */ {
/*     */   private Forest theForest;
/*     */   
/*     */   public VisitorFirstSets(Forest theForest)
/*     */   {
/*  30 */     this.theForest = theForest;
/*     */   }
/*     */   
/*     */   public Set<String>[] visitChoice(NodeChoice n)
/*     */   {
/*  35 */     int cc = n.getChildCount();
/*  36 */     NodeBase[] cn = new NodeBase[cc];
/*  37 */     ArrayList<Set<String>> firsts = new ArrayList();
/*  38 */     Set<String>[][] cFirsts = new Set[cc][];
/*  39 */     for (int i = 0; i < cc; i++) {
/*  40 */       cn[i] = ((NodeBase)n.getChildAt(i));
/*  41 */       cFirsts[i] = ((Set[])(Set[])cn[i].accept(this));
/*  42 */       while (firsts.size() < cFirsts[i].length)
/*  43 */         firsts.add(new HashSet());
/*     */     }
/*  45 */     for (int i = 0; i < cc; i++) {
/*  46 */       int cfLen = cFirsts[i].length;
/*  47 */       for (int j = 0; j < cfLen; j++) {
/*  48 */         ((Set)firsts.get(j)).addAll(cFirsts[i][j]);
/*  49 */         if ((cn[i].multiplicity == Multiplicity.ZeroOrMore) || (cn[i].multiplicity == Multiplicity.OneOrMore)) {
/*  50 */           while (firsts.size() < 5)
/*  51 */             firsts.add(new HashSet());
/*  52 */           for (int k = 1; j + cfLen * k < firsts.size(); k++) {
/*  53 */             ((Set)firsts.get(j + cfLen * k)).addAll(cFirsts[i][j]);
/*  54 */             if (j == 0)
/*  55 */               ((Set)firsts.get(cfLen * k)).add("");
/*     */           }
/*     */         }
/*     */       }
/*  59 */       if (cfLen < firsts.size())
/*  60 */         ((Set)firsts.get(cfLen)).add("");
/*     */     }
/*  62 */     return (Set[])firsts.toArray(new Set[firsts.size()]);
/*     */   }
/*     */   
/*     */   public Set<String>[] visitLiteral(NodeLiteral n)
/*     */   {
/*  67 */     Set<String> ss = new HashSet();
/*  68 */     ss.add(n.literalName);
/*  69 */     return new Set[] { ss };
/*     */   }
/*     */   
/*     */   public Set<String>[] visitReference(NodeReference n)
/*     */   {
/*  74 */     String referredRuleName = n.refRuleName;
/*  75 */     NodeBase referredRule = (NodeBase)this.theForest.ruleBank.get(referredRuleName);
/*  76 */     return (Set[])referredRule.accept(this);
/*     */   }
/*     */   
/*     */   public Set<String>[] visitRegex(NodeRegex n)
/*     */   {
/*  81 */     Set<String> ss = new HashSet();
/*  82 */     ss.add(n.regexName);
/*  83 */     return new Set[] { ss };
/*     */   }
/*     */   
/*     */   public Set<String>[] visitRepSep(NodeRepSep n)
/*     */   {
/*  88 */     if (n.getChildCount() == 2) {
/*  89 */       Set<String>[] rep = (Set[])((NodeBase)n.getChildAt(0)).accept(this);
/*  90 */       Set<String>[] sep = (Set[])((NodeBase)n.getChildAt(1)).accept(this);
/*  91 */       Set<String>[] retVal = (Set[])Arrays.copyOf(rep, 5);
/*  92 */       for (int i = 0; i < 5; i++) {
/*  93 */         if (retVal[i] == null)
/*  94 */           retVal[i] = new HashSet();
/*     */       }
/*  96 */       for (int k = 0; rep.length + k * (rep.length + sep.length) < 5; k++) {
/*  97 */         int off = rep.length + k * (rep.length + sep.length);
/*  98 */         retVal[off].add("");
/*  99 */         for (int i = 0; (i < sep.length) && (off + i < 5); i++)
/* 100 */           retVal[(off + i)].addAll(sep[i]);
/* 101 */         off += sep.length;
/* 102 */         for (int i = 0; (i < rep.length) && (off + i < 5); i++)
/* 103 */           retVal[(off + i)].addAll(rep[i]);
/*     */       }
/* 105 */       if (n.multiplicity == Multiplicity.ZeroOrMore)
/* 106 */         retVal[0].add("");
/* 107 */       return retVal;
/*     */     }
/* 109 */     return new Set[0];
/*     */   }
/*     */   
/*     */ 
/*     */   public Set<String>[] visitRoot(NodeRoot n)
/*     */   {
/* 115 */     if (this.activeRules.contains(n.getName())) {
/* 116 */       return new Set[0];
/*     */     }
/* 118 */     this.activeRules.add(n.getName());
/* 119 */     NodeBase child = (NodeBase)n.getFirstChild();
/* 120 */     Set<String>[] retVal = (Set[])child.accept(this);
/* 121 */     this.activeRules.remove(n.getName());
/* 122 */     return retVal;
/*     */   }
/*     */   
/*     */ 
/*     */   public Set<String>[] visitSemPred(NodeSemPred n)
/*     */   {
/* 128 */     return new Set[0];
/*     */   }
/*     */   
/*     */   private boolean mergeSequence(Set<String>[] from, List<Set<String>> to, NodeBase n) {
/* 132 */     int oldLength = to.size();
/* 133 */     int newLength = (n.multiplicity == Multiplicity.ZeroOrMore) || (n.multiplicity == Multiplicity.OneOrMore) ? 5 : Math.min(5, oldLength + from.length);
/*     */     
/* 135 */     for (int i = oldLength; i < newLength; i++) {
/* 136 */       HashSet<String> hs = new HashSet();
/* 137 */       if (i == oldLength)
/* 138 */         hs.add("");
/* 139 */       to.add(hs);
/*     */     }
/* 141 */     for (int i = newLength - 1; i >= 0; i--) {
/* 142 */       if (((Set)to.get(i)).contains(""))
/*     */       {
/* 144 */         if ((n.multiplicity != Multiplicity.ZeroOrOne) && (n.multiplicity != Multiplicity.ZeroOrMore)) {
/* 145 */           ((Set)to.get(i)).remove("");
/*     */         }
/* 147 */         if (i + from.length < newLength) {
/* 148 */           ((Set)to.get(i + from.length)).add("");
/*     */         }
/* 150 */         for (int j = 0; (j < from.length) && (i + j < newLength); j++) {
/* 151 */           int offs = i + j;
/* 152 */           ((Set)to.get(offs)).addAll(from[j]);
/* 153 */           if ((n.multiplicity == Multiplicity.OneOrMore) || (n.multiplicity == Multiplicity.ZeroOrMore))
/* 154 */             for (int offs2 = offs + from.length; offs2 < newLength; offs2 += from.length) {
/* 155 */               ((Set)to.get(offs2)).addAll(from[j]);
/* 156 */               if (j == 0)
/* 157 */                 ((Set)to.get(offs2)).add("");
/*     */             }
/*     */         }
/*     */       }
/*     */     }
/* 162 */     boolean more = false;
/* 163 */     for (int i = 0; (!more) && (i < newLength); i++)
/* 164 */       more = ((Set)to.get(i)).contains("");
/* 165 */     return (more) || (newLength < 5);
/*     */   }
/*     */   
/*     */   public Set<String>[] visitSequence(NodeSequence n)
/*     */   {
/* 170 */     List<Set<String>> firsts = new ArrayList();
/* 171 */     for (int i = 0; i < n.getChildCount(); i++) {
/* 172 */       NodeBase c = (NodeBase)n.getChildAt(i);
/* 173 */       Set<String>[] f = (Set[])c.accept(this);
/* 174 */       if (!mergeSequence(f, firsts, c))
/*     */         break;
/*     */     }
/* 177 */     return (Set[])firsts.toArray(new Set[firsts.size()]);
/*     */   }
/*     */   
/*     */   public Set<String>[] visitWildCard(NodeWildCard n)
/*     */   {
/* 182 */     Set<String> ss = new HashSet();
/* 183 */     ss.add("WildCard");
/* 184 */     return new Set[] { ss };
/*     */   }
/*     */   
/*     */ 
/* 188 */   private final int LA = 5;
/* 189 */   private Set<String> activeRules = new HashSet();
/*     */ }


/* Location:              /home/yogesh/Downloads/VLL4J.jar!/net/java/vll/vll4j/gui/VisitorFirstSets.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */