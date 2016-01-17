#pragma once
#include "predicate.h"
#include <vector>

using namespace std;

class Rule {
public:

	Rule();
	Rule(Predicate p);
	Rule(Predicate pred, vector<Predicate> predList);

	Predicate predicate;
	vector<Predicate> predicateList;

	void addPredicate(Predicate pred);
	vector<Predicate> getPredicateList();
	Predicate getPredicate();
	string toString();

};


