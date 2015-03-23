/*
 * Database.h
 *
 *  Created on: Feb 26, 2015
 *      Author: byoung17
 */

#ifndef DATABASE_H_
#define DATABASE_H_

#include <map>
#include "relation.h"
#include "predicate.h"
#include "Query.h"
#include "Tuple.h"

class Database {
public:
	Database();
	virtual ~Database();

	map<string, Relation> rMap;

	vector<Relation> relations;
	vector<Predicate> schemes;
	vector<Predicate> facts;
	vector<Predicate> queries;
    vector<Relation> toPrint;
    vector<int> positions;
    vector<string>IDs;

	void addRelation(Relation);
	void runQueries();
	void create(vector<Predicate> s, vector<Predicate> f, vector<Predicate> q);
	void printRelations();
	void addToRelation(Tuple t);
    void printToPrint();
};

#endif /* DATABASE_H_ */
