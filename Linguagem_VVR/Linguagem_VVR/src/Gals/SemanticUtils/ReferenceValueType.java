package Gals.SemanticUtils;

public enum ReferenceValueType {
    INT(0),
    DOUBLE(1),

    STRING(2),
    CHAR(3),
    BOOL(4);

    private final int varCode;
    private ReferenceValueType(int varCode) {
        this.varCode = varCode;
    }
    public int getVarCode(){
        return varCode;
    }

}
