package com.javamentor.qa.platform.service.impl.dto;

import com.javamentor.qa.platform.dao.abstracts.pagination.PageDtoDao;
import com.javamentor.qa.platform.exception.NoSuchDaoException;
import com.javamentor.qa.platform.models.dto.PageDTO;
import com.javamentor.qa.platform.models.entity.pagination.PaginationData;
import com.javamentor.qa.platform.exception.PageException;

import java.util.Map;

public abstract class DtoServiceImpl<T> {

    private final Map<String, PageDtoDao<T>> daoMap;

    public DtoServiceImpl(Map<String, PageDtoDao<T>> daoMap) {
        this.daoMap = daoMap;
    }

    public PageDTO<T> getPageDto(PaginationData properties) {
        if (!daoMap.containsKey(properties.getDaoName())) {
            throw new NoSuchDaoException("There is no dao with name: " + properties.getDaoName());
        }
        if (properties.getCurrentPage() < 0) {
            throw new PageException("The number can`t be less than 0");
        }
        if (properties.getItemsOnPage() < 0) {
            throw new PageException("The number can`t be less than 0");
        }
        PageDtoDao<T> currentDao = daoMap.get(properties.getDaoName());
        PageDTO<T> pageDTO = new PageDTO<>();
        pageDTO.setCurrentPageNumber(properties.getCurrentPage());
        pageDTO.setItems(currentDao.getPaginationItems(properties));
        pageDTO.setItemsOnPage(pageDTO.getItems().size());
        pageDTO.setTotalResultCount(currentDao.getTotalResultCount(properties.getProps()));
        pageDTO.setTotalPageCount((int) Math.ceil((double) pageDTO.getTotalResultCount() / properties.getItemsOnPage()));
        return pageDTO;
    }
}
