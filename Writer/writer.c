#include "stdio.h"
#include "stdlib.h"

struct StorageStructure
{
	int intVar;
	double doubleVar;
};

int main(int argc, char** argv)
{
	FILE* file = fopen("../input", "wb");
	struct StorageStructure temp = {0,3.01};
	fwrite(&temp, 1, sizeof(temp), file);
	fclose(file);
}