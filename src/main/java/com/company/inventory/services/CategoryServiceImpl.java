package com.company.inventory.services;

import com.company.inventory.dao.ICategoryDao;
import com.company.inventory.model.Category;
import com.company.inventory.response.CategoryResponseRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class CategoryServiceImpl implements ICategoryService{

    public static final String RESPUESTA_FALLIDA = "Respuesta fallida";
    public static final String RESPUESTA_OK = "Respuesta ok";
    public static final String ERROR = "Error: ";
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
            responseRest.setMetadata(RESPUESTA_OK,"200", "Categorías obtenidas");
        }catch (Exception e){
            responseRest.setMetadata(RESPUESTA_FALLIDA,"500", "Error al consultar categorías");
            logger.severe(ERROR + e.getMessage());
            return new ResponseEntity<>(responseRest, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(responseRest, HttpStatus.OK);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<CategoryResponseRest> searchById(Long id) {
        CategoryResponseRest responseRest = new CategoryResponseRest();
        List<Category> categories = new ArrayList<>();
        Logger logger = Logger.getLogger(getClass().getName());
        try {
            Optional<Category> category = categoryDao.findById(id);
            if(category.isPresent()){
                categories.add(category.get());
                responseRest.getCategoryResponse().setCategories(categories);
                responseRest.setMetadata(RESPUESTA_OK,"200", "Categoría encontrada");
            }else {
                responseRest.setMetadata(RESPUESTA_FALLIDA,"404", "No se encontró categoría con el ID solicitado");
                return new ResponseEntity<>(responseRest, HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            responseRest.setMetadata(RESPUESTA_FALLIDA,"500", "Error al consultar por ID");
            logger.severe(ERROR + e.getMessage());
            return new ResponseEntity<>(responseRest, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(responseRest, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<CategoryResponseRest> save(Category category) {
        CategoryResponseRest responseRest = new CategoryResponseRest();
        List<Category> categories = new ArrayList<>();
        Logger logger = Logger.getLogger(getClass().getName());
        try{
            Category categorySaved = categoryDao.save(category);
            if(categorySaved != null) {
                categories.add(categorySaved);
                responseRest.getCategoryResponse().setCategories(categories);
                responseRest.setMetadata(RESPUESTA_OK,"200", "Categoría guardada");
            }else {
                responseRest.setMetadata(RESPUESTA_FALLIDA,"400", "No se guardó categoría");
                return new ResponseEntity<>(responseRest, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            responseRest.setMetadata(RESPUESTA_FALLIDA,"500", "Error al guardar categoría");
            logger.severe(ERROR + e.getMessage());
            return new ResponseEntity<>(responseRest, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(responseRest, HttpStatus.OK);
    }
}
