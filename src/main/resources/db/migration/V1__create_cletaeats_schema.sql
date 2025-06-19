-- Crear tabla clientes
CREATE TABLE IF NOT EXISTS clientes
(
    id             VARCHAR(36) PRIMARY KEY,
    cedula         VARCHAR(9)   NOT NULL,
    nombre         VARCHAR(100) NOT NULL,
    direccion      VARCHAR(200) NOT NULL,
    telefono       VARCHAR(15)  NOT NULL,
    correo         VARCHAR(100) NOT NULL,
    estado         VARCHAR(20)  NOT NULL,
    contrasena     VARCHAR(100) NOT NULL,
    numero_tarjeta VARCHAR(16)  NOT NULL
);

-- Insertar datos de prueba para clientes
INSERT INTO clientes (id, cedula, nombre, direccion, telefono, correo, estado, contrasena, numero_tarjeta)
VALUES ('1', '123456789', 'Ana López', 'San José, Costa Rica', '88888888', 'ana@example.com', 'activo', 'password1234',
        '1234567812345678'),
       ('2', '987654321', 'Carlos Ramírez', 'Heredia, Costa Rica', '77777777', 'carlos@example.com', 'suspendido',
        'password1234', '8765432187654321'),
       ('3', '555666777', 'Laura Fernández', 'Alajuela, Costa Rica', '66666666', 'laura@example.com', 'activo',
        'password1234', '1111222233334444');

-- Crear tabla repartidores
CREATE TABLE IF NOT EXISTS repartidores
(
    id                    VARCHAR(36) PRIMARY KEY,
    cedula                VARCHAR(9)       NOT NULL,
    nombre                VARCHAR(100)     NOT NULL,
    direccion             VARCHAR(200)     NOT NULL,
    telefono              VARCHAR(15)      NOT NULL,
    correo                VARCHAR(100)     NOT NULL,
    estado                VARCHAR(20)      NOT NULL,
    km_recorridos_diarios DOUBLE PRECISION NOT NULL,
    costo_por_km_habiles  DOUBLE PRECISION NOT NULL,
    costo_por_km_feriados DOUBLE PRECISION NOT NULL,
    amonestaciones        INT              NOT NULL,
    contrasena            VARCHAR(100)     NOT NULL,
    numero_tarjeta        VARCHAR(16)
);

-- Insertar datos de prueba para repartidores
INSERT INTO repartidores (id, cedula, nombre, direccion, telefono, correo, estado, km_recorridos_diarios,
                          costo_por_km_habiles, costo_por_km_feriados, amonestaciones, contrasena, numero_tarjeta)
VALUES ('1', '111222333', 'Juan Pérez', 'Heredia, Costa Rica', '88888888', 'juan@example.com', 'disponible', 0.0,
        1000.0, 1500.0, 0, 'password1234', '1234567812345678'),
       ('2', '444555666', 'María Gómez', 'Alajuela, Costa Rica', '77777777', 'maria@example.com', 'disponible', 0.0,
        1000.0, 1500.0, 1, 'password1234', NULL),
       ('3', '777888999', 'Pedro Sánchez', 'San José, Costa Rica', '66666666', 'pedro@example.com', 'inactivo', 0.0,
        1000.0, 1500.0, 4, 'password1234', NULL);

-- Crear tabla restaurantes
CREATE TABLE IF NOT EXISTS restaurantes
(
    id              VARCHAR(36) PRIMARY KEY,
    cedula_juridica VARCHAR(10)  NOT NULL,
    nombre          VARCHAR(100) NOT NULL,
    direccion       VARCHAR(200) NOT NULL,
    tipo_comida     VARCHAR(50)  NOT NULL,
    contrasena      VARCHAR(100) NOT NULL
);

-- Crear tabla combos (relación con restaurantes)
CREATE TABLE IF NOT EXISTS restaurante_combos
(
    restaurante_id VARCHAR(36) REFERENCES restaurantes (id),
    numero         INT              NOT NULL,
    nombre         VARCHAR(100)     NOT NULL,
    precio         DOUBLE PRECISION NOT NULL,
    PRIMARY KEY (restaurante_id, numero)
);

-- Insertar datos de prueba para restaurantes y sus combos
INSERT INTO restaurantes (id, cedula_juridica, nombre, direccion, tipo_comida, contrasena)
VALUES ('1', '3001234567', 'La Pizzeria', 'San José, Costa Rica', 'Italiana', 'password1234'),
       ('2', '3009876543', 'Taco Loco', 'Heredia, Costa Rica', 'Mexicana', 'password1234'),
       ('3', '3001112223', 'Sushi House', 'Alajuela, Costa Rica', 'Japonesa', 'password1234');

