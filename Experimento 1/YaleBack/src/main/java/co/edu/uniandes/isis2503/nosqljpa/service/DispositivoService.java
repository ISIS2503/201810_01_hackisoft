/*
 * The MIT License
 *
 * Copyright 2018 Universidad De Los Andes - Departamento de Ingeniería de Sistemas.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package co.edu.uniandes.isis2503.nosqljpa.service;

import co.edu.uniandes.isis2503.nosqljpa.auth.AuthorizationFilter.Role;
import co.edu.uniandes.isis2503.nosqljpa.auth.Secured;
import co.edu.uniandes.isis2503.nosqljpa.logic.DispositivoLogic;
import co.edu.uniandes.isis2503.nosqljpa.logic.AlarmaLogic;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.DispositivoDTO;
import java.util.logging.Logger;
import java.util.List;
import java.util.logging.Level;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import co.edu.uniandes.isis2503.nosqljpa.interfaces.IDispositivoLogic;
import co.edu.uniandes.isis2503.nosqljpa.interfaces.IAlarmaLogic;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.AlarmaDTO;

/**
 *
 * @author e.galan10
 */
@Path("/dispositivos")
//@Secured({Role.propietario, Role.yale})
@Produces(MediaType.APPLICATION_JSON)
public class DispositivoService {

    private final IDispositivoLogic dispositivoLogic;
    private final IAlarmaLogic alarmaLogic;

    public DispositivoService() {
        this.dispositivoLogic = new DispositivoLogic();
        this.alarmaLogic = new AlarmaLogic();
    }

    @POST
    public DispositivoDTO add(DispositivoDTO dto) {
        return dispositivoLogic.add(dto);
    }

    /** * @POST
    @Path("{code}/rooms")
    public RoomDTO addRoom(@PathParam("code") String code, RoomDTO dto) {
        DispositivoDTO dispositivo = dispositivoLogic.findCode(code);
        RoomDTO result = roomLogic.add(dto);
        dispositivo.addRoom(dto.getId());
        dispositivoLogic.update(dispositivo);
        return result;
    }*/

    @PUT
    public DispositivoDTO update(DispositivoDTO dto) {
        return dispositivoLogic.update(dto);
    }
    
    @POST
    @Path("/{idDispositivo}/alarma")
    //@Secured({Role.yale})
    public AlarmaDTO addAlarma(@PathParam("idDispositivo") String idDispositivo, AlarmaDTO dto) {
        DispositivoDTO dispositivo = dispositivoLogic.find(idDispositivo);
        AlarmaDTO agregado = alarmaLogic.add(dto);
        dispositivo.addAlarma(dto);
        dispositivoLogic.update(dispositivo);
        return agregado;
    }

    @GET
    @Path("/{id}")
    public DispositivoDTO find(@PathParam("id") String id) {
        return dispositivoLogic.find(id);
    }
    
    @GET
    @Path("/alarmas/{id}")
    public List<AlarmaDTO> allAlarmas(@PathParam("id") String id) {
        return dispositivoLogic.find(id).getAlarmas();
    }

    @GET
    //@Secured({Role.yale})
    public List<DispositivoDTO> all() {
        return dispositivoLogic.all();
    }

    @DELETE
    @Path("/{id}")
    //@Secured({Role.yale})
    public Response delete(@PathParam("id") String id) {
        try {
            dispositivoLogic.delete(id);
            return Response.status(200).header("Access-Control-Allow-Origin", "*").entity("Sucessful: Dispositivo was deleted").build();
        } catch (Exception e) {
            Logger.getLogger(DispositivoService.class.getName()).log(Level.WARNING, e.getMessage());
            return Response.status(500).header("Access-Control-Allow-Origin", "*").entity("We found errors in your query, please contact the Web Admin.").build();
        }
    }
}
