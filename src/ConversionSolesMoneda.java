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
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Window.Type;

public class ConversionSolesMoneda extends JFrame {

	private JPanel contentPane;
	private JTextField inputMonto;
	private JTextField inputTipoCambio;
	private JTextField inputTotal;
	JComboBox cboMoneda = new JComboBox();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConversionSolesMoneda frame = new ConversionSolesMoneda();
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
	public ConversionSolesMoneda() {
		setType(Type.UTILITY);
		setResizable(false);
		setTitle("Convertir de Soles (S/.) a otra moneda");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 447, 269);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblMonto = new JLabel("Monto (S/.) a convertir: ");
		lblMonto.setBounds(30, 27, 131, 14);
		contentPane.add(lblMonto);
		
		inputMonto = new JTextField();
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
		
		inputMonto.setHorizontalAlignment(SwingConstants.CENTER);
		inputMonto.setText("0.00");
		inputMonto.setBounds(207, 24, 189, 20);
		contentPane.add(inputMonto);
		inputMonto.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Moneda convertir :");
		lblNewLabel.setBounds(30, 78, 113, 14);
		contentPane.add(lblNewLabel);
		cboMoneda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cboMoneda.getSelectedIndex()>=1) {
					validarMonto();
				}
			}
		});
		 
		cboMoneda.setModel(new DefaultComboBoxModel(new String[] {"Seleccione moneda..."}));
		llenarCombo();
		cboMoneda.setBounds(207, 74, 189, 22);
		contentPane.add(cboMoneda);
		
		JLabel lblTipoCambio = new JLabel("El tipo de cambio es: ");
		lblTipoCambio.setBounds(30, 126, 131, 14);
		contentPane.add(lblTipoCambio);
		
		JLabel lblResultado = new JLabel("El total de la conversion es:");
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
		
		JLabel lblAnucio1 = new JLabel("*presione enter para convertir");
		lblAnucio1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblAnucio1.setBounds(207, 44, 150, 14);
		contentPane.add(lblAnucio1);
		
		JButton btnReiniciar = new JButton("Reiniciar");
		btnReiniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reiniciar();
			}
		});
		btnReiniciar.setBounds(307, 195, 89, 23);
		contentPane.add(btnReiniciar);
		
		JLabel lblAnuncio2 = new JLabel("*Seleccione una moneda para convertir");
		lblAnuncio2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblAnuncio2.setBounds(207, 98, 189, 14);
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
		double resultado = monto / tipoCambio;
		inputTotal.setText(""+df.format(resultado));		
	}
}
