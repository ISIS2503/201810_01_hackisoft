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
public class DispositivoDTO{
    private String id;
    private List<AlarmaDTO> alarmas;

    public DispositivoDTO() {
    }

    public DispositivoDTO(String id) {
        this.id = id;
        this.alarmas = new ArrayList<>();
    }
    
    public List<AlarmaDTO> getAlarmas() {
        return alarmas;
    }
    
    public void addAlarma(AlarmaDTO alarma) {
        alarmas.add(alarma);
    }
    
    public AlarmaDTO deleteAlarma(String id){
        for (AlarmaDTO alarma : alarmas) {
            if(alarma.getId().equals(id)){
                alarmas.remove(alarma);
                return alarma;
            }
        }
        return null;
    }
    
    public void setAlarmas(List<AlarmaDTO> alarmas){
        this.alarmas = alarmas;
    }
    
    public String getId() {
        return id;
    }

    
    public void setId(String id) {
        this.id = id;
    }
}
