package edu.pgii.eventos.model;

public class MetodoPago {
    private final String idMetodo;
    private final String tipo;
    private final String referencia;

    /**
     * Crea un metodo de pago.
     *
     * @param idMetodo identificador unico del metodo
     * @param tipo tipo de metodo de pago
     * @param referencia referencia asociada al metodo
     */
    public MetodoPago(String idMetodo, String tipo, String referencia) {
        this.idMetodo = idMetodo;
        this.tipo = tipo;
        this.referencia = referencia;
    }
    public String getIdMetodo() { return idMetodo; }
    public String getTipo() { return tipo; }
    public String getReferencia() { return referencia; }

    /**
     * Devuelve una representacion legible del metodo de pago.
     */
    @Override
    public String toString() {
        return tipo + " " + referencia;
    }
}

