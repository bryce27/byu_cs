#include "GPA.h"
#include "Student.h"

int setNumDeleted = 0;
int mapNumDeleted = 0;
int setNumAdded = 0;
int mapNumAdded = 0;

bool is_number(string s){
	return( strspn( s.c_str(), "0123456789" ) == s.size() );
}

/*
 * getMap()
 *
 * Returns the map storing the student information.
 * The key of the map should be the student ID.
 */
map<unsigned long long int, StudentInterface*> GPA::getMap(){
	////cout << "\nRunning getMap()..." << endl;
	return myMap;
}

/*
 * getSet()
 *
 * Returns the set storing the student information.
 */
set<StudentInterface*,Comparator> GPA::getSet(){
	////cout << "\nRunning getSet()..." << endl;
	return mySet;
}

bool isValidStudentFile(string file_name){
	ifstream infile (file_name.c_str());

	string firstLine;

	if (infile.fail()){
		return false;
	}

	if (!infile.good()){
		return false;
	}

	string StudentID;
	string name;
	string address;
	string phone;

	if (infile.is_open()){
		while (infile.good()){
			getline(infile, StudentID);
			if (!is_number(StudentID)){
				////cout << "\nStudent ID is not a number...returning false..." << endl;
				return false;
			}
			if (StudentID == ""){
				break;
			}
			//////cout << "\nID: " << StudentID << endl;
			getline(infile, name);
			//////cout << "Name: " << name << endl;
			getline(infile, address);
			//////cout << "Address: " << address << endl;
			getline(infile, phone);
			//////cout << "Phone: " << phone << endl;
		}
		return true;
	}
	else {
		////cout << "\nUnable to open file" << endl;
		return false;
	}

}

bool GPA::importSet(string file_name){
	//for (set <SI*, Comparator>::iterator it = s.begin(); it != s.end(); it++){
	////cout << "\nImporting set..." << endl;
	string StudentID;
	string name;
	string address;
	string phone;
	ifstream infile (file_name.c_str());

	if (infile.is_open()){
		while (infile.good()){
			getline(infile, StudentID);
			if (!is_number(StudentID)){
				////cout << "\nStudent ID is not a number...returning false..." << endl;
				return false;
			}
			if (StudentID == ""){
				break;
			}
			//////cout << "\nID: " << StudentID << endl;
			getline(infile, name);
			//////cout << "Name: " << name << endl;
			getline(infile, address);
			//////cout << "Address: " << address << endl;
			getline(infile, phone);
			//////cout << "Phone: " << phone << endl;
			unsigned long long int ID = atoll(StudentID.c_str());
			//cout << "\nimportSet() making new student" << endl;
			StudentInterface* newStudent = new Student(ID, name, address, phone);
			setNumAdded++;
			//////cout << "Inserting into set..." << endl;
			mySet.insert(newStudent);
			////cout << "\nNew student added. " << endl;
			//newStudent->Student::toString();
		}
		return true;
	}
	else {
		////cout << "\nUnable to open file" << endl;
		return false;
	}
}

bool GPA::importMap(string file_name){
	//for (map <SI*, comparator>::iterator it = s.begin(); it != s.end(); it++){
	////cout << "\nImporting map..." << endl;
	string StudentID;
	string name;
	string address;
	string phone;
	ifstream infile (file_name.c_str());

	if (infile.is_open()){
		while (infile.good()){
			getline(infile, StudentID);
			if (!is_number(StudentID)){
				////cout << "\nStudent ID is not a number...returning false..." << endl;
				return false;
			}
			if (StudentID == ""){
				break;
			}
			//////cout << "\nID: " << StudentID << endl;
			getline(infile, name);
			//////cout << "Name: " << name << endl;
			getline(infile, address);
			//////cout << "Address: " << address << endl;
			getline(infile, phone);
			//////cout << "Phone: " << phone << endl;
			unsigned long long int ID = atoll(StudentID.c_str());
			//////cout << "\nID after converted to int is: " << ID << endl;
			//cout << "\nimportMap() making new student" << endl;
			StudentInterface* newStudent = new Student(ID, name, address, phone);
			mapNumAdded++;
			myMap.insert(myMap.end(), pair<unsigned long long int, StudentInterface*>(ID, newStudent));
			////cout << "\nNew student added. " << endl;
			//newStudent->Student::toString();
			//myMap[ID] = new Student(StudentID, name, address, phone);
		}
		return true;
	}
	else {
		////cout << "\nUnable to open file" << endl;
		return false;
	}
}

