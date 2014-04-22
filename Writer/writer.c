#include "stdio.h"
#include "stdlib.h"

typedef struct
{
	double base;
	int power;
} Involution;

int main(int argc, char** argv)
{
	if(argc%2==1 || argc<2)
	{
		printf("Incorrect arguments");
		return -1;
	}
	FILE* file = fopen(argv[1], "wb");
	if(!file)
	{
		printf("Can't open file");
		return -2;
	}
	int i;
	for(i = 2; i<argc; i+=2)
	{
		printf("%s, %s\n", argv[i], argv[i+1]);
		Involution temp = {atof(argv[i]),atoi(argv[i+1])};
		fwrite(&temp, 1, sizeof(temp), file);
	}
	fclose(file);
}