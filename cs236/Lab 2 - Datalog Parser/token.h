/*
 * token.cpp
 *
 *  Created on: Jan 20, 2015
 *      Author: byoung17
 */
#pragma once
#include <string>

using namespace std;

class Token{

private:
	// token value - string extracted from file text
	string value, type;
	// token line number
	int lineNum;

public:
	Token();
	Token(string type, string value, int lineNum);
	string printTokens();
	string getType();
	string getValue();

};



// output UNDEFINED if undefined symbol is found
