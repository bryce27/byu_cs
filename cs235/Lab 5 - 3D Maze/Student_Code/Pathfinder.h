#ifndef PATHFINDER_H_
#define PATHFINDER_H_

#include <string>
#include <vector>
#include <stdlib.h>
#include <stdio.h>
#include <time.h>
#include <cmath>
#include <sstream>
#include <fstream>
#include <stack>

#include "PathfinderInterface.h"

using namespace std;

class Pathfinder : public PathfinderInterface {
	public:
		Pathfinder();
		~Pathfinder();

		virtual string getMaze();
		virtual void createRandomMaze();
		virtual bool importMaze(string file_name);
		virtual vector<string> solveMaze();

	private:
		string currMaze;
		int Rows, Cols, Floors;

		string formatMaze(string input);
		bool verifyMaze(string Maze);
		int getValue(const string& Maze, int x, int y, int z);
		bool setValue(string& Maze, int x, int y, int z, int value);
		string createCoords(int x, int y, int z);
		bool check(string& Maze, stack<string>& path, int x, int y, int z);
};

#endif
