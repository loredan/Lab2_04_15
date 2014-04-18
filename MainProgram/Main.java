import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.Byte;

class Main
{
	public static void main(String[] args)
	{
		FileInputStream is = null;
		try
		{
			is = new FileInputStream("./../input");
		}
		catch (FileNotFoundException e)
		{
			System.console().writer().println("File not found");
		}

		int intVar = 0;
		for(int i = 0; i < Integer.SIZE/8; i++)
		{
			try
			{
				int temp = is.read();
				intVar = (intVar >>> 8) | (temp << Integer.SIZE-8);
			}
			catch(IOException e)
			{
				System.console().writer().println("IOException");
			}
		}

		long doubleVarRawLongBits = 0;
		for(int i = 0; i < Double.SIZE/8; i++)
		{
			try
			{
				long temp = Byte.valueOf((byte) is.read()).longValue();
				System.console().writer().println(Long.toHexString(doubleVarRawLongBits>>>8) + " " + Long.toHexString(temp << 8*Double.SIZE-1));
				doubleVarRawLongBits = (doubleVarRawLongBits >>> 8) | (temp << Double.SIZE-8);
			}
			catch (IOException e)
			{
				System.console().writer().println("IOException");
			}
		}
		try
		{
			doubleVarRawLongBits |= Double.doubleToRawLongBits(Byte.valueOf((byte) is.read()).doubleValue()) << 24;
			doubleVarRawLongBits |= Double.doubleToRawLongBits(Byte.valueOf((byte) is.read()).doubleValue()) << 16;
			doubleVarRawLongBits |= Double.doubleToRawLongBits(Byte.valueOf((byte) is.read()).doubleValue()) << 8;
			doubleVarRawLongBits |= Double.doubleToRawLongBits(Byte.valueOf((byte) is.read()).doubleValue());
		}
		catch (IOException e)
		{
			System.console().writer().println("IOException");
		}

		double doubleVar = Double.longBitsToDouble(doubleVarRawLongBits);

		System.console().writer().println(intVar);
		System.console().writer().println(doubleVar);
		System.console().writer().println(Long.toHexString(doubleVarRawLongBits));
	}
}