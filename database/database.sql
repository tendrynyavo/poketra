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

--
-- Name: cat_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.cat_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.cat_seq OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: categories; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.categories (
    id_categorie character varying(10) NOT NULL,
    nom character varying(255),
    salaire double precision
);


ALTER TABLE public.categories OWNER TO postgres;

--
-- Name: eff_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.eff_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.eff_seq OWNER TO postgres;

--
-- Name: effectifs; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.effectifs (
    id_effectif character varying(10) NOT NULL,
    id_categorie character varying(10),
    id_produit character varying(10),
    id_format character varying(10),
    nombre integer DEFAULT 0
);


ALTER TABLE public.effectifs OWNER TO postgres;

--
-- Name: empl_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.empl_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.empl_seq OWNER TO postgres;

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
    nom character varying(50),
    duree integer DEFAULT 0
);


ALTER TABLE public.produit OWNER TO postgres;

--
-- Name: produit_format_prix; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.produit_format_prix (
    id_produit character varying(10),
    id_format character varying(10),
    prix_de_vente double precision
);


ALTER TABLE public.produit_format_prix OWNER TO postgres;

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
-- Name: v_produit; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_produit AS
 SELECT p.id_produit,
    p.nom,
    pfp.id_format,
    p.duree,
    pfp.prix_de_vente
   FROM (public.produit p
     JOIN public.produit_format_prix pfp ON (((p.id_produit)::text = (pfp.id_produit)::text)));


ALTER VIEW public.v_produit OWNER TO postgres;

--
-- Name: v_produit_effectif; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_produit_effectif AS
 SELECT e.id_effectif,
    e.id_categorie,
    e.id_produit,
    e.id_format,
    e.nombre,
    p.duree,
    p.prix_de_vente
   FROM (public.effectifs e
     JOIN public.v_produit p ON ((((e.id_produit)::text = (p.id_produit)::text) AND ((e.id_format)::text = (p.id_format)::text))));


ALTER VIEW public.v_produit_effectif OWNER TO postgres;

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
-- Name: v_produit_effectif_prix; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_produit_effectif_prix AS
 SELECT pe.id_effectif,
    pe.id_categorie,
    pe.id_produit,
    pe.id_format,
    pp.prix,
    pe.nombre,
    pe.duree,
    pe.prix_de_vente
   FROM (public.v_produit_effectif pe
     JOIN public.v_produit_prix pp ON ((((pe.id_produit)::text = (pp.id_produit)::text) AND ((pe.id_format)::text = (pp.id_format)::text))));


ALTER VIEW public.v_produit_effectif_prix OWNER TO postgres;

--
-- Name: v_benefice; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.v_benefice AS
 SELECT pep.id_produit,
    pep.id_format,
    pep.prix_de_vente,
    sum(pep.nombre) AS sum,
    (sum((c.salaire * (pep.nombre)::double precision)) * (pep.duree)::double precision) AS salaire,
    pep.prix,
    ((sum((c.salaire * (pep.nombre)::double precision)) * (pep.duree)::double precision) + pep.prix) AS depense,
    (pep.prix_de_vente - ((sum((c.salaire * (pep.nombre)::double precision)) * (pep.duree)::double precision) + pep.prix)) AS benefice
   FROM (public.v_produit_effectif_prix pep
     JOIN public.categories c ON (((pep.id_categorie)::text = (c.id_categorie)::text)))
  GROUP BY pep.id_produit, pep.id_format, pep.prix_de_vente, pep.duree, pep.prix;


ALTER VIEW public.v_benefice OWNER TO postgres;

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
-- Name: look id_look; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.look ALTER COLUMN id_look SET DEFAULT nextval('public.look_id_look_seq'::regclass);


--
-- Name: look_matiere id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.look_matiere ALTER COLUMN id SET DEFAULT nextval('public.look_matiere_id_seq'::regclass);


--
-- Data for Name: categories; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.categories (id_categorie, nom, salaire) FROM stdin;
CAT1	ouvrier	24
CAT2	transporteur	16
\.


--
-- Data for Name: effectifs; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.effectifs (id_effectif, id_categorie, id_produit, id_format, nombre) FROM stdin;
EFF1	CAT1	PRO1	1	3
EFF2	CAT1	PRO1	2	6
EFF3	CAT2	PRO1	1	5
EFF4	CAT2	PRO1	2	10
EFF01	CAT1	PRO1	1	2
EFF02	CAT1	PRO1	2	4
\.


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

COPY public.produit (id_produit, nom, duree) FROM stdin;
PRO1	Chic Carry	11
PRO2	Urban Elegance	14
PRO3	Stylish Tote	15
PRO4	Trendy Satchel	10
\.


--
-- Data for Name: produit_format_prix; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.produit_format_prix (id_produit, id_format, prix_de_vente) FROM stdin;
PRO1	1	38097
PRO2	1	31424
PRO3	1	46046
PRO4	1	33569
PRO1	2	42010
PRO2	2	47544
PRO3	2	35489
PRO4	2	52974
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
-- Name: cat_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cat_seq', 1, false);


--
-- Name: eff_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.eff_seq', 2, true);


--
-- Name: empl_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.empl_seq', 1, false);


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
-- Name: categories categories_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categories
    ADD CONSTRAINT categories_pkey PRIMARY KEY (id_categorie);


--
-- Name: effectifs effectifs_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.effectifs
    ADD CONSTRAINT effectifs_pkey PRIMARY KEY (id_effectif);


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
-- Name: effectifs effectifs_id_categorie_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.effectifs
    ADD CONSTRAINT effectifs_id_categorie_fkey FOREIGN KEY (id_categorie) REFERENCES public.categories(id_categorie);


--
-- Name: effectifs effectifs_id_format_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.effectifs
    ADD CONSTRAINT effectifs_id_format_fkey FOREIGN KEY (id_format) REFERENCES public.format(id_format);


--
-- Name: effectifs effectifs_id_produit_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.effectifs
    ADD CONSTRAINT effectifs_id_produit_fkey FOREIGN KEY (id_produit) REFERENCES public.produit(id_produit);


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
-- Name: produit_format_prix produit_format_prix_id_format_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.produit_format_prix
    ADD CONSTRAINT produit_format_prix_id_format_fkey FOREIGN KEY (id_format) REFERENCES public.format(id_format);


--
-- Name: produit_format_prix produit_format_prix_id_produit_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.produit_format_prix
    ADD CONSTRAINT produit_format_prix_id_produit_fkey FOREIGN KEY (id_produit) REFERENCES public.produit(id_produit);


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

