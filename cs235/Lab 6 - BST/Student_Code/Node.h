#pragma once
#include "NodeInterface.h"

class Node : public NodeInterface {

public:

	int data;
	Node* left;
	Node* right;

	Node(int value, Node* leftChild = NULL, Node* rightChild = NULL) {
		data = value;
		left = leftChild;
		right = rightChild;
	}

	/*
	 * Returns the data that is stored in this node
	 *
	 * @return the data that is stored in this node.
	 */
	int getData(){
		return data;
	}

	/*
	 * Returns the left child of this node or null if it doesn't have one.
	 *
	 * @return the left child of this node or null if it doesn't have one.
	 */
	NodeInterface * getLeftChild(){
		if (left == NULL) return NULL;
		else return left;
	}

	/*
	 * Returns the right child of this node or null if it doesn't have one.
	 *
	 * @return the right child of this node or null if it doesn't have one.
	 */
	NodeInterface * getRightChild(){
		if (right == NULL) return NULL;
		else return right;
	}

private:

	void setData(int newdata){
		data = newdata;
	}

	void setLeftChild(Node* value){
		left = value;
	}

	void setRightChild(Node* value){
		right = value;
	}
};
