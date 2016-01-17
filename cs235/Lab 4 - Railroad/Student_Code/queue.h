#include "LinkedList.h"

class myQueue {
public:

	myQueue();
	~myQueue();

	LinkedList<int> qGarage;

	void add(int);

	void remove();

	int top();

	int size();

	bool hasDup(int);
};
