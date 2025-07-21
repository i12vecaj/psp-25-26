// CarreraHilos - Java Swing
// @author: dperez@ceslopedevega.com
//
// Compilar y ejecutar: javac CarreraHilos.java && java CarreraHilos
//
// Funcionalidades a añadir: 
//          1. Tenéis que hacer las etiquetas progresoHilo1, progresoHilo2 y progresoHilo3 se actualicen con el valor de la barra de progreso en "tiempo real"
//          2. ¿ ESTOS 3 new ChangeListener() ... PODRÍAIS CAMBIARLOS A UNA CLASE QUE IMPLEMENTE CHANGE LISTENER COMÚN ? .. Como se hace en OyenteBoton
//          3. Investigar el EDT Event Dispatcher Thread de Java Swing y porqué sí o no se actualizan ciertos elementos
//          4. ¿ Cómo podemos evitar los hiloX.stop() ... ?

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import java.awt.GridLayout;

class MainWindow extends JFrame 
{
    private final String author = "Daniel Pérez Rodríguez";
    private final int mwWidth = 600;
    private final int mwHeight = 300;

    private Container panel = null;
    private Container panelHilo1 = null;
    private Container panelHilo2 = null;
    private Container panelHilo3 = null;
    
    private FlowLayout layoutHilo1 = null;
    private FlowLayout layoutHilo2 = null;
    private FlowLayout layoutHilo3 = null;
    private JProgressBar progresoBarraHilo1 = null;
    private JProgressBar progresoBarraHilo2 = null;
    private JProgressBar progresoBarraHilo3 = null;
    private JSlider prioridadSliderHilo1 = null;
    private JSlider prioridadSliderHilo2 = null;
    private JSlider prioridadSliderHilo3 = null;
    private JLabel prioridadHilo1 = null;
    private JLabel prioridadHilo2 = null;
    private JLabel prioridadHilo3 = null;

    private JLabel progresoHilo1 = null;
    private JLabel progresoHilo2 = null;
    private JLabel progresoHilo3 = null;

    private JLabel etiquetaHilo1 = null;
    private JLabel etiquetaHilo2 = null;
    private JLabel etiquetaHilo3 = null;
    
    private JButton comenzarCarrera = null;
    private JButton nuevaCarrera = null;
    
    Carrera carrera = null;
    HiloCorredor hilo1 = null;
    HiloCorredor hilo2 = null;
    HiloCorredor hilo3 = null;

    public MainWindow()
    {
        configurarVentanaPrincipal();        
    }

