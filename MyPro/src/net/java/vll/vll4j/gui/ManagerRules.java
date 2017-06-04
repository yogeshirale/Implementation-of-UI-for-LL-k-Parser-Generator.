/*     */ package net.java.vll.vll4j.gui;
/*     */ 
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.util.Map;
/*     */ import java.util.Stack;
/*     */ import java.util.TreeSet;
/*     */ import javax.swing.AbstractAction;
/*     */ import javax.swing.Action;
/*     */ import javax.swing.Icon;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JOptionPane;
/*     */ import net.java.vll.vll4j.api.Forest;
/*     */ import net.java.vll.vll4j.api.NodeBase;
/*     */ import net.java.vll.vll4j.api.NodeRoot;
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
/*     */ public class ManagerRules
/*     */ {
/*     */   ManagerRules(Vll4jGui gui)
/*     */   {
/*  35 */     this.gui = gui;
/*     */   }
/*     */   
/*     */   void reset() {
/*  39 */     this.ruleStack.clear();
/*  40 */     this.ruleBackAction.setEnabled(false);
/*  41 */     this.lastRulePopped = "";
/*     */   }
/*     */   
/*  44 */   Action ruleBackAction = new AbstractAction("Back", Resources.back16)
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/*  47 */       ManagerRules.this.lastRulePopped = ((String)ManagerRules.this.ruleStack.pop());
/*  48 */       ManagerRules.this.theComboBox.setSelectedItem(ManagerRules.this.lastRulePopped);
/*  49 */       setEnabled(!ManagerRules.this.ruleStack.isEmpty());
/*     */     }
/*     */   };
/*     */   
/*     */   void addRuleToComboBox(String ruleName) {
/*  54 */     this.theComboBox.setAction(null);
/*  55 */     int oldLength = this.theComboBox.getItemCount();
/*  56 */     for (int i = this.theComboBox.getItemCount() - 1; i >= 0; i--) {
/*  57 */       String item = (String)this.theComboBox.getItemAt(i);
/*  58 */       if (ruleName.compareTo(item) > 0) {
/*  59 */         this.theComboBox.insertItemAt(ruleName, i + 1);
/*  60 */         break; }
/*  61 */       if (ruleName.compareTo(item) == 0) {
/*  62 */         JOptionPane.showMessageDialog(this.gui, "A rule with this name already exists", "WARNING - New rule", 2);
/*     */         
/*  64 */         break; }
/*  65 */       if (i == 0) {
/*  66 */         this.theComboBox.insertItemAt(ruleName, 0);
/*     */       }
/*     */     }
/*  69 */     if (oldLength != this.theComboBox.getItemCount()) {
/*  70 */       this.theComboBox.setMaximumSize(this.theComboBox.getPreferredSize());
/*  71 */       this.theComboBox.setSelectedItem(ruleName);
/*  72 */       this.gui.theTreePanel.setRuleName(ruleName);
/*     */     }
/*  74 */     this.theComboBox.setAction(this.comboBoxAction);
/*     */   }
/*     */   
/*     */   void removeRuleFromComboBox(String ruleName) {
/*  78 */     int idx = this.theComboBox.getSelectedIndex();
/*  79 */     this.theComboBox.setAction(null);
/*  80 */     this.theComboBox.removeItemAt(idx);
/*  81 */     this.theComboBox.setAction(this.comboBoxAction);
/*  82 */     String nextItem = idx >= this.theComboBox.getItemCount() ? (String)this.theComboBox.getItemAt(idx == 0 ? 0 : idx - 1) : (String)this.theComboBox.getItemAt(idx);
/*     */     
/*  84 */     this.gui.theTreePanel.setRuleName(nextItem);
/*     */   }
/*     */   
/*  87 */   Action ruleNewAction = new AbstractAction("New rule", Resources.newReference)
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/*  90 */       String ruleName = JOptionPane.showInputDialog(ManagerRules.this.gui, "Enter rule name", "New rule", 3);
/*  91 */       if (ruleName == null)
/*  92 */         return;
/*  93 */       ruleName = ruleName.trim();
/*  94 */       if (ManagerRules.this.gui.theForest.ruleBank.containsKey(ruleName)) {
/*  95 */         JOptionPane.showMessageDialog(ManagerRules.this.gui, "Rule name already used", "WARNING - New rule", 2);
/*  96 */         return;
/*     */       }
/*  98 */       if (!ruleName.matches("[a-zA-Z$_][a-zA-Z$_0-9-]*")) {
/*  99 */         JOptionPane.showMessageDialog(ManagerRules.this.gui, "Illegal rule name", "WARNING - New rule", 2);
/* 100 */         return;
/*     */       }
/* 102 */       ManagerRules.this.ruleStack.add((String)ManagerRules.this.theComboBox.getSelectedItem());
/* 103 */       ManagerRules.this.ruleBackAction.setEnabled(!ManagerRules.this.ruleStack.isEmpty());
/* 104 */       ManagerRules.this.gui.theForest.ruleBank.put(ruleName, new NodeRoot(ruleName));
/* 105 */       ManagerRules.this.addRuleToComboBox(ruleName);
/*     */     }
/*     */   };
/*     */   
/*     */   private String[] findRuleInRules(String rule) {
/* 110 */     VisitorRuleSearch v = new VisitorRuleSearch(rule);
/* 111 */     for (NodeBase n : this.gui.theForest.ruleBank.values()) {
/* 112 */       n.accept(v);
/*     */     }
/* 114 */     return (String[])v.ruleSet.toArray(new String[v.ruleSet.size()]);
/*     */   }
/*     */   
/* 117 */   Action ruleFindAction = new AbstractAction("Find rule", Resources.search16)
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 120 */       String ruleName = (String)ManagerRules.this.theComboBox.getSelectedItem();
/* 121 */       String[] rules = ManagerRules.this.findRuleInRules(ruleName);
/* 122 */       if (rules.length == 0) {
/* 123 */         JOptionPane.showMessageDialog(ManagerRules.this.gui, String.format("Rule '%s' isn't referred in any other rule", new Object[] { ruleName }), "WARNING - Find rule", 2);
/* 124 */         return;
/*     */       }
/* 126 */       String selectedRule = (String)JOptionPane.showInputDialog(ManagerRules.this.gui, String.format("Rule '%s' is referred in rules listed below\nClick OK to display selected rule", new Object[] { ruleName }), "Find rule", 3, null, rules, rules[0]);
/*     */       
/*     */ 
/* 129 */       if (selectedRule != null) {
/* 130 */         ManagerRules.this.theComboBox.setSelectedItem(selectedRule);
/*     */       }
/*     */     }
/*     */   };
/*     */   
/* 135 */   Action ruleRenameAction = new AbstractAction("Rename rule", Resources.refresh16)
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 138 */       String currentName = (String)ManagerRules.this.theComboBox.getSelectedItem();
/* 139 */       String newName = (String)JOptionPane.showInputDialog(ManagerRules.this.gui, "Enter new name", "Rename rule", 3, null, null, currentName);
/*     */       
/* 141 */       if (newName == null)
/* 142 */         return;
/* 143 */       newName = newName.trim();
/* 144 */       if (!newName.matches("[a-zA-Z$_][a-zA-Z$_0-9-]*")) {
/* 145 */         JOptionPane.showMessageDialog(ManagerRules.this.gui, "Illegal rule name", "WARNING - Rename rule", 2);
/* 146 */         return;
/*     */       }
/* 148 */       if (ManagerRules.this.gui.theForest.ruleBank.containsKey(newName)) {
/* 149 */         JOptionPane.showMessageDialog(ManagerRules.this.gui, "Rule name already used", "WARNING - Rename rule", 2);
/* 150 */         return;
/*     */       }
/* 152 */       VisitorRuleRenaming v = new VisitorRuleRenaming(currentName, newName);
/* 153 */       for (NodeBase n : ManagerRules.this.gui.theForest.ruleBank.values()) {
/* 154 */         n.accept(v);
/*     */       }
/*     */       
/* 157 */       NodeBase n = (NodeBase)ManagerRules.this.gui.theForest.ruleBank.get(currentName);
/* 158 */       ManagerRules.this.gui.theForest.ruleBank.put(newName, n);
/* 159 */       ManagerRules.this.gui.theForest.ruleBank.remove(currentName);
/* 160 */       if (ManagerRules.this.theComboBox.getItemCount() == 1) {
/* 161 */         ManagerRules.this.theComboBox.setAction(null);
/* 162 */         ManagerRules.this.theComboBox.removeAllItems();
/* 163 */         ManagerRules.this.theComboBox.addItem(newName);
/* 164 */         ManagerRules.this.theComboBox.setAction(ManagerRules.this.comboBoxAction);
/* 165 */         ManagerRules.this.theComboBox.setMaximumSize(ManagerRules.this.theComboBox.getPreferredSize());
/* 166 */         ManagerRules.this.gui.theTreePanel.setRuleName(newName);
/*     */       } else {
/* 168 */         ManagerRules.this.removeRuleFromComboBox(currentName);
/* 169 */         ManagerRules.this.addRuleToComboBox(newName);
/* 170 */         for (int i = 0; i < ManagerRules.this.ruleStack.size(); i++) {
/* 171 */           if (((String)ManagerRules.this.ruleStack.elementAt(i)).equals(currentName)) {
/* 172 */             ManagerRules.this.ruleStack.setElementAt(newName, i);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   };
/* 178 */   Action ruleDeleteAction = new AbstractAction("Delete rule", Resources.delete16)
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 181 */       if (ManagerRules.this.theComboBox.getItemCount() == 1) {
/* 182 */         JOptionPane.showMessageDialog(ManagerRules.this.gui, "Cant delete only rule - rename instead", "WARNING - Delete rule", 2);
/* 183 */         return;
/*     */       }
/* 185 */       String ruleToDelete = (String)ManagerRules.this.theComboBox.getSelectedItem();
/* 186 */       if (ruleToDelete == null)
/* 187 */         return;
/* 188 */       String[] refdRules = ManagerRules.this.findRuleInRules(ruleToDelete);
/* 189 */       if (refdRules.length != 0) {
/* 190 */         String selectedRule = (String)JOptionPane.showInputDialog(ManagerRules.this.gui, String.format("Cant delete rule '%s'\nused in rules listed below\nClick OK to display a rule", new Object[] { ruleToDelete }), "WARNING - Delete rule", 2, null, refdRules, refdRules[0]);
/*     */         
/*     */ 
/*     */ 
/* 194 */         if (selectedRule != null)
/* 195 */           ManagerRules.this.theComboBox.setSelectedItem(selectedRule);
/* 196 */         return;
/*     */       }
/* 198 */       if (0 != JOptionPane.showConfirmDialog(ManagerRules.this.gui, String.format("Delete rule '%s'?", new Object[] { ruleToDelete }), "Delete rule", 0, 3)) {
/* 199 */         return;
/*     */       }
/* 201 */       ManagerRules.this.gui.theForest.ruleBank.remove(ruleToDelete);
/* 202 */       ManagerRules.this.removeRuleFromComboBox(ruleToDelete);
/* 203 */       ManagerRules.this.ruleStack.remove(ruleToDelete);
/* 204 */       ManagerRules.this.ruleBackAction.setEnabled(!ManagerRules.this.ruleStack.isEmpty());
/*     */     }
/*     */   };
/*     */   
/* 208 */   Action ruleOptimizeAction = new AbstractAction("Optimize rule", Resources.preferences16)
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 211 */       JOptionPane.showMessageDialog(ManagerRules.this.gui, "Rule optimization coming soon!", "Optimize rule", -1);
/*     */     }
/*     */   };
/*     */   
/*     */ 
/* 216 */   Action comboBoxAction = new AbstractAction()
/*     */   {
/*     */     public void actionPerformed(ActionEvent e) {
/* 219 */       String selectedRuleName = (String)ManagerRules.this.theComboBox.getSelectedItem();
/* 220 */       String currentRuleName = ManagerRules.this.gui.theTreePanel.rootNode.ruleName;
/* 221 */       if (selectedRuleName.equals(currentRuleName))
/* 222 */         return;
/* 223 */       if (!selectedRuleName.equals(ManagerRules.this.lastRulePopped)) {
/* 224 */         ManagerRules.this.ruleStack.add(ManagerRules.this.gui.theTreePanel.rootNode.ruleName);
/* 225 */         ManagerRules.this.ruleBackAction.setEnabled(!ManagerRules.this.ruleStack.isEmpty());
/*     */       }
/* 227 */       ManagerRules.this.gui.theTreePanel.setRuleName(selectedRuleName);
/* 228 */       ManagerRules.this.gui.theActionCodePanel.resetView();
/*     */     }
/*     */   };
/*     */   
/*     */   Vll4jGui gui;
/*     */   JComboBox<String> theComboBox;
/* 234 */   private Stack<String> ruleStack = new Stack();
/* 235 */   private String lastRulePopped = "";
/*     */ }


/* Location:              /home/yogesh/Downloads/VLL4J.jar!/net/java/vll/vll4j/gui/ManagerRules.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */