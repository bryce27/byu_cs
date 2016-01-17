#include "LinkedList.h"

class myDequeInput {
public:

	myDequeInput();
	~myDequeInput();

	LinkedList<int> dGarageInput;

	void addLeft(int);
	//void addRight(int);

	void removeLeft();
	void removeRight();

	int topLeft();
	int topRight();

	int size();

	bool hasDup(int);

};
