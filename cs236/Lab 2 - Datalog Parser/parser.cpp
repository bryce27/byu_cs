#include "parser.h"
#include <iostream>

using namespace std;

// remember to make a constructor that doesn't take in parameters
Parser::Parser(){
	//cout << "making new Parser without params..." << endl;
	data = DLP();
	tokens = vector<Token>();
	index = 0;
	curr = Token();
}

Parser::Parser(vector<Token> tokenVector){
	//cout << "making new parser with tokenVector as param..." << endl;
	tokens = tokenVector;
	index = 0;
	curr = tokens[0];
	p = Predicate();
	r = Rule();
	//cout << "Vector size: " << tokens.size() << endl;
	parseDatalogProgram();

}

Parser::~Parser(){}

// no need for boolean anymore
bool Parser::match(string tokenType){
	//cout << "match(" << tokenType << ")..." << endl;
	//cout << "curr.getType() is " << curr.getType() << endl;
	if (tokenType == curr.getType()){
		//cout << "matches!" << endl;
		curr = tokens[++index];
		return true;
	}
	else {
		 throw string(curr.printTokens());
		 // is there a way to throw a line number?
		return false;
	}
}

void Parser::parseDatalogProgram(){
	//cout << "parseDatalogProgram()..." << endl;
	match("SCHEMES");
	match("COLON");
	parseScheme();
	parseSchemeList();
	match("FACTS");
	match("COLON");
	parseFactList();
	match("RULES");
	match("COLON");
	parseRuleList();
	match("QUERIES");
	match("COLON");
	parseQuery();
	parseQueryList();
}

void Parser::parseScheme(){
	p = Predicate(curr.getValue());
	//cout << "parseScheme" << curr.printTokens() << "..." << endl;
/*	if (curr.getType() == "ID"){
		p.addParameter(Parameter(curr.getType(), curr.getValue()));
	}*/
	match("ID");
	match("LEFT_PAREN");

	if (curr.getType() == "ID"){
		p.addParameter(Parameter(curr.getType(), curr.getValue()));
	}
	match("ID");
	if (curr.getType() == "COMMA"){
		parseIdList();
	}
	match("RIGHT_PAREN");
	data.addScheme(p);
}

void Parser::parseSchemeList(){
	//cout << "parseSchemeList()..." << endl;
	if (curr.getType() == "ID"){
	parseScheme();
	}
	//data.addScheme(p);
	if(curr.getType() == "ID")
		parseSchemeList();
}

void Parser::parseIdList(){
	//cout << "parseIdList()..." << curr.printTokens() << "..." << endl;
	match("COMMA");
	if (curr.getType() == "ID"){
		p.addParameter(Parameter(curr.getType(), curr.getValue()));
	}
	match("ID");
	if (curr.getType() == "COMMA")
		parseIdList();
}

void Parser::parseFact(){
	p = Predicate(curr.getValue());
	//cout << "parseFact()..." << curr.printTokens() << "..." << endl;
/*	if (curr.getType() == "ID"){
		p.addParameter(Parameter(curr.getType(), curr.getValue()));
	}*/
	match("ID");
	match("LEFT_PAREN");

	if (curr.getType() == "STRING"){
		p.addParameter(Parameter(curr.getType(), curr.getValue()));
	}
	match("STRING");
	if (curr.getType() == "COMMA"){
	parseStringList();
	}
	match("RIGHT_PAREN");
	match("PERIOD");
	data.addFact(p);
}

void Parser::parseFactList(){
	//cout << "parseFactList()..." << curr.printTokens() << "..." << endl;
	if(curr.getType() == "ID"){
		parseFact();
		if (curr.getType() == "ID"){
		parseFactList();
		}
	}
}

void Parser::parseRule(){
	//r = Rule(curr.getValue());
	//cout << "parseRule()..." << curr.printTokens() << "..." << endl;
	parseHeadPredicate();
	r = Rule(p);
	match("COLON_DASH");
	parsePredicate();
	if (curr.getType() == "COMMA"){
	parsePredicateList();
	}
	match("PERIOD");
	data.addRule(r);
}

