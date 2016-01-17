#pragma once
//#include "LinkedList.h"


template<class T>
class Node {



	// Node(T value)

	//public:
	// getters and setters functions that allow me to change the private variables

	//what type does this output?

public:

	T data;
	Node<T>* next;

	Node(T value, Node<T>* n = NULL) {
		data = value;
		next = n;
	}

	T getData(){
		return data;
	}

	void setData(T newdata){
		data = newdata;
	}

	Node<T>* getNext(){
		return next;
	}

	void setNext(Node<T>* n){
		next = n;
	}
};
