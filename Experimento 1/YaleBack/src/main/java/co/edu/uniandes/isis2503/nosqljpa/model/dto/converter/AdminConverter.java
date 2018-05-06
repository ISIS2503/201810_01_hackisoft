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

import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.AdminDTO;
import co.edu.uniandes.isis2503.nosqljpa.model.entity.AdminEntity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author da.cortes11
 */
public class AdminConverter {
    public static AdminConverter CONVERTER= new AdminConverter();

    public AdminConverter() {
    }
    
   public AdminDTO entityToDto(AdminEntity entity) {
        AdminDTO dto=new AdminDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getNombre());
        dto.setConjunto(entity.getConjunto());
        return dto;
    }
    
    public AdminEntity dtoToEntity(AdminDTO dto) {
        AdminEntity entity=new AdminEntity();
        entity.setId(dto.getId());
        entity.setNombre(dto.getName());
        entity.setConjunto(dto.getConjunto());
        return entity;
    }
    
    public List<AdminDTO> listEntitiesToListDTOs(List<AdminEntity> entities) {
        ArrayList<AdminDTO> dtos = new ArrayList<>();
        for (AdminEntity entity : entities) {
            dtos.add(entityToDto(entity));
        }
        return dtos;
    }

    public List<AdminEntity> listDTOsToListEntities(List<AdminDTO> dtos) {
        ArrayList<AdminEntity> entities = new ArrayList<>();
        for (AdminDTO dto : dtos) {
            entities.add(dtoToEntity(dto));
        }
        return entities;
    } 
}
