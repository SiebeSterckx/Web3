CREATE SCHEMA IF NOT EXISTS groep115;

-- C R E A T E   U S E R   T A B L E

GRANT ALL ON SCHEMA groep115 TO local_r0889536;

CREATE SEQUENCE IF NOT EXISTS groep115.user_id_seq;

GRANT ALL ON SEQUENCE groep115.user_id_seq TO local_r0889536;

CREATE TABLE IF NOT EXISTS groep115.users
(   userId integer NOT NULL DEFAULT nextval('groep115.user_id_seq'::regclass),
    name character varying COLLATE pg_catalog."default" NOT NULL,
    firstName character varying COLLATE pg_catalog."default" NOT NULL,
    email character varying COLLATE pg_catalog."default" NOT NULL,
    team character varying COLLATE pg_catalog."default" NOT NULL,
    role character varying COLLATE pg_catalog."default" NOT NULL,
    password character varying COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT user_pkey PRIMARY KEY (userId, name)
    );

GRANT ALL ON TABLE groep115.users TO local_r0889536;

-- grant aan andere student
GRANT ALL ON SCHEMA groep115 TO local_r0889531;
GRANT ALL ON SEQUENCE groep115.user_id_seq TO local_r0889531;
GRANT ALL ON TABLE groep115.users TO local_r0889531;

-- grant aan lectoren
GRANT ALL ON SCHEMA groep115 TO local_u0015529;
GRANT ALL ON SEQUENCE groep115.user_id_seq TO local_u0015529;
GRANT ALL ON TABLE groep115.users TO local_u0015529;

GRANT ALL ON SCHEMA groep115 TO local_u0034562;
GRANT ALL ON SEQUENCE groep115.user_id_seq TO local_u0034562;
GRANT ALL ON TABLE groep115.users TO local_u0034562;


-- C R E A T E   P R O J E C T   T A B L E

CREATE SEQUENCE IF NOT EXISTS groep115.project_id_seq;

GRANT ALL ON SEQUENCE groep115.project_id_seq TO local_r0889536;

CREATE TABLE IF NOT EXISTS groep115.projects
(   projectId integer NOT NULL DEFAULT nextval('groep115.project_id_seq'::regclass),
    name character varying COLLATE pg_catalog."default" NOT NULL,
    team character varying COLLATE pg_catalog."default" NOT NULL,
    startdate timestamp NOT NULL,
    enddate timestamp,
    CONSTRAINT project_pkey PRIMARY KEY (projectId, name)
    );

GRANT ALL ON TABLE groep115.projects TO local_r0889536;

-- grant aan andere student
GRANT ALL ON SCHEMA groep115 TO local_r0889531;
GRANT ALL ON SEQUENCE groep115.project_id_seq TO local_r0889531;
GRANT ALL ON TABLE groep115.projects TO local_r0889531;

-- grant aan lectoren
GRANT ALL ON SCHEMA groep115 TO local_u0015529;
GRANT ALL ON SEQUENCE groep115.project_id_seq TO local_u0015529;
GRANT ALL ON TABLE groep115.projects TO local_u0015529;

GRANT ALL ON SCHEMA groep115 TO local_u0034562;
GRANT ALL ON SEQUENCE groep115.project_id_seq TO local_u0034562;
GRANT ALL ON TABLE groep115.projects TO local_u0034562;


-- C R E A T E   W O R K O R D E R   T A B L E

CREATE SEQUENCE IF NOT EXISTS groep115.workorder_id_seq;

GRANT ALL ON SEQUENCE groep115.workorder_id_seq TO local_r0889536;

CREATE TABLE IF NOT EXISTS groep115.workorders
(   workorderId integer NOT NULL DEFAULT nextval('groep115.workorder_id_seq'::regclass),
    name character varying COLLATE pg_catalog."default" NOT NULL,
    team character varying COLLATE pg_catalog."default" NOT NULL,
    date date NOT NULL,
    starttime timestamp NOT NULL,
    endtime timestamp NOT NULL,
    description varchar(100),
    CONSTRAINT  workorder_pkey PRIMARY KEY (workorderId)
    );

GRANT ALL ON TABLE groep115.workorders TO local_r0889536;

-- grant aan andere student
GRANT ALL ON SCHEMA groep115 TO local_r0889531;
GRANT ALL ON SEQUENCE groep115.workorder_id_seq TO local_r0889531;
GRANT ALL ON TABLE groep115.workorders TO local_r0889531;

-- grant aan lectoren
GRANT ALL ON SCHEMA groep115 TO local_u0015529;
GRANT ALL ON SEQUENCE groep115.workorder_id_seq TO local_u0015529;
GRANT ALL ON TABLE groep115.workorders TO local_u0015529;

GRANT ALL ON SCHEMA groep115 TO local_u0034562;
GRANT ALL ON SEQUENCE groep115.workorder_id_seq TO local_u0034562;
GRANT ALL ON TABLE groep115.workorders TO local_u0034562;