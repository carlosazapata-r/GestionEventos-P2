package edu.pgii.eventos.patterns.structural;

import edu.pgii.eventos.model.Asiento;
import edu.pgii.eventos.model.Compra;
import edu.pgii.eventos.model.Enumeraciones.ReportFormat;
import edu.pgii.eventos.model.Enumeraciones.ReportType;
import edu.pgii.eventos.model.Evento;
import edu.pgii.eventos.model.MetodoPago;
import edu.pgii.eventos.model.ServicioAdicional;
import edu.pgii.eventos.model.Usuario;
import edu.pgii.eventos.model.Zona;
import edu.pgii.eventos.patterns.creational.FabricaExportadorReporte;
import edu.pgii.eventos.service.ServicioAutenticacion;
import edu.pgii.eventos.service.ServicioCompras;
import edu.pgii.eventos.service.ServicioEventos;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class FachadaTicketing {
    private final ServicioAutenticacion ServicioAutenticacion;
    private final ServicioEventos ServicioEventos;
    private final ServicioCompras ServicioCompras;
    private final FabricaExportadorReporte FabricaExportadorReporte;

    /**
     * Crea la fachada principal que coordina los servicios del sistema de ticketing.
     *
     * @param ServicioAutenticacion servicio encargado de autenticacion y registro
     * @param ServicioEventos servicio encargado de busqueda y gestion de eventos
     * @param ServicioCompras servicio encargado del flujo de compra
     * @param FabricaExportadorReporte fabrica usada para crear exportadores de reportes
     */
    public FachadaTicketing(ServicioAutenticacion ServicioAutenticacion, ServicioEventos ServicioEventos, ServicioCompras ServicioCompras,
                            FabricaExportadorReporte FabricaExportadorReporte) {
        this.ServicioAutenticacion = ServicioAutenticacion;
        this.ServicioEventos = ServicioEventos;
        this.ServicioCompras = ServicioCompras;
        this.FabricaExportadorReporte = FabricaExportadorReporte;
    }

    /**
     * Autentica un usuario con correo y contrasena.
     *
     * @param correo correo del usuario
     * @param password contrasena del usuario
     * @return usuario autenticado o un Optional vacio si las credenciales no coinciden
     */
    public Optional<Usuario> login(String correo, String password) {
        return ServicioAutenticacion.login(correo, password);
    }

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param nombre nombre completo del usuario
     * @param correo correo electronico del usuario
     * @param telefono telefono de contacto
     * @param password contrasena del usuario
     * @return usuario creado
     */
    public Usuario register(String nombre, String correo, String telefono, String password) {
        return ServicioAutenticacion.register(nombre, correo, telefono, password);
    }

    /**
     * Busca eventos aplicando filtros opcionales de ciudad, categoria, fecha y precio.
     *
     * @param ciudad ciudad por la que se desea filtrar
     * @param categoria categoria del evento
     * @param fecha fecha del evento
     * @param maxPrecio precio maximo permitido
     * @return eventos que cumplen los filtros indicados
     */
    public List<Evento> buscarEventos(String ciudad, String categoria, LocalDate fecha, BigDecimal maxPrecio) {
        return ServicioEventos.filter(ciudad, categoria, fecha, maxPrecio);
    }

    /**
     * Crea una compra para un usuario, evento, zona, asientos y servicios seleccionados.
     *
     * @param usuario usuario que realiza la compra
     * @param evento evento para el cual se compra
     * @param zona zona seleccionada
     * @param asientos asientos seleccionados para la compra
     * @param servicios servicios adicionales incluidos
     * @param nombreComprador nombre registrado para el comprador
     * @param metodoPago metodo de pago seleccionado
     * @return compra creada por el servicio de compras
     */
    public Compra comprar(Usuario usuario, Evento evento, Zona zona, List<Asiento> asientos,
                          List<ServicioAdicional> servicios, String nombreComprador, MetodoPago metodoPago) {
        return ServicioCompras.createCompra(usuario, evento, zona, asientos, servicios, nombreComprador, metodoPago);
    }

    /**
     * Procesa el pago de una compra.
     *
     * @param compra compra que se desea pagar
     * @param metodoPago metodo de pago utilizado
     */
    public void pagar(Compra compra, MetodoPago metodoPago) {
        ServicioCompras.pay(compra, metodoPago);
    }

    /**
     * Cancela una compra existente.
     *
     * @param compra compra que se desea cancelar
     */
    public void cancelar(Compra compra) {
        ServicioCompras.cancel(compra);
    }

    /**
     * Exporta un reporte creando el exportador adecuado segun el formato solicitado.
     *
     * @param type tipo de reporte que se desea generar
     * @param format formato de salida del reporte
     * @param from fecha inicial del rango del reporte
     * @param to fecha final del rango del reporte
     * @param compras compras usadas como fuente de datos
     * @return ruta del archivo de reporte generado
     * @throws IOException si ocurre un error durante la exportacion
     */
    public Path exportarReporte(ReportType type, ReportFormat format, LocalDate from, LocalDate to, List<Compra> compras) throws IOException {
        return new AdaptadorExportadorReporte(FabricaExportadorReporte.create(format)).exportReport(type, from, to, compras);
    }
}

