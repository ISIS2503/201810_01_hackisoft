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
package co.edu.uniandes.isis2503.nosqljpa.model.dto.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;

/**
 *
 * @author e.galan10
 */
@XmlRootElement
public class AdminDTO {
    private String id;
    private List<ResidenciaDTO> residencias;
    private String email;
    private String password;
    private String phone;

    public AdminDTO() {
    }

    public AdminDTO(String id, String email, String password, String phone) {
        this.id = id;
        this.residencias = new ArrayList<>();
        this.email = email;
        this.password = password;
        this.phone = phone;

    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public void setResidencias(List<ResidenciaDTO> residencias) {
        this.residencias = residencias;
    }
    
    public List<ResidenciaDTO> getResidencias() {
        return residencias;
    }
    
    public void addResidencia(ResidenciaDTO residencia) {
        residencias.add(residencia);
    }
    
    public ResidenciaDTO deleteResidencia(String id) {
        for (ResidenciaDTO residencia : residencias) {
            if(residencia.getId().equals(id)){
                residencias.remove(residencia);
                return residencia;
            }
        }
        return null;
    }
}
