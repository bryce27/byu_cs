#include "SuperStream.h"
#include <iostream>

SuperStream::SuperStream(string Stream, int LineNum){
	stream = Stream;
	lineNum = LineNum;
	index = 0;
}

//SuperStream::~SuperStream(){}

int SuperStream::getLineNum(){
	return lineNum;
}

char SuperStream::getChar(){
	return stream[index];
}

char SuperStream::proceed(){
	index++;
	return stream[index];
}

char SuperStream::peek(){
	return stream[index + 1];
}

char SuperStream::superPeek(){
	return stream[index + 2];
}

/*bool SuperStream::eof(){

}*/

SuperStream::~SuperStream(){}
