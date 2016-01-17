//
//  Fighter.h
//  CS 142 RPG Review
//
//  Created by Bryce Young on 9/11/14.
//  Copyright (c) 2014 Bryce Young. All rights reserved.
//
#pragma once
#include "FighterInterface.h"


class Fighter: public FighterInterface
{
	
	protected:
	
	string name;
	int maxhp, currenthp, strength, speed, magic;
	
	public:
	
	Fighter(){}
	Fighter(string name, int maxhp, int currenthp, int strength, int speed, int magic);
	virtual ~Fighter(){}

virtual string getName();

virtual int getMaximumHP();


virtual int getCurrentHP();


virtual int getStrength();


virtual int getSpeed();


virtual int getMagic();


virtual int getDamage();


virtual void takeDamage(int damage);


virtual void reset();


virtual void regenerate();


virtual bool useAbility();



};