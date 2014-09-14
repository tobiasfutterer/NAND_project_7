import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Parser {
	private FileReader vmReader;
	private ArrayList<String> commands;
	private Integer commandCounter = 0;

	public Parser(FileReader hackInput) {
		vmReader = hackInput;
		parseInputFile();
	}

	public void parseInputFile() {
		BufferedReader br = new BufferedReader(vmReader);
		String strLine;
		commands = new ArrayList<String>();
		try {
			while ((strLine = br.readLine()) != null) {
				if (strLine.length() > 0 && strLine.contains("/"))
					strLine = strLine.substring(0, strLine.indexOf("//"));
				strLine = strLine.replaceAll("\\s+", "|");
				if (strLine.length() > 0)
					commands.add(strLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	};

	public boolean hasMoreCommands() {
		if (commandCounter < commands.size())
			return true;
		return false;
	};

	public String advance() {
		commandCounter += 1;
		return commands.get(commandCounter - 1);
	}

	public String commandType(String command) {

		String[] commandSplit = command.split("\\|");
		if ((commandSplit[0].toLowerCase().equals("add")) || (commandSplit[0].toLowerCase().equals("sub"))
				|| (commandSplit[0].toLowerCase().equals("neg")) || (commandSplit[0].toLowerCase().equals("eq"))
				|| (commandSplit[0].toLowerCase().equals("gt")) || (commandSplit[0].toLowerCase().equals("lt"))
				|| (commandSplit[0].toLowerCase().equals("and")) || (commandSplit[0].toLowerCase().equals("or"))
				|| (commandSplit[0].toLowerCase().equals("not"))) {
			return "C_ARITHMETIC";
		}
		if (commandSplit[0].toLowerCase().equals("push")) {
			return "C_PUSH";
		}
		if (commandSplit[0].toLowerCase().equals("pop")) {
			return "C_POP";
		}
		if (commandSplit[0].toLowerCase().equals("label")) {
			return "C_LABEL";
		}
		if (commandSplit[0].toLowerCase().equals("goto")) {
			return "C_GOTO";
		}
		if (commandSplit[0].toLowerCase().equals("if-goto")) {
			return "C_IF";
		}
		if (commandSplit[0].toLowerCase().equals("function")) {
			return "C_FUNCTION";
		}
		if (commandSplit[0].toLowerCase().equals("return")) {
			return "C_RETURN";
		}
		if (commandSplit[0].toLowerCase().equals("call")) {
			return "C_CALL";
		}
		return "";
	}

	public String arg1(String command) {
		String[] commandSplit = command.split("\\|");
		if (commandSplit.length == 1) return commandSplit[0];
		else if (commandSplit.length > 1)return commandSplit[1];
		return "";
	}

	public String arg2(String command) {
		String[] commandSplit = command.split("\\|");
		if (commandSplit.length > 2) return commandSplit[2];
		return "";
	}
}
