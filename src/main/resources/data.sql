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



INSERT INTO cities (image, name, state_id)
VALUES ('https://i.imgur.com/qMKc4rf.png', 'Guarapuava', '16');



INSERT INTO addresses (neighborhood, number, postal_code, street, city_id)
VALUES ('Industrial', '800', '85053525', 'Avenida Professora Laura Pacheco Bastos', '1');



INSERT INTO users
(email, email_verified, name, phone_number, phone_verified, profile_picture, profile_verified,
 address_id, rating)
VALUES ('profissional1@gmail.com', 'true', 'Nome do Primeiro Profissional', '(42) 99999-9991', 'true',
        'https://i.imgur.com/owhNAKK.png', 'true', '1', 2),
       ('profissional2@gmail.com', 'true', 'Nome do Segundo Profissional', '(42) 99999-9992', 'true',
        'https://i.imgur.com/sHAg9pz.png', 'true', '1', 4),
       ('cliente1@gmail.com', 'true', 'Nome do Primeiro Cliente', '(42) 99999-9993', 'true',
        'https://i.imgur.com/y9dH2bJ.jpeg', 'true', '1', 5),
       ('cliente2@gmail.com', 'true', 'Nome do Segundo Cliente', '(42) 99999-9994', 'true',
        'https://i.imgur.com/owhNAKK.png', 'true', '1', 1),
       ('profissional3@gmail.com', 'true', 'Nome do Terceiro Profissional', '(42) 99999-9995', 'false',
        null, 'false', '1', 3),
       ('profissional4@gmail.com', 'true', 'Nome do Quarto Profissional', '(42) 99999-9996', 'true',
        'https://i.imgur.com/owhNAKK.png', 'true', '1', 2);

INSERT INTO individuals (cpf, description, gender, birth_date, id)
VALUES ('982.988.640-93', 'Descrição profissional 1', 'MASCULINE', '2003-01-01', '1'),
       ('998.045.450-47', 'Descrição profissional 2', 'MASCULINE', '2000-01-01', '2'),
       ('130.218.260-91', 'Descrição profissional 3', 'MASCULINE', '2001-01-01', '3'),
       ('619.487.532-19', 'Descrição profissional 3', 'MASCULINE', '2001-01-01', '4'),
       ('814.541.391-53', 'Descrição profissional 3', 'MASCULINE', '2001-01-01', '5'),
       ('567.690.630-85', 'Descrição profissional 4', 'MASCULINE', '2001-01-01', '6');

INSERT INTO expertises (name, description, path_icon)
VALUES ('Encanador', 'Realiza serviço como encanador', 'https://res.cloudinary.com/dgueb0wir/image/upload/v1656539373/images/f0ykcuhr8kbnjsjg6hrr.svg'),
       ('Eletricista', 'Realiza serviço como eletricista', 'https://res.cloudinary.com/dgueb0wir/image/upload/v1656539373/images/f0ykcuhr8kbnjsjg6hrr.svg'),
       ('Pintor', 'Realiza serviço como pintor', 'https://res.cloudinary.com/dgueb0wir/image/upload/v1656539373/images/f0ykcuhr8kbnjsjg6hrr.svg'),
       ('Mecânico', 'Realiza serviço como mecânico', 'https://res.cloudinary.com/dgueb0wir/image/upload/v1656539373/images/f0ykcuhr8kbnjsjg6hrr.svg');

INSERT INTO professional_expertises (expertise_id, professional_id, rating)
VALUES (1, 1, 1),
       (2, 1, 2),
       (3, 1, 3),
       (4, 1, 4),
       (2, 5, 4),
       (2, 6, 4);


INSERT INTO job_requests
(client_confirmation, date_created, date_expired, description, professional_confirmation, quantity_candidators_max,
 status, client_id, expertise_id)
