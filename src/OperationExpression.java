public class OperationExpression extends CompoundExpressionImpl {

    protected OperationExpression(String representation) {
        super(representation);
    }

    @Override
    public void flatten() {
        flattenChildren();
        flattenSelf();
    }

    //!!! to implement
    protected void flattenSelf() {
        for (int i = 0; i < mChildren.size(); i++) {
            if (mChildren.get(i).getType() == mRep) {
                for (int j = 0; j < ((CompoundExpressionImpl) mChildren.get(i)).getSubexpressions().size(); j++) {
                    this.addSubexpression(((CompoundExpressionImpl) mChildren.get(i)).getSubexpressions().get(j));
                }

                mChildren.get(i).setParent(null);
                this.mChildren.remove(i);
            }
        }
    }
}
