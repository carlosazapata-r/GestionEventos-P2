package edu.pgii.eventos.model;

public final class Enumeraciones {
    /**
     * Evita la instanciacion de la clase contenedora de enumeraciones.
     */
    private Enumeraciones() {
    }
    public enum Role { USUARIO, ADMIN }
    public enum EstadoEvento { BORRADOR, PUBLICADO, PAUSADO, CANCELADO, FINALIZADO }
    public enum EstadoAsiento { DISPONIBLE, RESERVADO, VENDIDO, BLOQUEADO }
    public enum EstadoCompra { CREADA, PAGADA, CONFIRMADA, CANCELADA, REEMBOLSADA, INCIDENCIA }
    public enum EstadoEntrada { ACTIVA, USADA, ANULADA }
    public enum TipoServicio { VIP, SEGURO_CANCELACION, MERCHANDISING, PARQUEADERO, ACCESO_PREFERENCIAL }
    public enum TipoIncidencia { EVENTO, COMPRA, USUARIO, PAGO, DISPONIBILIDAD }
    public enum ReportType { VENTAS, OCUPACION, SERVICIOS, CANCELACIONES, TOP_EVENTOS }
    public enum ReportFormat { CSV, PDF }
}

