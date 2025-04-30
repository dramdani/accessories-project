import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;

public class AccessoriesMasterMenu extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JComboBox<String> comboFilter1, comboFilter2;
    private JTextField fieldKeyword1, fieldKeyword2;
    private JButton btnSearch;

    public AccessoriesMasterMenu() {
        setTitle("Master Data Aksesoris");
        setSize(1100, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel Pencarian
        JPanel panelSearch = new JPanel(new FlowLayout(FlowLayout.LEFT));
        comboFilter1 = new JComboBox<>(new String[]{"-", "Kode Aksesoris", "Kategori Aksesoris"});
        fieldKeyword1 = new JTextField(15);
        comboFilter2 = new JComboBox<>(new String[]{"-", "Kode Aksesoris", "Kategori Aksesoris"});
        fieldKeyword2 = new JTextField(15);
        btnSearch = new JButton("Cari");

        panelSearch.add(new JLabel("Filter 1:"));
        panelSearch.add(comboFilter1);
        panelSearch.add(fieldKeyword1);
        panelSearch.add(new JLabel("Filter 2:"));
        panelSearch.add(comboFilter2);
        panelSearch.add(fieldKeyword2);
        panelSearch.add(btnSearch);
        add(panelSearch, BorderLayout.NORTH);

        // Kolom Data
        String[] columnNames = {
            "Tanggal Dibuat", "Kode Aksesoris", "Nama Aksesoris", "Kategori Aksesoris",
            "Satuan", "Harga Jual", "Diskon %", "Diskon IDR"
        };
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Event Tombol Cari
        btnSearch.addActionListener(e -> performSearch());
    }

    private void performSearch() {
        String filter1 = (String) comboFilter1.getSelectedItem();
        String keyword1 = fieldKeyword1.getText().trim().toLowerCase();
        String filter2 = (String) comboFilter2.getSelectedItem();
        String keyword2 = fieldKeyword2.getText().trim().toLowerCase();

        String sql = "SELECT a.cre_tms, a.accessories_tp_key, a.accessories_tp_name, c.accessories_category_tp_name, " +
                     "a.accessories_tp_satuan, a.accessories_tp_price_sell, a.accessories_tp_disc_percent, a.accessories_tp_disc_idr " +
                     "FROM accessories_tp a " +
                     "JOIN accessories_category_tp c ON a.accessories_category_tp_key = c.accessories_category_tp_key " +
                     "WHERE 1=1";

        if (!filter1.equals("-") && !keyword1.isEmpty()) {
            if (filter1.equals("Kode Aksesoris")) {
                sql += " AND a.accessories_tp_key LIKE ?";
            } else if (filter1.equals("Kategori Aksesoris")) {
                sql += " AND c.accessories_category_tp_name LIKE ?";
            }
        }

        if (!filter2.equals("-") && !keyword2.isEmpty()) {
            if (filter2.equals("Kode Aksesoris")) {
                sql += " AND a.accessories_tp_key LIKE ?";
            } else if (filter2.equals("Kategori Aksesoris")) {
                sql += " AND c.accessories_category_tp_name LIKE ?";
            }
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory", "root", "password");
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            int paramIndex = 1;
            if (!filter1.equals("-") && !keyword1.isEmpty()) {
                stmt.setString(paramIndex++, "%" + keyword1 + "%");
            }
            if (!filter2.equals("-") && !keyword2.isEmpty()) {
                stmt.setString(paramIndex++, "%" + keyword2 + "%");
            }

            ResultSet rs = stmt.executeQuery();
            model.setRowCount(0); // Clear existing data
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString("cre_tms"),
                    rs.getString("accessories_tp_key"),
                    rs.getString("accessories_tp_name"),
                    rs.getString("accessories_category_tp_name"),
                    rs.getString("accessories_tp_satuan"),
                    rs.getDouble("accessories_tp_price_sell"),
                    rs.getDouble("accessories_tp_disc_percent"),
                    rs.getDouble("accessories_tp_disc_idr")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AccessoriesMasterMenu app = new AccessoriesMasterMenu();
            app.setVisible(true);
        });
    }
}
