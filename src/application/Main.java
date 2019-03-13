package application;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JLabel;

public class Main 
{
	public static GUI calculator;
	
	public static void main(String[] args) 
	{
		calculator = new GUI();
		calculator.setVisible(true);
	}
	
	public static void addCharToCalc(String _char, JLabel _label)
	{
		_label.setText(_char);
	}
	
	public class KeyListener extends KeyAdapter {
		public void keyPressed(KeyEvent evt)
		{
			switch(evt.getKeyChar())
			{
				case KeyEvent.VK_0:
					addCharToCalc("" + KeyEvent.VK_0, calculator.text);
					break;
			}
		}
	}
}
