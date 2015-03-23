#include "Factory.h"
#include "QS.h"

//You may add #include statements here

/*
	You will MODIFY THIS DOCUMENT.
*/
//=======================================================================================
/*
	getLinkedList()

	Creates and returns an object whose class extends QSInterface.
	This should be an object of a class you have created.

	Example: If you made a class called "QS", you might say, "return new QS();".
*/
QSInterface * Factory::getQS()
{
  return new QS();//Modify this line
}
