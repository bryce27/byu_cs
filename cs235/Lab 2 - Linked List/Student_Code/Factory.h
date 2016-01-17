#pragma once
#include "LinkedListInterface.h"

using namespace std;

/*
	WARNING: It is expressly forbidden to modify any part of this document, including its name
*/
//=======================================================================================

class Factory
{
	public:
		static LinkedListInterface<int>* getLinkedListInt();
		static	LinkedListInterface<string>* getLinkedListString();
		
};
//=======================================================================================
