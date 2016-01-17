/*
 * token.cpp
 *
 *  Created on: Jan 21, 2015
 *      Author: bryce
 */
#include "token.h"
#include <sstream>

token::token(string Type, string Value, int LineNum){
	value = Value;
	lineNum = LineNum;
	type = Type;
}

string token::printTokens(){
	stringstream ss;
	ss << "(" << type <<  ",\"" << value << "\"," << lineNum << ")" << endl;
	return ss.str();
}
