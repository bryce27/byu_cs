#include "dequeInput.h"

	myDequeInput::myDequeInput(){}
	myDequeInput::~myDequeInput(){}

	void myDequeInput::addLeft(int x){
		dGarageInput.insertHead(x);
	}

	/*void myDequeInput::addRight(int x){
		dGarageInput.insertTail(x);
	}*/

	void myDequeInput::removeLeft(){
		// remove at index 0
		dGarageInput.remove(dGarageInput.at(0));
	}
	void myDequeInput::removeRight(){
		// remove tail
		dGarageInput.remove(dGarageInput.at(dGarageInput.size() - 1));
	}

	int myDequeInput::topLeft(){
		return dGarageInput.at(0);
	}
	int myDequeInput::topRight(){
		return dGarageInput.at(size()-1);
	}

	int myDequeInput::size(){
		return dGarageInput.size();
	}

	bool myDequeInput::hasDup(int x){
		return dGarageInput.hasDup(x);
	}