    private void configurarVentanaPrincipal()
    {
        setTitle("Carrera de Hilos - " + author);   
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(mwWidth, mwHeight);
        setLocation(0,0);

        int minimoBarras = 0;
        int maximoBarras = 1000;
    
        layoutHilo1 = new FlowLayout(FlowLayout.LEFT, 10,20);        
        layoutHilo2 = new FlowLayout(FlowLayout.LEFT, 10,20);
        layoutHilo3 = new FlowLayout(FlowLayout.LEFT, 10,20);

        panelHilo1 = new Container();
        panelHilo2 = new Container();
        panelHilo3 = new Container();

        panelHilo1.setLayout(layoutHilo1);
        panelHilo2.setLayout(layoutHilo2);
        panelHilo3.setLayout(layoutHilo3);

        etiquetaHilo1 = new JLabel("Hilo 1");
        progresoBarraHilo1 = new JProgressBar(minimoBarras, maximoBarras);
        progresoHilo1 = new JLabel("0");
        prioridadSliderHilo1 = new JSlider(JSlider.HORIZONTAL, 1, 10, 5) ;
        prioridadHilo1 = new JLabel("Prioridad: "+prioridadSliderHilo1.getValue());

        etiquetaHilo2 = new JLabel("Hilo 2");
        progresoBarraHilo2 = new JProgressBar(minimoBarras, maximoBarras);
        progresoHilo2 = new JLabel("0");
        prioridadSliderHilo2 = new JSlider(JSlider.HORIZONTAL, 1, 10, 5) ;
        prioridadHilo2 = new JLabel("Prioridad: "+prioridadSliderHilo2.getValue());

        etiquetaHilo3 = new JLabel("Hilo 3");
        progresoBarraHilo3 = new JProgressBar(minimoBarras, maximoBarras);
        progresoHilo3 = new JLabel("0");
        prioridadSliderHilo3 = new JSlider(JSlider.HORIZONTAL, 1, 10, 5) ;
        prioridadHilo3 = new JLabel("Prioridad: "+prioridadSliderHilo3.getValue());

        comenzarCarrera = new JButton("Comenzar Carrera");        
        nuevaCarrera = new JButton("Nueva Carrera");
        nuevaCarrera.setEnabled(false);
        
        panel = getContentPane();
        panel.setLayout(new GridLayout(5,1));
        
        panel.add(comenzarCarrera);
        panel.add(panelHilo1);
        panel.add(panelHilo2);
        panel.add(panelHilo3);
        panel.add(nuevaCarrera);

        panelHilo1.add(etiquetaHilo1);
        panelHilo1.add(progresoBarraHilo1);
        panelHilo1.add(progresoHilo1);        
        panelHilo1.add(prioridadSliderHilo1);
        panelHilo1.add(prioridadHilo1);

        panelHilo2.add(etiquetaHilo2);
        panelHilo2.add(progresoBarraHilo2);
        panelHilo2.add(progresoHilo2);
        panelHilo2.add(prioridadSliderHilo2);
        panelHilo2.add(prioridadHilo2);

        panelHilo3.add(etiquetaHilo3);
        panelHilo3.add(progresoBarraHilo3);
        panelHilo3.add(progresoHilo3);
        panelHilo3.add(prioridadSliderHilo3);
        panelHilo3.add(prioridadHilo3);

        comenzarCarrera.addActionListener(new OyenteBoton());      
        nuevaCarrera.addActionListener(new OyenteBoton());

        // ¿ ESTOS 3 new ChangeListener() ... PODRÍAIS CAMBIARLOS A UNA CLASE QUE IMPLEMENTE CHANGE LISTENER COMÚN ? .. Como se hace en OyenteBoton
        prioridadSliderHilo1.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
                prioridadHilo1.setText("Prioridad: " + prioridadSliderHilo1.getValue());
                System.out.println("Prioridad Hilo 1: "+ prioridadSliderHilo1.getValue());
            }
        });

        prioridadSliderHilo2.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
                prioridadHilo2.setText("Prioridad: " + prioridadSliderHilo2.getValue());
                System.out.println("Prioridad Hilo 2: "+ prioridadSliderHilo2.getValue());
            }
        });

        prioridadSliderHilo3.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
                prioridadHilo3.setText("Prioridad: " + prioridadSliderHilo3.getValue());
                System.out.println("Prioridad Hilo 3: "+ prioridadSliderHilo3.getValue());
            }
        });
        
        setVisible(true);
    }

    class OyenteBoton implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent event) {
            if(event.getSource() == comenzarCarrera)
            {
                comenzarCarrera.setEnabled(false);
                nuevaCarrera.setEnabled(true);
                prioridadSliderHilo1.setEnabled(false);
                prioridadSliderHilo2.setEnabled(false);
                prioridadSliderHilo3.setEnabled(false);   
                carrera = new Carrera();
                hilo1 = new HiloCorredor(1, progresoBarraHilo1,carrera);
                hilo2 = new HiloCorredor(2, progresoBarraHilo2,carrera);
                hilo3 = new HiloCorredor(3, progresoBarraHilo3,carrera);
    
                hilo1.setPriority(prioridadSliderHilo1.getValue());
                hilo2.setPriority(prioridadSliderHilo2.getValue());
                hilo3.setPriority(prioridadSliderHilo3.getValue());
                
                System.out.println("Comienza la Carrera !");
                
                hilo1.start();
                hilo2.start();
                hilo3.start();
            }
            else
            {
                comenzarCarrera.setEnabled(true);
                nuevaCarrera.setEnabled(false);
                prioridadSliderHilo1.setEnabled(true);
                prioridadSliderHilo2.setEnabled(true);
                prioridadSliderHilo3.setEnabled(true);
                prioridadSliderHilo1.setValue(5);
                prioridadSliderHilo2.setValue(5);
                prioridadSliderHilo3.setValue(5);
                progresoBarraHilo1.setValue(0);
                progresoBarraHilo2.setValue(0);
                progresoBarraHilo3.setValue(0);
                progresoHilo1.setText(Integer.toString(progresoBarraHilo1.getValue()));
                progresoHilo2.setText(Integer.toString(progresoBarraHilo2.getValue()));
                progresoHilo3.setText(Integer.toString(progresoBarraHilo3.getValue()));
                
                // CAVEAT EMPTOR: estos  métodos están deprecated (en desuso). En esta caso sencillo no hay problema, pero es una MALA, MALÍSIMA PRÁCTICA.
                hilo1.stop();
                hilo2.stop();
                hilo3.stop();
                System.out.println("Configura la nueva Carrera");                
            }
        }        
    }
}

class Carrera
{
    private int ganador = 0;
    public synchronized void setGanador(int hiloGanador) {
        if(ganador == 0)
        {
            ganador = hiloGanador;
            System.out.println("-------------------------");
            System.out.println("El ganador es el Hilo: " + ganador);
            System.out.println("-------------------------");
            JOptionPane.showMessageDialog(null,"El ganador es el Hilo: " + ganador);
        }
        else
        {
            System.out.println("Lo siento Hilo " + hiloGanador + " el ganador es el Hilo " + ganador);
        }        
    }

    public synchronized int ganador() {
        return ganador;
    }
} 


    

class HiloCorredor extends Thread
{
    JProgressBar barraProgreso;
    private Carrera carrera;
    int id;
    public HiloCorredor(int id, JProgressBar barraProgreso, Carrera carrera) {        
        this.id = id;
        this.barraProgreso = barraProgreso;
        this.carrera = carrera;
    }
    
    @Override
    public void run() { 
        System.out.println("Arranca Hilo " + id );
        for(int j=barraProgreso.getMinimum(); j <= barraProgreso.getMaximum(); j++)
        {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {                
                    // This will be called on the EDT <- INVESTIGAD ESTO !
                    barraProgreso.setValue(barraProgreso.getValue()+1);
                }
            });
            
            System.out.println("Hilo " + id + " - Valor: " + j);            
            try {
                Thread.sleep(10);
            } 
            catch (Exception e) {                
            }
        }
        carrera.setGanador(id);
    }
}




public class CarreraHilos {
    public static void main(String[] args) {
        System.out.println("----------------");
        System.out.println("Carrera de Hilos");
        System.out.println("----------------");
        
        MainWindow mw = new MainWindow();
    }
}
