/*     */ package net.java.vll.vll4j.combinator;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SimpleLexingParsers
/*     */   extends RegexParsers
/*     */ {
/*     */   public void reset()
/*     */   {
/*  34 */     this.tokenLexerMap.clear();
/*  35 */     this.setupDone = false;
/*  36 */     this.theLiterals.clear();
/*  37 */     this.sortedLiterals = ((Object[][])null);
/*  38 */     this.literalsMatcher = null;
/*  39 */     this.theRegexs.clear();
/*  40 */     this.regexMatchers = null;
/*  41 */     this.tokenList.clear();
/*  42 */     this.lastTokenId = null;
/*     */   }
/*     */   
/*     */   public void resetWhitespace() {
/*  46 */     String ws = Utils.unEscape(this.whiteSpaceRegex);
/*  47 */     String cmts = Utils.unEscape(this.commentSpecRegex);
/*  48 */     if ((ws.isEmpty()) && (cmts.isEmpty())) {
/*  49 */       this.whiteSpace = Pattern.compile("");
/*  50 */     } else if (cmts.isEmpty()) {
/*  51 */       this.whiteSpace = Pattern.compile(ws);
/*  52 */     } else if (ws.isEmpty()) {
/*  53 */       this.whiteSpace = Pattern.compile(cmts);
/*     */     } else {
/*  55 */       String wsp = String.format("(?:(?:%s)|(?:%s))+", new Object[] { ws, cmts });
/*  56 */       this.whiteSpace = Pattern.compile(wsp);
/*     */     }
/*     */   }
/*     */   
/*     */   public void defineLiteral(String tokenName, String lit) {
/*  61 */     if (this.setupDone)
/*  62 */       throw new IllegalStateException();
/*  63 */     String litKey = "L" + Utils.escapeMetachars(lit);
/*  64 */     if (this.tokenLexerMap.containsKey(litKey)) {
/*  65 */       throw new IllegalArgumentException(String.format("Literal '%s' already defined", new Object[] { litKey.substring(1) }));
/*     */     }
/*     */     
/*  68 */     this.theLiterals.add(lit);
/*  69 */     int id = -this.theLiterals.size();
/*  70 */     this.tokenLexerMap.put(litKey, lexerById(id));
/*  71 */     this.tokenList.add(0, tokenName);
/*     */   }
/*     */   
/*     */   public void defineRegex(String tokenName, Pattern pat)
/*     */   {
/*  76 */     if (this.setupDone)
/*  77 */       throw new IllegalStateException();
/*  78 */     String regString = pat.toString();
/*  79 */     String regKey = "R" + regString;
/*  80 */     if (this.tokenLexerMap.containsKey(regKey)) {
/*  81 */       throw new IllegalArgumentException(String.format("Regex '%s' already defined", new Object[] { regKey.substring(1) }));
/*     */     }
/*     */     
/*  84 */     this.theRegexs.add(regString);
/*  85 */     int id = this.theRegexs.size() - 1;
/*  86 */     this.tokenLexerMap.put(regKey, lexerById(id));
/*  87 */     this.tokenList.add(tokenName);
/*     */   }
/*     */   
/*     */   public Parsers.Parser<CharSequence> literal2(String errMsg, String lit)
/*     */   {
/*  92 */     String litKey = "L" + Utils.escapeMetachars(lit);
/*  93 */     if (this.tokenLexerMap.containsKey(litKey)) {
/*  94 */       return lexer2parser((Lexer)this.tokenLexerMap.get(litKey), errMsg);
/*     */     }
/*  96 */     throw new IllegalArgumentException("Undefined literal");
/*     */   }
/*     */   
/*     */ 
/*     */   public Parsers.Parser<CharSequence> regex2(String errMsg, Pattern pat)
/*     */   {
/* 102 */     String regString = pat.toString();
/* 103 */     String regKey = "R" + regString;
/* 104 */     if (this.tokenLexerMap.containsKey(regKey)) {
/* 105 */       return lexer2parser((Lexer)this.tokenLexerMap.get(regKey), errMsg);
/*     */     }
/* 107 */     throw new IllegalArgumentException("Undefined regex");
/*     */   }
/*     */   
/*     */   public Parsers.Parser<CharSequence> wildCard(String errMsg)
/*     */   {
/* 112 */     return lexer2parser(lexerById(Integer.MAX_VALUE), errMsg);
/*     */   }
/*     */   
/*     */   private Parsers.Parser<CharSequence> lexer2parser(final Lexer lexer, final String errMsg) {
/* 116 */     new Parsers.Parser() {
/*     */       public Parsers.ParseResult<CharSequence> apply(Reader in) {
/*     */         try {
/* 119 */           Object[] lexRes = lexer.apply(in);
/* 120 */           if (lexRes == null) {
/* 121 */             return new Parsers.Failure(errMsg + SimpleLexingParsers.this.lastTokenId, in);
/*     */           }
/* 123 */           return new Parsers.Success((CharSequence)lexRes[0], in.drop(((Integer)lexRes[2]).intValue()));
/*     */         } catch (StackOverflowError soe) {
/* 125 */           throw new RuntimeException(String.format("Java bug 5050507 at (%d, %d)", new Object[] { Integer.valueOf(in.line()), Integer.valueOf(in.column()) }), soe);
/*     */         }
/*     */       }
/*     */     };
/*     */   }
/*     */   
/*     */   private Lexer lexerById(final int id) {
/* 132 */     new Lexer(id) {
/*     */       Object[] apply(Reader input) {
/* 134 */         if (!SimpleLexingParsers.this.setupDone) {
/* 135 */           SimpleLexingParsers.this.setupLexerLiterals();
/* 136 */           SimpleLexingParsers.this.setupLexerRegexs();
/* 137 */           SimpleLexingParsers.this.tokenArray = ((String[])SimpleLexingParsers.this.tokenList.toArray(new String[SimpleLexingParsers.this.tokenList.size()]));
/* 138 */           SimpleLexingParsers.this.setupDone = true;
/*     */         }
/* 140 */         SimpleLexingParsers.this.lastTokenId = "";
/* 141 */         Object[] litRes = SimpleLexingParsers.this.theLiterals.isEmpty() ? null : SimpleLexingParsers.this.lexKnownLiterals(input);
/* 142 */         Object[] regRes = SimpleLexingParsers.this.regexMatchers.length == 0 ? null : SimpleLexingParsers.this.lexKnownRegexs(input);
/* 143 */         CharSequence lit = litRes == null ? null : (CharSequence)litRes[0];
/* 144 */         CharSequence reg = regRes == null ? null : (CharSequence)regRes[0];
/*     */         
/* 146 */         if (lit == null) {
/* 147 */           if (reg == null) {
/* 148 */             return null;
/*     */           }
/* 150 */           int regId = ((Integer)regRes[1]).intValue();
/* 151 */           if ((regId == id) || (id == Integer.MAX_VALUE)) {
/* 152 */             return regRes;
/*     */           }
/* 154 */           SimpleLexingParsers.this.lastTokenId = SimpleLexingParsers.this.tokenArray[(regId + SimpleLexingParsers.this.sortedLiterals.length)];
/* 155 */           return null;
/*     */         }
/*     */         
/*     */ 
/* 159 */         if (reg == null) {
/* 160 */           int litId = ((Integer)litRes[1]).intValue();
/* 161 */           if ((litId == id) || (id == Integer.MAX_VALUE)) {
/* 162 */             return litRes;
/*     */           }
/* 164 */           SimpleLexingParsers.this.lastTokenId = SimpleLexingParsers.this.tokenArray[(litId + SimpleLexingParsers.this.sortedLiterals.length)];
/* 165 */           return null;
/*     */         }
/*     */         
/* 168 */         if (lit.length() >= reg.length()) {
/* 169 */           int litId = ((Integer)litRes[1]).intValue();
/* 170 */           if (litId == id) {
/* 171 */             return litRes;
/*     */           }
/* 173 */           SimpleLexingParsers.this.lastTokenId = SimpleLexingParsers.this.tokenArray[(litId + SimpleLexingParsers.this.sortedLiterals.length)];
/* 174 */           return null;
/*     */         }
/*     */         
/* 177 */         int regId = ((Integer)regRes[1]).intValue();
/* 178 */         if (regId == id) {
/* 179 */           return regRes;
/*     */         }
/* 181 */         SimpleLexingParsers.this.lastTokenId = SimpleLexingParsers.this.tokenArray[(regId + SimpleLexingParsers.this.sortedLiterals.length)];
/* 182 */         return null;
/*     */       }
/*     */     };
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private Object[] lexKnownLiterals(Reader input)
/*     */   {
/* 192 */     int offset = input.offset();
/* 193 */     int offset2 = handleWhiteSpace(input.source(), offset);
/*     */     
/* 195 */     CharSequence cs = input.source().subSequence(offset2, input.source().length());
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
/* 207 */     int k = -1;int patLen = 0;
/* 208 */     String pattern = null;
/* 209 */     for (int i = 0; i < this.sortedLiterals.length; i++) {
/* 210 */       pattern = (String)this.sortedLiterals[i][0];
/* 211 */       patLen = pattern.length();
/* 212 */       if (cs.subSequence(0, Math.min(patLen, cs.length())).equals(pattern)) {
/* 213 */         k = i;
/* 214 */         break;
/*     */       }
/*     */     }
/* 217 */     if (k >= 0) {
/* 218 */       return new Object[] { pattern, (Integer)this.sortedLiterals[k][1], Integer.valueOf(offset2 - offset + patLen) };
/*     */     }
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
/* 232 */     return null;
/*     */   }
/*     */   
/*     */   private Object[] lexKnownRegexs(Reader input)
/*     */   {
/* 237 */     int offset = input.offset();
/* 238 */     int offset2 = handleWhiteSpace(input.source(), offset);
/* 239 */     CharSequence cs = input.source().subSequence(offset2, input.source().length());
/*     */     
/* 241 */     int idx = -1;int maxLength = -1;
/*     */     
/* 243 */     for (int i = 0; i < this.regexMatchers.length; i++) {
/* 244 */       Matcher m = this.regexMatchers[i].reset(cs);
/* 245 */       if ((m.lookingAt()) && (m.end() - m.start() > maxLength)) {
/* 246 */         maxLength = m.end() - m.start();
/* 247 */         idx = i;
/*     */       }
/*     */     }
/*     */     
/* 251 */     if (idx == -1) {
/* 252 */       return null;
/*     */     }
/* 254 */     return new Object[] { cs.subSequence(0, maxLength), Integer.valueOf(idx), Integer.valueOf(offset2 - offset + maxLength) };
/*     */   }
/*     */   
/*     */   private void setupLexerLiterals() {
/* 258 */     this.sortedLiterals = new Object[this.theLiterals.size()][];
/* 259 */     for (int i = 0; i < this.sortedLiterals.length; i++) {
/* 260 */       this.sortedLiterals[i] = new Object[2];
/* 261 */       this.sortedLiterals[i][0] = this.theLiterals.get(i);
/* 262 */       this.sortedLiterals[i][1] = Integer.valueOf((i + 1) * -1);
/*     */     }
/*     */     
/* 265 */     Arrays.sort(this.sortedLiterals, this.literalsComparator);
/* 266 */     StringBuilder sb = new StringBuilder();
/* 267 */     sb.append("(");
/* 268 */     for (int i = 0; i < this.sortedLiterals.length; i++)
/*     */     {
/* 270 */       sb.append(Utils.escapeMetachars(Utils.unEscape((String)this.sortedLiterals[i][0])));
/* 271 */       if (i != this.sortedLiterals.length - 1)
/* 272 */         sb.append(")|(");
/*     */     }
/* 274 */     sb.append(")");
/* 275 */     String pattern = sb.toString();
/* 276 */     this.literalsMatcher = Pattern.compile(pattern).matcher("");
/*     */   }
/*     */   
/*     */   private void setupLexerRegexs()
/*     */   {
/* 281 */     this.regexMatchers = new Matcher[this.theRegexs.size()];
/* 282 */     for (int i = 0; i < this.regexMatchers.length; i++) {
/* 283 */       this.regexMatchers[i] = Pattern.compile((String)this.theRegexs.get(i)).matcher("");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/* 288 */   private String lastTokenId = null;
/* 289 */   public String whiteSpaceRegex = Utils.reEscape(this.whiteSpace.pattern());
/* 290 */   public String commentSpecRegex = "";
/* 291 */   private Map<String, Lexer> tokenLexerMap = new HashMap();
/* 292 */   private boolean setupDone = false;
/* 293 */   private List<String> theLiterals = new ArrayList();
/* 294 */   private Object[][] sortedLiterals = (Object[][])null;
/* 295 */   private Matcher literalsMatcher = null;
/* 296 */   private List<String> tokenList = new ArrayList();
/* 297 */   private String[] tokenArray = null;
/* 298 */   private List<String> theRegexs = new ArrayList();
/* 299 */   private Matcher[] regexMatchers = null;
/* 300 */   private Comparator literalsComparator = new Comparator()
/*     */   {
/*     */     public int compare(Object a, Object b) {
/* 303 */       String aa = (String)((Object[])(Object[])a)[0];
/* 304 */       String bb = (String)((Object[])(Object[])b)[0];
/* 305 */       return bb.compareTo(aa);
/*     */     }
/*     */   };
/* 308 */   private Comparator literalsComparator2 = new Comparator()
/*     */   {
/*     */     public int compare(Object a, Object b) {
/* 311 */       String aa = (String)((Object[])(Object[])a)[0];
/* 312 */       String bb = (String)b;
/* 313 */       return bb.substring(0, Math.min(aa.length(), bb.length())).compareTo(aa);
/*     */     }
/*     */   };
/*     */   
/*     */   private static abstract class Lexer
/*     */   {
/*     */     abstract Object[] apply(Reader paramReader);
/*     */   }
/*     */ }


/* Location:              /home/yogesh/Downloads/VLL4J.jar!/net/java/vll/vll4j/combinator/SimpleLexingParsers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */