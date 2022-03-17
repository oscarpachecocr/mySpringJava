package com.ina.sysVentas.dao;

import com.ina.sysVentas.domain.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface IProductoDao extends JpaRepository<Producto, Long>{
    
    public Iterable<Producto> findByDescripcionContains(String descripcion);
    
    @Transactional
    @Procedure(procedureName = "ELIMINAR_PRODUCTO",outputParameterName = "res")
    public Integer eliminar_Producto(@Param("ID") long id);
}
