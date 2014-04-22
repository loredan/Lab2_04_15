package MainProgram;

import java.lang.Throwable;
import java.util.Vector;

class ArithmeticExpression
{
	private static int ASC0 = 48;

	public static final int TYPE_CONSTANT = 0;
	public static final int TYPE_ADDITION = 1;
	public static final int TYPE_SUBSTRACTION = 2;
	public static final int TYPE_MULTIPLICATION = 3;
	public static final int TYPE_DIVISION = 4;

	protected int mType;
	private double mConstant;
	protected ArithmeticExpression mLExpression;
	protected ArithmeticExpression mRExpression;

	private class Operation
	{
		public int mType;
		public int mLevel;
		public int mPosition;

		public Operation(int type, int level, int position)
		{
			mType = type;
			mLevel = level;
			mPosition = position;
		}
	}

	public ArithmeticExpression()
	{

	}

	public ArithmeticExpression(double constant)
	{
		initConstant(constant);
	}

	public ArithmeticExpression(String expression) throws Throwable
	{
		System.console().writer().println(expression);
		if(!check(expression))
			throw new Throwable("Incorrect expression");
		parse(expression);
	}

	public double calculate() throws Throwable
	{
		switch(mType)
		{
			case TYPE_CONSTANT:
				return mConstant;
			case TYPE_ADDITION:
				return mLExpression.calculate() + mRExpression.calculate();
			case TYPE_SUBSTRACTION:
				return mLExpression.calculate() - mRExpression.calculate();
			case TYPE_MULTIPLICATION:
				return mLExpression.calculate() * mRExpression.calculate();
			case TYPE_DIVISION:
				if(mRExpression.calculate() == Double.parseDouble("0"))
					throw new Throwable("Calculation error: division by zero");
				return mLExpression.calculate() / mRExpression.calculate();
		}
		throw new Throwable("Calculation error: unknown type");
	}

	protected void initConstant(double constant)
	{
		mType = TYPE_CONSTANT;
		mConstant = constant;
	}

	protected boolean check(String expression)
	{
		int level = 0;
		char prevSymbol = '(';
		char symbol = '!';
		for(int i = 0; i < expression.length(); i++)
		{
			symbol = expression.charAt(i);
			if(symbol>=ASC0 && symbol<ASC0+10)
				if(prevSymbol==')')
					return false;
				else;
			else if(symbol=='+' || symbol=='*' || symbol=='/')
				if(prevSymbol=='+' || prevSymbol=='-' || prevSymbol=='*' || prevSymbol=='/' || prevSymbol=='(' || prevSymbol=='.')
					return false;
				else;
			else if(symbol=='-')
				if(prevSymbol=='+' || prevSymbol=='-' || prevSymbol=='*' || prevSymbol=='/' || prevSymbol=='.')
					return false;
				else;
			else if(symbol==')')
				if(prevSymbol=='+' || prevSymbol=='-' || prevSymbol=='*' || prevSymbol=='/' || prevSymbol=='.')
					return false;
				else
					level--;
			else if(symbol=='(')
				if(prevSymbol>=ASC0 && prevSymbol<ASC0+10 || prevSymbol=='.')
					return false;
				else
					level++;
			else if(symbol=='.')
				if(prevSymbol<ASC0 || prevSymbol>=ASC0+10)
					return false;
				else;
			else
				return false;
			if(level<0)
				return false;
			prevSymbol = symbol;
		}
		if(symbol=='+' || symbol=='-' || symbol=='*' || symbol=='/' || symbol=='!')
			return false;
		return true;
	}

	protected void parse(String expression) throws Throwable
	{
		Vector<Operation> operations = new Vector<Operation>();
		int level = 0;
		for(int i = 0; i < expression.length(); i++)
		{
			char symbol = expression.charAt(i);
			if (symbol == '(')
				level++;
			else if (symbol == ')')
				level--;
			else if (symbol=='+' || symbol=='-' || symbol=='*' || symbol=='/')
			{
				int type;
				switch (symbol)
				{
					case '+':
						type = TYPE_ADDITION;
						break;
					case '-':
						type = TYPE_SUBSTRACTION;
						break;
					case '*':
						type = TYPE_MULTIPLICATION;
						break;
					case '/':
						type = TYPE_DIVISION;
						break;
					default:
						throw new Throwable("Parse error");
				}
				operations.add(new Operation(type, level, i));
				System.console().writer().println(Integer.toString(type) + level + i);
			}
		}
		if(operations.size() == 0)
		{
			mType = TYPE_CONSTANT;
			while(expression.charAt(0) == '(' && expression.charAt(expression.length()-1) == ')')
				expression = expression.substring(1, expression.length()-1);
			mConstant = Double.parseDouble(expression);
			return;
		}
		int minLevel = operations.firstElement().mLevel;
		for(int i = 1; i < operations.size(); i++)
			minLevel = operations.get(i).mLevel<minLevel ? operations.get(i).mLevel : minLevel;
		Operation operation = null;
		for(int i = 0; i < operations.size(); i++)
			if(operations.get(i).mLevel == minLevel)
				if(operation == null)
					operation = operations.get(i);
				else
					if((operation.mType==TYPE_MULTIPLICATION || operation.mType==TYPE_DIVISION) && (operations.get(i).mType==TYPE_ADDITION || operations.get(i).mType==TYPE_SUBSTRACTION))
						operation = operations.get(i);
		System.console().writer().println(Integer.toString(operation.mType) + operation.mLevel + operation.mPosition);
		String lExpression = expression.substring(minLevel, operation.mPosition);
		String rExpression = expression.substring(operation.mPosition+1, expression.length()-minLevel);
		mLExpression = new ArithmeticExpression(lExpression);
		mRExpression = new ArithmeticExpression(rExpression);
		mType = operation.mType;
	}
}