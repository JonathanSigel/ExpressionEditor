import java.util.List;

interface CompoundExpression extends Expression {
	/**
	 * Adds the specified expression as a child.
	 * @param subexpression the child expression to add
	 */
	void addSubexpression (Expression subexpression);

	/**
	 * Returns the list of the CompoundExpression's children.
	 * @return list of children
	 */
	List<Expression> getSubexpressions();

}
