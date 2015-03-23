#pragma once
#include "token.h"
#include "predicate.h"
#include "rule.h"
#include <set>

using namespace std;

class DLP {
public:

	DLP();
	~DLP();

	vector<Predicate> schemes;
	vector<Predicate> facts;
	vector<Rule> rules;
	vector<Predicate> queries;
	set<string> domain;

	void addScheme(Predicate s);
	void addFact(Predicate f);
	void addRule(Rule r);
	void addQuery(Predicate q);
	void addDomain(vector<Parameter> pList);

	string stringSchemes();
	string stringFacts();
	string stringRules();
	string stringQueries();
	string stringDomain();
	string toString();
	vector<Predicate> getSchemes();
	vector<Predicate> getQueries();
	vector<Predicate> getFacts();

};
