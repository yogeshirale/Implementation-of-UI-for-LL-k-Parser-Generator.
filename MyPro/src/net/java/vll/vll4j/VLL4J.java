/*    */ package net.java.vll.vll4j;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.PrintStream;
/*    */ import net.java.vll.vll4j.api.Vll4j;
import net.java.vll.vll4j.combinator.Parsers;
/*    */ import net.java.vll.vll4j.combinator.Parsers.ParseResult;
/*    */ import net.java.vll.vll4j.combinator.Parsers.Parser;
/*    */ import net.java.vll.vll4j.combinator.Utils;
/*    */ import net.java.vll.vll4j.gui.ReaderFile;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VLL4J
/*    */ {
/*    */   public static void main(String[] args)
/*    */   {
/* 34 */     if ((args.length > 4) || (args.length < 2)) {
/* 35 */       System.err.println("usage: java VLL4J [-a] grammar_file [parser_name] data/file");
/* 36 */       System.err.println("\t'-a' - do not print AST (default: print AST)");
/* 37 */       System.err.println("\t'grammar_file' - the *.vll file from VisualLangLab GUI");
/* 38 */       System.err.println("\t'parser_name' - the top-level parser name (default: 'Main')");
/* 39 */       System.err.println("\t'data/file' - literal data or file-name for parser input");
/* 40 */       System.exit(1);
/*    */     }
/* 42 */     boolean noAst = args[0].equals("-a");
/*    */     try {
/* 44 */       FileInputStream fis = new FileInputStream(noAst ? args[1] : args[0]);
/* 45 */       Vll4j vll4j = Vll4j.fromStream(fis);
/* 46 */       Parsers.Parser p = vll4j.getParserFor(args.length == 2 ? "Main" : noAst ? args[2] : args.length == 3 ? "Main" : args[1]);
/*    */       
/* 48 */       File dataFile = new File(args[(args.length - 1)]);
/* 49 */       Parsers.ParseResult pr = dataFile.exists() ? vll4j.parseAll(p, new ReaderFile(dataFile, false)) : vll4j.parseAll(p, args[(args.length - 1)]);
/*    */       
/* 51 */       if (pr.successful()) {
/* 52 */         System.out.println(Utils.dumpValue(pr.get(), true));
/*    */       } else
/* 54 */         System.out.println(vll4j.dumpResult(pr));
/*    */     } catch (Exception e) {
/* 56 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ }


/* Location:              /home/yogesh/Downloads/VLL4J.jar!/net/java/vll/vll4j/VLL4J.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */