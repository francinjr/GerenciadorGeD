CREATE TABLE finances (
    type smallint CHECK (type BETWEEN 0 AND 1),
    value float (53),
    id bigserial NOT NULL,
    paiday timestamp(6),
    description varchar(255),
    name varchar(255),
    PRIMARY KEY (id)
);