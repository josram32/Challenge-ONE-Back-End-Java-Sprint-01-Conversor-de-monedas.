import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import entidades.Moneda;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import javax.swing.SwingConstants;
import java.awt.Window.Type;

public class ConversionMonedaSoles extends JFrame {

	private JPanel contentPane;
	private JTextField inputMonto;
	private JTextField inputTipoCambio;
	private JTextField inputTotal;
	private JComboBox cboMoneda;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConversionMonedaSoles frame = new ConversionMonedaSoles();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ConversionMonedaSoles() {
		setType(Type.UTILITY);
		setResizable(false);
		setTitle("Convertir a Soles (S/.)");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 447, 269);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblMonto = new JLabel("Monto a convertir: ");
		lblMonto.setBounds(30, 79, 131, 14);
		contentPane.add(lblMonto);
		
		inputMonto = new JTextField();
		inputMonto.setHorizontalAlignment(SwingConstants.CENTER);
		inputMonto.setText("0.00");
		inputMonto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyChar()=='\n' && cboMoneda.getSelectedIndex()==0) 
					JOptionPane.showInternalMessageDialog(null, "Seleccione el tipo de moneda");
				
				if(e.getKeyChar()=='\n' && cboMoneda.getSelectedIndex()>=1) 
					validarMonto();
				
			}
		});
		inputMonto.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				inputMonto.setText("");
			}
		});
		
		inputMonto.setBounds(207, 76, 189, 20);
		contentPane.add(inputMonto);
		inputMonto.setColumns(10);
		
		JLabel lblMoneda = new JLabel("Moneda convertir :");
		lblMoneda.setBounds(30, 27, 113, 14);
		contentPane.add(lblMoneda);
		
		cboMoneda = new JComboBox();
		cboMoneda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cboMoneda.getSelectedIndex()>=1 && !inputMonto.getText().equals("")) {
					validarMonto();
				}
			}
		});
		cboMoneda.setModel(new DefaultComboBoxModel(new String[] {"Seleccione moneda..."}));
		llenarCombo();
		cboMoneda.setBounds(207, 23, 189, 22);
		contentPane.add(cboMoneda);
		
		JLabel lblTipoCambio = new JLabel("El tipo de cambio es: ");
		lblTipoCambio.setBounds(30, 126, 131, 14);
		contentPane.add(lblTipoCambio);
		
		JLabel lblResultado = new JLabel("El total  en Soles (S/.) es:");
		lblResultado.setBounds(30, 162, 157, 14);
		contentPane.add(lblResultado);
		
		inputTipoCambio = new JTextField();
		inputTipoCambio.setHorizontalAlignment(SwingConstants.CENTER);
		inputTipoCambio.setEditable(false);
		inputTipoCambio.setBounds(207, 123, 189, 20);
		contentPane.add(inputTipoCambio);
		inputTipoCambio.setColumns(10);
		
		inputTotal = new JTextField();
		inputTotal.setHorizontalAlignment(SwingConstants.CENTER);
		inputTotal.setEditable(false);
		inputTotal.setBounds(207, 159, 189, 21);
		contentPane.add(inputTotal);
		inputTotal.setColumns(10);
		
		JButton btnReiniciar = new JButton("Reiniciar");
		btnReiniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reiniciar();
			}
		});
		btnReiniciar.setBounds(307, 195, 89, 23);
		contentPane.add(btnReiniciar);
		
		JLabel lblAnuncio1 = new JLabel("*Seleccione una moneda para convertir");
		lblAnuncio1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblAnuncio1.setBounds(207, 44, 202, 14);
		contentPane.add(lblAnuncio1);
		
		JLabel lblAnuncio2 = new JLabel("*Presione enter para convertir");
		lblAnuncio2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblAnuncio2.setBounds(207, 96, 189, 14);
		contentPane.add(lblAnuncio2);
	}
	
	protected void reiniciar() {
		inputMonto.setText("0.00");
		cboMoneda.setSelectedIndex(0);
		inputTipoCambio.setText("");
		inputTotal.setText("");
		
	}
	
	public void llenarCombo() {
		for(Moneda moneda: MenuPrincipal.monedas) {
			cboMoneda.addItem(moneda.getNombre()+moneda.getSimbolo());
		}
	}
	
	public void validarMonto() {
		if(inputMonto.getText().equals("")) 
			JOptionPane.showInternalMessageDialog(null, "Ingrese un monto");
		else if(!inputMonto.getText().matches("^\\d+(\\.\\d+)?$"))
			JOptionPane.showInternalMessageDialog(null, "Ingrese un monto válido\n- No letras\n- No caracteres especiales\n");
		else
			convertir();
	}
	
	public void convertir() {
		
		DecimalFormat df = new DecimalFormat("#.###");
		double monto = Double.parseDouble(inputMonto.getText());
		int monedaSeleccionada = cboMoneda.getSelectedIndex()-1;
		Moneda moneda = MenuPrincipal.monedas.get(monedaSeleccionada);
		double tipoCambio = moneda.getTipoCambio();
		inputTipoCambio.setText(""+tipoCambio);
		double resultado = monto * tipoCambio;
		inputTotal.setText(""+df.format(resultado));		
	}
}
