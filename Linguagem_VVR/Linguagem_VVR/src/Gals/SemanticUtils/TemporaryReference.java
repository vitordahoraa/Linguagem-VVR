package Gals.SemanticUtils;

public class TemporaryReference {
    String nome;
    boolean isVector;

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
}
