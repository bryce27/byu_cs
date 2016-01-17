#include "Pathfinder.h"

#include <iostream>

using namespace std;

Pathfinder::Pathfinder() {
	//srand(time(NULL));
	Rows = 5;
	Cols = 5;
	Floors = 5;
}
Pathfinder::~Pathfinder() {}

string Pathfinder::getMaze() {
	//cout << "getting maze: " << currMaze << endl;
	if (currMaze == ""){
		//cout << "maze is empty--running format" << endl;
		return this->formatMaze(string(125, '1'));
	}
	//else{
		return this->formatMaze(currMaze);
	//}
}

void Pathfinder::createRandomMaze() {
	stringstream ss;
	int one_count = 0;
	int zero_count = 0;
	int variance = 5; // max variance allowed
	ss << 1; // (0, 0, 0)
	for (int i=0; i<((Rows*Cols*Floors) - 2); i++) {
		int randNum = rand() % 2;
		//if (randNum < (RAND_MAX/2)) randNum = 0;
		//else randNum = 1;

		// keep 1's and 0's about the same count
		if (abs(one_count - zero_count) > variance) {
			if (one_count > zero_count){
				randNum = 0;
			}
			else {
				randNum = 1;
			}
		}

		// count how many of each
		if (randNum == 1) ++one_count;
		else ++zero_count;

		ss << randNum;
	}
	ss << 1; // (4, 4, 4)

	currMaze = ss.str();
}

bool Pathfinder::importMaze(string file_name) {
	//cout << "running import on" << file_name << endl;
	bool valid = true;
	stringstream Maze;
	ifstream file (file_name.c_str());
	//ifstream file (file_name.c_str(), ifstream::in);
	//if (myfile.is_open()){
	if (file.is_open()) {
		string line;
		int row_count = 0;
		//while (getline(myfile, line)){
		while (getline(file, line) && valid) {
			if (line == ""){
				continue;
			}
			stringstream ss(line);
			string token;
			int col_count = 0;
			while (ss >> token) {
				if (token == "0" || token == "1") {
					Maze << token;
					++col_count;
				}
			}
			// cout << stringMaze.str() << " col_count: " << row_count << "/" << col_count << endl;
			valid = (col_count == Cols);
			++row_count;
		}
		valid = ((row_count == (Rows*Floors)) && this->verifyMaze(Maze.str()));
		//valid = ((row_count == (Rows + 1)) && this->verifyMaze(Maze.str()));
		//cout << "row_count = " << row_count << endl;
		//cout << "Rows = " << Rows << endl;
		//cout << "Rows*Floors = " << Rows*Floors << endl;
		if ((row_count == (Rows*Floors)) && this->verifyMaze(Maze.str())){
			//cout << "MAZE IS VALID!!!!" << endl;
		}

		if (valid){
			//cout << "imported maze is valid!" << endl;
			currMaze = Maze.str();
		}

		//cout << "currMaze: " << currMaze << endl;

		return valid;
	} else {
		return false;
	}
}

vector<string> Pathfinder::solveMaze() {
	string MazeCopy = currMaze;
	stack<string> tmp;
	vector<string> path;
	// int x = 4;
	// int y = 2;
	// int z = 1;
	// cout << "(" << x << "," << y << "," << z <<"): " << this->getValue(currentStringMaze, x, y, z) << endl;

	if (check(MazeCopy, tmp, 0, 0, 0)) {
		int count = 1;
		while (!tmp.empty()) {
			path.push_back(tmp.top());
			// cout << "STEP "<< count <<": " << tmp.top() << endl;
			tmp.pop();
			count++;
		}
	}

	return path;
}

/** private functions **/

string Pathfinder::formatMaze(string input) {
	//cout << "running format" << endl;
	if (!this->verifyMaze(input)){
		return input;
	}
	string maze;
	for (int i=0; i < (Rows * Floors); i++) {
		for (int j=0; j < Cols; j++) {
			maze += input.substr((Cols*i) + j, 1);
			// add a space unless it's the
			// last column
			if ((j+1) % Cols != 0){
				maze += " ";
			}
		}
		maze += '\n';
		// break up each level
		if ((i+1) % Cols == 0 && (i+1) != (Rows*Floors)){
			maze += '\n';
		}
	}
	//cout << "formatted maze: " << maze << endl;
	return maze;
}

bool Pathfinder::verifyMaze(string Maze) {
	//cout << "verifying" << endl;
	bool valid = true;
	valid = (Maze.length() == /*Rows * Cols * Floors*/125);
	valid = (this->getValue(Maze, 0, 0, 0) == 1 && this->getValue(Maze, 4, 4, 4) == 1);
	return valid;
}

int Pathfinder::getValue(const string& Maze, int x, int y, int z) {
	//cout << "getting value" << endl;
	if ((x+1) < 1 || (x+1) > Cols){
		return -1;
	}
	if ((y+1) < 1 || (y+1) > Rows){
		return -2;
	}
	if ((z+1) < 1 || (z+1) > Floors){
		return -3;
	}
	int index = (z*Rows*Cols) + (y*Cols) + x;

	stringstream ss;
	ss << Maze.substr(index, 1);
	int val;
	ss >> val;

	return val;
}

bool Pathfinder::setValue(string& Maze, int x, int y, int z, int value) {
	//cout << "setting value" << endl;
	if ((x+1) < 1 || (x+1) > Cols){
		return false;
	}
	if ((y+1) < 1 || (y+1) > Rows){
		return false;
	}
	if ((z+1) < 1 || (z+1) > Floors){
		return false;
	}
	int index = (z*Rows*Cols) + (y*Cols) + x;

	stringstream ss;
	ss << value;
	char newValue;
	ss >> newValue;

	Maze[index] = newValue;

	return true;
}

string Pathfinder::createCoords(int x, int y, int z) {
	stringstream ss;
	ss << "(" << x << ", " << y << ", " << z << ")";
	return ss.str();
}

bool Pathfinder::check(string& Maze, stack<string>& path, int x, int y, int z) {
	//cout << "checking" << endl;
	if (x == 4 && y == 4 && z == 4) {
		// positive base case
		path.push(this->createCoords(x, y, z));
		return true;
	} else {
		// negative cases
		if ((x+1) < 1 || (x+1) > Cols){
			return false;
		}
		if ((y+1) < 1 || (y+1) > Rows){
			return false;
		}
		if ((z+1) < 1 || (z+1) > Floors){
			return false;
		}
		if (this->getValue(Maze, x, y, z) == 1){
			this->setValue(Maze, x, y, z, 2);
		}
		else {
			return false;
		}
	}

	// recursive
	if (this->check(Maze, path, x-1, y, z) ||
		this->check(Maze, path, x+1, y, z) ||
		this->check(Maze, path, x, y-1, z) ||
		this->check(Maze, path, x, y+1, z) ||
		this->check(Maze, path, x, y, z-1) ||
		this->check(Maze, path, x, y, z+1)) {
			path.push(this->createCoords(x, y, z));
			return true;
	}
	return false;
}
































