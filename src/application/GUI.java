package application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUI extends JFrame 
{
	public JLabel text = new JLabel("0");
	public ArrayList<JButton> calculatorButtons = new ArrayList<JButton>();
	private static boolean started = false;

	public GUI()
	{
		Font textFont = new Font("Arial", Font.PLAIN, 50);
		Font calculatorFont = new Font("Arial", Font.PLAIN, 35);
		
		Color white = new Color(255, 255, 255);
		Color greyL = new Color(200, 200, 200);
		Color greyD = new Color(100, 100, 100);
		Color greyDP = new Color(135, 135, 135);
		
		BufferedImage icon = null;
		try
		{
			icon = ImageIO.read(getClass().getResource("/res/Calculator.png"));
		}
		catch (Exception ex)
		{
			System.out.println(ex);
		}
		
		this.setTitle("Calculator");
		this.setIconImage(icon);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(550, 500);
		this.getContentPane().setLayout(new BorderLayout());
		
		JPanel textPanel = new JPanel();
		textPanel.setSize(600, 100);
		FlowLayout flowTP = new FlowLayout();
		flowTP.setAlignment(FlowLayout.RIGHT);
		textPanel.setLayout(flowTP);
		text.setFont(textFont);
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new GridLayout(4, 5));
		
		calculatorButtons.add(new JButton("7"));
		calculatorButtons.add(new JButton("8"));
		calculatorButtons.add(new JButton("9"));
		calculatorButtons.add(new JButton("("));
		calculatorButtons.add(new JButton(")"));
		calculatorButtons.add(new JButton("4"));
		calculatorButtons.add(new JButton("5"));
		calculatorButtons.add(new JButton("6"));
		calculatorButtons.add(new JButton("+"));
		calculatorButtons.add(new JButton("-"));
		calculatorButtons.add(new JButton("1"));
		calculatorButtons.add(new JButton("2"));
		calculatorButtons.add(new JButton("3"));
		calculatorButtons.add(new JButton("*"));
		calculatorButtons.add(new JButton("/"));
		calculatorButtons.add(new JButton("."));
		calculatorButtons.add(new JButton("0"));
		calculatorButtons.add(new JButton("DEL"));
		calculatorButtons.add(new JButton("="));
		calculatorButtons.add(new JButton("C"));
		
		String[] numbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
		String[] specials = {"DEL", "C", "="};
		String[] operators = {"+", "-", "*", "/"};
		
		for(JButton button : calculatorButtons)
		{	
			button.setFocusPainted(false);
			button.setFont(calculatorFont);
			for(String spec : specials)
			{
				if(button.getText().equals(spec))
					button.setBackground(greyL);
			}
			for(String op : operators)
			{
				if(button.getText().equals(op))
					button.setBackground(greyL);
			}
			if(button.getText().equals("(") || button.getText().equals(")") || button.getText().equals("."))
				button.setBackground(greyL);
			for(String num : numbers)
			{
				if(button.getText().equals(num))
					button.setBackground(white);
			}
			
			button.addMouseListener(new MouseListener() 
			{
				@Override
				public void mouseClicked(MouseEvent arg0) 
				{
					button.setBackground(greyD);
				}

				@Override
				public void mouseEntered(MouseEvent arg0) 
				{
					button.setBackground(greyDP);
				}

				@Override
				public void mouseExited(MouseEvent arg0) 
				{
					for(String spec : specials)
					{
						if(button.getText().equals(spec))
							button.setBackground(greyL);
					}
					for(String op : operators)
					{
						if(button.getText().equals(op))
							button.setBackground(greyL);
					}
					if(button.getText().equals("(") || button.getText().equals(")") || button.getText().equals("."))
						button.setBackground(greyL);
					for(String num : numbers)
					{
						if(button.getText().equals(num))
							button.setBackground(white);
					}
				}

				@Override
				public void mousePressed(MouseEvent arg0) 
				{
					button.setBackground(greyDP);
				}

				@Override
				public void mouseReleased(MouseEvent arg0) 
				{
					button.setBackground(greyDP);
				}
			});
			
			buttonsPanel.add(button);
			
			button.addActionListener((evt) -> {
				boolean isSpecial = false;
				for(String character : specials)
				{
					if(button.getText().equals(character))
						isSpecial = true;
				}
				boolean isOperator = false;
				for(String character : operators)
				{
					if(button.getText().equals(character))
						isOperator = true;
				}
				
				if(!started)
				{
					if(!isOperator && !isSpecial)
					{
						if(button.getText().equals("."))
							Main.addCharToCalc("0" + button.getText(), text);
						else
							Main.addCharToCalc(button.getText(), text);
						
						started  = true;
					}
				}
				else
				{
					String before = text.getText();
					
					if(!isSpecial)
					{
						if(!isOperator)
						{
							if(button.getText().equals("."))
							{
								String[] nummbers = text.getText().split(" ");
								String last = nummbers[nummbers.length - 1];
								if(!last.contains("."))
								{
									if(!text.getText().endsWith(" "))
										Main.addCharToCalc(before + button.getText(), text);
									else
										Main.addCharToCalc(before + "0" + button.getText(), text);
								}
							}
							else
								Main.addCharToCalc(before + button.getText(), text);
						}
						else
						{
							if(!before.endsWith(" "))
							{
								if((before.substring(before.length() - 1).equals(".")))
									Main.addCharToCalc(before + "0 " + button.getText() + " ", text);
								else
									Main.addCharToCalc(before + " " + button.getText() + " ", text);
							}
						}
					}
					else
					{
						if(button.getText().equals(specials[0]))
						{
							if(before.endsWith(" "))
								before = before.substring(0, before.length() - 3);
							else
								before = before.substring(0, before.length() - 1);
							
							if(before.length() >= 1)
								Main.addCharToCalc(before, text);
							else
							{
								Main.addCharToCalc("0", text);
								started = false;
							}
						}
						
						if(button.getText().equals(specials[1]))
						{
							Main.addCharToCalc("0", text);
							started = false;
						}
					}
				}
				
				if(button.getText().equals(specials[2]))
				{
					try
					{
						ScriptEngineManager script = new ScriptEngineManager();
						
						ScriptEngine engine = script.getEngineByName("JavaScript");
						
						String operation = text.getText();
						Object obj = engine.eval(operation);

						text.setText(obj.toString());
					}
					catch(Exception ex)
					{
						text.setText("ERROR!");
						started = false;
					}
				}
			});
		}

		this.getContentPane().add(textPanel, BorderLayout.NORTH);
		this.getContentPane().add(buttonsPanel, BorderLayout.CENTER);
		
		textPanel.add(text);
		
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	}
}
