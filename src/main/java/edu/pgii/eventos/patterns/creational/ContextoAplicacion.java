package edu.pgii.eventos.patterns.creational;

import edu.pgii.eventos.model.Asiento;
import edu.pgii.eventos.model.AlmacenDatos;
import edu.pgii.eventos.model.Compra;
import edu.pgii.eventos.model.Enumeraciones.EstadoAsiento;
import edu.pgii.eventos.model.Enumeraciones.EstadoCompra;
import edu.pgii.eventos.model.Enumeraciones.EstadoEvento;
import edu.pgii.eventos.model.Enumeraciones.Role;
import edu.pgii.eventos.model.Enumeraciones.TipoIncidencia;
import edu.pgii.eventos.model.Enumeraciones.TipoServicio;
import edu.pgii.eventos.model.Evento;
import edu.pgii.eventos.model.Incidencia;
import edu.pgii.eventos.model.MetodoPago;
import edu.pgii.eventos.model.Recinto;
import edu.pgii.eventos.model.ServicioAdicional;
import edu.pgii.eventos.model.Usuario;
import edu.pgii.eventos.model.Zona;
import edu.pgii.eventos.patterns.behavioral.CentroNotificaciones;
import edu.pgii.eventos.patterns.structural.FachadaTicketing;
import edu.pgii.eventos.service.ServicioAutenticacion;
import edu.pgii.eventos.service.ServicioCompras;
import edu.pgii.eventos.service.ServicioEventos;
import edu.pgii.eventos.service.ServicioMetricas;
import edu.pgii.eventos.service.EstrategiaPagoTarjetaSimulada;
import edu.pgii.eventos.service.PoliticaCancelacionEstandar;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ContextoAplicacion {
    private static ContextoAplicacion instance;

    private final AlmacenDatos<Usuario> usuarios = new AlmacenDatos<>(Usuario::getIdUsuario);
    private final AlmacenDatos<Evento> eventos = new AlmacenDatos<>(Evento::getIdEvento);
    private final AlmacenDatos<Compra> compras = new AlmacenDatos<>(Compra::getIdCompra);
    private final AlmacenDatos<Recinto> recintos = new AlmacenDatos<>(Recinto::getIdRecinto);
    private final AlmacenDatos<Incidencia> incidencias = new AlmacenDatos<>(Incidencia::getIdIncidencia);
    private final CentroNotificaciones CentroNotificaciones = new CentroNotificaciones();
    private final ServicioAutenticacion ServicioAutenticacion = new ServicioAutenticacion(usuarios);
    private final ServicioEventos ServicioEventos = new ServicioEventos(eventos);
    private final ServicioCompras ServicioCompras = new ServicioCompras(compras, new EstrategiaPagoTarjetaSimulada(),
            new PoliticaCancelacionEstandar(), CentroNotificaciones);
    private final ServicioMetricas ServicioMetricas = new ServicioMetricas(ServicioEventos, ServicioCompras);
    private final FabricaExportadorReporte FabricaExportadorReporte = new FabricaExportadorReporte();
    private final FachadaTicketing facade = new FachadaTicketing(ServicioAutenticacion, ServicioEventos, ServicioCompras, FabricaExportadorReporte);

    private final List<ServicioAdicional> serviciosDisponibles = List.of(
            new ServicioAdicional(TipoServicio.SEGURO_CANCELACION, "Seguro de cancelacion", new BigDecimal("18000")),
            new ServicioAdicional(TipoServicio.MERCHANDISING, "Merchandising oficial", new BigDecimal("45000")),
            new ServicioAdicional(TipoServicio.PARQUEADERO, "Parqueadero", new BigDecimal("22000")),
            new ServicioAdicional(TipoServicio.ACCESO_PREFERENCIAL, "Acceso preferencial", new BigDecimal("30000")));

    /**
     * Crea el contexto unico de la aplicacion e inicializa los datos base.
     */
    private ContextoAplicacion() {
        seed();
    }

    /**
     * Devuelve la instancia unica del contexto de aplicacion.
     *
     * @return instancia compartida de {@code ContextoAplicacion}.
     */
    public static ContextoAplicacion getInstance() {
        if (instance == null) {
            instance = new ContextoAplicacion();
        }
        return instance;
    }

    /**
     * Carga usuarios, recintos, eventos e incidencias iniciales para la demo.
     */
    private void seed() {
        Usuario admin = new Usuario("U-ADMIN", "Administrador Operaciones", "admin@demo.com", "3000000000", "admin", Role.ADMIN);
        Usuario raul = new Usuario("U-RAUL", "Raul Yulbraynner", "raul@demo.com", "3015550001", "1234", Role.USUARIO);
        usuarios.save(admin);
        usuarios.save(raul);

        Recinto movistar = recinto("R-1", "Movistar Arena", "Diagonal 61C #26-36", "Bogota",
                zona("Z-1", "VIP", 24, "220000", true),
                zona("Z-2", "General", 40, "90000", true));
        Recinto teatro = recinto("R-2", "Teatro Metropolitano", "Calle 41 #57-30", "Medellin",
                zona("Z-3", "Platea", 20, "130000", true),
                zona("Z-4", "Balcon", 30, "70000", true));
        Recinto campin = recinto("R-3", "Estadio El Campin", "Carrera 30 #57-60", "Bogota",
                zona("Z-5", "Occidental VIP", 45, "260000", true),
                zona("Z-6", "Oriental", 75, "140000", true),
                zona("Z-7", "Norte General", 90, "85000", true));
        Recinto plazaMayor = recinto("R-4", "Plaza Mayor Medellin", "Calle 41 #55-80", "Medellin",
                zona("Z-8", "Auditorio A", 36, "160000", true),
                zona("Z-9", "Auditorio B", 48, "95000", true),
                zona("Z-10", "General", 60, "65000", true));
        recintos.save(movistar);
        recintos.save(teatro);
        recintos.save(campin);
        recintos.save(plazaMayor);

        eventos.save(new Evento("EV-1", "Festival Altavoz", "Concierto", "Bandas nacionales e internacionales.",
                "Bogota", LocalDateTime.now().plusDays(15), EstadoEvento.PUBLICADO,
                "Cancelacion hasta 24 horas antes.", "Reembolso del 85% si aplica.", movistar));
        eventos.save(new Evento("EV-2", "Hamlet Contemporaneo", "Teatro", "Puesta en escena moderna de Shakespeare.",
                "Medellin", LocalDateTime.now().plusDays(9), EstadoEvento.PUBLICADO,
                "Cambios sujetos a disponibilidad.", "Reembolso simulado segun politica.", teatro));
        eventos.save(new Evento("EV-3", "Conferencia IA Aplicada", "Conferencia", "Charlas sobre IA, producto y datos.",
                "Bogota", LocalDateTime.now().plusDays(25), EstadoEvento.PAUSADO,
                "Cancelacion flexible.", "Reembolso del 90%.", movistar));

        incidencias.save(new Incidencia("I-1", TipoIncidencia.EVENTO, "Evento pausado por ajustes de logistica.",
                LocalDateTime.now().minusDays(1), "EV-3"));
    }

    /**
     * Construye un recinto con sus zonas asociadas.
     *
     * @param id identificador del recinto.
     * @param nombre nombre visible del recinto.
     * @param direccion direccion fisica del recinto.
     * @param ciudad ciudad donde se ubica el recinto.
     * @param zonas zonas disponibles dentro del recinto.
     * @return recinto configurado con las zonas recibidas.
     */
    private Recinto recinto(String id, String nombre, String direccion, String ciudad, Zona... zonas) {
        Recinto recinto = new Recinto(id, nombre, direccion, ciudad);
        recinto.getZonas().addAll(List.of(zonas));
        return recinto;
    }

    /**
     * Construye una zona y genera automaticamente sus asientos numerados.
     *
     * @param id identificador de la zona.
     * @param nombre nombre visible de la zona.
     * @param capacidad cantidad maxima de asientos.
     * @param precio precio base de la zona expresado como texto decimal.
     * @param numerada indica si la zona maneja numeracion.
     * @return zona creada con asientos disponibles.
     */
    private Zona zona(String id, String nombre, int capacidad, String precio, boolean numerada) {
        Zona zona = new Zona(id, nombre, capacidad, new BigDecimal(precio), numerada);
        int[] numeracionPorFila = new int[3];
        for (int i = 1; i <= capacidad; i++) {
            int indiceFila = Math.min(2, (i - 1) * 3 / capacidad);
            String fila = String.valueOf((char) ('A' + indiceFila));
            int numero = ++numeracionPorFila[indiceFila];
            zona.getAsientos().add(new Asiento(id + "-A" + i, fila, numero, EstadoAsiento.DISPONIBLE));
        }
        return zona;
    }

    public AlmacenDatos<Usuario> usuarios() { return usuarios; }
    public AlmacenDatos<Evento> eventos() { return eventos; }
    public AlmacenDatos<Compra> compras() { return compras; }
    public AlmacenDatos<Recinto> recintos() { return recintos; }
    public AlmacenDatos<Incidencia> incidencias() { return incidencias; }
    public CentroNotificaciones notifications() { return CentroNotificaciones; }
    public ServicioAutenticacion auth() { return ServicioAutenticacion; }
    public ServicioEventos events() { return ServicioEventos; }
    public ServicioCompras comprasService() { return ServicioCompras; }
    public ServicioMetricas metrics() { return ServicioMetricas; }
    public FachadaTicketing facade() { return facade; }
    public List<ServicioAdicional> serviciosDisponibles() { return serviciosDisponibles; }
}

