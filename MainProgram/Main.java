import java.io.FileInputStream;
import java.io.FileNotFoundException;

class Main
{
	public static void main(String[] args)
	{
		try
		{
			FileInputStream is = new FileInputStream("../input");
		}
		catch (FileNotFoundException e)
		{
			System.console().writer().println("File not found");
		}
	}
}