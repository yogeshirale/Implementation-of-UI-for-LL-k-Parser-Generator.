/*     */ package net.java.vll.vll4j.combinator;
/*     */ 
/*     */ import java.util.HashMap;
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
/*     */ 
/*     */ 
/*     */ public class Utils
/*     */ {
/*  28 */   private static HashMap<String, Character> decodeMap = new HashMap();
/*     */   
/*  30 */   static { String[] keys = { "&#xA;", "&#x9;", "&#xD;", "&amp;", "&lt;", "&gt;", "&quot;", "&apos;" };
/*  31 */     char[] vals = { '\n', '\t', '\r', '&', '<', '>', '"', '\'' };
/*  32 */     for (int i = 0; i < keys.length; i++)
/*  33 */       decodeMap.put(keys[i], Character.valueOf(vals[i]));
/*     */   }
/*     */   
/*     */   public static String encode4xml(String s) {
/*  37 */     StringBuilder sb = new StringBuilder();
/*  38 */     for (int i = 0; i < s.length(); i++) {
/*  39 */       char ch = s.charAt(i);
/*  40 */       switch (ch) {
/*  41 */       case '\n':  sb.append("&#xA;"); break;
/*  42 */       case '\t':  sb.append("&#x9;"); break;
/*  43 */       case '\r':  sb.append("&#xD;"); break;
/*  44 */       case '&':  sb.append("&amp;"); break;
/*  45 */       case '<':  sb.append("&lt;"); break;
/*  46 */       case '>':  sb.append("&gt;"); break;
/*  47 */       case '"':  sb.append("&quot;"); break;
/*  48 */       case '\'':  sb.append("&apos;"); break;
/*  49 */       default:  sb.append(ch);
/*     */       }
/*     */     }
/*  52 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public static String decode4xml(String s)
/*     */   {
/*  57 */     StringBuilder sb = new StringBuilder();
/*  58 */     StringBuilder esc = new StringBuilder();
/*  59 */     boolean escaping = false;
/*  60 */     for (int i = 0; i < s.length(); i++) {
/*  61 */       char ch = s.charAt(i);
/*  62 */       if (escaping) {
/*  63 */         esc.append(ch);
/*  64 */         if (ch == ';') {
/*  65 */           escaping = false;
/*  66 */           sb.append(decodeMap.get(esc.toString()));
/*  67 */           esc.setLength(0);
/*     */         }
/*     */       }
/*  70 */       else if (ch == '&') {
/*  71 */         escaping = true;
/*  72 */         esc.append(ch);
/*     */       } else {
/*  74 */         sb.append(ch);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*  79 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public static String escapeMetachars(String s) {
/*  83 */     StringBuilder sb = new StringBuilder();
/*  84 */     for (int i = 0; i < s.length(); i++) {
/*  85 */       char ch = s.charAt(i);
/*  86 */       switch (ch) {
/*     */       case '$': case '(': case ')': case '*': case '+': case ',': case '-': case '.': 
/*     */       case '?': case '[': case '\\': case ']': case '^': case '{': case '|': case '}': 
/*  89 */         sb.append('\\').append(ch);
/*  90 */         break;
/*  91 */       default:  sb.append(ch);
/*     */       }
/*     */     }
/*  94 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public static String unEscape(String s) {
/*  98 */     if (!s.contains("\\")) {
/*  99 */       return s;
/*     */     }
/* 101 */     StringBuilder sb = new StringBuilder();StringBuilder octals = new StringBuilder();
/* 102 */     boolean escaping = false;
/* 103 */     for (int i = 0; i < s.length(); i++) {
/* 104 */       char ch = s.charAt(i);
/* 105 */       if (escaping) {
/* 106 */         if ((ch >= '0') && (ch <= '7')) {
/* 107 */           octals.append(ch);
/*     */         } else {
/* 109 */           if (octals.length() > 0) {
/* 110 */             sb.append((char)(Integer.parseInt(octals.toString(), 8) & 0xFF));
/* 111 */             octals.setLength(0);
/*     */           }
/* 113 */           escaping = false;
/* 114 */           switch (ch) {
/* 115 */           case 'b':  sb.append('\b'); break;
/* 116 */           case 'f':  sb.append('\f'); break;
/* 117 */           case 'n':  sb.append('\n'); break;
/* 118 */           case 'r':  sb.append('\r'); break;
/* 119 */           case 't':  sb.append('\t'); break;
/* 120 */           case '\\':  sb.append('\\'); break;
/* 121 */           case '\'':  sb.append('\''); break;
/* 122 */           case '"':  sb.append('"'); break;
/* 123 */           default:  throw new IllegalArgumentException(String.format("Bad escape at offset %d -> %s", new Object[] { Integer.valueOf(i), s }));
/*     */           }
/*     */           
/*     */         }
/*     */       }
/* 128 */       else if (ch == '\\') {
/* 129 */         escaping = true;
/*     */       } else {
/* 131 */         sb.append(ch);
/*     */       }
/*     */     }
/* 134 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public static String reEscape(String s)
/*     */   {
/* 139 */     StringBuilder sb = new StringBuilder();
/* 140 */     for (int i = 0; i < s.length(); i++) {
/* 141 */       char ch = s.charAt(i);
/* 142 */       switch (ch) {
/* 143 */       case '\b':  sb.append("\\b"); break;
/* 144 */       case '\f':  sb.append("\\f"); break;
/* 145 */       case '\n':  sb.append("\\n"); break;
/* 146 */       case '\r':  sb.append("\\r"); break;
/* 147 */       case '\t':  sb.append("\\t"); break;
/* 148 */       case '\\':  sb.append("\\\\"); break;
/* 149 */       default:  sb.append(ch);
/*     */       }
/*     */     }
/* 152 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public static void dumpValue(Object v, StringBuilder sb, boolean structured, int level) {
/* 156 */     if ((v instanceof List)) {
/* 157 */       List<Object> lst = (List)v;
/* 158 */       for (int i = 0; i < level; i++) sb.append("|  ");
/* 159 */       if (lst.isEmpty()) {
/* 160 */         sb.append("List()");
/*     */       } else {
/* 162 */         sb.append(structured ? "List(\n" : "List(");
/* 163 */         boolean first = true;
/* 164 */         for (Object e : lst) {
/* 165 */           if (first) {
/* 166 */             first = false;
/*     */           } else {
/* 168 */             sb.append(structured ? ",\n" : ", ");
/*     */           }
/* 170 */           dumpValue(e, sb, structured, structured ? level + 1 : level);
/*     */         }
/* 172 */         if (structured) sb.append("\n");
/* 173 */         for (int i = 0; i < level; i++) sb.append("|  ");
/* 174 */         sb.append(")");
/*     */       }
/* 176 */     } else if ((v instanceof Object[])) {
/* 177 */       Object[] arr = (Object[])v;
/* 178 */       for (int i = 0; i < level; i++) sb.append("|  ");
/* 179 */       if (arr.length == 0) {
/* 180 */         sb.append("Array()");
/*     */       } else {
/* 182 */         sb.append(structured ? "Array(\n" : "Array(");
/* 183 */         boolean first = true;
/* 184 */         for (Object e : arr) {
/* 185 */           if (first) {
/* 186 */             first = false;
/*     */           } else {
/* 188 */             sb.append(structured ? ",\n" : ", ");
/*     */           }
/* 190 */           dumpValue(e, sb, structured, structured ? level + 1 : level);
/*     */         }
/* 192 */         if (structured) sb.append("\n");
/* 193 */         for (int i = 0; i < level; i++) sb.append("|  ");
/* 194 */         sb.append(")");
/*     */       }
/*     */     } else {
/* 197 */       for (int i = 0; i < level; i++) sb.append("|  ");
/* 198 */       sb.append(v == null ? "null" : v.toString());
/*     */     }
/*     */   }
/*     */   
/*     */   public static String dumpValue(Object v, boolean structured) {
/* 203 */     StringBuilder sb = new StringBuilder();
/* 204 */     dumpValue(v, sb, structured, 0);
/* 205 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              /home/yogesh/Downloads/VLL4J.jar!/net/java/vll/vll4j/combinator/Utils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */