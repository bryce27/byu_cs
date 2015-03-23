#include "deque.h"

	myDeque::myDeque(){}
	myDeque::~myDeque(){}

	void myDeque::addLeft(int x){
		dGarage.insertHead(x);
	}

	void myDeque::addRight(int x){
		dGarage.insertTail(x);
	}

	void myDeque::removeLeft(){
		// remove at index 0
		dGarage.remove(dGarage.at(0));
	}
	void myDeque::removeRight(){
		// remove tail
		dGarage.remove(dGarage.at(dGarage.size() - 1));
	}

	int myDeque::topLeft(){
		return dGarage.at(0);
	}
	int myDeque::topRight(){
		return dGarage.at(size()-1);
	}

	int myDeque::size(){
		return dGarage.size();
	}

	bool myDeque::hasDup(int x){
		return dGarage.hasDup(x);
	}
