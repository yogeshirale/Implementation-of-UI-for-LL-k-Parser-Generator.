/*     */ package net.java.vll.vll4j.api;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Map;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import net.java.vll.vll4j.combinator.CharSequenceReader;
/*     */ import net.java.vll.vll4j.combinator.PackratParsers;
import net.java.vll.vll4j.combinator.Parsers;
/*     */ import net.java.vll.vll4j.combinator.Parsers.ParseResult;
/*     */ import net.java.vll.vll4j.combinator.Parsers.Parser;
/*     */ import net.java.vll.vll4j.combinator.Reader;
/*     */ import net.java.vll.vll4j.combinator.Utils;
/*     */ import net.java.vll.vll4j.gui.ReaderFile;
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
/*     */ public class Vll4j
/*     */   extends PackratParsers
/*     */ {
/*     */   public static Vll4j fromStream(InputStream is)
/*     */     throws ParserConfigurationException, SAXException, IOException
/*     */   {
/*  35 */     Vll4j vll4j = new Vll4j();
/*  36 */     vll4j.initForest(is);
/*  37 */     return vll4j;
/*     */   }
/*     */   
/*     */   public static Vll4j fromString(String inString) throws ParserConfigurationException, SAXException, IOException
/*     */   {
/*  42 */     ByteArrayInputStream bais = new ByteArrayInputStream(inString.getBytes());
/*  43 */     Vll4j vll4j = new Vll4j();
/*  44 */     vll4j.initForest(bais);
/*  45 */     return vll4j;
/*     */   }
/*     */   
/*     */   public static Vll4j fromFile(File inFile) throws ParserConfigurationException, SAXException, IOException
/*     */   {
/*  50 */     FileInputStream fis = new FileInputStream(inFile);
/*  51 */     Vll4j vll4j = new Vll4j();
/*  52 */     vll4j.initForest(fis);
/*  53 */     return vll4j;
/*     */   }
/*     */   
/*     */   public <T> Parsers.Parser<T> getParserFor(String ruleName) throws ParserConfigurationException, SAXException, IOException
/*     */   {
/*  58 */     if (!this.forest.ruleBank.containsKey(ruleName))
/*  59 */       throw new IllegalArgumentException(String.format("unknown rule: %s", new Object[] { ruleName }));
/*  60 */     reset();
/*  61 */     VisitorParserGeneration vpg = new VisitorParserGeneration(this.forest, this, false);
/*  62 */     NodeBase top = (NodeBase)this.forest.ruleBank.get(ruleName);
/*  63 */     return (Parsers.Parser)top.accept(vpg);
/*     */   }
/*     */   
/*     */   public <T> Parsers.ParseResult<T> parseAll(Parsers.Parser<T> p, CharSequence cs)
/*     */   {
/*  68 */     this.forest.bindings.put("vllSource", cs);
/*  69 */     return phrase(p).apply(new CharSequenceReader(cs));
/*     */   }
/*     */   
/*     */   public <T> Parsers.ParseResult<T> parseAll(Parsers.Parser<T> p, Reader rdr)
/*     */   {
/*  74 */     this.forest.bindings.put("vllSource", rdr.source());
/*  75 */     return phrase(p).apply(rdr);
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {
/*  79 */     if ((args.length != 3) && (args.length != 2)) {
/*  80 */       System.err.println("usage: java vll4j.Vll4j grammar [parser-name] file/data");
/*  81 */       System.exit(1);
/*     */     }
/*     */     try {
/*  84 */       FileInputStream fis = new FileInputStream(args[0]);
/*  85 */       Vll4j vll4j = fromStream(fis);
/*  86 */       Parsers.Parser<Object> p = vll4j.getParserFor(args.length == 2 ? "Main" : args[1]);
/*  87 */       File dataFile = new File(args[(args.length - 1)]);
/*  88 */       Parsers.ParseResult pr = dataFile.exists() ? vll4j.parseAll(p, new ReaderFile(dataFile, false)) : vll4j.parseAll(p, args[(args.length - 1)]);
/*     */       
/*  90 */       if (pr.successful()) {
/*  91 */         System.out.println(Utils.dumpValue(pr.get(), true));
/*     */       } else
/*  93 */         System.out.println(vll4j.dumpResult(pr));
/*     */     } catch (Exception e) {
/*  95 */       System.err.println(e);
/*     */     }
/*     */   }
/*     */   
/*     */   private void initForest(InputStream fis) throws ParserConfigurationException, SAXException, IOException
/*     */   {
/* 101 */     this.forest.openInputStream(fis, false);
/* 102 */     this.commentSpecRegex = this.forest.comment;
/* 103 */     this.whiteSpaceRegex = this.forest.whiteSpace;
/* 104 */     resetWhitespace();
/*     */   }
/*     */   
/* 107 */   private Forest forest = new Forest();
/*     */ }


/* Location:              /home/yogesh/Downloads/VLL4J.jar!/net/java/vll/vll4j/api/Vll4j.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */