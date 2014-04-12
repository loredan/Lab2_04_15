class StorageStructure
{
	public int intVar;
	public String stringVar;
	public char[] charArrayVar;
	public double doubleVar;

	public StorageStructure(byte[] byteArray)
	{
		for(int i = 0; i < 4; i++)
		{
			intVar = Byte.valueOf(byteArray[i]).intValue();
			intVar = intVar << 8;
		}
	}
}