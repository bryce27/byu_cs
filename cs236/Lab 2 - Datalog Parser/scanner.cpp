#include "scanner.h"
#include "token.h"
#include <cctype>
#include <vector>
#include <cstdio>

// to fix: double line comments not working -- especially with EOF
// make sure numbers are listed as undef

// check the list on the wiki to make sure you covered everything
// ID has to start with letter

// take out ////couts


using namespace std;

string charToString(char c){
stringstream ss;
string s;
ss << c;
ss >> s;
return s;
}

int numOfLines(string filename) {
char c;
int num=0;
ifstream is;
is.open(filename.c_str());
while (is.good())
{
c = is.get();
if (c=='\n')num++;
}
is.close();
////cout << "num of lines: " << num << endl;
return num;

}

int linesInFile = 0;

scanner::scanner(string file_name){
	lineCount = 1;
	readFile(file_name);
}

scanner::~scanner() {}


void scanner::readFile(string filename){
	linesInFile = numOfLines(filename);
	ifstream myInputFile;
	myInputFile.open(filename.data());
	if (myInputFile){
		char nextChar;
		string s = "";
		while(myInputFile.get(nextChar)){
			////cout << "readFile() while..." << endl;
			////cout << "nextChar: " << nextChar << endl;
			s += nextChar;
			/*if(nextChar == '\n'){
				readStream(SuperStream(s, lineCount));
				s = "";
				lineCount++;
			}
		}
		readStream(SuperStream(s + "\n", lineCount));
			s = nextChar;
			readStream(SuperStream(s, lineCount ));*/
		}
		readStream(SuperStream(s, 1));
	}
	else
		cout << "Cannot open file" << endl;
}

void scanner::readStream(SuperStream s){
	////cout << "readstream()..." << endl;
	// problem here: char's like \n have to be surrounded by single not double quotes
	// fixed
	//stringstream asf;
	//int nCounter = 0;
	//for (char curr = s.getChar(); curr != '\n';){
		while(lineCount < linesInFile + 1){

			////cout << "readStream while..." << endl;
			////cout << "readStream lineCount: " << lineCount << endl;
			////cout << "lines in file: " << linesInFile << endl;

			char curr = s.getChar();
			if (curr == '\n') {
				lineCount++;
				////cout << "readStream() lineCount++" << endl;
			}
		//char curr = s.getChar();
		//if (curr == '\n') nCounter++;
		if (isspace(curr)) {
			////cout << "is space" << endl;
			curr = s.proceed();
		}
		else if (isalpha(curr)){
			////cout << "isalpha" << endl;
			s = readWord(s);
			// after you read the word, go to the place it passed back and contine
			curr = s.getChar();
		}
		else {
			////cout << "is PuncToken" << endl;
			s = addPuncToken(s);
			curr = s.getChar();
			curr = s.proceed();
		}
	}
		if (lineCount == linesInFile + 1){
			tokens.push_back(Token("EOF", "", lineCount));
		}
}

SuperStream scanner::readWord(SuperStream s){
	////cout << "readWord()..." << endl;
	char curr = s.getChar();
	string str = "";
	str = curr;
	while(isalnum(curr = s.proceed())){
		if (curr == '\n') break;
		////cout << "readWord while..." << endl;
		str += curr;
	}
	// changed lineCount to s.lineNum
	addWordToken(str, lineCount);
	return s;
}

void scanner::addWordToken(string str, int line_number){
	////cout << "addWordToken()..." << endl;
	string type;

	if(str == "Schemes")
		type = "SCHEMES";
	else if(str == "Facts")
		type = "FACTS";
	else if(str == "Rules")
		type = "RULES";
	else if(str == "Queries")
		type = "QUERIES";
	else
		type = "ID";

	////cout << "addWordToken()...adding token..." << endl;
	tokens.push_back(Token(type, str, lineCount));
	////cout << "vector size: " << tokens.size() << endl;
}

bool scanner::isUndef(SuperStream s){
	////cout << "Starting isUndef()..." << endl;
	string str = "";
	while((s.proceed() != '\'')){
		////cout << "while s.proceed != quotation..." << endl;
			//if(s.getChar() == '\n')	lineCount++;
		str += s.getChar();
	}
	if (s.peek() == '\''){
		/*str += s.proceed();
		if (s.peek() != '\'')*/
		////cout << "s.peek() == quotation" << endl;
		////cout << "isUndef returning true..." << endl;
		return true;
		}
	else {
		////cout << "s.peek() != quotation" << endl;
		////cout << "isUndef() returning false..." << endl;
		return false;
	}
	}

