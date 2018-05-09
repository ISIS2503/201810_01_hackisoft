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
package co.edu.uniandes.isis2503.nosqljpa.model.dto.converter;

import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.BarrioDTO;
import co.edu.uniandes.isis2503.nosqljpa.model.entity.BarrioEntity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author da.cortes11
 */
public class BarrioConverter {
    public static BarrioConverter CONVERTER= new BarrioConverter();
    
    public BarrioDTO entityToDto(BarrioEntity entity)
    {
        BarrioDTO dto= new BarrioDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setUnidadesResidenciales(entity.getUnidadesResidenciales());
        return dto;
    }
    
    public BarrioEntity dtoToEntity(BarrioDTO dto)
    {
        BarrioEntity entity= new BarrioEntity();
        entity.setId(dto.getId());
        entity.setNombre(dto.getNombre());
        entity.setUnidadesResidenciales(dto.getUnidadesResidenciales());
        return entity;
    }
    
   public List<BarrioDTO> listEntitiesToListDTos(List<BarrioEntity> entities)
   {
       ArrayList<BarrioDTO> dtos= new ArrayList();
       for(BarrioEntity entity:entities)
       {
           dtos.add(entityToDto(entity));
       }
       return dtos;
   }
    
    public List<BarrioEntity> listDTOsToListEntities(List<BarrioDTO> dtos)
    {
        ArrayList<BarrioEntity> entities= new ArrayList<>();
        for(BarrioDTO dto:dtos)
        {
            entities.add(dtoToEntity(dto));
        }
        return entities;
    }

}