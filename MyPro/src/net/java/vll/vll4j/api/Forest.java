/*     */ package net.java.vll.vll4j.api;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.TreeMap;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.script.Compilable;
/*     */ import javax.script.CompiledScript;
/*     */ import javax.script.ScriptEngine;
/*     */ import javax.script.ScriptEngineManager;
/*     */ import javax.script.ScriptException;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import net.java.vll.vll4j.combinator.PackratParsers;
/*     */ import net.java.vll.vll4j.combinator.Reader;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.xml.sax.SAXException;
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
/*     */ public class Forest
/*     */ {
/*     */   private void populateNode(Node xn, NodeBase pn)
/*     */   {
/*  42 */     NamedNodeMap attr = xn.getAttributes();
/*  43 */     if (attr.getNamedItem("Drop") != null) {
/*  44 */       pn.isDropped = true;
/*     */     }
/*  46 */     if (attr.getNamedItem("ActionText") != null) {
/*  47 */       pn.actionText = attr.getNamedItem("ActionText").getTextContent();
/*  48 */       String status = compileActionCode(pn);
/*  49 */       if (status != null) {
/*  50 */         throw new IllegalArgumentException(String.format("Action-Code error at '%s': %s%n", new Object[] { pn.nodeName(), status }));
/*     */       }
/*     */     }
/*     */     
/*  54 */     if (attr.getNamedItem("Description") != null) {
/*  55 */       pn.description = attr.getNamedItem("Description").getTextContent();
/*     */     }
/*  57 */     if (attr.getNamedItem("ErrMsg") != null) {
/*  58 */       pn.errorMessage = attr.getNamedItem("ErrMsg").getTextContent();
/*     */     }
/*  60 */     if (attr.getNamedItem("Mult") != null) {
/*  61 */       String m = attr.getNamedItem("Mult").getTextContent();
/*  62 */       if (m.equals("*")) {
/*  63 */         pn.multiplicity = Multiplicity.ZeroOrMore;
/*  64 */       } else if (m.equals("+")) {
/*  65 */         pn.multiplicity = Multiplicity.OneOrMore;
/*  66 */       } else if (m.equals("?")) {
/*  67 */         pn.multiplicity = Multiplicity.ZeroOrOne;
/*  68 */       } else if (m.equals("0")) {
/*  69 */         pn.multiplicity = Multiplicity.Not;
/*  70 */       } else if (m.equals("=")) {
/*  71 */         pn.multiplicity = Multiplicity.Guard;
/*     */       }
/*     */     }
/*  74 */     if ((!(pn instanceof NodeChoice)) && 
/*  75 */       (!(pn instanceof NodeLiteral)) && 
/*  76 */       (!(pn instanceof NodeReference)) && 
/*  77 */       (!(pn instanceof NodeRegex)) && 
/*  78 */       (!(pn instanceof NodeRepSep))) {
/*  79 */       if ((pn instanceof NodeRoot)) {
/*  80 */         if (attr.getNamedItem("Packrat") != null) {
/*  81 */           ((NodeRoot)pn).isPackrat = true;
/*     */         }
/*  83 */       } else if (((pn instanceof NodeSequence)) && 
/*  84 */         (attr.getNamedItem("Commit") != null)) {
/*  85 */         ((NodeSequence)pn).commitIndex = Integer.parseInt(attr.getNamedItem("Commit").getTextContent());
/*     */       }
/*     */     }
/*  88 */     NodeList clist = xn.getChildNodes();
/*  89 */     for (int i = 0; i < clist.getLength(); i++) {
/*  90 */       Node cn = clist.item(i);
/*  91 */       String elmtName = cn.getNodeName();
/*  92 */       if (elmtName.equals("Choice")) {
/*  93 */         NodeBase c = new NodeChoice();
/*  94 */         pn.add(c);
/*  95 */         populateNode(cn, c);
/*  96 */       } else if (elmtName.equals("Token")) {
/*  97 */         String tokenName = cn.getAttributes().getNamedItem("Ref").getTextContent();
/*  98 */         String tokenValue = (String)this.tokenBank.get(tokenName);
/*  99 */         NodeBase c = tokenValue.charAt(0) == 'L' ? new NodeLiteral(tokenName) : new NodeRegex(tokenName);
/*     */         
/* 101 */         pn.add(c);
/* 102 */         populateNode(cn, c);
/* 103 */       } else if (elmtName.equals("Reference")) {
/* 104 */         String ruleName = cn.getAttributes().getNamedItem("Ref").getTextContent();
/* 105 */         NodeBase c = new NodeReference(ruleName);
/* 106 */         pn.add(c);
/* 107 */         populateNode(cn, c);
/* 108 */       } else if (elmtName.equals("RepSep")) {
/* 109 */         NodeBase c = new NodeRepSep();
/* 110 */         pn.add(c);
/* 111 */         populateNode(cn, c);
/* 112 */       } else if (!elmtName.equals("Root"))
/*     */       {
/* 114 */         if (elmtName.equals("Sequence")) {
/* 115 */           NodeBase c = new NodeSequence();
/* 116 */           pn.add(c);
/* 117 */           populateNode(cn, c);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void openInputStream(InputStream is, boolean tokensOnly) throws ParserConfigurationException, SAXException, IOException {
/* 124 */     DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
/* 125 */     DocumentBuilder db = dbf.newDocumentBuilder();
/* 126 */     Document d = db.parse(is);
/* 127 */     Element docElmt = d.getDocumentElement();
/* 128 */     NodeList regs = docElmt.getElementsByTagName("Regex");
/* 129 */     for (int i = 0; i < regs.getLength(); i++) {
/* 130 */       Node r = regs.item(i);
/* 131 */       String regName = r.getAttributes().getNamedItem("Name").getTextContent();
/* 132 */       String regPat = r.getAttributes().getNamedItem("Pattern").getTextContent();
/* 133 */       this.tokenBank.put(regName, "R" + regPat);
/*     */     }
/* 135 */     NodeList lits = docElmt.getElementsByTagName("Literal");
/* 136 */     for (int i = 0; i < lits.getLength(); i++) {
/* 137 */       Node r = lits.item(i);
/* 138 */       String litName = r.getAttributes().getNamedItem("Name").getTextContent();
/* 139 */       String litPat = r.getAttributes().getNamedItem("Pattern").getTextContent();
/* 140 */       this.tokenBank.put(litName, "L" + litPat);
/*     */     }
/* 142 */     if (!tokensOnly) {
/* 143 */       this.whiteSpace = docElmt.getElementsByTagName("Whitespace").item(0).getTextContent();
/* 144 */       this.comment = docElmt.getElementsByTagName("Comments").item(0).getTextContent();
/* 145 */       NodeList parsers = docElmt.getElementsByTagName("Parser");
/* 146 */       for (int i = 0; i < parsers.getLength(); i++) {
/* 147 */         Node xNode = parsers.item(i);
/* 148 */         String ruleName = xNode.getAttributes().getNamedItem("Name").getTextContent();
/* 149 */         NodeRoot root = new NodeRoot(ruleName);
/* 150 */         populateNode(xNode, root);
/* 151 */         this.ruleBank.put(ruleName, root);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private ActionFunction compile(String script) throws ScriptException {
/* 157 */     if (this.compilable == null) {
/* 158 */       this.scriptEngine = new ScriptEngineManager().getEngineByName("JavaScript");
/* 159 */       this.compilable = ((Compilable)this.scriptEngine);
/*     */     }
/* 161 */     script = script.substring(script.indexOf('('));
/* 162 */     final CompiledScript cs = this.compilable.compile(String.format("(function %s)(vllARG)", new Object[] { script }));
/* 163 */     new ActionFunction()
/*     */     {
/*     */       public Object run(Object arg, Reader input, int endOffset) throws ScriptException {
/* 166 */         cs.getEngine().put("vllARG", arg);
/* 167 */         cs.getEngine().put("vllLine", Integer.valueOf(input.line()));
/* 168 */         cs.getEngine().put("vllCol", Integer.valueOf(input.column()));
/* 169 */         cs.getEngine().put("vllOffset", Integer.valueOf(input.offset()));
/* 170 */         cs.getEngine().put("vllInput", input.source().subSequence(input.offset(), endOffset));
/* 171 */         cs.getEngine().put("vllLastNoSuccess", PackratParsers.lastNoSuccess);
/* 172 */         for (String k : Forest.this.bindings.keySet()) {
/* 173 */           cs.getEngine().put(k, Forest.this.bindings.get(k));
/*     */         }
/* 175 */         return cs.eval();
/*     */       }
/*     */     };
/*     */   }
/*     */   
/*     */   public String compileActionCode(NodeBase node) {
/* 181 */     String script = node.actionText;
/* 182 */     if (script.trim().isEmpty()) {
/* 183 */       node.actionFunction = null;
/* 184 */       return null;
/*     */     }
/* 186 */     if (!this.functionMatcher.reset(script).matches()) {
/* 187 */       node.actionFunction = null;
/* 188 */       return "Need JavaScript function with 1 argument";
/*     */     }
/*     */     try {
/* 191 */       node.actionFunction = compile(script);
/* 192 */       return null;
/*     */     } catch (Exception e) {
/* 194 */       node.actionFunction = null;
/* 195 */       String message = e.getMessage();
/* 196 */       return message.contains(": ") ? message.substring(message.indexOf(": ") + 2) : message;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/* 202 */   ScriptEngine scriptEngine = null;
/* 203 */   Compilable compilable = null;
/* 204 */   private Matcher functionMatcher = Pattern.compile("\\s*f(?:u(?:n(?:c(?:t(?:i(?:on?)?)?)?)?)?)?\\s*\\(\\s*[a-zA-Z][a-zA-Z0-9]*\\s*\\)(?s:.*)").matcher("");
/*     */   
/* 206 */   public Map<String, String> tokenBank = new TreeMap();
/* 207 */   public Map<String, NodeBase> ruleBank = new TreeMap();
/*     */   public String whiteSpace;
/* 209 */   public String comment; public Map<String, Object> bindings = new HashMap();
/*     */ }


/* Location:              /home/yogesh/Downloads/VLL4J.jar!/net/java/vll/vll4j/api/Forest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */