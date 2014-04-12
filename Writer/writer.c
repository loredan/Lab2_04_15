#include "stdio.h"
#include "stdlib.h"

struct StorageStructure
{
	int intVar;
	char* charArrayVar;
	double doubleVar;
};

int main(int argc, char** argv)
{
	FILE* file = fopen("../input", "wb");
	struct StorageStructure temp = {1,"abc",1.01};
	fwrite(&temp, 1, sizeof(struct StorageStructure), file);
	fclose(file);
}