INSERT INTO restaurante_combos (restaurante_id, numero, nombre, precio)
VALUES ('1', 1, 'Pizza Margherita', 4000.0),
       ('1', 2, 'Pizza Pepperoni', 5000.0),
       ('1', 3, 'Pizza Suprema', 6000.0),
       ('2', 1, 'Taco al Pastor', 4000.0),
       ('2', 2, 'Burrito Grande', 5000.0),
       ('2', 3, 'Quesadilla Especial', 6000.0),
       ('3', 1, 'Sushi Básico', 4000.0),
       ('3', 2, 'Sushi Combinado', 5000.0),
       ('3', 3, 'Sushi Premium', 6000.0),
       ('3', 4, 'Sushi Deluxe', 7000.0);

-- Crear tabla pedidos
CREATE TABLE IF NOT EXISTS pedidos
(
    id                 VARCHAR(36) PRIMARY KEY,
    cliente_id         VARCHAR(36) REFERENCES clientes (id),
    restaurante_id     VARCHAR(36) REFERENCES restaurantes (id),
    repartidor_id      VARCHAR(36) REFERENCES repartidores (id),
    precio             DOUBLE PRECISION NOT NULL,
    distancia          DOUBLE PRECISION NOT NULL,
    costo_transporte   DOUBLE PRECISION NOT NULL,
    iva                DOUBLE PRECISION NOT NULL,
    total              DOUBLE PRECISION NOT NULL,
    estado             VARCHAR(20)      NOT NULL,
    hora_realizado     VARCHAR(19)      NOT NULL,
    hora_entregado     VARCHAR(19),
    nombre_restaurante VARCHAR(100)
);

-- Crear tabla pedido_combos (relación con pedidos)
CREATE TABLE IF NOT EXISTS pedido_combos
(
    pedido_id VARCHAR(36) REFERENCES pedidos (id),
    numero    INT              NOT NULL,
    nombre    VARCHAR(100)     NOT NULL,
    precio    DOUBLE PRECISION NOT NULL,
    PRIMARY KEY (pedido_id, numero)
);

-- Insertar datos de prueba para pedidos y sus combos
INSERT INTO pedidos (id, cliente_id, restaurante_id, repartidor_id, precio, distancia, costo_transporte, iva, total,
                     estado, hora_realizado, hora_entregado, nombre_restaurante)
VALUES ('1', '1', '1', '1', 9000.0, 5.0, 5000.0, 1170.0, 15170.0, 'en preparación', '2025-06-18 12:00:00', NULL,
        'La Pizzeria'),
       ('2', '2', '2', '2', 9000.0, 3.0, 3000.0, 1170.0, 13170.0, 'en camino', '2025-06-18 13:00:00', NULL,
        'Taco Loco'),
       ('3', '3', '1', '1', 6000.0, 4.0, 4000.0, 780.0, 10780.0, 'entregado', '2025-06-18 14:00:00',
        '2025-06-18 14:30:00', 'La Pizzeria'),
       ('4', '1', '3', '2', 11000.0, 6.0, 6000.0, 1430.0, 18430.0, 'en preparación', '2025-06-18 15:00:00', NULL,
        'Sushi House');

INSERT INTO pedido_combos (pedido_id, numero, nombre, precio)
VALUES ('1', 1, 'Pizza Margherita', 4000.0),
       ('1', 2, 'Pizza Pepperoni', 5000.0),
       ('2', 1, 'Taco al Pastor', 4000.0),
       ('2', 2, 'Burrito Grande', 5000.0),
       ('3', 3, 'Pizza Suprema', 6000.0),
       ('4', 1, 'Sushi Básico', 4000.0),
       ('4', 4, 'Sushi Deluxe', 7000.0);

-- Crear tabla admins
CREATE TABLE IF NOT EXISTS admins
(
    id             VARCHAR(36) PRIMARY KEY,
    nombre_usuario VARCHAR(50)  NOT NULL,
    contrasena     VARCHAR(100) NOT NULL
);

-- Insertar datos de prueba para admins
INSERT INTO admins (id, nombre_usuario, contrasena)
VALUES ('1', 'admin1234', 'password1234');

-- Crear tabla quejas (relación con repartidores)
CREATE TABLE IF NOT EXISTS repartidor_quejas
(
    repartidor_id VARCHAR(36) REFERENCES repartidores (id),
    queja         VARCHAR(200) NOT NULL,
    PRIMARY KEY (repartidor_id, queja)
);

-- Insertar datos de prueba para quejas
INSERT INTO repartidor_quejas (repartidor_id, queja)
VALUES ('2', 'Retraso en entrega'),
       ('3', 'Mala atención'),
       ('3', 'Producto dañado'),
       ('3', 'Retraso'),
       ('3', 'No entregó factura');
