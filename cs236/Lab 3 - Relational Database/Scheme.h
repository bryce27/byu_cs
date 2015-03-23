/*
 * Scheme.h
 *
 *  Created on: Feb 27, 2015
 *      Author: byoung17
 */

#ifndef SCHEME_H_
#define SCHEME_H_

#include <vector>
#include "parameter.h"
#include <iostream>

using namespace std;

class Scheme {
public:
	Scheme();
	~Scheme();

	vector<Parameter> params;

	void addParams(vector<Parameter> p){
		params = p;
	}
    
    void setParams(vector<string> p){
        for (int i = 0; i < p.size(); i++){
            Parameter newP("ID", p[i]);
            params.push_back(newP);
        }
        
    }

	void rename(int pos, string s){
		Parameter p("ID", s);
		params.at(pos) = p;
	}

	void printScheme(){
		for (int i = 0; i < params.size(); i++){
			cout << params[i].toString() << endl;
		}
	}
    
    vector<Parameter> getParams(){
        return params;
    }
};

#endif /* SCHEME_H_ */
