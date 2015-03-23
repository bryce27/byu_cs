/*
 * Database.cpp
 *
 *  Created on: Feb 26, 2015
 *      Author: byoung17
 */

#include "Database.h"
#include <sstream>

Database::Database() {}

Database::~Database() {}

/*void Database::addRelation(Relation r){
	relations.push_back(r);
}*/

void Database::addRelation(Relation r){

	rMap.insert(pair<string,Relation>(r.getName(), r));
	//cout << "adding relation " << r.name << " to map ( " << rMap.size() << " )" << endl;
}

void Database::create(vector<Predicate> s, vector<Predicate> f, vector<Predicate> q){
	schemes = s;
	facts = f;
	queries = q;


	for (int i = 0; i < s.size(); i++){
		Relation r;
		string name = s[i].getID();
		vector<Parameter> ps = s[i].getParameters();
		Scheme s;
		s.addParams(ps);
		r.setName(name);
		r.setSchema(s);
		addRelation(r);
	}

	for (int i = 0; i < f.size(); i++){
		Tuple t;
		string factName = f[i].getID();
		vector<Parameter> factParams = f[i].getParameters();
		vector<string> factStrings;
		//cout << "here" << endl;
		for (int j = 0; j < factParams.size(); j++){
			string hey = factParams[j].getValue();
			factStrings.push_back(hey);
		}
		//cout << "here" << endl;
		t.tList = factStrings;
		t.setRelationName(factName);
		addToRelation(t);
			// "The fact name identifies a relation to which the tuple belongs." = f[i].getID();
	}
}

void Database::printRelations(){
	cout << "Relations: " << endl;

	  for (map<string,Relation>::iterator it=rMap.begin(); it!=rMap.end(); ++it){
	    cout << it->first << " => " << endl;
	  	it->second.printRelation();
	  }
}

void Database::runQueries(){
    Relation temp;
	for (int i = 0; i < queries.size(); i++){
        string name = queries[i].getID();
        vector<Parameter> params = queries[i].getParameters();
        
        // find relation in DB that matches the query
        Relation match = rMap.find(name)->second;
        temp = match;
        
        // loop through params of query
        for (int j = 0; j < params.size(); j++) {
            string type = params[j].getType();
            string value = params[j].getValue();
            
            if (type == "STRING"){
                
//                if (temp.getName() == ""){ // if temp hasn't been edited yet
//                    temp = match;
//                    temp = temp.select(j, value);
//                }
                
                //else {
                //cout << "select on value: " << value << endl;
                    temp = temp.select(j, value);
                //}
            }
            
            if (type == "ID"){
                
                // fix this for A, B, A case
                // if you find a duplicate, don't put it in here
                // check if it's a duplicate
                bool isDup = false;
                int dupPosition = 0;
                for (int k = 0; k < IDs.size(); k++) {
                    if (IDs[k] == value){
                        isDup = true;
                        dupPosition = k;
                    }
                }
                
                
                if (isDup){
                    
                    // select (pos 1, pos 2)
//                    if (temp.getName() == ""){ // if temp hasn't been edited yet
//                        temp = match;
//                        temp = temp.select(dupPosition, j);
//                    }
                    
                    //else {
                        temp = temp.select(dupPosition, j);
                    //}
                }
                
                else {
                    // only do this if there are no dups
                    IDs.push_back(value);
                    positions.push_back(j);
                }
            }
        }

        temp = temp.project(positions);
        //cout << "temp after project here is: ";
        //temp.printRelation();
        temp = temp.rename(IDs);
        
        // debug
        stringstream ss;
        for (int z = 0; z < params.size(); z++) {
            ss << params[z].toString();
            if (z == (params.size() - 1)){}
            else ss << ",";
        }
        
        // print query
        /*temp.tuples.size()*/
        int number = temp.countToPresentTuples(positions, IDs);
        cout << name << "(" << ss.str() << ")?";
        if (temp.tuples.size() > 0 ){//&& (!(temp.tupleIsEmpty(temp.tuples)))) {
            cout << " Yes(" << number << ")" << endl;
        }
        else {
            cout << " No" << endl;
        }
        
        // print tuples
//        for(int r = 0; r < params.size(); r++){
//            string schemeLetter = params[r].getValue();
//            cout << schemeLetter << "=";
//            temp.printTuples();
//        }
        
        temp.presentTuples(positions, IDs, number);
        //temp.printTuples();
        //cout << "clearing IDs and positions" << endl;
        IDs.clear();
        positions.clear();
    }
    
    //toPrint.push_back(temp);
}

void Database::printToPrint(){
    for (int i = 0; i < toPrint.size(); i++) {
        toPrint[i].printRelation();
    }
}

void Database::addToRelation(Tuple t){
	//cout << "addToRelation ... ( ";
	//t.printTuple();
	//cout << " )" << endl;
	//map<string, Relation>::iterator it;
	string curr_name = t.getRelationName();
	Relation curr = rMap.find(curr_name)->second;
	//Relation curr = rMap.at(curr_name);
	curr.addTuple(t);
	rMap.at(curr_name) = curr;
	}

