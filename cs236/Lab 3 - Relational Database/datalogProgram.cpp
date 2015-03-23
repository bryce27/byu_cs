#include "datalogProgram.h"
#include <iostream>
#include <sstream>

using namespace std;

DLP::DLP(){
	schemes = vector<Predicate>();
	facts = vector<Predicate>();
	rules = vector<Rule>();
	queries = vector<Predicate>();
	domain = set<string>();
}
DLP::~DLP(){}

void DLP::addScheme(Predicate s){
	//cout << "adding scheme..." << endl;
	schemes.push_back(s);
}

void DLP::addFact(Predicate fact){
	//cout << "adding fact..." << endl;
	facts.push_back(fact);
	addDomain(fact.getParameters());
}

void DLP::addRule(Rule rule){
	//cout << "adding rule..." << endl;
	rules.push_back(rule);
	addDomain(rule.getPredicate().getParameters());
	/*for(int i = 0; i < (int)rule.getPredicateList().size(); i++)
		addDomain(rule.getPredicateList()[i].getParameters());*/
}

void DLP::addQuery(Predicate query){
	//cout << "adding query..." << endl;
	queries.push_back(query);
	//addDomain(query.getParameters());
}

void DLP::addDomain(vector<Parameter> paramList){
	//cout << "adding domain..." << endl;
	for(int i = 0; i < (int)paramList.size(); i++){
		if(paramList[i].getType() == "STRING")
			domain.insert(paramList[i].getValue());
	}

}

string DLP::stringSchemes(){
	stringstream ss;
	ss << "Schemes(" << schemes.size() << "):";
	for(int i = 0; i < (int)schemes.size(); i++)
		ss << "\n  " << schemes[i].toString();
	return ss.str();
}

string DLP::stringDomain(){
	stringstream ss;
	ss << "Domain(" << domain.size() << "):";
	set<string>::iterator myIterator;
	for(myIterator = domain.begin();
		myIterator != domain.end();
		myIterator++)
		ss << "\n  " << (*myIterator);
	return ss.str();
}

string DLP::stringFacts(){
	stringstream ss;
	ss << "Facts(" << facts.size() << "):";
	for(int i = 0; i < (int)facts.size(); i++)
		ss << "\n  " << facts[i].toString() << ".";
	return ss.str();
}

string DLP::stringQueries(){
	stringstream ss;
	ss << "Queries(" << queries.size() << "):";
	for(int i = 0; i < (int)queries.size(); i++)
		ss << "\n  " << queries[i].toString() << "?";
	return ss.str();
}

string DLP::stringRules(){
	stringstream ss;
	ss << "Rules(" << rules.size() << "):";
	for(int i = 0; i < (int)rules.size(); i++)
		ss << "\n  " << rules[i].toString();
	return ss.str();
}

string DLP::toString(){
	stringstream ss;
	ss << "Success!";;
	ss << endl << stringSchemes();
	ss << endl << stringFacts();
	ss << endl << stringRules() ;
	ss << endl << stringQueries();
	ss << endl << stringDomain();
	return ss.str();
}

vector<Predicate> DLP::getSchemes(){
	return schemes;
}
vector<Predicate> DLP::getQueries(){
	return queries;
}
vector<Predicate> DLP::getFacts(){
	return facts;
}
