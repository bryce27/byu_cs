/*
 * token.cpp
 *
 *  Created on: Jan 21, 2015
 *      Author: bryce
 */
#include "token.h"
#include <sstream>

Token::Token(){}

Token::Token(string Type, string Value, int LineNum){
	value = Value;
	lineNum = LineNum;
	type = Type;
}

string Token::printTokens(){
	stringstream ss;
	ss << "(" << type <<  ",\"" << value << "\"," << lineNum << ")" << endl;
	return ss.str();
}

string Token::getType(){
	return type;
}

string Token::getValue(){
	return value;
}
