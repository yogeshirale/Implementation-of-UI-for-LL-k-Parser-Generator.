/*    */ package net.java.vll.vll4j.gui;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import java.awt.FontMetrics;
/*    */ import java.awt.Graphics;
/*    */ import java.awt.Rectangle;
/*    */ import java.util.ArrayList;
/*    */ import javax.swing.JTextArea;
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
/*    */ public class LogTextArea
/*    */   extends JTextArea
/*    */ {
/*    */   public void paintComponent(Graphics g)
/*    */   {
/* 29 */     super.paintComponent(g);
/* 30 */     if (this.fontMetrics == null) {
/* 31 */       this.fontMetrics = g.getFontMetrics();
/* 32 */       this.fontBase = (this.fontMetrics.getLeading() + this.fontMetrics.getAscent());
/* 33 */       this.fontHeight = this.fontMetrics.getHeight();
/* 34 */       this.backGroundColor = getBackground();
/*    */     }
/* 36 */     int y1 = getVisibleRect().y;
/* 37 */     int y2 = y1 + getVisibleRect().height;
/* 38 */     int width = getVisibleRect().width;
/* 39 */     int rowHeight = getRowHeight();
/* 40 */     for (Integer[] el : this.errLines)
/*    */       try {
/* 42 */         int textOffset = el[0].intValue();
/* 43 */         int textLength = el[1].intValue();
/* 44 */         int y = modelToView(textOffset).y;
/* 45 */         if ((y + rowHeight >= y1) && (y <= y2)) {
/* 46 */           String s = getText(textOffset, textLength);
/*    */           
/* 48 */           if ((getLineWrap()) && (s != null) && (!s.isEmpty())) {
/* 49 */             int len = 0;
/* 50 */             for (int j = 0;; j++) 
						{ if (j >= s.length()) 
							break;///////////////////////////////////////////////// label315
/* 51 */               len += this.fontMetrics.charWidth(s.charAt(j));
/* 52 */               if (len > width) {
/* 53 */                 String ss = s.substring(0, j);
/* 54 */                 g.setColor(this.backGroundColor);
/* 55 */                 g.fillRect(0, y, width, this.fontHeight);
/* 56 */                 g.setColor(Color.red);
/* 57 */                 g.drawString(ss, 0, y + this.fontBase);
/* 58 */                 y += rowHeight;
/* 59 */                 s = s.substring(j);
/* 60 */                 break;
/*    */               }
/*    */             }
/*    */           }
/*    */           
/* 65 */           if (!s.isEmpty()) {
/* 66 */             g.setColor(this.backGroundColor);
/* 67 */             g.fillRect(0, y, width, this.fontHeight);
/* 68 */             g.setColor(Color.red);
/* 69 */             g.drawString(s, 0, y + this.fontBase);
/*    */           }
/*    */         }
/*    */       }
		catch (Exception e) 
		{}
		/*    */     label315 :
			{}
			}
/*    */   
/* 75 */   volatile ArrayList<Integer[]> errLines = new ArrayList();
/*    */   private Color backGroundColor;
/*    */   private int fontHeight;
/* 78 */   private int fontBase; private FontMetrics fontMetrics = null;
/*    */ }


/* Location:              /home/yogesh/Downloads/VLL4J.jar!/net/java/vll/vll4j/gui/LogTextArea.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */