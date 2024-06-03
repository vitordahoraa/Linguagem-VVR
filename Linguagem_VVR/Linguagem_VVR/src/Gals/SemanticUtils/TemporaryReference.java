package Gals.SemanticUtils;

public class TemporaryReference {
    String nome;
    boolean isVector;

    int vectorSize;
    int value;

    public TemporaryReference(String nome, boolean isVector) {
        this.nome = nome;
        this.isVector = isVector;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isVector() {
        return isVector;
    }

    public void setVector(boolean vector) {
        isVector = vector;
    }

    public int getVectorSize() {
        return vectorSize;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setVectorSize(int vectorSize) {
        this.vectorSize = vectorSize;
    }
}
