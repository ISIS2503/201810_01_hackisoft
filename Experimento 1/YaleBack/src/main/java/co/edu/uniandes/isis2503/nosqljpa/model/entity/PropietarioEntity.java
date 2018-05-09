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
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author da.cortes11
 */
@Entity
public class PropietarioEntity implements Serializable{
    
    @Id
    private String id;
    
    private String nombre;
    
    private String correo;
    
    private int telefono;
    
    private String login;
    
    private String contrasena;

    public PropietarioEntity() {
    }

    public PropietarioEntity(String id, String nombre, String correo, int telefono, String login, String contrasena) {
        this.id = id;
        this.nombre = nombre;
        this.correo= correo;
        this.telefono= telefono;
        this.login=login;
        this.contrasena=contrasena;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getCorreo()
    {
      return correo;   
    } 
    
    public void setCorreo()
    {
        this.correo=correo;
    }
    
    public String getTelefono()
    {
     return telefono;   
    }
    
    public void setTelefono()
    {
        this.telefono=telefono
    }
    
    public String getLogin()
    {
        return login;
    }
    
    public void setLogin()
    {
        this.login=login;
    }
    public String getContrasena()
    {
        return contrasena;
    }
    public setContrasena()
    {
     this.contrasena=contrasena;   
    }
    
}
