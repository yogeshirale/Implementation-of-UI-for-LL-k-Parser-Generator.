/*     */ package net.java.vll.vll4j.gui;
/*     */ 
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.swing.AbstractAction;
/*     */ import javax.swing.Action;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JOptionPane;
/*     */ import net.java.vll.vll4j.api.Forest;
/*     */ import net.java.vll.vll4j.api.NodeBase;
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
/*     */ public class ManagerTokens
/*     */ {
/*     */   ManagerTokens(Vll4jGui theGui)
/*     */   {
/*  37 */     this.gui = theGui;
/*     */   }
/*     */   
/*     */   private String[] getTokenInfo(boolean isRegex) {
/*  41 */     String title = String.format("New %s", new Object[] { isRegex ? "regex" : "literal" });
/*  42 */     Pattern inPattern = Pattern.compile(isRegex ? "([^\\s]+)|([a-zA-Z][a-zA-Z0-9$_]*(?::\\-?\\d+)?)\\s+(.+)" : "([^\\s]+)|([a-zA-Z][a-zA-Z0-9$_]*)\\s+(.+)");
/*     */     
/*  44 */     String input = JOptionPane.showInputDialog(this.gui, "Enter: name & spaces (optional), pattern", title, 3);
/*     */     
/*  46 */     if ((input == null) || (input.trim().length() == 0))
/*  47 */       return null;
/*  48 */     Matcher m = inPattern.matcher(input.trim());
/*  49 */     if (!m.matches()) {
/*  50 */       JOptionPane.showMessageDialog(this.gui, "Bad format\nExpected name, space(s), pattern", "WARNING - " + title, 2);
/*     */       
/*  52 */       return null;
/*     */     }
/*  54 */     String tokenName = m.group(1) == null ? m.group(2) : String.format("\"%s\"", new Object[] { m.group(0) });
/*  55 */     if (this.gui.theForest.tokenBank.containsKey(tokenName)) {
/*  56 */       JOptionPane.showMessageDialog(this.gui, "Name conflict\nA token with this name already exists", "WARNING - " + title, 2);
/*     */       
/*  58 */       return null;
/*     */     }
/*  60 */     String tokenPattern = m.group(1) == null ? m.group(3) : m.group(0);
/*     */     try {
/*  62 */       String reg = Utils.unEscape(tokenPattern);
/*  63 */       if (isRegex)
/*  64 */         Pattern.compile(reg);
/*     */     } catch (Exception ex) {
/*  66 */       JOptionPane.showMessageDialog(this.gui, ex.toString(), "WARNING - New " + (isRegex ? "regex" : "literal"), 2);
/*     */       
/*  68 */       return null;
/*     */     }
/*  70 */     boolean ok = true;
/*  71 */     for (Map.Entry<String, String> e : this.gui.theForest.tokenBank.entrySet()) {
/*  72 */       if ((((String)e.getValue()).substring(1).equals(tokenPattern)) && (((String)e.getKey()).endsWith("_") == tokenName.endsWith("_")))
/*     */       {
/*  74 */         JOptionPane.showMessageDialog(this.gui, String.format("Pattern conflict\nToken '%s' uses the same pattern", new Object[] { e.getKey() }), "WARNING - " + title, 2);
/*     */         
/*  76 */         ok = false;
/*     */       }
/*     */     }
/*  79 */     if ((isRegex) && (!tokenPattern.equals("\\\\z")) && ("".matches(Utils.unEscape(tokenPattern)))) {
/*  80 */       JOptionPane.showMessageDialog(this.gui, "Bad pattern\nPattern matches empty string", "WARNING - " + title, 2);
/*     */       
/*  82 */       return null;
/*     */     }
/*  84 */     if ((isRegex) && (tokenPattern.equals("\\\\z")) && (!tokenName.endsWith("_"))) {
/*  85 */       JOptionPane.showMessageDialog(this.gui, "Bad name\nEOF must be local token", "WARNING - " + title, 2);
/*     */       
/*  87 */       return null;
/*     */     }
/*  89 */     String pattern = (isRegex ? "R" : "L") + tokenPattern;
/*  90 */     return ok ? new String[] { tokenName, pattern } : null;
/*     */   }
/*     */   
/*     */   private String[] findTokenInRules(String token) {
/*  94 */     VisitorTokenSearch v = new VisitorTokenSearch(token);
/*  95 */     for (NodeBase n : this.gui.theForest.ruleBank.values()) {
/*  96 */       n.accept(v);
/*     */     }
/*  98 */     return (String[])v.ruleSet.toArray(new String[v.ruleSet.size()]);
/*     */   }
/*     */   
/* 101 */   Action newLiteralAction = new AbstractAction("New literal", Resources.newLiteral)
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 104 */       String[] info = ManagerTokens.this.getTokenInfo(false);
/* 105 */       if (info == null)
/* 106 */         return;
/* 107 */       ManagerTokens.this.gui.theForest.tokenBank.put(info[0], info[1]);
/*     */     }
/*     */   };
/*     */   
/* 111 */   Action newRegexAction = new AbstractAction("New regex", Resources.newRegex)
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 114 */       String[] info = ManagerTokens.this.getTokenInfo(true);
/* 115 */       if (info == null)
/* 116 */         return;
/* 117 */       ManagerTokens.this.gui.theForest.tokenBank.put(info[0], info[1]);
/*     */     }
/*     */   };
/*     */   
/*     */   void editToken(String token) {
/* 122 */     String value = (String)this.gui.theForest.tokenBank.get(token);
/* 123 */     boolean isRegex = value.charAt(0) == 'R';
/* 124 */     value = value.substring(1);
/* 125 */     String title = String.format(isRegex ? "Edit regex '%s'" : "Edit literal '%s'", new Object[] { token });
/* 126 */     String newValue = (String)JOptionPane.showInputDialog(this.gui, "Edit pattern", title, 3, null, null, value);
/*     */     
/* 128 */     if ((newValue == null) || (newValue.equals(value))) {
/* 129 */       return;
/*     */     }
/* 131 */     if (isRegex) {
/*     */       try {
/* 133 */         Pattern.compile(Utils.unEscape(newValue));
/*     */       } catch (Exception ex) {
/* 135 */         JOptionPane.showMessageDialog(this.gui, ex.getMessage(), title, 2);
/* 136 */         return;
/*     */       }
/*     */     }
/* 139 */     String pattern = (isRegex ? "R" : "L") + newValue;
/* 140 */     for (Map.Entry<String, String> me : this.gui.theForest.tokenBank.entrySet()) {
/* 141 */       if (((String)me.getValue()).equals(pattern)) {
/* 142 */         JOptionPane.showMessageDialog(this.gui, String.format("Token %s has the same pattern", new Object[] { me.getKey() }), title, 2);
/* 143 */         return;
/*     */       }
/*     */     }
/* 146 */     this.gui.theForest.tokenBank.put(token, pattern);
/*     */   }
/*     */   
/* 149 */   Action editTokenAction = new AbstractAction("Edit token", Resources.edit16)
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 152 */       String[] names = (String[])ManagerTokens.this.gui.theForest.tokenBank.keySet().toArray(new String[ManagerTokens.this.gui.theForest.tokenBank.size()]);
/* 153 */       List<String> editableNames = new ArrayList();
/* 154 */       for (String key : names) {
/* 155 */         String val = (String)ManagerTokens.this.gui.theForest.tokenBank.get(key);
/*     */         
/* 157 */         if ((val.length() != key.length() - 1) || (!val.substring(1).equals(key.substring(1, key.length() - 1))))
/*     */         {
/*     */ 
/* 160 */           editableNames.add(key); }
/*     */       }
/* 162 */       if (editableNames.size() == 0) {
/* 163 */         JOptionPane.showMessageDialog(ManagerTokens.this.gui, "No editable tokens defined yet", "WARNING - Edit token", 2);
/* 164 */         return;
/*     */       }
/* 166 */       names = (String[])editableNames.toArray(new String[editableNames.size()]);
/* 167 */       String token = (String)JOptionPane.showInputDialog(ManagerTokens.this.gui, "Select token to edit", "Edit token", 3, null, names, names[0]);
/*     */       
/* 169 */       if (token == null)
/* 170 */         return;
/* 171 */       ManagerTokens.this.editToken(token);
/*     */     }
/*     */   };
/*     */   
/* 175 */   Action findTokenAction = new AbstractAction("Find token", Resources.replace16)
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 178 */       Object[] names = ManagerTokens.this.gui.theForest.tokenBank.keySet().toArray();
/* 179 */       if (names.length == 0) {
/* 180 */         JOptionPane.showMessageDialog(ManagerTokens.this.gui, "No tokens defined yet", "WARNING - Find token", 2);
/* 181 */         return;
/*     */       }
/* 183 */       String tokenName = (String)JOptionPane.showInputDialog(ManagerTokens.this.gui, "Select token to find", "Find token", 3, null, names, names[0]);
/*     */       
/* 185 */       if (tokenName == null)
/* 186 */         return;
/* 187 */       String[] rules = ManagerTokens.this.findTokenInRules(tokenName);
/* 188 */       if (rules.length == 0) {
/* 189 */         JOptionPane.showMessageDialog(ManagerTokens.this.gui, String.format("Token '%s' is not referred in any rule", new Object[] { tokenName }), "WARNING - Find token", 2);
/* 190 */         return;
/*     */       }
/* 192 */       String rule = (String)JOptionPane.showInputDialog(ManagerTokens.this.gui, String.format("Token '%s' is referred in rules listed below\nClick OK to display selected rule", new Object[] { tokenName }), "Find token", 3, null, rules, rules[0]);
/*     */       
/*     */ 
/* 195 */       if (rule != null) {
/* 196 */         ManagerTokens.this.gui.theRuleManager.theComboBox.setSelectedItem(rule);
/*     */       }
/*     */     }
/*     */   };
/* 200 */   Action deleteTokenAction = new AbstractAction("Delete token", Resources.delete16)
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 203 */       Object[] names = ManagerTokens.this.gui.theForest.tokenBank.keySet().toArray();
/* 204 */       if (names.length == 0) {
/* 205 */         JOptionPane.showMessageDialog(ManagerTokens.this.gui, "No tokens defined yet", "WARNING - Delete token", 2);
/* 206 */         return;
/*     */       }
/* 208 */       String tokenToDelete = (String)JOptionPane.showInputDialog(ManagerTokens.this.gui, "Select token to delete", "Delete token", 3, null, names, names[0]);
/*     */       
/* 210 */       if (tokenToDelete == null)
/* 211 */         return;
/* 212 */       String[] rules = ManagerTokens.this.findTokenInRules(tokenToDelete);
/* 213 */       if (rules.length != 0) {
/* 214 */         String rule = (String)JOptionPane.showInputDialog(ManagerTokens.this.gui, String.format("Can't delete token '%s'\nUsed in rules listed below\nClick OK to display a rule", new Object[] { tokenToDelete }), "WARNING - Delete token", 2, null, rules, rules[0]);
/*     */         
/*     */ 
/*     */ 
/* 218 */         if (rule != null)
/* 219 */           ManagerTokens.this.gui.theRuleManager.theComboBox.setSelectedItem(rule);
/* 220 */         return;
/*     */       }
/* 222 */       int opt = JOptionPane.showConfirmDialog(ManagerTokens.this.gui, String.format("Delete '%s' ?", new Object[] { tokenToDelete }), "Delete token", 2);
/* 223 */       if (opt == 0) {
/* 224 */         ManagerTokens.this.gui.theForest.tokenBank.remove(tokenToDelete);
/*     */       }
/*     */     }
/*     */   };
/*     */   Vll4jGui gui;
/*     */ }


/* Location:              /home/yogesh/Downloads/VLL4J.jar!/net/java/vll/vll4j/gui/ManagerTokens.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */