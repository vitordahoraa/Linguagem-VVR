package Gals.SemanticUtils;

public enum ReferenceValueType {
    INT(1),
    DOUBLE(2),

    STRING(3),
    CHAR(4),
    BOOL(5);

    private final int varCode;
    private ReferenceValueType(int varCode) {
        this.varCode = varCode;
    }
    public int getVarCode(){
        return varCode;
    }

}
