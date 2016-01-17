#include "AVL.h"
#include <iostream>

using namespace std;

int size_of_tree = 0;

// make empty tree
Node* root = NULL;

AVL::AVL(){
	root = NULL;
}
AVL::~AVL(){
	clear();
}

	//Please note that the class that implements this interface must be made
	//of objects which implement the NodeInterface

	/*
	 * Returns the root node for this tree
	 *
	 * @return the root node for this tree.
	 */
	NodeInterface * AVL::getRootNode(){
		return root;
	}

	int AVL::size(){
		return size_of_tree;
	}


	bool AVL::recContains(Node* curr, int value){

		bool contains = false;

		if (curr == NULL){
			return false;
		}

		if (value == curr->data) {
			////cout << "\nrecContains(): Found value! Tree contains  " << value << endl;
			contains = true;
		}
		else if (value < curr->data){
			////cout << "\nValue: " << value << " is less than curr->data: " << curr->data << "." << endl;
			contains = recContains(curr->left, value);
		}
		else if (value > curr->data){
			////cout << "\nValue: " << value << " is greater than curr->data: " << curr->data << "." << endl;
			contains = recContains(curr->right, value);
		}
		else {
			//cout << "recContains(): didn't find value -- returning false" << endl;
			contains = false;
		}
		return contains;
	}

	bool AVL::contains(int value){

		////cout << "\nStarting contains(" << value << ")..." << endl;

		if (root == NULL)
		{
			////cout << "\ncontaints(): root is NULL -- returning false" << endl;
			return false;
		}

		else {

			////cout << "\nStarting recContains()..." << endl;
			bool contains = AVL::recContains(root, value);
			//if (contains == true) //cout << "\nYES!" << endl;
			return contains;
		}
	}

	// recursive add
	Node* AVL::recAdd(Node* c, int x){
		// recursive method
		// doesn't deal with root

		// returns what the parent of c should point to afterward

		////cout << "\nStarting recAdd()..." << endl;

		if (c == NULL){
			////cout << "\nrecAdd() added " << x << " to the tree" << endl;
			return new Node(x);
		}

		else if (x < c->data){
			c->left = recAdd(c->left, x);

			return balance(c);
		}

		else if (x > c->data){
			c->right = recAdd(c->right, x);
			return balance(c);
		}
	}

	void AVL::RR(Node* a, Node* b) {
		a->right = b->left;
		b->left = a;

		a->updateHeight();
		b->updateHeight();
	}

	void AVL::RL(Node* a, Node* b, Node* c) {
		a->right = c;
		b->left = c->right;
		c->right = b;

		AVL::RR(a, c);

		a->updateHeight();
		b->updateHeight();
		c->updateHeight();
	}

	void AVL::LL(Node* a, Node* b) {
		a->left = b->right;
		b->right = a;

		a->updateHeight();
		b->updateHeight();
	}

	void AVL::LR(Node* a, Node* b, Node* c) {
		a->left = c;
		b->right = c->left;
		c->left = b;

		AVL::LL(a, c);

		a->updateHeight();
		b->updateHeight();
		c->updateHeight();
	}

	Node* AVL::balance(Node* a){
		// pass in just one node and then assign children to variables
		if (a == NULL) return NULL;
		a->updateHeight();

		if (a->getBalance() > 1) {
			// RR or RL case
			Node* b = a->right;

			if (b->getBalance() < 0) {
				Node* c = b->left;
				AVL::RL(a, b, c);
				return c;

			} else {
				AVL::RR(a, b);
				return b;
			}

		} else if (a->getBalance() < -1) {
			// LL or LR case
			Node* b = a->left;

			if (b->getBalance() > 0) {
			 	Node* c = b->right;
				AVL::LR(a, b, c);
				return c;

			 } else {
				AVL::LL(a, b);
				return b;
			 }

		} else {

			return a;
		}

	}

	/*
	 * Attempts to add the given int to the AVL tree
	 *
	 * @return true if added
	 * @return false if unsuccessful (i.e. the int is already in tree)
	 */

	bool AVL::add(int x){
		// this is the entry method

		//cout << "\nStarting add(" << x <<")..." << endl;
		if (x == 2){
			//cout << "\nTrying to enter 2" << endl;
		}

		// find
		if (!(AVL::contains(x))){
			root = AVL::recAdd(root, x);

			//root->updateHeight();
			//root = balance(root);
			//updateHeight(root);
			//cout << "\nheight of root(" << root->getData() <<"): " << root->getHeight() << endl;
		    //cout <<"\nbalance of " << root->getData() << " is " << root->getBalance() << endl;
			return true;
		}
		else {
			//cout << "\nadd() returning false" << endl;
			return false;
		}
	}


	Node* AVL::IOP(Node* curr, Node* parent){

		////cout << "\nStarting IOP..." << endl;

		if (curr->right == NULL){
			////cout << "\ncurr->right == NULL" << endl;
			// swap between IOP and node to be removed
			int swapData = parent->data;
			parent->data = curr->data;
			curr->data = swapData;

			// save left child
			Node* savedNode = curr->left;

			// delete
			delete curr;

			// return left child
			return balance(savedNode);
		}

		else {
			////cout << "curr != NULL" << endl;
			curr->right = AVL::IOP(curr->right, parent);
			return balance(curr);
		}
		}

	Node* AVL::recRemove(Node* c, int x){

		////cout << "\nStarting recRemove..." << endl;

		if (x == c->data){
			// this has 2 cases

			////cout << "x = c->data" << endl;

			// if it has just a right child
			if (c->left == NULL ){
				////cout << "\nc->left == NULL" << endl;
				 Node* temp = c->right;
				 delete c;
				 ////cout << "\nDeleted c..." << endl;
				 return balance(temp);
			}

			// if it has just a left child
			else{
				////cout << "\nc->left != NULL" << endl;
				c->left = AVL::IOP(c->left, c);
				//Node* IOP() stuff
				// IOP- this is another recursive method
				// IOP- its function: find IOP, swap values, get rid of IOP
				return balance(c);
			}

		}

		if (x < c->data){
			////cout << "\nx < c->data" << endl;
			c->left = recRemove(c->left, x);
			return balance(c);
		}

		if (x > c->data){
			////cout << "\nx > c->data" << endl;
			c->right = recRemove(c->right, x);
			return balance(c);
		}


	}

	/*
	 * Attempts to remove the given int from the AVL tree
	 *
	 * @return true if successfully removed
	 * @return false if remove is unsuccessful(i.e. the int is not in the tree)
	 */
	bool AVL::remove(int data){

		//cout << "\nStarting remove..." << endl;

		if (AVL::contains(data)){
			// make sure this function works when the tree is empty
			root = AVL::recRemove(root, data);
			////cout << "\nremove() returned true" << endl;
			//root = balance(root);
			return true;
		}
		else {
			////cout << "\nremove() returned false" << endl;
			return false;
		}
	}



	void AVL::clear(){

		while (root != NULL){
			remove(root->data);
		}

		//root = NULL;

	}
	/*	////cout << "\nRunning clear()..." << endl;
		if (root == NULL){
			////cout << "\nRoot is null" << endl;
			return;
		}
		////cout << "\nStarting recClear()..." << endl;
		AVL::recClear(root);
		}*/

