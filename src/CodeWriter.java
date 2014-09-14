import java.io.PrintWriter;

public class CodeWriter {
	private PrintWriter writer;
	private StringBuilder sb;
	private Integer label_counter = 0;
	private String fileName = "";

	public CodeWriter(String newFileName) {
		try {
			writer = new PrintWriter(newFileName, "UTF-8");
			fileName = newFileName;
			sb = new StringBuilder();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void setFileName(String newFileName) {
		fileName = newFileName;
	}

	public void writeArithmetic(String command) {
		if (command.toLowerCase().equals("add")) {
			sb.append("// " + command);
			sb.append(System.lineSeparator());
			set_PointerMinusOne();
			add();
		}
		if (command.toLowerCase().equals("sub")) {
			sb.append("// " + command);
			sb.append(System.lineSeparator());
			set_PointerMinusOne();
			sub();
		}
		if (command.toLowerCase().equals("neg")) {
			sb.append("// " + command);
			sb.append(System.lineSeparator());
			neg();
		}
		if (command.toLowerCase().equals("eq")) {
			sb.append("// " + command);
			sb.append(System.lineSeparator());
			set_PointerMinusOne();
			sub();
			// and then check if @SP != 0 and push result to SP
			set_PointerMinusOne();
			eq();
			set_PointerPlusOne();
		}
		if (command.toLowerCase().equals("gt")) {
			// X > y , X = 256 , Y = 257
			sb.append("// " + command);
			sb.append(System.lineSeparator());
			set_PointerMinusOne();
			sub();
			// and then check if @SP > 0 and push result to SP
			set_PointerMinusOne();
			gt();
			set_PointerPlusOne();
		}
		if (command.toLowerCase().equals("lt")) {
			// X < y , X = 256 , Y = 257
			sb.append("// " + command);
			sb.append(System.lineSeparator());
			// subtract y from x
			set_PointerMinusOne();
			sub();
			// and then check if @R13 < 0 and push result to SP
			set_PointerMinusOne();
			lt();
			set_PointerPlusOne();
		}
		if (command.toLowerCase().equals("and")) {
			sb.append("// " + command);
			sb.append(System.lineSeparator());
			set_PointerMinusOne();
			and();
		}
		if (command.toLowerCase().equals("or")) {
			sb.append("// " + command);
			sb.append(System.lineSeparator());
			set_PointerMinusOne();
			or();
		}
		if (command.toLowerCase().equals("not")) {
			// NOT Y=256
			sb.append("// " + command);
			sb.append(System.lineSeparator());
			not();
		}
	}

	public void WritePushPop(String command, String segment, Integer index) {
		// memory segments: argument, local, static, constant, this,that,
		// pointer, temp
		if (command.toLowerCase().equals("push")) {
			sb.append("// " + command + " " + segment + " " + index.toString() );
			sb.append(System.lineSeparator());

			if (segment.toLowerCase().equals("constant")) {
				push_Constant(index);
			} else if (segment.toLowerCase().equals("argument")) {
				push_Argument(index);
			} else if (segment.toLowerCase().equals("local")) {
				push_Local(index);
			} else if (segment.toLowerCase().equals("static")) {
				push_Static(index);
			} else if (segment.toLowerCase().equals("this")) {
				push_This(index);
			} else if (segment.toLowerCase().equals("that")) {
				push_That(index);
			} else if (segment.toLowerCase().equals("pointer")) {
				push_Pointer(index);
			} else if (segment.toLowerCase().equals("temp")) {
				push_Temp(index);
			}
		} else if (command.toLowerCase().equals("pop")) {
			sb.append("// " + command + " " + segment + " " + index.toString() );
			sb.append(System.lineSeparator());
			if (segment.toLowerCase().equals("argument")) {
				pop_Argument(index);
			} else if (segment.toLowerCase().equals("local")) {
				pop_Local(index);
			} else if (segment.toLowerCase().equals("static")) {
				pop_Static(index);
			} else if (segment.toLowerCase().equals("this")) {
				pop_This(index);
			} else if (segment.toLowerCase().equals("that")) {
				pop_That(index);
			} else if (segment.toLowerCase().equals("pointer")) {
				pop_Pointer(index);
			} else if (segment.toLowerCase().equals("temp")) {
				pop_Temp(index);
			}

		}
	}

	public void close() {
		writer.print(sb);
		writer.close();
	}

	private void not() {
		sb.append("@SP");
		sb.append(System.lineSeparator());
		sb.append("A=M");
		sb.append(System.lineSeparator());
		sb.append("A=A-1");
		sb.append(System.lineSeparator());
		sb.append("D=M");
		sb.append(System.lineSeparator());
		sb.append("M=!D");
		sb.append(System.lineSeparator());
	}

	private void add() {
		sb.append("@SP");
		sb.append(System.lineSeparator());
		sb.append("A=M");
		sb.append(System.lineSeparator());
		sb.append("D=M");
		sb.append(System.lineSeparator());
		sb.append("A=A-1");
		sb.append(System.lineSeparator());
		sb.append("M=M+D");
		sb.append(System.lineSeparator());
	}

	private void sub() {
		sb.append("@SP");
		sb.append(System.lineSeparator());
		sb.append("A=M");
		sb.append(System.lineSeparator());
		sb.append("D=M");
		sb.append(System.lineSeparator());
		sb.append("A=A-1");
		sb.append(System.lineSeparator());
		sb.append("M=M-D");
		sb.append(System.lineSeparator());
	}

	private void and() {
		sb.append("@SP");
		sb.append(System.lineSeparator());
		sb.append("A=M");
		sb.append(System.lineSeparator());
		sb.append("D=M");
		sb.append(System.lineSeparator());
		sb.append("A=A-1");
		sb.append(System.lineSeparator());
		sb.append("M=D&M");
		sb.append(System.lineSeparator());
	}

	private void or() {
		sb.append("@SP");
		sb.append(System.lineSeparator());
		sb.append("A=M");
		sb.append(System.lineSeparator());
		sb.append("D=M");
		sb.append(System.lineSeparator());
		sb.append("A=A-1");
		sb.append(System.lineSeparator());
		sb.append("M=D|M");
		sb.append(System.lineSeparator());
	}

	private void gt() {
		sb.append("@SP");
		sb.append(System.lineSeparator());
		sb.append("A=M");
		sb.append(System.lineSeparator());
		sb.append("D=M");
		sb.append(System.lineSeparator());
		sb.append("@ISGREATER" + label_counter.toString());
		sb.append(System.lineSeparator());
		sb.append("D;JGT");
		sb.append(System.lineSeparator());
		push_Constant(0);
		sb.append("@END_EQ" + label_counter.toString());
		sb.append(System.lineSeparator());
		sb.append("0;JMP");
		sb.append(System.lineSeparator());
		sb.append("(ISGREATER" + label_counter.toString() + ")");
		sb.append(System.lineSeparator());
		sb.append("D=-1");
		sb.append(System.lineSeparator());
		sb.append("@SP");
		sb.append(System.lineSeparator());
		sb.append("A=M");
		sb.append(System.lineSeparator());
		sb.append("M=D");
		sb.append(System.lineSeparator());
		sb.append("@END_EQ" + label_counter.toString());
		sb.append(System.lineSeparator());
		sb.append("0;JMP");
		sb.append(System.lineSeparator());
		sb.append("(END_EQ" + label_counter.toString() + ")");
		sb.append(System.lineSeparator());
		label_counter += 1;
	}

	private void lt() {
		sb.append("@SP");
		sb.append(System.lineSeparator());
		sb.append("A=M");
		sb.append(System.lineSeparator());
		sb.append("D=M");
		sb.append(System.lineSeparator());
		sb.append("@ISLESS" + label_counter.toString());
		sb.append(System.lineSeparator());
		sb.append("D;JLT");
		sb.append(System.lineSeparator());
		push_Constant(0);
		sb.append("@END_EQ" + label_counter.toString());
		sb.append(System.lineSeparator());
		sb.append("0;JMP");
		sb.append(System.lineSeparator());
		sb.append("(ISLESS" + label_counter.toString() + ")");
		sb.append(System.lineSeparator());
		sb.append("D=-1");
		sb.append(System.lineSeparator());
		sb.append("@SP");
		sb.append(System.lineSeparator());
		sb.append("A=M");
		sb.append(System.lineSeparator());
		sb.append("M=D");
		sb.append(System.lineSeparator());
		sb.append("@END_EQ" + label_counter.toString());
		sb.append(System.lineSeparator());
		sb.append("0;JMP");
		sb.append(System.lineSeparator());
		sb.append("(END_EQ" + label_counter.toString() + ")");
		sb.append(System.lineSeparator());
		label_counter += 1;
	}

	private void eq() {
		sb.append("@SP");
		sb.append(System.lineSeparator());
		sb.append("A=M");
		sb.append(System.lineSeparator());
		sb.append("D=M");
		sb.append(System.lineSeparator());
		sb.append("@ISEQUAL" + label_counter.toString());
		sb.append(System.lineSeparator());
		sb.append("D;JEQ");
		sb.append(System.lineSeparator());
		push_Constant(0);
		sb.append("@END_EQ" + label_counter.toString());
		sb.append(System.lineSeparator());
		sb.append("0;JMP");
		sb.append(System.lineSeparator());
		sb.append("(ISEQUAL" + label_counter.toString() + ")");
		sb.append(System.lineSeparator());
		sb.append("D=-1");
		sb.append(System.lineSeparator());
		sb.append("@SP");
		sb.append(System.lineSeparator());
		sb.append("A=M");
		sb.append(System.lineSeparator());
		sb.append("M=D");
		sb.append(System.lineSeparator());
		sb.append("@END_EQ" + label_counter.toString());
		sb.append(System.lineSeparator());
		sb.append("0;JMP");
		sb.append(System.lineSeparator());
		sb.append("(END_EQ" + label_counter.toString() + ")");
		sb.append(System.lineSeparator());
		label_counter += 1;
	}

	private void neg() {
		sb.append("@SP");
		sb.append(System.lineSeparator());
		sb.append("A=M");
		sb.append(System.lineSeparator());
		sb.append("A=A-1");
		sb.append(System.lineSeparator());
		sb.append("M=-M");
		sb.append(System.lineSeparator());
	}

	private void set_PointerPlusOne() {
		sb.append("@SP");
		sb.append(System.lineSeparator());
		sb.append("M=M+1");
		sb.append(System.lineSeparator());
	}

	private void set_PointerMinusOne() {
		sb.append("@SP");
		sb.append(System.lineSeparator());
		sb.append("M=M-1");
		sb.append(System.lineSeparator());
	}

	private void push_Constant(Integer index) {
		sb.append("@" + index.toString().trim());
		sb.append(System.lineSeparator());
		sb.append("D=A");
		sb.append(System.lineSeparator());
		sb.append("@SP");
		sb.append(System.lineSeparator());
		sb.append("A=M");
		sb.append(System.lineSeparator());
		sb.append("M=D");
		sb.append(System.lineSeparator());
		sb.append("@SP");
		sb.append(System.lineSeparator());
		sb.append("M=M+1");
		sb.append(System.lineSeparator());
	}

	private void push_Argument(Integer index) {
		sb.append("@ARG");
		sb.append(System.lineSeparator());
		sb.append("D=M");
		sb.append(System.lineSeparator());
		sb.append("@" + index.toString());
		sb.append(System.lineSeparator());
		sb.append("D=D+A");
		sb.append(System.lineSeparator());
		sb.append("A=D");
		sb.append(System.lineSeparator());
		sb.append("D=M");
		sb.append(System.lineSeparator());
		sb.append("@SP");
		sb.append(System.lineSeparator());
		sb.append("A=M");
		sb.append(System.lineSeparator());
		sb.append("M=D");
		sb.append(System.lineSeparator());
		sb.append("@SP");
		sb.append(System.lineSeparator());
		sb.append("M=M+1");
		sb.append(System.lineSeparator());
	}

	private void pop_Argument(Integer index) {
		sb.append("@SP");
		sb.append(System.lineSeparator());
		sb.append("M=M-1");		
		// Kopiere Adresse des Fields in R13 
		sb.append(System.lineSeparator());
		sb.append("@ARG");
		sb.append(System.lineSeparator());
		sb.append("D=M");
		sb.append(System.lineSeparator());
		sb.append("@" + index.toString());
		sb.append(System.lineSeparator());
		sb.append("D=D+A");
		sb.append(System.lineSeparator());
		sb.append("@R13");
		sb.append(System.lineSeparator());
		sb.append("M=D");
		sb.append(System.lineSeparator());
		//Fülle SP in D
		sb.append("@SP");
		sb.append(System.lineSeparator());
		sb.append("A=M");
		sb.append(System.lineSeparator());
		sb.append("D=M");
		sb.append(System.lineSeparator());
		// Setze D in Adresse aus R13
		sb.append("@R13");
		sb.append(System.lineSeparator());
		sb.append("A=M");
		sb.append(System.lineSeparator());
		sb.append("M=D");
		sb.append(System.lineSeparator());
	}

	private void push_Local(Integer index) {
		sb.append("@LCL");
		sb.append(System.lineSeparator());
		sb.append("D=M");
		sb.append(System.lineSeparator());
		sb.append("@" + index.toString());
		sb.append(System.lineSeparator());
		sb.append("D=D+A");
		sb.append(System.lineSeparator());
		sb.append("A=D");
		sb.append(System.lineSeparator());
		sb.append("D=M");
		sb.append(System.lineSeparator());
		sb.append("@SP");
		sb.append(System.lineSeparator());
		sb.append("A=M");
		sb.append(System.lineSeparator());
		sb.append("M=D");
		sb.append(System.lineSeparator());
		sb.append("@SP");
		sb.append(System.lineSeparator());
		sb.append("M=M+1");
		sb.append(System.lineSeparator());
	}

	private void pop_Local(Integer index) {
		sb.append("@SP");
		sb.append(System.lineSeparator());
		sb.append("M=M-1");		
		// Kopiere Adresse des Fields in R13 
		sb.append(System.lineSeparator());
		sb.append("@LCL");
		sb.append(System.lineSeparator());
		sb.append("D=M");
		sb.append(System.lineSeparator());
		sb.append("@" + index.toString());
		sb.append(System.lineSeparator());
		sb.append("D=D+A");
		sb.append(System.lineSeparator());
		sb.append("@R13");
		sb.append(System.lineSeparator());
		sb.append("M=D");
		sb.append(System.lineSeparator());
		//Fülle SP in D
		sb.append("@SP");
		sb.append(System.lineSeparator());
		sb.append("A=M");
		sb.append(System.lineSeparator());
		sb.append("D=M");
		sb.append(System.lineSeparator());
		// Setze D in Adresse aus R13
		sb.append("@R13");
		sb.append(System.lineSeparator());
		sb.append("A=M");
		sb.append(System.lineSeparator());
		sb.append("M=D");
		sb.append(System.lineSeparator());
	}

	private void push_This(Integer index) {
		sb.append("@THIS");
		sb.append(System.lineSeparator());
		sb.append("D=M");
		sb.append(System.lineSeparator());
		sb.append("@" + index.toString());
		sb.append(System.lineSeparator());
		sb.append("D=D+A");
		sb.append(System.lineSeparator());
		sb.append("A=D");
		sb.append(System.lineSeparator());
		sb.append("D=M");
		sb.append(System.lineSeparator());
		sb.append("@SP");
		sb.append(System.lineSeparator());
		sb.append("A=M");
		sb.append(System.lineSeparator());
		sb.append("M=D");
		sb.append(System.lineSeparator());
		sb.append("@SP");
		sb.append(System.lineSeparator());
		sb.append("M=M+1");
		sb.append(System.lineSeparator());
	}

	private void pop_This(Integer index) {
		sb.append("@SP");
		sb.append(System.lineSeparator());
		sb.append("M=M-1");		
		// Kopiere Adresse des Fields in R13 
		sb.append(System.lineSeparator());
		sb.append("@THIS");
		sb.append(System.lineSeparator());
		sb.append("D=M");
		sb.append(System.lineSeparator());
		sb.append("@" + index.toString());
		sb.append(System.lineSeparator());
		sb.append("D=D+A");
		sb.append(System.lineSeparator());
		sb.append("@R13");
		sb.append(System.lineSeparator());
		sb.append("M=D");
		sb.append(System.lineSeparator());
		//Fülle SP in D
		sb.append("@SP");
		sb.append(System.lineSeparator());
		sb.append("A=M");
		sb.append(System.lineSeparator());
		sb.append("D=M");
		sb.append(System.lineSeparator());
		// Setze D in Adresse aus R13
		sb.append("@R13");
		sb.append(System.lineSeparator());
		sb.append("A=M");
		sb.append(System.lineSeparator());
		sb.append("M=D");
		sb.append(System.lineSeparator());
	}

	private void push_That(Integer index) {
		sb.append("@THAT");
		sb.append(System.lineSeparator());
		sb.append("D=M");
		sb.append(System.lineSeparator());
		sb.append("@" + index.toString());
		sb.append(System.lineSeparator());
		sb.append("D=D+A");
		sb.append(System.lineSeparator());
		sb.append("A=D");
		sb.append(System.lineSeparator());
		sb.append("D=M");
		sb.append(System.lineSeparator());
		sb.append("@SP");
		sb.append(System.lineSeparator());
		sb.append("A=M");
		sb.append(System.lineSeparator());
		sb.append("M=D");
		sb.append(System.lineSeparator());
		sb.append("@SP");
		sb.append(System.lineSeparator());
		sb.append("M=M+1");
		sb.append(System.lineSeparator());
	}

	private void pop_That(Integer index) {
		sb.append("@SP");
		sb.append(System.lineSeparator());
		sb.append("M=M-1");		
		// Kopiere Adresse des Fields in R13 
		sb.append(System.lineSeparator());
		sb.append("@THAT");
		sb.append(System.lineSeparator());
		sb.append("D=M");
		sb.append(System.lineSeparator());
		sb.append("@" + index.toString());
		sb.append(System.lineSeparator());
		sb.append("D=D+A");
		sb.append(System.lineSeparator());
		sb.append("@R13");
		sb.append(System.lineSeparator());
		sb.append("M=D");
		sb.append(System.lineSeparator());
		//Fülle SP in D
		sb.append("@SP");
		sb.append(System.lineSeparator());
		sb.append("A=M");
		sb.append(System.lineSeparator());
		sb.append("D=M");
		sb.append(System.lineSeparator());
		// Setze D in Adresse aus R13
		sb.append("@R13");
		sb.append(System.lineSeparator());
		sb.append("A=M");
		sb.append(System.lineSeparator());
		sb.append("M=D");
		sb.append(System.lineSeparator());
	}

	private void push_Pointer(Integer index) {
		if (index == 0)
			sb.append("@THIS");
		else if (index == 1)
			sb.append("@THAT");
		sb.append(System.lineSeparator());
		sb.append("D=M");
		sb.append(System.lineSeparator());
		sb.append("@SP");
		sb.append(System.lineSeparator());
		sb.append("A=M");
		sb.append(System.lineSeparator());
		sb.append("M=D");
		sb.append(System.lineSeparator());
		sb.append("@SP");
		sb.append(System.lineSeparator());
		sb.append("M=M+1");
		sb.append(System.lineSeparator());
	}

	private void pop_Pointer(Integer index) {
		sb.append("@SP");
		sb.append(System.lineSeparator());
		sb.append("M=M-1");
		sb.append(System.lineSeparator());
		sb.append("A=M");
		sb.append(System.lineSeparator());
		sb.append("D=M");
		sb.append(System.lineSeparator());
		if (index == 0)
			sb.append("@THIS");
		else if (index == 1)
			sb.append("@THAT");
		sb.append(System.lineSeparator());
		sb.append("M=D");
		sb.append(System.lineSeparator());
	}

	private void push_Static(Integer index) {
		sb.append("@" + fileName.trim() + index.toString());
		sb.append(System.lineSeparator());
		sb.append("D=M");
		sb.append(System.lineSeparator());
		sb.append("@SP");
		sb.append(System.lineSeparator());
		sb.append("A=M");
		sb.append(System.lineSeparator());
		sb.append("M=D");
		sb.append(System.lineSeparator());
		sb.append("@SP");
		sb.append(System.lineSeparator());
		sb.append("M=M+1");
		sb.append(System.lineSeparator());
	}

	private void pop_Static(Integer index) {
		sb.append("@SP");
		sb.append(System.lineSeparator());
		sb.append("M=M-1");
		sb.append(System.lineSeparator());
		sb.append("A=M");
		sb.append(System.lineSeparator());
		sb.append("D=M");
		sb.append(System.lineSeparator());
		sb.append("@" + fileName.trim() + index.toString());
		sb.append(System.lineSeparator());
		sb.append("M=D");
		sb.append(System.lineSeparator());
	}

	private void push_Temp(Integer index) {
		index += 5;
		sb.append("@R" + index.toString());
		sb.append(System.lineSeparator());
		sb.append("D=M");
		sb.append(System.lineSeparator());
		sb.append("@SP");
		sb.append(System.lineSeparator());
		sb.append("A=M");
		sb.append(System.lineSeparator());
		sb.append("M=D");
		sb.append(System.lineSeparator());
		sb.append("@SP");
		sb.append(System.lineSeparator());
		sb.append("M=M+1");
		sb.append(System.lineSeparator());
	}

	private void pop_Temp(Integer index) {
		index += 5;
		sb.append("@SP");
		sb.append(System.lineSeparator());
		sb.append("M=M-1");
		sb.append(System.lineSeparator());
		sb.append("A=M");
		sb.append(System.lineSeparator());
		sb.append("D=M");
		sb.append(System.lineSeparator());
		sb.append("@R" + index.toString());
		sb.append(System.lineSeparator());
		sb.append("M=D");
		sb.append(System.lineSeparator());
	}
}
