package com.mycompany.kasirbi;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

// ================= CLASS INDUK =================
class Barang {
    private String namaBarang;
    private int hargaBarang;
    private int jumlahBeli;

    public Barang(String namaBarang, int hargaBarang, int jumlahBeli) {
        this.namaBarang = namaBarang;
        this.hargaBarang = hargaBarang;
        this.jumlahBeli = jumlahBeli;
    }

    // Getter untuk Enkapsulasi
    public String getNamaBarang() {
        return namaBarang;
    }

    public int getHargaBarang() {
        return hargaBarang;
    }

    public int getJumlahBeli() {
        return jumlahBeli;
    }

    // Polymorphism (Method Virtual)
    public double hitungDiskon() {
        return 0;
    }

    public double totalBayar() {
        return (hargaBarang * jumlahBeli) - hitungDiskon();
    }
}

// ================= CLASS TURUNAN =================
class Makanan extends Barang {
    public Makanan(String namaBarang, int hargaBarang, int jumlahBeli) {
        super(namaBarang, hargaBarang, jumlahBeli);
    }

    @Override
    public double hitungDiskon() {
        // Makanan mendapatkan diskon 10% jika beli lebih dari 2 item
        if (getJumlahBeli() > 2) {
            return (getHargaBarang() * getJumlahBeli()) * 0.10;
        }
        return 0;
    }
}

class Minuman extends Barang {
    public Minuman(String namaBarang, int hargaBarang, int jumlahBeli) {
        super(namaBarang, hargaBarang, jumlahBeli);
    }

    @Override
    public double hitungDiskon() {
        // Minuman mendapatkan diskon 5% jika beli lebih dari 3 item
        if (getJumlahBeli() > 3) {
            return (getHargaBarang() * getJumlahBeli()) * 0.05;
        }
        return 0;
    }
}

// ================= CLASS UTAMA (GUI) =================
public class KasirBI extends JFrame {

    JLabel lblNama = new JLabel("Nama Barang");
    JLabel lblHarga = new JLabel("Harga Barang");
    JLabel lblJumlah = new JLabel("Jumlah Beli");
    JLabel lblJenis = new JLabel("Jenis Barang");

    JTextField txtNama = new JTextField();
    JTextField txtHarga = new JTextField();
    JTextField txtJumlah = new JTextField();

    String[] jenis = {"Makanan", "Minuman"};
    JComboBox<String> cmbJenis = new JComboBox<>(jenis);

    // Penyesuaian tombol GUI
    JButton btnTambah = new JButton("Tambah");
    JButton btnHitungTotal = new JButton("Hitung Total");
    JButton btnCetak = new JButton("Cetak Struk");
    JButton btnReset = new JButton("Reset");

    JTable table;
    DefaultTableModel model;

    JLabel lblTotal = new JLabel("Total Pembayaran : Rp 0");

    double grandTotal = 0;
    
    // Pengaturan Arraylist
    ArrayList<Barang> daftarBelanja = new ArrayList<>();

    public KasirBI() {

        setTitle("Aplikasi KasirBI");
        setSize(800, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null); 

        // Mengatur posisi komponen Label dan TextField
        lblNama.setBounds(20, 20, 120, 25);
        txtNama.setBounds(150, 20, 200, 25);

        lblHarga.setBounds(20, 60, 120, 25);
        txtHarga.setBounds(150, 60, 200, 25);

        lblJumlah.setBounds(20, 100, 120, 25);
        txtJumlah.setBounds(150, 100, 200, 25);

        lblJenis.setBounds(20, 140, 120, 25);
        cmbJenis.setBounds(150, 140, 200, 25);

        // Mengatur posisi tombol aksi (Diperbarui untuk tombol Cetak)
        btnTambah.setBounds(20, 190, 90, 30);
        btnHitungTotal.setBounds(120, 190, 110, 30);
        btnCetak.setBounds(240, 190, 110, 30);
        btnReset.setBounds(360, 190, 80, 30);

        // Pengaturan Model Tabel Kasir
        model = new DefaultTableModel();
        model.addColumn("Nama Barang");
        model.addColumn("Jenis Barang");
        model.addColumn("Harga");
        model.addColumn("Jumlah");
        model.addColumn("Diskon");
        model.addColumn("Total Bayar");

        table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(20, 240, 740, 200);

        lblTotal.setBounds(20, 460, 400, 30);
        lblTotal.setFont(new java.awt.Font("Tahoma", java.awt.Font.BOLD, 14));

        // Menambahkan seluruh komponen ke Frame GUI
        add(lblNama); add(txtNama);
        add(lblHarga); add(txtHarga);
        add(lblJumlah); add(txtJumlah);
        add(lblJenis); add(cmbJenis);
        add(btnTambah); add(btnHitungTotal); add(btnCetak); add(btnReset);
        add(pane); add(lblTotal);

        // Event Handling Tombol
        btnTambah.addActionListener(e -> tambahData());
        btnHitungTotal.addActionListener(e -> lblTotal.setText("Total Pembayaran : Rp " + (long) grandTotal));
        btnCetak.addActionListener(e -> cetakStruk());
        btnReset.addActionListener(e -> resetData());

        setVisible(true);
    }

