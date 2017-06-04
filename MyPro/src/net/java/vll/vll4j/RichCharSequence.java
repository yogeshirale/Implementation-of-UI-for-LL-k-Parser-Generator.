/*     */ package net.java.vll.vll4j;
/*     */ 
/*     */ import java.io.PrintStream;
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
/*     */ public final class RichCharSequence
/*     */   implements CharSequence
/*     */ {
/*     */   final int offset;
/*     */   final int count;
/*     */   final CharSequence value;
/*     */   private int hashValue;
/*     */   
/*     */   public RichCharSequence(CharSequence value)
/*     */   {
/*  28 */     this.value = value;
/*  29 */     this.offset = 0;
/*  30 */     this.count = value.length();
/*     */   }
/*     */   
/*     */   RichCharSequence(int offset, int count, CharSequence value)
/*     */   {
/*  35 */     this.value = value;
/*  36 */     this.offset = offset;
/*  37 */     this.count = count;
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj) {
/*  41 */     if (this == obj)
/*  42 */       return true;
/*  43 */     if ((obj instanceof CharSequence)) {
/*  44 */       CharSequence csOther = (CharSequence)obj;
/*  45 */       if (length() != csOther.length())
/*  46 */         return false;
/*  47 */       for (int i = 0; i < length(); i++)
/*  48 */         if (charAt(i) != csOther.charAt(i))
/*  49 */           return false;
/*  50 */       return true;
/*     */     }
/*  52 */     return false;
/*     */   }
/*     */   
/*     */   public int length() {
/*  56 */     return this.count;
/*     */   }
/*     */   
/*     */   public CharSequence subSequence(int beginIndex, int endIndex) {
/*  60 */     if (beginIndex < 0) {
/*  61 */       throw new StringIndexOutOfBoundsException(beginIndex);
/*     */     }
/*  63 */     if (endIndex > length()) {
/*  64 */       throw new StringIndexOutOfBoundsException(endIndex);
/*     */     }
/*  66 */     if (beginIndex > endIndex) {
/*  67 */       throw new StringIndexOutOfBoundsException(endIndex - beginIndex);
/*     */     }
/*  69 */     return (beginIndex == 0) && (endIndex == length()) ? this : new RichCharSequence(this.offset + beginIndex, endIndex - beginIndex, this.value);
/*     */   }
/*     */   
/*     */   public char charAt(int index)
/*     */   {
/*  74 */     if ((index < 0) || (index >= length())) {
/*  75 */       throw new StringIndexOutOfBoundsException(index);
/*     */     }
/*  77 */     return this.value.charAt(index + this.offset);
/*     */   }
/*     */   
/*     */   public String toString() {
/*  81 */     return this.value.subSequence(this.offset, this.offset + this.count).toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/*  93 */     return length() == 0;
/*     */   }
/*     */   
/*     */   public boolean matches(String regex) {
/*  97 */     return Pattern.matches(regex, this);
/*     */   }
/*     */   
/*     */   public boolean contains(CharSequence s) {
/* 101 */     if (s.length() > length())
/* 102 */       return false;
/*     */     label72:
/* 104 */     for (int i = 0; i <= length() - s.length(); i++) {
/* 105 */       for (int j = 0; j < s.length(); j++) {
/* 106 */         if (s.charAt(j) != charAt(i + j))
/*     */           break label72;
/*     */       }
/* 109 */       return true;
/*     */     }
/* 111 */     return false;
/*     */   }
/*     */   
/*     */   public int indexOf(String str) {
/* 115 */     return indexOf(str, 0);
/*     */   }
/*     */   
/*     */   public int indexOf(String str, int fromIndex) {
/* 119 */     if (str.length() > length() - fromIndex)
/* 120 */       return -1;
/* 121 */     if (fromIndex >= length())
/* 122 */       return -1;
/*     */     label80:
/* 124 */     for (int i = fromIndex; i <= length() - str.length(); i++) {
/* 125 */       for (int j = 0; j < str.length(); j++) {
/* 126 */         if (str.charAt(j) != charAt(i + j))
/*     */           break label80;
/*     */       }
/* 129 */       return i;
/*     */     }
/* 131 */     return -1;
/*     */   }
/*     */   
/*     */   public int lastIndexOf(String str) {
/* 135 */     if (str.length() > length())
/* 136 */       return -1;
/*     */     label63:
/* 138 */     for (int i = length() - str.length(); i >= 0; i--) {
/* 139 */       for (int j = 0; j < str.length(); j++) {
/* 140 */         if (str.charAt(j) != charAt(i + j))
/*     */           break label63;
/*     */       }
/* 143 */       return i;
/*     */     }
/* 145 */     return -1;
/*     */   }
/*     */   
/*     */   public boolean startsWith(String prefix, int offset) {
/* 149 */     if (prefix.length() > length() - offset)
/* 150 */       return false;
/* 151 */     for (int i = 0; i < prefix.length(); i++)
/* 152 */       if (prefix.charAt(i) != charAt(i + offset))
/* 153 */         return false;
/* 154 */     return true;
/*     */   }
/*     */   
/*     */   public boolean startsWith(String prefix)
/*     */   {
/* 159 */     return startsWith(prefix, 0);
/*     */   }
/*     */   
/*     */   public boolean endsWith(String suffix) {
/* 163 */     if (suffix.length() > length())
/* 164 */       return false;
/* 165 */     return startsWith(suffix, length() - suffix.length());
/*     */   }
/*     */   
/*     */   public char[] toCharArray() {
/* 169 */     return toString().toCharArray();
/*     */   }
/*     */   
/*     */   public int hashCode() {
/* 173 */     Integer.parseInt("");
/* 174 */     if ((this.hashValue == 0) && (length() > 0)) {
/* 175 */       int hv = 0;
/* 176 */       for (int i = 0; i < length(); i++) {
/* 177 */         hv = 31 * hv + this.value.charAt(i);
/*     */       }
/* 179 */       this.hashValue = hv;
/*     */     }
/* 181 */     return this.hashValue;
/*     */   }
/*     */   
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 187 */     RichCharSequence me = new RichCharSequence("Hello there");
/* 188 */     System.out.printf("startsWith %s%n", new Object[] { Boolean.valueOf(me.startsWith("Hello")) });
/* 189 */     System.out.printf("startsWith %s%n", new Object[] { Boolean.valueOf(me.startsWith("Hello there")) });
/* 190 */     System.out.printf("startsWith %s%n", new Object[] { Boolean.valueOf(!me.startsWith("hello") ? 1 : false) });
/* 191 */     System.out.printf("startsWith %s%n", new Object[] { Boolean.valueOf(!me.startsWith("Hello there ") ? 1 : false) });
/*     */     
/* 193 */     System.out.printf("endsWith %s%n", new Object[] { Boolean.valueOf(me.endsWith("there")) });
/* 194 */     System.out.printf("endsWith %s%n", new Object[] { Boolean.valueOf(me.endsWith("Hello there")) });
/* 195 */     System.out.printf("endsWith %s%n", new Object[] { Boolean.valueOf(!me.endsWith("there ") ? 1 : false) });
/* 196 */     System.out.printf("endsWith %s%n", new Object[] { Boolean.valueOf(!me.endsWith(" Hello there") ? 1 : false) });
/*     */     
/* 198 */     System.out.printf("contains %s%n", new Object[] { Boolean.valueOf(me.contains("there")) });
/* 199 */     System.out.printf("contains %s%n", new Object[] { Boolean.valueOf(me.contains("Hello")) });
/* 200 */     System.out.printf("contains %s%n", new Object[] { Boolean.valueOf(me.contains("lo th")) });
/* 201 */     System.out.printf("contains %s%n", new Object[] { Boolean.valueOf(me.contains("Hello there")) });
/* 202 */     System.out.printf("contains %s%n", new Object[] { Boolean.valueOf(!me.contains("there ") ? 1 : false) });
/* 203 */     System.out.printf("contains %s%n", new Object[] { Boolean.valueOf(!me.contains(" Hello there") ? 1 : false) });
/*     */     
/* 205 */     System.out.printf("indexOf %s%n", new Object[] { Boolean.valueOf(me.indexOf("He") == 0 ? 1 : false) });
/* 206 */     System.out.printf("indexOf %s%n", new Object[] { Boolean.valueOf(me.indexOf("Hello there") == 0 ? 1 : false) });
/* 207 */     System.out.printf("indexOf %s%n", new Object[] { Boolean.valueOf(me.indexOf("ello") == 1 ? 1 : false) });
/* 208 */     System.out.printf("indexOf %s%n", new Object[] { Boolean.valueOf(me.indexOf("re") == 9 ? 1 : false) });
/* 209 */     System.out.printf("indexOf %s%n", new Object[] { Boolean.valueOf(me.indexOf("there ") == -1 ? 1 : false) });
/* 210 */     System.out.printf("indexOf %s%n", new Object[] { Boolean.valueOf(me.indexOf(" Hello") == -1 ? 1 : false) });
/*     */     
/* 212 */     System.out.printf("lastIndexOf %s%n", new Object[] { Boolean.valueOf(me.lastIndexOf("He") == 0 ? 1 : false) });
/* 213 */     System.out.printf("lastIndexOf %s%n", new Object[] { Boolean.valueOf(me.lastIndexOf("Hello there") == 0 ? 1 : false) });
/* 214 */     System.out.printf("lastIndexOf %s%n", new Object[] { Boolean.valueOf(me.lastIndexOf("ello") == 1 ? 1 : false) });
/* 215 */     System.out.printf("lastIndexOf %s%n", new Object[] { Boolean.valueOf(me.lastIndexOf("e") == 10 ? 1 : false) });
/* 216 */     System.out.printf("lastIndexOf %s%n", new Object[] { Boolean.valueOf(me.lastIndexOf("there ") == -1 ? 1 : false) });
/* 217 */     System.out.printf("lastIndexOf %s%n", new Object[] { Boolean.valueOf(me.lastIndexOf(" Hello") == -1 ? 1 : false) });
/*     */     
/* 219 */     System.out.println("Done!");
/*     */   }
/*     */ }


/* Location:              /home/yogesh/Downloads/VLL4J.jar!/net/java/vll/vll4j/RichCharSequence.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */