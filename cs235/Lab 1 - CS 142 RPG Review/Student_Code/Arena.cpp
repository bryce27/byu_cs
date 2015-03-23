//
//  Arena.cpp
//  CS 142 RPG Review
//

#include "Arena.h"
#include <sstream>
#include <vector>
#include <algorithm>


/*
 *	addFighter(string)
 *
 *	Adds a new fighter to the collection of fighters in the arena. Do not allow
 *	duplicate names.  Reject any string that does not adhere to the format
 *	outlined in the lab specs.
 *
 *	Return true if a new fighter was added; false otherwise.
 */
bool Arena::addFighter(string info){
    
	string name, type;
	int currenthp, maxhp, strength, speed, magic;
	
    stringstream ss(info);
    ss >> name;
    ss >> type;
    ss >> maxhp;
    ss >> strength;
    ss >> speed;
    ss >> magic;
        
    if (maxhp <= 0){
    	return false;
    }

 if (strength <= 0){
    	return false;
    }

 if (speed <= 0){
    	return false;
    }
 if (magic <= 0){
    	return false;
    }
    
    // do this for other attributes too
    

    
	for (int i = 0; i < Arena.size(); i++){
    	    	
    	string newName = Arena[i]->getName(); 
    	if (newName == name){
    		return false;
    	 }
    }
    
	 if (ss.fail()){
	    	return false;
	    }


	 if (!ss.eof()){
	    	return false;
	    }

    if (type == "A"){
    	Archer* a = new Archer(name, maxhp, currenthp, strength, speed, magic);
    	Arena.push_back(a);
	return true;
    }
    
    else if (type == "R"){
    	Robot* a = new Robot(name, maxhp, currenthp, strength, speed, magic);
    	Arena.push_back(a);
	return true;
    }
   
    else if (type == "C"){
    	Cleric* a = new Cleric(name, maxhp, currenthp, strength, speed, magic);
    	Arena.push_back(a);
	return true;
    }
   
   return false;
   
};

/*
 *	removeFighter(string)
 *
 *	Removes the fighter whose name is equal to the given name.  Does nothing if
 *	no fighter is found with the given name.
 *
 *	Return true if a fighter is removed; false otherwise.
 */
bool Arena::removeFighter(string name){
	
	for (int i = 0; i < Arena.size(); i++){
	    	
	    string newName = Arena[i]->getName(); 
	    if (newName == name){
	    	Arena.erase(Arena.begin() + (i));
	    	return true;
	    }
	   
	}
	
	
	
	return false;
}

/*
 *	getFighter(string)
 *
 *	Returns the memory address of a fighter whose name is equal to the given
 *	name.  Returns NULL if no fighter is found with the given name.
 *
 *	Return a memory address if a fighter is found; NULL otherwise.
 */
FighterInterface* Arena::getFighter(string name){
   //FighterInterface* A = new Fighter();
    //return NULL;
    
/*
	if (find(Arena.begin(), Arena.end(), "Fumblemore") != Arena.end()) 
{
return A;
}
*/

   
	for (int i = 0; i < Arena.size(); i++){
    	    	
    	string newName = Arena[i]->getName(); 
    	if (newName == name){
    		return Arena[i];
    	 }
    } 


/*Arena<it>::const_iterator it = find(vec.begin(), vec.end(), "Fumblemore");

if (it != vec.end())
{
cout << "found" << *it << " in the vector" <<endl;
}
*/

}

/*
 *	getSize()
 *
 *	Returns the number of fighters in the arena.
 *
 *	Return a non-negative integer.
 */
int Arena::getSize(){
    return Arena.size();
}


/* int main(){
	Fighter* r = new Fighter("Bob", 1, 2, 3, 4, 5);
	addFighter(r);
	return true;
} */

//Xephos A 200 13 21 10
