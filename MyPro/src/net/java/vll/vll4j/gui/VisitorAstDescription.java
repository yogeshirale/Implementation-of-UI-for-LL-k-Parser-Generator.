/*     */ package net.java.vll.vll4j.gui;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
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
/*     */ public class VisitorAstDescription
/*     */   extends VisitorBase
/*     */ {
/*     */   private Vll4jGui gui;
/*     */   
/*     */   public VisitorAstDescription(Vll4jGui gui)
/*     */   {
/*  29 */     this.gui = gui;
/*     */   }
/*     */   
/*     */   public Object visitChoice(NodeChoice n)
/*     */   {
/*  34 */     if (this.level > this.maxDepth)
/*  35 */       return getSpaces(n) + "_";
/*  36 */     if ((n != this.gui.theTreePanel.selectedNode) && (!n.actionText.trim().isEmpty()))
/*  37 */       return String.format("%saction@%s", new Object[] { getSpaces(n), n.nodeName() });
/*  38 */     if (n.getChildCount() == 0)
/*  39 */       return getSpaces(n) + "?";
/*  40 */     StringBuilder sb = new StringBuilder();
/*  41 */     sb.append(getSpaces(n)).append("Choice(\n");
/*  42 */     this.level += 1;
/*  43 */     int cc = n.getChildCount();
/*  44 */     for (int i = 0; i < cc; i++) {
/*  45 */       sb.append(getSpaces(n)).append("Array(").append(i);
/*  46 */       this.level += 1;
/*  47 */       String childAst = (String)((NodeBase)n.getChildAt(i)).accept(this);
/*  48 */       if (childAst.contains("\n")) {
/*  49 */         sb.append(",\n").append(childAst).append("\n");
/*  50 */         this.level -= 1;
/*  51 */         sb.append(getSpaces(n)).append(")");
/*     */       } else {
/*  53 */         sb.append(", ").append(stripSpaces(childAst)).append(")");
/*  54 */         this.level -= 1;
/*     */       }
/*  56 */       if (i == cc - 1) {
/*  57 */         sb.append("\n");
/*     */       } else
/*  59 */         sb.append(",\n");
/*     */     }
/*  61 */     this.level -= 1;
/*  62 */     sb.append(getSpaces(n)).append(")");
/*  63 */     return withMultiplicity(sb.toString(), n);
/*     */   }
/*     */   
/*     */   public Object visitLiteral(NodeLiteral n)
/*     */   {
/*  68 */     if (this.level > this.maxDepth)
/*  69 */       return getSpaces(n) + "_";
/*  70 */     if ((n != this.gui.theTreePanel.selectedNode) && (!n.actionText.trim().isEmpty()))
/*  71 */       return String.format("%saction@%s", new Object[] { getSpaces(n), n.nodeName() });
/*  72 */     StringBuilder sb = new StringBuilder();
/*  73 */     sb.append(getSpaces(n)).append("\"").append(((String)this.gui.theForest.tokenBank.get(n.literalName)).substring(1)).append("\"");
/*     */     
/*  75 */     return withMultiplicity(sb.toString(), n);
/*     */   }
/*     */   
/*     */   public Object visitReference(NodeReference n)
/*     */   {
/*  80 */     if (this.level > this.maxDepth)
/*  81 */       return getSpaces(n) + "_";
/*  82 */     if ((n != this.gui.theTreePanel.selectedNode) && (!n.actionText.trim().isEmpty()))
/*  83 */       return String.format("%saction@%s", new Object[] { getSpaces(n), n.nodeName() });
/*  84 */     if (this.maxDepth == Integer.MAX_VALUE)
/*     */     {
/*  86 */       String ast = withMultiplicity((String)((NodeBase)this.gui.theForest.ruleBank.get(n.refRuleName)).accept(this), n);
/*     */       
/*  88 */       return ast;
/*     */     }
/*  90 */     StringBuilder sb = new StringBuilder();
/*  91 */     sb.append(getSpaces(n)).append("@").append(n.refRuleName);
/*  92 */     return withMultiplicity(sb.toString(), n);
/*     */   }
/*     */   
/*     */ 
/*     */   public Object visitRegex(NodeRegex n)
/*     */   {
/*  98 */     if (this.level > this.maxDepth)
/*  99 */       return getSpaces(n) + "_";
/* 100 */     if ((n != this.gui.theTreePanel.selectedNode) && (!n.actionText.trim().isEmpty()))
/* 101 */       return String.format("%saction@%s", new Object[] { getSpaces(n), n.nodeName() });
/* 102 */     StringBuilder sb = new StringBuilder();
/* 103 */     sb.append(getSpaces(n)).append("[").append(n.regexName).append("]");
/* 104 */     return withMultiplicity(sb.toString(), n);
/*     */   }
/*     */   
/*     */   public Object visitRepSep(NodeRepSep n)
/*     */   {
/* 109 */     if (this.level > this.maxDepth)
/* 110 */       return getSpaces(n) + "_";
/* 111 */     if (n.getChildCount() == 0)
/* 112 */       return getSpaces(n) + "?";
/* 113 */     if ((n != this.gui.theTreePanel.selectedNode) && (!n.actionText.trim().isEmpty()))
/* 114 */       return String.format("%saction@%s", new Object[] { getSpaces(n), n.nodeName() });
/* 115 */     return withMultiplicity((String)((NodeBase)n.getChildAt(0)).accept(this), n);
/*     */   }
/*     */   
/*     */   public Object visitRoot(NodeRoot n)
/*     */   {
/* 120 */     if (n.getChildCount() == 0)
/* 121 */       return getSpaces(n) + "?";
/* 122 */     if ((n != this.gui.theTreePanel.selectedNode) && (!n.actionText.trim().isEmpty()))
/* 123 */       return String.format("%saction@%s", new Object[] { getSpaces(n), n.nodeName() });
/* 124 */     if (this.activeNodes.contains(n)) {
/* 125 */       return getSpaces(n) + "_";
/*     */     }
/* 127 */     this.activeNodes.add(n);
/*     */     
/*     */ 
/* 130 */     String ast = (String)((NodeBase)n.getChildAt(0)).accept(this);
/*     */     
/* 132 */     this.activeNodes.remove(n);
/* 133 */     return ast;
/*     */   }
/*     */   
/*     */ 
/*     */   public Object visitSemPred(NodeSemPred n)
/*     */   {
/* 139 */     return getSpaces(n) + "_";
/*     */   }
/*     */   
/*     */   public Object visitSequence(NodeSequence n)
/*     */   {
/* 144 */     if (this.level > this.maxDepth)
/* 145 */       return getSpaces(n) + "_";
/* 146 */     if ((n != this.gui.theTreePanel.selectedNode) && (!n.actionText.trim().isEmpty()))
/* 147 */       return String.format("%saction@%s", new Object[] { getSpaces(n), n.nodeName() });
/* 148 */     if (n.getChildCount() == 0)
/* 149 */       return getSpaces(n) + "?";
/* 150 */     ArrayList<NodeBase> childNodes = childNodesInAST(n);
/* 151 */     int cc = childNodes.size();
/* 152 */     StringBuilder sb = new StringBuilder();
/* 153 */     if (cc == 0) {
/* 154 */       sb.append(getSpaces(n)).append("Array()");
/* 155 */       return sb.toString(); }
/* 156 */     if (cc == 1) {
/* 157 */       sb.append(((NodeBase)childNodes.get(0)).accept(this));
/* 158 */       return withMultiplicity(sb.toString(), n);
/*     */     }
/* 160 */     sb.append(getSpaces(n)).append("Array(\n");
/* 161 */     this.level += 1;
/* 162 */     for (int i = 0; i < cc; i++) {
/* 163 */       sb.append(((NodeBase)childNodes.get(i)).accept(this));
/* 164 */       if (i == cc - 1) {
/* 165 */         sb.append("\n");
/*     */       } else
/* 167 */         sb.append(",\n");
/*     */     }
/* 169 */     this.level -= 1;
/* 170 */     sb.append(getSpaces(n)).append(")");
/* 171 */     return withMultiplicity(sb.toString(), n);
/*     */   }
/*     */   
/*     */   private ArrayList<NodeBase> childNodesInAST(NodeSequence ns)
/*     */   {
/* 176 */     ArrayList<NodeBase> al = new ArrayList();
/* 177 */     for (int i = 0; i < ns.getChildCount(); i++) {
/* 178 */       NodeBase nc = (NodeBase)ns.getChildAt(i);
/* 179 */       if ((nc.multiplicity != Multiplicity.Guard) && (nc.multiplicity != Multiplicity.Not) && (!nc.isDropped) && (!(nc instanceof NodeSemPred)))
/*     */       {
/*     */ 
/* 182 */         al.add(nc); }
/*     */     }
/* 184 */     return al;
/*     */   }
/*     */   
/*     */   private String withMultiplicity(String ast, NodeBase n) {
/* 188 */     StringBuilder sb = new StringBuilder();
/* 189 */     if (n.multiplicity == Multiplicity.ZeroOrOne) {
/* 190 */       if (ast.contains("\n")) {
/* 191 */         sb.append(getSpaces(n)).append("Option(\n");
/* 192 */         sb.append(pad(ast)).append("\n");
/* 193 */         sb.append(getSpaces(n)).append(")");
/*     */       } else {
/* 195 */         sb.append(getSpaces(n)).append("Option(").append(stripSpaces(ast)).append(")");
/*     */       }
/* 197 */     } else if ((n.multiplicity == Multiplicity.OneOrMore) || (n.multiplicity == Multiplicity.ZeroOrMore))
/*     */     {
/* 199 */       if (ast.contains("\n")) {
/* 200 */         sb.append(getSpaces(n)).append("List(\n");
/* 201 */         sb.append(pad(ast)).append("\n");
/* 202 */         sb.append(getSpaces(n)).append(")");
/*     */       } else {
/* 204 */         sb.append(getSpaces(n)).append("List(").append(stripSpaces(ast)).append(")");
/*     */       }
/*     */     } else {
/* 207 */       sb.append(ast);
/*     */     }
/* 209 */     if (!n.description.isEmpty())
/* 210 */       sb.append(" <").append(n.description).append(">");
/* 211 */     return sb.toString();
/*     */   }
/*     */   
/*     */   private String getSpaces(NodeBase n) {
/* 215 */     if (this.level == 0)
/* 216 */       return "";
/* 217 */     if (this.level == 1)
/* 218 */       return this.spacer;
/* 219 */     if (this.spacers.containsKey(Integer.valueOf(this.level))) {
/* 220 */       return (String)this.spacers.get(Integer.valueOf(this.level));
/*     */     }
/* 222 */     StringBuilder sb = new StringBuilder();
/* 223 */     for (int i = 0; i < this.level; i++)
/* 224 */       sb.append(this.spacer);
/* 225 */     this.spacers.put(Integer.valueOf(this.level), sb.toString());
/* 226 */     return sb.toString();
/*     */   }
/*     */   
/*     */   private String stripSpaces(String a)
/*     */   {
/* 231 */     if (a.startsWith(this.spacer)) {
/* 232 */       return stripSpaces(a.substring(this.spacer.length()));
/*     */     }
/* 234 */     return a;
/*     */   }
/*     */   
/*     */   private String pad(String a) {
/* 238 */     StringBuilder sb = new StringBuilder();
/* 239 */     String[] lines = a.split("\n");
/* 240 */     boolean first = true;
/* 241 */     for (String s : lines) {
/* 242 */       if (first) {
/* 243 */         first = false;
/*     */       } else {
/* 245 */         sb.append("\n");
/*     */       }
/* 247 */       sb.append(this.spacer).append(s);
/*     */     }
/* 249 */     return sb.toString();
/*     */   }
/*     */   
/*     */ 
/*     */   public Object visitWildCard(NodeWildCard n)
/*     */   {
/* 255 */     return getSpaces(n) + "*";
/*     */   }
/*     */   
/*     */ 
/* 259 */   private String spacer = "|  ";
/* 260 */   private Map<Integer, String> spacers = new HashMap();
/* 261 */   int level = 0;
/* 262 */   int maxDepth = 0;
/* 263 */   private Set<NodeBase> activeNodes = new HashSet();
/*     */ }


/* Location:              /home/yogesh/Downloads/VLL4J.jar!/net/java/vll/vll4j/gui/VisitorAstDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */