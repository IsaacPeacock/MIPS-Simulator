import java.io.*;
import java.util.*;

// This is good for collaborative working but we'll probably want to run it in our own environment because of speed.
class Simulator 
{
	// Stores words as memory
	static String[] memory;
	static int maxMemorySize = 1000;

	// Program Counter
	static int PC;

	// Standard MIPS Registers
	static int[] registers;

	// Placeholders
	static String opcode, rs, rt, rd, shamt, funct, immediate;

	// Main Method
	public static void main(String[] args) throws Exception 
    {
		// Instantiate the memory location
		memory = new String[maxMemorySize];

		// Create new Registers
		registers = new int[32];

		// ASK FOR WHAT FILE TO LOAD
		String path = "testCode.txt";

		loadFile(path);
		System.out.println(PC);
		System.out.println(memory[PC]);
		while (memory[PC] != null) 
        {
			execute();
			PC++;
		}

		printRegisterContents();
	}

	// Load File
	static void loadFile(String path) throws Exception 
    {
		// Reading the file and storing the instructions in the array
		File file = new File(path);
		Scanner fileRead = new Scanner(file);
		int i;

		if (fileRead.hasNextLine()) 
        {
			i = fileRead.nextInt();
			PC = i;
			fileRead.nextLine();

			if (i >= maxMemorySize)
				throw new Exception("Starting location out of bounds");
		} 
        
        else 
        {
			throw new Exception("This file is empty.");
		}

		while (fileRead.hasNextLine()) 
        {
			System.out.println("Putting in memory " + i);
			memory[i] = fileRead.nextLine();
			i++;
		}
	}

	// Print out 200 memory locations with 4 locations per line
	static void memoryDump(int startingAddy) 
    {
		for (int i = 0; i < 50; i++) 
        {
			System.out.printf("%5d:\t %32s\t %32s\t %32s\t %32s\t\n", startingAddy, memory[startingAddy],
					memory[startingAddy + 1], memory[startingAddy + 2], memory[startingAddy + 3]);

			startingAddy += 4;
		}
	}

	// Print Register Contents
	static void printRegisterContents() 
    {
		for (int i = 0; i < registers.length; i++) 
        {
			System.out.printf("R%d: %5d\n", i, registers[i]);
		}
	}

	static void execute() throws Exception 
    {
		String instruction = memory[PC];
		opcode = instruction.substring(0, 6);
		System.out.println("OPCODE: " + opcode);

		rs = instruction.substring(6, 11);
		int rsVal = Integer.parseInt(rs, 2);
		System.out.println("RS: " + rs);
		System.out.println("RS: " + rsVal);

		rt = instruction.substring(11, 16);
		int rtVal = Integer.parseInt(rt, 2);
		System.out.println("RT: " + rt);
		System.out.println("RT: " + rtVal);

		rd = instruction.substring(16, 21);
		int rdVal = Integer.parseInt(rd, 2);

		immediate = instruction.substring(16, 32);
		int immVal = Integer.parseInt(immediate, 2);
		System.out.println("Immediate: " + immediate);
		System.out.println("Immediate: " + immVal);

		// TYPE R INSTRUCTION
		if (opcode.equals("000000")) {
			shamt = instruction.substring(21, 26);
			int shamtVal = Integer.parseInt(shamt, 2);

			funct = instruction.substring(26, 32);

			switch (funct) {
				// ADD RS, RT, RD
				case "100000":
					add(rsVal, rtVal, rdVal);
					break;

				// SUB

			}
		}

		else // I and J's
		{
			switch (opcode) {
				case "001000":
					// ADDI
					addi(rtVal, rsVal, immVal);
					break;
			}
		}

	}

	// MIPS METHOD CALLS

	// ADD RD, RS, RT
	static void add(int rd, int rs, int rt) {
		registers[rd] = registers[rs] + registers[rt];
	}

	// ADDI RT, RS, Immediate
	static void addi(int rt, int rs, int imm) {

		if (imm > 32767) {
			imm = -(65536 - imm); // Convert to signed
		}
		registers[rt] = registers[rs] + imm;

	}

    static void sub(int rd, int rs, int rt)
    {
        registers[rd] = registers[rs] - registers[rt];
    }

    static void and(int rd, int rs, int rt)
    {
        registers[rd] = registers[rs] & registers[rt];
    }

    static void andi(int rt, int rs, int imm)
    {
        if (imm > 32767) 
        {
			imm = -(65536 - imm); // Convert to signed
		}
        registers[rt] = registers[rs] & imm;
    }

    static void or(int rd, int rs, int rt)
    {
        registers[rd] = registers[rs] | registers[rt];
    }

    static void ori(int rt, int rs, int imm)
    {
        if (imm > 32767) 
        {
			imm = -(65536 - imm); // Convert to signed
		}
        registers[rt] = registers[rs] | imm;
    }

    static void nor(int rd, int rs, int rt)
    {
        registers[rd] = ~(registers[rs] | registers[rt]);
    }

    static void xor(int rd, int rs, int rt)
    {
        registers[rd] = registers[rs] ^ registers[rt];
    }

    static void xori(int rt, int rs, int imm)
    {
        if (imm > 32767) 
        {
			imm = -(65536 - imm); // Convert to signed
		}
        registers[rt] = registers[rs] ^ imm;
    }
}