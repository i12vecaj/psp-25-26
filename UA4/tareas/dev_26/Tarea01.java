import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;


public class Tarea01 extends JFrame {

   
    private JTextField campoServidor, campoUsuario;
    private JPasswordField campoPassword;
    private JTextArea areaLog;
    private JButton botonConectar;

    public Tarea01() {
      
        super("Tarea 1 - Cliente FTP");
        setLayout(new BorderLayout(10, 10));

        JPanel panelInputs = new JPanel(new GridLayout(4, 2, 8, 8));
        panelInputs.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        panelInputs.add(new JLabel("Servidor FTP:"));
        campoServidor = new JTextField("ftp.rediris.es");
        panelInputs.add(campoServidor);

        panelInputs.add(new JLabel("Usuario:"));
        campoUsuario = new JTextField("anonymous");
        panelInputs.add(campoUsuario);

        panelInputs.add(new JLabel("Contraseña:"));
        campoPassword = new JPasswordField("");
        panelInputs.add(campoPassword);

        botonConectar = new JButton("Establecer Conexión");
        botonConectar.setBackground(new Color(70, 130, 180));
        botonConectar.setForeground(Color.WHITE);
        panelInputs.add(new JLabel("Acción:"));
        panelInputs.add(botonConectar);

        areaLog = new JTextArea(12, 45);
        areaLog.setEditable(false);
        areaLog.setFont(new Font("Monospaced", Font.PLAIN, 12));
        areaLog.setBackground(new Color(245, 245, 245));
        JScrollPane scrollPane = new JScrollPane(areaLog);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Log de Actividad"));

        add(panelInputs, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        botonConectar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ejecutarProcesoFTP();
            }
        });
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null); 
    }

    private void ejecutarProcesoFTP() {
        FTPClient cliente = new FTPClient();
        String servidor = campoServidor.getText().trim();
        String user = campoUsuario.getText().trim();
        String pass = new String(campoPassword.getPassword());

        try {
            areaLog.append("Intentando conectar a: " + servidor + "...\n");
            cliente.connect(servidor);

            int respuesta = cliente.getReplyCode();
            areaLog.append("Servidor dice: " + cliente.getReplyString());

            if (!FTPReply.isPositiveCompletion(respuesta)) {
                cliente.disconnect();
                areaLog.append("CONEXIÓN RECHAZADA: Código " + respuesta + "\n");
                return;
            }

            areaLog.append("Autenticando usuario: " + user + "...\n");
            boolean loginExitoso = cliente.login(user, pass);

            if (loginExitoso) {
                areaLog.append(">> LOGIN CORRECTO <<\n");
                areaLog.append("Estado: Conectado y listo.\n");
                
                cliente.logout();
                areaLog.append("Cerrando sesión...\n");
            } else {
                areaLog.append("ERROR: Usuario o contraseña incorrectos.\n");
            }

            cliente.disconnect();
            areaLog.append("Desconexión finalizada.\n");
            areaLog.append("---------------------------------------------------\n");

        } catch (Exception ex) {
            areaLog.append("ERROR CRÍTICO: " + ex.getMessage() + "\n");
        }
    }

    public static void main(String[] args) {
    
        SwingUtilities.invokeLater(() -> {
            new Tarea01().setVisible(true);
        });
    }
}