CREATE TABLE academic_info
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    student_id       BINARY(16)            NOT NULL,
    programa         VARCHAR(255) NULL,
    semestre_actual  INT NULL,
    promedio_acumulado DOUBLE NULL,
    detalle_materias TEXT NULL,
    CONSTRAINT pk_academic_info PRIMARY KEY (id)
);

CREATE TABLE estudiantes
(
    id                BINARY(16)   NOT NULL,
    nombre            VARCHAR(255) NULL,
    edad              INT NULL,
    correo            VARCHAR(255) NULL,
    telefono          VARCHAR(50) NULL,
    ciudad_residencia VARCHAR(255) NULL,
    CONSTRAINT pk_estudiantes PRIMARY KEY (id)
);

CREATE TABLE materias_academicas
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    academic_info_id BIGINT NOT NULL,
    materia          VARCHAR(255) NULL,
    CONSTRAINT pk_materias_academicas PRIMARY KEY (id)
);

CREATE TABLE preferences_actividades
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    preference_id BIGINT NOT NULL,
    actividad     VARCHAR(255) NULL,
    CONSTRAINT pk_preferences_actividades PRIMARY KEY (id)
);

CREATE TABLE preferencias_estudiantes
(
    id                BIGINT AUTO_INCREMENT NOT NULL,
    student_id        BINARY(16)            NOT NULL,
    modalidad_estudio VARCHAR(255) NULL,
    notificaciones    TEXT NULL,
    CONSTRAINT pk_preferencias_estudiantes PRIMARY KEY (id)
);

ALTER TABLE academic_info
    ADD CONSTRAINT FK_ACADEMIC_INFO_ON_STUDENT FOREIGN KEY (student_id) REFERENCES estudiantes (id);

ALTER TABLE materias_academicas
    ADD CONSTRAINT FK_MATERIAS_ACADEMICAS_ON_ACADEMIC_INFO FOREIGN KEY (academic_info_id) REFERENCES academic_info (id);

ALTER TABLE preferences_actividades
    ADD CONSTRAINT FK_PREFERENCES_ACTIVIDADES_ON_PREFERENCE FOREIGN KEY (preference_id) REFERENCES preferencias_estudiantes (id);

ALTER TABLE preferencias_estudiantes
    ADD CONSTRAINT FK_PREFERENCIAS_ESTUDIANTES_ON_STUDENT FOREIGN KEY (student_id) REFERENCES estudiantes (id);