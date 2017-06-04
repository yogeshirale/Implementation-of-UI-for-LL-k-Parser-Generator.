/*    */ package net.java.vll.vll4j.gui;
/*    */ 
/*    */ import javax.swing.ImageIcon;
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
/*    */ 
/*    */ public class Resources
/*    */ {
/* 26 */   static final ClassLoader cl = Resources.class.getClassLoader();///////////////////////////////////
/*    */   
/* 28 */   static ImageIcon getIcon(String name) { return new ImageIcon(cl.getResource("net/java/vll/vll4j/gui/resources/" + name)); }
/*    */   
/* 30 */   static ImageIcon alignLeft16 = getIcon("AlignLeft16.gif");
/* 31 */   static ImageIcon back16 = getIcon("Back16.gif");
/* 32 */   static ImageIcon choice = getIcon("Choice.gif");
/* 33 */   static ImageIcon clear16 = getIcon("Clear16.gif");
/* 34 */   static ImageIcon commitMark = getIcon("CommitMark.gif");
/* 35 */   static ImageIcon copy16 = getIcon("Copy16.gif");
/* 36 */   static ImageIcon cut16 = getIcon("Cut16.gif");
/* 37 */   static ImageIcon delete16 = getIcon("Delete16.gif");
/* 38 */   static ImageIcon edit16 = getIcon("Edit16.gif");
/* 39 */   static ImageIcon errorMark = getIcon("ErrorMark.gif");
/* 40 */   static ImageIcon export16 = getIcon("Export16.gif");
/* 41 */   static ImageIcon fastForward16 = getIcon("FastForward16.gif");
/* 42 */   static ImageIcon help16 = getIcon("Help16.gif");
/* 43 */   static ImageIcon home16 = getIcon("Home16.gif");
/* 44 */   static ImageIcon host16 = getIcon("Host16.gif");
/* 45 */   static ImageIcon icon = getIcon("Icon.gif");
/* 46 */   static ImageIcon import16 = getIcon("Import16.gif");
/* 47 */   static ImageIcon information16 = getIcon("Information16.gif");
/* 48 */   static ImageIcon literal = getIcon("Literal.gif");
/* 49 */   static ImageIcon literalLocal = getIcon("LiteralPrivate.gif");
/* 50 */   static ImageIcon new16 = getIcon("New16.gif");
/* 51 */   static ImageIcon newLiteral = getIcon("NewLiteral.gif");
/* 52 */   static ImageIcon newReference = getIcon("NewReference.gif");
/* 53 */   static ImageIcon newRegex = getIcon("NewRegex.gif");
/* 54 */   static ImageIcon open16 = getIcon("Open16.gif");
/* 55 */   static ImageIcon paste16 = getIcon("Paste16.gif");
/* 56 */   static ImageIcon play16 = getIcon("Play16.gif");
/* 57 */   static ImageIcon predicate = getIcon("SemPred.gif");
/* 58 */   static ImageIcon preferences16 = getIcon("Preferences16.gif");
/* 59 */   static ImageIcon reference = getIcon("Reference.gif");
/* 60 */   static ImageIcon refresh16 = getIcon("Refresh16.gif");
/* 61 */   static ImageIcon regex = getIcon("Regex.gif");
/* 62 */   static ImageIcon regexLocal = getIcon("RegexPrivate.gif");
/* 63 */   static ImageIcon repSep = getIcon("RepSep.gif");
/* 64 */   static ImageIcon replace16 = getIcon("Replace16.gif");
/* 65 */   static ImageIcon root = getIcon("Root.gif");
/* 66 */   static ImageIcon rule = getIcon("Rule.gif");
/* 67 */   static ImageIcon save16 = getIcon("Save16.gif");
/* 68 */   static ImageIcon saveAs16 = getIcon("SaveAs16.gif");
/* 69 */   static ImageIcon search16 = getIcon("Search16.gif");
/* 70 */   static ImageIcon semPred = getIcon("SemPred.gif");
/* 71 */   static ImageIcon sequence = getIcon("Sequence.gif");
/* 72 */   static ImageIcon server16 = getIcon("Server16.gif");
/* 73 */   static ImageIcon splashImage = getIcon("SplashImage.png");
/* 74 */   static ImageIcon stop16 = getIcon("Stop16.gif");
/* 75 */   static ImageIcon tipOfTheDay16 = getIcon("TipOfTheDay16.gif");
/* 76 */   static ImageIcon token = getIcon("Token.gif");
/* 77 */   static ImageIcon wildCard = getIcon("WildCard.gif");
/*    */ }


/* Location:              /home/yogesh/Downloads/VLL4J.jar!/net/java/vll/vll4j/gui/Resources.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */