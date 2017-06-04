/*     */ package net.java.vll.vll4j.combinator;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RegexParsers
/*     */   extends Parsers
/*     */ {
/*  34 */   protected Pattern whiteSpace = Pattern.compile("\\s+");
/*     */   
/*     */   public boolean skipWhitespace() {
/*  37 */     return this.whiteSpace.toString().length() > 0;
/*     */   }
/*     */   
/*     */   protected int handleWhiteSpace(CharSequence source, int offset) {
/*  41 */     if (skipWhitespace()) {
/*  42 */       Matcher m = this.whiteSpace.matcher(source.subSequence(offset, source.length()));
/*  43 */       if (m.lookingAt()) {
/*  44 */         return offset + m.end();
/*     */       }
/*  46 */       return offset;
/*     */     }
/*  48 */     return offset;
/*     */   }
/*     */   
/*     */   public Parsers.Parser<CharSequence> literal(String lit)
/*     */   {
/*  53 */     return literal(String.format("expected literal(%s)", new Object[] { lit }), lit);
/*     */   }
/*     */   
/*     */   private boolean startsWith(CharSequence cs, String t) {
/*  57 */     if (t.length() > cs.length())
/*  58 */       return false;
/*  59 */     for (int i = 0; i < t.length(); i++)
/*  60 */       if (cs.charAt(i) != t.charAt(i))
/*  61 */         return false;
/*  62 */     return true;
/*     */   }
/*     */   
/*     */   public Parsers.Parser<CharSequence> literal(final String errMsg, final String lit) {
/*  66 */     new Parsers.Parser()
/*     */     {
/*     */       public Parsers.ParseResult<CharSequence> apply(Reader input) {
/*  69 */         int offset2 = RegexParsers.this.handleWhiteSpace(input.source(), input.offset());
/*  70 */         CharSequence cs = input.source();
/*  71 */         if (RegexParsers.this.startsWith(cs.subSequence(offset2, cs.length()), lit)) {
/*  72 */           return new Parsers.Success(lit, input.drop(offset2 - input.offset() + lit.length()));
/*     */         }
/*  74 */         return new Parsers.Failure(errMsg, input);
/*     */       }
/*     */     };
/*     */   }
/*     */   
/*     */   public Parsers.Parser<CharSequence> regex(Pattern p) {
/*  80 */     return regex(String.format("expected regex(%s)", new Object[] { p.toString() }), p);
/*     */   }
/*     */   
/*     */   public Parsers.Parser<CharSequence> regex(final String errMsg, final Pattern p) {
/*  84 */     new Parsers.Parser()
/*     */     {
/*     */       public Parsers.ParseResult<CharSequence> apply(Reader input) {
/*  87 */         int offset2 = RegexParsers.this.handleWhiteSpace(input.source(), input.offset());
/*  88 */         if ((p.toString().equals("\\\\z")) && (offset2 >= input.source().length())) {
/*  89 */           return new Parsers.Success("", input.drop(offset2 - input.offset()));
/*     */         }
/*  91 */         CharSequence cs = input.source();
/*  92 */         Matcher m = p.matcher(cs.subSequence(offset2, cs.length()));
/*  93 */         if (m.lookingAt()) {
/*  94 */           return new Parsers.Success(m.group(), input.drop(offset2 - input.offset() + m.group().length()));
/*     */         }
/*  96 */         return new Parsers.Failure(errMsg, input);
/*     */       }
/*     */     };
/*     */   }
/*     */   
/*     */ 
/*     */   public <T> Parsers.Parser<T> phrase(final Parsers.Parser<T> p)
/*     */   {
/* 104 */     new Parsers.Parser()
/*     */     {
/*     */       public Parsers.ParseResult<T> apply(Reader input) {
/* 107 */         Parsers.ParseResult<T> pr = p.apply(input);
/*     */         
/* 109 */         if (!pr.successful())
/* 110 */           return new Parsers.Failure("phrase", input, (Parsers.NoSuccess)pr);
/* 111 */         if (pr.next().atEnd())
/* 112 */           return pr;
/* 113 */         int step = RegexParsers.this.handleWhiteSpace(pr.next().source(), pr.next().offset());
/* 114 */         if (pr.next().drop(step - pr.next().offset()).atEnd()) {
/* 115 */           return pr;
/*     */         }
/* 117 */         return new Parsers.Failure(String.format("expected end of input at [%d, %d]", new Object[] { Integer.valueOf(pr.next().line()), Integer.valueOf(pr.next().column()) }), input);
/*     */       }
/*     */     };
/*     */   }
/*     */   
/*     */   public <T> Parsers.ParseResult<T> parseAll(Parsers.Parser<T> p, CharSequence cs)
/*     */   {
/* 124 */     return phrase(p).apply(new CharSequenceReader(cs));
/*     */   }
/*     */   
/*     */   public <T> Parsers.ParseResult<T> parseAll(Parsers.Parser<T> p, Reader rdr) {
/* 128 */     return phrase(p).apply(rdr);
/*     */   }
/*     */ }


/* Location:              /home/yogesh/Downloads/VLL4J.jar!/net/java/vll/vll4j/combinator/RegexParsers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */