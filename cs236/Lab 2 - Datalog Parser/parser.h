#pragma once
#include "datalogProgram.h"
#include <vector>


using namespace std;

class Parser{

public:
	Parser();
	Parser(vector<Token> tokenVector);
	~Parser();

	int index;
	Token curr;
	vector<Token> tokens;
	DLP data;
	Predicate p;
	Rule r;

	bool match(string tokenType);
	void parseDatalogProgram();
	void parseSchemeList();
	void parseScheme();
	void parseIdList();
	void parseFactList();
	void parseFact();
	void parseRuleList();
	void parseRule();
	void parseQueryList();
	void parseStringList();
	void parseHeadPredicate();
	void parsePredicate();
	void parsePredicateList();
	void parseParameter();
	void parseParameterList();
	void parseExpression();
	void parseOperator();
	void parseQuery();

	DLP getData();
};

