package Gals.SemanticUtils;

public enum ReferenceType {
    INT(1),
    DOUBLE(2),

    STRING(3),
    CHAR(4),
    BOOL(5);

    private final int varCode;
    private ReferenceType(int varCode) {
        this.varCode = varCode;
    }
    public int getVarCode(){
        return varCode;
    }

}
