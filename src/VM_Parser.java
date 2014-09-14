import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class VM_Parser {

	public static void main(String[] args) throws FileNotFoundException {
		if (hasCommands(args) || true) {
			// File f= new File(args[0]);
			String test = "C:\\Projects\\00_Eigenes\\Built Your Own Computer\\nand2tetris\\projects\\07\\MemoryAccess\\Testtest";
			File f = new File(test);
			if (f.isFile()) {
				Parser p1 = new Parser(new FileReader(f));
				CodeWriter c1 = new CodeWriter(f.getAbsoluteFile()
						.getParentFile().getAbsolutePath()
						+ "\\"
						+ f.getName().substring(0, f.getName().indexOf("."))
						+ ".asm");
				String command, commandType;
				while (p1.hasMoreCommands()) {
					c1.setFileName(f.getName().substring(0,
							f.getName().indexOf(".")));
					command = p1.advance();
					commandType = p1.commandType(command);
					if (commandType.equals("C_ARITHMETIC"))
						c1.writeArithmetic(p1.arg1(command));
					else if (commandType.equals("C_PUSH"))
						c1.WritePushPop("push", p1.arg1(command),
								Integer.valueOf(p1.arg2(command)));
					else if (commandType.equals("C_POP"))
						c1.WritePushPop("pop", p1.arg1(command),
								Integer.valueOf(p1.arg2(command)));
				}
				c1.close();
			} else if (f.isDirectory()) {
				String asmFileName = f.getAbsolutePath().substring(f.getAbsolutePath().lastIndexOf("\\")+1) + ".asm";
				
				CodeWriter c1 = new CodeWriter(f.getAbsolutePath() + "\\" + asmFileName);
				
				for (File g : f.listFiles()) {
					if (!g.getAbsolutePath().contains(".vm")) break;
					Parser p1 = new Parser(new FileReader(g));
					String command, commandType;
					while (p1.hasMoreCommands()) {
						c1.setFileName(g.getName().substring(0,
								g.getName().indexOf(".")));
						command = p1.advance();
						commandType = p1.commandType(command);
						if (commandType.equals("C_ARITHMETIC"))
							c1.writeArithmetic(p1.arg1(command));
						else if (commandType.equals("C_PUSH"))
							c1.WritePushPop("push", p1.arg1(command),
									Integer.valueOf(p1.arg2(command)));
						else if (commandType.equals("C_POP"))
							c1.WritePushPop("pop", p1.arg1(command),
									Integer.valueOf(p1.arg2(command)));
					}
				}
				c1.close();
			}
		} else
			System.out.println("has no commands");
	}

	public static boolean hasCommands(String[] args) {
		if (args.length > 0)
			return true;
		return false;
	}

}
