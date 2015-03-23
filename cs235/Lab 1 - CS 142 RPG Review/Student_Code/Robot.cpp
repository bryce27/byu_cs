//
//  Robot.cpp
//  CS 142 RPG Review
//
//  Created by Bryce Young on 9/11/14.
//  Copyright (c) 2014 Bryce Young. All rights reserved.
//

#include "Robot.h"

Robot::Robot(string name, int maxhp, int currenthp, int strength, int speed, int magic)
: Fighter(name, maxhp, currenthp, strength, speed, magic){
	 currentenergy = 2.0 * magic;
	 maxenergy = 2.0 * magic;
	 bonus_damage = 0;
}

// initialize variables unique to robot even if you set them to zero

/*
 *	getName()
 *
 *	Returns the name of this fighter.
 */

string Robot::getName(){
    return name;
}


/*
 *	getMaximumHP()
 *
 *	Returns the maximum hit points of this fighter.
 */
int Robot::getMaximumHP(){
    return maxhp;
}

/*
 *	getCurrentHP()
 *
 *	Returns the current hit points of this fighter.
 */
int Robot::getCurrentHP(){
    return currenthp;
}

/*
 *	getStrength()
 *
 *	Returns the strength stat of this fighter.
 */
int Robot::getStrength(){
    return strength;
}

/*
 *	getSpeed()
 *
 *	Returns the speed stat of this fighter.
 */
int Robot::getSpeed(){
    return speed;
}

/*
 *	getMagic()
 *
 *	Returns the magic stat of this fighter.
 */
int Robot::getMagic(){
    return magic;
}

/*
 *	getDamage()
 *
 *	Returns the amount of damage a fighter will deal.
 *
 *	Robot:
 *	This value is equal to the Robot's strength plus any additional damage added for having just used its special ability.
 *
 *	Archer:
 *	This value is equal to the Archer's speed.
 *
 *	Cleric:
 *	This value is equal to the Cleric's magic.
 */
int Robot::getDamage(){
   int hurt;
   hurt = strength + bonus_damage;
   bonus_damage = 0;
   return hurt;
}

/*
 *	takeDamage(int)
 *
 *	Reduces the fighter's current hit points by an amount equal to the given
 *	damage minus one fourth of the fighter's speed.  This method must reduce
 *	the fighter's current hit points by at least one.  It is acceptable for
 *	this method to give the fighter negative current hit points.
 *
 *	Examples:
 *		damage=10, speed=7		=> damage_taken=9
 *		damage=10, speed=9		=> damage_taken=8
 *		damage=10, speed=50		=> damage_taken=1
 */
void Robot::takeDamage(int damage){
	if ((damage - (speed / 4 )) < 1){
		currenthp = (currenthp - 1);	
	}

	else{
	currenthp = currenthp - (damage - (speed / 4));
	}
	
}

/*
 *	reset()
 *
 *	Restores a fighter's current hit points to its maximum hit points.
 *
 *	Robot:
 *	Also restores a Robot's current energy to its maximum value (which is 2 times its magic).
 *	Also resets a Robot's bonus damage to 0.
 *
 *	Archer:
 *	Also resets an Archer's current speed to its original value.
 *
 *	Cleric:
 *	Also restores a Cleric's current mana to its maximum value (which is 5 times its magic).
 */
void Robot::reset(){
	currenthp = maxhp;
	currentenergy = maxenergy;
	bonus_damage = 0;

}

/*
 *	regenerate()
 *
 *	Increases the fighter's current hit points by an amount equal to one sixth of
 *	the fighter's strength.  This method must increase the fighter's current hit
 *	points by at least one.  Do not allow the current hit points to exceed the
 *	maximum hit points.
 *
 *	Cleric:
 *	Also increases a Cleric's current mana by an amount equal to one fifth of the
 *	Cleric's magic.  This method must increase the Cleric's current mana by at
 *	least one.  Do not allow the current mana to exceed the maximum mana.
 */
void Robot::regenerate(){
	if ((strength / 6) < 1){
			currenthp = currenthp + 1;
		}
	else{
		currenthp = (currenthp + (strength / 6));
	}

	if (currenthp > maxhp){
			currenthp = maxhp;
		}
	
}

/*
 *	useAbility()
 *
 *	Attempts to perform a special ability based on the type of fighter.  The
 *	fighter will attempt to use this special ability just prior to attacking
 *	every turn.
 *
 *	Robot: Shockwave Punch
 *	Adds bonus damage to the Robot's next attack (and only its next attack) equal to (strength  * ((current_energy/maximum_energy)^4)).
 *	Can only be used if the Robot has at least [ROBOT_ABILITY_COST] energy.
 *	Decreases the Robot's current energy by [ROBOT_ABILITY_COST] (after calculating the additional damage) when used.
 *		Examples:
 *		strength=20, current_energy=20, maximum_energy=20		=> bonus_damage=20
 *		strength=20, current_energy=15, maximum_energy=20		=> bonus_damage=6
 *		strength=20, current_energy=10, maximum_energy=20		=> bonus_damage=1
 *		strength=20, current_energy=5,  maximum_energy=20		=> bonus_damage=0
 *	Robot Note:
 *	The bonus damage formula should be computed using double arithmetic, and only
 *	the final result should be cast into an integer.
 *
 *	Archer: Quickstep
 *	Increases the Archer's speed by one point each time the ability is used.
 *	This bonus lasts until the reset() method is used.
 *	This ability always works; there is no maximum bonus speed.
 *
 *	Cleric: Healing Light
 *	Increases the Cleric's current hit points by an amount equal to one third of its magic.
 *	Can only be used if the Cleric has at least [CLERIC_ABILITY_COST] mana.
 *	Will be used even if the Cleric's current HP is equal to their maximum HP.
 *	Decreases the Cleric's current mana by [CLERIC_ABILITY_COST] when used.
 *	Cleric Note:
 *	This ability, when successful, must increase the Cleric's current hit points
 *	by at least one.  Do not allow the current hit points to exceed the maximum
 *	hit points.
 *
 *	Return true if the ability was used; false otherwise.
 */
bool Robot::useAbility(){
	// Shockwave Punch
	if (currentenergy >= ROBOT_ABILITY_COST){
	bonus_damage = (double)strength * pow(((double)currentenergy / maxenergy), 4);
	currentenergy = currentenergy - ROBOT_ABILITY_COST;
	return true;

	}
	else{

		return false;
	}
}


