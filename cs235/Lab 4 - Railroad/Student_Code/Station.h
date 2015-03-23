#pragma once
#include "StationInterfaceExtra.h"
#include "stack.h"
#include "queue.h"
#include "deque.h"
#include "dequeInput.h"
#include "dequeOutput.h"

class Station : public StationInterfaceExtra {

public:
	int car;
	int curr;
	LinkedList <int> Cars;

	myStack s;
	myQueue q;
	myDeque d;
	myDequeInput i;
	myDequeOutput o;

	Station();
	~Station();
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
			virtual bool addToStation(int car);

			/**
			 * Returns the ID of the current car.
			 * Return -1 if there is no current car.
			 *
			 * @return the ID of the current car; -1 if there is no current car
			 */
			virtual int showCurrentCar();

			/**
			 * Removes the current car from the station.
			 * Does nothing if there is no current car.
			 *
			 * @return true if the current car successfully left; false otherwise
			 */
			virtual bool removeFromStation();

			//Part 2--------------------------------------------------------------
			/**
			 * Adds the current car to the stack.  After this operation, there should be no current car.
			 * Does nothing if there is no current car or if the stack is already full.
			 *
			 * @return true if the car is successfully added to the stack; false otherwise
			 */
			virtual bool addToStack();

			/**
			 * Removes the first car in the stack and makes it the current car.
			 * Does nothing if there is already a current car or if the stack already empty.
			 *
			 * @return true if the car is successfully removed from the stack; false otherwise
			 */
			virtual bool removeFromStack();

			/**
			 * Returns the ID of the first car in the stack.
			 *
			 * @return the ID of the first car in the stack; -1 if the stack is empty
			 */
			virtual int showTopOfStack();

			/**
			 * Returns the number of cars in the stack.
			 *
			 * @return the number of cars in the stack
			 */
			virtual int showSizeOfStack();

			//Part 3--------------------------------------------------------------
			/**
			 * Adds the current car to the queue.  After this operation, there should be no current car.
			 * Does nothing if there is no current car or if the queue is already full.
			 *
			 * @return true if the car is successfully added to the queue; false otherwise
			 */
			virtual bool addToQueue();

			/**
			 * Removes the first car in the queue and makes it the current car.
			 * Does nothing if there is already a current car or if the queue already empty.
			 *
			 * @return true if the car is successfully removed from the queue; false otherwise
			 */
			virtual bool removeFromQueue();

			/**
			 * Returns the ID of the first car in the queue.
			 *
			 * @return the ID of the first car in the queue; -1 if the queue is empty
			 */
			virtual int showTopOfQueue();

			/**
			 * Returns the number of cars in the queue.
			 *
			 * @return the number of cars in the queue
			 */
			virtual int showSizeOfQueue();

			//Part 4--------------------------------------------------------------
			/**
			 * Adds the current car to the deque on the left side.  After this operation, there should be no current car.
			 * Does nothing if there is no current car or if the deque is already full.
			 *
			 * @return true if the car is successfully added to the deque; false otherwise
			 */
			virtual bool addToDequeLeft();

			/**
			 * Adds the current car to the deque on the right side.  After this operation, there should be no current car.
			 * Does nothing if there is no current car or if the deque is already full.
			 *
			 * @return true if the car is successfully added to the deque; false otherwise
			 */
			virtual bool addToDequeRight();

			/**
			 * Removes the leftmost car in the deque and makes it the current car.
			 * Does nothing if there is already a current car or if the deque already empty.
			 *
			 * @return true if the car is successfully removed from the deque; false otherwise
			 */
			virtual bool removeFromDequeLeft();

			/**
			 * Removes the rightmost car in the deque and makes it the current car.
			 * Does nothing if there is already a current car or if the deque already empty.
			 *
			 * @return true if the car is successfully removed from the deque; false otherwise
			 */
			virtual bool removeFromDequeRight();

			/**
			 * Returns the ID of the leftmost car in the deque.
			 *
			 * @return the ID of the leftmost car in the deque; -1 if the deque is empty
			 */
			virtual int showTopOfDequeLeft();

			/**
			 * Returns the ID of the rightmost car in the deque.
			 *
			 * @return the ID of the rightmost car in the deque; -1 if the deque is empty
			 */
			virtual int showTopOfDequeRight();

			/**
			 * Returns the number of cars in the deque.
			 *
			 * @return the number of cars in the deque
			 */
			virtual int showSizeOfDeque();

			//Input-Restricted Deque----------------------------------------------
			/**
			 * Adds the current car to the IRDeque on the left side.  After this operation, there should be no current car.
			 * Does nothing if there is no current car or if the IRDeque is already full.
			 *
			 * @return true if the car is successfully added to the IRDeque; false otherwise
			 */
			virtual bool addToIRDequeLeft();

			/**
			 * Removes the leftmost car in the IRDeque and makes it the current car.
			 * Does nothing if there is already a current car or if the IRDeque already empty.
			 *
			 * @return true if the car is successfully removed from the IRDeque; false otherwise
			 */
			virtual bool removeFromIRDequeLeft();

			/**
			 * Removes the rightmost car in the IRDeque and makes it the current car.
			 * Does nothing if there is already a current car or if the IRDeque already empty.
			 *
			 * @return true if the car is successfully removed from the IRDeque; false otherwise
			 */
			virtual bool removeFromIRDequeRight();

			/**
			 * Returns the ID of the leftmost car in the IRDeque.
			 *
			 * @return the ID of the leftmost car in the IRDeque; -1 if the IRDeque is empty
			 */
			virtual int showTopOfIRDequeLeft();

			/**
			 * Returns the ID of the rightmost car in the IRDeque.
			 *
			 * @return the ID of the rightmost car in the IRDeque; -1 if the IRDeque is empty
			 */
			virtual int showTopOfIRDequeRight();

			/**
			 * Returns the number of cars in the IRDeque.
			 *
			 * @return the number of cars in the IRDeque
			 */
			virtual int showSizeOfIRDeque();

			//Output-Restricted Deque---------------------------------------------
			/**
			 * Adds the current car to the ORDeque on the left side.  After this operation, there should be no current car.
			 * Does nothing if there is no current car or if the ORDeque is already full.
			 *
			 * @return true if the car is successfully added to the ORDeque; false otherwise
			 */
			virtual bool addToORDequeLeft();

			/**
			 * Adds the current car to the ORDeque on the right side.  After this operation, there should be no current car.
			 * Does nothing if there is no current car or if the ORDeque is already full.
			 *
			 * @return true if the car is successfully added to the ORDeque; false otherwise
			 */
			virtual bool addToORDequeRight();

			/**
			 * Removes the leftmost car in the ORDeque and makes it the current car.
			 * Does nothing if there is already a current car or if the ORDeque already empty.
			 *
			 * @return true if the car is successfully removed from the ORDeque; false otherwise
			 */
			virtual bool removeFromORDequeLeft();

			/**
			 * Returns the ID of the leftmost car in the ORDeque.
			 *
			 * @return the ID of the leftmost car in the ORDeque; -1 if the ORDeque is empty
			 */
			virtual int showTopOfORDequeLeft();

			/**
			 * Returns the number of cars in the ORDeque.
			 *
			 * @return the number of cars in the ORDeque
			 */
			virtual int showSizeOfORDeque();
	};

