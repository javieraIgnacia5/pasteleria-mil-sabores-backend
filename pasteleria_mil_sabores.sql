
-- BASE DE DATOS PASTELERÍA MIL SABORES

CREATE DATABASE IF NOT EXISTS pasteleria_mil_sabores;

USE pasteleria_mil_sabores;

-- TABLA USUARIO
CREATE TABLE IF NOT EXISTS usuario (
    id_usuario BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(150) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    rol VARCHAR(20) NOT NULL
);

-- TABLA CATEGORIA
CREATE TABLE IF NOT EXISTS categoria (
    id_categoria BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

-- TABLA PRODUCTO
CREATE TABLE IF NOT EXISTS producto (
    id_producto BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL,
    imagen VARCHAR(255),
    id_categoria BIGINT NOT NULL,
    FOREIGN KEY (id_categoria)
        REFERENCES categoria(id_categoria)
);

-- TABLA BOLETA
CREATE TABLE IF NOT EXISTS boleta (
    id_boleta BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha DATETIME NOT NULL,
    total DECIMAL(12,2) NOT NULL,
    id_usuario BIGINT NOT NULL,
    FOREIGN KEY (id_usuario)
        REFERENCES usuario(id_usuario)
);

-- TABLA DETALLE BOLETA
CREATE TABLE IF NOT EXISTS detalle_boleta (
    id_detalle BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_boleta BIGINT NOT NULL,
    id_producto BIGINT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(12,2) NOT NULL,
    FOREIGN KEY (id_boleta)
        REFERENCES boleta(id_boleta),
    FOREIGN KEY (id_producto)
        REFERENCES producto(id_producto)
);

-- CATEGORIAS DE PRUEBA
INSERT INTO categoria (nombre)
SELECT 'Tortas'
WHERE NOT EXISTS (
    SELECT 1 FROM categoria WHERE nombre = 'Tortas'
);

INSERT INTO categoria (nombre)
SELECT 'Galletas'
WHERE NOT EXISTS (
    SELECT 1 FROM categoria WHERE nombre = 'Galletas'
);

INSERT INTO categoria (nombre)
SELECT 'Postres'
WHERE NOT EXISTS (
    SELECT 1 FROM categoria WHERE nombre = 'Postres'
);

-- PRODUCTOS DE PRUEBA
INSERT INTO producto
(nombre, descripcion, precio, stock, imagen, id_categoria)
SELECT
    'Torta de Chocolate',
    'Torta artesanal',
    15000,
    10,
    'torta.jpg',
    id_categoria
FROM categoria
WHERE nombre = 'Tortas'
AND NOT EXISTS (
    SELECT 1 FROM producto
    WHERE nombre = 'Torta de Chocolate'
)
LIMIT 1;

INSERT INTO producto
(nombre, descripcion, precio, stock, imagen, id_categoria)
SELECT
    'Galletas de Avena',
    'Galletas artesanales',
    3500,
    20,
    'galletas.jpg',
    id_categoria
FROM categoria
WHERE nombre = 'Galletas'
AND NOT EXISTS (
    SELECT 1 FROM producto
    WHERE nombre = 'Galletas de Avena'
)
LIMIT 1;

INSERT INTO producto
(nombre, descripcion, precio, stock, imagen, id_categoria)
SELECT
    'Brownie de Chocolate',
    'Brownie artesanal',
    4500,
    15,
    'brownie.jpg',
    id_categoria
FROM categoria
WHERE nombre = 'Postres'
AND NOT EXISTS (
    SELECT 1 FROM producto
    WHERE nombre = 'Brownie de Chocolate'
)
LIMIT 1;

-- CONSULTAS DE COMPROBACION
SHOW TABLES;

SELECT * FROM categoria;
SELECT * FROM producto;
