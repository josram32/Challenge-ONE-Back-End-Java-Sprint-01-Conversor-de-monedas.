import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingConstants;

public class ConversionSolesMoneda extends JFrame {

	private JPanel contentPane;
	private JTextField inputMonto;
	private JTextField inputTipoCambio;
	private JTextField inputTotal;

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
		setTitle("Convertir de Soles (S/.) a otra moneda");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 485, 221);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblMonto = new JLabel("Monto (S/.) a convertir: ");
		lblMonto.setBounds(30, 27, 131, 14);
		contentPane.add(lblMonto);
		
		inputMonto = new JTextField();
		inputMonto.setHorizontalAlignment(SwingConstants.CENTER);
		inputMonto.setText("0.00");
		inputMonto.setBounds(207, 24, 189, 20);
		contentPane.add(inputMonto);
		inputMonto.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Moneda convertir :");
		lblNewLabel.setBounds(30, 63, 113, 14);
		contentPane.add(lblNewLabel);
		
		JComboBox cboMoneda = new JComboBox();
		cboMoneda.setModel(new DefaultComboBoxModel(new String[] {"Seleccione moneda...", "Dolar ($)", "Euros (€)", "Libra Esterlina (£)", "Yen Japonés (¥)", "Won Sul Coreano (₩)"}));
		cboMoneda.setBounds(207, 59, 189, 22);
		contentPane.add(cboMoneda);
		
		JLabel lblTipoCambio = new JLabel("El tipo de cambio es: ");
		lblTipoCambio.setBounds(30, 98, 131, 14);
		contentPane.add(lblTipoCambio);
		
		JLabel lblResultado = new JLabel("El total de la conversion es:");
		lblResultado.setBounds(30, 134, 157, 14);
		contentPane.add(lblResultado);
		
		inputTipoCambio = new JTextField();
		inputTipoCambio.setEditable(false);
		inputTipoCambio.setBounds(207, 95, 189, 20);
		contentPane.add(inputTipoCambio);
		inputTipoCambio.setColumns(10);
		
		inputTotal = new JTextField();
		inputTotal.setEditable(false);
		inputTotal.setBounds(207, 131, 189, 21);
		contentPane.add(inputTotal);
		inputTotal.setColumns(10);
	}
}
