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

import co.edu.uniandes.isis2503.nosqljpa.interfaces.IDispositivoConverter;
import co.edu.uniandes.isis2503.nosqljpa.interfaces.IHubConverter;
import co.edu.uniandes.isis2503.nosqljpa.model.dto.model.HubDTO;
import co.edu.uniandes.isis2503.nosqljpa.model.entity.HubEntity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author e.galan10
 */
public class HubConverter implements IHubConverter {

    public static IHubConverter CONVERTER = new HubConverter();
    public static IDispositivoConverter dispositivoConverter = new DispositivoConverter();

    public HubConverter() {
    }

    @Override
    public HubDTO entityToDto(HubEntity entity) {
        HubDTO dto = new HubDTO();
        dto.setId(entity.getId());
        dto.setDispositivos(dispositivoConverter.listEntitiesToListDTOs(entity.getDispositivos()));
        return dto;
    }

    @Override
    public HubEntity dtoToEntity(HubDTO dto) {
        HubEntity entity = new HubEntity();
        entity.setId(dto.getId());
        entity.setDispositivos(dispositivoConverter.listDTOsToListEntities(dto.getDispositivos()));
        return entity;
    }

    @Override
    public List<HubDTO> listEntitiesToListDTOs(List<HubEntity> entities) {
        ArrayList<HubDTO> dtos = new ArrayList<>();
        if (entities != null)
            for (HubEntity entity : entities) {
                dtos.add(entityToDto(entity));
            }
        return dtos;
    }

    @Override
    public List<HubEntity> listDTOsToListEntities(List<HubDTO> dtos) {
        ArrayList<HubEntity> entities = new ArrayList<>();
        if (dtos != null)
            for (HubDTO dto : dtos) {
                entities.add(dtoToEntity(dto));
            }
        return entities;
    }
}
