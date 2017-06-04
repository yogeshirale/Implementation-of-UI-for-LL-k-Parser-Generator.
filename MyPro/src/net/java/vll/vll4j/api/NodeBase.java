/*     */ package net.java.vll.vll4j.api;
/*     */ 
/*     */ import javax.swing.tree.DefaultMutableTreeNode;
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
/*     */ public abstract class NodeBase
/*     */   extends DefaultMutableTreeNode
/*     */ {
/*     */   public abstract Object accept(VisitorBase paramVisitorBase);
/*     */   
/*     */   public abstract String getName();
/*     */   
/*     */   public abstract String nodeType();
/*     */   
/*     */   protected void copyFrom(NodeBase src)
/*     */   {
/*  32 */     this.multiplicity = src.multiplicity;
/*  33 */     this.errorMessage = src.errorMessage;
/*  34 */     this.description = src.description;
/*  35 */     this.actionText = src.actionText;
/*  36 */     this.isTraced = false;
/*  37 */     this.isDropped = src.isDropped;
/*  38 */     this.actionFunction = src.actionFunction;
/*     */   }
/*     */   
/*     */   public String nodeName() {
/*  42 */     NodeBase parentNode = (NodeBase)getParent();
/*  43 */     if (parentNode == null)
/*  44 */       return "";
/*  45 */     int idx = parentNode.getIndex(this);
/*  46 */     return String.format("%s.%d", new Object[] { parentNode.nodeName(), Integer.valueOf(idx) });
/*     */   }
/*     */   
/*     */   public String getAttributes() {
/*  50 */     NodeBase parent = (NodeBase)getParent();
/*  51 */     boolean isCommitted = (parent != null) && ((parent instanceof NodeSequence)) && (((NodeSequence)parent).commitIndex == parent.getIndex(this));
/*     */     
/*  53 */     if ((this.isTraced) || (this.isDropped) || (!this.errorMessage.trim().isEmpty()) || (!this.actionText.trim().isEmpty()) || (isCommitted) || (((this instanceof NodeRoot)) && (((NodeRoot)this).isPackrat)))
/*     */     {
/*  55 */       StringBuilder sb = new StringBuilder();
/*  56 */       sb.append("[");
/*  57 */       if (!this.actionText.trim().isEmpty())
/*  58 */         sb.append("action");
/*  59 */       if (isCommitted) {
/*  60 */         if (sb.length() != 1)
/*  61 */           sb.append(" ");
/*  62 */         sb.append("commit");
/*     */       }
/*  64 */       if (this.isDropped) {
/*  65 */         if (sb.length() != 1)
/*  66 */           sb.append(" ");
/*  67 */         sb.append("drop");
/*     */       }
/*  69 */       if (!this.errorMessage.trim().isEmpty()) {
/*  70 */         if (sb.length() != 1)
/*  71 */           sb.append(" ");
/*  72 */         sb.append("message");
/*     */       }
/*  74 */       if (((this instanceof NodeRoot)) && (((NodeRoot)this).isPackrat)) {
/*  75 */         if (sb.length() != 1)
/*  76 */           sb.append(" ");
/*  77 */         sb.append("packrat");
/*     */       }
/*  79 */       if (this.isTraced) {
/*  80 */         if (sb.length() != 1)
/*  81 */           sb.append(" ");
/*  82 */         sb.append("trace");
/*     */       }
/*  84 */       sb.append("]");
/*  85 */       return sb.toString();
/*     */     }
/*  87 */     return "";
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean getAllowsChildren()
/*     */   {
/*  93 */     return true;
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/*  98 */     return getClass().getSimpleName() + ": " + nodeName();
/*     */   }
/*     */   
/* 101 */   public Multiplicity multiplicity = Multiplicity.One;
/* 102 */   public String errorMessage = "";
/* 103 */   public String description = "";
/* 104 */   public String actionText = "";
/* 105 */   public boolean isTraced = false;
/* 106 */   public boolean isDropped = false;
/* 107 */   public ActionFunction actionFunction = null;
/*     */ }


/* Location:              /home/yogesh/Downloads/VLL4J.jar!/net/java/vll/vll4j/api/NodeBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */