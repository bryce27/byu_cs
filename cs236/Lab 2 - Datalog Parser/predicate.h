#pragma once
#include "parameter.h"
#include <vector>

using namespace std;

class Predicate {
public:
	Predicate();
	Predicate(string ident);
	Predicate(string ident, vector<Parameter> paramList);
	~Predicate();
	vector<Parameter> paramList;
	string id;
	string getID();
	vector<Parameter> getParameters();
	void addParameter(Parameter param);
	string toString();
};
