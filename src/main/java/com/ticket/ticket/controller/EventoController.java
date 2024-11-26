package com.ticket.ticket.controller;

import com.ticket.ticket.dto.EventoDTO;
import com.ticket.ticket.mapper.EventoMapper;
import com.ticket.ticket.model.Evento;
import com.ticket.ticket.service.EventoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/evento/v1")
@AllArgsConstructor
public class EventoController {

    @Autowired
    private EventoService eventoService;


    /**
     * Obtiene un evento por su ID.
     * <p>
     * Este endpoint recibe el ID de un evento como parámetro en la URL y devuelve los detalles del evento correspondiente
     * en formato {@code EventoDTO}. Si el evento con el ID especificado no se encuentra, se devuelve una respuesta HTTP 404 (Not Found).
     * </p>
     *
     * <p><b>Excepciones posibles:</b></p>
     * <ul>
     *     <li><b>EventoNoEncontradoException:</b> Si no se encuentra el evento con el ID proporcionado.</li>
     * </ul>
     *
     * @param eventoId El ID del evento que se desea obtener.
     * @return Una respuesta que contiene el {@code EventoDTO} del evento solicitado o una respuesta 404 si el evento no se encuentra.
     *
     * <p><b>Ejemplo de solicitud:</b></p>
     * <pre>
     * - Método: GET
     * - URL: /{eventoId}
     * </pre>
     * <p><b>URL de ejemplo:</b> /1</p>
     *
     * <p><b>Respuesta esperada:</b></p>
     * <ul>
     *     <li><b>200 OK:</b> Si el evento es encontrado y los detalles son devueltos en el formato {@code EventoDTO}.</li>
     *     <li><b>404 NOT FOUND:</b> Si no existe un evento con el ID especificado.</li>
     * </ul>
     *
     * <p><b>Ejemplo de respuesta (si el evento es encontrado):</b></p>
     * <pre>
     * HTTP/1.1 200 OK
     * Content-Type: application/json

     * </pre>
     *
     * <p><b>Ejemplo de respuesta (si el evento no es encontrado):</b></p>
     * <pre>
     * HTTP/1.1 404 Not Found
     * </pre>
     *
     * <p><b>Notas:</b></p>
     * <ul>
     *     <li>Este endpoint es útil para obtener detalles específicos de un evento a partir de su ID.</li>
     *     <li>Si el evento con el ID solicitado no existe, se devuelve una respuesta {@code 404 Not Found}.</li>
     * </ul>
     */
    @GetMapping("/{eventoId}")
    public ResponseEntity<EventoDTO> eventoById(@PathVariable Long eventoId){

        Evento evento = eventoService.getEventoById(eventoId);
        if (evento != null){
            EventoDTO eventoDTO = EventoMapper.INSTANCE.toEventoDTO(evento);
            return ResponseEntity.ok(eventoDTO);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Actualiza un evento existente en el sistema.
     * <p>
     * Este endpoint permite actualizar los detalles de un evento existente utilizando su ID. Recibe un objeto {@code Evento}
     * con los nuevos datos a través del cuerpo de la solicitud. Si el evento con el ID proporcionado no se encuentra,
     * se devuelve un estado HTTP 404 (Not Found). Si la actualización es exitosa, se retorna un mensaje de éxito con estado HTTP 200 (OK).
     * </p>
     *
     * <p><b>Excepciones posibles:</b></p>
     * <ul>
     *     <li><b>404 NOT FOUND:</b> Si el evento con el ID especificado no existe en el sistema.</li>
     *     <li><b>500 INTERNAL SERVER ERROR:</b> Si ocurre un error inesperado durante la actualización del evento.</li>
     * </ul>
     *
     * @param eventId El ID del evento que se desea actualizar.
     * @param evento El objeto {@code Evento} que contiene los nuevos datos del evento.
     * @return Una respuesta con un mensaje que indica si la actualización fue exitosa o si el evento no fue encontrado.
     *
     * <p><b>Respuestas esperadas:</b></p>
     * <ul>
     *     <li><b>200 OK:</b> Si el evento se actualizó correctamente.</li>
     *     <li><b>404 NOT FOUND:</b> Si el evento con el ID especificado no se encuentra en el sistema.</li>
     *     <li><b>500 INTERNAL SERVER ERROR:</b> Si ocurre un error inesperado durante la actualización del evento.</li>
     * </ul>
     *
     * <p><b>Ejemplo de solicitud:</b></p>
     * <pre>
     * - Método: PUT
     * - URL: /update/{eventId}
     * </pre>
     * <p><b>Cuerpo de la solicitud (JSON):</b></p>
     * <pre>
     * {
     *   "nombre": "Concierto de Jazz",
     *   "fecha": "2024-12-15",
     *   "hora": "19:30",
     *   "ubicacion": "Teatro Municipal",
     *   "descripcion": "Un evento exclusivo con los mejores músicos de jazz.",
     *   "precio": 75.00,
     *   "capacidad": 500
     * }
     * </pre>
     *
     * <p><b>Notas:</b></p>
     * <ul>
     *     <li>Asegúrate de que el ID del evento sea válido y que el evento exista en el sistema.</li>
     *     <li>Este endpoint permite modificar la información básica del evento, como nombre, fecha, hora, ubicación,
     *         descripción, precio y capacidad.</li>
     * </ul>
     */
    @PutMapping("/update/{eventId}")
    public ResponseEntity<?> updateEvent(@PathVariable Long eventId, @RequestBody Evento evento){
        Boolean updateEvent = eventoService.updateEvent(eventId,evento);
        if (updateEvent){
            return ResponseEntity.ok("Evento actualizado exitosamente");
        }
        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Evento no encontrado");
    }

    /**
     * Elimina un evento existente del sistema.
     * <p>
     * Este endpoint permite eliminar un evento utilizando su ID. Antes de proceder con la eliminación,
     * verifica si el evento existe en el sistema. Si el evento no se encuentra, se devuelve un estado HTTP 404 (Not Found).
     * Si la eliminación es exitosa, se retorna un estado HTTP 200 (OK) sin contenido adicional.
     * </p>
     *
     * <p><b>Excepciones posibles:</b></p>
     * <ul>
     *     <li><b>404 NOT FOUND:</b> Si el evento con el ID especificado no existe en el sistema.</li>
     *     <li><b>500 INTERNAL SERVER ERROR:</b> Si ocurre un error inesperado durante la eliminación del evento.</li>
     * </ul>
     *
     * @param eventId El ID del evento que se desea eliminar.
     * @return Una respuesta con estado {@code 200 OK} si el evento fue eliminado correctamente o {@code 404 NOT FOUND} si no existe.
     *
     * <p><b>Respuestas esperadas:</b></p>
     * <ul>
     *     <li><b>200 OK:</b> Si el evento se eliminó correctamente.</li>
     *     <li><b>404 NOT FOUND:</b> Si el evento con el ID especificado no se encuentra en el sistema.</li>
     *     <li><b>500 INTERNAL SERVER ERROR:</b> Si ocurre un error inesperado durante la eliminación.</li>
     * </ul>
     *
     * <p><b>Ejemplo de solicitud:</b></p>
     * <pre>
     * - Método: DELETE
     * - URL: /delete/{eventId}
     * </pre>
     * <p><b>Notas:</b></p>
     * <ul>
     *     <li>Asegúrate de que el ID del evento sea válido antes de enviar la solicitud.</li>
     *     <li>Este endpoint elimina permanentemente el evento del sistema.</li>
     * </ul>
     */
    @DeleteMapping("/delete/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long eventId){
        if (eventoService.existsById(eventId)) {
            eventoService.deleteEvent(eventId);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    /**
     * Obtiene todos los eventos disponibles en el sistema.
     * <p>
     * Este endpoint recupera una lista de todos los eventos registrados en el sistema. Si no se encuentran eventos,
     * se devuelve un estado HTTP 204 (No Content). Si ocurre un error durante el proceso de recuperación de eventos,
     * se devuelve un estado HTTP 500 (Internal Server Error).
     * </p>
     *
     * <p><b>Excepciones posibles:</b></p>
     * <ul>
     *     <li><b>500 INTERNAL SERVER ERROR:</b> Si ocurre un error inesperado al intentar recuperar los eventos.</li>
     * </ul>
     *
     * @return Una respuesta que contiene una lista de objetos {@code EventoDTO} con la información de todos los eventos.
     *         Si no se encuentran eventos, se retorna un estado {@code 204 No Content}.
     *
     * <p><b>Respuestas esperadas:</b></p>
     * <ul>
     *     <li><b>200 OK:</b> Si se encuentran eventos y la lista se retorna correctamente.</li>
     *     <li><b>204 NO CONTENT:</b> Si no se encuentran eventos registrados en el sistema.</li>
     *     <li><b>500 INTERNAL SERVER ERROR:</b> Si ocurre un error inesperado durante la recuperación de los eventos.</li>
     * </ul>
     *
     * <p><b>Ejemplo de solicitud:</b></p>
     * <pre>
     * - Método: GET
     * - URL: /all
     * </pre>
     *
     * <p><b>Respuesta esperada:</b></p>
     * Si hay eventos disponibles:
     * <pre>
     * HTTP/1.1 200 OK
     * Content-Type: application/json

     * </pre>
     *
     * Si no hay eventos disponibles:
     * <pre>
     * HTTP/1.1 204 No Content
     * </pre>
     *
     * <p><b>Notas:</b></p>
     * <ul>
     *     <li>Si no hay eventos registrados en el sistema, se retornará un estado {@code 204 No Content}.</li>
     *     <li>Este endpoint es útil para mostrar todos los eventos disponibles en el sistema.</li>
     * </ul>
     */
    @GetMapping("/all")
    public ResponseEntity<List<EventoDTO>> allEvent(){
        try {
            List<EventoDTO> event = eventoService.getAllEvent();
            if (event.isEmpty()) {
                return ResponseEntity.noContent().build(); // 204 NO CONTENT
            }

            return ResponseEntity.ok(event);
        } catch (Exception e) {
            // Manejo de excepciones
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }



    /**
     * Crea un nuevo evento en el sistema.
     * <p>
     * Este endpoint permite crear un nuevo evento a través de una solicitud {@code POST}. Se debe enviar un objeto {@code Evento}
     * en el cuerpo de la solicitud que contenga todos los detalles del evento, como el nombre, la fecha, la hora, la ubicación,
     * la descripción, el precio, la capacidad y la cantidad de tickets vendidos.
     * El proceso incluye la validación de los datos del evento antes de realizar la creación. Si la creación es exitosa, se
     * devuelve una respuesta con el estado HTTP 200 (OK) y un mensaje de éxito. En caso de error, se devuelve un mensaje adecuado.
     * </p>
     *
     * <p><b>Excepciones posibles:</b></p>
     * <ul>
     *     <li><b>400 BAD REQUEST:</b> Si los datos del evento son inválidos o faltan campos obligatorios.</li>
     *     <li><b>500 INTERNAL SERVER ERROR:</b> Si ocurre un error inesperado durante el proceso de creación del evento.</li>
     * </ul>
     *
     * @param evento El objeto {@code Evento} que contiene la información completa del evento a crear. Este objeto debe incluir:
     *               - Nombre, fecha, hora, ubicación, descripción, precio, capacidad y cantidad de tickets vendidos.
     * @return Una respuesta con el estado de la creación del evento:
     *         - Si la creación es exitosa, se retorna el estado {@code 200 OK} con un mensaje de éxito.
     *         - En caso de error, se retorna un mensaje adecuado con el estado correspondiente.
     *
     * <p><b>Ejemplo de solicitud:</b></p>
     * <pre>
     * - Método: POST
     * - URL: /create
     * </pre>
     * <p><b>Cuerpo de la solicitud (JSON):</b></p>
     * <pre>
     * {
     *   "nombre": "Concierto de Rock",
     *   "fecha": "2024-12-01",
     *   "hora": "20:00",
     *   "ubicacion": "Estadio Nacional",
     *   "descripcion": "Un gran concierto de rock con varias bandas.",
     *   "precio": 50.00,
     *   "capacidad": 1000,
     *   "ticketsVendidos": 0
     * }
     * </pre>
     *
     * <p><b>Respuestas esperadas:</b></p>
     * <ul>
     *     <li><b>200 OK:</b> Si el evento es creado con éxito y la respuesta incluye un mensaje de éxito.</li>
     *     <li><b>400 BAD REQUEST:</b> Si los datos del evento son inválidos o faltan campos esenciales.</li>
     *     <li><b>500 INTERNAL SERVER ERROR:</b> Si ocurre un error inesperado al procesar la creación del evento.</li>
     * </ul>
     *
     * <p><b>Notas:</b></p>
     * <ul>
     *     <li>Asegúrese de que todos los campos requeridos estén presentes y sean válidos antes de enviar la solicitud.</li>
     *     <li>Este método valida los datos del evento, como la fecha, hora, y capacidad, para asegurar que el evento cumpla
     *         con los requisitos del sistema.</li>
     * </ul>
     */
    @PostMapping("/create")
    public ResponseEntity<?> createEvento(@RequestBody Evento evento){
        Evento eventoGuardado = eventoService.saveEvento(evento);

        return ResponseEntity.status(200).body("creado con exito");
    }

}