bool isGood(char c){
	if (c == '\'') return false;
	if (isspace(c)) return true;
	if (isalpha(c)) return true;
	else return false;
}

SuperStream scanner::addUndefString(SuperStream s){
	////cout << "Starting addUndefString()..." << endl;
	string str = "";
	int undefStartLine = lineCount;
	////cout << "undefStartLine: " << undefStartLine << endl;
	char curr = s.getChar();
	str = curr;
	while((curr = s.proceed()) != '\''){
		////cout << "addUndefString() while (s.proceed != quotation..." << endl;
	//while (curr != -1){
		////cout << "IN THE LAST WHILE" << endl;
		//if (s.getChar() == )
		//curr = s.getChar();
		if(curr == '\n'){
			////cout << "addUndefString() lineCount++" << endl;
			lineCount++;
		}
		str += s.getChar();
	}
	//str += '\'';

	if (s.peek() == '\''){
		////cout << "addUndefString() peek == quotation" << endl;
		////cout << "here" << endl;
		curr = s.getChar();
		str += curr;
		while ((curr = s.proceed()) == '\''){
		//while (!(filename.eof())){
			//curr = s.getChar();
			////cout << "addUndefString() s.proceed == quotation" << endl;
			if(curr == '\n'){
				////cout << "addUndefString() lineCount++" << endl;
				lineCount++;
			}
			str += s.getChar();
		}
	}

	curr = s.getChar();
	str += curr;
	//while(isGood(curr = s.proceed())){
	while ((lineCount != linesInFile)){
	//while (curr != -1){
		curr = s.proceed();
		////cout << "addUndefString() while s.proceed != quotation..." << endl;
		//if (s.getChar() == )
		//curr = s.getChar();
		if(curr == '\n'){
			lineCount++;
			////cout << "addUndefString() lineCount++" << endl;
		}
		str += s.getChar();
	}
	if (lineCount == linesInFile){
		tokens.push_back(Token("UNDEFINED", str, undefStartLine));
		return s;
		//break;
	}

	else {

	//str += '\'';
	////cout << "adding undefined string to vector" << endl;
	tokens.push_back(Token("UNDEFINED", str, undefStartLine));
	//lineCount++;
	////cout << "addUndefString() ending lineCount: " << lineCount << endl;
	return s;
	}
}

bool isEven(int n){
    if ( n%2 == 0) return true;
    else return false;
}

