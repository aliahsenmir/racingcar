package net.sourceforge.jFuzzyLogic.ruleConnection;

/**
 * Methods used to connect rule's antecedents
 * 
 * Connection type: AND
 * Connection Method: Bounded difference
 * 
 * @author pcingola@users.sourceforge.net
 */
public class RuleConnectionMethodAndBoundedDif extends RuleConnectionMethod {

	public RuleConnectionMethodAndBoundedDif() {
		super();
		name = "and";
	}

	public double connect(double antecedent1, double antecedent2) {
		return Math.max(0, antecedent1 * antecedent2 - 1);
	}
	
	public String toStringFCL() {
		return "AND : BDIFF;";
	}
}
