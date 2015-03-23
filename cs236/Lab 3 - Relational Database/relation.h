/*
 * relation.h
 *
 *  Created on: Feb 26, 2015
 *      Author: byoung17
 */

#ifndef RELATION_H_
#define RELATION_H_

#include <vector>
#include <set>
#include <string>
#include "Tuple.h"
#include "Scheme.h"
#include "predicate.h"
#include <iostream>

using namespace std;

class Relation {
public:

	Relation();
	~Relation();

	string name;
	Scheme schema;
	set<Tuple> tuples;

	string getName(){
		return name;
	}

	void setName(string s){
		name = s;
	}

	Scheme getSchema(){
		return schema;
	}

	void setSchema(Scheme s){
		schema = s;
	}

	void addSchema();

	void addTuple(Tuple t);
    // returns how many queries worked
    void presentTuples(vector<int> positions, vector<string> IDs, int number);
    int countToPresentTuples(vector<int> positions, vector<string> IDs);

	void printRelation();
    void printTuples();
    
	Relation select(int index, string value);
	Relation select(int index1, int index2);
    Relation project(vector<int> positions);
	Relation rename(vector<string> IDs);
    Relation evalParams(vector<Predicate> q, int index);

};



#endif /* RELATION_H_ */
