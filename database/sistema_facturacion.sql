CREATE DATABASE facturacion;
USE facturacion;

CREATE TABLE proveedor(
  id CHAR(30) NOT NULL ,
  nombre CHAR(30) NOT NULL,
  direccion CHAR(50) NOT NULL,
  PRIMARY KEY (id)
)ENGINE = InnoDB;

CREATE TABLE producto(
  id CHAR(40) NOT NULL UNIQUE,
  nombre CHAR(60) NOT NULL,
  marca CHAR(30),
  imagen LONGBLOB,
  stock INTEGER UNSIGNED NOT NULL,
  precio_venta DECIMAL(5,2) NOT NULL,
  precio_inicial DECIMAL(5,2) NOT NULL,
  PRIMARY KEY (id)
)ENGINE = InnoDB;

CREATE TABLE grupos(
  id CHAR (30) NOT NULL UNIQUE,
  nombre CHAR (30) NOT NULL,
  descripcion CHAR (200) NOT NULL,
  PRIMARY KEY (id)
)ENGINE = InnoDB;

CREATE TABLE Cliente(
  Cedula_C VARCHAR(15) NOT NULL UNIQUE,
  Fecha_C DATE,
  Nombre_C VARCHAR(30) NOT NULL,
  Apellido_C VARCHAR(30) NOT NULL,
  Direccion_C VARCHAR(30) NOT NULL,
  Celular_C VARCHAR(30),
  Convencional_C VARCHAR(30),
  Email_C VARCHAR(30),
  PRIMARY KEY (Cedula_C)
)ENGINE = InnoDB;

CREATE TABLE empleado(
  cedula CHAR(30) NOT NULL UNIQUE,
  nombre CHAR(30) NOT NULL,
  horario_ent CHAR(30) NOT NULL,
  horario_sal CHAR(30) NOT NULL,
  es_admin INTEGER NOT NULL,
  sueldo  DECIMAL(5,2) NOT NULL,
  PRIMARY KEY (cedula)
)ENGINE = InnoDB;

CREATE TABLE item(
  id INTEGER AUTO_INCREMENT NOT NULL UNIQUE,
  precio  DECIMAL(5,2)  NOT NULL,
  nombre CHAR(30) NOT NULL,
  descripcion CHAR(200),
  fecha DATE NOT NULL,
  PRIMARY KEY (id)
)ENGINE = InnoDB;

CREATE TABLE factura(
  id CHAR (30) NOT NULL UNIQUE,
  tipo CHAR (30) NOT NULL,
  valor  DECIMAL(5,2) NOT NULL,
  fecha DATE NOT NULL,
  cedula_c CHAR(30) NOT NULL,
  cedula_empl CHAR(30) NOT NULL,
  anulada INTEGER NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT FOREIGN KEY (cedula_c) REFERENCES Cliente(Cedula_C),
  CONSTRAINT FOREIGN KEY (cedula_empl) REFERENCES empleado(cedula)
)ENGINE = InnoDB;

CREATE TABLE proveedor_producto(
  id CHAR(30) NOT NULL,
  id_proveedor CHAR(30) NOT NULL,
  id_producto CHAR(40) NOT NULL,
  cantidad INTEGER NOT NULL,
  precio FLOAT UNSIGNED NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (id_proveedor) REFERENCES proveedor(id),
  FOREIGN KEY (id_producto) REFERENCES producto(id)
)ENGINE = InnoDB;


CREATE TABLE producto_grupos(
  id_perteneceA CHAR(30) NOT NULL,
  id_producto CHAR(40) NOT NULL,
  id_grupo CHAR(30) NOT NULL,
  PRIMARY KEY (id_perteneceA),
  CONSTRAINT FOREIGN KEY (id_producto) REFERENCES producto(id),
  CONSTRAINT FOREIGN KEY (id_grupo) REFERENCES grupos(id)
)ENGINE = InnoDB;

CREATE TABLE producto_factura(
  id CHAR (30) NOT NULL,
  id_producto CHAR(40) NOT NULL,
  id_orden CHAR(30) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT FOREIGN KEY (id_producto) REFERENCES producto(id),
  CONSTRAINT FOREIGN KEY (id_orden) REFERENCES factura(id)
)ENGINE = InnoDB;

CREATE TABLE telefonos_cliente(
  cedula_c CHAR(30) NOT NULL,
  telefono CHAR(30) NOT NULL,
  CONSTRAINT FOREIGN KEY (cedula_c) REFERENCES Cliente(Cedula_C)
)ENGINE = InnoDB;

CREATE TABLE item_empleado(
  id INTEGER AUTO_INCREMENT NOT NULL UNIQUE,
  id_item INTEGER NOT NULL,
  cedula_empl CHAR(30) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT FOREIGN KEY (id_item) REFERENCES item (id),
  CONSTRAINT FOREIGN KEY (cedula_empl) REFERENCES empleado (cedula)
)ENGINE = InnoDB;

CREATE TABLE telefono_proveedor(
  id_proveedor CHAR(30) NOT NULL,
  telefono CHAR(30) NOT NULL,
  CONSTRAINT FOREIGN KEY (id_proveedor) REFERENCES proveedor (id)
)ENGINE = InnoDB;
