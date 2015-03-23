#include "BST.h"
#include <iostream>

using namespace std;

int size_of_tree = 0;

// make empty tree
Node* root = NULL;

BST::BST(){
	root = NULL;
}
BST::~BST(){
	clear();
}

	//Please note that the class that implements this interface must be made
	//of objects which implement the NodeInterface

	/*
	 * Returns the root node for this tree
	 *
	 * @return the root node for this tree.
	 */
	NodeInterface * BST::getRootNode(){
		return root;
	}

	int BST::size(){
		return size_of_tree;
	}


	bool BST::recContains(Node* curr, int value){

		bool contains = false;

		if (curr == NULL){
			return false;
		}

		if (value == curr->data) {
			//cout << "\nrecContains(): Found value! Tree contains  " << value << endl;
			contains = true;
		}
		else if (value < curr->data){
			//cout << "\nValue: " << value << " is less than curr->data: " << curr->data << "." << endl;
			contains = recContains(curr->left, value);
		}
		else if (value > curr->data){
			//cout << "\nValue: " << value << " is greater than curr->data: " << curr->data << "." << endl;
			contains = recContains(curr->right, value);
		}
		else {
			//cout << "recContains(): didn't find value -- returning false" << endl;
			contains = false;
		}
		return contains;
	}

	bool BST::contains(int value){

		//cout << "\nStarting contains()..." << endl;

		if (root == NULL)
		{
			//cout << "\ncontaints(): root is NULL -- returning false" << endl;
			return false;
		}

		else {

			//cout << "\nStarting recContains()..." << endl;
			bool contains = BST::recContains(root, value);
			return contains;
		}
	}

	// recursive add
	Node* BST::recAdd(Node* c, int x){
		// recursive method
		// doesn't deal with root

		// returns what the parent of c should point to afterward

		//cout << "\nStarting recAdd()..." << endl;

		// base case
		if (c == NULL){
			//cout << "\nrecAdd() added " << x << " to the tree" << endl;
			return new Node(x);
		}

		else if (x < c->data){
			c->left = recAdd(c->left, x);
			return c;
		}

		else if (x > c->data){
			c->right = recAdd(c->right, x);
			return c;
		}
		// just in case
		return c;
	}

	/*
	 * Attempts to add the given int to the BST tree
	 *
	 * @return true if added
	 * @return false if unsuccessful (i.e. the int is already in tree)
	 */

	bool BST::add(int x){
		// this is the entry method

		//cout << "\nStarting add()..." << endl;

		// find
		if (!BST::contains(x)){
			// make sure this function works when the tree is empty
			root = BST::recAdd(root, x);
			return true;
		}
		else {
			return false;
		}
	}


	Node* BST::IOP(Node* curr, Node* parent){

		//cout << "\nStarting IOP..." << endl;

		if (curr->right == NULL){
			//cout << "\ncurr->right == NULL" << endl;
			// swap between IOP and node to be removed
			int swapData = parent->data;
			parent->data = curr->data;
			curr->data = swapData;

			// save left child
			Node* savedNode = curr->left;

			// delete
			delete curr;

			// return left child
			return savedNode;
		}

		else {
			//cout << "curr != NULL" << endl;
			curr->right = BST::IOP(curr->right, parent);
			return curr;
		}
		}

	Node* BST::recRemove(Node* c, int x){

		//cout << "\nStarting recRemove..." << endl;

		if (x == c->data){
			// this has 2 cases

			//cout << "x = c->data" << endl;

			// if it has just a right child
			if (c->left == NULL ){
				//cout << "\nc->left == NULL" << endl;
				 Node* temp = c->right;
				 delete c;
				 //cout << "\nDeleted c..." << endl;
				 return temp;
			}

			// if it has just a left child
			else{
				//cout << "\nc->left != NULL" << endl;
				c->left = BST::IOP(c->left, c);
				//Node* IOP() stuff
				// IOP- this is another recursive method
				// IOP- its function: find IOP, swap values, get rid of IOP
				return c;
			}

		}

		if (x < c->data){
			//cout << "\nx < c->data" << endl;
			c->left = recRemove(c->left, x);
			return c;
		}

		if (x > c->data){
			//cout << "\nx > c->data" << endl;
			c->right = recRemove(c->right, x);
			return c;
		}


	}

	/*
	 * Attempts to remove the given int from the BST tree
	 *
	 * @return true if successfully removed
	 * @return false if remove is unsuccessful(i.e. the int is not in the tree)
	 */
	bool BST::remove(int data){

		//cout << "\nStarting remove..." << endl;

		if (BST::contains(data)){
			// make sure this function works when the tree is empty
			root = BST::recRemove(root, data);
			//cout << "\nremove() returned true" << endl;
			return true;
		}
		else {
			//cout << "\nremove() returned false" << endl;
			return false;
		}
	}


	/*
	 * Removes all nodes from the tree, resulting in an empty tree.
	 */
	void BST::clear(){

		while (root != NULL){
			remove(root->data);
		}

		//root = NULL;

	}
	/*	//cout << "\nRunning clear()..." << endl;
		if (root == NULL){
			//cout << "\nRoot is null" << endl;
			return;
		}
		//cout << "\nStarting recClear()..." << endl;
		BST::recClear(root);
		}*/

	void Inorder(Node *root) {
		if(root == NULL) return;

		Inorder(root->left);       //Visit left subtree
		//cout << root->data << " ";  //Print data
		Inorder(root->right);      // Visit right subtree
	}
