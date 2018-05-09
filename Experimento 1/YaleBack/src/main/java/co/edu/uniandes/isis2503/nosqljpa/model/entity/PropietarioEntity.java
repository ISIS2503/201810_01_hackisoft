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
package co.edu.uniandes.isis2503.nosqljpa.model.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.*;
import javax.persistence.ElementCollection;
import javax.persistence.OneToMany;

/**
 *
 * @author e.galan10
 */
@Entity
@Table(name = "PROPIETARIOS")
public class PropietarioEntity implements Serializable{

    @Id
    private String id;
    
    @OneToMany
    private List<InmuebleEntity> inmuebles;
    private String email;
    private String password;
    private String phone;

    public PropietarioEntity() {
    }

    public PropietarioEntity(String id, String email, String password, String phone) {
        this.id = id;
        inmuebles = new ArrayList<>();
        this.email = email;
        this.password = password;
        this.phone = phone;
    }
    
    public List<InmuebleEntity> getInmuebles() {
        return inmuebles;
    }

    public void setInmuebles(List<InmuebleEntity> inmuebles) {
        this.inmuebles = inmuebles;
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
    
    public void addInmueble(InmuebleEntity inmueble) {
        inmuebles.add(inmueble);
    }
    
    public InmuebleEntity deleteInmueble(String id) {
        for (InmuebleEntity inmueble : inmuebles) {
            if(inmueble.getId().equals(id)){
                inmuebles.remove(inmueble);
                return inmueble;
            }
        }
        return null;
    }
}
