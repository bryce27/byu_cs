#include "QS.h"

#include <algorithm>
#include <iostream>
#include <string>

//int* myArray;
//int [9] myArray;
int* myArray;
int sizePointer = -1;
int formalSize = 0;

QS::QS(){
	myArray = NULL;
}
QS::~QS() {
	clear();
}

void swapVal(int first, int second){
	int temp = myArray[first];
	myArray[first] = myArray[second];
	myArray[second] = temp;
}

void QS::sort(int left, int right){
	//cout << "\nStarting sort()..." << endl;
	//if (size == 1) return;
	// if (size == 2) compare and maybe swap

	// if size is 2, compare and swap if necessary
	if (right - left == 1){
		if (myArray[right] > myArray[left]){
			return;
		}
		else {
			swapVal(right, left);
			return;
		}
	}

	if (right == left) return;

	if (left < 0 || right > sizePointer - 1 || right < left || left > right) return;
	int pivotIndex = medianOfThree(left, right);
	int newIndex = partition(left, right, pivotIndex);
	sort(left, newIndex - 1);
	sort(newIndex + 1, right);

}

/*
 * sortAll()
 *
 * Sorts elements of the array.  After this method is called, every
 * element in the array is less than or equal to the following element.
 *
 * Does nothing if the array is empty.
 */
void QS::sortAll(){
	//cout << "\nsortAll()..." << endl;
	if (sizePointer < 0) return;
	////cout << "\nsortAll()...size is: " << sizePointer << endl;
	//int newIndex = medianOfThree(0, sizePointer - 1);
	sort(0, sizePointer -1);

}

/*
 * medianOfThree()
 *
 * Performs median-of-three pivot selection from among the values in
 * the array between the two given indices. Median-of-three pivot
 * selection will sort the first, middle, and last elements in a given
 * array with respect to each other. After this method is called,
 * data[first] <= data[middle] <= data[last], where middle =
 * (first + last)/2.
 *
 * Returns -1 if the array is empty, if either of the given integers
 * is out of bounds, or if the first integer is not less than the second
 * integer.
 *
 * @param left
 * 		the left boundary for the subarray from which to find a pivot
 * @param right
 * 		the right boundary for the subarray from which to find a pivot
 * @return
 *		the index of the pivot{} -1 if provided with invalid input
 */
int QS::medianOfThree(int left, int right){
	////cout << "\nmedianOfThree()..." << "left: " << left << " right: " << right << endl;
	if (sizePointer < 0) return -1;
	if (left > right || left < 0 || right < left || right > sizePointer - 1 || left == right) return -1;

	int pIndex = (left + right) / 2;
	//if (left == pIndex || right == pIndex);
	if (!(myArray[left] < myArray[pIndex])) swapVal(left, pIndex);
	if (!(myArray[pIndex] < myArray[right])) swapVal(right, pIndex);
	if (!(myArray[left] < myArray[pIndex])) swapVal(left, pIndex);

	////cout << "\npIndex is " << pIndex << endl;
	////cout << "left: " << left << " right: " << right << endl;
	//partition(left, right, pIndex);
	getArray();
	return pIndex;

}

/*
 * Partitions a subarray around a pivot value selected according to
 * median-of-three pivot selection.
 *
 * The values which are smaller than the pivot should be placed to the left
 * of the pivot{} the values which are larger than the pivot should be placed
 * to the right of the pivot.
 *
 * Does nothing and returns -1 if the array is null, if either of the
 * given integers is out of bounds, or if the first integer is not less than
 * the second integer, or if the pivot is not between the two boundaries.
 *
 * @param left
 * 		the left boundary for the subarray to partition
 * @param right
 * 		the right boundary for the subarray to partition
 * @param pivotIndex
 * 		the index of the pivot in the subarray
 * @return
 *		the pivot's ending index after the partition completes{} -1 if
 * 		provided with bad input
 */
