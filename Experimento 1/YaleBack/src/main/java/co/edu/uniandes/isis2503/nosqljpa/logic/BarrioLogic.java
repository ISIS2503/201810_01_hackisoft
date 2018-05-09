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
package co.edu.uniandes.isis2503.nosqljpa.logic;

import static co.edu.uniandes.isis2503.nosqljpa.model.dto.converter.BarrioConverter.CONVERTER;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.BarrioDTO;
import co.edu.uniandes.isis2503.nosqljpa.persistence.BarrioPersistence;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author da.cortes11
 */
public class BarrioLogic
{
  private final BarrioPersistence persistence;

    public BarrioLogic() {
        this.persistence = new BarrioPersistence();
    }
  
    public BarrioDTO add(BarrioDTO dto)
    {
         if (dto.getId() == null) {
            //dto.setId(UUID.randomUUID().toString());
            dto.setId(UUID.randomUUID().toString());
        }
        BarrioDTO result = CONVERTER.entityToDto(persistence.add(CONVERTER.dtoToEntity(dto)));
        return result;
    }
    
    public BarrioDTO update (BarrioDTO dto)
    {
        BarrioDTO result= CONVERTER.entityToDto(persistence.update(CONVERTER.dtoToEntity(dto)));
        return result;
    }
    
    public BarrioDTO find(String id)
    {
        return CONVERTER.entityToDto(persistence.find(id));
    }

    
    public List<BarrioDTO> all()
    {
        return  CONVERTER.listEntitiesToListDTos(persistence.all());
    }
    
    
    

    public Boolean delete(String id) {
        return persistence.delete(id);
    }
  
  
  
}
