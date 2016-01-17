#include "parameter.h"

using namespace std;

Parameter::Parameter(){
	// id or string
	type = "";
	value = "";
}

Parameter::Parameter(string tokenType, string val) {
	type = tokenType;
	value = val;
}

Parameter::~Parameter(){}

string Parameter::getValue(){
	return value;
}

string Parameter::getType(){
	return type;
}

string Parameter::toString(){
	return value;
}
