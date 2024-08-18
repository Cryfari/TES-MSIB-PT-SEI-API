CREATE DATABASE sei_restful_api;

USE sei_restful_api;

CREATE TABLE proyek (
    id int(5) NOT NULL AUTO_INCREMENT,
    nama_proyek varchar(100) NOT NULL,
    client varchar(100) NOT NULL,
    tgl_mulai datetime NOT NULL,
    tgl_selesai datetime NOT NULL,
    pimpinan_proyek varchar(100) NOT NULL,
    keterangan text NOT NULL,
    created_at timestamp NOT NULL DEFAULT current_timestamp(),
    PRIMARY KEY (id),
    UNIQUE (nama_proyek)
) ENGINE=InnoDB;

CREATE TABLE lokasi (
    id int(5) NOT NULL AUTO_INCREMENT,
    nama_lokasi varchar(100) NOT NULL,
    negara varchar(100) NOT NULL,
    provinsi varchar(100) NOT NULL,
    kota varchar(100) NOT NULL,
    created_at timestamp NOT NULL DEFAULT current_timestamp(),
    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE `proyek_lokasi` (
    proyek_id int(5) NOT NULL,
    lokasi_id int(5) DEFAULT NULL,
    FOREIGN KEY fk_proyek_id (proyek_id) REFERENCES proyek (id) ON DELETE CASCADE,
    FOREIGN KEY fk_lokasi_id (lokasi_id) REFERENCES lokasi (id) ON DELETE SET NULL
) ENGINE=InnoDB;