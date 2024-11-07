import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JogoDeCombate extends JFrame {
    private Campeao campeao1;
    private Campeao campeao2;
    private int turnosRestantes;
    private JTextArea statusArea1;
    private JTextArea statusArea2;
    private JLabel resultadoLabel;

    public JogoDeCombate(Campeao campeao1, Campeao campeao2, int turnos) {
        this.campeao1 = campeao1;
        this.campeao2 = campeao2;
        this.turnosRestantes = turnos;

        setTitle("Jogo de Combate");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(1, 2));

        JPanel painelCampeao1 = new JPanel();
        painelCampeao1.setLayout(new BorderLayout());
        painelCampeao1.setBackground(Color.WHITE);
        painelCampeao1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JLabel nomeLabel1 = new JLabel(campeao1.getNome(), SwingConstants.CENTER);
        painelCampeao1.add(nomeLabel1, BorderLayout.NORTH);
        statusArea1 = new JTextArea();
        statusArea1.setEditable(false);
        painelCampeao1.add(statusArea1, BorderLayout.CENTER);

        JPanel painelCampeao2 = new JPanel();
        painelCampeao2.setLayout(new BorderLayout());
        painelCampeao2.setBackground(Color.WHITE);
        painelCampeao2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JLabel nomeLabel2 = new JLabel(campeao2.getNome(), SwingConstants.CENTER);
        painelCampeao2.add(nomeLabel2, BorderLayout.NORTH);
        statusArea2 = new JTextArea();
        statusArea2.setEditable(false);
        painelCampeao2.add(statusArea2, BorderLayout.CENTER);

        painel.add(painelCampeao1);
        painel.add(painelCampeao2);

        JButton botaoTurno = new JButton("Próximo Turno");
        botaoTurno.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executarTurno();
            }
        });

        resultadoLabel = new JLabel("Turnos restantes: " + turnosRestantes, SwingConstants.CENTER);

        add(painel, BorderLayout.CENTER);
        add(botaoTurno, BorderLayout.SOUTH);
        add(resultadoLabel, BorderLayout.NORTH);

        atualizarStatus();
    }

    private void executarTurno() {
        if (turnosRestantes > 0 && campeao1.estaVivo() && campeao2.estaVivo()) {
            campeao2.takeDamage(campeao1.getAtaque());
            campeao1.takeDamage(campeao2.getAtaque());
            turnosRestantes--;
            resultadoLabel.setText("Turnos restantes: " + turnosRestantes);
            atualizarStatus();

            if (!campeao1.estaVivo() || !campeao2.estaVivo() || turnosRestantes == 0) {
                resultadoLabel.setText("### FIM DO COMBATE ###");
            }
        }
    }

    private void atualizarStatus() {
        statusArea1.setText("Vida: " + campeao1.getVida() + (campeao1.estaVivo() ? "" : " (morreu)"));
        statusArea2.setText("Vida: " + campeao2.getVida() + (campeao2.estaVivo() ? "" : " (morreu)"));
    }

    public static void main(String[] args) {
        Campeao campeao1 = new Campeao("Han Solo", 50, 8, 1);
        Campeao campeao2 = new Campeao("Jabba The Hut", 40, 10, 2);
        int turnos = 5;

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JogoDeCombate jogo = new JogoDeCombate(campeao1, campeao2, turnos);
                jogo.setVisible(true);
            }
        });
    }
}
