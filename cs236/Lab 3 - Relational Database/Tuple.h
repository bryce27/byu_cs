/*
 * Tuple.h
 *
 *  Created on: Feb 26, 2015
 *      Author: byoung17
 */

#ifndef TUPLE_H_
#define TUPLE_H_

#include <vector>
#include "parameter.h"

using namespace std;

class Tuple {
public:
	Tuple();
	virtual ~Tuple();

	vector<string> tList;
	string relationName;

	void setRelationName(string n){
		relationName = n;
	}

	string getRelationName(){
		return relationName;
	}

	void setTupleList(vector<string> tlist){
		tList = tlist;
	}

	void printTuple();
    //void presentTuple();

	bool operator< (const Tuple b) const {
	return tList < b.tList;
	}

};

#endif /* TUPLE_H_ */