    // Method untuk memproses dan menambahkan data ke dalam tabel & ArrayList
    private void tambahData() {
        try {
            String nama = txtNama.getText().trim();
            if (nama.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nama barang tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int hargaBarang = Integer.parseInt(txtHarga.getText().trim());
            int jumlahBeli = Integer.parseInt(txtJumlah.getText().trim());
            String jenisBarang = (String) cmbJenis.getSelectedItem();

            Barang barang;

            // Penerapan Polimorfisme
            if (jenisBarang.equals("Makanan")) {
                barang = new Makanan(nama, hargaBarang, jumlahBeli);
            } else {
                barang = new Minuman(nama, hargaBarang, jumlahBeli);
            }

            // Menyimpan objek ke ArrayList
            daftarBelanja.add(barang);

            double diskon = barang.hitungDiskon();
            double total = barang.totalBayar();
            grandTotal += total;

            // Menambah baris ke GUI Tabel
            model.addRow(new Object[]{
                barang.getNamaBarang(),
                jenisBarang,
                "Rp " + barang.getHargaBarang(),
                barang.getJumlahBeli() + " pcs",
                "Rp " + (long) diskon,
                "Rp " + (long) total
            });

            // Reset field input
            txtNama.setText("");
            txtHarga.setText("");
            txtJumlah.setText("");
            cmbJenis.setSelectedIndex(0);
            
            JOptionPane.showMessageDialog(this, "Barang berhasil dimasukkan keranjang!", "Sukses", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Harga dan Jumlah Beli harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method Baru: Menampilkan Struk Pembayaran
    private void cetakStruk() {
        if (daftarBelanja.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Keranjang belanja kosong! Silakan tambah barang terlebih dahulu.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        StringBuilder struk = new StringBuilder();
        struk.append("========== STRUK PEMBAYARAN ==========\n");
        struk.append("          TOKO MODERN BI          \n");
        struk.append("======================================\n\n");

        for (Barang b : daftarBelanja) {
            struk.append("Nama Barang : ").append(b.getNamaBarang()).append("\n");
            struk.append("Harga Satuan: Rp ").append(b.getHargaBarang()).append("\n");
            struk.append("Jumlah Beli : ").append(b.getJumlahBeli()).append(" pcs\n");
            
            if (b.hitungDiskon() > 0) {
                struk.append("Diskon      : Rp ").append((long) b.hitungDiskon()).append("\n");
            }
            struk.append("Subtotal    : Rp ").append((long) b.totalBayar()).append("\n");
            struk.append("--------------------------------------\n");
        }

        struk.append("\n======================================\n");
        struk.append("TOTAL BAYAR : Rp ").append((long) grandTotal).append("\n");
        struk.append("======================================\n");
        struk.append("Terima kasih atas kunjungan Anda!\n");

        // Menampilkan struk di dalam JTextArea pop-up
        JTextArea areaStruk = new JTextArea(struk.toString());
        areaStruk.setEditable(false);
        areaStruk.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12)); // Font rapi seperti struk asli

        JOptionPane.showMessageDialog(this, new JScrollPane(areaStruk), "Cetak Struk", JOptionPane.INFORMATION_MESSAGE);
    }

    // Method untuk mereset transaksi dan ArrayList
    private void resetData() {
        model.setRowCount(0);
        daftarBelanja.clear(); // Mengosongkan data di ArrayList
        grandTotal = 0;
        
        lblTotal.setText("Total Pembayaran : Rp 0");
        txtNama.setText("");
        txtHarga.setText("");
        txtJumlah.setText("");
        cmbJenis.setSelectedIndex(0);
        
        JOptionPane.showMessageDialog(this, "Data transaksi berhasil dikosongkan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
    }

    // Main Method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new KasirBI());
    }
}