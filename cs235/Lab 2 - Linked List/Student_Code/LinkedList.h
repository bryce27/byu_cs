#pragma once
#include "LinkedListInterface.h"
#include "Node.h"


template <class T>
class LinkedList : public LinkedListInterface <T> {

/*public LLI<T>{
	
}*/

	//store pointer somewhere in here that points to the head


public:

	Node<T>* head;

	LinkedList(){head = NULL;}
		
	~LinkedList(){
		clear();
	}

	bool hasDup(T value){
		Node<T>* dupCheck = head;
		while(dupCheck != NULL){
			if (dupCheck->data == value){
				return true;
			}
			dupCheck = dupCheck->next;
		}
		return false;
	}
		
	/*
		insertHead

		A node with the given value should be inserted at the beginning of the list.

		Do not allow duplicate values in the list.

		insertHead(4)
		// check for duplicates
		// encapsulate data into a node
		create a node and put 4 in it
		// link new node to the list

		//must have a temporary pointer to point to any new node you have
		Node<T>* temp = whatever
		temp->next = head
		set temp to head
	 */
	virtual void insertHead(T value) {
		if (hasDup(value)){
			return;
			}
		if (head == NULL){
			head = new Node<T>(value);
		}
		else{
		// to do...duplicates
		Node<T>* temp = new Node<T>(value);
		temp->next = head;
		head = temp;
		}
	}

	/*
		insertTail

		A node with the given value should be inserted at the end of the list.

		Do not allow duplicate values in the list.

		make pointer curr and set it to head
		take him through the list checking if his next is NULL
	 */
	virtual void insertTail(T value) {
		if (head == NULL){
			insertHead(value);
			return;
		}

		if (hasDup(value)){
					return;
		}
		Node<T>* temp = head;
		while(temp->next != NULL){
			temp = temp->next;
		}
		if (temp->next == NULL){
			Node<T>* newNode = new Node<T>(value);
			temp->next = newNode;
			newNode->next = NULL;
					}
	}

	/*
		insertAfter

		A node with the given value should be inserted immediately after the
		node whose value is equal to insertionNode.

		A node should only be added if the node whose value is equal to
		insertionNode is in the list. Do not allow duplicate values in the list.

		insertAfter(4,8)
		take curr throught the list checking curr's data
		when you find 4, assign 4's node's pointer to node after curr
		assign node that has 8 to node that has 4
	 */
	virtual void insertAfter(T value, T insertionNode) {
		if (hasDup(value) == true){
			return;
		}
		Node<T>* curr = head;
		while(curr != NULL){
			if (curr->data == insertionNode){
				Node<T>* newNode = new Node<T>(value);
				newNode->next = curr->next;
				curr->next = newNode;
			}
			curr = curr->next;
		}
	}
	
	/*
		remove

		The node with the given value should be removed from the list.

		The list may or may not include a node with the given value.

		remove(5)
		check if it's there
		set pointer curr to head. it will check data
		while curr->next is not null search for 5
		logically disconnect it, linking its before and after nodes
		toDelete set to curr->next and then delete it later
		// waht if 5 is at the head?
		 * point to head
		 * set head to head->next
		 * delete head
	 */
	virtual void remove(T value) {
		Node<T>* curr = head;
		Node<T>* deleteMe = head;
		if (head->data == value){		// check to see if it's the head we want to remove
			head = curr->next;
			delete curr;
			return;
		}
		while(curr->next != NULL){
			if (curr->next->data == value){
				deleteMe = curr->next;
				curr->next = deleteMe->next;
				deleteMe->next = NULL;
				delete deleteMe;
				return;
			}
		curr = curr->next;
		}
	}

	/*
		clear

		Remove all nodes from the list.
	 */
	virtual void clear(){
		while (head != NULL){
			Node<T>* n = head->next;
			delete head;
			head = n;
		}

	}
	/*
		at

		Returns the value of the node at the given index. The list begins at
		index 0.

		If the given index is out of range of the list, return NULL;
	 */
	virtual T at(int index) {
		if (index >= size() || index < 0){
		return NULL;
	}
		else{
			Node<T>* n = head;
			int i = 0;
			while (n != NULL){
				if(i == index){
					return n->data;
				}
				i++;
				n = n->next;
			}
		}
	}
	/*
		size

		Returns the number of nodes in the list.
	 */
	virtual int size() {
		Node<T>* n = head;
		int i = 0;
		while (n != NULL){
			n = n->next;
			i++;
		}
		return i;
	}


};
