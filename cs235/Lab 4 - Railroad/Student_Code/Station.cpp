#include "Station.h"

int curr = -1;

Station::Station(){}
Station::~Station(){}


//Part 1--------------------------------------------------------------
/**
 * Let another car arrive at the station and become the current car.
 * Do not allow a new car to arrive if any of the following conditions apply:
 * 1.	There is already a current car
 * 2.	The new car's ID already exists in any structure
 * 3.	The new car's ID is negative
 *
 * @param car the ID of the car arriving at the station
 * @return true if the car successfully arrived; false otherwise
 */
bool Station::addToStation(int car) {
	if (curr > 0){
		//cout << "there is already a car in the station -- can't add" << endl;
		return false;
	}
	else if (s.hasDup(car) || q.hasDup(car) || d.hasDup(car) || i.hasDup(car) || o.hasDup(car)){
		//cout << "There's a duplicate, not adding to station" << endl;
		return false;
	}
	else if (car <= 0){
		//cout << "car ID is :" << car << " -- negative or zero -- not allowed in station" << endl;
		return false;
	}
	else {
	//cout << "adding car: " << car << " to station" << endl;
	curr = car;
	return true;
	}
}

/**
 * Returns the ID of the current car.
 * Return -1 if there is no current car.
 *
 * @return the ID of the current car; -1 if there is no current car
 */
int Station::showCurrentCar() {
	if (curr == -1 || curr == 0){
		return -1;
	}
	//cout << "current car is " << curr << endl;
	return curr;
}


/**
 * Removes the current car from the station.
 * Does nothing if there is no current car.
 *
 * @return true if the current car successfully left; false otherwise
 */
bool Station::removeFromStation() {
	if (curr <= 0){
		//cout << "no current car -- cannot remove" << endl;
		return false;
	}
	else{
		//cout << "removing car: " << curr << " from station" << endl;
		curr = -1;
		return true;
}
}

//Part 2--------------------------------------------------------------
/**
 * Adds the current car to the stack.  After this operation, there should be no current car.
 * Does nothing if there is no current car or if the stack is already full.
 *
 * @return true if the car is successfully added to the stack; false otherwise
 */
bool Station::addToStack() {
	if (s.size() >= 5){
		//cout << "Stack is full" << endl;
		return false;
	}
	else if (curr == -1){
		return false;
	}
	else {
	//cout << "adding car: " << curr << " to stack" << endl;
	s.push(curr);
	curr = -1;
	return true;
}
}

/**
 * Removes the first car in the stack and makes it the current car.
 * Does nothing if there is already a current car or if the stack already empty.
 *
 * @return true if the car is successfully removed from the stack; false otherwise
 */
bool Station::removeFromStack() {
	if (s.size() < 1){
		return false;
	}
	else if (curr != -1){
		return false;
	}
	else{
	//cout << "removing car: " << s.top() << " from stack" << endl;
	curr = s.top();
	s.pop();
	//cout << "new stack top is " << s.top() << endl;
	return true;
	}
}

/**
 * Returns the ID of the first car in the stack.
 *
 * @return the ID of the first car in the stack; -1 if the stack is empty
 */
int Station::showTopOfStack() {
	if (s.size() < 1){
		return -1;
	}
	else {
		//cout << "top of stack is " << s.top() << endl;
		return s.top();
	}
}

/**
 * Returns the number of cars in the stack.
 *
 * @return the number of cars in the stack
 */
int Station::showSizeOfStack() {
	//cout << "stack size is: " << s.size() << endl;
	return s.size();
}

//Part 3--------------------------------------------------------------
/**
 * Adds the current car to the queue.  After this operation, there should be no current car.
 * Does nothing if there is no current car or if the queue is already full.
 *
 * @return true if the car is successfully added to the queue; false otherwise
 */
bool Station::addToQueue() {
	if (q.size() >= 5){
		//cout << "queue is full" << endl;
		return false;
	}
	else if (curr == -1){
		return false;
	}
	else {
		//cout << "adding to queue: " << curr << endl;
		q.add(curr);
		curr = -1;
		return true;
	}
}

/**
 * Removes the first car in the queue and makes it the current car.
 * Does nothing if there is already a current car or if the queue already empty.
 *
 * @return true if the car is successfully removed from the queue; false otherwise
 */
