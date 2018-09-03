package com.ecommerce.microcommerce.web.controller;

import com.ecommerce.microcommerce.dao.ProductDao;
import com.ecommerce.microcommerce.model.Product;
import com.ecommerce.microcommerce.web.exceptions.ProduitGratuitException;
import com.ecommerce.microcommerce.web.exceptions.ProduitIntrouvableException;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Api(description="API pour des opérations CRUD sur les produits.")
@RestController
public class ProductController {

    @Autowired
    private ProductDao productDao;

//Récupérer la liste des produits
    /*@RequestMapping(value="/Produits", method=RequestMethod.GET)
    public MappingJacksonValue listeProduits(){

        List<Product> produits = productDao.findAll();
        SimpleBeanPropertyFilter monFiltre = SimpleBeanPropertyFilter.serializeAllExcept("prixAchat");
        FilterProvider listDeNosFiltres = new SimpleFilterProvider().addFilter("monFiltreDynamique", monFiltre);
        MappingJacksonValue produitsFiltres = new MappingJacksonValue(produits);

        produitsFiltres.setFilters(listDeNosFiltres);

        return produitsFiltres;
    }*/

//Récupérer un produit par son Id
    @ApiOperation(value="Récupère un produit grâce a son ID à condition que celui-ci soit en stock!")
    @GetMapping(value="/Produits/{id}")
    public Product afficherUnProduit(@PathVariable int id){
        Product produit = productDao.findById(id);

        if(produit == null) throw new ProduitIntrouvableException("Le produit avec l'id " + id + " est INTROUVABLE!!");

        return produit;
    }

    @GetMapping(value="test/produits/{recherche}")
        public List<Product> testeDeRequetes(@PathVariable String recherche){
            return productDao.findByNomLike("%"+recherche+"%");
    }
//Calculer la marge d'un produit
    @GetMapping(value="/AdminProduits")
    public List<String> calculerMargeProduit(){
        List<String> margeList = new ArrayList<>();
        List <Product> productMarge = productDao.findAll();

        for (Product p : productMarge){
            int prixMarge = (p.getPrix() - p.getPrixAchat());
            margeList.add(p.toString() + ", marge : " +prixMarge);
        }
        return margeList;
    }
//Tri par ordre alphabétique
    @GetMapping(value="/ProduitsTrie")
        public List<Product> trierProduitsParOrdreAlphabetique(){
        List<Product> pTrie = productDao.OrderByNomAsc();
        return pTrie;
    }
//Ajouter un produit
    @PostMapping(value="/Produits")
    public ResponseEntity<Void> ajouterProduit(@Valid @RequestBody Product product){
        List<Product> prod = productDao.findAll();
        Product productAdded = productDao.save(product);

        if (productAdded.getPrix() == 0) {
            throw new ProduitGratuitException("Le prix doit être DIFFERENT de 0!!");
        }
        if(productAdded == null )
            return ResponseEntity.noContent().build();

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productAdded.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping(value="/Produits")
        public void updateProduit(@RequestBody Product product){
            productDao.save(product);
    }
}
