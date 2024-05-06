package Gals.SemanticUtils;

public enum ReturnType {
    ERR(-1),
    OK_(0),
    WAR(1);

    private int code;
    private ReturnType(int code){
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
