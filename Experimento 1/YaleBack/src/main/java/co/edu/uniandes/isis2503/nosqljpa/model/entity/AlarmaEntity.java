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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author e.galan10
 */
@Entity
@Table(name = "ALARMAS")
public class AlarmaEntity implements Serializable{
    
    @Id
    private String id;
    
    private String message;
    @OneToOne
    private InmuebleEntity inmueble;
    @OneToOne
    private ResidenciaEntity residencia;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecha;

    public AlarmaEntity() {
    }

    public AlarmaEntity(String message, ResidenciaEntity residencia, InmuebleEntity inmueble) {
        this.residencia = residencia;
        this.message = message;
        this.inmueble = inmueble;
    }
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public ResidenciaEntity getResidencia(){
        return residencia;
    }
    
    public void setResidencia(ResidenciaEntity residencia){
        this.residencia = residencia;
    }
    
    public InmuebleEntity getInmueble(){
        return inmueble;
    }
    
    public void setInmueble(InmuebleEntity inmueble){
        this.inmueble = inmueble;
    }
    
    public Date getFecha() {
        return fecha;
    }
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
