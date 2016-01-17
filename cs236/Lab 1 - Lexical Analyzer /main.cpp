#include <cctype>
//#include "token.h"
#include "scanner.h"
#include <vector>


int main(int argc, char* argv[]){

	//cout << "File Name: " << endl;
	string file_name = argv[1];
	//cin >> file_name;
	scanner Scanner(file_name);
	//scanner Scanner("text.txt");
	stringstream outputStream;
	vector<token> tokenVector = Scanner.getTokens();
	for (int i = 0; i < tokenVector.size(); i++){
		//cout << "in for loop" << endl;
		outputStream << tokenVector[i].printTokens();
	}
	cout << outputStream.str();
	cout << "Total Tokens = " << tokenVector.size();

	return 0;
};
