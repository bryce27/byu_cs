#include "rule.h"

#include <sstream>

Rule::Rule(){
	predicate = Predicate();
	predicateList = vector<Predicate>();
}

Rule::Rule(Predicate p){
	predicate = p;
	predicateList = vector<Predicate>();
}

Rule::Rule(Predicate p, vector<Predicate> pList){
	predicate = p;
	predicateList = pList;
}


void Rule::addPredicate(Predicate pred){
	predicateList.push_back(pred);
}
Predicate Rule::getPredicate(){
	return predicate;
}
vector<Predicate> Rule::getPredicateList(){
	return predicateList;
}

string Rule::toString(){
	stringstream ss;
	ss << predicate.toString() << " :- ";
	for(int i = 0; i < (int)predicateList.size(); i++){
		if(i == 0) ss << predicateList[i].toString();
		else ss << "," + predicateList[i].toString();
	}
	ss << ".";
	return ss.str();
}
