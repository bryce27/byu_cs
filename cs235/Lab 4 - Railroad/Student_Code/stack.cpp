#include "stack.h"

myStack::myStack(){}
myStack::~myStack(){}

void myStack::push(int x){
	sGarage.insertTail(x);
}

void myStack::pop(){
	sGarage.remove(sGarage.at(size()-1));
}

int myStack::top(){
	return sGarage.at(size()-1);
}

int myStack::size(){
	return sGarage.size();
}

bool myStack::hasDup(int x){
	return sGarage.hasDup(x);
}