void Parser::parseRuleList(){
	//cout << "parseRuleList()..." << curr.printTokens() << "..." << endl;
	if(curr.getType() == "ID"){
		parseRule();
		if (curr.getType() == "ID"){
		parseRuleList();
		}
	}
}

void Parser::parseHeadPredicate(){
	//r = Rule(curr.getValue());
	p = Predicate(curr.getValue());
	//cout << "parseHeadPredicate()..." << curr.printTokens() << "..." << endl;
/*	if (curr.getType() == "ID"){
		//r = Rule(curr.getValue());
		//p.addParameter(Parameter(curr.getType(), curr.getValue()));
	}*/
	match("ID");
	match("LEFT_PAREN");
	if (curr.getType() == "ID"){
		p.addParameter(Parameter(curr.getType(), curr.getValue()));
	}
	match("ID");
	if (curr.getType() == "COMMA"){
	parseIdList();
	}
	match("RIGHT_PAREN");
}

void Parser::parsePredicate(){
	//cout << "parsePredicate()..." << curr.printTokens() << "..." << endl;
	p = Predicate(curr.getValue());
/*	if (curr.getType() == "ID"){
		p.addParameter(Parameter(curr.getType(), curr.getValue()));
	}*/
	match("ID");
	match("LEFT_PAREN");
	parseParameter();
	if (curr.getType() == "COMMA"){
	parseParameterList();
	}
	match("RIGHT_PAREN");
	r.addPredicate(p);
}



void Parser::parsePredicateList(){
	//cout << "parsePredicateList()..." << curr.printTokens() << "..." << endl;
	match("COMMA");
	parsePredicate();
	if(curr.getType() == "COMMA"){
		parsePredicateList();
	}
}

void Parser::parseParameter(){
	//cout << "parseParameter()..." << curr.printTokens() << "..." << endl;
	if(curr.getType() == "STRING" || curr.getType() == "ID"){
		p.addParameter(Parameter(curr.getType(), curr.getValue()));
		match(curr.getType());
	}
	if (curr.getType() == "LEFT_PAREN"){
		parseExpression();
	}
/*	else
		match("not a valid param");*/
}

void Parser::parseParameterList(){
	//cout << "parseParameterList()..." << curr.printTokens() << "..." << endl;
	match("COMMA");
	parseParameter();
	if(curr.getType() == "COMMA"){
		parseParameterList();
	}
}

void Parser::parseExpression(){
	//cout << "parseExpression()..." << curr.printTokens() << "..." << endl;
	match("LEFT_PAREN");
	parseParameter();
	parseOperator();
	parseParameter();
	match("RIGHT_PAREN");
}

void Parser::parseOperator(){
	//cout << "parseOperator()..." << curr.printTokens() << "..." << endl;
	if (curr.getType() == "ADD")
		match("ADD");
	if (curr.getType() == "MULTIPLY")
		match("MULTIPLY");
}

void Parser::parseQuery(){
	p = Predicate(curr.getValue());
	//cout << "parseQuery()..." << curr.printTokens() << "..." << endl;
	parsePredicate();
	match("Q_MARK");
	data.addQuery(p);
}

void Parser::parseQueryList(){
	//cout << "parseQueryList()..." << curr.printTokens() << "..." << endl;
	if (curr.getType() == "EOF"){

	}
	else {
	parseQuery();
	if(curr.getType() == "ID")
		parseQueryList();

	else if(curr.getType() != "EOF")
		throw string(curr.printTokens());
}
}

void Parser::parseStringList(){
	//cout << "parseStringList()..." << curr.printTokens() << "..." << endl;
	match("COMMA");
	if (curr.getType() == "STRING"){
		p.addParameter(Parameter(curr.getType(), curr.getValue()));
	}
	match("STRING");
	if (curr.getType() == "COMMA")
		parseStringList();
}


DLP Parser::getData(){
	//cout << "getData()..." << curr.printTokens() << "..." << endl;
	return data;
}
