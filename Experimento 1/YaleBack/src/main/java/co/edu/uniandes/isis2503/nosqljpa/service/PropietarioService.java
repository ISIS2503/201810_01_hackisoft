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
import co.edu.uniandes.isis2503.nosqljpa.interfaces.IPropietarioLogic;
import co.edu.uniandes.isis2503.nosqljpa.logic.PropietarioLogic;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.PropietarioDTO;
import co.edu.uniandes.isis2503.nosqljpa.interfaces.IInmuebleLogic;
import co.edu.uniandes.isis2503.nosqljpa.logic.InmuebleLogic;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.InmuebleDTO;
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

/**
 *
 * @author e.galan10
 */
@Path("/propietarios")
//@Secured({Role.yale, Role.propietario})
@Produces(MediaType.APPLICATION_JSON)
public class PropietarioService {

    private final IPropietarioLogic propietarioLogic;
    private final IInmuebleLogic inmuebleLogic;

    public PropietarioService() {
        this.propietarioLogic = new PropietarioLogic();
        this.inmuebleLogic = new InmuebleLogic();
    }

    @POST
    public PropietarioDTO add(PropietarioDTO dto) {
        return propietarioLogic.add(dto);
    }

    /**@POST
    @Path("{code}/rooms")
    public RoomDTO addRoom(@PathParam("code") String code, RoomDTO dto) {
        PropietarioDTO propietario = propietarioLogic.findCode(code);
        RoomDTO result = roomLogic.add(dto);
        propietario.addRoom(dto.getId());
        propietarioLogic.update(propietario);
        return result;
    }*/

    @PUT
    public PropietarioDTO update(PropietarioDTO dto) {
        return propietarioLogic.update(dto);
    }
    
    @POST
    @Path("/inmueble")
    //@Secured({Role.propietario})
    public InmuebleDTO addInmueble(InmuebleDTO dto) {
        PropietarioDTO propietario = propietarioLogic.find(AuthenticationFilter.USER_ID);
        InmuebleDTO agregado = inmuebleLogic.add(dto);
        propietario.addInmueble(dto);
        propietarioLogic.update(propietario);
        return agregado;
    }
    
    @POST
    @Path("/{idPropietario}/inmueble")
    //@Secured({Role.yale})
    public InmuebleDTO addInmueble(@PathParam("idPropietario") String idPropietario, InmuebleDTO dto) {
        PropietarioDTO propietario = propietarioLogic.find(idPropietario);
        InmuebleDTO agregado = inmuebleLogic.add(dto);
        propietario.addInmueble(dto);
        propietarioLogic.update(propietario);
        return agregado;
    }

    @GET
    @Path("/{id}")
    public PropietarioDTO find(@PathParam("id") String id) {
        return propietarioLogic.find(id);
    }

    @GET
    public List<PropietarioDTO> all() {
        return propietarioLogic.all();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") String id) {
        try {
            propietarioLogic.delete(id);
            return Response.status(200).header("Access-Control-Allow-Origin", "*").entity("Sucessful: Propietario was deleted").build();
        } catch (Exception e) {
            Logger.getLogger(PropietarioService.class.getName()).log(Level.WARNING, e.getMessage());
            return Response.status(500).header("Access-Control-Allow-Origin", "*").entity("We found errors in your query, please contact the Web Admin.").build();
        }
    }
}
