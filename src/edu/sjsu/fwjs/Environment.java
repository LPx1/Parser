/* CS
 * @Luis Pamintuan
 * @Ivan Hernandez 
 * 
 * 	16 October 2018
 */

package edu.sjsu.fwjs;

import java.util.Map;



import java.util.HashMap;

public class Environment {
    private Map<String,Value> env = new HashMap<String,Value>(); 
    private Environment outerEnv;

    /**
     * Constructor for global environment ***
     */
    public Environment() {}

    /**
     * Constructor for local environment of a function ***
     */
    public Environment(Environment outerEnv) {
        this.outerEnv = outerEnv;
    }

    /**
     * Handles the logic of resolving a variable.
     * If the variable name is in the current scope, it is returned.
     * Otherwise, search for the variable in the outer scope.
     * If we are at the outermost scope (AKA the global scope)
     * null is returned (similar to how JS returns undefined.
     */
    public Value resolveVar(String varName) {
        // YOUR CODE HERE
    	//
    	//Handles and checks if variable name exists in local scope, if so return it.
    	//else if, check the outer scope and return that
    	//Otherwise if none, return the value as none.
        if (env.containsKey(varName)){
            return env.get(varName);
        }
        else if (outerEnv != null) {
        	return outerEnv.resolveVar(varName);
        }
        return new NullVal();
    }

    /**
     * Used for updating existing variables.
     * If a variable has not been defined previously in the current scope,
     * or any of the function's outer scopes, the var is stored in the global scope.
     */
    public void updateVar(String key, Value v) {
        // YOUR CODE HERE
    	//
    	//If the local env contains variable then replace the key with the new value
    	//else, check if the global env contains the key 
    	//otherwise just create the key in the global scope 
    	if(env.containsKey(key)) {
    		env.put(key, v);
    	}
    	else if (outerEnv != null)
    	{
    		outerEnv.updateVar(key, v);
    	}
    	else {
    		env.put(key, v);
    	}
    }

    /**
     * Creates a new variable in the local scope.
     * If the variable has been defined in the current scope previously,
     * a RuntimeException is thrown.
     */
    public void createVar(String key, Value v) throws RuntimeException {
    	//Creates new variable in the local scope 
    	//If the variable is already defined or already exists in local scope,
    	//then it throws a RuntimeException
    	if(env.containsKey(key)) {
    		throw new RuntimeException("The variable " + key + " is already defined");
    	}
    	else {
    		env.put(key, v);
    	}
    }
}