int QS::partition(int left, int right, int pivotIndex){

	if (myArray == NULL){
		//cout << "\nArray is Null" << endl;
		return -1;
	}

	//cout << "\npartition()..." << endl;
	getArray();
	//cout << "\nleft: " << left << "\nright: " << right << "\npivotIndex: " << pivotIndex << endl;
	if ((sizePointer < 0) || (formalSize <= 0)){
		//cout << "\nsizePointer less than 0" << endl;
		return -1;
	}
	if ((left < 0) || (right < 0)){
		//cout << "\nleft or right is less than 0" << endl;
		return -1;
	}
	if ((right > sizePointer - 1)){
		//cout << "\nright is too big" << endl;
		return -1;
	}
	if (!(left < right)){
		//cout << "\nleft is too big" << endl;
		return -1;
	}
	if ((!(pivotIndex <= right)) || (!(pivotIndex >= left)) ){
		//cout << "\npivotIndex is not in bounds" << endl;
		return -1;
	}

	//cout << "\nswapping right: " << myArray[right] << " and pivot: " << myArray[pivotIndex] << endl;
	swapVal(right, pivotIndex);
	int i = left;
	int j = right - 1;
	getArray();


	// this while loop only happens once
	while (i < j){
		//cout << "\npartition() running while !(i >= j)..." << endl;

		while ((myArray[i] < myArray[right])){
			//cout << "\nvalue of i: " << myArray[i] << endl;
			//cout << "\nvalue of right: " << myArray[right] << endl;
			//cout << "\nincrementing i to " << i + 1 << endl;
			i++;
		}

		while ((myArray[j] > myArray[right])){
			//cout << "\nvalue of j: " << myArray[j] << endl;
			//cout << "\nvalue of right: " << myArray[right] << endl;
			//cout << "\ndecrementing j to " << j - 1 << endl;
			j--;
		}

		if (i < j) {
			//cout << "\ni and j switching places..." << endl;
			swapVal(i, j);
			//return i;
			//cout << "\ni: " << i << "\nj: " << j << endl;
		}
	}

	if (i >= j){
		//cout <<"swaping i: " << i << " and right: " << right << endl;
		swapVal(i, right);
		//pivotIndex = medianOfThree(left, pivotIndex - 1);
		//pivotIndex = medianOfThree(pivotIndex + 1, right);
		//cout << "\npartition()...returning i: " << i << endl;
		return i;
	}
}

/*
 * Gets the array of values and puts them into a string. For example: if my array 			 * looked like {5,7,2,9,0}, then the string to be returned would look like "5,7,2,9,0" 			 * with no trailing comma.
 *
 * Does nothing and returns an empty string, if the array is null or empty.
 *
 * @return
 *		the string representation of the current array
 */
string QS::getArray(){
	//cout << "\ngetArray()..." << endl;
	////cout << "\ngetArray(): size is: " << sizePointer << endl;
	if (sizePointer < 0 || formalSize <= 0){
		return "";
	}
	stringstream ss;
	for (int i = 0; i != sizePointer; i++){
		ss << myArray[i] << ",";
		// remove trailing comma
	}
	string result = ss.str();
	// take off the trailing comma
	string output = result.substr(0, result.size()-1);
	//cout << "\nCurrent Array: " << output << endl;
	return output;
}

/*
 * Gets the current size of the current array.
 *
 * @return
 * 		the current size
 */
int QS::getSize(){
	////cout << "\ngetSize()..." << endl;
	return formalSize;
}

/*
 * Adds the given value to the array.
 */
void QS::addToArray(int value){
	////cout << "\naddToArray(): adding " << value << endl;
	//if (sizePointer < 0) return;

	if (myArray == NULL) return;

	myArray[sizePointer] = value;
	sizePointer++;

	//Alternatively...
/*	int* temp = new int[sizePointer + 1];
	temp = myArray;
	myArray = temp;
	myArray[sizePointer + 1] = value;
	sizePointer++;*/
	//myArray[myArray.size()] = value;
}

/*
 * Creates an array of with the given size.
 *
 * Returns false if the given value is non-positive, true otherwise.
 *
 * @param
 *		size of array
 * @return
 *		true if the array was created, false otherwise
 */
bool QS::createArray(int size){
	clear();
	//cout << "\ncreateArray()..." << endl;
	if (size < 0){
		//cout << "\ncreateArray() returning false" << endl;
		return false;
	}
	else {
		myArray = new int[size];
		sizePointer = 0;
		formalSize = size;
		////cout << "\ncreateArray() returning true" << endl;
		return true;
	}
}

/*
 * Clears the array.
 */
void QS::clear(){
	if (myArray == NULL){
		return;
	}
	//cout << "\nclear()..." << endl;
	//array.fill(0);
	delete [] myArray;
	sizePointer = -1;
	formalSize = 0;
	myArray = NULL;
}
