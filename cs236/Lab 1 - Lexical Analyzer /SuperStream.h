
#pragma once
#include <string>

using namespace std;

// making a stream a class gives me more features to look at the stream
class SuperStream {
public:
	string stream;
	int lineNum;
	int index;

	// public:
	// constructor
	SuperStream(string stream, int lineNum);
	virtual ~SuperStream();
	int getLineNum();
	char peek();
	char superPeek();
	char getChar();
	char proceed();
	//bool eof();
};
