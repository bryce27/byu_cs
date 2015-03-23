#include "LinkedList.h"

class myStack {
public:

	myStack();
	~myStack();

	LinkedList<int> sGarage;

	void push(int);

	void pop();

	int top();

	int size();

	bool hasDup(int);
};
