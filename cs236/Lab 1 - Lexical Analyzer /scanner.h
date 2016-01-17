#pragma once
#include "token.h"
#include "SuperStream.h"
#include <vector>
#include <iostream>
#include <sstream>
#include <fstream>
#include <string>

using namespace std;
// purpose - group characters from a stream into tokens

class scanner {
public:

	// private
	void readFile(string file_name);
	vector<token> tokens;
	int lineCount;
	//LineStream addBasicToken(LineStream stream);
	//void addComplexToken(string str, int line_num);
	//LineStream readString(LineStream stream);
	//void readTokens(string token);
	void readStream(SuperStream s);

	SuperStream readWord(SuperStream s);

	//string stream;

	void addWordToken(string str, int line_number);
	SuperStream addPuncToken(SuperStream s);
	bool isUndef(SuperStream s);
	SuperStream addUndefString(SuperStream s);
	void doItAgain(SuperStream s);

/*	void addSymbolToken(string str, int line_number){

	}*/

	// public
	scanner(string filename);
	//vector<token> getTokens();

	virtual ~scanner();

	vector<token> getTokens();

};


// use enum somewhere

// as you read in the file you create tokens

// save each token in a vector

// at the end you'll iterate through and print them out
