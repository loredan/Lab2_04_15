package MainProgram;

import MainProgram.ArithmeticExpression;

class InvolutionExpression extends ArithmeticExpression
{
	public InvolutionExpression(double constant)
	{
		initConstant(constant);
	}

	public InvolutionExpression(double base, int power)
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
	}
}