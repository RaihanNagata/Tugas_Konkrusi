import javax.swing.*;
import java.awt.*;

public class MainFrame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Contoh Konkurensi di Swing");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 200);
            frame.setLayout(new BorderLayout());

            // Label untuk status
            JLabel statusLabel = new JLabel("Tekan tombol untuk mulai tugas berat", JLabel.CENTER);

            // Tombol untuk memulai proses
            JButton startButton = new JButton("Mulai");

            // Progress bar
            JProgressBar progressBar = new JProgressBar(0, 60);
            progressBar.setValue(0);
            progressBar.setStringPainted(true);

            // Tambahkan komponen ke frame
            frame.add(statusLabel, BorderLayout.NORTH);
            frame.add(progressBar, BorderLayout.CENTER);
            frame.add(startButton, BorderLayout.SOUTH);

            // Tombol aksi
            startButton.addActionListener(e -> {
                startButton.setEnabled(false); // Nonaktifkan tombol saat proses berjalan
                statusLabel.setText("Proses sedang berjalan...");
                SwingWorker<Void, Integer> worker = new SwingWorker<>() {
                    @Override
                    protected Void doInBackground() {
                        for (int i = 0; i <= 60; i++) {
                            try {
                                Thread.sleep(100); // Simulasi proses berat
                                publish(i); // Update progres
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        }
                        return null;
                    }

                    @Override
                    protected void process(java.util.List<Integer> chunks) {
                        int progress = chunks.get(chunks.size() - 1);
                        progressBar.setValue(progress);
                    }

                    @Override
                    protected void done() {
                        statusLabel.setText("Proses selesai!");
                        startButton.setEnabled(true); // Aktifkan kembali tombol
                    }
                };
                worker.execute(); // Jalankan SwingWorker
            });

            // Tampilkan frame
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
