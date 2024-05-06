package Gals.SemanticUtils;

public enum OperatorType {

    SUM(0),
    SUB(1),
    MUL(2),
    DIV(3),
    REL(4),
    LOG(5),
    BIT(6); // qualquer operador relacional

    private final int OPcode;
    private OperatorType(int code){
        this.OPcode = code;
    }

    public int getCode() {
        return OPcode;
    }
}
