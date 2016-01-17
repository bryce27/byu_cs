#pragma once
#include "AVLInterface.h"
#include "Node.h"

class AVL: public AVLInterface {
public:

	//Please note that the class that implements this interface must be made
	//of objects which implement the NodeInterface

	AVL();
	~AVL();

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
	 * Attempts to add the given int to the AVL tree
	 * Rebalances the tree if data is successfully added
	 *
	 * @return true if added
	 * @return false if unsuccessful (i.e. the int is already in tree)
	 */
	virtual bool add(int data);

	Node* IOP(Node* curr, Node* rNode);

	Node* recRemove(Node* curr, int x);

	void RR(Node* a, Node* b);
	void RL(Node* a, Node* b, Node* c);
	void LL(Node* a, Node* b);
	void LR(Node* a, Node* b, Node* c);

	/*
	 * Attempts to remove the given int from the AVL tree
	 * Rebalances the tree if data is successfully removed
	 *
	 * @return true if successfully removed
	 * @return false if remove is unsuccessful(i.e. the int is not in the tree)
	 */
	virtual bool remove(int data);
	/*
	 * ROTATIONS:
	 * 			LEFT
	 *
	 * 				IDENTIFY NODES TO ROTATE
	 *
	 * 				ROT.RIGHT=CUR.LEFT
	 * 				CUR.LEFT=ROT
	 * 				CUR=ROT
	 *
	 *
	 *
	 *
	 * 			RIGHT
	 *
	 *
	 *
	 */

	/*
	 * Removes all nodes from the tree, resulting in an empty tree.
	 */
	virtual void clear();

	// my functions

	virtual int size();


	virtual Node* balance(Node* node);

	//virtual void updateHeight(Node* node);

};