/*
 * importStudents()
 *
 * Read in and parse through the given files. Each part of an entry in a
 * file is given on a separate line in the file. Student ID is first, name is
 * second, address is third, and phone is last. There are no blank lines between
 * students. The following is an example file:
 *
 *	 5291738600
 * 	 Dick B. Smith
 * 	 879 Maple Road, Centralia, Colorado 24222
 * 	 312-000-1000
 * 	 9251738707
 *	 Harry C. Anderson
 *	 635 Main Drive, Midville, California 48444
 * 	 660-050-2200
 *
 * If a valid file is given, the new Students should be added to the existing
 * map and set.
 *
 * If an invalid file name is given or if there is a missing entry for a student,
 * return false. Duplicate student ID numbers and duplicate students will not be
 * tested for. There will never be a student that exists in the map and set. If
 * the function returns false, then no changes should have been made to the
 * existing map or set.
 *
 * The key of the map should be the student ID.
 *
 * Returns false if an invalid filename is given or if there is a missing entry for a
 * student, otherwise true.
 */
bool GPA::importStudents(string mapFileName, string setFileName){
	////cout << "\nStarting importStudents..." << endl;
	if (!isValidStudentFile(mapFileName) || !isValidStudentFile(setFileName)){
		////cout << "map file: " << mapFileName << " is not valid" << endl;
		////cout << "set file: " << setFileName << " is not valid" << endl;
 		return false;
	}
	else {
		bool mapWorked = GPA::importMap(mapFileName);
		bool setWorked = GPA::importSet(setFileName);
		if (!mapWorked) return false;
		if (!setWorked) return false;
		return true;
	}
}

bool isValidGradeFile(string file_name){
	ifstream infile (file_name.c_str());

	string firstLine;

	if (infile.fail()){
		return false;
	}

	if (!infile.good()){
		return false;
	}

	string ID, course, grade;

	if (infile.is_open()){
		while (infile.good()){
			getline(infile, ID);
			if (!is_number(ID)){
				////cout << "\nStudent ID is not a number...returning false..." << endl;
				return false;
			}
			if (ID == ""){
				break;
			}
			//////cout << "\nID: " << StudentID << endl;
			getline(infile, course);
			//////cout << "Name: " << name << endl;
			getline(infile, grade);
		}
		return true;
	}
	else {
		////cout << "\nUnable to open file" << endl;
		return false;
	}

}

double gradeToGPA(string g){

	if  (g == "A")
		return 4.00;
	else if (g == "A-")
		return 3.70;
	else if (g == "B+")
		return 3.40;
	else if (g == "B")
		return 3.00;
	else if (g == "B-")
		return 2.70;
	else if (g == "C+")
		return 2.40;
	else if (g == "C")
		return 2.00;
	else if (g == "C-")
		return 1.70;
	else if (g == "D+")
		return 1.40;
	else if (g == "D")
		return 1.00;
	else if (g == "D-")
		return 0.70;
	else if (g == "E")
		return 0.00;
	}

string GPAtoString(int studentID, double GPA, string name){
	stringstream ss;
	ss << studentID;
	string ID = ss.str();

	// GPA precision
	stringstream gss;
	gss << setprecision(3) << GPA;
	string precisionGPA = gss.str();
	string output = ID + " " + precisionGPA + " " + name + "\n";
	return output;
}


