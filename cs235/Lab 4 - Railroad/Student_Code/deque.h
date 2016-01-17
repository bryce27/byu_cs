#include "LinkedList.h"

class myDeque {
public:

	myDeque();
	~myDeque();

	LinkedList<int> dGarage;

	void addLeft(int);
	void addRight(int);

	void removeLeft();
	void removeRight();

	int topLeft();
	int topRight();

	int size();

	bool hasDup(int);
};
