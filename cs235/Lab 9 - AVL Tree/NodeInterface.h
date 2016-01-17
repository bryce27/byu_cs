//YOU MAY NOT MODIFY THIS DOCUMENT
#pragma once
#include<string>

using namespace std;

class NodeInterface {

public:
	NodeInterface() {}
	virtual ~NodeInterface() {}

	/*
	 * Returns the data that is stored in this node
	 *
	 * @return the data that is stored in this node.
	 */
	virtual int getData() = 0;

	/*
	 * Returns the left child of this node or null if it doesn't have one.
	 *
	 * @return the left child of this node or null if it doesn't have one.
	 */
	virtual NodeInterface * getLeftChild() = 0;

	/*
	 * Returns the right child of this node or null if it doesn't have one.
	 *
	 * @return the right child of this node or null if it doesn't have one.
	 */
	virtual NodeInterface * getRightChild() = 0;

	/*
	 * Returns the height of this node. The height is the number of edges
	 * from this node to this nodes farthest child.
	 */
	virtual int getHeight() = 0;

};
