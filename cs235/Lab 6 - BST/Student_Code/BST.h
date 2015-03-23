#pragma once
#include "BSTInterface.h"
#include "Node.h"

class BST: public BSTInterface {
public:

	//Please note that the class that implements this interface must be made
	//of objects which implement the NodeInterface

	BST();
	~BST();

	/*
	 * Returns the root node for this tree
	 *
	 * @return the root node for this tree.
	 */
	virtual NodeInterface * getRootNode();

	virtual Node* recAdd(Node* curr, int value);

	virtual bool contains(int value);

	virtual bool recContains(Node* curr, int value);

	/*
	 * Attempts to add the given int to the BST tree
	 *
	 * @return true if added
	 * @return false if unsuccessful (i.e. the int is already in tree)
	 */
	virtual bool add(int data);

	Node* IOP(Node* curr, Node* rNode);

	Node* recRemove(Node* curr, int x);

	/*
	 * Attempts to remove the given int from the BST tree
	 *
	 * @return true if successfully removed
	 * @return false if remove is unsuccessful(i.e. the int is not in the tree)
	 */
	virtual bool remove(int data);

	/*
	 * Removes all nodes from the tree, resulting in an empty tree.
	 */
	virtual void clear();

	// my functions

	virtual int size();
};
