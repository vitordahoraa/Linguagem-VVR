package Gals.SemanticUtils;

public enum ReferenceType {

    VAR(1),
    PARAMETRO(2),
    FUNCAO(3);

    private final int refType;
    private ReferenceType(int refType) {
        this.refType = refType;
    }
    public int getVarCode(){
        return refType;
    }
}
