/*
 * relation.cpp
 *
 *  Created on: Feb 26, 2015
 *      Author: byoung17
 */
#include "relation.h"
#include <sstream>

	Relation::Relation(){}
	Relation::~Relation(){}

	void Relation::addTuple(Tuple t){
		tuples.insert(t);
		//cout << "\nadding tuple ";
        //t.printTuple();
        //cout << " to set ( " << tuples.size() << " )" << endl;
	}

void Relation::printTuples(){
    for (set<Tuple>::iterator i = tuples.begin(); i != tuples.end(); i++){
        //cout << "staring set iterator for loop for printTuple()" << endl;
        Tuple tup = *i;
        tup.printTuple();
    }
}

void Relation::presentTuples(vector<int> positions, vector<string> IDs, int number){
    //int howMany = 0;
    for (set<Tuple>::iterator i = tuples.begin(); i != tuples.end(); i++){
        //cout << "staring set iterator for loop for printTuple()" << endl;
        Tuple tup = *i;
        
        int numOutputted = 0;
        
        stringstream ss;
        //for (int k = 0; k < positions.size(); k++) {
        
            for (int j = 0; j < tup.tList.size(); j++) {
                
                
                
                //howMany++;
                
                if ( (j == tup.tList.size() - 1) || (IDs.size() < 2)){
                    if ( j % IDs.size() == 1){
                    ss << IDs[j % positions.size()] << "=" << tup.tList[j] << endl;
                        //numOutputted++;
                    //cout << ss.str();
                    }
                    else{
                        if ((j == tup.tList.size() - 1) && (numOutputted > 0)){
                            ss <<  IDs[j % positions.size()] << "=" << tup.tList[j] << endl;
                        }
                        else
                            ss << "  " << IDs[j % positions.size()] << "=" << tup.tList[j] << endl;
                        //numOutputted++;
                    //cout << ss.str();
                    }
                }


                // its not at the end of tList and its list of IDs has 2 or more
                else {
                    //cout << "size here: " << tup.tList.size() << endl;
                    int newNumber = (tup.tList.size() / number);
                    //cout << "2" << endl;
                    if ( numOutputted == (newNumber - 1)){
                    //if (j == tup.tList.size()){
                    //if ((tup.tList.size() / j) == 2){
                        //cout << "2.1" << endl;
                        ss << IDs[j % positions.size()] << "=" << tup.tList[j] << endl;
                        numOutputted = 0;
                        //cout << ss.str();
                    }
                    else {
                        if (numOutputted == 0){
                            ss << "  " << IDs[j % positions.size()] << "=" << tup.tList[j] << ", ";
                            numOutputted++;
                        }
                        
                        else{
                        //cout << "2.2" << endl;
                        ss  << IDs[j % positions.size()] << "=" << tup.tList[j] << ", ";
                        numOutputted++;
                        }
                    }
                }
                //cout << ss.str();
            }
        
        
        
        cout << ss.str();
    }
}

int Relation::countToPresentTuples(vector<int> positions, vector<string> IDs){
    int howMany = 0;
    for (set<Tuple>::iterator i = tuples.begin(); i != tuples.end(); i++){
        //cout << "staring set iterator for loop for printTuple()" << endl;
        Tuple tup = *i;
        
        stringstream ss;
        //for (int k = 0; k < positions.size(); k++) {
        
        for (int j = 0; j < tup.tList.size(); j++) {
            howMany++;
        }
        //}
        
        // ss << tupleValue << "=" << tup.tList[tuplePos] << endl;
        
        //tup.printTuple();
    }
    if (howMany != 0){
        return (howMany / IDs.size());
    }
    else return 1;
}

	void Relation::printRelation(){
        cout << "Relation: " << endl;
		cout << "name: " << name << endl;
		schema.printScheme();
        printTuples();
		cout << "tuples ( " << tuples.size() << " )" << endl;
		for (set<Tuple>::iterator i = tuples.begin(); i != tuples.end(); i++){
			//cout << "staring set iterator for loop for printTuple()" << endl;
			Tuple tup = *i;
			tup.printTuple();
		}
	}

    Relation Relation::evalParams(vector<Predicate> q, int index){
        vector<Parameter> params = q[index].getParameters();
        Relation r;
        for (int i = 0; i < params.size(); i++){
            if (params[i].getType() == "STRING"){
                r = this->select(i, params[i].getValue());
            };
        }
        return r;
    }


	Relation Relation::select(int index, string value){
        //cout << "\nselect (" << index << ", " << value << ")" << endl;
		Relation r;
		r.name = name;
		r.schema = schema;
		for (set<Tuple>::iterator i = tuples.begin(); i != tuples.end(); i++){
			Tuple tup = *i;
			if (tup.tList[index] == value){
				r.addTuple(tup);
                //cout << "select adding tuple: ";
                //tup.printTuple();
			}
		}
        //cout << "\nselect (" << index << ", " << value << ")" << " returning relation: ";
        //r.printRelation();

		return r;
	}

	Relation Relation::select(int index1, int index2){
        //cout << "\nselect (" << index1 << ", " << index2 << ")" << endl;
		Relation r;
		r.name = name;
		r.schema = schema;
		for (set<Tuple>::iterator i = tuples.begin(); i != tuples.end(); i++){
            Tuple tup = *i;
            //cout << "tup is" << endl;
            //tup.printTuple();
			if (tup.tList[index1] == tup.tList[index2]){
                //cout << "hey" << endl;
				r.addTuple(tup); // crashes here
			}
		}
		return r;
	}

    Relation Relation::project(vector<int> positions){
        //cout << "\nproject... " << endl;
        Relation r;
        r.name = name;
        r.schema = schema;
        
        set<Tuple>::iterator it;
        Tuple projected;
        int toProject;
        
        bool shouldInsert = false;
        
        for(it = tuples.begin(); it != tuples.end(); it++)
        {
            Tuple temp;
            temp = *it;
            
            shouldInsert = true;
            
            for(int i = 0; i < positions.size(); i++){
                toProject = positions.at(i);
                //cout << temp.tList.at(toProject);
                projected.tList.push_back(temp.tList.at(toProject));

            }
        }
        
        if (shouldInsert){
        r.tuples.insert(projected);
        }
        //cout << "r here is: ";
        //r.printRelation();
        
        return r;
        
//        Project ( which column you want (vector of indices of columns)
//                  for each t in tuple
//                       take the tuple and pull out certain columns
    
    }

		Relation Relation::rename(vector<string> IDs){
            Relation r;
            r.name = name;
            r.tuples = tuples;
            if (IDs.size() != 0)
            r.schema.setParams(IDs);
            return r;
		}



