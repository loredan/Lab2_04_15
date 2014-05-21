#include "stdio.h"
#include "stdlib.h"

typedef struct
{
	double base;
	int power;
} Involution;

int main(int argc, char** argv)
{
	if(argc!=4)
	{
		printf("Incorrect arguments\n");
		return -1;
	}
	FILE* file = fopen(argv[1], "wb");
	if(!file)
	{
		printf("Can't open file\n");
		return -2;
	}

	Involution temp = {atof(argv[2]),atoi(argv[3])};
	fwrite(&temp, 1, sizeof(temp), file);

	fclose(file);
}