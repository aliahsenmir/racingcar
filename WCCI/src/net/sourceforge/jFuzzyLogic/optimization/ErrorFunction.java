package net.sourceforge.jFuzzyLogic.optimization;
import net.sourceforge.jFuzzyLogic.rule.FuzzyRuleSet;

/**
 * Generic abstract error function
 */

public abstract class ErrorFunction {

	/** 
	 * Default constructor (does nothing)
	 */
	public ErrorFunction() {	
	}

	/** Evaluate error function for this fuzzyRuleSet */
	public abstract double evaluate(FuzzyRuleSet fuzzyRuleSet);
}
