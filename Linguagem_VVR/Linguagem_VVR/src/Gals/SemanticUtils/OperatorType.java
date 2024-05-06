package Gals.SemanticUtils;

public enum OperatorType {

    SUM(0),
    SUB(1),
    MUL(2),
    DIV(3),
    REL(4), // qualquer operador relacional
    LOG(5),
    BIT(6),
    BIW(7),
    MOD(8),
    NEG(9),
    INC(10);

    private final int OPcode;
    private OperatorType(int code){
        this.OPcode = code;
    }

    public int getCode() {
        return OPcode;
    }
}
