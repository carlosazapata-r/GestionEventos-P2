package edu.pgii.eventos.viewcontroller;

import edu.pgii.eventos.model.Asiento;
import edu.pgii.eventos.model.Compra;
import edu.pgii.eventos.model.Enumeraciones.EstadoAsiento;
import edu.pgii.eventos.model.Enumeraciones.EstadoCompra;
import edu.pgii.eventos.model.Enumeraciones.EstadoEvento;
import edu.pgii.eventos.model.Enumeraciones.ReportFormat;
import edu.pgii.eventos.model.Enumeraciones.ReportType;
import edu.pgii.eventos.model.Enumeraciones.Role;
import edu.pgii.eventos.model.Enumeraciones.TipoServicio;
import edu.pgii.eventos.model.Evento;
import edu.pgii.eventos.model.MetodoPago;
import edu.pgii.eventos.model.Recinto;
import edu.pgii.eventos.model.ServicioAdicional;
import edu.pgii.eventos.model.Usuario;
import edu.pgii.eventos.model.Zona;
import edu.pgii.eventos.patterns.behavioral.ComandoCambioEstadoEvento;
import edu.pgii.eventos.patterns.creational.ContextoAplicacion;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ControladorVistaPrincipal {
    private final ContextoAplicacion contexto;
    private final Runnable alCerrarSesion;
    private final List<Asiento> asientosSeleccionados = new ArrayList<>();

    @FXML private BorderPane raiz;
    @FXML private Label etiquetaSesion;
    @FXML private Label etiquetaNotificaciones;
    @FXML private TextField campoEventoNombre;
    @FXML private ComboBox<String> comboEventoCategoria;
    @FXML private TextField campoEventoCiudad;
    @FXML private DatePicker campoEventoFecha;
    @FXML private ComboBox<Recinto> comboEventoRecinto;
    @FXML private TextArea campoEventoDescripcion;
    @FXML private TextField campoRecintoNombre;
    @FXML private TextField campoRecintoCiudad;
    @FXML private TextField campoRecintoAforo;
    @FXML private ComboBox<Integer> comboRecintoSecciones;
    @FXML private TextField campoPrecioVip;
    @FXML private TextField campoPrecioNormal;
    @FXML private TextField campoVipA;
    @FXML private TextField campoVipB;
    @FXML private TextField campoVipC;
    @FXML private TextField campoNormalA;
    @FXML private TextField campoNormalB;
    @FXML private TextField campoNormalC;
    @FXML private TextField filtroCiudad;
    @FXML private TextField filtroCategoria;
    @FXML private DatePicker filtroFecha;
    @FXML private TextField filtroPrecioMaximo;
    @FXML private TableView<Evento> tablaEventos;
    @FXML private TableView<Evento> tablaEventosAdmin;
    @FXML private TableView<Compra> tablaCompras;
    @FXML private TableView<Usuario> tablaUsuarios;
    @FXML private TextArea areaDetalleEvento;
    @FXML private ComboBox<Zona> comboZona;
    @FXML private FlowPane mapaAsientosA;
    @FXML private FlowPane mapaAsientosB;
    @FXML private FlowPane mapaAsientosC;
    @FXML private ListView<CheckBox> listaServicios;
    @FXML private TextField campoNombreCompra;
    @FXML private TextField campoMetodoPagoCompra;
    @FXML private TextField campoEditarNombreCompra;
    @FXML private TextField campoEditarMetodoPago;
    @FXML private ComboBox<ReportFormat> comboFormatoReporteCompras;
    @FXML private Label etiquetaTasaCancelacion;
    @FXML private BarChart<String, Number> graficaVentas;
    @FXML private PieChart graficaOcupacion;
    @FXML private Tab pestanaAdmin;
    @FXML private Button botonPublicarEvento;
    @FXML private Button botonPausarEvento;
    @FXML private Button botonQuitarEvento;

    @FXML private TableColumn<Evento, String> colEventoNombre;
    @FXML private TableColumn<Evento, String> colEventoCategoria;
    @FXML private TableColumn<Evento, String> colEventoCiudad;
    @FXML private TableColumn<Evento, Object> colEventoFecha;
    @FXML private TableColumn<Evento, Object> colEventoEstado;
    @FXML private TableColumn<Evento, String> colAdminEventoNombre;
    @FXML private TableColumn<Evento, String> colAdminEventoCategoria;
    @FXML private TableColumn<Evento, String> colAdminEventoCiudad;
    @FXML private TableColumn<Evento, Object> colAdminEventoFecha;
    @FXML private TableColumn<Evento, Object> colAdminEventoEstado;
    @FXML private TableColumn<Compra, String> colCompraId;
    @FXML private TableColumn<Compra, Object> colCompraEvento;
    @FXML private TableColumn<Compra, String> colCompraNombre;
    @FXML private TableColumn<Compra, String> colCompraAsientos;
    @FXML private TableColumn<Compra, Object> colCompraFecha;
    @FXML private TableColumn<Compra, Object> colCompraEstado;
    @FXML private TableColumn<Compra, Object> colCompraTotal;
    @FXML private TableColumn<Usuario, String> colUsuarioId;
    @FXML private TableColumn<Usuario, String> colUsuarioNombre;
    @FXML private TableColumn<Usuario, String> colUsuarioCorreo;
    @FXML private TableColumn<Usuario, String> colUsuarioTelefono;
    @FXML private TableColumn<Usuario, Object> colUsuarioRol;

    public ControladorVistaPrincipal(ContextoAplicacion contexto, Runnable alCerrarSesion) {
        this.contexto = contexto;
        this.alCerrarSesion = alCerrarSesion;
        contexto.notifications().subscribe(mensaje -> {
            if (etiquetaNotificaciones != null) {
                etiquetaNotificaciones.setText(mensaje);
            }
        });
    }

    @FXML private void initialize() {
        configurarControles();
        refrescarTodo();
    }

    private void configurarControles() {
        configurarColumnasEventos(colEventoNombre, colEventoCategoria, colEventoCiudad, colEventoFecha, colEventoEstado);
        configurarColumnasEventos(colAdminEventoNombre, colAdminEventoCategoria, colAdminEventoCiudad, colAdminEventoFecha, colAdminEventoEstado);
        colCompraId.setCellValueFactory(new PropertyValueFactory<>("idCompra"));
        colCompraEvento.setCellValueFactory(new PropertyValueFactory<>("evento"));
        colCompraNombre.setCellValueFactory(new PropertyValueFactory<>("nombreComprador"));
        colCompraAsientos.setCellValueFactory(new PropertyValueFactory<>("resumenAsientos"));
        colCompraFecha.setCellValueFactory(new PropertyValueFactory<>("fechaCreacion"));
        colCompraEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colCompraTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colUsuarioId.setCellValueFactory(new PropertyValueFactory<>("idUsuario"));
        colUsuarioNombre.setCellValueFactory(new PropertyValueFactory<>("nombreCompleto"));
        colUsuarioCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colUsuarioTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colUsuarioRol.setCellValueFactory(new PropertyValueFactory<>("role"));
        comboEventoCategoria.setItems(FXCollections.observableArrayList("Teatro", "Concierto", "Conferencia"));
        comboRecintoSecciones.setItems(FXCollections.observableArrayList(1, 2, 3));
        comboRecintoSecciones.setValue(3);
        comboFormatoReporteCompras.setItems(FXCollections.observableArrayList(ReportFormat.CSV, ReportFormat.PDF));
        comboFormatoReporteCompras.setValue(ReportFormat.CSV);
        comboEventoRecinto.setItems(FXCollections.observableArrayList(contexto.recintos().findAll()));
        tablaEventos.getSelectionModel().selectedItemProperty().addListener((obs, previo, seleccionado) -> mostrarDetalleEvento(seleccionado));
        comboZona.setOnAction(evento -> refrescarAsientos());
        tablaCompras.getSelectionModel().selectedItemProperty().addListener((obs, previo, compra) -> mostrarCompraParaEditar(compra));
        Usuario usuario = contexto.auth().getCurrentUser();
        if (usuario != null) {
            campoNombreCompra.setText(usuario.getNombreCompleto());
        }
    }

    private void configurarColumnasEventos(TableColumn<Evento, String> nombre, TableColumn<Evento, String> categoria,
                                           TableColumn<Evento, String> ciudad, TableColumn<Evento, Object> fecha,
                                           TableColumn<Evento, Object> estado) {
        nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        categoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        ciudad.setCellValueFactory(new PropertyValueFactory<>("ciudad"));
        fecha.setCellValueFactory(new PropertyValueFactory<>("fechaHora"));
        estado.setCellValueFactory(new PropertyValueFactory<>("estado"));
    }

    @FXML private void cerrarSesion() {
        contexto.auth().logout();
        alCerrarSesion.run();
    }

    @FXML private void filtrarEventos() {
        BigDecimal precioMaximo = filtroPrecioMaximo.getText().isBlank() ? null : new BigDecimal(filtroPrecioMaximo.getText());
        tablaEventos.setItems(FXCollections.observableArrayList(contexto.facade().buscarEventos(
                filtroCiudad.getText(), filtroCategoria.getText(), filtroFecha.getValue(), precioMaximo)));
    }

    @FXML private void limpiarFiltros() {
        filtroCiudad.clear();
        filtroCategoria.clear();
        filtroFecha.setValue(null);
        filtroPrecioMaximo.clear();
        refrescarEventos();
    }

    @FXML private void crearCompraDesdeAsientos() {
        Usuario usuario = contexto.auth().getCurrentUser();
        Evento evento = tablaEventos.getSelectionModel().getSelectedItem();
        Zona zona = comboZona.getValue();
        if (usuario == null || usuario.getRole() != Role.USUARIO || evento == null || zona == null || asientosSeleccionados.isEmpty()) {
            error("Debe iniciar como usuario y seleccionar evento, zona y al menos un asiento.");
            return;
        }
        if (campoNombreCompra.getText().isBlank() || campoMetodoPagoCompra.getText().isBlank()) {
            error("Ingrese nombre del comprador y metodo de pago.");
            return;
        }
        if (evento.getEstado() != EstadoEvento.PUBLICADO) {
            error("El evento debe estar publicado para crear compras.");
            return;
        }
        try {
            MetodoPago metodoPago = crearMetodoPago(usuario, campoMetodoPagoCompra.getText());
            Compra compra = contexto.facade().comprar(usuario, evento, zona, new ArrayList<>(asientosSeleccionados),
                    serviciosSeleccionados(), campoNombreCompra.getText(), metodoPago);
            asientosSeleccionados.clear();
            refrescarTodo();
            mostrarDetalleEvento(evento);
            info("Compra creada", "Compra " + compra.getIdCompra() + " creada. Confirme el pago desde la seccion de compras.");
        } catch (Exception ex) {
            error(ex.getMessage());
        }
    }

    @FXML private void confirmarCompraSeleccionada() {
        Compra compra = tablaCompras.getSelectionModel().getSelectedItem();
        if (compra == null) {
            error("Seleccione una compra.");
            return;
        }
        if (!puedeGestionarCompra(compra)) {
            error("Solo el administrador o el usuario que hizo la compra pueden modificarla.");
            return;
        }
        if (compra.getEstado() != EstadoCompra.CREADA) {
            error("Solo se pueden confirmar compras creadas.");
            return;
        }
        MetodoPago metodoPago = campoEditarMetodoPago.getText().isBlank()
                ? compra.getMetodoPagoSeleccionado()
                : crearMetodoPago(compra.getUsuario(), campoEditarMetodoPago.getText());
        if (metodoPago == null) {
            error("Seleccione metodo de pago.");
            return;
        }
        contexto.facade().pagar(compra, metodoPago);
        refrescarTodo();
        tablaCompras.refresh();
    }

    @FXML private void editarCompraSeleccionada() {
        Compra compra = tablaCompras.getSelectionModel().getSelectedItem();
        if (compra == null) {
            error("Seleccione una compra.");
            return;
        }
        if (!puedeGestionarCompra(compra)) {
            error("Solo el administrador o el usuario que hizo la compra pueden modificarla.");
            return;
        }
        try {
            MetodoPago metodoPago = campoEditarMetodoPago.getText().isBlank()
                    ? compra.getMetodoPagoSeleccionado()
                    : crearMetodoPago(compra.getUsuario(), campoEditarMetodoPago.getText());
            contexto.comprasService().updateDatosCompra(compra, campoEditarNombreCompra.getText(),
                    metodoPago);
            refrescarTodo();
        } catch (Exception ex) {
            error(ex.getMessage());
        }
    }

    @FXML private void cancelarCompraSeleccionada() {
        Compra compra = tablaCompras.getSelectionModel().getSelectedItem();
        if (compra == null) {
            error("Seleccione una compra.");
            return;
        }
        if (!puedeGestionarCompra(compra)) {
            error("Solo el administrador o el usuario que hizo la compra pueden cancelarla.");
            return;
        }
        boolean compraFinalizada = compra.getEstado() == EstadoCompra.PAGADA || compra.getEstado() == EstadoCompra.CONFIRMADA;
        boolean tieneSeguro = compra.getServicios().stream()
                .anyMatch(servicio -> servicio.getTipo() == TipoServicio.SEGURO_CANCELACION);
        if (compraFinalizada && !tieneSeguro) {
            error("La compra ya fue confirmada y solo se puede cancelar si se compro seguro de cancelacion.");
            return;
        }
        compra.getEntradas().forEach(entrada -> {
            if (entrada.getAsiento() != null) {
                entrada.getAsiento().setEstado(EstadoAsiento.DISPONIBLE);
            }
        });
        contexto.compras().delete(compra.getIdCompra());
        refrescarTodo();
        tablaCompras.refresh();
    }

    @FXML private void generarReporteCompras() {
        try {
            var ruta = contexto.facade().exportarReporte(ReportType.VENTAS, comboFormatoReporteCompras.getValue(),
                    LocalDate.now().minusYears(1), LocalDate.now().plusYears(1), comprasVisibles());
            info("Reporte generado", ruta.toString());
        } catch (Exception ex) {
            error(ex.getMessage());
        }
    }

    @FXML private void publicarEvento() { cambiarEventoAdmin(EstadoEvento.PUBLICADO); }
    @FXML private void pausarEvento() { cambiarEventoAdmin(EstadoEvento.PAUSADO); }
    @FXML private void crearEventoAdmin() {
        Usuario usuario = contexto.auth().getCurrentUser();
        if (usuario == null || usuario.getRole() != Role.ADMIN) {
            error("Solo el administrador puede crear eventos.");
            return;
        }
        if (campoEventoNombre.getText().isBlank() || comboEventoCategoria.getValue() == null
                || campoEventoCiudad.getText().isBlank() || comboEventoRecinto.getValue() == null) {
            error("Ingrese nombre, categoria, ciudad y recinto.");
            return;
        }
        if (contexto.recintos().findAll().isEmpty()) {
            error("No hay recintos disponibles para asociar el evento.");
            return;
        }
        LocalDate fecha = campoEventoFecha.getValue() == null ? LocalDate.now().plusDays(7) : campoEventoFecha.getValue();
        Evento evento = new Evento(
                "EV-" + UUID.randomUUID(),
                campoEventoNombre.getText(),
                comboEventoCategoria.getValue(),
                campoEventoDescripcion.getText().isBlank() ? "Evento creado por administrador." : campoEventoDescripcion.getText(),
                campoEventoCiudad.getText(),
                LocalDateTime.of(fecha, LocalTime.of(20, 0)),
                EstadoEvento.BORRADOR,
                "Cancelacion hasta 24 horas antes.",
                "Reembolso simulado segun politica.",
                comboEventoRecinto.getValue());
        contexto.events().save(evento);
        campoEventoNombre.clear();
        comboEventoCategoria.setValue(null);
        campoEventoCiudad.clear();
        campoEventoFecha.setValue(null);
        comboEventoRecinto.setValue(null);
        campoEventoDescripcion.clear();
        refrescarTodo();
    }

    @FXML private void crearRecintoAdmin() {
        Usuario usuario = contexto.auth().getCurrentUser();
        if (usuario == null || usuario.getRole() != Role.ADMIN) {
            error("Solo el administrador puede crear recintos.");
            return;
        }
        try {
            int secciones = comboRecintoSecciones.getValue() == null ? 3 : comboRecintoSecciones.getValue();
            int vipA = numeroSeccion(campoVipA, secciones >= 1);
            int vipB = numeroSeccion(campoVipB, secciones >= 2);
            int vipC = numeroSeccion(campoVipC, secciones >= 3);
            int normalA = numeroSeccion(campoNormalA, secciones >= 1);
            int normalB = numeroSeccion(campoNormalB, secciones >= 2);
            int normalC = numeroSeccion(campoNormalC, secciones >= 3);
            int aforo = entero(campoRecintoAforo);
            int suma = vipA + vipB + vipC + normalA + normalB + normalC;
            if (campoRecintoNombre.getText().isBlank() || campoRecintoCiudad.getText().isBlank()) {
                error("Ingrese nombre y ciudad del recinto.");
                return;
            }
            if (aforo != suma) {
                error("El aforo debe coincidir con la suma de asientos por seccion y zona. Suma actual: " + suma);
                return;
            }
            Recinto recinto = new Recinto("R-" + UUID.randomUUID(), campoRecintoNombre.getText(),
                    "Direccion registrada por admin", campoRecintoCiudad.getText());
            recinto.getZonas().add(crearZonaPersonalizada("VIP", campoPrecioVip.getText(), vipA, vipB, vipC));
            recinto.getZonas().add(crearZonaPersonalizada("Normal", campoPrecioNormal.getText(), normalA, normalB, normalC));
            contexto.recintos().save(recinto);
            comboEventoRecinto.setItems(FXCollections.observableArrayList(contexto.recintos().findAll()));
            limpiarFormularioRecinto();
            info("Recinto creado", recinto.getNombre() + " agregado a la lista de recintos.");
        } catch (NumberFormatException ex) {
            error("Aforo, precios y cantidades de asientos deben ser numeros validos.");
        }
    }

    @FXML private void quitarEvento() {
        Usuario usuario = contexto.auth().getCurrentUser();
        if (usuario == null || usuario.getRole() != Role.ADMIN) {
            error("Solo el administrador puede quitar eventos.");
            return;
        }
        Evento seleccionado = tablaEventosAdmin.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            error("Seleccione un evento.");
            return;
        }
        contexto.compras().findAll().stream()
                .filter(compra -> compra.getEvento().getIdEvento().equals(seleccionado.getIdEvento()))
                .toList()
                .forEach(compra -> {
                    compra.getEntradas().forEach(entrada -> {
                        if (entrada.getAsiento() != null) {
                            entrada.getAsiento().setEstado(EstadoAsiento.DISPONIBLE);
                        }
                    });
                    contexto.compras().delete(compra.getIdCompra());
                });
        contexto.events().delete(seleccionado);
        refrescarTodo();
    }

    private void mostrarDetalleEvento(Evento evento) {
        if (evento == null) return;
        areaDetalleEvento.setText("Evento: " + evento.getNombre() + "\nCategoria: " + evento.getCategoria()
                + "\nCiudad: " + evento.getCiudad() + "\nFecha: " + evento.getFechaHora()
                + "\nEstado: " + evento.getEstado() + "\nAforo: " + evento.getAforo()
                + "\nRecinto: " + evento.getRecinto()
                + "\nPolitica: " + evento.getPoliticaCancelacion()
                + "\n\n" + evento.getDescripcion());
        asientosSeleccionados.clear();
        comboZona.setItems(FXCollections.observableArrayList(evento.getRecinto().getZonas()));
        if (!comboZona.getItems().isEmpty()) comboZona.getSelectionModel().selectFirst();
        listaServicios.setItems(FXCollections.observableArrayList(contexto.serviciosDisponibles().stream()
                .map(servicio -> new CheckBox(servicio.toString()))
                .toList()));
    }

    private void refrescarAsientos() {
        Zona zona = comboZona.getValue();
        if (zona == null) return;
        asientosSeleccionados.clear();
        mapaAsientosA.getChildren().clear();
        mapaAsientosB.getChildren().clear();
        mapaAsientosC.getChildren().clear();
        Evento evento = tablaEventos.getSelectionModel().getSelectedItem();
        int[] consecutivos = new int[3];
        zona.getAsientos().forEach(asiento -> {
            String seccion = seccionVisualPorCategoria(evento, asiento.getFila());
            int indice = Math.max(0, Math.min(2, seccion.charAt(0) - 'A'));
            String etiqueta = seccion + (++consecutivos[indice]);
            panelParaFila(seccion).getChildren().add(crearBotonAsiento(asiento, seccion, etiqueta));
        });
    }

    private String seccionVisualPorCategoria(Evento evento, String fila) {
        if (evento == null) {
            return fila;
        }
        String categoria = evento.getCategoria() == null ? "" : evento.getCategoria().toLowerCase();
        if (categoria.contains("concierto")) {
            return fila;
        }
        if (categoria.contains("teatro") || categoria.contains("conferencia")) {
            return fila.equals("A") ? "A" : "B";
        }
        return fila;
    }

    private FlowPane panelParaFila(String fila) {
        return switch (fila) {
            case "A" -> mapaAsientosA;
            case "B" -> mapaAsientosB;
            default -> mapaAsientosC;
        };
    }

    private ToggleButton crearBotonAsiento(Asiento asiento, String seccionVisual, String etiqueta) {
        ToggleButton boton = new ToggleButton(etiqueta);
        boton.setMinSize(54, 34);
        boton.setMaxSize(54, 34);
        boton.getStyleClass().add("asiento-" + seccionVisual.toLowerCase());
        boolean disponible = asiento.getEstado() == EstadoAsiento.DISPONIBLE;
        boton.setDisable(!disponible);
        if (!disponible) {
            boton.getStyleClass().add("asiento-ocupado");
        }
        boton.setOnAction(evento -> {
            if (boton.isSelected()) {
                asientosSeleccionados.add(asiento);
            } else {
                asientosSeleccionados.remove(asiento);
            }
        });
        return boton;
    }

    private void mostrarCompraParaEditar(Compra compra) {
        if (compra == null) return;
        campoEditarNombreCompra.setText(compra.getNombreComprador());
        campoEditarMetodoPago.setText(compra.getMetodoPagoSeleccionado() == null ? "" : compra.getMetodoPagoSeleccionado().getReferencia());
    }

    private MetodoPago crearMetodoPago(Usuario usuario, String referencia) {
        MetodoPago metodoPago = new MetodoPago("MP-" + UUID.randomUUID(), "Metodo ingresado", referencia);
        usuario.getMetodosPago().add(metodoPago);
        return metodoPago;
    }

    private List<ServicioAdicional> serviciosSeleccionados() {
        List<ServicioAdicional> seleccionados = new ArrayList<>();
        for (int i = 0; i < listaServicios.getItems().size(); i++) {
            if (listaServicios.getItems().get(i).isSelected()) {
                seleccionados.add(contexto.serviciosDisponibles().get(i));
            }
        }
        return seleccionados;
    }

    private void cambiarEventoAdmin(EstadoEvento estado) {
        Usuario usuario = contexto.auth().getCurrentUser();
        if (usuario == null || usuario.getRole() != Role.ADMIN) {
            error("Solo el administrador puede cambiar el estado de los eventos.");
            return;
        }
        Evento seleccionado = tablaEventosAdmin.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            error("Seleccione un evento.");
            return;
        }
        new ComandoCambioEstadoEvento(contexto.events(), seleccionado, estado).execute();
        refrescarTodo();
        tablaEventosAdmin.refresh();
        tablaEventos.refresh();
    }

    private void refrescarTodo() {
        Usuario usuario = contexto.auth().getCurrentUser();
        etiquetaSesion.setText(usuario == null ? "Sin sesion" : usuario.getNombreCompleto() + " / " + usuario.getRole());
        aplicarPermisos(usuario);
        refrescarEventos();
        tablaCompras.setItems(FXCollections.observableArrayList(comprasVisibles()));
        tablaUsuarios.setItems(FXCollections.observableArrayList(contexto.usuarios().findAll()));
        refrescarMetricas();
    }

    private void refrescarEventos() {
        tablaEventos.setItems(FXCollections.observableArrayList(contexto.events().listAll().stream()
                .filter(evento -> evento.getEstado() == EstadoEvento.PUBLICADO)
                .toList()));
        tablaEventosAdmin.setItems(FXCollections.observableArrayList(contexto.events().listAll()));
        tablaEventos.refresh();
        tablaEventosAdmin.refresh();
    }

    private List<Compra> comprasVisibles() {
        Usuario usuario = contexto.auth().getCurrentUser();
        if (usuario == null) {
            return List.of();
        }
        return usuario.getRole() == Role.ADMIN ? contexto.comprasService().all() : contexto.comprasService().byUsuario(usuario);
    }

    private boolean puedeGestionarCompra(Compra compra) {
        Usuario usuario = contexto.auth().getCurrentUser();
        if (usuario == null || compra == null) {
            return false;
        }
        return usuario.getRole() == Role.ADMIN || compra.getUsuario().getIdUsuario().equals(usuario.getIdUsuario());
    }

    private void refrescarMetricas() {
        graficaVentas.getData().clear();
        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        serie.setName("Ingresos");
        contexto.metrics().ventasPorEvento().forEach((nombre, valor) -> serie.getData().add(new XYChart.Data<>(nombre, valor)));
        graficaVentas.getData().add(serie);
        graficaOcupacion.getData().setAll(contexto.metrics().ocupacionPorZona().entrySet().stream()
                .map(e -> new PieChart.Data(e.getKey(), e.getValue().doubleValue()))
                .toList());
        etiquetaTasaCancelacion.setText(String.format("Tasa de cancelacion: %.2f%%", contexto.metrics().tasaCancelacion()));
    }

    private void aplicarPermisos(Usuario usuario) {
        boolean esAdmin = usuario != null && usuario.getRole() == Role.ADMIN;
        if (pestanaAdmin != null) {
            pestanaAdmin.setDisable(!esAdmin);
        }
        if (botonPublicarEvento != null) {
            botonPublicarEvento.setDisable(!esAdmin);
            botonPausarEvento.setDisable(!esAdmin);
            botonQuitarEvento.setDisable(!esAdmin);
        }
    }

    private Zona crearZonaPersonalizada(String nombre, String precio, int a, int b, int c) {
        int capacidad = a + b + c;
        Zona zona = new Zona("Z-" + UUID.randomUUID(), nombre, capacidad, new BigDecimal(precio), true);
        agregarAsientos(zona, "A", a);
        agregarAsientos(zona, "B", b);
        agregarAsientos(zona, "C", c);
        return zona;
    }

    private void agregarAsientos(Zona zona, String fila, int cantidad) {
        for (int i = 1; i <= cantidad; i++) {
            zona.getAsientos().add(new Asiento("A-" + UUID.randomUUID(), fila, i, EstadoAsiento.DISPONIBLE));
        }
    }

    private int numeroSeccion(TextField campo, boolean activa) {
        return activa ? entero(campo) : 0;
    }

    private int entero(TextField campo) {
        return campo.getText().isBlank() ? 0 : Integer.parseInt(campo.getText());
    }

    private void limpiarFormularioRecinto() {
        campoRecintoNombre.clear();
        campoRecintoCiudad.clear();
        campoRecintoAforo.clear();
        campoPrecioVip.clear();
        campoPrecioNormal.clear();
        campoVipA.clear();
        campoVipB.clear();
        campoVipC.clear();
        campoNormalA.clear();
        campoNormalB.clear();
        campoNormalC.clear();
        comboRecintoSecciones.setValue(3);
    }

    private void info(String titulo, String mensaje) {
        new Alert(Alert.AlertType.INFORMATION, mensaje).showAndWait();
    }

    private void error(String mensaje) {
        new Alert(Alert.AlertType.ERROR, mensaje).showAndWait();
    }
}