bool Station::removeFromQueue() {
	if (q.size() < 1){
		return false;
	}
	else if (curr != -1){
		return false;
	}
	else{
		//cout << "removing car: " << q.top() << " from the queue" << endl;
		curr = q.top();
		q.remove();
		return true;
	}
}

/**
 * Returns the ID of the first car in the queue.
 *
 * @return the ID of the first car in the queue; -1 if the queue is empty
 */
int Station::showTopOfQueue() {
	if (q.size() < 1){
			return -1;
		}
		else {
			//cout << "top of queue is " << q.top() << endl;
			return q.top();
		}
}

/**
 * Returns the number of cars in the queue.
 *
 * @return the number of cars in the queue
 */
int Station::showSizeOfQueue() {
	//cout << "size of queue: " << q.size() << endl;
	return q.size();
}

//Part 4--------------------------------------------------------------
/**
 * Adds the current car to the deque on the left side.  After this operation, there should be no current car.
 * Does nothing if there is no current car or if the deque is already full.
 *
 * @return true if the car is successfully added to the deque; false otherwise
 */
bool Station::addToDequeLeft() {
	if (d.size() >= 5){
		//cout << "deque is full" << endl;
		return false;
	}
	else if (curr == -1){
		return false;
	}
	else {
		//cout << "adding car: " << curr << " to dequeLeft" << endl;
		d.addLeft(curr);
		curr = -1;
		return true;
	}
}

/**
 * Adds the current car to the deque on the right side.  After this operation, there should be no current car.
 * Does nothing if there is no current car or if the deque is already full.
 *
 * @return true if the car is successfully added to the deque; false otherwise
 */
bool Station::addToDequeRight() {
	if (d.size() >= 5){
		//cout << "deque is full" << endl;
		return false;
	}
	else if (curr == -1){
		return false;
	}
	else {
		//cout << "adding car: " << curr << " to dequeRight" << endl;
		d.addRight(curr);
		curr = -1;
		return true;
	}
}

/**
 * Removes the leftmost car in the deque and makes it the current car.
 * Does nothing if there is already a current car or if the deque already empty.
 *
 * @return true if the car is successfully removed from the deque; false otherwise
 */
bool Station::removeFromDequeLeft() {
	if (d.size() < 1){
		return false;
	}
	else if (curr != -1){
		return false;
	}
	else{
		//cout << "removing car: " << d.topLeft() << " from dequeLeft" << endl;
		curr = d.topLeft();
		d.removeLeft();
		return true;
	}
}

/**
 * Removes the rightmost car in the deque and makes it the current car.
 * Does nothing if there is already a current car or if the deque already empty.
 *
 * @return true if the car is successfully removed from the deque; false otherwise
 */
bool Station::removeFromDequeRight() {
	if (d.size() < 1){
		return false;
	}
	else if (curr != -1){
		return false;
	}
	else{
		curr = d.topRight();
		//cout << "removing car: " << curr << " from dequeRight" << endl;
		d.removeRight();
		return true;
	}
}

/**
 * Returns the ID of the leftmost car in the deque.
 *
 * @return the ID of the leftmost car in the deque; -1 if the deque is empty
 */
int Station::showTopOfDequeLeft() {
	if (d.size() < 1){
			return -1;
		}
		else {
			return d.topLeft();
		}
}

/**
 * Returns the ID of the rightmost car in the deque.
 *
 * @return the ID of the rightmost car in the deque; -1 if the deque is empty
 */
int Station::showTopOfDequeRight() {
	if (d.size() < 1){
			return -1;
		}
		else {
			//cout << "top of dequeRight is " << d.topRight() << endl;
			return d.topRight();
		}
}

/**
 * Returns the number of cars in the deque.
 *
 * @return the number of cars in the deque
 */
int Station::showSizeOfDeque() {
	//cout << "size of deque: " << d.size() << endl;
	return d.size();
}

//Input-Restricted Deque----------------------------------------------
/**
 * Adds the current car to the IRDeque on the left side.  After this operation, there should be no current car.
 * Does nothing if there is no current car or if the IRDeque is already full.
 *
 * @return true if the car is successfully added to the IRDeque; false otherwise
 */
bool Station::addToIRDequeLeft(){
	if (i.size() >= 5){
		//cout << "deque is full" << endl;
		return false;
	}
	else if (curr == -1){
		return false;
	}
	else {
		//cout << "adding car: " << curr << " to dequeLeft" << endl;
		i.addLeft(curr);
		curr = -1;
		return true;
	}
}


