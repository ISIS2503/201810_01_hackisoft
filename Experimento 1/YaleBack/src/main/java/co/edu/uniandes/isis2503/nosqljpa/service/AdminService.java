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

import co.edu.uniandes.isis2503.nosqljpa.auth.Secured;
import co.edu.uniandes.isis2503.nosqljpa.logic.AdminLogic;
import co.edu.uniandes.isis2503.nosqljpa.logic.UnidadResidencialLogic;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.AdminDTO;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.UnidadResidencialDTO;
import com.sun.istack.logging.Logger;
import java.util.List;
import java.util.logging.Level;
import javax.management.relation.Role;
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
 * @author da.cortes11
 */
@Path("/administradores")
@Secured
@Produces(MediaType.APPLICATION_JSON)


public class AdminService {
    private final AdminLogic adminlogic;
    private final UnidadResidencialLogic residenciaLogic;

    public AdminService() {
        this.adminlogic = new AdminLogic();
        this.residenciaLogic = new UnidadResidencialLogic();
    }
    
    @POST
    public AdminDTO add(AdminDTO dto) 
    {
        return adminlogic.add(dto);
    }
    
    @POST
    @Path("{id}/unidadresidencial")
    public UnidadResidencialDTO addUnidad(@PathParam("id")String id, UnidadResidencialDTO dto)
    {
        AdminDTO admin= adminlogic.find(id);
        UnidadResidencialDTO result= residenciaLogic.add(dto);
        admin.setConjunto(dto.getId());
        adminlogic.update(admin);
        return result;
    }
    
    @PUT
    public AdminDTO update(AdminDTO dto)
    {
        return adminlogic.update(dto);
    }
    
    @GET
    @Path("/{id}")
    public AdminDTO find (@PathParam("id")String id)
    {
       return adminlogic.find(id);
    }
    @GET
    public List<AdminDTO> all()
    {
        return adminlogic.all();
    }
    
    @DELETE
    //@Secured({Role.yale})
    @Path("/{id}")
    
    public Response delete(@PathParam("id")String id)
    {
        try{
            adminlogic.delete(id);
            return Response.status(200).header("Acces-Control_Allow-Origin", "*").entity("Successsful:Floor was deleted").build();
        }
        catch(Exception e)
                {
                    Logger.getLogger(AdminService.class).log(Level.WARNING, e.getMessage());
                    return Response.status(500).header("Acces-Control-Allow-Origin","*").entity("we found errors in our query, pleasecontact the Web admin.").build();
                }
    }
}