VALUES ('false', '2021-01-01', '2021-01-01', 'Preciso de um Encanador 1!', 'true', '5', 'AVAILABLE', '2', '1'),
       ('false', '2021-01-01', '2021-01-02', 'Preciso de um Encanador 2!', 'true', '10', 'AVAILABLE', '1', '1'),
       ('false', '2021-01-01', '2021-01-07', 'Preciso de um Encanador 3!', 'true', '15', 'AVAILABLE', '1', '1'),
       ('true', '2021-01-01', '2021-01-07', 'Preciso de um Encanador 30!', 'true', '15', 'TO_HIRED', '1', '1'),
       ('false', '2021-01-01', '2021-01-08', 'Preciso de um Encanador 4!', 'true', '20', 'TO_DO', '1', '1'), -- 4
       ('false', '2021-01-01', '2021-01-08', 'Preciso de um Encanador 5!', 'true', '20', 'CLOSED', '1', '1'),
       ('false', '2021-01-01', '2021-01-08', 'Preciso de um Encanador 6!', 'true', '20', 'CLOSED', '1', '1'),
       ('false', '2021-01-01', '2021-01-30', 'Preciso de um Eletricista 1!', 'true', '5', 'AVAILABLE', '3', '2'),
       ('false', '2021-01-01', '2021-02-02', 'Preciso de um Eletricista 2!', 'true', '10', 'AVAILABLE', '3', '2'),
       ('true', '2021-01-01', '2021-03-01', 'Preciso de um Eletricista 3!', 'true', '15', 'TO_HIRED', '4', '2'),
       ('false', '2021-01-01', '2021-04-01', 'Preciso de um Eletricista 4!', 'true', '20', 'TO_DO', '4', '2'), -- 10
       ('false', '2021-01-01', '2021-04-01', 'Preciso de um Eletricista 5!', 'true', '20', 'CLOSED', '4', '2'),
       ('false', '2021-01-01', '2021-04-01', 'Preciso de um Eletricista 6!', 'true', '20', 'CLOSED', '4', '2'),
       ('true', '2021-01-01', '2021-01-01', 'Preciso de um Pintor 1!', 'true', '5', 'AVAILABLE', '4', '3'),
       ('true', '2021-01-01', '2021-01-02', 'Preciso de um Pintor 2!', 'true', '10', 'AVAILABLE', '4', '3'),
       ('true', '2021-01-01', '2021-01-03', 'Preciso de um Pintor 3!', 'true', '15', 'TO_HIRED', '4', '3'),
       ('true', '2021-01-01', '2021-01-04', 'Preciso de um Pintor 4!', 'true', '20', 'TO_DO', '4', '3'), -- 16
       ('true', '2021-01-01', '2021-01-04', 'Preciso de um Pintor 5!', 'true', '20', 'CLOSED', '4', '3'),
       ('true', '2021-01-01', '2021-01-04', 'Preciso de um Pintor 6!', 'true', '20', 'CLOSED', '4', '3'),
       ('true', '2021-08-26', '2021-08-27', 'Preciso de um Mecânico 1!', 'true', '5', 'AVAILABLE', '4', '4'),
       ('true', '2021-08-26', '2021-08-28', 'Preciso de um Mecânico 2!', 'true', '10', 'AVAILABLE', '4', '4'),
       ('true', '2021-08-26', '2021-09-01', 'Preciso de um Mecânico 3!', 'true', '15', 'AVAILABLE', '4', '4'),
       ('true', '2021-08-26', '2021-09-10', 'Preciso de um Mecânico 4!', 'true', '20', 'AVAILABLE', '4', '4'),
       ('true', '2021-08-26', '2021-09-20', 'Preciso de um Mecânico 5!', 'true', '20', 'AVAILABLE', '4', '4');



INSERT INTO job_contracted (comments, rating, job_request, professional)
VALUES ('Ótimo Encanador!', '5', '4', '1'),
       ('Ótimo Eletricista!', '5', '10', '1'),
       ('Ótimo Pintor!', '5', '16', '1'),
       ('Bom Encanador!', '3', '5', '1'),
       ('Péssimo Encanador!', '1', '6', '1'),
       ('Bom Eletricista!', '3', '11', '1'),
       ('Péssimo Eletricista!', '1', '12', '1'),
       ('Bom Pintor!', '3', '17', '1'),
       ('Péssimo Pintor!', '1', '18', '1');



INSERT INTO job_candidates (job_id, professional_id, chosen_by_budget, date, is_quit)
VALUES ('1', '1', 'false', '2021-01-01', 'false'),
       ('1', '2', 'true', '2021-01-01', 'false'),
       ('1', '5', 'false', '2021-01-01', 'false'),
       ('2', '1', 'false', '2021-01-02', 'false'),
       ('4', '1', 'true', '2021-01-02', 'false'),
       ('5', '1', 'false', '2021-01-29', 'false'),
       ('6', '1', 'false', '2021-01-02', 'false'),
       ('9', '1', 'true', '2021-01-02', 'false'),
       ('10', '1', 'true', '2021-01-02', 'false'),
       ('16', '1', 'true', '2021-01-02', 'false');


