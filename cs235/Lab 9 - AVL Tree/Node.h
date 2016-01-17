#pragma once
#include "NodeInterface.h"
#include <iostream>


class Node : public NodeInterface {

public:

	int data;
	Node* left;
	Node* right;
	int height;

	Node(int value, Node* leftChild = NULL, Node* rightChild = NULL, int tallness = 0) {
		data = value;
		left = leftChild;
		right = rightChild;
		height = tallness;

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
		//if (left == NULL) return NULL;
		return left;
	}

	/*
	 * Returns the right child of this node or null if it doesn't have one.
	 *
	 * @return the right child of this node or null if it doesn't have one.
	 */
	NodeInterface * getRightChild(){
		//if (right == NULL) return NULL;
		return right;
	}

	/*
	 * Returns the height of this node. The height is the number of edges
	 * from this node to this nodes farthest child.
	 */
	int getHeight(){
		cout <<"\ngetHeight(" << this->data <<")..." << endl;
		cout << "height: " << height << endl;
		return height;
	/*	cout <<"\ngetHeight()..." << endl;
		int maxChildHeight;
		if (this->left == NULL && this->right == NULL) {
			cout <<"\nthis->left and this->right are NULL" << endl;
			return 0;

		}
		if (this->left->getHeight() > this->right->getHeight()) {
			cout << "\nleft is heigher than right" << endl;
			maxChildHeight = this->left->getHeight();
		}
		else if ((this->left->getHeight() < this->right->getHeight()) || (this->left->getHeight() == this->right->getHeight())){
			cout << "\nleft is heigher than right or heights are equal" << endl;
			maxChildHeight = this->right->getHeight();
		}
		else {
			return height;
		}
		cout << "maxChildHeight + 1 = " << maxChildHeight + 1 << endl;
		return maxChildHeight + 1;*/
	}

	void setHeight(int value){
		this->height = value;
	}

	void updateHeight() {
		int LH;
		if (left == NULL)LH = -1;
		else LH = left->height;

		int RH;
		if (right == NULL) RH = -1;
		else RH = right->height;

		height = max(LH, RH) + 1;
	}

	int getBalance() {
		int LH = (left == NULL) ? -1 : left->height;
		int RH = (right == NULL) ? -1 : right->height;

		return RH - LH;
	}

};
