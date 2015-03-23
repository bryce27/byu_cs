#pragma once
#include "StudentInterface.h"
#include <fstream>
#include <cstring>
#include <cstdlib>
#include <iostream>
#include <stdlib.h>
#include <sstream>
#include <iomanip>


class Student : public StudentInterface
{
	public:

		int studentID;
		string name;
		double GPA;
		string address;
		string phone;
		double numClasses;


		Student(int first, string second, string third, string fourth) : StudentInterface(){
			studentID = first;
			name = second;
			address = third;
			phone = fourth;
			GPA = 0.00;
			numClasses = 0.00;
		};

		~Student(void){};

/*		Student(int first, string second, string third, string fourth);

		~Student(void);*/

		/*
		 * getID()
		 *
		 * Returns the ID of the Student.
		 */
		unsigned long long int getID();

		/*
		 * getName()
		 *
		 * Returns the name of the Student
		 */

		string getName();

		/*
		 * getGPA()
		 *
		 * Returns the string representation of the Student's GPA.
		 */

		string getGPA();

		/*
		 * addGPA()
		 *
		 * Incorporates the given course grade into the Student's overall GPA.
		 */

		void addGPA(double classGrade);

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
		string toString();

};



