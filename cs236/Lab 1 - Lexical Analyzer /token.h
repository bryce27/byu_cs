/*
 * token.cpp
 *
 *  Created on: Jan 20, 2015
 *      Author: byoung17
 */
#pragma once
#include <string>

using namespace std;

class token{

private:
	// token type
	/*enum tokenType {
		COMMA, PERIOD, Q_MARK, LEFT_PAREN, RIGHT_PAREN, COLON, COLON_DASH, MULTIPLY, ADD, SCHEMES, FACTS, RULES, QUERIES,
		ID, STRING, COMMENT, WHITESPACE, UNDEFINED, EOF
	};*/
	// token value - string extracted from file text
	string value, type;
	// token line number
	int lineNum;

public:
	token(string type, string value, int lineNum);
	string printTokens();

};



// output UNDEFINED if undefined symbol is found
