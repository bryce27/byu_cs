#include "queue.h"

	myQueue::myQueue(){}
	myQueue::~myQueue(){}

	void myQueue::add(int x){
		qGarage.insertHead(x);
	}

	void myQueue::remove(){
		qGarage.remove(qGarage.at(qGarage.size() - 1));
	}

	int myQueue::top(){
		return qGarage.at(size()-1);
	}

	int myQueue::size(){
		return qGarage.size();
	}

	bool myQueue::hasDup(int x){
		return qGarage.hasDup(x);
	}