/*
 * importGrades()
 *
 * Read in and parse through the given file. Each part of an entry in the file
 * is given on a separate line in the file. Student ID is first, course is
 * second, and grade is last. There are no blank lines between entries. The
 * following is an example file:
 *
 * 	5291738860
 * 	CHEM134
 * 	A
 * 	9251734870
 * 	BOT180
 * 	B
 * 	9251733870
 * 	PE273
 * 	D+
 * 	5291738760
 * 	HIS431
 *  	A-
 *
 * Compute the GPA by finding the average of all the grades with a matching student ID
 * in the Grade file. The GPA is calculated by taking a Student's total sum GPA and
 * dividing by the number of classes taken.
 *
 *  If the given student ID has no matching ----------------------------------------------- ???
 * grades in the Grade file, the GPA is 0.00.  -------------------------------------------- ???
 *
 * It is not necessary to store the course
 * names so long as the total number of courses taken is counted.
 *
 * You may assume that the given student ID exists in the map or set.
 *
 * Use the following point values for each grade.
 *
 *		  A = 4.0  A- = 3.7
 *	B+ = 3.4  B = 3.0  B- = 2.7
 *	C+ = 2.4  C = 2.0  C- = 1.7
 *	D+ = 1.4  D = 1.0  D-{}.7
 *		  E{}.0
 *
 * Returns false if an invalid filename is given, otherwise true.
 */
bool GPA::importGrades(string fileName){
/*	if (!isValidGradeFile(fileName)){
		////cout << "\nimportGrades(): File not valid, return false" << endl;
		return false;
	}
	else {*/
		string StudentID;
		string course;
		string grade;
		ifstream infile (fileName.c_str());

		if (infile.is_open()){
			while (infile.good()){
				getline(infile, StudentID);
				if (StudentID == "") {
					////cout << "\nBreaking while loop" << endl;
					break;
				}
				getline(infile, course);
				getline(infile, grade);

			unsigned long long int ID = atoll(StudentID.c_str());
				for (set<StudentInterface*, Comparator>::iterator it = mySet.begin(); it != mySet.end(); it++){
					StudentInterface* temp = *it;
					if (temp->getID() == ID){
						temp->addGPA(gradeToGPA(grade));
					}
				}

				for (map<unsigned long long int, StudentInterface*>::iterator it = myMap.begin(); it != myMap.end(); it++){
					if (it->first == ID){
						it->second->addGPA(gradeToGPA(grade));
					}
				}
			}
			return true;
		}
		// else return false
	/*}*/
}

/*
 * querySet()
 *
 * Read in and parse through the given file. The 'Query' file contains a list of
 * student ID numbers. Each entry in the Query file is a student ID given on a
 * line by itself. You are to compute and report the GPA for each of the students
 * listed in the Query file. The following is an example Query file:
 *
 * 	5291738860
 * 	9251733870
 *
 * For each student ID given in the Query file, use the student information stored in
 * your set to compute the GPA for the student and create an output string which
 * contains the student ID, GPA, and name. If the given student ID does not match any
 * student, do not give any output for that student ID. Each line of the output string
 * contains student ID, GPA, and name as shown:
 *
 * 	5291738860 2.85 Dick B. Smith
 *	9251733870 3.00 Harry C. Anderson
 *
 * Return a string representation of the given query. If an invalid file name is given,
 * then return an empty string. The precision of the GPA will be rounded to two decimal places.
 * There will be a trailing new line.
 */
