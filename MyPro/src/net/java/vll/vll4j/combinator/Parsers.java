/*     */ package net.java.vll.vll4j.combinator;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class Parsers
/*     */ {
/*     */   public static abstract class Parser<T>
/*     */   {
/*     */     public String name;
/*     */     
/*     */     public abstract Parsers.ParseResult<T> apply(Reader paramReader);
/*     */   }
/*     */   
/*     */   public static abstract interface ParseResult<T>
/*     */   {
/*     */     public abstract T get();
/*     */     
/*     */     public abstract boolean successful();
/*     */     
/*     */     public abstract Reader next();
/*     */   }
/*     */   
/*     */   public static class NoSuccess<T>
/*     */     implements Parsers.ParseResult<T>
/*     */   {
/*     */     public final String msg;
/*     */     
/*  47 */     public NoSuccess(String msg, Reader next) { this(msg, next, null); }
/*     */     
/*     */     public NoSuccess(String msg, Reader next, NoSuccess reason) {
/*  50 */       this.msg = msg;
/*  51 */       this.next = next;
/*  52 */       this.reason = reason;
/*  53 */       Parsers.lastNoSuccess = this;
/*     */     }
/*     */     
/*  56 */     public T get() { return null; }
/*     */     
/*  58 */     public boolean successful() { return false; }
/*     */     
/*  60 */     public Reader next() { return this.next; }
/*     */     
/*  62 */     public NoSuccess reason = null;
/*     */     private Reader next; }
/*     */   
/*     */   public static class Success<T> implements Parsers.ParseResult<T> { private final T result;
/*     */     private Reader next;
/*     */     
/*  68 */     public Success(T result, Reader next) { this.result = result;
/*  69 */       this.next = next;
/*     */     }
/*     */     
/*  72 */     public T get() { return (T)this.result; }
/*     */     
/*  74 */     public boolean successful() { return true; }
/*     */     
/*  76 */     public Reader next() { return this.next; }
/*     */   }
/*     */   
/*     */   public static class Failure<T> extends Parsers.NoSuccess<T>
/*     */   {
/*     */     public Failure(String msg, Reader next)
/*     */     {
/*  83 */       this(msg, next, null);
/*     */     }
/*     */     
/*  86 */     public Failure(String msg, Reader next, Parsers.NoSuccess reason) { super(next, reason); }
/*     */   }
/*     */   
/*     */   public static class Error extends Parsers.NoSuccess
/*     */   {
/*     */     public Error(String msg, Reader next) {
/*  92 */       super(next);
/*     */     }
/*     */     
/*  95 */     public Error(String msg, Reader next, Parsers.NoSuccess reason) { super(next, reason); }
/*     */   }
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
/*     */   public <T> Parser<T> phrase(final Parser<T> p)
/*     */   {
/* 109 */     lastNoSuccess = null;
/* 110 */     new Parser()
/*     */     {
/*     */       public Parsers.ParseResult<T> apply(Reader input) {
/* 113 */         Parsers.ParseResult<T> pr = p.apply(input);
/* 114 */         if (pr.next().atEnd()) {
/* 115 */           return pr;
/*     */         }
/* 117 */         return new Parsers.Failure("expected end of input", input);
/*     */       }
/*     */     };
/*     */   }
/*     */   
/*     */   public Parser<Object> failure(final String msg) {
/* 123 */     new Parser()
/*     */     {
/*     */       public Parsers.ParseResult<Object> apply(Reader input) {
/* 126 */         return new Parsers.Failure(msg, input);
/*     */       }
/*     */     };
/*     */   }
/*     */   
/*     */   public <T> Parser<T> success(final T value) {
/* 132 */     new Parser()
/*     */     {
/*     */       public Parsers.ParseResult<T> apply(Reader input) {
/* 135 */         return new Parsers.Success(value, input);
/*     */       }
/*     */     };
/*     */   }
/*     */   
/*     */   public <T> Parser<T> guard(final Parser<T> p) {
/* 141 */     new Parser()
/*     */     {
/*     */       public Parsers.ParseResult<T> apply(Reader input) {
/* 144 */         Parsers.ParseResult<T> pr = p.apply(input);
/* 145 */         if (pr.successful()) {
/* 146 */           return new Parsers.Success(pr.get(), input);
/*     */         }
/* 148 */         return new Parsers.Failure("??guard??", input);
/*     */       }
/*     */     };
/*     */   }
/*     */   
/*     */   public <T> Parser<T> not(final Parser<T> p) {
/* 154 */     new Parser()
/*     */     {
/*     */       public Parsers.ParseResult<T> apply(Reader input) {
/* 157 */         Parsers.ParseResult<T> pr = p.apply(input);
/* 158 */         if (pr.successful()) {
/* 159 */           return new Parsers.Failure("??not??", input);
/*     */         }
/* 161 */         return new Parsers.Success(pr.get(), input);
/*     */       }
/*     */     };
/*     */   }
/*     */   
/*     */   public <T> Parser<Object[]> opt(final Parser<T> p) {
/* 167 */     new Parser()
/*     */     {
/*     */       public Parsers.ParseResult<Object[]> apply(Reader input) {
/* 170 */         Parsers.ParseResult<T> pr = p.apply(input);
/* 171 */         if (pr.successful()) {
/* 172 */           return new Parsers.Success(new Object[] { pr.get() }, pr.next());
/*     */         }
/* 174 */         return new Parsers.Success(new Object[] { null }, input);
/*     */       }
/*     */     };
/*     */   }
/*     */   
/*     */   public <T> Parser<List<T>> rep(final Parser<T> p) {
/* 180 */     new Parser()
/*     */     {
/*     */       public Parsers.ParseResult<List<T>> apply(Reader input) {
/* 183 */         List<T> list = new ArrayList();
/*     */         Parsers.ParseResult<T> pr;
/* 185 */         while ((pr = p.apply(input)).successful()) {
/* 186 */           list.add(pr.get());
/* 187 */           input = pr.next();
/*     */         }
/* 189 */         return new Parsers.Success(list, input);
/*     */       }
/*     */     };
/*     */   }
/*     */   
/*     */   public <T> Parser<List<T>> repSep(final Parser<T> rep, final Parser<T> sep) {
/* 195 */     new Parser()
/*     */     {
/*     */       public Parsers.ParseResult<List<T>> apply(Reader input) {
/* 198 */         List<T> list = new ArrayList();
/*     */         Parsers.ParseResult<T> pr;
/* 200 */         if ((pr = rep.apply(input)).successful()) {
/* 201 */           list.add(pr.get());
/* 202 */           input = pr.next();
/* 203 */           while (((pr = sep.apply(input)).successful()) && ((pr = rep.apply(pr.next())).successful()))
/*     */           {
/* 205 */             list.add(pr.get());
/* 206 */             input = pr.next();
/*     */           }
/*     */         }
/* 209 */         return new Parsers.Success(list, input);
/*     */       }
/*     */     };
/*     */   }
/*     */   
/*     */   public <T> Parser<List<T>> rep1(Parser<T> p) {
/* 215 */     return rep1("??rep1??", p);
/*     */   }
/*     */   
/*     */   public <T> Parser<List<T>> rep1(final String errMsg, final Parser<T> p) {
/* 219 */     new Parser()
/*     */     {
/*     */       public Parsers.ParseResult<List<T>> apply(Reader input) {
/* 222 */         List<T> list = new ArrayList();
/*     */         Parsers.ParseResult<T> pr;
/* 224 */         while ((pr = p.apply(input)).successful()) {
/* 225 */           list.add(pr.get());
/* 226 */           input = pr.next();
/*     */         }
/* 228 */         if (list.isEmpty()) {
/* 229 */           return new Parsers.Failure(errMsg, input, (Parsers.NoSuccess)pr);
/*     */         }
/* 231 */         return new Parsers.Success(list, input);
/*     */       }
/*     */     };
/*     */   }
/*     */   
/*     */   public <T> Parser<List<T>> rep1Sep(Parser<T> rep, Parser<T> sep) {
/* 237 */     return rep1Sep("??rep1sep??", rep, sep);
/*     */   }
/*     */   
/*     */   public <T> Parser<List<T>> rep1Sep(final String errMsg, final Parser<T> rep, final Parser<T> sep) {
/* 241 */     new Parser()
/*     */     {
/*     */       public Parsers.ParseResult<List<T>> apply(Reader input) {
/* 244 */         List<T> list = new ArrayList();
/*     */         Parsers.ParseResult<T> pr;
/* 246 */         if ((pr = rep.apply(input)).successful()) {
/* 247 */           list.add(pr.get());
/* 248 */           input = pr.next();
/* 249 */           while (((pr = sep.apply(input)).successful()) && ((pr = rep.apply(pr.next())).successful()))
/*     */           {
/* 251 */             list.add(pr.get());
/* 252 */             input = pr.next();
/*     */           }
/*     */         }
/* 255 */         if (list.isEmpty()) {
/* 256 */           return new Parsers.Failure(errMsg, input, (Parsers.NoSuccess)pr);
/*     */         }
/* 258 */         return new Parsers.Success(list, input);
/*     */       }
/*     */     };
/*     */   }
/*     */   
/*     */   public Parser<? extends Object> sequence(int commitIndex, int dropMap, Parser<Object>... p) {
/* 264 */     return sequence("??sequence??", commitIndex, dropMap, p);
/*     */   }
/*     */   
/*     */   public Parser<? extends Object> sequence(final String errMsg, final int commitIndex, final int dropMap, final Parser<? extends Object>... p) {
/* 268 */     new Parser()
/*     */     {
/*     */       public Parsers.ParseResult<Object> apply(Reader input) {
/* 271 */         int bc = Integer.bitCount(dropMap);
/* 272 */         Object[] res = new Object[p.length - bc];
/* 273 */         Reader inputOriginal = input;
/* 274 */         Parsers.ParseResult<? extends Object> pr = null;
/* 275 */         int i = 0;int j = 0;
/* 276 */         for (int mask = 1; i < p.length; mask <<= 1) {
/* 277 */           pr = p[i].apply(input);
/* 278 */           if (!pr.successful()) {
/* 279 */             if (i <= commitIndex) break;
/* 280 */             Parsers.NoSuccess<? extends Object> ns = (Parsers.NoSuccess)pr;
/* 281 */             break;
/*     */           }
/*     */           
/* 284 */           if ((dropMap & mask) == 0) {
/* 285 */             res[(j++)] = pr.get();
/*     */           }
/* 287 */           input = pr.next();i++;
/*     */         }
/* 289 */         if ((pr != null) && (pr.successful())) {
/* 290 */           if (res.length == 1) {
/* 291 */             return new Parsers.Success(res[0], input);
/*     */           }
/* 293 */           return new Parsers.Success(res, input);
/*     */         }
/* 295 */         return i > commitIndex ? new Parsers.Error(errMsg, inputOriginal, (Parsers.NoSuccess)pr) : new Parsers.Failure(errMsg, inputOriginal, (Parsers.NoSuccess)pr);
/*     */       }
/*     */     };
/*     */   }
/*     */   
/*     */   public Parser<Object[]> choice(Parser<? extends Object>... p)
/*     */   {
/* 302 */     return choice("??choice??", p);
/*     */   }
/*     */   
/*     */   public Parser<Object[]> choice(final String errMsg, final Parser<? extends Object>... p) {
/* 306 */     new Parser()
/*     */     {
/*     */       public Parsers.ParseResult<Object[]> apply(Reader input) {
/* 309 */         Parsers.ParseResult<? extends Object> pr = null;
/*     */         
/* 311 */         for (int n = 0; n < p.length; n++) {
/* 312 */           pr = p[n].apply(input);
/* 313 */           if ((pr.successful()) || ((pr instanceof Parsers.Error)))
/*     */             break;
/*     */         }
/* 316 */         if ((pr != null) && (pr.successful())) {
/* 317 */           return new Parsers.Success(new Object[] { Integer.valueOf(n), pr.get() }, pr.next());
/*     */         }
/* 319 */         return new Parsers.Failure(errMsg, input, (Parsers.NoSuccess)pr);
/*     */       }
/*     */     };
/*     */   }
/*     */   
/* 324 */   public static NoSuccess lastNoSuccess = null;
/*     */   
/*     */   public String dumpResult(ParseResult pr) {
/* 327 */     if ((pr instanceof Success)) {
/* 328 */       Success s = (Success)pr;
/* 329 */       return String.format("Success(%d, %d): %s", new Object[] { Integer.valueOf(s.next().line()), Integer.valueOf(s.next().column()), s.get() }); }
/* 330 */     if ((pr instanceof NoSuccess)) {
/* 331 */       StringBuilder sb = new StringBuilder();
/* 332 */       NoSuccess f = (NoSuccess)pr;
/* 333 */       while (f != null) {
/* 334 */         Reader str = f.next;
/* 335 */         String sample = str.source().subSequence(str.offset(), Math.min(str.source().length(), str.offset() + 20)).toString();
/* 336 */         sb.append(String.format("%s(%d, %d): %s <%s>%n", new Object[] { f.getClass().getSimpleName(), Integer.valueOf(f.next().line()), Integer.valueOf(f.next().column()), f.msg, sample }));
/*     */         
/* 338 */         f = f.reason;
/*     */       }
/* 340 */       return sb.toString();
/*     */     }
/* 342 */     return String.format("Unknown: [%s] %s", new Object[] { pr.getClass().getName(), pr });
/*     */   }
/*     */ }


/* Location:              /home/yogesh/Downloads/VLL4J.jar!/net/java/vll/vll4j/combinator/Parsers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */