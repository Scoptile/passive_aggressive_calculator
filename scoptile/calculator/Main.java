package scoptile.calculator;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.scoptile.util.Draw;
import com.scoptile.util.program.Program;

public class Main extends Program {
	private List<Button> buttons = new ArrayList<Button>();
	
	private String lastNumber = "0";
	private String currentNumber = "0";
	private String operation = "";
	private boolean clearCurrent = false;
	private boolean actuallyZero = false;
	
	private int tries = 0;
	private String lastEq = "";
	
	public Main () {
		super(304, 490, new Color(0xFFFFFF));
	}
	
	public void init () {
		buttons.add(new Button(16, 200, 64, 64, "7"));
		buttons.add(new Button(88, 200, 64, 64, "8"));
		buttons.add(new Button(160, 200, 64, 64, "9"));
		buttons.add(new Button(16, 272, 64, 64, "4"));
		buttons.add(new Button(88, 272, 64, 64, "5"));
		buttons.add(new Button(160, 272, 64, 64, "6"));
		buttons.add(new Button(16, 346, 64, 64, "1"));
		buttons.add(new Button(88, 346, 64, 64, "2"));
		buttons.add(new Button(160, 346, 64, 64, "3"));
		buttons.add(new Button(16, 418, 64, 64, "."));
		buttons.add(new Button(88, 418, 64, 64, "0"));
		buttons.add(new Button(160, 418, 64, 64, "-/+"));
		buttons.add(new Button(160, 128, 64, 64, "C"));
		buttons.add(new Button(232, 128, 64, 64, "÷"));
		buttons.add(new Button(232, 200, 64, 64, "x"));
		buttons.add(new Button(232, 272, 64, 64, "-"));
		buttons.add(new Button(232, 346, 64, 64, "+"));
		buttons.add(new Button(232, 418, 64, 64, "="));
		
		display.getFrame().setResizable(false);
	}
	
	public void tick () {
		for (int i = 0; i < buttons.size(); i ++) buttons.get(i).tick();
		
		keyboard.tick();
		mouse.tick();
	}
	
	public void draw () {
		Graphics2D g = Draw.g;
		
		g.setFont(new Font("Consolas", 0, currentNumber.length() <= 7 ? 72 : currentNumber.length() == 8 ? 64 : currentNumber.length() == 9 ? 56 : currentNumber.length() == 10 ? 51 : currentNumber.length() == 11 ? 46 : 32));
		g.setColor(new Color(0x000000));
		g.drawString(currentNumber, 296 - g.getFontMetrics(g.getFont()).stringWidth(currentNumber), 16 + g.getFont().getSize2D());
		
		for (int i = 0; i < buttons.size(); i ++) buttons.get(i).render(g);
	}
	
	public class Button {
		public int x;
		public int y;
		public int width;
		public int height;
		public String text;
		
		public Button (int x, int y, int width, int height, String text) {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			this.text = text;
		}
		
		public void tick () {
			boolean mouseOver = (mouse.mouseX >= x && mouse.mouseX <= x + width && mouse.mouseY >= y && mouse.mouseY <= y + height);
			boolean mouseClick = mouseOver && mouse.mouseCheck(MouseEvent.BUTTON1);
			
			if (mouse.mouseCheckReleased(MouseEvent.BUTTON1) && mouseOver) {
				if (text == "C") {
					currentNumber = "0";
					operation = "";
				}
				else if (text == "÷" || text == "x" || text == "-" || text == "+") {
					operation = text;
					if (actuallyZero) currentNumber = "0";
					actuallyZero = false;
					lastNumber = currentNumber;
					clearCurrent = true;
				}
				else if (text == "-/+") {
					if (currentNumber.startsWith("-")) currentNumber = currentNumber.substring(1);
					else currentNumber = "-" + currentNumber;
				}
				else if (text == "=") {
					Random rand = new Random();
					
					if (operation != "") {
						int choice = rand.nextInt(6);
						
						if (choice == 0) {
							currentNumber = "no.";
							actuallyZero = true;
						}
						else if (choice == 2) {
							currentNumber = "( ⌐■ ͟ʖ ■ )";
						}
						else {
							if (actuallyZero) currentNumber = "0";
							actuallyZero = false;
							
							if (operation == "÷") currentNumber = (Float.parseFloat(lastNumber) / Float.parseFloat(currentNumber)) + "";
							if (operation == "x") currentNumber = (Float.parseFloat(lastNumber) * Float.parseFloat(currentNumber)) + "";
							if (operation == "-") currentNumber = (Float.parseFloat(lastNumber) - Float.parseFloat(currentNumber)) + "";
							if (operation == "+") currentNumber = (Float.parseFloat(lastNumber) + Float.parseFloat(currentNumber)) + "";
						}
						
						if (choice == 1) currentNumber = (Float.parseFloat(currentNumber) + 1) + "";
						if (choice == 3) currentNumber = "O _O " + currentNumber;
						
						if (currentNumber.endsWith(".0")) currentNumber = currentNumber.substring(0, currentNumber.length() - 2);
						
						lastNumber = "";
						
						operation = "";
						clearCurrent = true;
					}
					
					lastEq = lastNumber + " " + operation + " " + currentNumber;
				}
				else {
					if (clearCurrent || currentNumber == "0") {
						currentNumber = "";
						clearCurrent = false;
					}
					
					currentNumber += text;
				}
			}
		}
		
		public void render (Graphics2D g) {
			boolean mouseOver = (mouse.mouseX >= x && mouse.mouseX <= x + width && mouse.mouseY >= y && mouse.mouseY <= y + height);
			boolean mouseClick = mouseOver && mouse.mouseCheck(MouseEvent.BUTTON1);
			
			if (mouseOver) {
				g.setColor(operation == text ? new Color(0xC0E0C0) : new Color(0xC0C0C0));
			}
			else {
				g.setColor(operation == text ? new Color(0xD0E0D0) : new Color(0xD0D0D0));
			}
			
			g.fillRect(x + (mouseClick ? 2 : 0), y + (mouseClick ? 2 : 0), width - (mouseClick ? 4 : 0), height - (mouseClick ? 4 : 0));
			
			g.setFont(new Font("Consolas", 0, (mouseClick ? 28 : 32)));
			
			FontMetrics fm = g.getFontMetrics(g.getFont());
			
			g.setColor(new Color(0x000000));
			g.drawString(text, x + width / 2 - fm.stringWidth(text) / 2, y + height / 2 + g.getFont().getSize2D() / 2 - 5);
		}
	}
}
