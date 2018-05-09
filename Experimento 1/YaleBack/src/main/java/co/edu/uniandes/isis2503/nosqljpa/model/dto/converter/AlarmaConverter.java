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

import co.edu.uniandes.isis2503.nosqljpa.interfaces.IAlarmaConverter;
import co.edu.uniandes.isis2503.nosqljpa.interfaces.IResidenciaConverter;
import co.edu.uniandes.isis2503.nosqljpa.interfaces.IInmuebleConverter;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.AlarmaDTO;
import co.edu.uniandes.isis2503.nosqljpa.model.entity.AlarmaEntity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author e.galan10
 */
public class AlarmaConverter implements IAlarmaConverter {

    public static IAlarmaConverter CONVERTER = new AlarmaConverter();
    public static IResidenciaConverter residenciaCONVERTER = new ResidenciaConverter();
    public static IInmuebleConverter inmuebleCONVERTER = new InmuebleConverter();

    public AlarmaConverter() {
    }

    @Override
    public AlarmaDTO entityToDto(AlarmaEntity entity) {
        AlarmaDTO dto = new AlarmaDTO();
        dto.setId(entity.getId());
        dto.setFecha(entity.getFecha());
        dto.setResidencia(residenciaCONVERTER.entityToDto(entity.getResidencia()));
        dto.setInmueble(inmuebleCONVERTER.entityToDto(entity.getInmueble()));
        dto.setMessage(entity.getMessage());
        return dto;
    }

    @Override
    public AlarmaEntity dtoToEntity(AlarmaDTO dto) {
        AlarmaEntity entity = new AlarmaEntity();
        entity.setId(dto.getId());
        entity.setFecha(dto.getFecha());
        entity.setResidencia(residenciaCONVERTER.dtoToEntity(dto.getResidencia()));
        entity.setInmueble(inmuebleCONVERTER.dtoToEntity(dto.getInmueble()));
        entity.setMessage(dto.getMessage());
        return entity;
    }

    @Override
    public List<AlarmaDTO> listEntitiesToListDTOs(List<AlarmaEntity> entities) {
        ArrayList<AlarmaDTO> dtos = new ArrayList<>();
        if (entities != null)
            for (AlarmaEntity entity : entities) {
                dtos.add(entityToDto(entity));
            }
        return dtos;
    }

    @Override
    public List<AlarmaEntity> listDTOsToListEntities(List<AlarmaDTO> dtos) {
        ArrayList<AlarmaEntity> entities = new ArrayList<>();
        if (dtos != null)
            for (AlarmaDTO dto : dtos) {
                entities.add(dtoToEntity(dto));
            }
        return entities;
    }
}