/**
 * Removes the leftmost car in the IRDeque and makes it the current car.
 * Does nothing if there is already a current car or if the IRDeque already empty.
 *
 * @return true if the car is successfully removed from the IRDeque; false otherwise
 */
bool Station::removeFromIRDequeLeft() {
	if (i.size() < 1){
		return false;
	}
	else if (curr != -1){
		return false;
	}
	else{
		//cout << "removing car: " << i.topLeft() << " from dequeLeft" << endl;
		curr = i.topLeft();
		i.removeLeft();
		return true;
	}
}

/**
 * Removes the rightmost car in the IRDeque and makes it the current car.
 * Does nothing if there is already a current car or if the IRDeque already empty.
 *
 * @return true if the car is successfully removed from the IRDeque; false otherwise
 */
bool Station::removeFromIRDequeRight() {
	if (i.size() < 1){
		return false;
	}
	else if (curr != -1){
		return false;
	}
	else{
		curr = i.topRight();
		//cout << "removing car: " << curr << " from dequeRight" << endl;
		i.removeRight();
		return true;
	}
}

/**
 * Returns the ID of the leftmost car in the IRDeque.
 *
 * @return the ID of the leftmost car in the IRDeque; -1 if the IRDeque is empty
 */
int Station::showTopOfIRDequeLeft() {
	if (i.size() < 1){
			return -1;
		}
		else {
			return i.topLeft();
		}
}

/**
 * Returns the ID of the rightmost car in the IRDeque.
 *
 * @return the ID of the rightmost car in the IRDeque; -1 if the IRDeque is empty
 */
int Station::showTopOfIRDequeRight() {
	if (i.size() < 1){
			return -1;
		}
		else {
			//cout << "top of dequeRight is " << i.topRight() << endl;
			return i.topRight();
		}
}

/**
 * Returns the number of cars in the IRDeque.
 *
 * @return the number of cars in the IRDeque
 */
int Station::showSizeOfIRDeque() {
	//cout << "size of deque: " << i.size() << endl;
	return i.size();
}

//Output-Restricted Deque---------------------------------------------
/**
 * Adds the current car to the ORDeque on the left side.  After this operation, there should be no current car.
 * Does nothing if there is no current car or if the ORDeque is already full.
 *
 * @return true if the car is successfully added to the ORDeque; false otherwise
 */
bool Station::addToORDequeLeft() {
	if (o.size() >= 5){
		//cout << "deque is full" << endl;
		return false;
	}
	else if (curr == -1){
		return false;
	}
	else {
		//cout << "adding car: " << curr << " to dequeLeft" << endl;
		o.addLeft(curr);
		curr = -1;
		return true;
	}
}

/**
 * Adds the current car to the ORDeque on the right side.  After this operation, there should be no current car.
 * Does nothing if there is no current car or if the ORDeque is already full.
 *
 * @return true if the car is successfully added to the ORDeque; false otherwise
 */
bool Station::addToORDequeRight() {
	if (o.size() >= 5){
		//cout << "deque is full" << endl;
		return false;
	}
	else if (curr == -1){
		return false;
	}
	else {
		//cout << "adding car: " << curr << " to dequeRight" << endl;
		o.addRight(curr);
		curr = -1;
		return true;
	}
}

/**
 * Removes the leftmost car in the ORDeque and makes it the current car.
 * Does nothing if there is already a current car or if the ORDeque already empty.
 *
 * @return true if the car is successfully removed from the ORDeque; false otherwise
 */
bool Station::removeFromORDequeLeft() {
	if (o.size() < 1){
		return false;
	}
	else if (curr != -1){
		return false;
	}
	else{
		//cout << "removing car: " << o.topLeft() << " from dequeLeft" << endl;
		curr = o.topLeft();
		o.removeLeft();
		return true;
	}
}

/**
 * Returns the ID of the leftmost car in the ORDeque.
 *
 * @return the ID of the leftmost car in the ORDeque; -1 if the ORDeque is empty
 */
int Station::showTopOfORDequeLeft() {
	if (o.size() < 1){
			return -1;
		}
		else {
			return o.topLeft();
		}
}

/**
 * Returns the number of cars in the ORDeque.
 *
 * @return the number of cars in the ORDeque
 */
int Station::showSizeOfORDeque() {
	//cout << "size of deque: " << o.size() << endl;
		return o.size();
}


