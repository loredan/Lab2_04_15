package TestPackage;

import java.io.RandomAccessFile;
import java.lang.StringBuilder;
import java.lang.Math;
import java.util.ArrayList;
import java.io.IOException;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

import Expression.ArithmeticExpression;
import Expression.InvolutionExpression;

public class Test
{
	private static ArrayList<ArrayList<String>> mCsv;
 	private static int mTotalTests;
	private static int mTestsPassed;

	public static final int TYPE_UNKNOWN = 0;
	public static final int TYPE_ARITHMETIC = 1;
	public static final int TYPE_INVOLUTION = 2;
	public static final int TYPE_INVOLUTION_FILE = 3;

	public static void main(String[] args)
	{
		if(args.length!=1)
		{
			System.console().writer().println("Incorrect arguments");
			return;
		}

		try
		{
			readFile(args[0]);
		}
		catch (Throwable e)
		{
			e.printStackTrace();
			return;
		}

		for(int i = 1; i < mCsv.size(); i++)
		{
			String testType = mCsv.get(i).get(3);
			String testInput = mCsv.get(i).get(4);
			String testExpectedOutput = mCsv.get(i).get(5);
			String testOutput = new String();
			double testOutputRaw = 0;
			boolean testResult;
			ArithmeticExpression expression;

			try
			{
				int separator = testType.indexOf(":");
				String generalType = testType.substring(0, separator);
				String subType = testType.substring(separator+1);
				separator = subType.indexOf(":");
				String inputType = subType.substring(0, separator);
				String outputType = subType.substring(separator+1);

				if(generalType.equals("ArithmeticExpression"))
					expression = new ArithmeticExpression(testInput);
				else if(generalType.equals("InvolutionExpression"))
					if(inputType.equals("string"))
					{
						separator = testInput.indexOf(" ");
						if(separator==-1)
						{
							throw new Throwable("Incorrect input");
						}
						double base = Double.parseDouble(testInput.substring(0, separator));
						int power = Integer.parseInt(testInput.substring(separator+1));
						expression = new InvolutionExpression(base, power);
					}
					else if(inputType.equals("file"))
						expression = new InvolutionExpression(testInput);
					else
						throw new Throwable("Unknown input type");
				else
					throw new Throwable("Unknown expression type");

				if(outputType.equals("calculate"))
				{
					testOutputRaw = expression.calculate();
					mCsv.get(i).set(6, Double.toString(testOutputRaw));
					testResult = true;
				}
				else if(outputType.equals("prefix"))
				{
					mCsv.get(i).set(6, expression.getExpression());
					testResult = false;
				}
				else
					throw new Throwable("Unknown output type");
			}
			catch(Throwable e)
			{
				mCsv.get(i).set(6, e.toString());
				testResult = false;
			}

			if(testResult)
			{
				if(Math.nextAfter(testOutputRaw, Double.parseDouble(testExpectedOutput)) >= Double.parseDouble(testExpectedOutput) ^ testOutputRaw < Double.parseDouble(testExpectedOutput))
				{
					mCsv.get(i).set(7, "Success");
					mTestsPassed++;
				}
				else
				{
					mCsv.get(i).set(7, "Fail");
				}
			}
			else
			{
				if(mCsv.get(i).get(6).contains(testExpectedOutput))
				{
					mCsv.get(i).set(7, "Success");
					mTestsPassed++;
				}
				else
					mCsv.get(i).set(7, "Fail");
			}
			mTotalTests++;
		}

		try
		{
			writeToFile(args[0]);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return;
		}

		System.console().writer().println("Summary");
		System.console().writer().println("Total tests: " + mTotalTests);
		System.console().writer().println("Passed: " + mTestsPassed);
		System.console().writer().println("Failed: " + (mTotalTests - mTestsPassed));
	}

	private static void readFile(String fileName) throws FileNotFoundException, IOException
	{
		mCsv = new ArrayList<ArrayList<String>>();
		FileReader reader = new FileReader(fileName);
		ArrayList<String> row = new ArrayList<String>();
		StringBuilder builder = new StringBuilder();
		char symbol;
		while(true)
		{
			symbol = (char) reader.read();
			switch (symbol)
			{
				case ',':
					row.add(builder.toString());
					builder = new StringBuilder();
					break;
				case '\n':
					row.add(builder.toString());
					builder = new StringBuilder();
					mCsv.add(row);
					row = new ArrayList<String>();
					break;
				case (char) -1:
					reader.close();
					return;
				default:
					builder.append(symbol);
			}
		}
	}

	private static void writeToFile(String fileName) throws IOException
	{
		FileWriter writer = new FileWriter(fileName);
		for(ArrayList<String> row : mCsv)
		{
			for(String cell : row)
			{
				if(row.indexOf(cell)!=0)
					writer.write(',');
				writer.write(cell, 0, cell.length());
			}
			writer.write('\n');
		}
		writer.close();
	}

	private static ArithmeticExpression generateExpression(String type, String input)
	{
		int separator = type.indexOf("(");
		String generalType = type.substring(0, separator);
		int endSeparator = separator;
		int level = 1;
		while(level!=1 || type.charAt(separator) != ')')
		{
			if(type.charAt(endSeparator)=='(')
				level++;
			else if(type.charAt(endSeparator)==')')
				level--;
			endSeparator++;
		}
		String inputType = type.substring(separator+1, endSeparator);

		if(generalType.equals("ArithmeticExpression"))
			if(inputType.equals("string"))
				expression = new ArithmeticExpression(input);
			else
				throw new Throwable("Unknown input type");
		else if(generalType.equals("InvolutionExpression"))
			if(inputType.equals("string"))
			{
				separator = input.indexOf(" ");
				if(separator==-1)
				{
					throw new Throwable("Incorrect input");
				}
				double base = Double.parseDouble(input.substring(0, separator));
				int power = Integer.parseInt(input.substring(separator+1));
				expression = new InvolutionExpression(base, power);
			}
			else if(inputType.equals("file"))
				expression = new InvolutionExpression(input);
			else
				throw new Throwable("Unknown input type");
		else if(generalType.equals("TwoExpressions"))
		{
			separator = inputType.indexOf("{");
		}
		else
			throw new Throwable("Unknown expression type");

		if(outputType.equals("calculate"))
		{
			testOutputRaw = expression.calculate();
			mCsv.get(i).set(6, Double.toString(testOutputRaw));
			testResult = true;
		}
		else if(outputType.equals("prefix"))
		{
			mCsv.get(i).set(6, expression.getExpression());
			testResult = false;
		}
		else
			throw new Throwable("Unknown output type");
	}
}