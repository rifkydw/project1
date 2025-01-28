package com.example.app_guru;
public class Ujian {
    private String ujianId; // ID unik untuk ujian
    private String namaUjian;
    private String tanggal;
    private String waktuMulai; // Waktu mulai
    private String waktuSelesai; // Waktu selesai
    private String pengawas;

    public Ujian() {
        // Constructor tanpa argumen (diperlukan untuk Firebase)
    }

    public Ujian(String ujianId, String namaUjian, String tanggal, String waktuMulai, String waktuSelesai, String pengawas) {
        this.ujianId = ujianId;
        this.namaUjian = namaUjian;
        this.tanggal = tanggal;
        this.waktuMulai = waktuMulai;
        this.waktuSelesai = waktuSelesai;
        this.pengawas = pengawas;
    }

    // Getter dan Setter untuk setiap atribut
    public String getUjianId() {
        return ujianId;
    }

    public void setUjianId(String ujianId) {
        this.ujianId = ujianId;
    }

    public String getNamaUjian() {
        return namaUjian;
    }

    public void setNamaUjian(String namaUjian) {
        this.namaUjian = namaUjian;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal; // Memperbaiki duplikasi
    }

    public String getWaktuMulai() {
        return waktuMulai;
    }

    public void setWaktuMulai(String waktuMulai) {
        this.waktuMulai = waktuMulai;
    }

    public String getWaktuSelesai() {
        return waktuSelesai;
    }

    public void setWaktuSelesai(String waktuSelesai) {
        this.waktuSelesai = waktuSelesai;
    }

    public String getPengawas() {
        return pengawas;
    }

    public void setPengawas(String pengawas) {
        this.pengawas = pengawas;
    }

    @Override
    public String toString() {
        return String.format("Ujian %s pada %s, dari %s hingga %s, diawasi oleh %s",
                namaUjian, tanggal, waktuMulai, waktuSelesai, pengawas);
    }
}

