--
-- PostgreSQL database dump
--

-- Dumped from database version 16.0
-- Dumped by pg_dump version 16.0

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: format; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.format (
    id_format character varying(10) NOT NULL,
    nom character varying(50)
);


ALTER TABLE public.format OWNER TO postgres;

--
-- Name: look; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.look (
    id_look integer NOT NULL,
    nom character varying(50) NOT NULL
);


ALTER TABLE public.look OWNER TO postgres;

--
-- Name: look_id_look_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.look_id_look_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.look_id_look_seq OWNER TO postgres;

--
-- Name: look_id_look_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.look_id_look_seq OWNED BY public.look.id_look;


--
-- Name: look_matiere; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.look_matiere (
    id integer NOT NULL,
    id_look integer,
    id_matiere integer
);


ALTER TABLE public.look_matiere OWNER TO postgres;

--
-- Name: look_matiere_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.look_matiere_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.look_matiere_id_seq OWNER TO postgres;

--
-- Name: look_matiere_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.look_matiere_id_seq OWNED BY public.look_matiere.id;


--
-- Name: matiere; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.matiere (
    id_matiere character varying(10) NOT NULL,
    nom character varying(50) NOT NULL,
    prix_unitaire double precision DEFAULT 0
);


ALTER TABLE public.matiere OWNER TO postgres;

--
-- Name: mouvement; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mouvement (
    id_matiere character varying(10),
    date date DEFAULT now(),
    entree double precision DEFAULT 0,
    sortie double precision DEFAULT 0,
    prix double precision DEFAULT 0,
    id_mouvement character varying(10) NOT NULL
);


ALTER TABLE public.mouvement OWNER TO postgres;

--
-- Name: produit; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.produit (
    id_produit character varying(10) NOT NULL,
    nom character varying(50)
);


ALTER TABLE public.produit OWNER TO postgres;

--
-- Name: quantite; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.quantite (
    id_produit character varying(10),
    id_format character varying(10),
    id_matiere character varying(10),
    quantite integer
);


ALTER TABLE public.quantite OWNER TO postgres;

--
-- Name: seq_format; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_format
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_format OWNER TO postgres;

--
-- Name: seq_matiere; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_matiere
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_matiere OWNER TO postgres;

--
-- Name: seq_mouvement; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_mouvement
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_mouvement OWNER TO postgres;

--
-- Name: seq_produit; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_produit
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_produit OWNER TO postgres;

--
-- Name: seq_quantite; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seq_quantite
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seq_quantite OWNER TO postgres;

--
-- Name: v_etat_stock; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_etat_stock AS
 SELECT id_matiere,
    sum(entree) AS entree,
    sum(sortie) AS sortie,
    (sum(entree) - sum(sortie)) AS stock
   FROM public.mouvement
  GROUP BY id_matiere;


ALTER VIEW public.v_etat_stock OWNER TO postgres;

--
-- Name: v_fabrication; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_fabrication AS
 SELECT quantite.id_produit,
    produit.nom AS produit,
    quantite.id_format,
    format.nom AS format,
    quantite.id_matiere,
    matiere.nom AS matiere,
    quantite.quantite
   FROM (((public.quantite
     JOIN public.produit ON (((quantite.id_produit)::text = (produit.id_produit)::text)))
     JOIN public.format ON (((quantite.id_format)::text = (format.id_format)::text)))
     JOIN public.matiere ON (((quantite.id_matiere)::text = (matiere.id_matiere)::text)));


ALTER VIEW public.v_fabrication OWNER TO postgres;

--
-- Name: v_produit_prix; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_produit_prix AS
 SELECT quantite.id_produit,
    quantite.id_format,
    sum(((quantite.quantite)::double precision * matiere.prix_unitaire)) AS prix
   FROM (public.quantite
     JOIN public.matiere ON (((quantite.id_matiere)::text = (matiere.id_matiere)::text)))
  GROUP BY quantite.id_produit, quantite.id_format;


ALTER VIEW public.v_produit_prix OWNER TO postgres;

--
-- Name: look id_look; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.look ALTER COLUMN id_look SET DEFAULT nextval('public.look_id_look_seq'::regclass);


--
-- Name: look_matiere id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.look_matiere ALTER COLUMN id SET DEFAULT nextval('public.look_matiere_id_seq'::regclass);


--
-- Data for Name: format; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.format (id_format, nom) FROM stdin;
2	GM
1	PM
FOR01	John
FOR02	B3
\.


