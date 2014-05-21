package Expression;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileInputStream;

import Expression.ArithmeticExpression;

public class InvolutionExpression extends ArithmeticExpression
{
	private int mPower;

	public InvolutionExpression(String fileName) throws FileNotFoundException, IOException, Throwable
	{
		FileInputStream is = new FileInputStream(fileName);

		long baseRawLongBits = 0;
		for(int i = 0; i < Double.SIZE/8; i++)
		{
			long temp = Byte.valueOf((byte) is.read()).longValue();
			baseRawLongBits = (baseRawLongBits >>> 8) | (temp << Double.SIZE-8);
		}

		double base = Double.longBitsToDouble(baseRawLongBits);

		int power = 0;
		for(int i = 0; i < Integer.SIZE/8; i++)
		{
			int temp = is.read();
			power = (power >>> 8) | (temp << Integer.SIZE-8);
		}

		is.skip(4);

		initInvolution(base, power);
	}

	public InvolutionExpression(double constant)
	{
		super(constant);
	}

	public InvolutionExpression(double base, int power)
	{
		initInvolution(base, power);
	}

	private void initInvolution(double base, int power)
	{
		if(power==0)
		{
			initConstant(1);
			return;
		}
		if(power==1)
		{
			initConstant(base);
			return;
		}
		StringBuilder builder = new StringBuilder();
		builder.append(base);
		mLExpression = new InvolutionExpression(base);
		mRExpression = new InvolutionExpression(base, power-1);
		mType = TYPE_MULTIPLICATION;
		mPower = power;
	}

	public String getExpression()
	{
		return "^ " + String.valueOf(mLExpression.getExpression()) + " " + String.valueOf(mPower);
	}
}