string GPA::querySet(string fileName){
	////cout << "\nQuerySet() starting..." << endl;
	string StudentID;
	ifstream infile (fileName.c_str());
	//ostringstream out;
	//string out = "";

	if (infile.is_open()){
		stringstream out;
		//string out = "";
		while (infile.good()){
			getline(infile, StudentID);
			/*if (StudentID == ""){
				return "";
			}*/

			unsigned long long int ID = atoll(StudentID.c_str());
			for (set<StudentInterface*, Comparator>::iterator it = mySet.begin(); it != mySet.end(); it++){
				////cout << "QuerySet() for loop ()" << endl;
				//stringstream out;
				StudentInterface* temp = *it;
				if (temp->getID() == ID){
					//return GPAtoString(temp->getID(), temp->getGPA(), temp->getName());
					stringstream ss;
					ss << temp->getID();
					string ID = ss.str();

					// GPA precision
					stringstream gss;
					gss << setprecision(2) << fixed << temp->getGPA();
					string precisionGPA = gss.str();
					string output = ID + " " + precisionGPA + " " + temp->getName() + "\n";
					//out = out + output;
					out << output;
					}
				}
		}
		return out.str();
		//return "hey";
	}
	else return "";
	//return out;
	//else return false;
}

/*
 * queryMap()
 *
 * Read in and parse through the given file. The 'Query' file contains a list of
 * student ID numbers. Each entry in the Query file is a student ID given on a
 * line by itself. You are to compute and report the GPA for each of the students
 * listed in the Query file. The following is an example Query file:
 *
 * 	5291738860
 * 	9251733870
 *
 * For each student ID given in the Query file, use the student information stored in
 * your map to compute the GPA for the student and create an output string which
 * contains the student ID, GPA, and name. If the given student ID does not match any
 * student, do not give any output for that student ID. Each line of the output string
 * contains student ID, GPA, and name as shown:
 *
 * 	5291738860 2.85 Dick B. Smith
 *	9251733870 3.00 Harry C. Anderson
 *
 * Return a string representation of the given query. if an ivalid file name is given,
 * then return an empty string. The precision of the GPA will be rounded to two decimal places.
 * There will be a trailing new line.
 */
string GPA::queryMap(string fileName){
	////cout << "\nQueryMap() starting..." << endl;
	string StudentID;
	ifstream infile (fileName.c_str());

	if (infile.is_open()){
		////cout <<"\nfile is open in query map" << endl;
		stringstream out;
		while (infile.good()){
			////cout << "\nqueryMap() while loop" << endl;
			getline(infile, StudentID);
			/*if (StudentID == ""){
				return "";
			}*/

			unsigned long long int ID = atoll(StudentID.c_str());
			for (map<unsigned long long int, StudentInterface*>::iterator it = myMap.begin(); it != myMap.end(); it++){
				////cout << "QueryMap() for loop ()" << endl;
				//StudentInterface* temp = *it;
				if (it->first == ID){
					////cout <<"\nQueryMap if statement.." << endl;
					//return GPAtoString(it->second->getID(), it->second->getGPA(), it->second->getName());
					stringstream ss;
					ss << it->first;
					string IDstring = ss.str();

					// GPA precision
					stringstream gss;
					gss << setprecision(2) << fixed << it->second->getGPA();
					string precisionGPA = gss.str();
					string output = IDstring + " " + precisionGPA + " " + it->second->getName() + "\n";
					out << output;
				}
			}
		}
		////cout << "\nReturning" << endl;
		//cout << "\nsetNumDeleted: " << setNumDeleted << "\nmapNumDeleted: " << mapNumDeleted << "\nsetNumAdded: " << setNumAdded << "\nmapNumAdded: " << mapNumAdded << endl;
		return out.str();
	}
	else return "";
}

/*
 * Clears the students from the map and set.
 */
void GPA::clear(){

	for (map<unsigned long long int, StudentInterface*>::iterator it = myMap.begin(); it != myMap.end(); it++){
		//delete it->first;
		//cout << "\nclear() Map: deleting student" << endl;
		mapNumDeleted--;
		delete it->second;
	}
	//cout << "\nmyMap.clear()" << endl;
	myMap.clear();

	// check this one
	for (set<StudentInterface*, Comparator>::iterator it = mySet.begin(); it != mySet.end(); it++){
		StudentInterface* temp = *it;
		//cout << "\nclear() Set: deleting student" << endl;
		setNumDeleted--;
		delete temp;
	}

	//cout << "\nmySet.clear()" << endl;
	mySet.clear();

}
