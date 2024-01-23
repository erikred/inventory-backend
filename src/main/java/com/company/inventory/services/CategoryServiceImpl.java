package com.company.inventory.services;

import com.company.inventory.dao.ICategoryDao;
import com.company.inventory.model.Category;
import com.company.inventory.response.CategoryResponseRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Logger;

@Service
public class CategoryServiceImpl implements ICategoryService{

    private final ICategoryDao categoryDao;
    @Autowired
    public  CategoryServiceImpl(ICategoryDao categoryDao){
        this.categoryDao = categoryDao;
    }
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<CategoryResponseRest> search() {
        CategoryResponseRest responseRest = new CategoryResponseRest();
        Logger logger = Logger.getLogger(getClass().getName());
        try {
            List<Category> categories = (List<Category>) categoryDao.findAll();
            responseRest.getCategoryResponse().setCategories(categories);
            responseRest.setMetadata("Respuesta ok","00", "Respuesta exitosa");
        }catch (Exception e){
            responseRest.setMetadata("Respuesta fail","-1", "Respuesta fallida");
            logger.severe("Error: " + e.getMessage());
            return new ResponseEntity<>(responseRest, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(responseRest, HttpStatus.OK);
    }
}
