#include "Student.h"
//#include "GPA.h"

/*Student::Student(int first, string second, string third, string fourth) : StudentInterface(){
	studentID = first;
	name = second;
	address = third;
	phone = fourth;
	GPA = "0.00";
	numClasses = 0;
}

Student::~Student(void){}*/

/*
 * getID()
 *
 * Returns the ID of the Student.
 */
unsigned long long int Student::getID(){
	return studentID;
}

/*
 * getName()
 *
 * Returns the name of the Student
 */

string Student::getName(){
	return this->name;
}


/*
 * getGPA()
 *
 * Returns the string representation of the Student's GPA.
 */

string Student::getGPA(){
	stringstream ss;
	if (numClasses == 0){
		ss << setprecision(2) << fixed << GPA;
		return ss.str();
	}
	else {
		ss << setprecision(2) << fixed << (GPA / numClasses);
		return ss.str();
	}
}

/*
 * addGPA()
 *
 * Incorporates the given course grade into the Student's overall GPA.
 */

void Student::addGPA(double classGrade){

	// TA Notes
	// classes++
	// overall += classGrades;
	// GPA = overall/classes

	//cout << "\naddGPA: adding classgrade: " << classGrade << " for student: " << name << endl;
	//cout << "\nCurrent GPA: " << GPA << endl;
	//cout << "\nNumber of class: " << numClasses << endl;
	numClasses++;
	double newGPA = (classGrade + GPA);
	GPA = newGPA;
	//cout << "\nNew # of classes: " << numClasses << endl;
	//cout << "\nNew GPA: " << GPA << endl;

}

/*
 * toString()
 *
 * The student object will be put into string representation. Student info will be
 * ordered ID, name, address, phone number, and GPA. Each piece of information will
 * be on its own line. GPA will not have a newline following it and the precision
 * of the GPA will be rounded to two decimal places. For example,
 *
 * 123456789
 * Ben Thompson
 * 17 Russell St, Provo, UT 84606
 * 555-555-5555
 * 3.12
 *
 * Returns a string representation of the student object There is no trailing new line.
 */
string Student::toString(){
	////cout << "\ntoString()..." << endl;
	stringstream ss;
	ss << studentID;
	string ID = ss.str();

	/*stringstream gss;
	gss << setprecision(2) << fixed << getGPA();
	string stringGPA = gss.str();*/

	string output = ID + "\n" + name + "\n" + address + "\n" + phone + "\n" + getGPA();

	// deletes trailing newline if there is one
/*	if (!output.empty() && output[output.length()-1] == '\n') {
	    output.erase(output.length()-1);
	}*/

	//cout << output << "\n" << endl;
	return output;
}
