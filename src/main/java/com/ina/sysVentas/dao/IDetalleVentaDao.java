package com.ina.sysVentas.dao;

import com.ina.sysVentas.domain.DetalleVenta;
import com.ina.sysVentas.domain.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDetalleVentaDao extends JpaRepository<DetalleVenta, Long>{
    public Iterable<DetalleVenta> findByVenta(Venta venta);
}
