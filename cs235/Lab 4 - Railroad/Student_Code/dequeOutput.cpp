#include "dequeOutput.h"

	myDequeOutput::myDequeOutput(){}
	myDequeOutput::~myDequeOutput(){}

	void myDequeOutput::addLeft(int x){
		dGarageOutput.insertHead(x);
	}

	void myDequeOutput::addRight(int x){
		dGarageOutput.insertTail(x);
	}

	void myDequeOutput::removeLeft(){
		// remove at index 0
		dGarageOutput.remove(dGarageOutput.at(0));
	}
	/*void myDequeOutput::removeRight(){
		// remove tail
		dGarageOutput.remove(dGarageOutput.at(dGarageOutput.size() - 1));
	}*/

	int myDequeOutput::topLeft(){
		return dGarageOutput.at(0);
	}
	int myDequeOutput::topRight(){
		return dGarageOutput.at(size()-1);
	}

	int myDequeOutput::size(){
		return dGarageOutput.size();
	}

	bool myDequeOutput::hasDup(int x){
		return dGarageOutput.hasDup(x);
	}
