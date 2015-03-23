#include "LinkedList.h"

class myDequeOutput {
public:

	myDequeOutput();
	~myDequeOutput();

	LinkedList<int> dGarageOutput;

	void addLeft(int);
	void addRight(int);

	void removeLeft();
	//void removeRight();

	int topLeft();
	int topRight();

	int size();

	bool hasDup(int);
};
