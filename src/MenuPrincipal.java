import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import entidades.Moneda;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JInternalFrame;
import java.awt.CardLayout;
import javax.swing.JSeparator;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class MenuPrincipal {

	private JFrame frmConversorDeMonedas;
	public static ArrayList<Moneda> monedas = new ArrayList<Moneda>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuPrincipal window = new MenuPrincipal();
					window.frmConversorDeMonedas.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MenuPrincipal() {
		initialize();
		crearMonedasIniciales();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmConversorDeMonedas = new JFrame();
		frmConversorDeMonedas.setTitle("Conversor de monedas");
		frmConversorDeMonedas.setBounds(100, 100, 499, 346);
		frmConversorDeMonedas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmConversorDeMonedas.getContentPane().setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		frmConversorDeMonedas.setJMenuBar(menuBar);
		
		JMenu mnConversiones = new JMenu("Conversiones");
		menuBar.add(mnConversiones);
		
		JMenuItem mnItemSolesMoneda = new JMenuItem("De Soles(S/.) a otra moneda");
		mnItemSolesMoneda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				ConversionSolesMoneda frame = new ConversionSolesMoneda();
				frame.setVisible(true);
				
			}
		});
		mnConversiones.add(mnItemSolesMoneda);
		
		JMenuItem mnItemMonedaSoles = new JMenuItem("De otra moneda a Soles (S/.)");
		mnItemMonedaSoles.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				ConversionMonedaSoles frame = new ConversionMonedaSoles();
				frame.setVisible(true);
			}
		});			
		
		mnConversiones.add(mnItemMonedaSoles);
		
		JMenu mnMantenimiento = new JMenu("Mantenimiento");
		menuBar.add(mnMantenimiento);
		
		JMenuItem mnItemAñadirMoneda = new JMenuItem("Mantenimiento moneda");
		mnItemAñadirMoneda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MantenimientoMoneda frame = new MantenimientoMoneda();
				frame.setVisible(true);
			}
		});
		mnMantenimiento.add(mnItemAñadirMoneda);
		
		JMenuItem mnItemTipoCambio = new JMenuItem("Tipo de cambio");
		mnMantenimiento.add(mnItemTipoCambio);
		
		JMenu mnAyuda = new JMenu("Ayuda");
		menuBar.add(mnAyuda);
		
		JMenuItem mnItemAcercaDe = new JMenuItem("Acerca de...");
		mnAyuda.add(mnItemAcercaDe);
		
		JMenu mnSalir = new JMenu("Salir");
		menuBar.add(mnSalir);
		
		JMenuItem mnItemSalir = new JMenuItem("Salir");
		mnItemSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmConversorDeMonedas.dispose();
			}
		});
		mnSalir.add(mnItemSalir);
	}
	
	public void crearMonedasIniciales() {
		Moneda moneda1 = new Moneda("Dolar","($)",3.63);
		monedas.add(moneda1);
		Moneda moneda2 = new Moneda("Euro","(€)",3.95);
		monedas.add(moneda2);
		Moneda moneda3 = new Moneda("Libra Esterlina","(£)",4.62);
		monedas.add(moneda3);
		Moneda moneda4 = new Moneda("Yen Japonés","(¥)",0.025);
		monedas.add(moneda4);
		Moneda moneda5 = new Moneda("Won Sul-Coreano","(₩)",0.028);
		monedas.add(moneda5);
	}
}
