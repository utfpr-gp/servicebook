INSERT INTO states (name, uf)
VALUES ('Acre', 'AC'),
       ('Alagoas', 'AL'),
       ('Amapá', 'AP'),
       ('Amazonas', 'AM'),
       ('Bahia', 'BA'),
       ('Ceará', 'CE'),
       ('Distrito Federal', 'DF'),
       ('Espírito Santo', 'ES'),
       ('Goiás', 'GO'),
       ('Maranhão', 'MA'),
       ('Mato Grosso', 'MT'),
       ('Mato Grosso do Sul', 'MS'),
       ('Minas Gerais', 'MG'),
       ('Pará', 'PA'),
       ('Paraíba', 'PB'),
       ('Paraná', 'PR'),
       ('Pernambuco', 'PE'),
       ('Piauí', 'PI'),
       ('Rio de Janeiro ', 'RJ'),
       ('Rio Grande do Norte ', 'RN'),
       ('Rio Grande do Sul', 'RS'),
       ('Rondônia', 'RO'),
       ('Roraima', 'RR'),
       ('Santa Catarina', 'SC'),
       ('São Paulo ', 'SP'),
       ('Sergipe', 'SE'),
       ('Tocantins', 'TO');



INSERT INTO users (birth_date, email, email_verified, gender, name, phone_number, phone_verified, profile_picture, type)
VALUES ('2018-12-03', 'darth_vader@gmail.com', 'true', 'Masculino', 'Darth Vader', '(42)9 9999-9999', 'true',
        'https://i.imgur.com/owhNAKK.png', 'profissional'),
       ('2018-12-03', 'chewbacca@gmail.com', 'false', 'Masculino', 'Chewbacca', '', 'false', '', 'profissional');

INSERT INTO professionals (cpf, description, rating, denounce_amount, id)
VALUES ('222222222', 'Sua falta de fé é perturbadora.', '5', '1', '1'),
       ('111111111', '', '1', '2', '2');

INSERT INTO clients (usuario_id)
VALUES ('1'),
       ('2');

INSERT INTO expertises (name)
VALUES ('Encanador'),
       ('Eletricista'),
       ('Pintor'),
       ('Desenvolvedor'),
       ('Mecânico');

INSERT INTO professional_expertises (expertise_id, professional_id, rating)
VALUES (1, 1, 1),
       (2, 1, 2),
       (3, 1, 3),
       (4, 1, 4),
       (5, 1, 5);

INSERT INTO job_requests (client_confirmation, date_created, date_expired, description, professional_confirmation, quantity_candidators_max, status, client_id, expertise_id)
VALUES ('true', '2018-12-03', '2018-12-03', 'Lorem ipsum dolor', 'true', '3', 'CLOSED', '2', '1'),
       ('true', '2018-12-03', '2018-12-03', 'Lorem ipsum dolor', 'true', '4', 'CLOSED', '2', '1'),
       ('true', '2018-12-03', '2018-12-03', 'Lorem ipsum dolor', 'true', '3', 'CLOSED', '2', '2');

INSERT INTO job_contracted (comments, rating, job_request, professional)
VALUES ('Muito bom profissional', '5', '1', '1'),
       ('Péssimo', '1', '2', '1'),
       ('Gostei do trabalho!', '4', '3', '1');
