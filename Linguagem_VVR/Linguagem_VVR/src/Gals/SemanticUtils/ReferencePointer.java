package Gals.SemanticUtils;

public class ReferencePointer {
    String nome;
    ReferenceType tipo;
    boolean iniciada;
    boolean utilizada;
    int escopo;

    boolean isParameter;
    int posicaoParameto;
    boolean isVector;
    boolean isReference;
    boolean isFunction;

    public ReferencePointer(String nome, ReferenceType tipo, boolean iniciada, boolean utilizada, int escopo, boolean isParameter, int posicaoParameto, boolean isVector, boolean isReference, boolean isFunction) {
        this.nome = nome;
        this.tipo = tipo;
        this.iniciada = iniciada;
        this.utilizada = utilizada;
        this.escopo = escopo;
        this.isParameter = isParameter;
        this.posicaoParameto = posicaoParameto;
        this.isVector = isVector;
        this.isReference = isReference;
        this.isFunction = isFunction;
    }

    public void setIniciada(boolean iniciada) {
        this.iniciada = iniciada;
    }

    public void setUtilizada(boolean utilizada) {
        this.utilizada = utilizada;
    }

    public String getNome() {
        return nome;
    }

    public ReferenceType getTipo() {
        return tipo;
    }

    public boolean isIniciada() {
        return iniciada;
    }

    public boolean isUtilizada() {
        return utilizada;
    }

    public int getEscopo() {
        return escopo;
    }

    public boolean isParameter() {
        return isParameter;
    }

    public int getPosicaoParameto() {
        return posicaoParameto;
    }

    public boolean isVector() {
        return isVector;
    }

    public boolean isReference() {
        return isReference;
    }

    public boolean isFunction() {
        return isFunction;
    }
}
