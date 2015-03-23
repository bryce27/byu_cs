#pragma once
#include "QSInterface.h"

using namespace std;

/*
	WARNING: It is expressly forbidden to modify any part of this document, including its name
*/
//=======================================================================================
/*
	getQS()

	Creates and returns an object whose class extends QSInterface.
	This should be an object of a class you have created.

	Example: If you made a class called "QS", you might say, "return new QS();".
*/
class Factory
{
	public:
		static 	QSInterface * getQS();
};
//=======================================================================================