SuperStream scanner::addPuncToken(SuperStream s){
	////cout << "addPuncToken()..." << endl;
	string input = charToString(s.getChar());
	switch(s.getChar()){
	case ',': {
		// try replacing these lineCounts with lineCount
		////cout << "adding comma " << endl;
		tokens.push_back(Token("COMMA", ",", lineCount));
		// consider using lineCount if lineCount isn't working
	} break;
	case '.': {
		////cout << "adding period " << endl;
		tokens.push_back(Token("PERIOD", ".", lineCount));
	} break;
	case '?': {
		////cout << "adding ? " << endl;
		tokens.push_back(Token("Q_MARK", "?", lineCount));
	} break;
/*	case "EOF": {
			////cout << "adding ? " << endl;
			tokens.push_back(token("EOF", "", lineCount));
	} break;*/
	case '(': {
		////cout << "adding ( " << endl;
		tokens.push_back(Token("LEFT_PAREN", "(", lineCount));
	} break;
	case ')': {
		////cout << "adding ) " << endl;
		tokens.push_back(Token("RIGHT_PAREN", ")", lineCount));
	} break;
	case '*': {
			////cout << "adding * " << endl;
			tokens.push_back(Token("MULTIPLY", "*", lineCount));
	} break;
	case '+': {
			////cout << "adding + " << endl;
			tokens.push_back(Token("ADD", "+", lineCount));
	} break;
	case '\'': {
		/*		if (isUndef(s)) {
			////cout << "is Undef, starting addUndeftring()..." << endl;
			s = addUndefString(s);
		}*/

/*		if (isUndef(s)) {
					////cout << "is Undef, starting addUndeftring()..." << endl;
		s = addUndefString(s);
		}*/

		//else {

		string str = "";
		int stringStartLine = lineCount;
		char curr = s.getChar();
		str += '\'';
		int i = 1;
		//bool going = true;
		//bool yesAdd = false;

		//while (lineCount != linesInFile){

		// added new line allowance

		while ((((s.peek()) == '\'') || (s.peek() == '\n')) && (lineCount != linesInFile + 1)){
			//cout << "1st while" << endl;
			if (s.peek() != '\n'){
			i++;
			}
			curr = s.proceed();
			//if (s.peek() != '\n'){
			str += curr;
			//}
			//str += curr;
			if (curr == '\n') lineCount++;
		}

		// if it's even, then you have a string
		if (isEven(i)){
			//cout << "isEven(i)" << endl;
			//str += '\'';
			////cout << "here" << endl;
			tokens.push_back(Token("STRING", str, stringStartLine));
			//going = false;
			break;
		}


		//else if ()

		// if it's not even you have the beginning of a string
		if (!(isEven(i))){
			//cout << "!isEven(i)" << endl;
			// collect all the things after the ' that aren't 's
			//if ((s.peek()) !=
			while ( ((s.peek()) != '\'') && (lineCount != linesInFile + 1)){
				//cout << "second while" << endl;
				////cout << "second" << endl;
				curr = s.proceed();
				str += curr;
				if (curr == '\n') lineCount++;
			}

			if (lineCount == linesInFile + 1){
				tokens.push_back(Token("UNDEFINED", str, stringStartLine));
				break;
				}

			if (s.peek() == '\''){
				//cout << "first peek" << endl;
				// get the last '
				curr = s.proceed();
				str += curr;
				i++;
			}

			// for '' case

			if (s.peek() == '\''){
				//cout << "second peek" << endl;
				curr = s.proceed();
				str += curr;
				i++;
				while ((s.peek() != '\'') && (lineCount != linesInFile + 1)){
					//cout << "third while" << endl;
					curr = s.proceed();
					str += curr;
					if (curr == '\n') lineCount++;
				}
				if (lineCount == linesInFile + 1){
					tokens.push_back(Token("UNDEFINED", str, stringStartLine));
					break;
					}
				curr = s.proceed();
				str += curr;
				i++;
			}

			// end of for '' case

		}

		if (isEven(i)){
			//cout << "isEven.. pushing string" << endl;
			tokens.push_back(Token("STRING", str, stringStartLine));
			break;
		}


		/*			if ((curr = s.proceed() == '\'')){
				tokens.push_back(token("STRING", str, stringStartLine));
				break;
			}*/
		else {
			//str += s.getChar();
			//cout << "isnot even.. pushing undef" << endl;
			tokens.push_back(Token("UNDEFINED", str, stringStartLine));
			break;
		}

		//}


	} break;
	case ':': {
		if(s.peek() == '-'){
			tokens.push_back(Token("COLON_DASH", ":-", lineCount));
			s.proceed();
		}
		else
		tokens.push_back(Token("COLON", ":", lineCount));
	} break;
	case '#': {
		string str = "";
		/*if (s.peek() == '|'){
			while (s.proceed() != '#'){
				if (s.getChar() == '\n') lineCount++;
				str = str + s.getChar();
			}
			//str = str + "#";
		}*/
		//else {
		str += s.getChar();
		if (s.peek() == '|') {
			char curr = s.proceed();
			str += curr;
			int commentStartLine = lineCount;
			while ((curr = s.proceed()) != '|' && (lineCount != linesInFile + 1)){
				if (s.getChar() == '\n') lineCount++;
				str += s.getChar();
			}
			//str += s.getChar();
			if (s.getChar() == '|'){
				str += s.getChar();
				if (s.peek() == '#'){
					char curr = s.proceed();
					str += curr;
					//tokens.push_back(Token("COMMENT", str, commentStartLine));
					//return s;
				}
			}

			else if (lineCount == linesInFile + 1){
				tokens.push_back(Token("UNDEFINED", str, commentStartLine));
				//break;
			}
			else {
				str += s.getChar();
				//tokens.push_back(Token("COMMENT", str, commentStartLine));
				//lineCount++;
				//s.proceed();
			}
		}
		else {
			while(s.proceed() != '\n'){
			str = str + s.getChar();
			}
			//tokens.push_back(Token("COMMENT", str, lineCount));
			lineCount++;
		}


		//lineCount++;
		//tokens.push_back(token("COMMENT", str, lineCount));
		//return s;
		//return SuperStream(" \n", 0);
		//str = '#' + str;

	} break;
	default: {
		tokens.push_back(Token("UNDEFINED", input, lineCount));
		//throw lineCount;
		//lineCount++;
	} break;
	}
	////cout << s.getChar() << endl;
	return s;
	}

/*SuperStream scanner::addUndefString(SuperStream s){
	string str = "";
	while (lineCount < linesInFile + 1){
		char curr = s.getChar();
		if (curr == '\n') lineCount++;
		str += curr;
	}
	tokens.push_back(token("UNDEFINED", str, s.lineNum));
	lineCount++;
	return s;
}*/

vector<Token> scanner::getTokens() {
	return tokens;
}
