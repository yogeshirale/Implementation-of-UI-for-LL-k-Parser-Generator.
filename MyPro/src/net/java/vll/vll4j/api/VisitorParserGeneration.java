/*     */ package net.java.vll.vll4j.api;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.script.ScriptException;
/*     */ import net.java.vll.vll4j.combinator.PackratParsers;
/*     */ import net.java.vll.vll4j.combinator.Parsers.Failure;
/*     */ import net.java.vll.vll4j.combinator.Parsers.ParseResult;
/*     */ import net.java.vll.vll4j.combinator.Parsers.Parser;
/*     */ import net.java.vll.vll4j.combinator.Parsers.Success;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VisitorParserGeneration
/*     */   extends VisitorBase
/*     */ {
/*     */   private PackratParsers parsersInstance;
/*     */   private boolean traceAll;
/*     */   
/*     */   public VisitorParserGeneration(Forest theForest, PackratParsers parsersInstance, boolean traceAll, boolean[] stopFlag)
/*     */   {
/*  39 */     parsersInstance.reset();
/*  40 */     this.theForest = theForest;
/*  41 */     this.parsersInstance = parsersInstance;
/*  42 */     this.traceAll = traceAll;
/*  43 */     createTokenParsers();
/*  44 */     this.visitorNodeValidation = new VisitorValidation();
/*  45 */     this.parserGeneratedOk = true;
/*  46 */     this.stopFlag = stopFlag;
/*     */   }
/*     */   
/*     */   public VisitorParserGeneration(Forest theForest, PackratParsers parsersInstance, boolean traceAll) {
/*  50 */     this(theForest, parsersInstance, traceAll, new boolean[] { Boolean.FALSE.booleanValue() });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void createTokenParsers()
/*     */   {
/*  60 */     for (Map.Entry<String, String> e : this.theForest.tokenBank.entrySet())
/*  61 */       if (!((String)e.getKey()).endsWith("_"))
/*     */       {
/*  63 */         String tokenName = (String)e.getKey();
/*  64 */         String pat = (String)e.getValue();
/*  65 */         if (pat.startsWith("L")) {
/*  66 */           this.parsersInstance.defineLiteral(tokenName, Utils.unEscape(pat.substring(1)));
/*  67 */         } else if (pat.startsWith("R")) {
/*  68 */           this.parsersInstance.defineRegex(tokenName, Pattern.compile(Utils.unEscape(pat.substring(1))));
/*     */         } else
/*  70 */           throw new IllegalArgumentException("Bad token");
/*     */       }
/*     */   }
/*     */   
/*     */   private void traceIndent() {
/*  75 */     for (int i = 0; i < this.traceLevel; i++) {
/*  76 */       System.out.print("|  ");
/*     */     }
/*     */   }
/*     */   
/*     */   Parsers.Parser<? extends Object> withAction(final Parsers.Parser<? extends Object> p, final NodeBase node) {
/*  81 */     if ((!(node instanceof NodeSemPred)) && (node.actionFunction != null)) {
/*  82 */       Parsers.Parser<? extends Object> pm = new Parsers.Parser()
/*     */       {
/*     */         public Parsers.ParseResult<? extends Object> apply(Reader input) {
/*     */           try {
/*  86 */             node.actionFunction.run(null, input, input.source().length());
/*  87 */             Parsers.ParseResult<? extends Object> res = p.apply(input);
/*  88 */             if (res.successful()) {
/*  89 */               int postWhitespace = res.next().offset() == input.offset() ? input.offset() : VisitorParserGeneration.this.parsersInstance.handleWhiteSpace(input.source(), input.offset());
/*     */               
/*  91 */               Reader forAction = input.drop(postWhitespace - input.offset());
/*  92 */               Object r2 = node.actionFunction.run(res.get(), forAction, res.next().offset());
/*  93 */               return new Parsers.Success(r2, res.next());
/*     */             }
/*  95 */             return res;
/*     */           } catch (ScriptException exc) {
/*  97 */             throw new IllegalArgumentException(String.format("Error in action-code @ %s", new Object[] { node.nodeName() }), exc);
/*     */           } catch (IllegalArgumentException exc) {
/*  99 */             throw new IllegalArgumentException(String.format("Error in 'withAction' @ %s", new Object[] { node.nodeName() }), exc);
/*     */           }
/*     */         }
/* 102 */       };
/* 103 */       return pm;
/*     */     }
/* 105 */     return p;
/*     */   }
/*     */   
/*     */   Parsers.Parser<? extends Object> withTrace(final Parsers.Parser<? extends Object> p, final NodeBase node)
/*     */   {
/* 110 */     if ((node.isTraced) || (((node instanceof NodeRoot)) && (this.traceAll))) {
/* 111 */       Parsers.Parser<? extends Object> pm = new Parsers.Parser()
/*     */       {
/*     */         public Parsers.ParseResult<? extends Object> apply(Reader input) {
/* 114 */           VisitorParserGeneration.this.traceIndent();
/* 115 */           System.out.print(String.format(">> %s (line=%d, col=%d)%n", new Object[] { node.nodeName(), Integer.valueOf(input.line()), Integer.valueOf(input.column()) }));
/* 116 */           VisitorParserGeneration.access$204(VisitorParserGeneration.this);
/* 117 */           int postWhitespace = VisitorParserGeneration.this.parsersInstance.handleWhiteSpace(input.source(), input.offset());
/* 118 */           String sample = Utils.reEscape(input.source().subSequence(postWhitespace, Math.min(input.source().length(), postWhitespace + 20)).toString());
/*     */           
/* 120 */           Parsers.ParseResult<? extends Object> res = p.apply(input);
/* 121 */           VisitorParserGeneration.access$206(VisitorParserGeneration.this);
/* 122 */           VisitorParserGeneration.this.traceIndent();
/* 123 */           System.out.print(String.format("<< %s: %s (line=%d, col=%d, input=%s)%n", new Object[] { node.nodeName(), res.getClass().getSimpleName(), Integer.valueOf(res.next().line()), Integer.valueOf(res.next().column()), sample }));
/*     */           
/* 125 */           return res;
/*     */         }
/* 127 */       };
/* 128 */       return withAction(pm, node);
/*     */     }
/* 130 */     return withAction(p, node);
/*     */   }
/*     */   
/*     */   Parsers.Parser<? extends Object> withMultiplicity(Parsers.Parser<? extends Object> p, NodeBase node)
/*     */   {
/* 135 */     Parsers.Parser<? extends Object> pm = p;
/* 136 */     if (node.multiplicity == Multiplicity.ZeroOrMore) {
/* 137 */       pm = this.parsersInstance.rep(p);
/* 138 */     } else if (node.multiplicity == Multiplicity.OneOrMore) {
/* 139 */       pm = this.parsersInstance.rep1(String.format("rep1(%s)", new Object[] { node.nodeName() }), p);
/* 140 */     } else if (node.multiplicity == Multiplicity.ZeroOrOne) {
/* 141 */       pm = this.parsersInstance.opt(p);
/* 142 */     } else if (node.multiplicity == Multiplicity.Not) {
/* 143 */       pm = this.parsersInstance.not(p);
/* 144 */     } else if (node.multiplicity == Multiplicity.Guard) {
/* 145 */       pm = this.parsersInstance.guard(p);
/*     */     }
/* 147 */     return withTrace(pm, node);
/*     */   }
/*     */   
/*     */   Parsers.Parser<CharSequence> withStopTest(final Parsers.Parser<CharSequence> p) {
/* 151 */     new Parsers.Parser()
/*     */     {
/*     */       public Parsers.ParseResult<CharSequence> apply(Reader input) {
/* 154 */         if (VisitorParserGeneration.this.stopFlag[0] != 0)
/* 155 */           throw new IllegalArgumentException("User-Requested STOP", new IOException());
/* 156 */         return p.apply(input);
/*     */       }
/*     */     };
/*     */   }
/*     */   
/*     */   public Parsers.Parser<? extends Object> visitChoice(NodeChoice n)
/*     */   {
/* 163 */     int childCount = n.getChildCount();
/* 164 */     if (n.accept(this.visitorNodeValidation) == null) {
/* 165 */       Parsers.Parser<? extends Object>[] cp = (Parsers.Parser[])new Parsers.Parser[childCount];
/* 166 */       for (int i = 0; i < childCount; i++) {
/* 167 */         cp[i] = ((Parsers.Parser)((NodeBase)n.getChildAt(i)).accept(this));
/*     */       }
/* 169 */       return withMultiplicity(this.parsersInstance.choice(n.errorMessage.isEmpty() ? String.format("choice(%s)", new Object[] { n.nodeName() }) : n.errorMessage, cp), n);
/*     */     }
/*     */     
/* 172 */     this.parserGeneratedOk = false;
/* 173 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public Parsers.Parser<? extends Object> visitLiteral(NodeLiteral n)
/*     */   {
/* 179 */     if (n.accept(this.visitorNodeValidation) == null) {
/* 180 */       String litString = Utils.unEscape(((String)this.theForest.tokenBank.get(n.literalName)).substring(1));
/* 181 */       String errMsg = n.errorMessage.isEmpty() ? String.format("%s expects literal %s, found ", new Object[] { n.nodeName(), n.literalName }) : n.errorMessage;
/*     */       
/* 183 */       return withMultiplicity(withStopTest(n.literalName.endsWith("_") ? this.parsersInstance.literal(errMsg, litString) : this.parsersInstance.literal2(errMsg, litString)), n);
/*     */     }
/*     */     
/* 186 */     this.parserGeneratedOk = false;
/* 187 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public Parsers.Parser<? extends Object> visitReference(NodeReference n)
/*     */   {
/* 193 */     if (n.accept(this.visitorNodeValidation) == null) {
/* 194 */       final String referredRuleName = n.refRuleName;
/* 195 */       NodeBase referredRule = (NodeBase)this.theForest.ruleBank.get(referredRuleName);
/* 196 */       if (!this.parserCache.containsKey(referredRuleName)) {
/* 197 */         referredRule.accept(this);
/*     */       }
/* 199 */       final Parsers.Parser<? extends Object>[] holder = (Parsers.Parser[])this.parserCache.get(referredRuleName);
/* 200 */       Parsers.Parser<? extends Object> p = new Parsers.Parser()
/*     */       {
/*     */         public Parsers.ParseResult<? extends Object> apply(Reader input) {
/*     */           try {
/* 204 */             return holder[0].apply(input);
/*     */           } catch (StackOverflowError soe) {
/* 206 */             throw new RuntimeException(String.format("Possible left-recursion in '%s'", new Object[] { referredRuleName }), soe);
/*     */           }
/*     */         }
/* 209 */       };
/* 210 */       return withMultiplicity(p, n);
/*     */     }
/* 212 */     this.parserGeneratedOk = false;
/* 213 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public Parsers.Parser<? extends Object> visitRegex(NodeRegex n)
/*     */   {
/* 219 */     if (n.accept(this.visitorNodeValidation) == null) {
/* 220 */       String regString = Utils.unEscape(((String)this.theForest.tokenBank.get(n.regexName)).substring(1));
/* 221 */       String errMsg = n.errorMessage.isEmpty() ? String.format("%s expects regex %s, found ", new Object[] { n.nodeName(), n.regexName }) : n.errorMessage;
/*     */       
/* 223 */       return withMultiplicity(withStopTest(n.regexName.endsWith("_") ? this.parsersInstance.regex(errMsg, Pattern.compile(regString)) : this.parsersInstance.regex2(errMsg, Pattern.compile(regString))), n);
/*     */     }
/*     */     
/*     */ 
/* 227 */     this.parserGeneratedOk = false;
/* 228 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public Parsers.Parser<? extends Object> visitRepSep(NodeRepSep n)
/*     */   {
/* 234 */     if (n.accept(this.visitorNodeValidation) == null) {
/* 235 */       Parsers.Parser<Object> rep = (Parsers.Parser)((NodeBase)n.getChildAt(0)).accept(this);
/* 236 */       Parsers.Parser<Object> sep = (Parsers.Parser)((NodeBase)n.getChildAt(1)).accept(this);
/* 237 */       if (n.multiplicity == Multiplicity.ZeroOrMore)
/* 238 */         return withTrace(this.parsersInstance.repSep(rep, sep), n);
/* 239 */       if (n.multiplicity == Multiplicity.OneOrMore) {
/* 240 */         return withTrace(this.parsersInstance.rep1Sep(n.errorMessage.isEmpty() ? String.format("rep1sep(%s) error", new Object[] { n.nodeName() }) : n.errorMessage, rep, sep), n);
/*     */       }
/*     */       
/* 243 */       this.parserGeneratedOk = false;
/* 244 */       return null;
/*     */     }
/*     */     
/* 247 */     this.parserGeneratedOk = false;
/* 248 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Parsers.Parser<? extends Object> visitRoot(NodeRoot n)
/*     */   {
/* 255 */     Parsers.Parser[] holder = new Parsers.Parser[1];
/* 256 */     this.parserCache.put(n.ruleName, holder);
/* 257 */     Parsers.Parser<? extends Object> p; Parsers.Parser<? extends Object> p; if (n.accept(this.visitorNodeValidation) == null) {
/* 258 */       p = (Parsers.Parser)((NodeBase)n.getChildAt(0)).accept(this);
/*     */     } else {
/* 260 */       this.parserGeneratedOk = false;
/* 261 */       p = null;
/*     */     }
/* 263 */     if (n.isPackrat) {
/* 264 */       p = this.parsersInstance.parser2packrat(p);
/*     */     }
/* 266 */     holder[0] = withTrace(p, n);
/* 267 */     return holder[0];
/*     */   }
/*     */   
/*     */   public Parsers.Parser<? extends Object> visitSemPred(final NodeSemPred n)
/*     */   {
/* 272 */     if (n.accept(this.visitorNodeValidation) == null) {
/* 273 */       Parsers.Parser<? extends Object> parser = new Parsers.Parser()
/*     */       {
/*     */         public Parsers.ParseResult<? extends Object> apply(Reader input) {
/* 276 */           Object result = null;
/*     */           try {
/* 278 */             result = n.actionFunction.run(null, input, input.source().length());
/*     */           }
/*     */           catch (ScriptException ex) {}
/* 281 */           if (result == Boolean.TRUE) {
/* 282 */             return new Parsers.Success(null, input);
/*     */           }
/* 284 */           return new Parsers.Failure(n.errorMessage.isEmpty() ? String.format("SemPred(%s)", new Object[] { n.nodeName() }) : n.errorMessage, input);
/*     */         }
/*     */         
/*     */ 
/* 288 */       };
/* 289 */       return withTrace(parser, n);
/*     */     }
/* 291 */     this.parserGeneratedOk = false;
/* 292 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public Parsers.Parser<? extends Object> visitSequence(NodeSequence n)
/*     */   {
/* 298 */     int childCount = n.getChildCount();
/* 299 */     if (n.accept(this.visitorNodeValidation) == null) {
/* 300 */       int dropMap = 0;
/* 301 */       Parsers.Parser<? extends Object>[] cp = (Parsers.Parser[])new Parsers.Parser[childCount];
/* 302 */       int i = 0; for (int mask = 1; i < childCount; mask <<= 1) {
/* 303 */         NodeBase child = (NodeBase)n.getChildAt(i);
/* 304 */         cp[i] = ((Parsers.Parser)child.accept(this));
/* 305 */         if ((child.isDropped) || (child.multiplicity == Multiplicity.Guard) || (child.multiplicity == Multiplicity.Not) || ((child instanceof NodeSemPred)))
/*     */         {
/* 307 */           dropMap |= mask;
/*     */         }
/* 302 */         i++;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 309 */       return withMultiplicity(this.parsersInstance.sequence(n.errorMessage.isEmpty() ? String.format("sequence(%s)", new Object[] { n.nodeName() }) : n.errorMessage, n.commitIndex, dropMap, cp), n);
/*     */     }
/*     */     
/* 312 */     this.parserGeneratedOk = false;
/* 313 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public Parsers.Parser<? extends Object> visitWildCard(NodeWildCard n)
/*     */   {
/* 319 */     if (n.accept(this.visitorNodeValidation) == null) {
/* 320 */       String errMsg = n.errorMessage.isEmpty() ? String.format("wildCard(%s)", new Object[] { n.nodeName() }) : n.errorMessage;
/*     */       
/* 322 */       return withMultiplicity(this.parsersInstance.wildCard(errMsg), n);
/*     */     }
/* 324 */     this.parserGeneratedOk = false;
/* 325 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 331 */   private Map<String, Parsers.Parser<? extends Object>[]> parserCache = new HashMap();
/*     */   public boolean parserGeneratedOk;
/* 333 */   private int traceLevel = 0;
/*     */   private VisitorValidation visitorNodeValidation;
/*     */   private Forest theForest;
/*     */   public boolean[] stopFlag;
/*     */ }


/* Location:              /home/yogesh/Downloads/VLL4J.jar!/net/java/vll/vll4j/api/VisitorParserGeneration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */