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
import co.edu.uniandes.isis2503.nosqljpa.interfaces.ICerraduraLogic;
import co.edu.uniandes.isis2503.nosqljpa.logic.CerraduraLogic;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.CerraduraDTO;
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
@Path("/cerraduras")
@Secured({Role.admin, Role.segPrivada, Role.propietario})
@Produces(MediaType.APPLICATION_JSON)
public class CerraduraService {

    private final ICerraduraLogic cerraduraLogic;

    public CerraduraService() {
        this.cerraduraLogic = new CerraduraLogic();
    }

    @POST
    public CerraduraDTO add(CerraduraDTO dto) {
        return cerraduraLogic.add(dto);
    }

    /**@POST
    @Path("{code}/rooms")
    public RoomDTO addRoom(@PathParam("code") String code, RoomDTO dto) {
        CerraduraDTO cerradura = cerraduraLogic.findCode(code);
        RoomDTO result = roomLogic.add(dto);
        cerradura.addRoom(dto.getId());
        cerraduraLogic.update(cerradura);
        return result;
    }*/

    @PUT
    public CerraduraDTO update(CerraduraDTO dto) {
        return cerraduraLogic.update(dto);
    }

    @GET
    @Path("/{id}")
    public CerraduraDTO find(@PathParam("id") String id) {
        return cerraduraLogic.find(id);
    }

    @GET
    public List<CerraduraDTO> all() {
        return cerraduraLogic.all();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") String id) {
        try {
            cerraduraLogic.delete(id);
            return Response.status(200).header("Access-Control-Allow-Origin", "*").entity("Sucessful: Cerradura was deleted").build();
        } catch (Exception e) {
            Logger.getLogger(CerraduraService.class.getName()).log(Level.WARNING, e.getMessage());
            return Response.status(500).header("Access-Control-Allow-Origin", "*").entity("We found errors in your query, please contact the Web Admin.").build();
        }
    }
}
