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
import co.edu.uniandes.isis2503.nosqljpa.auth.AuthenticationFilter;
import co.edu.uniandes.isis2503.nosqljpa.auth.Secured;
import co.edu.uniandes.isis2503.nosqljpa.interfaces.ISeguridadPrivadaLogic;
import co.edu.uniandes.isis2503.nosqljpa.logic.SeguridadPrivadaLogic;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.SeguridadPrivadaDTO;
import co.edu.uniandes.isis2503.nosqljpa.interfaces.IResidenciaLogic;
import co.edu.uniandes.isis2503.nosqljpa.logic.ResidenciaLogic;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.ResidenciaDTO;
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
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author e.galan10
 */
@Path("/seguridadesPrivadas")
//@Secured({Role.yale, Role.segPrivada})
@Produces(MediaType.APPLICATION_JSON)
public class SeguridadPrivadaService {

    private final ISeguridadPrivadaLogic seguridadPrivadaLogic;
    private final IResidenciaLogic residenciaLogic;

    public SeguridadPrivadaService() {
        this.seguridadPrivadaLogic = new SeguridadPrivadaLogic();
        this.residenciaLogic = new ResidenciaLogic();
    }

    @POST
    public SeguridadPrivadaDTO add(SeguridadPrivadaDTO dto) {
        return seguridadPrivadaLogic.add(dto);
    }

    /**@POST
    @Path("{code}/rooms")
    public RoomDTO addRoom(@PathParam("code") String code, RoomDTO dto) {
        SeguridadPrivadaDTO alarma = alarmaLogic.findCode(code);
        RoomDTO result = roomLogic.add(dto);
        alarma.addRoom(dto.getId());
        alarmaLogic.update(alarma);
        return result;
    }*/

    @PUT
    public SeguridadPrivadaDTO update(SeguridadPrivadaDTO dto) {
        return seguridadPrivadaLogic.update(dto);
    }
    
    @POST
    @Path("/residencia")
    //@Secured({Role.segPrivada})
    public ResidenciaDTO addResidencia(ResidenciaDTO dto) {
        SeguridadPrivadaDTO seguridadPrivada = seguridadPrivadaLogic.find(AuthenticationFilter.USER_ID);
        ResidenciaDTO agregado = residenciaLogic.add(dto);
        seguridadPrivada.addResidencia(dto);
        seguridadPrivadaLogic.update(seguridadPrivada);
        return agregado;
    }
    
    @POST
    @Path("/{idSeguridadPrivada}/residencia")
    //@Secured({Role.yale})
    public ResidenciaDTO addResidencia(@PathParam("idSeguridadPrivada") String idSeguridadPrivada, ResidenciaDTO dto) {
        SeguridadPrivadaDTO seguridadPrivada = seguridadPrivadaLogic.find(idSeguridadPrivada);
        ResidenciaDTO agregado = residenciaLogic.add(dto);
        seguridadPrivada.addResidencia(dto);
        seguridadPrivadaLogic.update(seguridadPrivada);
        return agregado;
    }

    @GET
    @Path("/{id}")
    public SeguridadPrivadaDTO find(@PathParam("id") String id) {
        return seguridadPrivadaLogic.find(id);
    }

    @GET
    public List<SeguridadPrivadaDTO> all() {
        return seguridadPrivadaLogic.all();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") String id) {
        try {
            seguridadPrivadaLogic.delete(id);
            return Response.status(200).header("Access-Control-Allow-Origin", "*").entity("Sucessful: SeguridadPrivada was deleted").build();
        } catch (Exception e) {
            Logger.getLogger(SeguridadPrivadaService.class.getName()).log(Level.WARNING, e.getMessage());
            return Response.status(500).header("Access-Control-Allow-Origin", "*").entity("We found errors in your query, please contact the Web Admin.").build();
        }
    }
}
