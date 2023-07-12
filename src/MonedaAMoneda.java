import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import entidades.Moneda;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.awt.event.ActionEvent;

public class MonedaAMoneda extends JFrame {

	private JPanel contentPane;
	private JComboBox cboMoneda2;
	private JTextField inputMonto;
	private JTextField inputTotal;
	private JComboBox cboMoneda1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MonedaAMoneda frame = new MonedaAMoneda();
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
	public MonedaAMoneda() {
		setResizable(false);
		setTitle("Convertir de moneda a otra moneda");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 423, 257);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Seleccione una moneda de origen");
		lblNewLabel.setBounds(20, 21, 177, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblSeleccioneLaMoneda = new JLabel("Seleccione la moneda a convertir");
		lblSeleccioneLaMoneda.setBounds(20, 106, 215, 14);
		contentPane.add(lblSeleccioneLaMoneda);
		
		cboMoneda1 = new JComboBox();
		cboMoneda1.setModel(new DefaultComboBoxModel(new String[] {"Seleccione moneda..."}));
		cboMoneda1.setBounds(20, 46, 177, 22);
		contentPane.add(cboMoneda1);
		
		cboMoneda2 = new JComboBox();
		cboMoneda2.setModel(new DefaultComboBoxModel(new String[] {"Seleccione moneda..."}));
		cboMoneda2.setBounds(20, 131, 177, 22);
		contentPane.add(cboMoneda2);
		
		cboMoneda1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cboMoneda1.getSelectedIndex() >= 1 && cboMoneda2.getSelectedIndex()>=1) {
					convertir();
				}
			}
		});
		
		cboMoneda2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cboMoneda1.getSelectedIndex() >= 1 && cboMoneda2.getSelectedIndex()>=1) {
					convertir();
				}
			}
		});
		
		inputMonto = new JTextField();
		inputMonto.setHorizontalAlignment(SwingConstants.CENTER);
		inputMonto.setText("0.00");
		inputMonto.setBounds(256, 47, 129, 20);
		contentPane.add(inputMonto);
		inputMonto.setColumns(10);
		
		inputMonto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == '\n' && cboMoneda1.getSelectedIndex() == 0)
					JOptionPane.showInternalMessageDialog(null, "Seleccione el tipo de moneda de origen");
				
				if (e.getKeyChar() == '\n' && cboMoneda2.getSelectedIndex() == 0)
					JOptionPane.showInternalMessageDialog(null, "Seleccione el tipo de moneda a convertir");

				if (e.getKeyChar() == '\n' && cboMoneda1.getSelectedIndex() >= 1 && cboMoneda2.getSelectedIndex()>=1)
					convertir();
			}
		});
		
		inputMonto.addMouseListener(new MouseAdapter() {

			public void mouseClicked(MouseEvent e) {
				inputMonto.setText("");
			}
		});
		
		inputTotal = new JTextField();
		inputTotal.setHorizontalAlignment(SwingConstants.CENTER);
		inputTotal.setEditable(false);
		inputTotal.setColumns(10);
		inputTotal.setBounds(256, 132, 129, 20);
		contentPane.add(inputTotal);
		
		JLabel lblNewLabel_1 = new JLabel("Monto a convertir");
		lblNewLabel_1.setBounds(256, 21, 129, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("El total es");
		lblNewLabel_1_1.setBounds(256, 106, 129, 14);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_2 = new JLabel("*presione enter para convertir");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 9));
		lblNewLabel_2.setBounds(256, 68, 168, 14);
		contentPane.add(lblNewLabel_2);
		
		JButton btnReiniciar = new JButton("Reiniciar");
		btnReiniciar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiarCampos();
			}
		});
		btnReiniciar.setBounds(296, 179, 89, 23);
		contentPane.add(btnReiniciar);
		
		llenarCombos();
	}

	protected void limpiarCampos() {
		//eliminamos los items de los combos para de esta manera volverlo a llenar y se añadan monedas nuevas en caso se hayan añadido
		//De esta manera podemos actualizar las monedas sin necesidad de cerrar y abrir el frame
				
		cboMoneda1.removeAllItems();
		cboMoneda2.removeAllItems();
		cboMoneda1.setModel(new DefaultComboBoxModel(new String[] {"Seleccione moneda..."}));
		cboMoneda2.setModel(new DefaultComboBoxModel(new String[] {"Seleccione moneda..."}));	
		llenarCombos();
		inputMonto.setText("0.00");
		inputTotal.setText("");
		
	}

	protected void llenarCombos() {
		for(Moneda moneda : MenuPrincipal.monedas) {
			cboMoneda1.addItem(moneda.getNombre()+moneda.getSimbolo());
			cboMoneda2.addItem(moneda.getNombre()+moneda.getSimbolo());
		}
	}
	
	public Boolean validarMonto() {
		if (inputMonto.getText().equals("")) {
			JOptionPane.showInternalMessageDialog(null, "Ingrese un monto");
			return false;
		}
		if (!inputMonto.getText().matches("^\\d+(\\.\\d+)?$")) {
			JOptionPane.showInternalMessageDialog(null,
					"Ingrese un monto válido\n- No letras\n- No caracteres especiales\n");
			return false;
		}
		return true;
	}

	public void convertir() {

		Boolean ok = validarMonto();

		if (ok) {
			DecimalFormat df = new DecimalFormat("#.###");
			double monto = Double.parseDouble(inputMonto.getText());
			
			//primero convertimos la moneda de origen seleccionada a soles
			int monedaOrigen = cboMoneda1.getSelectedIndex() - 1;
			Moneda moneda1 = MenuPrincipal.monedas.get(monedaOrigen);
			double tipoCambio = moneda1.getTipoCambio();
			double resultado = monto * tipoCambio;
			
			//con el resultado en soles convertimos a la moneda que se desea convertir
			int monedaConversion = cboMoneda2.getSelectedIndex() - 1;
			Moneda moneda2 = MenuPrincipal.monedas.get(monedaConversion);
			tipoCambio = moneda2.getTipoCambio();
			resultado = resultado / tipoCambio;
			inputTotal.setText("" + df.format(resultado));
		}
	}
}
