#include "ExpressionManager.h"
#include <cstring>
#include <cctype>
#include <cstdlib>

ExpressionManager::ExpressionManager(){}
ExpressionManager::~ExpressionManager(){}

bool ExpressionManager::isOpening(string paren){

	if (paren == "(" || paren == "[" || paren == "{"){
		return true;
	}
	else{
		return false;
	}
}

bool ExpressionManager::isClosing(string paren){
	if (paren == ")" || paren == "]" || paren == "}"){
		return true;
		}
	else{
		return false;
	}
}

bool ExpressionManager::isEmpty(stringstream stream){
	if (stream == "NULL"){
		return true;
	}
	else{
		return false;

	}
}

bool parenChecker(string first, string second){
	   if(first == "(" && second == ")"){
	     return true;
	   }
	   else if(first == "{" && second == "}")
	   {
	     return true;}
	   else if(first == "[" && second == "]")
	   {
	     return true;
	   }
	   else{
	     return false;
	   }
}

bool isParen(string s){
	if (s == "(" || s == "{" || s == "[" || s == ")" || s == "}" || s == "]"){
		return true;
	}
	else{
		return false;
	}
}

string Operators = "+-*/%";
bool is_operator(string s){
	return Operators.find(s) != string::npos;
}

/*string Numbers = "1234567890";
bool is_number(string s){
	return Numbers.find(s) != string::npos;
}*/

/*bool isOp(string s){
	if (s == "*" || s == "/" || s == "+" || s == "-"){
		return true;
	}
	else{
		return false;
	}
}*/

/*bool isNumber(string s){
	if (isOp(s) || isParen(s)){
		return false;
	}
	else{
		return true;
	}
}*/

/*bool is_number(const std::string& s)
{
    std::string::const_iterator it = s.begin();
    while (it != s.end() && std::isdigit(*it)) ++it;
    return !s.empty() && it == s.end();
}*/

bool is_number(const std::string& s)
{
    return( strspn( s.c_str(), "0123456789" ) == s.size() );
}

int precedence(string op){
	if (op == "+" || op == "-"){
		return 1;
	}
	else if (op == "*" || op == "/" || op == "%"){
		return 2;
	}
	else if (op == ")" || op == "}" || op == "]"){
		return 3;
	}
	else{
		return 0;
	}
}

// this isn't working for double digits
bool isInvalid(string exp){
	stringstream s(exp);
	string curr;
	int numCount = 0;
	int opCount = 0;
	int parenCount = 0;

	while(s >> curr){
		if (is_number(curr)){
			numCount++;
			}
		else if(is_operator(curr)){
			opCount++;
		}
		else if(isParen(curr)){
			parenCount++;
		}
	}
	if ((numCount - opCount) == 1){
		return false;
	}
	else{
		return true;
	}
}

/*
	* Checks whether an expression is balanced on its parentheses
	*
	* - The given expression will have a space between every number or operator
	*
	* @return true if expression is balanced
	* @return false otherwise
	*/
bool ExpressionManager::isBalanced(string expression){

	stack <string> mystack;
	stringstream ss(expression);
	string curr;
	while (ss >> curr){

		if (isOpening(curr)){
			mystack.push(curr);
		}
		else if (isClosing(curr)){
			if (mystack.empty()){
				return false;
				break;
			}
			else{
				string topItem = mystack.top();
				//if (isOpening(topItem)){
				if (parenChecker(topItem, curr)){
					mystack.pop();
				}
				else{
					
					return false;
					break;
				}
			}

		}
	}

	if (!mystack.empty()){
		return false;
	}
	else{
		return true;
	}
}


// how do you check for the type of a paren?


	/**
	 * Converts a postfix expression into an infix expression
	 * and returns the infix expression.
	 *
	 * - The given postfix expression will have a space between every number or operator.
	 * - The returned infix expression must have a space between every number or operator.
	 * - Redundant parentheses are acceptable i.e. ( ( 3 * 4 ) + 5 ).
	 * - Check lab requirements for what will be considered invalid.
	 *
	 * return the string "invalid" if postfixExpression is not a valid postfix expression.
	 * otherwise, return the correct infix expression as a string.
	 */
string ExpressionManager::postfixToInfix(string postfixExpression){
	stack <string> mystack;
	stringstream ss(postfixExpression);
	string curr;
	string right;
	string left;
	string newExp;

	if (!isBalanced(postfixExpression)){
		return "invalid";
	}

	if (isInvalid(postfixExpression)){
		return "invalid";
	}

	while (ss >> curr){
		if (curr == "NULL"){
			return "invalid";
		}
		if (mystack.empty()){
		}
		if (is_number(curr)){
			mystack.push(curr);
		}
		else if (is_operator(curr)){
			if (mystack.empty()){
				// if it's empty, go ahead and push onto the stack
				return "invalid";
			}
			else{
				right = mystack.top();
				mystack.pop();
				if (mystack.empty()){
					return "invalid";
				}
				left = mystack.top();
				mystack.pop();
				newExp = "( " + left + " " + curr + " " + right + " " + ")";
				mystack.push(newExp);
				}
		}else{
			return "invalid";
		}
	}
	if (mystack.empty()){
		return "invalid";
	}
	newExp = mystack.top();
	return newExp;
}

