package edu.pgii.eventos.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class AlmacenDatos<T> {
    private final Map<String, T> datos = new LinkedHashMap<>();
    private final Function<T, String> extractorId;

    /**
     * Crea un almacen usando una funcion para obtener el id de cada entidad.
     *
     * @param extractorId funcion que extrae el identificador de una entidad
     */
    public AlmacenDatos(Function<T, String> extractorId) {
        this.extractorId = extractorId;
    }

    /**
     * Obtiene todas las entidades conservando el orden de insercion.
     *
     * @return lista con todas las entidades almacenadas
     */
    public List<T> findAll() {
        return new ArrayList<>(datos.values());
    }

    /**
     * Busca una entidad por su identificador.
     *
     * @param id identificador de la entidad
     * @return entidad encontrada o un Optional vacio
     */
    public Optional<T> findById(String id) {
        return Optional.ofNullable(datos.get(id));
    }

    /**
     * Guarda o reemplaza una entidad segun su identificador.
     *
     * @param entidad entidad que se desea almacenar
     */
    public void save(T entidad) {
        datos.put(extractorId.apply(entidad), entidad);
    }

    /**
     * Elimina una entidad del almacen.
     *
     * @param id identificador de la entidad a eliminar
     */
    public void delete(String id) {
        datos.remove(id);
    }
}