--
-- Data for Name: look; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.look (id_look, nom) FROM stdin;
1	Luxe
2	Normal
3	Debraille
\.


--
-- Data for Name: look_matiere; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.look_matiere (id, id_look, id_matiere) FROM stdin;
1	1	1
2	1	2
3	2	2
4	3	4
\.


--
-- Data for Name: matiere; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.matiere (id_matiere, nom, prix_unitaire) FROM stdin;
MAT001	Cuir	5000
MAT002	Coton	4000
MAT003	Soga	3000
MAT004	Lin	2000
\.


--
-- Data for Name: mouvement; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.mouvement (id_matiere, date, entree, sortie, prix, id_mouvement) FROM stdin;
MAT001	2023-05-16	180	0	0	M0004
MAT003	2023-05-16	135	0	0	M0005
MAT003	2023-12-15	45	0	0	M0006
MAT001	2023-12-15	90	0	0	M0007
MAT003	2023-12-15	1	0	0	M0008
MAT001	2023-12-15	2	0	0	M0009
\.


--
-- Data for Name: produit; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.produit (id_produit, nom) FROM stdin;
PRO1	Chic Carry
PRO2	Urban Elegance
PRO3	Stylish Tote
PRO4	Trendy Satchel
\.


--
-- Data for Name: quantite; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.quantite (id_produit, id_format, id_matiere, quantite) FROM stdin;
PRO1	1	MAT003	1
PRO1	2	MAT001	4
PRO1	1	MAT001	2
PRO1	2	MAT003	3
PRO2	1	MAT002	1
PRO2	1	MAT004	3
PRO2	2	MAT002	2
PRO2	2	MAT004	5
PRO3	1	MAT004	2
PRO3	1	MAT001	3
PRO3	2	MAT004	3
PRO3	2	MAT001	6
PRO4	1	MAT003	2
PRO4	1	MAT002	1
PRO4	2	MAT003	4
PRO4	2	MAT002	3
\.


--
-- Name: look_id_look_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.look_id_look_seq', 3, true);


--
-- Name: look_matiere_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.look_matiere_id_seq', 4, true);


--
-- Name: seq_format; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_format', 2, true);


--
-- Name: seq_matiere; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_matiere', 2, true);


--
-- Name: seq_mouvement; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_mouvement', 9, true);


--
-- Name: seq_produit; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_produit', 1, false);


--
-- Name: seq_quantite; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seq_quantite', 1, false);


--
-- Name: format format_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.format
    ADD CONSTRAINT format_pkey PRIMARY KEY (id_format);


--
-- Name: look_matiere look_matiere_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.look_matiere
    ADD CONSTRAINT look_matiere_pkey PRIMARY KEY (id);


--
-- Name: look look_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.look
    ADD CONSTRAINT look_pkey PRIMARY KEY (id_look);


--
-- Name: matiere matiere_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.matiere
    ADD CONSTRAINT matiere_pkey PRIMARY KEY (id_matiere);


--
-- Name: mouvement mouvement_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mouvement
    ADD CONSTRAINT mouvement_pkey PRIMARY KEY (id_mouvement);


--
-- Name: produit produit_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.produit
    ADD CONSTRAINT produit_pkey PRIMARY KEY (id_produit);


--
-- Name: look_matiere look_matiere_id_look_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.look_matiere
    ADD CONSTRAINT look_matiere_id_look_fkey FOREIGN KEY (id_look) REFERENCES public.look(id_look);


--
-- Name: mouvement mouvement_id_matiere_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mouvement
    ADD CONSTRAINT mouvement_id_matiere_fkey FOREIGN KEY (id_matiere) REFERENCES public.matiere(id_matiere);


--
-- Name: quantite quantite_id_format_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.quantite
    ADD CONSTRAINT quantite_id_format_fkey FOREIGN KEY (id_format) REFERENCES public.format(id_format);


--
-- Name: quantite quantite_id_matiere_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.quantite
    ADD CONSTRAINT quantite_id_matiere_fkey FOREIGN KEY (id_matiere) REFERENCES public.matiere(id_matiere);


--
-- Name: quantite quantite_id_produit_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.quantite
    ADD CONSTRAINT quantite_id_produit_fkey FOREIGN KEY (id_produit) REFERENCES public.produit(id_produit);


--
-- PostgreSQL database dump complete
--