// the string always had white space at the end of it so I'm using this function to take it off so it will pass the tests
void trim(string& s)
{
	size_t p = s.find_first_not_of(" \t");
	s.erase(0, p);

	p = s.find_last_not_of(" \t");
	if (string::npos != p)
		s.erase(p+1);
}

	/*
	 * Converts an infix expression into a postfix expression
	 * and returns the postfix expression
	 *
	 * - The given infix expression will have a space between every number or operator.
	 * - The returned postfix expression must have a space between every number or operator.
	 * - Check lab requirements for what will be considered invalid.
	 *
	 * return the string "invalid" if infixExpression is not a valid infix expression.
	 * otherwise, return the correct postfix expression as a string.
	 */
string ExpressionManager::infixToPostfix(string infixExpression){
	stack <string> mystack;
	stringstream ss(infixExpression);
	stringstream os;
	string curr;

	if (!isBalanced(infixExpression)){
		return "invalid";
	}

	if (isInvalid(infixExpression)){
		return "invalid";
	}

	while (ss >> curr){
		if (is_number(curr)){
			os << curr << " ";
		}
		// if it's an operator
		else if (is_operator(curr)){
			if (mystack.empty()){
				// if it's empty, go ahead and push onto the stack
				mystack.push(curr);
			}
			// if the top is a paren, go ahead and push onto the stack
			else if (isParen(mystack.top())){
				mystack.push(curr);
			}
			// if the operator has higher precedence than the stack's top, push onto stack
			else if (precedence(curr) > precedence(mystack.top())){
				mystack.push(curr);
			}
			else {
				while (!mystack.empty() && (precedence(curr)) <= precedence(mystack.top())) {
					topItem = mystack.top();
					os << topItem << " ";
					mystack.pop();
				}
				mystack.push(curr);
			}
		}
		// if it's a paren
		else if (isOpening(curr)){
			mystack.push(curr);
		}
		else if (isClosing(curr)){
			while (!parenChecker(mystack.top(), curr)){
				topItem = mystack.top();
				os << topItem << " ";
				mystack.pop();
			}
			mystack.pop();
		}
		// if it's not a paren, operator, or a number
		else{
			return "invalid";
		}

	}
	/*	if (isInvalid(os.str())){
		return "invalid";
	}*/
	while (!mystack.empty()){
		topItem = mystack.top();
		os << topItem << " ";
		mystack.pop();
	}
	string output;
	output = os.str();
	trim(output);
	return output;
}
//pops two operands and applies operator op to its operands, returning the result
int ExpressionManager::eval_op(string op){
	stringstream evalstream;
	int answer;
	string right = mystack.top();
	mystack.pop();
	string left = mystack.top();
	mystack.pop();

	int R = atoi(right.c_str());
	int L = atoi(left.c_str());

	if (op == "+"){
		answer = L + R;
	}
	if (op == "-"){
		answer = L - R;
	}
	if (op == "*"){
		answer = L * R;
	}
	if (op == "/"){
		if(R == 0 || L == 0){
			// can I make this 0?
			return 1000;
		}
		else{
			answer = L / R;
		}
	}
	return answer;
}

/*
// scans postfix exp and processes each of its tokens, where a token is an integer or operator
// returns value of an expression
int ExpressionManager::eval(string expression){
	stringstream tokens(expression)
	string curr;

	while (!mystack.empty()){
		mystack.pop();
	}

	while (tokens >> curr){
		if (is_number(curr)){
			cout << "postfixEval: it's a number -- putting on stack" << endl;
			mystack.push(curr);
		}
		else if (is_operator(curr)){
			int result = eval_op(curr);
			mystack.push(curr);
		}
		else {
			return 0;
		}
	}
	if (!mystack.empty()){
		int answer = mystack.top();
		mystack.pop();
		if (mystack.empty()){
			return answer;
		}
		else{
			throw Sytax_Error("Stack Should be empty")''
		}

	// empty the stack
	// while there are more tokens
		// get the next token
		// if the first character of the token is a digit
			// push the int onto the stack
		// else if the token is an operator
			// pop the right op off the stack
			// pop the left operand off the stack
			// evaluate the operation
			// push the result onto the stack
	// pop the stack and returnt eh results

	}
}*/
/*
	 * Evaluates a postfix expression returns the result as a string
	 *
	 * - The given postfix expression will have a space between every number or operator.
	 * - Check lab requirements for what will be considered invalid.
	 *
	 * return the string "invalid" if postfixExpression is not a valid postfix Expression
	 * otherwise, return the correct evaluation as a string
	 */
string ExpressionManager::postfixEvaluate(string postfixExpression){

	stringstream ps(postfixExpression);
	//stringstream os;
	string curr;
	string right;
	string left;
	int answer;

	while (!mystack.empty()){
		mystack.pop();
	}

	if (isInvalid(postfixExpression)){
		return "invalid";
	}

	while(ps >> curr){
		if (is_number(curr)){
			mystack.push(curr);
		}
		else if(is_operator(curr)){
			if (mystack.empty()){
				return "invalid";
			}
			//eval_op(curr);
			right = mystack.top();
			mystack.pop();
			if (mystack.empty()){
				return "invalid";
			}
			left = mystack.top();
			mystack.pop();

			int R = atoi(right.c_str());
			int L = atoi(left.c_str());

			if (curr == "+"){
				answer = L + R;
			}
			if (curr == "-"){
				answer = L - R;
			}
			if (curr == "*"){
				answer = L * R;
			}
			if (curr == "%"){
				answer = L % R;
			}
			if (curr == "/"){
				if(R == 0 ){
					answer = 0;
                    return "invalid";
				}
				else{
					answer = L / R;
				}
			}
			stringstream os;
			os << answer;
			// put on stack
			mystack.push(os.str());
			//return os.str();
		}

		else{
			return "invalid";
		}
	}
	string finalExp = mystack.top();
	return finalExp;
}


