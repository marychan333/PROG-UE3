package at.ac.fhcampuswien.newsanalyzer.ui;


import at.ac.fhcampuswien.newsanalyzer.ctrl.Controller;
import org.graalvm.compiler.graph.Node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UserInterface 
{
	private Controller ctrl = new Controller();

	public void getDataFromCtrl1(){
		System.out.println("ABC");

		ctrl.process();
	}

	public void getDataFromCtrl2(){
		// TODO implement me
		System.out.println("DEF");

		ctrl.process();
	}

	public void getDataFromCtrl3(){
		// TODO implement me
		System.out.println("3");

		ctrl.process();
	}
	
	public void getDataForCustomInput() {
		// TODO implement me
		System.out.println("Enter the Topic: ");
		String r = readLine();
		System.out.print("Enter languages:\nar,de,en,es,fr,he,it,nl,no,pt,ru,se,ud,zh\n: ");
		String input = readLine();
		if (input.equals("")) {
			input = "en";
		}
		try {
			System.out.println(ctrl.getData());
		} catch {
			System.err.println(e.getMessage());
		}


	}


	public void start() {
		Menu<Runnable> menu = new Menu<>("User Interface");
		menu.setTitle("Wählen Sie aus:");
		menu.insert("a", "Choice ABC", this::getDataFromCtrl1);
		menu.insert("b", "Choice DEF", this::getDataFromCtrl2);
		menu.insert("c", "Choice 3", this::getDataFromCtrl3);
		menu.insert("d", "Choice User Input:",this::getDataForCustomInput);
		menu.insert("q", "Quit", null);
		Runnable choice;
		while ((choice = menu.exec()) != null) {
			 choice.run();
		}
		System.out.println("Program finished");
	}


    protected String readLine() {
		String value = "\0";
		BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
		try {
			value = inReader.readLine();
        } catch (IOException ignored) {
		}
		return value.trim();
	}

	protected Double readDouble(int lowerlimit, int upperlimit) 	{
		Double number = null;
        while (number == null) {
			String str = this.readLine();
			try {
				number = Double.parseDouble(str);
            } catch (NumberFormatException e) {
                number = null;
				System.out.println("Please enter a valid number:");
				continue;
			}
            if (number < lowerlimit) {
				System.out.println("Please enter a higher number:");
                number = null;
            } else if (number > upperlimit) {
				System.out.println("Please enter a lower number:");
                number = null;
			}
		}
		return number;
	}
}
