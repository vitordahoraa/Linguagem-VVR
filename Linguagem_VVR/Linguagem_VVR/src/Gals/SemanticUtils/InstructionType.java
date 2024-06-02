package Gals.SemanticUtils;

public enum InstructionType {

    INPUT(0),
    DECL(1),
    ATRIB(2),
    OP_ARIT(3);

    private final int InstructionCode;
    private InstructionType(int code){
        this.InstructionCode = code;
    }

    public int getCode() {
        return InstructionCode;
    }
}
