package Mains;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;

import Game.Game;

public class Main{
	public static int x = 800;
	public static int y = 600;

    public static boolean opened_menu = true;

	public static void main(String[] args) {
		JFrame f=new JFrame("Menu");  
		JButton b=new JButton("Play Game");

		b.setFont(new Font("Calibri", Font.PLAIN, 40));
        b.setBackground(new Color(0x2dce98));
        b.setForeground(Color.WHITE);
        // customize the button with your own look
        b.setUI(new StyledButtonUI());

		f.setSize(x,y);  
		f.setLayout(new GridBagLayout());

		f.add(b);

		f.setResizable(false);
		f.setLocationRelativeTo(null);
		f.setBackground(Color.BLACK);
        f.getContentPane().setBackground(Color.BLACK);
		f.setVisible(true);  

		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){ 
                if (opened_menu){
                    opened_menu=false;
					Game.main(args);
                    f.setVisible(false);
					f.dispose();
                }
			}
		});

        f.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode==KeyEvent.VK_SPACE){
                    if (opened_menu){
                        opened_menu=false;
                        Game.main(args);
                        f.setVisible(false);
						f.dispose();
                    }
				}else if(keyCode==KeyEvent.VK_ESCAPE){
					f.setVisible(false);
					f.dispose();
				}
            }
        });
	}
}

class StyledButtonUI extends BasicButtonUI {
    @Override
    public void installUI (JComponent c) {
        super.installUI(c);
        AbstractButton button = (AbstractButton) c;
        button.setOpaque(false);
        button.setBorder(new EmptyBorder(5, 15, 5, 15));
    }

    @Override
    public void paint (Graphics g, JComponent c) {
        AbstractButton b = (AbstractButton) c;
        paintBackground(g, b, b.getModel().isPressed() ? 2 : 0);
        super.paint(g, c);
    }

    private void paintBackground (Graphics g, JComponent c, int yOffset) {
        Dimension size = c.getSize();
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(c.getBackground().darker());
        g.fillRoundRect(0, yOffset, size.width, size.height - yOffset, 10, 10);
        g.setColor(c.getBackground());
        g.fillRoundRect(0, yOffset, size.width, size.height + yOffset - 5, 10, 10);
    }
}