-- CLIENTE
CREATE TABLE cliente (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         cpf VARCHAR(11) NOT NULL UNIQUE,
                         nome VARCHAR(45) NOT NULL
);

-- VEICULO (single table para herança com discriminator)
CREATE TABLE veiculo (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         tipo_veiculo VARCHAR(31), -- usado pelo @DiscriminatorColumn
                         placa VARCHAR(8) NOT NULL UNIQUE,
                         marca VARCHAR(50) NOT NULL,
                         modelo VARCHAR(50) NOT NULL,
                         ano INT NOT NULL,
                         diaria DOUBLE NOT NULL,
                         valorBem DOUBLE NOT NULL
);

-- ALUGUEL
CREATE TABLE aluguel (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         veiculo_id BIGINT NOT NULL,
                         cliente_id BIGINT NOT NULL,
                         dias INT NOT NULL,
                         data_inicio TIMESTAMP NOT NULL,
                         data_devolucao TIMESTAMP,
                         aluguel DOUBLE NOT NULL,

                         FOREIGN KEY (veiculo_id) REFERENCES veiculo(id),
                         FOREIGN KEY (cliente_id) REFERENCES cliente(id)
);
