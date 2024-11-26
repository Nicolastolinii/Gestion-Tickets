package com.ticket.ticket.controller;


import com.ticket.ticket.dto.TicketDTO;
import com.ticket.ticket.dto.TicketResponseDTO;
import com.ticket.ticket.model.Ticket;
import com.ticket.ticket.repository.TicketRepo;
import com.ticket.ticket.service.TicketService;
import com.ticket.ticket.utils.CustomExceptions;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/ticket/v1")
public class TicketController {

    @Autowired
    private TicketRepo ticketRepo;

    @Autowired
    private TicketService ticketService;

    /**
     * Obtiene todos los tickets asociados a un usuario específico.
     * <p>
     * Este endpoint permite obtener una lista de tickets que están asociados a un usuario,
     * identificándolos a través de su ID. Si el usuario no tiene tickets, se devuelve una respuesta
     * indicando que no hay contenido.
     * </p>
     *
     * @param userId El ID del usuario cuyo tickets se desean recuperar.
     * @return Una lista de tickets asociados al usuario.
     *
     * <p><b>Ejemplo de solicitud:</b></p>
     * <pre>
     * GET /user/1
     * </pre>
     *
     * <p><b>En este ejemplo, se pasa el parámetro "userId" como parte de la URL.</b></p>
     *
     * <p><b>Respuesta esperada:</b></p>
     * <pre>
     * - 200 OK: Si el usuario tiene tickets, devuelve la lista de tickets.
     * - 204 NO CONTENT: Si el usuario no tiene tickets asociados.
     * </pre>
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TicketResponseDTO>> getTicketByUser(@PathVariable Long userId){
        List<TicketResponseDTO> tickets = ticketService.getTicketByUser(userId);
        if (tickets.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tickets);
    }

    /**
     * Verifica si el ticket con el código proporcionado es válido para el evento especificado.
     *
     * @param code El código único del ticket que se desea verificar.
     * @param eventoId El ID del evento con el cual se desea validar el ticket.
     * @return Una respuesta que indica si el ticket es válido o no para el evento.
     *
     * Ejemplo de solicitud:
     * <pre>
     * - Método: GET
     *
     * - URL: /verify?code=2dd33e99-de5a-4ac8-9c3a-75f7fd26468b&eventoId=1
     * </pre>
     *
     * En este ejemplo, se pasan los parámetros "code" y "eventoId" como parámetros de consulta (query parameters).
     *
     * Respuesta esperada:
     * - 200 OK: Si el ticket es válido.
     * - 404 NOT FOUND: Si el ticket no existe.
     * - 400 BAD REQUEST: Si el ticket no corresponde al evento especificado.
     */
    @GetMapping("/verify")
    public ResponseEntity<?> verifyTicket(@RequestParam String code, @RequestParam Long eventoId){
        Ticket ticket = ticketRepo.findByCodigo(code);

        if (ticket == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("El ticket con el código proporcionado no fue encontrado.");
        }
        if (!ticket.getEvento().getId().equals(eventoId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El ticket no pertenece al evento especificado.");
        }
        return ResponseEntity.ok("ticket valido.");
    }


    /**
     * Crea un nuevo ticket para un evento.
     * <p>
     * Este endpoint permite crear un ticket para un evento específico. Se debe enviar un objeto {@code TicketDTO} en el cuerpo de la solicitud
     * que contenga los detalles del ticket. El proceso incluye la verificación de diversas condiciones, como la disponibilidad del evento y la
     * capacidad, y en caso de éxito, se devuelve un objeto {@code TicketResponseDTO} con el ticket creado.
     * </p>
     *
     * <p><b>Excepciones posibles:</b></p>
     * <ul>
     *     <li><b>EventoSoldOutException:</b> Si el evento ya está agotado.</li>
     *     <li><b>CapacidadInsuficienteException:</b> Si se excede el límite de capacidad para la compra del ticket.</li>
     *     <li><b>EventoNoEncontradoException:</b> Si el evento no existe.</li>
     *     <li><b>RuntimeException:</b> Cualquier otro error no manejado específicamente.</li>
     * </ul>
     *
     * @param ticketDTO El objeto {@code TicketDTO} que contiene la información del ticket a crear.
     * @return Una respuesta que contiene el {@code TicketResponseDTO} del ticket creado o un mensaje de error si hubo fallos.
     *
     * <p><b>Ejemplo de solicitud:</b></p>
     * <pre>
     * - Método: POST
     * - URL: /tickets
     * </pre>
     * <p><b>Cuerpo de la solicitud (JSON):</b></p>
     * <pre>
     * {
     *   "eventoId": 1,
     *   "cantidad": 3,
     *   "usuarioId": 10
     * }
     * </pre>
     *
     * <p><b>Respuestas esperadas:</b></p>
     * <ul>
     *     <li><b>201 CREATED:</b> Si el ticket es creado correctamente.</li>
     *     <li><b>400 BAD REQUEST:</b> Si el evento está agotado, se excede la capacidad o el evento no existe.</li>
     *     <li><b>404 NOT FOUND:</b> En caso de un error no manejado o si ocurre algún problema inesperado.</li>
     * </ul>
     *
     * <p><b>Notas:</b> Asegúrese de que la cantidad solicitada no exceda el límite de capacidad del evento.</p>
     */
    @PostMapping("/tickets")
    public ResponseEntity<?> createTicket(@RequestBody TicketDTO ticketDTO){
        try {
            TicketResponseDTO ticket = ticketService.createTicket(ticketDTO);
            return new ResponseEntity<>(ticket, HttpStatus.CREATED);
        } catch (CustomExceptions.EventoSoldOutException e) {
            return new ResponseEntity<>("SoldOut.", HttpStatus.BAD_REQUEST);
        } catch (CustomExceptions.CapacidadInsuficienteException e) {
            return new ResponseEntity<>("Excede el limite disponible..", HttpStatus.BAD_REQUEST);
        } catch (CustomExceptions.EventoNoEncontradoException e) {
            return new ResponseEntity<>("No existe.", HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Elimina un ticket del sistema según su ID.
     * <p>
     * Este endpoint permite eliminar un ticket existente utilizando su ID. Antes de realizar la eliminación,
     * se verifica si el ticket con el ID especificado existe en el sistema. Si el ticket existe, se elimina
     * y se devuelve una respuesta con estado HTTP 200 (OK). Si no se encuentra el ticket, se devuelve
     * una respuesta con estado HTTP 404 (Not Found).
     * </p>
     *
     * <p><b>Excepciones posibles:</b></p>
     * <ul>
     *     <li><b>404 NOT FOUND:</b> Si el ticket no existe en el sistema.</li>
     * </ul>
     *
     * @param ticketId El ID del ticket que se desea eliminar.
     * @return Una respuesta HTTP:
     *         <ul>
     *             <li><b>200 OK:</b> Si el ticket fue eliminado correctamente.</li>
     *             <li><b>404 NOT FOUND:</b> Si no se encuentra el ticket con el ID especificado.</li>
     *         </ul>
     *
     * <p><b>Ejemplo de solicitud:</b></p>
     * <pre>
     * - Método: DELETE
     * - URL: /delete/{ticketId}
     * </pre>
     * <p><b>Ejemplo de URL:</b> `/delete/1`</p>
     *
     * <p><b>Notas:</b></p>
     * <ul>
     *     <li>Este endpoint es útil para gestionar la eliminación de tickets que ya no son válidos o necesarios.</li>
     *     <li>La existencia del ticket es verificada antes de la eliminación para evitar errores.</li>
     * </ul>
     */
    @DeleteMapping("/delete/{ticketId}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long ticketId){
        if (ticketService.existsById(ticketId)){
            ticketService.deleteTicket(ticketId);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
