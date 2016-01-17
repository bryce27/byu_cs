/*
 * Tuple.cpp
 *
 *  Created on: Feb 26, 2015
 *      Author: byoung17
 */

#include "Tuple.h"
#include <iostream>

Tuple::Tuple() {
	// TODO Auto-generated constructor stub

}

Tuple::~Tuple() {
	// TODO Auto-generated destructor stub
}

void Tuple::printTuple(){
	//cout << "relationName: " << relationName << endl;
	//cout << "starting printTuple()..." << endl;
	int j = 0;
	for (int i = 0; i < tList.size(); i++){
		if ((j%2 == 0)){
			cout << "\n";
		}
		string output = tList[i];
		cout << output;
		j++;
	}
	cout << "\n" << endl;

}

